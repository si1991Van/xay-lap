package com.viettel.construction.model.api;

import java.util.List;

public class ConstructionScheduleDTOResponse {
    private SysUserRequest sysUser;
    private ResultInfo resultInfo;
    private List<ConstructionScheduleDTO> listConstructionScheduleRealizationDTO;
    private List<ConstructionScheduleDTO> listConstructionSchedulePartnerDTO;
    private List<ConstructionScheduleDTO> listConstructionScheduleDirectorByDTO;
    private List<ConstructionScheduleItemDTO> listConstructionScheduleItemDTO;
    private List<ConstructionScheduleWorkItemDTO> listConstructionScheduleWorkItemDTOList;



    public ConstructionScheduleDTOResponse() {
    }

    public SysUserRequest getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUserRequest sysUser) {
        this.sysUser = sysUser;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public List<ConstructionScheduleDTO> getListConstructionScheduleRealizationDTO() {
        return listConstructionScheduleRealizationDTO;
    }

    public void setListConstructionScheduleRealizationDTO(List<ConstructionScheduleDTO> listConstructionScheduleRealizationDTO) {
        this.listConstructionScheduleRealizationDTO = listConstructionScheduleRealizationDTO;
    }

    public List<ConstructionScheduleDTO> getListConstructionSchedulePartnerDTO() {
        return listConstructionSchedulePartnerDTO;
    }

    public void setListConstructionSchedulePartnerDTO(List<ConstructionScheduleDTO> listConstructionSchedulePartnerDTO) {
        this.listConstructionSchedulePartnerDTO = listConstructionSchedulePartnerDTO;
    }

    public List<ConstructionScheduleDTO> getListConstructionScheduleDirectorByDTO() {
        return listConstructionScheduleDirectorByDTO;
    }

    public void setListConstructionScheduleDirectorByDTO(List<ConstructionScheduleDTO> listConstructionScheduleDirectorByDTO) {
        this.listConstructionScheduleDirectorByDTO = listConstructionScheduleDirectorByDTO;
    }

    public List<ConstructionScheduleItemDTO> getListConstructionScheduleItemDTO() {
        return listConstructionScheduleItemDTO;
    }

    public void setListConstructionScheduleItemDTO(List<ConstructionScheduleItemDTO> listConstructionScheduleItemDTO) {
        this.listConstructionScheduleItemDTO = listConstructionScheduleItemDTO;
    }

    public List<ConstructionScheduleWorkItemDTO> getListConstructionScheduleWorkItemDTOList() {
        return listConstructionScheduleWorkItemDTOList;
    }

    public void setListConstructionScheduleWorkItemDTOList(List<ConstructionScheduleWorkItemDTO> listConstructionScheduleWorkItemDTOList) {
        this.listConstructionScheduleWorkItemDTOList = listConstructionScheduleWorkItemDTOList;
    }
}
