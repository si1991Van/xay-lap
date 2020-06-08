package com.viettel.construction.model.api;

import java.io.Serializable;

public class ConstructionMerchandiseDTO implements Serializable{
    private Long constructionMerchandiseId;
    private String goodsName;
    private String goodsCode;
    private String goodsUnitName;
    private String type;
    private Long goodsId;
    private Long merEntityId;
    private Double quantity;
    private Double remainCount;
    private Long constructionId;
    private String goodsIsSerial;
    private String serial;
    private String errorFilePath;
    private Long workItemId;
    private Double slConLai;

    public Long getConstructionMerchandiseId() {
        return constructionMerchandiseId;
    }

    public void setConstructionMerchandiseId(Long constructionMerchandiseId) {
        this.constructionMerchandiseId = constructionMerchandiseId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsUnitName() {
        return goodsUnitName;
    }

    public void setGoodsUnitName(String goodsUnitName) {
        this.goodsUnitName = goodsUnitName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getMerEntityId() {
        return merEntityId;
    }

    public void setMerEntityId(Long merEntityId) {
        this.merEntityId = merEntityId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(Double remainCount) {
        this.remainCount = remainCount;
    }

    public Long getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(Long constructionId) {
        this.constructionId = constructionId;
    }

    public String getGoodsIsSerial() {
        return goodsIsSerial;
    }

    public void setGoodsIsSerial(String goodsIsSerial) {
        this.goodsIsSerial = goodsIsSerial;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getErrorFilePath() {
        return errorFilePath;
    }

    public void setErrorFilePath(String errorFilePath) {
        this.errorFilePath = errorFilePath;
    }

    public Long getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(Long workItemId) {
        this.workItemId = workItemId;
    }

    public Double getSlConLai() {
        return slConLai;
    }

    public void setSlConLai(Double slConLai) {
        this.slConLai = slConLai;
    }
}
