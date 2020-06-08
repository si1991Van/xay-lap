package com.viettel.construction.model.api;

import java.util.List;

public class ConstructionMerchandiseDTOResponse {
    private ResultInfo resultInfo;
    private SysUserRequest sysUser;
    private List<ConstructionMerchandiseDetailDTO> listConstructionMerchandisePagesDTO;
    private List<ConstructionMerchandiseDetailDTO> listConstructionMerchandiseWorkItemPagesDTO;
    private List<ConstructionMerchandiseDetailVTDTO> listConstructionMerchandiseVT;
    private List<ConstructionMerchandiseDetailVTDTO> listConstructionMerchandiseTB;
    private List<ConstructionImageInfo> listImage;
    private Integer numberNoConstructionReturn;
    private Integer numberConstructionReturn;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public SysUserRequest getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUserRequest sysUser) {
        this.sysUser = sysUser;
    }

    public List<ConstructionMerchandiseDetailDTO> getListConstructionMerchandisePagesDTO() {
        return listConstructionMerchandisePagesDTO;
    }

    public void setListConstructionMerchandisePagesDTO(List<ConstructionMerchandiseDetailDTO> listConstructionMerchandisePagesDTO) {
        this.listConstructionMerchandisePagesDTO = listConstructionMerchandisePagesDTO;
    }

    public List<ConstructionMerchandiseDetailDTO> getListConstructionMerchandiseWorkItemPagesDTO() {
        return listConstructionMerchandiseWorkItemPagesDTO;
    }

    public void setListConstructionMerchandiseWorkItemPagesDTO(List<ConstructionMerchandiseDetailDTO> listConstructionMerchandiseWorkItemPagesDTO) {
        this.listConstructionMerchandiseWorkItemPagesDTO = listConstructionMerchandiseWorkItemPagesDTO;
    }

    public List<ConstructionMerchandiseDetailVTDTO> getListConstructionMerchandiseVT() {
        return listConstructionMerchandiseVT;
    }

    public void setListConstructionMerchandiseVT(List<ConstructionMerchandiseDetailVTDTO> listConstructionMerchandiseVT) {
        this.listConstructionMerchandiseVT = listConstructionMerchandiseVT;
    }

    public List<ConstructionMerchandiseDetailVTDTO> getListConstructionMerchandiseTB() {
        return listConstructionMerchandiseTB;
    }

    public void setListConstructionMerchandiseTB(List<ConstructionMerchandiseDetailVTDTO> listConstructionMerchandiseTB) {
        this.listConstructionMerchandiseTB = listConstructionMerchandiseTB;
    }

    public List<ConstructionImageInfo> getListImage() {
        return listImage;
    }

    public void setListImage(List<ConstructionImageInfo> listImage) {
        this.listImage = listImage;
    }

    public Integer getNumberNoConstructionReturn() {
        return numberNoConstructionReturn;
    }

    public void setNumberNoConstructionReturn(Integer numberNoConstructionReturn) {
        this.numberNoConstructionReturn = numberNoConstructionReturn;
    }

    public Integer getNumberConstructionReturn() {
        return numberConstructionReturn;
    }

    public void setNumberConstructionReturn(Integer numberConstructionReturn) {
        this.numberConstructionReturn = numberConstructionReturn;
    }
}
