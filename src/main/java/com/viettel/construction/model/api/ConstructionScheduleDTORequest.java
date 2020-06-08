package com.viettel.construction.model.api;

public class ConstructionScheduleDTORequest {

    private SysUserRequest sysUserRequest;
    private SysUserRequest sysUserReceiver;
    private ConstructionScheduleDTO constructionScheduleDTO;
    private ConstructionScheduleItemDTO constructionScheduleItemDTO;
    private String scheduleType;

    public SysUserRequest getSysUserReceiver() {
        return sysUserReceiver;
    }

    public void setSysUserReceiver(SysUserRequest sysUserReceiver) {
        this.sysUserReceiver = sysUserReceiver;
    }

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }

    public ConstructionScheduleDTO getConstructionScheduleDTO() {
        return constructionScheduleDTO;
    }

    public void setConstructionScheduleDTO(ConstructionScheduleDTO constructionScheduleDTO) {
        this.constructionScheduleDTO = constructionScheduleDTO;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public ConstructionScheduleItemDTO getConstructionScheduleItemDTO() {
        return constructionScheduleItemDTO;
    }

    public void setConstructionScheduleItemDTO(ConstructionScheduleItemDTO constructionScheduleItemDTO) {
        this.constructionScheduleItemDTO = constructionScheduleItemDTO;
    }
}
