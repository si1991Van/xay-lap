package com.viettel.construction.model.api;

/**
 * Created by manro on 3/12/2018.
 */

public class CategoryConstructionApi {
    private SysUserRequest sysUserRequest;
    private ConstructionStationWorkItem constructionStationWorkItem;

    public CategoryConstructionApi(SysUserRequest sysUserRequest, ConstructionStationWorkItem constructionStationWorkItem) {
        this.sysUserRequest = sysUserRequest;
        this.constructionStationWorkItem = constructionStationWorkItem;
    }

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }

    public ConstructionStationWorkItem getConstructionStationWorkItem() {
        return constructionStationWorkItem;
    }

    public void setConstructionStationWorkItem(ConstructionStationWorkItem constructionStationWorkItem) {
        this.constructionStationWorkItem = constructionStationWorkItem;
    }
}
