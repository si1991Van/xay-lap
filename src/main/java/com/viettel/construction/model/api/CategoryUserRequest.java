package com.viettel.construction.model.api;

import java.io.Serializable;

public class CategoryUserRequest implements Serializable {

    private SysUserRequest sysUserRequest;

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }


}
