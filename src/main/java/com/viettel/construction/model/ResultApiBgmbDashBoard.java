package com.viettel.construction.model;

import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.api.CountConstructionTaskDTO;
import com.viettel.construction.model.api.ResultInfo;

import java.util.List;

public class ResultApiBgmbDashBoard {

    private ResultInfo resultInfo;
    private int  totalRecordNotReceived;
    private int  totalRecordReceived;
    private String houseType;
    private String groundingType;

    public ResultApiBgmbDashBoard(ResultInfo resultInfo, int totalRecordNotReceived, int totalRecordReceived, String houseType, String groundingType) {
        this.resultInfo = resultInfo;
        this.totalRecordNotReceived = totalRecordNotReceived;
        this.totalRecordReceived = totalRecordReceived;
        this.houseType = houseType;
        this.groundingType = groundingType;
    }

    public ResultApiBgmbDashBoard() {
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public int getTotalRecordNotReceived() {
        return totalRecordNotReceived;
    }

    public void setTotalRecordNotReceived(int totalRecordNotReceived) {
        this.totalRecordNotReceived = totalRecordNotReceived;
    }

    public int getTotalRecordReceived() {
        return totalRecordReceived;
    }

    public void setTotalRecordReceived(int totalRecordReceived) {
        this.totalRecordReceived = totalRecordReceived;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getGroundingType() {
        return groundingType;
    }

    public void setGroundingType(String groundingType) {
        this.groundingType = groundingType;
    }
}
