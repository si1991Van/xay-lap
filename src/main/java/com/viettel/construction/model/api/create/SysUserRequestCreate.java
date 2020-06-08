package com.viettel.construction.model.api.create;

import com.viettel.construction.model.api.AuthenticationInfo;

/**
 * Created by manro on 3/21/2018.
 */

public class SysUserRequestCreate {

    private AuthenticationInfo authenticationInfo;
    private long sysUserId;
    private long sysGroupId;

    public SysUserRequestCreate(AuthenticationInfo authenticationInfo, long sysUserId, long sysGroupId) {
        this.authenticationInfo = authenticationInfo;
        this.sysUserId = sysUserId;
        this.sysGroupId = sysGroupId;
    }
}
