package com.viettel.construction.model.api.entangle;

import java.io.Serializable;

public class ObstructedDTO implements Serializable{
    private Long obstructedId;
    private String closedDate;
    private String code;
    private String createdDate;
    private Long createdUserId;
    private Long createdGroupId;
    private String updatedDate;
    private Long updatedUserId;
    private Long updatedGroupId;

    public Long getObstructedId() {
        return obstructedId;
    }

    public void setObstructedId(Long obstructedId) {
        this.obstructedId = obstructedId;
    }

    public String getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(String closedDate) {
        this.closedDate = closedDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Long createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Long getCreatedGroupId() {
        return createdGroupId;
    }

    public void setCreatedGroupId(Long createdGroupId) {
        this.createdGroupId = createdGroupId;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(Long updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public Long getUpdatedGroupId() {
        return updatedGroupId;
    }

    public void setUpdatedGroupId(Long updatedGroupId) {
        this.updatedGroupId = updatedGroupId;
    }
}
