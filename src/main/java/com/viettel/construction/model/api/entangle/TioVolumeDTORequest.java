package com.viettel.construction.model.api.entangle;

import com.viettel.construction.model.api.SysUserRequest;

public class TioVolumeDTORequest {

    private SysUserRequest sysUserRequest;
//    private EntangleManageDTO entangleManageDTODetail;
    private TioVolumeDTO tioVolumeDTODetail;

    public TioVolumeDTO getTioVolumeDTODetail() {
        return tioVolumeDTODetail;
    }

    public void setTioVolumeDTODetail(TioVolumeDTO tioVolumeDTODetail) {
        this.tioVolumeDTODetail = tioVolumeDTODetail;
    }

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }



}
