package com.zidio.keystone.service;
import com.zidio.keystone.entity.WorkOrderTime;
import com.zidio.keystone.repository.WorkOrderTimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WorkOrderTimeService {
    private final WorkOrderTimeRepository workOrderTimeRepository;
    public WorkOrderTimeService(WorkOrderTimeRepository workOrderTimeRepository) {
        this.workOrderTimeRepository = workOrderTimeRepository;
    }
    public WorkOrderTime addTime(WorkOrderTime time) {
        return workOrderTimeRepository.save(time);
    }
    public List<WorkOrderTime> getTimeByWorkOrder(Long workOrderId) {
        return workOrderTimeRepository.findAll()
                .stream()
                .filter(time -> time.getWorkOrderId().equals(workOrderId))
                .toList();
    }
}