package com.viettel.construction.model.api;

import java.util.List;

/**
 * Created by Manroid on 3/10/2018.
 */

public class ResultApi {

    private ResultInfo resultInfo;
    private CountConstructionTaskDTO countConstructionTaskDTO;
    private List<ConstructionImageInfo> listImage;
    private CountConstructionTaskDTO countStockTrans;


    public ResultApi(ResultInfo resultInfo, CountConstructionTaskDTO countConstructionTaskDTO) {
        this.resultInfo = resultInfo;
        this.countConstructionTaskDTO = countConstructionTaskDTO;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public CountConstructionTaskDTO getCountConstructionTaskDTO() {
        return countConstructionTaskDTO;
    }

    public void setCountConstructionTaskDTO(CountConstructionTaskDTO countConstructionTaskDTO) {
        this.countConstructionTaskDTO = countConstructionTaskDTO;
    }

    public List<ConstructionImageInfo> getListImage() {
        return listImage;
    }

    public void setListImage(List<ConstructionImageInfo> listImage) {
        this.listImage = listImage;
    }

    public CountConstructionTaskDTO getCountStockTrans() {
        return countStockTrans;
    }

    public void setCountStockTrans(CountConstructionTaskDTO countStockTrans) {
        this.countStockTrans = countStockTrans;
    }
}
