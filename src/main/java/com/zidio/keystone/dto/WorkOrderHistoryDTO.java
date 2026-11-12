package com.zidio.keystone.dto;
import java.time.LocalDateTime;
public class WorkOrderHistoryDTO {
    private Long workOrderId;
    private String oldStatus;
    private String newStatus;
    private LocalDateTime changedAt;

    public WorkOrderHistoryDTO() {
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }
    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }
    public String getOldStatus() {
        return oldStatus;
    }
    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }
    public String getNewStatus() {
        return newStatus;
    }
    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }
    public LocalDateTime getChangedAt() {
        return changedAt;
    }
    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }
}
