package com.viettel.construction.model.api;

public class ConstructionTaskDailyDTO {
    private long sysGroupId;
    private Double amount;
    private long type;
    private long createdUserId;
    private long createdGroupId;
    private long constructionTaskId;
    private Double quantity;
    private long workItemId;
    private long catTaskId;
    private String startingDateTK;
    private String path;
    private Double currentAmount;
    private Double totalAmount;

    public Double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ConstructionTaskDailyDTO(double currentAmount,double totalAmount,String path,String startingDateTK, long sysGroupId, Double amount, long type, long createdUserId, long createdGroupId, long constructionTaskId, Double quantity, long workItemId, long catTaskId) {
        this.sysGroupId = sysGroupId;
        this.amount = amount;
        this.type = type;
        this.createdUserId = createdUserId;
        this.createdGroupId = createdGroupId;
        this.constructionTaskId = constructionTaskId;
        this.quantity = quantity;
        this.workItemId = workItemId;
        this.catTaskId = catTaskId;
        this.path = path;
        this.startingDateTK = startingDateTK;
        this.currentAmount = currentAmount;
        this.totalAmount = totalAmount;
    }

    public String getStartingDateTK() {
        return startingDateTK;
    }

    public void setStartingDateTK(String startingDateTK) {
        this.startingDateTK = startingDateTK;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ConstructionTaskDailyDTO() {
    }

    public long getSysGroupId() {
        return sysGroupId;
    }

    public void setSysGroupId(long sysGroupId) {
        this.sysGroupId = sysGroupId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public long getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(long createdUserId) {
        this.createdUserId = createdUserId;
    }

    public long getCreatedGroupId() {
        return createdGroupId;
    }

    public void setCreatedGroupId(long createdGroupId) {
        this.createdGroupId = createdGroupId;
    }

    public long getConstructionTaskId() {
        return constructionTaskId;
    }

    public void setConstructionTaskId(long constructionTaskId) {
        this.constructionTaskId = constructionTaskId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public long getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(long workItemId) {
        this.workItemId = workItemId;
    }

    public long getCatTaskId() {
        return catTaskId;
    }

    public void setCatTaskId(long catTaskId) {
        this.catTaskId = catTaskId;
    }
}
