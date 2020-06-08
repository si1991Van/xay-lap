package com.viettel.construction.model.api;

/**
 * Created by manro on 3/6/2018.
 */

public class ResultUserStation {

    private AuthenticationInfo authenticationInfo;
    private long sysUserId;
    private long departmentId;
    private long sysGroupId;


    public ResultUserStation() {
    }

    public ResultUserStation(AuthenticationInfo authenticationInfo, long sysUserId, long sysGroupId) {
        this.authenticationInfo = authenticationInfo;
        this.sysUserId = sysUserId;
        this.sysGroupId = sysGroupId;
    }

    public ResultUserStation(AuthenticationInfo authenticationInfo, long sysUserId, long departmentId, long sysGroupId) {
        this.authenticationInfo = authenticationInfo;
        this.sysUserId = sysUserId;
        this.departmentId = departmentId;
        this.sysGroupId = sysGroupId;
    }
}
