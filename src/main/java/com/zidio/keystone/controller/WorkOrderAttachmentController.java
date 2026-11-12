package com.zidio.keystone.controller;
import com.zidio.keystone.entity.WorkOrderAttachment;
import com.zidio.keystone.service.WorkOrderAttachmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-orders")
public class WorkOrderAttachmentController {

    private final WorkOrderAttachmentService attachmentService;

    public WorkOrderAttachmentController(
            WorkOrderAttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/{id}/attachments")
    public WorkOrderAttachment addAttachment(
            @PathVariable Long id,
            @RequestBody WorkOrderAttachment attachment) {

        attachment.setWorkOrderId(id);
        return attachmentService.addAttachment(attachment);
    }

    @GetMapping("/{id}/attachments")
    public List<WorkOrderAttachment> getAttachments(
            @PathVariable Long id) {

        return attachmentService.getAttachmentsByWorkOrder(id);
    }
}