package com.zidio.keystone.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_order_history")
public class WorkOrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "work_order_id")
    private Long workOrderId;
    @Column(name = "old_status")
    private String oldStatus;
    @Column(name = "new_status")
    private String newStatus;
    @Column(name = "changed_at")
    private LocalDateTime changedAt;

    public WorkOrderHistory() {
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
