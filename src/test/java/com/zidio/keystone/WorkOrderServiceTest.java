package com.zidio.keystone;
import com.zidio.keystone.entity.WorkOrder;
import com.zidio.keystone.repository.WorkOrderHistoryRepository;
import com.zidio.keystone.repository.WorkOrderRepository;
import com.zidio.keystone.service.NotificationService;
import com.zidio.keystone.service.WorkOrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkOrderServiceTest {

    @Mock
    private WorkOrderRepository workOrderRepository;

    @Mock
    private WorkOrderHistoryRepository historyRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private WorkOrderService workOrderService;

    @Test
    void calculateDueDateForHighPriority() {

        WorkOrder workOrder = new WorkOrder();
        workOrder.setPriority("HIGH");
        when(workOrderRepository.save(any(WorkOrder.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

        WorkOrder savedWorkOrder = workOrderService.createWorkOrder(workOrder);

        assertNotNull(savedWorkOrder.getDueDate());
    }
}