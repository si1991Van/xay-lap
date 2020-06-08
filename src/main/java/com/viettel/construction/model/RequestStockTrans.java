package com.viettel.construction.model;

import com.viettel.construction.model.api.AuthenticationInfo;
import com.viettel.construction.model.api.ResultUser;
import com.viettel.construction.model.api.SynStockTransDTO;
import com.viettel.construction.model.api.SynStockTransDetailDTO;
import com.viettel.construction.model.api.SysUserRequest;

public class RequestStockTrans {

    private ResultUser resultUser;
    private SynStockTransDTO synStockTransDto;
    private SynStockTransDetailDTO synStockTransDetailDto;
    private SysUserRequest sysUserRequest;

    public ResultUser getResultUser() {
        return resultUser;
    }

    public void setResultUser(ResultUser resultUser) {
        this.resultUser = resultUser;
    }

    public SynStockTransDTO getSynStockTransDto() {
        return synStockTransDto;
    }

    public void setSynStockTransDto(SynStockTransDTO synStockTransDto) {
        this.synStockTransDto = synStockTransDto;
    }

    public SynStockTransDetailDTO getSynStockTransDetailDto() {
        return synStockTransDetailDto;
    }

    public void setSynStockTransDetailDto(SynStockTransDetailDTO synStockTransDetailDto) {
        this.synStockTransDetailDto = synStockTransDetailDto;
    }

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }
}
