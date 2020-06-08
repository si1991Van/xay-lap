package com.viettel.construction.model.api;

public class BgmbRequestGetListImage {
    private long assignHandoverId;

    public BgmbRequestGetListImage(long assignHandoverId) {
        this.assignHandoverId = assignHandoverId;
    }

    public BgmbRequestGetListImage() {
    }

    public long getAssignHandoverId() {
        return assignHandoverId;
    }

    public void setAssignHandoverId(long assignHandoverId) {
        this.assignHandoverId = assignHandoverId;
    }
}
