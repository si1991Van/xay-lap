package com.viettel.construction.model.api;

import java.io.Serializable;

public class SynStockTransDetailDTO implements Serializable{
    private Long synStockTransDetailId;
    private Long orderId;
    private String goodsType;
    private String goodsTypeName;
    private Long goodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsIsSerial;
    private String goodState;
    private String goodsStateName;
    private String goodsUnitName;
    private Long goodsUnitId;
    private Double amountOrder;
    private Double amountReal;
    private Double totalPrice;
    private Double amount;
    private Long stockTransId;
    private Long synStockTransId;
    private Long idTable;
    private Long typeExport;

    public Long getSynStockTransDetailId() {
        return synStockTransDetailId;
    }

    public void setSynStockTransDetailId(Long synStockTransDetailId) {
        this.synStockTransDetailId = synStockTransDetailId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsIsSerial() {
        return goodsIsSerial;
    }

    public void setGoodsIsSerial(String goodsIsSerial) {
        this.goodsIsSerial = goodsIsSerial;
    }

    public String getGoodState() {
        return goodState;
    }

    public void setGoodState(String goodState) {
        this.goodState = goodState;
    }

    public String getGoodsStateName() {
        return goodsStateName;
    }

    public void setGoodsStateName(String goodsStateName) {
        this.goodsStateName = goodsStateName;
    }

    public String getGoodsUnitName() {
        return goodsUnitName;
    }

    public void setGoodsUnitName(String goodsUnitName) {
        this.goodsUnitName = goodsUnitName;
    }

    public Long getGoodsUnitId() {
        return goodsUnitId;
    }

    public void setGoodsUnitId(Long goodsUnitId) {
        this.goodsUnitId = goodsUnitId;
    }

    public Double getAmountOrder() {
        return amountOrder;
    }

    public void setAmountOrder(Double amountOrder) {
        this.amountOrder = amountOrder;
    }

    public Double getAmountReal() {
        return amountReal;
    }

    public void setAmountReal(Double amountReal) {
        this.amountReal = amountReal;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getStockTransId() {
        return stockTransId;
    }

    public void setStockTransId(Long stockTransId) {
        this.stockTransId = stockTransId;
    }

    public Long getSynStockTransId() {
        return synStockTransId;
    }

    public void setSynStockTransId(Long synStockTransId) {
        this.synStockTransId = synStockTransId;
    }

    public Long getIdTable() {
        return idTable;
    }

    public void setIdTable(Long idTable) {
        this.idTable = idTable;
    }

    public Long getTypeExport() {
        return typeExport;
    }

    public void setTypeExport(Long typeExport) {
        this.typeExport = typeExport;
    }
}
