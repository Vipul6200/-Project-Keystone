package com.zidio.keystone.repository;
import com.zidio.keystone.entity.WorkOrderAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkOrderAttachmentRepository
        extends JpaRepository<WorkOrderAttachment, Long> {

    List<WorkOrderAttachment> findByWorkOrderId(Long workOrderId);
}