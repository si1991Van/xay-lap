package com.viettel.construction.model.api.entangle;

import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.api.ResultInfo;

import java.util.List;

public class TioVolumeDTORespone {
    private ResultInfo resultInfo;
    private List<TioVolumeDTO> listTiovolumeDTO;
//    private List<ConstructionImageInfo> listImg;


    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public List<TioVolumeDTO> getListTiovolumeDTO() {
        return listTiovolumeDTO;
    }

    public void setListTiovolumeDTO(List<TioVolumeDTO> listTiovolumeDTO) {
        this.listTiovolumeDTO = listTiovolumeDTO;
    }
}
