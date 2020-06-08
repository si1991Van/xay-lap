package com.viettel.construction.model.api.history;

import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.SysUserRequest;

import java.util.List;

public class HandOverHistoryDTORespone {
    private List<StTransactionDTO> listStTransactionReceivePagesDTO;
    private List<StTransactionDTO> listStTransactionHandoverPagesDTO;
    private List<StTransactionDTO> listStTransactionHandoverDTO;
    private List<StTransactionDTO> listStTransactionReceiveDTO;
    private List<StTransactionDTO> listStTransactionHandoverVTTBDetailDTO;
    private List<StTransactionDTO> listStTransactionReceiveVTTBDetailDTO;
    private List<StTransactionDetailDTO> listStTransactionVTTBDTO;
    private List<StTransactionDetailDTO> listStTransactionVTTBDetailDTO;

    private SysUserRequest sysUser;
    private ResultInfo resultInfo;

    public List<StTransactionDTO> getListStTransactionReceivePagesDTO() {
        return listStTransactionReceivePagesDTO;
    }

    public void setListStTransactionReceivePagesDTO(List<StTransactionDTO> listStTransactionReceivePagesDTO) {
        this.listStTransactionReceivePagesDTO = listStTransactionReceivePagesDTO;
    }

    public List<StTransactionDTO> getListStTransactionHandoverPagesDTO() {
        return listStTransactionHandoverPagesDTO;
    }

    public void setListStTransactionHandoverPagesDTO(List<StTransactionDTO> listStTransactionHandoverPagesDTO) {
        this.listStTransactionHandoverPagesDTO = listStTransactionHandoverPagesDTO;
    }

    public List<StTransactionDTO> getListStTransactionHandoverDTO() {
        return listStTransactionHandoverDTO;
    }

    public void setListStTransactionHandoverDTO(List<StTransactionDTO> listStTransactionHandoverDTO) {
        this.listStTransactionHandoverDTO = listStTransactionHandoverDTO;
    }

    public List<StTransactionDTO> getListStTransactionReceiveDTO() {
        return listStTransactionReceiveDTO;
    }

    public void setListStTransactionReceiveDTO(List<StTransactionDTO> listStTransactionReceiveDTO) {
        this.listStTransactionReceiveDTO = listStTransactionReceiveDTO;
    }

    public List<StTransactionDTO> getListStTransactionHandoverVTTBDetailDTO() {
        return listStTransactionHandoverVTTBDetailDTO;
    }

    public void setListStTransactionHandoverVTTBDetailDTO(List<StTransactionDTO> listStTransactionHandoverVTTBDetailDTO) {
        this.listStTransactionHandoverVTTBDetailDTO = listStTransactionHandoverVTTBDetailDTO;
    }

    public List<StTransactionDTO> getListStTransactionReceiveVTTBDetailDTO() {
        return listStTransactionReceiveVTTBDetailDTO;
    }

    public void setListStTransactionReceiveVTTBDetailDTO(List<StTransactionDTO> listStTransactionReceiveVTTBDetailDTO) {
        this.listStTransactionReceiveVTTBDetailDTO = listStTransactionReceiveVTTBDetailDTO;
    }

    public SysUserRequest getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUserRequest sysUser) {
        this.sysUser = sysUser;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public List<StTransactionDetailDTO> getListStTransactionVTTBDTO() {
        return listStTransactionVTTBDTO;
    }

    public void setListStTransactionVTTBDTO(List<StTransactionDetailDTO> listStTransactionVTTBDTO) {
        this.listStTransactionVTTBDTO = listStTransactionVTTBDTO;
    }

    public List<StTransactionDetailDTO> getListStTransactionVTTBDetailDTO() {
        return listStTransactionVTTBDetailDTO;
    }

    public void setListStTransactionVTTBDetailDTO(List<StTransactionDetailDTO> listStTransactionVTTBDetailDTO) {
        this.listStTransactionVTTBDetailDTO = listStTransactionVTTBDetailDTO;
    }
}
