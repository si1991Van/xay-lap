package com.viettel.construction.model.api;

import java.io.Serializable;

public class ConstructionScheduleItemDTO extends ConstructionScheduleDTO implements Serializable{

    private String name;
    private Long workItemId;
    private Long completeState;
    private Long completePercent;
    private String itemOder;
    private String syuFullName;
    private String catPrtName;
    private Long performerId;
    private Long sysGroupId;
    //
    private int perUnImplemented;// Chưa thực hiện
    private int perImplemented;//: Đang thực hiện
    private int perStop;//: Tạm dừng
    private int perComplete;//: Hoàn thành
    private String startDate;
    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getPerUnImplemented() {
        return perUnImplemented;
    }

    public void setPerUnImplemented(int perUnImplemented) {
        this.perUnImplemented = perUnImplemented;
    }

    public int getPerImplemented() {
        return perImplemented;
    }

    public void setPerImplemented(int perImplemented) {
        this.perImplemented = perImplemented;
    }

    public int getPerStop() {
        return perStop;
    }

    public void setPerStop(int perStop) {
        this.perStop = perStop;
    }

    public int getPerComplete() {
        return perComplete;
    }

    public void setPerComplete(int perComplete) {
        this.perComplete = perComplete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(Long workItemId) {
        this.workItemId = workItemId;
    }

    public Long getCompleteState() {
        return completeState;
    }

    public void setCompleteState(Long completeState) {
        this.completeState = completeState;
    }

    public Long getCompletePercent() {
        return completePercent;
    }

    public void setCompletePercent(Long completePercent) {
        this.completePercent = completePercent;
    }

    public String getItemOder() {
        return itemOder;
    }

    public void setItemOder(String itemOder) {
        this.itemOder = itemOder;
    }

    public String getSyuFullName() {
        return syuFullName;
    }

    public void setSyuFullName(String syuFullName) {
        this.syuFullName = syuFullName;
    }

    public String getCatPrtName() {
        return catPrtName;
    }

    public void setCatPrtName(String catPrtName) {
        this.catPrtName = catPrtName;
    }

    public Long getPerformerId() {
        return performerId;
    }

    public void setPerformerId(Long performerId) {
        this.performerId = performerId;
    }

    public Long getSysGroupId() {
        return sysGroupId;
    }

    public void setSysGroupId(Long sysGroupId) {
        this.sysGroupId = sysGroupId;
    }
}
