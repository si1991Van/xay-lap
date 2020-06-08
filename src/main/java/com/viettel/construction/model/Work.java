package com.viettel.construction.model;

import java.io.Serializable;

/**
 * Created by Manroid on 19/01/2018.
 */

public class Work implements Serializable{

    private String work;
    private String startTime;
    private String finishTime;
    private String estimateTime;
    private String nameConstruction;
    private String category;
    private String status;
    private boolean isLowProcess;

    public Work(String work, String startTime, String finishTime, String estimateTime, String nameConstruction, String category, String status, boolean isLowProcess) {
        this.work = work;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.estimateTime = estimateTime;
        this.nameConstruction = nameConstruction;
        this.category = category;
        this.status = status;
        this.isLowProcess = isLowProcess;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(String estimateTime) {
        this.estimateTime = estimateTime;
    }

    public String getNameConstruction() {
        return nameConstruction;
    }

    public void setNameConstruction(String nameConstruction) {
        this.nameConstruction = nameConstruction;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isLowProcess() {
        return isLowProcess;
    }

    public void setLowProcess(boolean lowProcess) {
        isLowProcess = lowProcess;
    }
}
