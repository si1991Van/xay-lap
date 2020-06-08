package com.viettel.construction.model.api.issue;

import java.io.Serializable;

/**
 * Created by Manroid on 22/01/2018.
 */

public class IssueWorkItemDTO implements Serializable{
    private Long changeSysRoleCode;
    private Long issueId;
    private String content;
    private Long constructionId;
    private String code;
    private String oldStatus;
    private String status;//1: phản ảnh mở, 1 phản ánh đóng
    private Long workItemId;
    private String state;
    private String contentHanding;
    private Long currentHandingUserId;
    private long createdUserId;
    private String workItemName;
    private Long isProcessFeedBack;

    public IssueWorkItemDTO(Long changeSysRoleCode, Long issueId, String content, Long constructionId, String code, String status, String oldStatus, Long workItemId, String state, String contentHanding, Long currentHandingUserId) {
        this.changeSysRoleCode = changeSysRoleCode;
        this.issueId = issueId;
        this.content = content;
        this.constructionId = constructionId;
        this.code = code;
        this.status = status;
        this.oldStatus = oldStatus;
        this.workItemId = workItemId;
        this.state = state;
        this.contentHanding = contentHanding;
        this.currentHandingUserId = currentHandingUserId;
    }


    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public Long getChangeSysRoleCode() {
        return changeSysRoleCode;
    }

    public void setChangeSysRoleCode(Long changeSysRoleCode) {
        this.changeSysRoleCode = changeSysRoleCode;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(Long constructionId) {
        this.constructionId = constructionId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(Long workItemId) {
        this.workItemId = workItemId;
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

    public Long getCurrentHandingUserId() {
        return currentHandingUserId;
    }

    public void setCurrentHandingUserId(Long currentHandingUserId) {
        this.currentHandingUserId = currentHandingUserId;
    }

    public long getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(long createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getWorkItemName() {
        return workItemName;
    }

    public void setWorkItemName(String workItemName) {
        this.workItemName = workItemName;
    }

    public Long getIsProcessFeedBack() {
        return isProcessFeedBack;
    }

    public void setIsProcessFeedBack(Long isProcessFeedBack) {
        this.isProcessFeedBack = isProcessFeedBack;
    }
}
