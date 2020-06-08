package com.viettel.construction.model.api;

import java.util.List;

public class ConstructionAcceptanceDTOResponse {
    private ResultInfo resultInfo;
    private SysUserRequest sysUser;

    private List<ConstructionAcceptanceCertDetailDTO> listConstructionAcceptanceCertPagesDTO;
    private List<ConstructionAcceptanceCertDetailDTO> listConstructionAcceptanceCertWorkItemsPagesDTO;

    private List<ConstructionAcceptanceCertDetailVTADTO> listConstructionAcceptanceCertDetailVTAPagesDTO;
    private List<ConstructionAcceptanceCertDetailVTADTO> listConstructionAcceptanceCertDetailTBAPagesDTO;

    private List<ConstructionAcceptanceCertDetailVTADTO> listConstructionAcceptanceCertDetailVTATPagesDTO;
    private List<ConstructionAcceptanceCertDetailVTADTO> listConstructionAcceptanceCertDetailTBATPagesDTO;

    private List<ConstructionAcceptanceCertDetailVTBDTO> listConstructionAcceptanceCertDetailVTBPagesDTO;
    private List<ConstructionAcceptanceCertDetailVTBDTO> listConstructionAcceptanceCertDetailTBBPagesDTO;

    private ConstructionAcceptanceCertDetailDTO constructionAcceptanceCertDetailDTO;

    private Long totalConstructionAcceptance;
    private Long numberNoConstructionAcceptance;
    private Long numberConstructionAcceptance;

    private List<ConstructionImageInfo> listImage;

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

    public List<ConstructionAcceptanceCertDetailDTO> getListConstructionAcceptanceCertPagesDTO() {
        return listConstructionAcceptanceCertPagesDTO;
    }

    public void setListConstructionAcceptanceCertPagesDTO(List<ConstructionAcceptanceCertDetailDTO> listConstructionAcceptanceCertPagesDTO) {
        this.listConstructionAcceptanceCertPagesDTO = listConstructionAcceptanceCertPagesDTO;
    }

    public List<ConstructionAcceptanceCertDetailDTO> getListConstructionAcceptanceCertWorkItemsPagesDTO() {
        return listConstructionAcceptanceCertWorkItemsPagesDTO;
    }

    public void setListConstructionAcceptanceCertWorkItemsPagesDTO(List<ConstructionAcceptanceCertDetailDTO> listConstructionAcceptanceCertWorkItemsPagesDTO) {
        this.listConstructionAcceptanceCertWorkItemsPagesDTO = listConstructionAcceptanceCertWorkItemsPagesDTO;
    }

    public List<ConstructionAcceptanceCertDetailVTADTO> getListConstructionAcceptanceCertDetailVTAPagesDTO() {
        return listConstructionAcceptanceCertDetailVTAPagesDTO;
    }

    public void setListConstructionAcceptanceCertDetailVTAPagesDTO(List<ConstructionAcceptanceCertDetailVTADTO> listConstructionAcceptanceCertDetailVTAPagesDTO) {
        this.listConstructionAcceptanceCertDetailVTAPagesDTO = listConstructionAcceptanceCertDetailVTAPagesDTO;
    }

    public List<ConstructionAcceptanceCertDetailVTADTO> getListConstructionAcceptanceCertDetailTBAPagesDTO() {
        return listConstructionAcceptanceCertDetailTBAPagesDTO;
    }

    public void setListConstructionAcceptanceCertDetailTBAPagesDTO(List<ConstructionAcceptanceCertDetailVTADTO> listConstructionAcceptanceCertDetailTBAPagesDTO) {
        this.listConstructionAcceptanceCertDetailTBAPagesDTO = listConstructionAcceptanceCertDetailTBAPagesDTO;
    }

    public List<ConstructionAcceptanceCertDetailVTBDTO> getListConstructionAcceptanceCertDetailVTBPagesDTO() {
        return listConstructionAcceptanceCertDetailVTBPagesDTO;
    }

    public void setListConstructionAcceptanceCertDetailVTBPagesDTO(List<ConstructionAcceptanceCertDetailVTBDTO> listConstructionAcceptanceCertDetailVTBPagesDTO) {
        this.listConstructionAcceptanceCertDetailVTBPagesDTO = listConstructionAcceptanceCertDetailVTBPagesDTO;
    }

    public List<ConstructionAcceptanceCertDetailVTBDTO> getListConstructionAcceptanceCertDetailTBBPagesDTO() {
        return listConstructionAcceptanceCertDetailTBBPagesDTO;
    }

    public void setListConstructionAcceptanceCertDetailTBBPagesDTO(List<ConstructionAcceptanceCertDetailVTBDTO> listConstructionAcceptanceCertDetailTBBPagesDTO) {
        this.listConstructionAcceptanceCertDetailTBBPagesDTO = listConstructionAcceptanceCertDetailTBBPagesDTO;
    }

    public ConstructionAcceptanceCertDetailDTO getConstructionAcceptanceCertDetailDTO() {
        return constructionAcceptanceCertDetailDTO;
    }

    public void setConstructionAcceptanceCertDetailDTO(ConstructionAcceptanceCertDetailDTO constructionAcceptanceCertDetailDTO) {
        this.constructionAcceptanceCertDetailDTO = constructionAcceptanceCertDetailDTO;
    }

    public Long getTotalConstructionAcceptance() {
        return totalConstructionAcceptance;
    }

    public void setTotalConstructionAcceptance(Long totalConstructionAcceptance) {
        this.totalConstructionAcceptance = totalConstructionAcceptance;
    }

    public Long getNumberNoConstructionAcceptance() {
        return numberNoConstructionAcceptance;
    }

    public void setNumberNoConstructionAcceptance(Long numberNoConstructionAcceptance) {
        this.numberNoConstructionAcceptance = numberNoConstructionAcceptance;
    }

    public Long getNumberConstructionAcceptance() {
        return numberConstructionAcceptance;
    }

    public void setNumberConstructionAcceptance(Long numberConstructionAcceptance) {
        this.numberConstructionAcceptance = numberConstructionAcceptance;
    }

    public List<ConstructionAcceptanceCertDetailVTADTO> getListConstructionAcceptanceCertDetailVTATPagesDTO() {
        return listConstructionAcceptanceCertDetailVTATPagesDTO;
    }

    public void setListConstructionAcceptanceCertDetailVTATPagesDTO(List<ConstructionAcceptanceCertDetailVTADTO> listConstructionAcceptanceCertDetailVTATPagesDTO) {
        this.listConstructionAcceptanceCertDetailVTATPagesDTO = listConstructionAcceptanceCertDetailVTATPagesDTO;
    }

    public List<ConstructionAcceptanceCertDetailVTADTO> getListConstructionAcceptanceCertDetailTBATPagesDTO() {
        return listConstructionAcceptanceCertDetailTBATPagesDTO;
    }

    public void setListConstructionAcceptanceCertDetailTBATPagesDTO(List<ConstructionAcceptanceCertDetailVTADTO> listConstructionAcceptanceCertDetailTBATPagesDTO) {
        this.listConstructionAcceptanceCertDetailTBATPagesDTO = listConstructionAcceptanceCertDetailTBATPagesDTO;
    }

    public List<ConstructionImageInfo> getListImage() {
        return listImage;
    }

    public void setListImage(List<ConstructionImageInfo> listImage) {
        this.listImage = listImage;
    }
}
