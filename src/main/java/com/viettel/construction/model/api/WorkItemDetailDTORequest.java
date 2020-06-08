package com.viettel.construction.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by manro on 3/19/2018.
 */

public class WorkItemDetailDTORequest {
    private SysUserRequest sysUserRequest;
    @SerializedName("workItemDetailDto")
    private WorkItemDetailDTO workItemDetailDTO;

    public WorkItemDetailDTORequest(SysUserRequest sysUserRequest, WorkItemDetailDTO workItemDetailDTO) {
        this.sysUserRequest = sysUserRequest;
        this.workItemDetailDTO = workItemDetailDTO;
    }
}
