package com.viettel.construction.model.api.version;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.viettel.construction.model.api.ResultInfo;

public class AppParamDTOResponse {
    @SerializedName("resultInfo")
    @Expose
    private ResultInfo resultInfo;
    @SerializedName("appParamDTO")
    @Expose
    private AppParamDTO appParamDTO;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public AppParamDTO getAppParamDTO() {
        return appParamDTO;
    }

    public void setAppParamDTO(AppParamDTO appParamDTO) {
        this.appParamDTO = appParamDTO;
    }
}
