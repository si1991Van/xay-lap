package com.viettel.construction.model;

/**
 * Created by manro on 2/22/2018.
 */

public class Bill {
    private String code;
    private String formCode;
    private String exportDate;
    private String status;

    public Bill() {
    }

    public Bill(String code, String formCode, String exportDate, String status) {
        this.code = code;
        this.formCode = formCode;
        this.exportDate = exportDate;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getExportDate() {
        return exportDate;
    }

    public void setExportDate(String exportDate) {
        this.exportDate = exportDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
