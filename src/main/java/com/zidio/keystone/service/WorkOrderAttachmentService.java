package com.zidio.keystone.service;
import com.zidio.keystone.entity.WorkOrderAttachment;
import com.zidio.keystone.repository.WorkOrderAttachmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WorkOrderAttachmentService {

    private final WorkOrderAttachmentRepository attachmentRepository;

    public WorkOrderAttachmentService(
            WorkOrderAttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public WorkOrderAttachment addAttachment(WorkOrderAttachment attachment) {
        return attachmentRepository.save(attachment);
    }

    public List<WorkOrderAttachment> getAttachmentsByWorkOrder(Long workOrderId) {
        return attachmentRepository.findByWorkOrderId(workOrderId);
    }
}