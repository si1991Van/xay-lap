package com.viettel.construction.model.api;

/**
 * Created by manro on 3/6/2018.
 */

public class ResultUser {

    private AuthenticationInfo authenticationInfo;
    private long sysUserId;
    private long departmentId;
    private long flag;
    private long sysGroupId;


    public ResultUser() {
    }

    public ResultUser(long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public ResultUser(AuthenticationInfo authenticationInfo, long sysUserId, long departmentId, long flag) {
        this.authenticationInfo = authenticationInfo;
        this.sysUserId = sysUserId;
        this.departmentId = departmentId;
        this.flag = flag;
    }



    public ResultUser(AuthenticationInfo authenticationInfo, long sysUserId) {
        this.authenticationInfo = authenticationInfo;
        this.sysUserId = sysUserId;
    }

    public ResultUser(AuthenticationInfo authenticationInfo, long sysUserId, long departmentId) {
        this.authenticationInfo = authenticationInfo;
        this.sysUserId = sysUserId;
        this.departmentId = departmentId;
    }

}
