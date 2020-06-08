package com.viettel.construction.model.api;

import java.io.Serializable;

/**
 * Created by manro on 3/12/2018.
 */

public class SysUserRequest implements Serializable{

    private AuthenticationInfo authenticationInfo;
    private long sysUserId;
    private long departmentId;
    private long sysGroupId;
    private String authorities;

    private int flag;

    public SysUserRequest() {
        setAuthorities(null);
    }

    public SysUserRequest(AuthenticationInfo authenticationInfo) {
        this.authenticationInfo = authenticationInfo;
    }

    public SysUserRequest(AuthenticationInfo authenticationInfo, long sysUserId, long departmentId, long sysGroupId) {
        this.authenticationInfo = authenticationInfo;
        this.sysUserId = sysUserId;
        this.departmentId = departmentId;
        this.sysGroupId = sysGroupId;
    }


    public SysUserRequest(AuthenticationInfo authenticationInfo, long sysUserId, long departmentId) {
        this.authenticationInfo = authenticationInfo;
        this.sysUserId = sysUserId;
        this.departmentId = departmentId;
    }

    public AuthenticationInfo getAuthenticationInfo() {
        return authenticationInfo;
    }

    public void setAuthenticationInfo(AuthenticationInfo authenticationInfo) {
        this.authenticationInfo = authenticationInfo;
    }

    public long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public long getSysGroupId() {
        return sysGroupId;
    }

    public void setSysGroupId(long sysGroupId) {
        this.sysGroupId = sysGroupId;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
}
