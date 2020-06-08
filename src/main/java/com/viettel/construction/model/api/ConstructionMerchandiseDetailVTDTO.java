package com.viettel.construction.model.api;

import com.viettel.construction.model.api.acceptance.SynStockTransDetailSerialDTO;

import java.util.List;

public class ConstructionMerchandiseDetailVTDTO extends SynStockTransDetailSerialDTO{
    private String goodsUnitName;
    private String realIeTransDate;
    private Long constructionId;
    private Long workItemId;
    private String workItemName;
    private String goodsIsSerial;
    private Double conMerquantity;
    private Double numberXuat;
    private Double numberThuhoi;
    private Double numberNghiemthu;
    private Integer numberHoanTra;
    private Double numberHoanTraKhac;
    private Double numberNghiemThuKhac;
    private Long totalItemDetail;
    private Integer quantity;
    private Long employ;
    private List<ConstructionMerchandiseItemTBDTO> listTBDetail;

    public String getGoodUnitName() {
        return goodsUnitName;
    }

    public void setGoodUnitName(String goodUnitName) {
        this.goodsUnitName = goodUnitName;
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

    public Double getConMerquantity() {
        return conMerquantity;
    }

    public void setConMerquantity(Double conMerquantity) {
        this.conMerquantity = conMerquantity;
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

    public Integer getNumberHoanTra() {
        return numberHoanTra;
    }

    public void setNumberHoanTra(Integer numberHoanTra) {
        this.numberHoanTra = numberHoanTra;
    }

    public Double getNumberHoanTraKhac() {
        return numberHoanTraKhac;
    }

    public void setNumberHoanTraKhac(Double numberHoanTraKhac) {
        this.numberHoanTraKhac = numberHoanTraKhac;
    }

    public Double getNumberNghiemThuKhac() {
        return numberNghiemThuKhac;
    }

    public void setNumberNghiemThuKhac(Double numberNghiemThuKhac) {
        this.numberNghiemThuKhac = numberNghiemThuKhac;
    }

    public Long getTotalItemDetail() {
        return totalItemDetail;
    }

    public void setTotalItemDetail(Long totalItemDetail) {
        this.totalItemDetail = totalItemDetail;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getEmploy() {
        return employ;
    }

    public void setEmploy(Long employ) {
        this.employ = employ;
    }

    public List<ConstructionMerchandiseItemTBDTO> getListTBDetail() {
        return listTBDetail;
    }

    public void setListTBDetail(List<ConstructionMerchandiseItemTBDTO> listTBDetail) {
        this.listTBDetail = listTBDetail;
    }
}
