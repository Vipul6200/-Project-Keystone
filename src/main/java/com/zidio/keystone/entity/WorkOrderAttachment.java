package com.zidio.keystone.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "work_order_attachments")
public class WorkOrderAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "work_order_id")
    private Long workOrderId;

    private String fileName;

    private String fileUrl;

    public WorkOrderAttachment() {
    }

    public Long getId() {
        return id;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}