package com.zidio.keystone.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_order_time")
public class WorkOrderTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "work_order_id")
    private Long workOrderId;
    private Integer minutes;
    private String note;

    @Column(name = "logged_at")
    private LocalDateTime loggedAt;
    public WorkOrderTime() {
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
    public Integer getMinutes() {
        return minutes;
    }
    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public LocalDateTime getLoggedAt() {
        return loggedAt;
    }
    public void setLoggedAt(LocalDateTime loggedAt) {
        this.loggedAt = loggedAt;
    }
}