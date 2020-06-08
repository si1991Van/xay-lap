package com.viettel.construction.model.api;

/**
 * Created by Ramona on 3/19/2018.
 */

public class WorkOnDayApi {
    private AuthenticationInfo authenticationInfo;
    private long departmentId;
    private long flag;
    private long sysGroupId;
    private long sysUserId;

    public WorkOnDayApi(AuthenticationInfo authenticationInfo, long departmentId, long flag, long sysGroupId, long sysUserId) {
        this.authenticationInfo = authenticationInfo;
        this.departmentId = departmentId;
        this.flag = flag;
        this.sysGroupId = sysGroupId;
        this.sysUserId = sysUserId;
    }
}
