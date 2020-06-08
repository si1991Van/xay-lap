package com.viettel.construction.model.api.create;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.api.ConstructionTaskDTO;
import com.viettel.construction.model.api.ConstructionTaskDetailDTO;
import com.viettel.construction.model.api.SysUserDTO;
import com.viettel.construction.model.api.SysUserRequest;

/**
 * Created by manro on 3/21/2018.
 */

public class Create {

    private List<ConstructionImageInfo> listConstructionImageInfo;
    private SysUserRequest sysUserRequest;
    private ConstructionTaskDTO constructionTaskDTO;
    private ConstructionTaskDetailDTO constructionTaskDetail;

    @SerializedName("sysUserDto")
    private SysUserDTO sysUserDto;

    public Create() {
    }

    public Create(ConstructionTaskDetailDTO constructionTaskDetail, SysUserRequestCreate sysUserDto) {
        this.constructionTaskDetail = constructionTaskDetail;
        this.sysUserRequest = sysUserRequest;
    }

    public List<ConstructionImageInfo> getListConstructionImageInfo() {
        return listConstructionImageInfo;
    }

    public void setListConstructionImageInfo(List<ConstructionImageInfo> listConstructionImageInfo) {
        this.listConstructionImageInfo = listConstructionImageInfo;
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

    public ConstructionTaskDetailDTO getConstructionTaskDetail() {
        return constructionTaskDetail;
    }

    public void setConstructionTaskDetail(ConstructionTaskDetailDTO constructionTaskDetail) {
        this.constructionTaskDetail = constructionTaskDetail;
    }

    public SysUserDTO getSysUserDto() {
        return sysUserDto;
    }

    public void setSysUserDto(SysUserDTO sysUserDto) {
        this.sysUserDto = sysUserDto;
    }
}
