package com.zidio.keystone.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity 
@Table(name = "notifications")
public class Notification {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "work_order_id")
    private Long workOrderId;
    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "is_read")
    private Boolean isRead;
    public  Notification() {

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
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public Boolean getIsRead() {
        return isRead;
    }
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}