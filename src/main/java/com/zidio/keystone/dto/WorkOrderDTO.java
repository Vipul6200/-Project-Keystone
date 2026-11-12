package com.zidio.keystone.dto;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
public class WorkOrderDTO {

    @NotBlank(message = "Title is mandatory")
    private String title;
    private String description;

    @NotBlank(message = "Status is required")
    private String status;
    @NotBlank(message = "Priority is required")
    private String priority;
    private Long assignedTo;
    private Long siteId;
    private LocalDateTime dueDate;
    
    public WorkOrderDTO() {
        
    }
    public WorkOrderDTO(String title, String description, String status, String priority) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
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
    public Long getSiteId() {
        return siteId;
    }
    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    
}
