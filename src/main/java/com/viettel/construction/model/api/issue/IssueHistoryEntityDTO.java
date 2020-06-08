package com.viettel.construction.model.api.issue;

public class IssueHistoryEntityDTO {
    private String issueHistoryId;
    private String oldValue;
    private String newValue;
    private int type;
    private String createdDate;
    private long createdUserId;
    private long issueId;

    public String getIssueHistoryId() {
        return issueHistoryId;
    }

    public void setIssueHistoryId(String issueHistoryId) {
        this.issueHistoryId = issueHistoryId;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public long getIssueId() {
        return issueId;
    }

    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }
}
