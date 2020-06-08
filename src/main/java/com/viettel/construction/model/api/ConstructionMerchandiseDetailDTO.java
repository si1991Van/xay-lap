package com.viettel.construction.model.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ConstructionMerchandiseDetailDTO extends ConstructionMerchandiseDTO {

    private String constructionCode;
    private String constructionIsReturn;
    private String constructionStatus;
    private String workItemName;
    private Long countWorkItemComplete;
    private String statusComplete;


    public String getConstructionCode() {
        return constructionCode;
    }

    public void setConstructionCode(String constructionCode) {
        this.constructionCode = constructionCode;
    }

    public String getConstructionIsReturn() {
        return constructionIsReturn;
    }

    public void setConstructionIsReturn(String constructionIsReturn) {
        this.constructionIsReturn = constructionIsReturn;
    }

    public String getConstructionStatus() {
        return constructionStatus;
    }

    public void setConstructionStatus(String constructionStatus) {
        this.constructionStatus = constructionStatus;
    }

    public String getWorkItemName() {
        return workItemName;
    }

    public void setWorkItemName(String workItemName) {
        this.workItemName = workItemName;
    }

    public Long getCountWorkItemComplete() {
        return countWorkItemComplete;
    }

    public void setCountWorkItemComplete(Long countWorkItemComplete) {
        this.countWorkItemComplete = countWorkItemComplete;
    }

    public String getStatusComplete() {
        return statusComplete;
    }

    public void setStatusComplete(String statusComplete) {
        this.statusComplete = statusComplete;
    }


}
