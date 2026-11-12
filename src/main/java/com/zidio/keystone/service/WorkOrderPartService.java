package com.zidio.keystone.service;
import com.zidio.keystone.entity.WorkOrderPart;
import com.zidio.keystone.repository.WorkOrderPartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class WorkOrderPartService {
    private final WorkOrderPartRepository workOrderPartRepository;
    private final PartStockService partStockService;
    public WorkOrderPartService(WorkOrderPartRepository workOrderPartRepository, PartStockService partStockService) {
        this.workOrderPartRepository = workOrderPartRepository;
        this.partStockService = partStockService;
    }
@Transactional 
    public WorkOrderPart addPart(WorkOrderPart part) {
        boolean stockAvailable = partStockService.reduceStock( part.getPartName(), part.getQuantity());
        if (!stockAvailable) {
            return null;
        }
        return workOrderPartRepository.save(part);
    }
    public List<WorkOrderPart> getPartsByWorkOrder(Long workOrderId) {
        return workOrderPartRepository.findAll()
                .stream()
                .filter(part -> part.getWorkOrderId().equals(workOrderId))
                .toList();
    }
}
