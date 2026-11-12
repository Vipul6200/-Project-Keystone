package com.zidio.keystone.controller;
import com.zidio.keystone.dto.WorkOrderDTO;
import com.zidio.keystone.entity.WorkOrder;
import com.zidio.keystone.service.WorkOrderService;
import com.zidio.keystone.entity.Site;
import com.zidio.keystone.entity.User;
import com.zidio.keystone.repository.SiteRepository;
import com.zidio.keystone.repository.UserRepository;
import com.zidio.keystone.config.RoleAccessConfig;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/work-orders")
public class WorkOrderController {
    private final WorkOrderService workOrderService;
    private final SiteRepository siteRepository;
    private final UserRepository userRepository;
    

    public WorkOrderController(WorkOrderService workOrderService, SiteRepository siteRepository, UserRepository userRepository) {
        this.workOrderService = workOrderService;
        this.siteRepository = siteRepository;
        this.userRepository = userRepository;
       
    }

 @GetMapping
 public List<WorkOrder> getAllWorkOrders(Authentication authentication) {
  User user = userRepository.findByUsername(authentication.getName())
      .orElseThrow(() -> new RuntimeException("User not found"));
      if (user.getRole().equalsIgnoreCase("TECHNICIAN")) {
          return workOrderService.getWorkOrdersByTechnician(user.getId());
      }
      if (user.getRole().equalsIgnoreCase("CUSTOMER")) {
          return workOrderService.getWorkOrdersByCustomer(user.getId());
      }
      return workOrderService.getAllWorkOrders();

   }

