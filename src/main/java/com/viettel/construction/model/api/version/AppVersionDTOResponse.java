package com.viettel.construction.model.api.version;

        import com.viettel.construction.model.api.ResultInfo;

public class AppVersionDTOResponse {

    private ResultInfo resultInfo;
    private AppVersionWorkItemDTO appVersionWorkItemDTO;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public AppVersionWorkItemDTO getAppVersionWorkItemDTO() {
        return appVersionWorkItemDTO;
    }

    public void setAppVersionWorkItemDTO(AppVersionWorkItemDTO appVersionWorkItemDTO) {
        this.appVersionWorkItemDTO = appVersionWorkItemDTO;
    }
}
