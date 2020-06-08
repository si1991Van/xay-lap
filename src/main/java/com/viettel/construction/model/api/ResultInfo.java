package com.viettel.construction.model.api;

/**
 * Created by Manroid on 3/10/2018.
 */

public class ResultInfo {
    private String status;
    private String message;

    public ResultInfo() {
    }

    public ResultInfo(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
