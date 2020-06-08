package com.viettel.construction.model.api;

import java.util.List;

public class StockTransRequest {
    private SysUserRequest sysUserRequest;
    private SysUserRequest sysUserReceiver;
    private SynStockTransDTO synStockTransDto;
    private List<SynStockTransDTO> lstSynStockTransDto;
    private SynStockTransDetailDTO synStockTransDetailDto;
    private long page;
    private int pageSize;
    private String keySearch;
    private String constructionType;
    private long confirm;
    private long state;
    private String overDateKPI;
    private Long receiverId;
    private String confirmDescription;

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }

    public SysUserRequest getSysUserReceiver() {
        return sysUserReceiver;
    }

    public void setSysUserReceiver(SysUserRequest sysUserReceiver) {
        this.sysUserReceiver = sysUserReceiver;
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

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeySearch() {
        return keySearch;
    }

    public void setKeySearch(String keySearch) {
        this.keySearch = keySearch;
    }

    public String getConstructionType() {
        return constructionType;
    }

    public void setConstructionType(String constructionType) {
        this.constructionType = constructionType;
    }

    public long getConfirm() {
        return confirm;
    }

    public void setConfirm(long confirm) {
        this.confirm = confirm;
    }

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    public String getOverDateKPI() {
        return overDateKPI;
    }

    public void setOverDateKPI(String overDateKPI) {
        this.overDateKPI = overDateKPI;
    }

    public List<SynStockTransDTO> getLstSynStockTransDto() {
        return lstSynStockTransDto;
    }

    public void setLstSynStockTransDto(List<SynStockTransDTO> lstSynStockTransDto) {
        this.lstSynStockTransDto = lstSynStockTransDto;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getConfirmDescription() {
        return confirmDescription;
    }

    public void setConfirmDescription(String confirmDescription) {
        this.confirmDescription = confirmDescription;
    }
}

