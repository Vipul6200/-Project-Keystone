package com.zidio.keystone.service;
import com.zidio.keystone.dto.ReportSummaryDTO;
import com.zidio.keystone.entity.WorkOrder;
import com.zidio.keystone.repository.WorkOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
@Service
public class ReportService {
    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderService workOrderService;
    public ReportService(
            WorkOrderRepository workOrderRepository,
            WorkOrderService workOrderService) {

        this.workOrderRepository = workOrderRepository;
        this.workOrderService = workOrderService;
    }
    public ReportSummaryDTO getSummary() {

        List<WorkOrder> workOrders = workOrderRepository.findAll();
        ReportSummaryDTO summary = new ReportSummaryDTO();
        summary.setTotalWorkOrders(workOrders.size());

        long newCount = 0;
        long assignedCount = 0;
        long inProgressCount = 0;
        long completedCount = 0;

        long overdueCount = 0;
        long onTimeCount = 0;
        long atRiskCount = 0;
        long breachedCount = 0;
        Map<Long, Long> technicianBreakdown = new HashMap<>();

        for (WorkOrder workOrder : workOrders) {
            if(workOrder.getAssignedTo() != null) {
                technicianBreakdown.merge(
                    workOrder.getAssignedTo(), 1L, (oldValue, newValue) -> oldValue + newValue
                );
            }

            String status = workOrder.getStatus();
            if ("NEW".equalsIgnoreCase(status)) {
                newCount++;
            } else if ("ASSIGNED".equalsIgnoreCase(status)) {
                assignedCount++;
            } else if ("IN_PROGRESS".equalsIgnoreCase(status)) {
                inProgressCount++;
            } else if ("COMPLETED".equalsIgnoreCase(status)) {
                completedCount++;
            }
            if (workOrderService.isOverdue(workOrder)) {
                overdueCount++;
            }

            String slaStatus = workOrderService.getSlaStatus(workOrder);
            if ("ON_TIME".equals(slaStatus)) {
                onTimeCount++;
            } else if ("AT_RISK".equals(slaStatus)) {
                atRiskCount++;
            } else if ("BREACHED".equals(slaStatus)) {
                breachedCount++;
            }
        }
        summary.setNewCount(newCount);
        summary.setAssignedCount(assignedCount);
        summary.setInProgressCount(inProgressCount);
        summary.setCompletedCount(completedCount);

        summary.setOverdueCount(overdueCount);
        summary.setOnTimeCount(onTimeCount);
        summary.setAtRiskCount(atRiskCount);
        summary.setBreachedCount(breachedCount);
        summary.setTechnicianBreakdown(technicianBreakdown);
        return summary;
    }
}
