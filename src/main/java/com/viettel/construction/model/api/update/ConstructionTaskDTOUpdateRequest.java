package com.viettel.construction.model.api.update;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.viettel.construction.model.api.version.AppParamDTO;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.api.ConstructionTaskDTO;
import com.viettel.construction.model.api.ConstructionTaskDailyDTO;
import com.viettel.construction.model.api.ConstructionTaskDetailDTO;
import com.viettel.construction.model.api.SysUserDTO;
import com.viettel.construction.model.api.SysUserRequest;

/**
 * Created by manro on 3/19/2018.
 */

public class ConstructionTaskDTOUpdateRequest {

    private List<ConstructionImageInfo> listConstructionImageInfo;
    private List<AppParamDTO> listParamDto;
    private SysUserRequest sysUserRequest;
    private ConstructionTaskDTO constructionTaskDTO;
    private ConstructionTaskDetailDTO constructionTaskDetail;

    public ConstructionTaskDailyDTO getConstructionTaskDailyDTO() {
        return constructionTaskDailyDTO;
    }

    public void setConstructionTaskDailyDTO(ConstructionTaskDailyDTO constructionTaskDailyDTO) {
        this.constructionTaskDailyDTO = constructionTaskDailyDTO;
    }

    private ConstructionTaskDailyDTO constructionTaskDailyDTO;

    @SerializedName("sysUserDto")
    private SysUserDTO sysUserDto;
    private long flag;



    public ConstructionTaskDTOUpdateRequest() {
    }

    public List<ConstructionImageInfo> getListConstructionImageInfo() {
        return listConstructionImageInfo;
    }

    public void setListConstructionImageInfo(List<ConstructionImageInfo> listConstructionImageInfo) {
        this.listConstructionImageInfo = listConstructionImageInfo;
    }

    public List<AppParamDTO> getListParamDto() {
        return listParamDto;
    }

    public void setListParamDto(List<AppParamDTO> listParamDto) {
        this.listParamDto = listParamDto;
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

    public long getFlag() {
        return flag;
    }

    public void setFlag(long flag) {
        this.flag = flag;
    }
}
