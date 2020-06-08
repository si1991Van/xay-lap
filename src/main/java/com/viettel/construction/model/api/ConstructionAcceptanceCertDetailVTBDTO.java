package com.viettel.construction.model.api;

import com.viettel.construction.model.api.acceptance.ConstructionAcceptanceCertItemTBDTO;

import java.util.List;

public class ConstructionAcceptanceCertDetailVTBDTO extends SynStockTransDetailDTO{
    private Double assetQuantity;
    private Double consQantity;
    private Double remainQuantity;
    private Long merEntityId;
    private String serial;
    private String constructionCode;
    private Long constructionMerchadiseId;
    private Integer numberXuat;
    private Integer numberThuhoi;
    private Integer numberSuDung;
    private Long employ;
    private Long constructionId;
    private Integer quantity;
    private Double slcl;
    private String realTransDate;
    private Long detailSerial;
    private Long workItemId;
    private String workItemName;
    private String totalItemDetail;
    private List<ConstructionAcceptanceCertItemTBDTO> listTBBDetail;

    public Double getAssetQuantity() {
        return assetQuantity;
    }

    public void setAssetQuantity(Double assetQuantity) {
        this.assetQuantity = assetQuantity;
    }

    public Double getConsQantity() {
        return consQantity;
    }

    public void setConsQantity(Double consQantity) {
        this.consQantity = consQantity;
    }

    public Double getRemainQuantity() {
        return remainQuantity;
    }

    public void setRemainQuantity(Double remainQuantity) {
        this.remainQuantity = remainQuantity;
    }

    public Long getMerEntityId() {
        return merEntityId;
    }

    public void setMerEntityId(Long merEntityId) {
        this.merEntityId = merEntityId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getConstructionCode() {
        return constructionCode;
    }

    public void setConstructionCode(String constructionCode) {
        this.constructionCode = constructionCode;
    }

    public Long getConstructionMerchadiseId() {
        return constructionMerchadiseId;
    }

    public void setConstructionMerchadiseId(Long constructionMerchadiseId) {
        this.constructionMerchadiseId = constructionMerchadiseId;
    }

    public Integer getNumberXuat() {
        return numberXuat;
    }

    public void setNumberXuat(Integer numberXuat) {
        this.numberXuat = numberXuat;
    }

    public Integer getNumberThuhoi() {
        return numberThuhoi;
    }

    public void setNumberThuhoi(Integer numberThuhoi) {
        this.numberThuhoi = numberThuhoi;
    }

    public Integer getNumberSuDung() {
        return numberSuDung;
    }

    public void setNumberSuDung(Integer numberSuDung) {
        this.numberSuDung = numberSuDung;
    }

    public Long getEmploy() {
        return employ;
    }

    public void setEmploy(Long employ) {
        this.employ = employ;
    }

    public Long getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(Long constructionId) {
        this.constructionId = constructionId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSlcl() {
        return slcl;
    }

    public void setSlcl(Double slcl) {
        this.slcl = slcl;
    }

    public String getRealTransDate() {
        return realTransDate;
    }

    public void setRealTransDate(String realTransDate) {
        this.realTransDate = realTransDate;
    }

    public Long getDetailSerial() {
        return detailSerial;
    }

    public void setDetailSerial(Long detailSerial) {
        this.detailSerial = detailSerial;
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

    public String getTotalItemDetail() {
        return totalItemDetail;
    }

    public void setTotalItemDetail(String totalItemDetail) {
        this.totalItemDetail = totalItemDetail;
    }

    public List<ConstructionAcceptanceCertItemTBDTO> getListTBBDetail() {
        return listTBBDetail;
    }

    public void setListTBBDetail(List<ConstructionAcceptanceCertItemTBDTO> listTBBDetail) {
        this.listTBBDetail = listTBBDetail;
    }
}
