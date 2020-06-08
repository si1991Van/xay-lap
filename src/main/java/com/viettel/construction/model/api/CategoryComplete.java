package com.viettel.construction.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by manro on 3/22/2018.
 */

public class CategoryComplete {

    private SysUserRequest sysUserRequest;
    @SerializedName("workItemDetailDto")
    private WorkItemDetailDTO workItemDetailDTO;

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }

    public WorkItemDetailDTO getWorkItemDetailDTO() {
        return workItemDetailDTO;
    }

    public void setWorkItemDetailDTO(WorkItemDetailDTO workItemDetailDTO) {
        this.workItemDetailDTO = workItemDetailDTO;
    }
}
