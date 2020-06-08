package com.viettel.construction.model;

import com.viettel.construction.model.api.ConstructionBGMBDTO;

import java.util.ArrayList;

public class BgmbTaskUpdateRequest {
    private long sysUserId;;
    private ConstructionBGMBDTO assignHandoverDTO;

    public BgmbTaskUpdateRequest(long sysUserId, ConstructionBGMBDTO assignHandoverDTO) {
        this.sysUserId = sysUserId;
        this.assignHandoverDTO = assignHandoverDTO;
    }

    public BgmbTaskUpdateRequest() {
    }

    public long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public ConstructionBGMBDTO getAssignHandoverDTO() {
        return assignHandoverDTO;
    }

    public void setAssignHandoverDTO(ConstructionBGMBDTO assignHandoverDTO) {
        this.assignHandoverDTO = assignHandoverDTO;
    }
}
