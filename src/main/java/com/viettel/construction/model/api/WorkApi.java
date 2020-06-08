package com.viettel.construction.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 15/03/2018.
 */

public class WorkApi {
    private SysUserRequest sysUserRequest;
    @SerializedName("workItemDetailDto")
    private WorkItemDetailDTO workItemDetailDTO;

    public WorkApi(SysUserRequest sysUserRequest, WorkItemDetailDTO workItemDetailDTO) {
        this.sysUserRequest = sysUserRequest;
        this.workItemDetailDTO = workItemDetailDTO;
    }

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
