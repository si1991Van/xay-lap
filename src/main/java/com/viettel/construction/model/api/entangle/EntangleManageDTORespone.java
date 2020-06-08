package com.viettel.construction.model.api.entangle;

import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.api.ResultInfo;

import java.util.List;

public class EntangleManageDTORespone {
    private ResultInfo resultInfo;
    private List<EntangleManageDTO> listEntangleManageDTO;
    private List<ConstructionImageInfo> listImg;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public List<EntangleManageDTO> getListEntangleManageDTO() {
        return listEntangleManageDTO;
    }

    public void setListEntangleManageDTO(List<EntangleManageDTO> listEntangleManageDTO) {
        this.listEntangleManageDTO = listEntangleManageDTO;
    }

    public List<ConstructionImageInfo> getListImg() {
        return listImg;
    }

    public void setListImg(List<ConstructionImageInfo> listImg) {
        this.listImg = listImg;
    }
}
