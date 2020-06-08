package com.viettel.construction.model.api;

import com.viettel.construction.model.api.version.AppParamDTO;

public class AppParamDTORequest {
    private SysUserRequest sysUserRequest;
    private SysUserRequest sysUserReceiver;
    private AppParamDTO appParamDTO;

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }

    public SysUserRequest getSysUserReceiver() {
        return sysUserReceiver;
    }

    public void setSysUserReceiver(SysUserRequest sysUserReceiver) {
        this.sysUserReceiver = sysUserReceiver;
    }

    public AppParamDTO getAppParamDTO() {
        return appParamDTO;
    }

    public void setAppParamDTO(AppParamDTO appParamDTO) {
        this.appParamDTO = appParamDTO;
    }
}
