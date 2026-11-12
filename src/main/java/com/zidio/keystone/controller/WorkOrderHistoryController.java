package com.zidio.keystone.controller;
import com.zidio.keystone.entity.WorkOrder;
import com.zidio.keystone.entity.WorkOrderHistory;
import com.zidio.keystone.entity.User;
import com.zidio.keystone.repository.UserRepository;
import com.zidio.keystone.repository.WorkOrderRepository;
import com.zidio.keystone.service.WorkOrderHistoryService;
import com.zidio.keystone.config.RoleAccessConfig;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/work-orders")
public class WorkOrderHistoryController {

    private final WorkOrderHistoryService historyService;
    private final UserRepository userRepository;
    private final WorkOrderRepository workOrderRepository;
    public WorkOrderHistoryController(
            WorkOrderHistoryService historyService,UserRepository userRepository,
            WorkOrderRepository workOrderRepository) {
            this.historyService = historyService;
            this.userRepository = userRepository;
            this.workOrderRepository = workOrderRepository;
        }
    @GetMapping("/{workOrderId}/history")
        public List<WorkOrderHistory> getHistory(
            @PathVariable Long workOrderId,Authentication authentication) {
            User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
            if (user.getRole().equalsIgnoreCase(RoleAccessConfig.TECHNICIAN)) {
                WorkOrder workOrder = workOrderRepository.findById(workOrderId)
                    .orElseThrow(() -> new RuntimeException("Work order not found"));
               if (!user.getId().equals(workOrder.getAssignedTo())) {
                throw new AccessDeniedException(
                        "You are not allowed to access this work order history");
              }
            }
            return historyService.getHistoryByWorkOrderId(workOrderId);
        }
}