package com.zidio.keystone.controller;
import com.zidio.keystone.dto.WorkOrderTimeDTO;
import com.zidio.keystone.entity.WorkOrderTime;
import com.zidio.keystone.service.WorkOrderTimeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/work-orders")
public class WorkOrderTimeController {
    private final WorkOrderTimeService workOrderTimeService;
    public WorkOrderTimeController(WorkOrderTimeService workOrderTimeService) {
        this.workOrderTimeService = workOrderTimeService;
    }
    @PostMapping("/{id}/time")
    public WorkOrderTime addTime(
            @PathVariable Long id,
            @Valid @RequestBody WorkOrderTimeDTO timeDTO) {

            WorkOrderTime time = new WorkOrderTime();

            time.setWorkOrderId(id);
            time.setMinutes(timeDTO.getMinutes());
            time.setNote(timeDTO.getNote());

            return workOrderTimeService.addTime(time);
            }

    @GetMapping("/{id}/time")
    public List<WorkOrderTime> getTime(
            @PathVariable Long id) {

             return workOrderTimeService.getTimeByWorkOrder(id);
             
            }
}
