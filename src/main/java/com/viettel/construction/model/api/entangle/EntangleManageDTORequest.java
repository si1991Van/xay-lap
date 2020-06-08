package com.viettel.construction.model.api.entangle;

import com.viettel.construction.model.api.SysUserRequest;

public class EntangleManageDTORequest {

    private SysUserRequest sysUserRequest;
    private EntangleManageDTO entangleManageDTODetail;

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }

    public EntangleManageDTO getEntangleManageDTODetail() {
        return entangleManageDTODetail;
    }

    public void setEntangleManageDTODetail(EntangleManageDTO entangleManageDTODetail) {
        this.entangleManageDTODetail = entangleManageDTODetail;
    }
}
