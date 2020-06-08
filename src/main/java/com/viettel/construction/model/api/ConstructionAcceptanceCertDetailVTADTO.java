package com.viettel.construction.model.api;

import com.viettel.construction.model.api.acceptance.ConstructionAcceptanceCertItemTBDTO;
import com.viettel.construction.model.api.acceptance.SynStockTransDetailSerialDTO;

import java.util.List;

public class ConstructionAcceptanceCertDetailVTADTO extends SynStockTransDetailSerialDTO {
    private String goodsUnitName;
    private String realIeTransDate;
    private Long constructionId;
    private Long workItemId;
    private String workItemName;
    private String goodsIsSerial;
    private Double comMerQuantity;
    private Double numberXuat;
    private Double numberThuhoi;
    private Double numberNghiemthu;
    private Double quantity;
    private Double numberDaNghiemThuKhac;
    private Long employ;
    private String totalItemDetail;
    private List<ConstructionAcceptanceCertItemTBDTO> listTBADetail;

    public String getGoodsUnitName() {
        return goodsUnitName;
    }

    public void setGoodsUnitName(String goodsUnitName) {
        this.goodsUnitName = goodsUnitName;
    }

    public String getRealIeTransDate() {
        return realIeTransDate;
    }

    public void setRealIeTransDate(String realIeTransDate) {
        this.realIeTransDate = realIeTransDate;
    }

    public Long getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(Long constructionId) {
        this.constructionId = constructionId;
    }

    public Long getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(Long workItemId) {
        this.workItemId = workItemId;
    }

    public String getWorkItemName() {
        return workItemName;
    }

    public void setWorkItemName(String workItemName) {
        this.workItemName = workItemName;
    }

    public String getGoodsIsSerial() {
        return goodsIsSerial;
    }

    public void setGoodsIsSerial(String goodsIsSerial) {
        this.goodsIsSerial = goodsIsSerial;
    }

    public Double getComMerQuantity() {
        return comMerQuantity;
    }

    public void setComMerQuantity(Double comMerQuantity) {
        this.comMerQuantity = comMerQuantity;
    }

    public Double getNumberXuat() {
        return numberXuat;
    }

    public void setNumberXuat(Double numberXuat) {
        this.numberXuat = numberXuat;
    }

    public Double getNumberThuhoi() {
        return numberThuhoi;
    }

    public void setNumberThuhoi(Double numberThuhoi) {
        this.numberThuhoi = numberThuhoi;
    }

    public Double getNumberNghiemthu() {
        return numberNghiemthu;
    }

    public void setNumberNghiemthu(Double numberNghiemthu) {
        this.numberNghiemthu = numberNghiemthu;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Long getEmploy() {
        return employ;
    }

    public void setEmploy(Long employ) {
        this.employ = employ;
    }

    public Double getNumberDaNghiemThuKhac() {
        return numberDaNghiemThuKhac;
    }

    public void setNumberDaNghiemThuKhac(Double numberDaNghiemThuKhac) {
        this.numberDaNghiemThuKhac = numberDaNghiemThuKhac;
    }

    public String getTotalItemDetail() {
        return totalItemDetail;
    }

    public void setTotalItemDetail(String totalItemDetail) {
        this.totalItemDetail = totalItemDetail;
    }

    public List<ConstructionAcceptanceCertItemTBDTO> getListTBADetail() {
        return listTBADetail;
    }

    public void setListTBADetail(List<ConstructionAcceptanceCertItemTBDTO> listTBADetail) {
        this.listTBADetail = listTBADetail;
    }
}
