package com.viettel.construction.model.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.viettel.construction.model.Image;

/**
 * Created by Ramona on 3/10/2018.
 */

public class ListConstructionTaskAll {
    private ResultInfo resultInfo;
    @SerializedName("lstConstrucitonTask")
    private List<ConstructionTaskDTO> lstConstrucitonTask;
    private List<ConstructionTaskDTO> listConstructionScheduleWorkItemDTO;
    private List<Image> listImage;

    public ListConstructionTaskAll(ResultInfo resultInfo, List<ConstructionTaskDTO> lstConstrucitonTask, List<Image> listImage) {
        this.resultInfo = resultInfo;
        this.lstConstrucitonTask = lstConstrucitonTask;
        this.listImage = listImage;
    }

    public ListConstructionTaskAll(ResultInfo resultInfo, List<ConstructionTaskDTO> lstConstrucitonTask) {
        this.resultInfo = resultInfo;
        this.lstConstrucitonTask = lstConstrucitonTask;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public List<ConstructionTaskDTO> getLstConstructionTaskDTO() {
        return lstConstrucitonTask;
    }

    public void setLstConstructionTaskDTO(List<ConstructionTaskDTO> lstConstructionTaskDTO) {
        this.lstConstrucitonTask = lstConstructionTaskDTO;
    }

    public List<ConstructionTaskDTO> getLstConstructionTask() {
        return lstConstrucitonTask;
    }

    public void setLstConstructionTask(List<ConstructionTaskDTO> lstConstructionTask) {
        this.lstConstrucitonTask = lstConstructionTask;
    }

    public List<Image> getListImage() {
        return listImage;
    }

    public void setListImage(List<Image> listImage) {
        this.listImage = listImage;
    }

    public List<ConstructionTaskDTO> getLstConstrucitonTask() {
        return lstConstrucitonTask;
    }

    public void setLstConstrucitonTask(List<ConstructionTaskDTO> lstConstrucitonTask) {
        this.lstConstrucitonTask = lstConstrucitonTask;
    }

    public List<ConstructionTaskDTO> getListConstructionScheduleWorkItemDTO() {
        return listConstructionScheduleWorkItemDTO;
    }

    public void setListConstructionScheduleWorkItemDTO(List<ConstructionTaskDTO> listConstructionScheduleWorkItemDTO) {
        this.listConstructionScheduleWorkItemDTO = listConstructionScheduleWorkItemDTO;
    }
}
