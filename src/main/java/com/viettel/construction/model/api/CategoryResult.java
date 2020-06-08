package com.viettel.construction.model.api;

/**
 * Created by Ramona on 3/19/2018.
 */

public class CategoryResult {
    private AuthenticationInfo authenticationInfo;
    private long sysUserId;
    private long sysGroupId;

    public CategoryResult(AuthenticationInfo authenticationInfo, long sysUserId, long sysGroupId) {
        this.authenticationInfo = authenticationInfo;
        this.sysUserId = sysUserId;
        this.sysGroupId = sysGroupId;
    }
}
