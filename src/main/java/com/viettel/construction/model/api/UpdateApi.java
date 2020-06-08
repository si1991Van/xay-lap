package com.viettel.construction.model.api;

/**
 * Created by manro on 3/12/2018.
 */

public class UpdateApi {
    private SysUserRequest sysUserRequest;
    private ConstructionTaskDTO constructionTaskDTO;
    private String flag;

    public UpdateApi(SysUserRequest sysUserRequest, ConstructionTaskDTO constructionTaskDTO, String flag) {
        this.sysUserRequest = sysUserRequest;
        this.constructionTaskDTO = constructionTaskDTO;
        this.flag = flag;
    }

    public UpdateApi(SysUserRequest sysUserRequest, ConstructionTaskDTO constructionTaskDTO) {
        this.sysUserRequest = sysUserRequest;
        this.constructionTaskDTO = constructionTaskDTO;
    }

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }

    public ConstructionTaskDTO getConstructionTaskDTO() {
        return constructionTaskDTO;
    }

    public void setConstructionTaskDTO(ConstructionTaskDTO constructionTaskDTO) {
        this.constructionTaskDTO = constructionTaskDTO;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
