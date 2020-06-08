package com.viettel.construction.model.api;


import java.util.Date;

public class WorkItemDetail {
    private long workItemId;
    private long contrucstionId;
    private long catWorkItemTypeId;
    private String code;
    private String name;
    private String isInternal;
    private long constructorId;
    private long supervisorId;
    private Date startingDate;
    private Date completeDate;
    private String status;
    private double quantity;
    private double approveQuantity;
    private String approveState;
    private Date approveDate;
    private long approveUserId;
    private String approveDescription;
    private Date createdDate;
    private long createdUserId;
    private long createdGroupId;
    private Date updatedDate;
    private long updatedUserId;
    private long updateGroupId;
    private String constructionName;
    private String constructorName1;
    private String constructorName2;

    public WorkItemDetail(long workItemId, long contrucstionId, long catWorkItemTypeId, String code, String name, String isInternal, long constructorId, long supervisorId, Date startingDate, Date completeDate, String status, double quantity, double approveQuantity, String approveState, Date approveDate, long approveUserId, String approveDescription, Date createdDate, long createdUserId, long createdGroupId, Date updatedDate, long updatedUserId, long updateGroupId, String constructionName, String constructorName1, String constructorName2) {
        this.workItemId = workItemId;
        this.contrucstionId = contrucstionId;
        this.catWorkItemTypeId = catWorkItemTypeId;
        this.code = code;
        this.name = name;
        this.isInternal = isInternal;
        this.constructorId = constructorId;
        this.supervisorId = supervisorId;
        this.startingDate = startingDate;
        this.completeDate = completeDate;
        this.status = status;
        this.quantity = quantity;
        this.approveQuantity = approveQuantity;
        this.approveState = approveState;
        this.approveDate = approveDate;
        this.approveUserId = approveUserId;
        this.approveDescription = approveDescription;
        this.createdDate = createdDate;
        this.createdUserId = createdUserId;
        this.createdGroupId = createdGroupId;
        this.updatedDate = updatedDate;
        this.updatedUserId = updatedUserId;
        this.updateGroupId = updateGroupId;
        this.constructionName = constructionName;
        this.constructorName1 = constructorName1;
        this.constructorName2 = constructorName2;
    }

    public long getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(long workItemId) {
        this.workItemId = workItemId;
    }

    public long getContrucstionId() {
        return contrucstionId;
    }

    public void setContrucstionId(long contrucstionId) {
        this.contrucstionId = contrucstionId;
    }

    public long getCatWorkItemTypeId() {
        return catWorkItemTypeId;
    }

    public void setCatWorkItemTypeId(long catWorkItemTypeId) {
        this.catWorkItemTypeId = catWorkItemTypeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsInternal() {
        return isInternal;
    }

    public void setIsInternal(String isInternal) {
        this.isInternal = isInternal;
    }

    public long getConstructorId() {
        return constructorId;
    }

    public void setConstructorId(long constructorId) {
        this.constructorId = constructorId;
    }

    public long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(long supervisorId) {
        this.supervisorId = supervisorId;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getApproveQuantity() {
        return approveQuantity;
    }

    public void setApproveQuantity(double approveQuantity) {
        this.approveQuantity = approveQuantity;
    }

    public String getApproveState() {
        return approveState;
    }

    public void setApproveState(String approveState) {
        this.approveState = approveState;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public long getApproveUserId() {
        return approveUserId;
    }

    public void setApproveUserId(long approveUserId) {
        this.approveUserId = approveUserId;
    }

    public String getApproveDescription() {
        return approveDescription;
    }

    public void setApproveDescription(String approveDescription) {
        this.approveDescription = approveDescription;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public long getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(long updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public long getUpdateGroupId() {
        return updateGroupId;
    }

    public void setUpdateGroupId(long updateGroupId) {
        this.updateGroupId = updateGroupId;
    }

    public String getConstructionName() {
        return constructionName;
    }

    public void setConstructionName(String constructionName) {
        this.constructionName = constructionName;
    }

    public String getConstructorName1() {
        return constructorName1;
    }

    public void setConstructorName1(String constructorName1) {
        this.constructorName1 = constructorName1;
    }

    public String getConstructorName2() {
        return constructorName2;
    }

    public void setConstructorName2(String constructorName2) {
        this.constructorName2 = constructorName2;
    }
}
