package com.viettel.construction.model.api;

import java.util.List;

/**
 * Created by manro on 3/19/2018.
 */

public class WorkItemDetailDTORespone {
    private List<WorkItemDetailDTO> listWorkItem;
    private ResultInfo resultInfo;

    public WorkItemDetailDTORespone(List<WorkItemDetailDTO> listWorkItem, ResultInfo resultInfo) {
        this.listWorkItem = listWorkItem;
        this.resultInfo = resultInfo;
    }

    public List<WorkItemDetailDTO> getListWorkItem() {
        return listWorkItem;
    }

    public void setListWorkItem(List<WorkItemDetailDTO> listWorkItem) {
        this.listWorkItem = listWorkItem;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }
}
