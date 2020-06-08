package com.viettel.construction.model.api.update;

import com.viettel.construction.model.api.AuthenticationInfo;

/**
 * Created by manro on 3/19/2018.
 */

public class Sys {
    private AuthenticationInfo authenticationInfo;
    private long sysGroupId;
    private long sysUserId;

    public Sys(AuthenticationInfo authenticationInfo) {
        this.authenticationInfo = authenticationInfo;
    }

    public Sys(AuthenticationInfo authenticationInfo, long sysGroupId, long sysUserId) {
        this.authenticationInfo = authenticationInfo;
        this.sysGroupId = sysGroupId;
        this.sysUserId = sysUserId;
    }
}
