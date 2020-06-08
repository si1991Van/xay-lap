package com.viettel.construction.model.api;

import java.io.Serializable;

public class ConstructionAcceptanceCertDTO implements Serializable {

    private String importer;
    private String isActive;
    private Long updateGroupId;
    private Long updateUserId;
    private String updatedDate;
    private Long createdGroupId;
    private Long createdUserId;
    private String createdDate;
    private String description;
    private String acceptanceResult;
    private String completeDate;
    private String startingDate;
    private Long constructionId;
    private Long constructionAcceptCerId;

    public String getImporter() {
        return importer;
    }

    public void setImporter(String importer) {
        this.importer = importer;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Long getUpdateGroupId() {
        return updateGroupId;
    }

    public void setUpdateGroupId(Long updateGroupId) {
        this.updateGroupId = updateGroupId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getCreatedGroupId() {
        return createdGroupId;
    }

    public void setCreatedGroupId(Long createdGroupId) {
        this.createdGroupId = createdGroupId;
    }

    public Long getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Long createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAcceptanceResult() {
        return acceptanceResult;
    }

    public void setAcceptanceResult(String acceptanceResult) {
        this.acceptanceResult = acceptanceResult;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public Long getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(Long constructionId) {
        this.constructionId = constructionId;
    }

    public Long getConstructionAcceptCerId() {
        return constructionAcceptCerId;
    }

    public void setConstructionAcceptCerId(Long constructionAcceptCerId) {
        this.constructionAcceptCerId = constructionAcceptCerId;
    }
}
