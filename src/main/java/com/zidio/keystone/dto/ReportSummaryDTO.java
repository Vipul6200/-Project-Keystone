package com.zidio.keystone.dto;

public class ReportSummaryDTO {
    private java.util.Map<Long, Long> technicianBreakdown;
    private long totalWorkOrders;
    private long newCount;
    private long assignedCount;
    private long inProgressCount;
    private long completedCount;
    private long overdueCount;
    private long onTimeCount;
    private long atRiskCount;
    private long breachedCount;

    public ReportSummaryDTO() {
    }
    public long getTotalWorkOrders() {
        return totalWorkOrders;
    }
    public void setTotalWorkOrders(long totalWorkOrders) {
        this.totalWorkOrders = totalWorkOrders;
    }
    public java.util.Map<Long, Long> getTechnicianBreakdown() {
        return technicianBreakdown;
    }
    public void setTechnicianBreakdown(java.util.Map<Long, Long> technicianBreakdown) {
        this.technicianBreakdown = technicianBreakdown;
    }
    public long getNewCount() {
        return newCount;
    }
    public void setNewCount(long newCount) {
        this.newCount = newCount;
    }
    public long getAssignedCount() {
        return assignedCount;
    }
    public void setAssignedCount(long assignedCount) {
        this.assignedCount = assignedCount;
    }
    public long getInProgressCount() {
        return inProgressCount;
    }
    public void setInProgressCount(long inProgressCount) {
        this.inProgressCount = inProgressCount;
    }
    public long getCompletedCount() {
        return completedCount;
    }
    public void setCompletedCount(long completedCount) {
        this.completedCount = completedCount;
    }
    public long getOverdueCount() {
        return overdueCount;
    }
    public void setOverdueCount(long overdueCount) {
        this.overdueCount = overdueCount;
    }
    public long getOnTimeCount() {
        return onTimeCount;
    }
    public void setOnTimeCount(long onTimeCount) {
        this.onTimeCount = onTimeCount;
    }
    public long getAtRiskCount() {
        return atRiskCount;
    }
    public void setAtRiskCount(long atRiskCount) {
        this.atRiskCount = atRiskCount;
    }
    public long getBreachedCount() {
        return breachedCount;
    }
    public void setBreachedCount(long breachedCount) {
        this.breachedCount = breachedCount;
    }

}
