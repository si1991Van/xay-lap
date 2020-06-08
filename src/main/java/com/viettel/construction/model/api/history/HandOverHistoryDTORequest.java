package com.viettel.construction.model.api.history;

import com.viettel.construction.model.api.SysUserRequest;

public class HandOverHistoryDTORequest {
    private SysUserRequest sysUserRequest;
    private StTransactionDTO stTransactionDTO;
    private StTransactionDetailDTO stTransactionDetailDTO;

    public SysUserRequest getRequest() {
        return sysUserRequest;
    }

    public void setRequest(SysUserRequest request) {
        this.sysUserRequest = request;
    }

    public StTransactionDTO getStTransactionDTO() {
        return stTransactionDTO;
    }

    public void setStTransactionDTO(StTransactionDTO stTransactionDTO) {
        this.stTransactionDTO = stTransactionDTO;
    }

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }

    public StTransactionDetailDTO getStTransactionDetailDTO() {
        return stTransactionDetailDTO;
    }

    public void setStTransactionDetailDTO(StTransactionDetailDTO stTransactionDetailDTO) {
        this.stTransactionDetailDTO = stTransactionDetailDTO;
    }
}
