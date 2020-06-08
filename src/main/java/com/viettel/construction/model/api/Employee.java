package com.viettel.construction.model.api;

import java.io.Serializable;

/**
 * Created by manro on 3/18/2018.
 */

public class Employee implements Serializable {

    private AuthenticationInfo authenticationInfo;
    private String authorities;
    private long sysUserId;
    private long departmentId;
    private long sysGroupId;
    private String emloyeeCode;
    private long lastShipperId;

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public String getEmloyeeCode() {
        return emloyeeCode;
    }

    public void setEmloyeeCode(String emloyeeCode) {
        this.emloyeeCode = emloyeeCode;
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

    public long getLastShipperId() {
        return lastShipperId;
    }

    public void setLastShipperId(long lastShipperId) {
        this.lastShipperId = lastShipperId;
    }
}
