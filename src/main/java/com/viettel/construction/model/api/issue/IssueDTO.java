package com.viettel.construction.model.api.issue;

public class IssueDTO {
    private long changeSysRoleCode;
    private long issueId;
    private String content;
    private long constructionId;
    private long workItemId;
    private String status;
    private String state;
    private String contentHanding;
    private long currentHandingUserId;
    private String createdDate;
    private long createdUserId;
    private long createdGroupId;
    private String updatedDate;
    private long updatedUserId;

    public long getChangeSysRoleCode() {
        return changeSysRoleCode;
    }

    public void setChangeSysRoleCode(long changeSysRoleCode) {
        this.changeSysRoleCode = changeSysRoleCode;
    }

    public long getIssueId() {
        return issueId;
    }

    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(long constructionId) {
        this.constructionId = constructionId;
    }

    public long getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(long workItemId) {
        this.workItemId = workItemId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContentHanding() {
        return contentHanding;
    }

    public void setContentHanding(String contentHanding) {
        this.contentHanding = contentHanding;
    }

    public long getCurrentHandingUserId() {
        return currentHandingUserId;
    }

    public void setCurrentHandingUserId(long currentHandingUserId) {
        this.currentHandingUserId = currentHandingUserId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
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

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public long getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(long updatedUserId) {
        this.updatedUserId = updatedUserId;
    }
}
