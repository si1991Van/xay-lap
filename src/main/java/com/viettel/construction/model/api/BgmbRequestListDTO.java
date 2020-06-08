package com.viettel.construction.model.api;

public class BgmbRequestListDTO {
    private long sysUserId;
    private String status;
    private String rowNum;
    private String keySearch;

    public BgmbRequestListDTO() {
    }

    public BgmbRequestListDTO(long sysUserId, String status, String rowNum, String keySearch) {
        this.sysUserId = sysUserId;
        this.status = status;
        this.rowNum = rowNum;
        this.keySearch = keySearch;
    }

    public long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    public String getKeySearch() {
        return keySearch;
    }

    public void setKeySearch(String keySearch) {
        this.keySearch = keySearch;
    }
}
