package com.viettel.construction.model.api;

import java.util.List;

import com.viettel.construction.model.Image;

/**
 * Created by manro on 3/18/2018.
 */

public class ResultUpdate {
    private List<Image> listImage;
    private List<ConstructionTask> listConstructionTask;
    private ResultInfo resultInfo;
    private List<ConstructionTaskDTO> lstConstructionTaskDTO;

    public ResultUpdate(List<Image> listImage, List<ConstructionTask> listConstructionTask, ResultInfo resultInfo, List<ConstructionTaskDTO> lstConstructionTaskDTO) {
        this.listImage = listImage;
        this.listConstructionTask = listConstructionTask;
        this.resultInfo = resultInfo;
        this.lstConstructionTaskDTO = lstConstructionTaskDTO;
    }

    public List<Image> getListImage() {
        return listImage;
    }

    public void setListImage(List<Image> listImage) {
        this.listImage = listImage;
    }

    public List<ConstructionTask> getListConstructionTask() {
        return listConstructionTask;
    }

    public void setListConstructionTask(List<ConstructionTask> listConstructionTask) {
        this.listConstructionTask = listConstructionTask;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public List<ConstructionTaskDTO> getLstConstructionTaskDTO() {
        return lstConstructionTaskDTO;
    }

    public void setLstConstructionTaskDTO(List<ConstructionTaskDTO> lstConstructionTaskDTO) {
        this.lstConstructionTaskDTO = lstConstructionTaskDTO;
    }
}
