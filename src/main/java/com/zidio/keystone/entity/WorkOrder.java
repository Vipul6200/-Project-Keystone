package com.zidio.keystone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import  java.time.LocalDateTime;
@Entity
@Table(name = "work_order")
public class WorkOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status;
    private String priority;
    private Long assignedTo;
    private LocalDateTime dueDate;

    @ManyToOne 
    @JoinColumn(name = "site_id")
    private Site site;
    public WorkOrder() {
    }

    public WorkOrder(String title, String description, String status, String priority) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
    }
    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public Long getAssignedTo() {
        return assignedTo;
    }
    public void setAssignedTo(Long assignedTo) {
        this.assignedTo = assignedTo;
    }
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    public Site getSite() {
        return site;
    }
    public void setSite(Site site) {
        this.site = site;
    }
}