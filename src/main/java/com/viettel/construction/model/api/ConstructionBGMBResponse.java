package com.viettel.construction.model.api;

import com.viettel.construction.model.ItemSpinnerBgmb;

import java.util.List;

public class ConstructionBGMBResponse {

    private ResultInfo resultInfo;
    private int  totalRecordNotReceived;
    private int  totalRecordReceived;
    private List<ConstructionBGMBDTO> assignHandoverDTO;
    private List<ConstructionImageInfo> constructionImageInfo;
    private List<ItemSpinnerBgmb> groundingType;
    private List<ItemSpinnerBgmb> houseType;

    public ConstructionBGMBResponse(ResultInfo resultInfo, int totalRecordNotReceived, int totalRecordReceived, List<ConstructionBGMBDTO> assignHandoverDTO, List<ConstructionImageInfo> constructionImageInfo, List<ItemSpinnerBgmb> groundingType, List<ItemSpinnerBgmb> houseType) {
        this.resultInfo = resultInfo;
        this.totalRecordNotReceived = totalRecordNotReceived;
        this.totalRecordReceived = totalRecordReceived;
        this.assignHandoverDTO = assignHandoverDTO;
        this.constructionImageInfo = constructionImageInfo;
        this.groundingType = groundingType;
        this.houseType = houseType;
    }

    public ConstructionBGMBResponse() {
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

    public List<ConstructionBGMBDTO> getAssignHandoverDTO() {
        return assignHandoverDTO;
    }

    public void setAssignHandoverDTO(List<ConstructionBGMBDTO> assignHandoverDTO) {
        this.assignHandoverDTO = assignHandoverDTO;
    }

    public List<ConstructionImageInfo> getConstructionImageInfo() {
        return constructionImageInfo;
    }

    public void setConstructionImageInfo(List<ConstructionImageInfo> constructionImageInfo) {
        this.constructionImageInfo = constructionImageInfo;
    }

    public List<ItemSpinnerBgmb> getGroundingType() {
        return groundingType;
    }

    public void setGroundingType(List<ItemSpinnerBgmb> groundingType) {
        this.groundingType = groundingType;
    }

    public List<ItemSpinnerBgmb> getHouseType() {
        return houseType;
    }

    public void setHouseType(List<ItemSpinnerBgmb> houseType) {
        this.houseType = houseType;
    }
}
