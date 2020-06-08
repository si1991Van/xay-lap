package com.viettel.construction.model.api;

/**
 * Created by manro on 3/18/2018.
 */

public class UpdateClient {
    private SysUserRequest sysUserRequest;
    private long sysGroupId;
    private long sysUserId;
    private ConstructionTaskDTO constructionTaskDTO;
    private SysUserDTO sysUserDTO;
    private String flag ;

    public UpdateClient(SysUserRequest sysUserRequest, long sysGroupId, long sysUserId, ConstructionTaskDTO constructionTaskDTO, SysUserDTO sysUserDTO, String flag) {
        this.sysUserRequest = sysUserRequest;
        this.sysGroupId = sysGroupId;
        this.sysUserId = sysUserId;
        this.constructionTaskDTO = constructionTaskDTO;
        this.sysUserDTO = sysUserDTO;
        this.flag = flag;
    }

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }

    public long getSysGroupId() {
        return sysGroupId;
    }

    public void setSysGroupId(long sysGroupId) {
        this.sysGroupId = sysGroupId;
    }

    public long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public ConstructionTaskDTO getConstructionTaskDTO() {
        return constructionTaskDTO;
    }

    public void setConstructionTaskDTO(ConstructionTaskDTO constructionTaskDTO) {
        this.constructionTaskDTO = constructionTaskDTO;
    }

    public SysUserDTO getSysUserDTO() {
        return sysUserDTO;
    }

    public void setSysUserDTO(SysUserDTO sysUserDTO) {
        this.sysUserDTO = sysUserDTO;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
