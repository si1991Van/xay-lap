package com.viettel.construction.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.viettel.construction.model.ListImage;

import java.util.List;

public class StockTransResponse {

    @SerializedName("resultInfo")
    @Expose
    public ResultInfo resultInfo;
    @SerializedName("countStockTrans")
    @Expose
    public CountConstructionTaskDTO countStockTrans;
    @SerializedName("lstSynStockTransDto")
    @Expose
    public List<SynStockTransDTO> lstSynStockTransDto;
    @SerializedName("lstSynStockTransDetail")
    @Expose
    public List<SynStockTransDetailDTO> lstSynStockTransDetail;
    @SerializedName("lstMerEntity")
    @Expose
    public List<MerEntityDTO> lstMerEntity;
    @SerializedName("utilAttachDocumentDTOs")
    @Expose
    public List<ListImage> utilAttachDocumentDTOs;

    @SerializedName("total")
    @Expose
    public long total;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public List<SynStockTransDTO> getLstSynStockTransDto() {
        return lstSynStockTransDto;
    }

    public void setLstSynStockTransDto(List<SynStockTransDTO> lstSynStockTransDto) {
        this.lstSynStockTransDto = lstSynStockTransDto;
    }

    public CountConstructionTaskDTO getCountStockTrans() {
        return countStockTrans;
    }

    public void setCountStockTrans(CountConstructionTaskDTO countStockTrans) {
        this.countStockTrans = countStockTrans;
    }

    public List<SynStockTransDetailDTO> getLisSynStockTransDetail() {
        return lstSynStockTransDetail;
    }

    public void setLisSynStockTransDetail(List<SynStockTransDetailDTO> lisSynStockTransDetail) {
        this.lstSynStockTransDetail = lisSynStockTransDetail;
    }

    public List<MerEntityDTO> getLstMerEntity() {
        return lstMerEntity;
    }

    public void setLstMerEntity(List<MerEntityDTO> lstMerEntity) {
        this.lstMerEntity = lstMerEntity;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
