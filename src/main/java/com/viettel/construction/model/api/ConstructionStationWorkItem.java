package com.viettel.construction.model.api;

import java.io.Serializable;

/**
 * Created by manro on 3/9/2018.
 */

public class ConstructionStationWorkItem implements Serializable{

    private String name;

    private long constructionId;
    private String constructionCode;

    private String workItemCode;
    private long workItemId;

    private long catTaskId;
    private String catTaskCode;

    private String address;

    private String status;
    private String is_building_permit;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIs_building_permit() {
        return is_building_permit;
    }

    public void setIs_building_permit(String is_building_permit) {
        this.is_building_permit = is_building_permit;
    }

    public ConstructionStationWorkItem() {
    }

    public ConstructionStationWorkItem(long constructionId) {
        this.constructionId = constructionId;
    }

    public ConstructionStationWorkItem(long constructionId, long workItemId) {
        this.constructionId = constructionId;
        this.workItemId = workItemId;
    }

    public ConstructionStationWorkItem(String name, long constructionId, String address, long workItemId, long catTaskId) {
        this.name = name;
        this.constructionId = constructionId;
        this.address = address;
        this.workItemId = workItemId;
        this.catTaskId = catTaskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(long constructionId) {
        this.constructionId = constructionId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getCatWorkItemTypeId() {
        return workItemId;
    }

    public void setCatWorkItemTypeId(long catWorkItemTypeId) {
        this.workItemId = catWorkItemTypeId;
    }

    public long getCatTaskId() {
        return catTaskId;
    }

    public void setCatTaskId(long catTaskId) {
        this.catTaskId = catTaskId;
    }

    public String getConstructionCode() {
        return constructionCode;
    }

    public void setConstructionCode(String constructionCode) {
        this.constructionCode = constructionCode;
    }

    public String getWorkItemCode() {
        return workItemCode;
    }

    public void setWorkItemCode(String workItemCode) {
        this.workItemCode = workItemCode;
    }

    public long getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(long workItemId) {
        this.workItemId = workItemId;
    }

    public String getCatTaskCode() {
        return catTaskCode;
    }

    public void setCatTaskCode(String catTaskCode) {
        this.catTaskCode = catTaskCode;
    }
}
