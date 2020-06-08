package com.viettel.construction.model.api;

import java.util.List;

public class ConstructionMerchandiseDTORequest {

    private SysUserRequest sysUserRequest;
    private ConstructionMerchandiseDTO constructionMerchandiseDTO;
    private ConstructionMerchandiseDetailDTO constructionMerchandiseDetailDTO;

    private List<ConstructionImageInfo> listImage;

    private List<ConstructionMerchandiseDetailVTDTO> listDSVT;
    private List<ConstructionMerchandiseDetailVTDTO> listDSTB;

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }

    public ConstructionMerchandiseDTO getConstructionMerchandiseDTO() {
        return constructionMerchandiseDTO;
    }

    public void setConstructionMerchandiseDTO(ConstructionMerchandiseDTO constructionMerchandiseDTO) {
        this.constructionMerchandiseDTO = constructionMerchandiseDTO;
    }

    public ConstructionMerchandiseDetailDTO getConstructionMerchandiseDetailDTO() {
        return constructionMerchandiseDetailDTO;
    }

    public void setConstructionMerchandiseDetailDTO(ConstructionMerchandiseDetailDTO constructionMerchandiseDetailDTO) {
        this.constructionMerchandiseDetailDTO = constructionMerchandiseDetailDTO;
    }

    public List<ConstructionImageInfo> getListImage() {
        return listImage;
    }

    public void setListImage(List<ConstructionImageInfo> listImage) {
        this.listImage = listImage;
    }

    public List<ConstructionMerchandiseDetailVTDTO> getListDSVT() {
        return listDSVT;
    }

    public void setListDSVT(List<ConstructionMerchandiseDetailVTDTO> listDSVT) {
        this.listDSVT = listDSVT;
    }

    public List<ConstructionMerchandiseDetailVTDTO> getListDSTB() {
        return listDSTB;
    }

    public void setListDSTB(List<ConstructionMerchandiseDetailVTDTO> listDSTB) {
        this.listDSTB = listDSTB;
    }
}
