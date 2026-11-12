package com.zidio.keystone.service;
import com.zidio.keystone.entity.WorkOrderHistory;
import com.zidio.keystone.repository.WorkOrderHistoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WorkOrderHistoryService {

    private final WorkOrderHistoryRepository historyRepository;
    public WorkOrderHistoryService(WorkOrderHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }
    public WorkOrderHistory saveHistory(WorkOrderHistory history) {
        return historyRepository.save(history);
    }
    public List<WorkOrderHistory> getHistoryByWorkOrderId(Long workOrderId) {
        return historyRepository.findByWorkOrderId(workOrderId);
    }
}