 @GetMapping("/paged")
      public Page<WorkOrder> getAllWorkOrders(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        Authentication authentication) {

    Pageable pageable = PageRequest.of(page, size);
    User user = userRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (user.getRole().equalsIgnoreCase(RoleAccessConfig.TECHNICIAN)) {
        return workOrderService.getWorkOrdersByTechnician(user.getId(), pageable);
    }

    return workOrderService.getAllWorkOrders(pageable);
  }
  @GetMapping("/{id}")
    public Optional<WorkOrder> getWorkOrderById(@PathVariable Long id,Authentication authentication) {
    User user = userRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));

    Optional<WorkOrder> workOrder = workOrderService.getWorkOrderById(id);
    if (user.getRole().equalsIgnoreCase(RoleAccessConfig.TECHNICIAN)
            && workOrder.isPresent()
            && !user.getId().equals(workOrder.get().getAssignedTo())) {
        throw new AccessDeniedException("You are not allowed to access this work order");
    }
    return workOrder;
  }

 @GetMapping("/search")
 public List<WorkOrder> searchWorkOrders(@RequestParam String title) {
    return workOrderService.searchWorkOrders(title);
   }

 @GetMapping("/filter")
  public List<WorkOrder> filterByStatus(@RequestParam String status) {
    return workOrderService.searchWorkOrdersByStatus(status);
   }

 @PostMapping
 public WorkOrder createWorkOrder(@Valid @RequestBody WorkOrderDTO workOrderDTO) {
    WorkOrder workOrder = new WorkOrder();
    workOrder.setTitle(workOrderDTO.getTitle());
    workOrder.setDescription(workOrderDTO.getDescription());
    workOrder.setStatus(workOrderDTO.getStatus());
    workOrder.setPriority(workOrderDTO.getPriority());
    workOrder.setAssignedTo(workOrderDTO.getAssignedTo());
    workOrder.setDueDate(workOrderDTO.getDueDate());

    if (workOrderDTO.getSiteId() != null) {
    Site site = siteRepository.findById(workOrderDTO.getSiteId())
            .orElseThrow(() -> new RuntimeException("Site not found"));
             workOrder.setSite(site);
      }
    return workOrderService.createWorkOrder(workOrder);
   }

 @PutMapping("/{id}")
 public WorkOrder updateWorkOrder(@PathVariable Long id, @Valid @RequestBody WorkOrderDTO workOrderDTO) {
   WorkOrder workOrderDetails = new WorkOrder();
   workOrderDetails.setTitle(workOrderDTO.getTitle());
    workOrderDetails.setDescription(workOrderDTO.getDescription());
    workOrderDetails.setStatus(workOrderDTO.getStatus());
    workOrderDetails.setPriority(workOrderDTO.getPriority()); 
    workOrderDetails.setAssignedTo(workOrderDTO.getAssignedTo());
    workOrderDetails.setDueDate(workOrderDTO.getDueDate());

    if (workOrderDTO.getSiteId() != null) {
    Site site = siteRepository.findById(workOrderDTO.getSiteId())
            .orElseThrow(() -> new RuntimeException("Site not found"));
    workOrderDetails.setSite(site);
           }
   return workOrderService.updateWorkOrder(id, workOrderDetails);
   }

 @PutMapping("/{id}/status")
  public WorkOrder updateWorkOrderStatus(
        @PathVariable Long id,
        @RequestParam String status) {

    return workOrderService.updateWorkOrderStatus(id, status);
    }

  @PostMapping("/{id}/assign")
  public WorkOrder assignWorkOrder(
        @PathVariable Long id,
        @RequestParam Long assignedTo) {
      return workOrderService.updateWorkOrderAssignment(id, assignedTo);
    }

  @GetMapping("/technician/{assignedTo}")
        public List<WorkOrder> getWorkOrderByTechnician(
        @PathVariable Long assignedTo,Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole().equalsIgnoreCase(RoleAccessConfig.TECHNICIAN)
            && !user.getId().equals(assignedTo)) {
        throw new AccessDeniedException(
                "You are not allowed to access another technician's work orders");
          }
        return workOrderService.getWorkOrdersByTechnician(assignedTo);
      } 
  
  @GetMapping("/customer/{customerId}")
  public List<WorkOrder> getWorkOrdersByCustomer(
        @PathVariable Long customerId, Authentication authentication) {
       User user = userRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
            if (user.getRole().equalsIgnoreCase(RoleAccessConfig.CUSTOMER)
                && !user.getId().equals(customerId)) {
            throw new AccessDeniedException(
                    "You are not allowed to access another customer's work orders");
            }
      return workOrderService.getWorkOrdersByCustomer(customerId);
  }

  @GetMapping("/{id}/overdue")
  public boolean isOverdue(@PathVariable Long id) {
    Optional<WorkOrder> workOrder = workOrderService.getWorkOrderById(id);
    if(workOrder.isEmpty()) {
      return false;
    }
    return workOrderService.isOverdue(workOrder.get());
  }
 @GetMapping("/{id}/sla-status")
 public String getSlaStatus(@PathVariable Long id) {
  Optional<WorkOrder> workOrder = workOrderService.getWorkOrderById(id);
  if(workOrder.isEmpty()) {
    return "WORK_ORDER_NOT_FOUND";
  }
  return workOrderService.getSlaStatus(workOrder.get());
 }
 
  @PostMapping("/customer/{customerId}/request")
    public WorkOrder createCustomerRequest(
        @PathVariable Long customerId,
        @Valid @RequestBody WorkOrderDTO workOrderDTO) {

    if (workOrderDTO.getSiteId() == null) {
        throw new RuntimeException("Site is required");
    }

    Site site = siteRepository.findById(workOrderDTO.getSiteId())
            .orElseThrow(() -> new RuntimeException("Site not found"));

    if (site.getCustomer() == null ||
        !site.getCustomer().getId().equals(customerId)) {
        throw new RuntimeException("Customer cannot create request for this site");
    }

    WorkOrder workOrder = new WorkOrder();

    workOrder.setTitle(workOrderDTO.getTitle());
    workOrder.setDescription(workOrderDTO.getDescription());
    workOrder.setPriority(workOrderDTO.getPriority());
    workOrder.setStatus("NEW");
    workOrder.setSite(site);

    return workOrderService.createWorkOrder(workOrder);
    }
 @DeleteMapping("/{id}")
 public void deleteWorkOrder(@PathVariable Long id) {
    workOrderService.deleteWorkOrder(id);
   }

}