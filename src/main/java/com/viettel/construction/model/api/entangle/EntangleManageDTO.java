package com.viettel.construction.model.api.entangle;

import com.viettel.construction.model.api.ConstructionImageInfo;

import java.util.List;

public class EntangleManageDTO extends ObstructedDTO {
    private String consCode;
    private Long constructionId;
    private String consName;
    private String obstructedState;
    private String obstructedContent;
    private String workItemName;
    private Long workItemId;
    private String consStatus;
    private List<ConstructionImageInfo> listImage;

    public Long getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(Long workItemId) {
        this.workItemId = workItemId;
    }

    public String getWorkItemName() {
        return workItemName;
    }

    public void setWorkItemName(String workItemName) {
        this.workItemName = workItemName;
    }

    public String getConsCode() {
        return consCode;
    }

    public void setConsCode(String consCode) {
        this.consCode = consCode;
    }

    public Long getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(Long constructionId) {
        this.constructionId = constructionId;
    }

    public String getConsName() {
        return consName;
    }

    public void setConsName(String consName) {
        this.consName = consName;
    }

    public String getObstructedState() {
        return obstructedState;
    }

    public void setObstructedState(String obstructedState) {
        this.obstructedState = obstructedState;
    }

    public String getObstructedContent() {
        return obstructedContent;
    }

    public void setObstructedContent(String obstructedContent) {
        this.obstructedContent = obstructedContent;
    }

    public String getConsStatus() {
        return consStatus;
    }

    public void setConsStatus(String consStatus) {
        this.consStatus = consStatus;
    }

    public List<ConstructionImageInfo> getListImage() {
        return listImage;
    }

    public void setListImage(List<ConstructionImageInfo> listImage) {
        this.listImage = listImage;
    }
}
