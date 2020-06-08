package com.viettel.construction.model.api;

import com.viettel.construction.model.api.version.AppParamDTO;

import java.util.List;

public class AppParamManageDto {

    private ResultInfo resultInfo;
    private List<AppParamDTO> listAppParam;
    private List<AppParamDTO> listDetail;
    private Double proscess;
    private Double amountPreview;
    private int confirmDaily;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public List<AppParamDTO> getListAppParam() {
        return listAppParam;
    }

    public void setListAppParam(List<AppParamDTO> listAppParam) {
        this.listAppParam = listAppParam;
    }

    public List<AppParamDTO> getListDetail() {
        return listDetail;
    }

    public void setListDetail(List<AppParamDTO> listDetail) {
        this.listDetail = listDetail;
    }

    public Double getProcess() {
        return proscess;
    }

    public void setProcess(Double process) {
        this.proscess = process;
    }

    public Double getProscess() {
        return proscess;
    }

    public void setProscess(Double proscess) {
        this.proscess = proscess;
    }

    public Double getAmountPreview() {
        return amountPreview;
    }

    public void setAmountPreview(Double amountPreview) {
        this.amountPreview = amountPreview;
    }

    public int getConfirmDaily() {
        return confirmDaily;
    }

    public void setConfirmDaily(int confirmDaily) {
        this.confirmDaily = confirmDaily;
    }
}
