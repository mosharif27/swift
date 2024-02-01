package com.SwiftCaseChallenge.Swift;

import java.util.Date;

public class Task {
    private int taskId;
    private String taskName;
    private double cost;
    private boolean cancelled;
    private Date startDate;
    private Date endDate;

    // Add getters and setters for the missing methods

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public long getStartDateTimestamp() {
        return startDate.toInstant().getEpochSecond();
    }

    public long getEndDateTimestamp() {
        return endDate.toInstant().getEpochSecond();
    }
}
