package com.zidio.keystone.service;
import com.zidio.keystone.entity.WorkOrder;
import com.zidio.keystone.entity.WorkOrderHistory;
import com.zidio.keystone.entity.User;
import com.zidio.keystone.config.RoleAccessConfig;
import com.zidio.keystone.repository.WorkOrderHistoryRepository;
import com.zidio.keystone.repository.WorkOrderRepository;
import com.zidio.keystone.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkOrderService {
    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderHistoryRepository historyRepository;
    private final NotificationService notificationService;
    private final UserRepository userRepository;
    public WorkOrderService(WorkOrderRepository workOrderRepository, WorkOrderHistoryRepository historyRepository, 
        NotificationService notificationService, UserRepository userRepository) {
        this.workOrderRepository = workOrderRepository;
        this.historyRepository = historyRepository;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }
    public List<WorkOrder> getAllWorkOrders() {
        return workOrderRepository.findAll();
    }
    public Page<WorkOrder> getAllWorkOrders(Pageable pageable) {
        return workOrderRepository.findAll(pageable);
    }
    public Page<WorkOrder> getWorkOrdersByTechnician(Long assignedTo, Pageable pageable) {
        return workOrderRepository.findByAssignedTo(assignedTo,pageable);
    }
    public List<WorkOrder> searchWorkOrders(String title) {
        return workOrderRepository.findByTitleContainingIgnoreCase(title); 
    }
    public List<WorkOrder> searchWorkOrdersByStatus(String status) {
        return workOrderRepository.findByStatusIgnoreCase(status);
    }
    public Optional<WorkOrder> getWorkOrderById(Long id) {
        return workOrderRepository.findById(id);
    }
    public WorkOrder createWorkOrder(WorkOrder workOrder) {
        if (workOrder.getDueDate() == null) {
            workOrder.setDueDate( calculationDueDate(workOrder.getPriority()));
        }
        return workOrderRepository.save(workOrder);
    }
    public boolean isOverdue(WorkOrder workOrder) {
        if (workOrder.getDueDate() == null) {
            return  false;
        }
        return workOrder.getDueDate().isBefore(java.time.LocalDateTime.now());

    }
    public String getSlaStatus(WorkOrder workOrder) {
        if (workOrder.getDueDate() == null) {
            return "NO_DUE_DATE";
        }
        if (workOrder.getDueDate().isBefore(java.time.LocalDateTime.now())) {
            return "BREACHED";
        }
        if (workOrder.getDueDate().isBefore(java.time.LocalDateTime.now().plusHours(24))) {
            return "AT_RISK";
        }
        return "ON_TIME";
    }

    public WorkOrder updateWorkOrder(Long id, WorkOrder workOrderDetails) {
    WorkOrder updated = workOrderRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Work Order not found: " + id
            ));

    String currentStatus = updated.getStatus();
    if ("COMPLETED".equalsIgnoreCase(currentStatus)
            || "CLOSED".equalsIgnoreCase(currentStatus)
            || "CANCELLED".equalsIgnoreCase(currentStatus)) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Cannot edit a completed, closed, or cancelled Work Order"
        );
    }
    updated.setTitle(workOrderDetails.getTitle());
    updated.setDescription(workOrderDetails.getDescription());
    updated.setPriority(workOrderDetails.getPriority());
    updated.setSite(workOrderDetails.getSite());

    return workOrderRepository.save(updated);
    }
    
    public WorkOrder updateWorkOrderStatus(Long id, String newStatus) {
    WorkOrder updated = workOrderRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Work Order not found: " + id
            ));
    if (newStatus == null || newStatus.isBlank()) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Status is required"
        );
    }
    newStatus = newStatus.trim().toUpperCase();
    String oldStatus = updated.getStatus();
    if (!isValidTransition(oldStatus, newStatus)) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Invalid status transition: "
                        + oldStatus + " -> " + newStatus
        );
    }
    if (oldStatus.equals(newStatus)) {
        return updated;
    }
    updated.setStatus(newStatus);
    WorkOrderHistory history = new WorkOrderHistory();
    history.setWorkOrderId(updated.getId());
    history.setOldStatus(oldStatus);
    history.setNewStatus(newStatus);
    historyRepository.save(history);
    return workOrderRepository.save(updated);
    }

   @Transactional
    public WorkOrder updateWorkOrderAssignment(Long id, Long assignedTo) {
     Optional<WorkOrder> existing = workOrderRepository.findById(id);
       if (existing.isPresent()) {
        WorkOrder updated = existing.get();
        User technician = userRepository.findById(assignedTo)
        .orElseThrow(() -> new RuntimeException("Technician not found: " + assignedTo));
        if (!RoleAccessConfig.TECHNICIAN.equalsIgnoreCase(technician.getRole())) {
            throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Work Order can only be assigned to a Technician"
            );
        }
        updated.setAssignedTo(assignedTo);
        String oldStatus = updated.getStatus();
        updated.setStatus("ASSIGNED");
        WorkOrderHistory history = new WorkOrderHistory();
        history.setWorkOrderId(updated.getId());
        history.setOldStatus(oldStatus);
        history.setNewStatus("ASSIGNED");
        history.setChangedAt(java.time.LocalDateTime.now());
        historyRepository.saveAndFlush(history);
        System.out.println("HISTORY SAVED FOR WORK ORDER: " + updated.getId());
        return workOrderRepository.save(updated);

       }
        throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Work Order not found: " + id
        );
   }
   public List<WorkOrder>
   getWorkOrdersByTechnician(Long assignedTo) {
    return workOrderRepository.findByAssignedTo(assignedTo);
   }
   public List<WorkOrder> getWorkOrdersByCustomer(Long customerId) {
    return workOrderRepository.findBySiteCustomerId(customerId);
    }
   private boolean isValidTransition(String oldStatus, String newStatus) {
    if (oldStatus.equals(newStatus)) {
        return true;
    }
    if (oldStatus.equals("NEW") && newStatus.equals("ASSIGNED")) {
        return true;
    }
    if (oldStatus.equals("ASSIGNED") && newStatus.equals("IN_PROGRESS")) {
        return true;
    }
    if (oldStatus.equals("IN_PROGRESS") && newStatus.equals("ON_HOLD")) {
        return true;
    }
    if (oldStatus.equals("ON_HOLD") && newStatus.equals("IN_PROGRESS")) {
        return true;
    }
    if (oldStatus.equals("IN_PROGRESS") && newStatus.equals("COMPLETED")) {
        return true;
    }
    if (oldStatus.equals("COMPLETED") && newStatus.equals("CLOSED")) {
        return true;
    }
    if (!oldStatus.equals("CLOSED") && newStatus.equals("CANCELLED")) {
        return true;
    }
    return false;
    }

    public java.time.LocalDateTime calculationDueDate(String priority) {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        if (priority.equalsIgnoreCase("HIGH")) {
            return now.plusHours(24);
        }
        if (priority.equalsIgnoreCase("MEDIUM")) {
            return now.plusHours(48);
        }
        return now.plusHours(72);
    }
   @Scheduled(fixedRate = 60000)
    public void checkOverdueWorkOrder() {
    List<WorkOrder> workOrders = workOrderRepository.findAll();
    for (WorkOrder workOrder : workOrders) {
        if (isOverdue(workOrder)) {
            System.out.println(
                "SLA BREACHED: Work Order ID = " + workOrder.getId()
            );
            notificationService.createNotification(
                workOrder.getId(),
                "SLA breached for Work Order ID: " + workOrder.getId()
            );
        }
    }
 }
    public void deleteWorkOrder(Long id) {
        workOrderRepository.deleteById(id);
    }
}
