package com.viettel.construction.model.api;

import java.io.Serializable;

/***
 * Item quản lý công trình
 */
public class ConstructionScheduleDTO implements Serializable {

    private Long constructionId;
    private String constructionCode;
    private String constructionName;
    private Long detailMonthPlanId;
    private String status;
    private String startingDate;
    private String stationCode;
    private String unCompletedTask;
    private String totalTask;
    private String uncomTotalTask;

    private Long constructionScheduleId;
    private String statusText;
    private String sortThisMonth;
    private Double progress;
    private String scheduleType;
    private String isInternal;

    private Double quantity;

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public ConstructionScheduleDTO() {
    }


    public void setDetailMonthPlanId(Long detailMonthPlanId) {
        this.detailMonthPlanId = detailMonthPlanId;
    }

    public String getUncomTotalTask() {
        return uncomTotalTask;
    }

    public void setUncomTotalTask(String uncomTotalTask) {
        this.uncomTotalTask = uncomTotalTask;
    }

    public Long getConstructionScheduleId() {
        return constructionScheduleId;
    }

    public void setConstructionScheduleId(Long constructionScheduleId) {
        this.constructionScheduleId = constructionScheduleId;
    }

    public Long getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(Long constructionId) {
        this.constructionId = constructionId;
    }

    public String getConstructionCode() {
        return constructionCode;
    }

    public void setConstructionCode(String constructionCode) {
        this.constructionCode = constructionCode;
    }

    public String getConstructionName() {
        return constructionName;
    }

    public void setConstructionName(String constructionName) {
        this.constructionName = constructionName;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getSortThisMonth() {
        return sortThisMonth;
    }

    public void setSortThisMonth(String sortThisMonth) {
        this.sortThisMonth = sortThisMonth;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public String getTotalTask() {
        return totalTask;
    }

    public void setTotalTask(String totalTask) {
        this.totalTask = totalTask;
    }

    public String getUnCompletedTask() {
        return unCompletedTask;
    }

    public void setUnCompletedTask(String unCompletedTask) {
        this.unCompletedTask = unCompletedTask;
    }

    public long getDetailMonthPlanId() {
        return detailMonthPlanId;
    }

    public void setDetailMonthPlanId(long detailMonthPlanId) {
        this.detailMonthPlanId = detailMonthPlanId;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getIsInternal() {
        return isInternal;
    }

    public void setIsInternal(String isInternal) {
        this.isInternal = isInternal;
    }

}