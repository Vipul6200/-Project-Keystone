package com.zidio.keystone.controller;
import com.zidio.keystone.entity.WorkOrderPart;
import com.zidio.keystone.dto.WorkOrderPartDTO;
import com.zidio.keystone.service.WorkOrderPartService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
@RestController
@RequestMapping("/api/work-orders")
public class WorkOrderPartController {
    private final WorkOrderPartService workOrderPartService;
    public WorkOrderPartController(WorkOrderPartService workOrderPartService) {
        this.workOrderPartService = workOrderPartService;
    }
    @PostMapping("/{id}/parts")
    public WorkOrderPart addPart(
            @PathVariable Long id,
            @Valid @RequestBody WorkOrderPartDTO partDTO) {
                WorkOrderPart part = new WorkOrderPart();
                part.setWorkOrderId(id);
                part.setPartName(partDTO.getPartName());
                part.setQuantity(partDTO.getQuantity());

            return workOrderPartService.addPart(part);
    }
    @GetMapping("/{id}/parts")
    public List<WorkOrderPart> getParts(
            @PathVariable Long id) {

        return workOrderPartService.getPartsByWorkOrder(id);
    }
}
