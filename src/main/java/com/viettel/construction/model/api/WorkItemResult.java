package com.viettel.construction.model.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.viettel.construction.model.Image;

/**
 * Created by hoang on 15/03/2018.
 */

public class WorkItemResult {

    private ResultInfo resultInfo;
    private List<Image> listImage;
    @SerializedName("listWorkItem")
    private List<WorkItemDetailDTO> listWorkItem;

    public WorkItemResult(ResultInfo resultInfo, List<WorkItemDetailDTO> listWorkItemDetailDTO) {
        this.resultInfo = resultInfo;
        this.listWorkItem = listWorkItemDetailDTO;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public List<WorkItemDetailDTO> getListWorkItemDTO() {
        return listWorkItem;
    }

    public void setListWorkItemDTO(List<WorkItemDetailDTO> listWorkItemDetailDTO) {
        this.listWorkItem = listWorkItemDetailDTO;
    }
}
