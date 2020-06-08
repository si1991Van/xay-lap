package com.viettel.construction.model.api;

public class MerEntityDTO {
    private String goodsName;
    private String goodsCode;
    private String serial;
    private String quantity;
    private String cntConstractCode;
    private Long stockId;
    private Long partNumber;
    private String manufactureName;
    private String productionCountryName;
    private String stockName;
    private String stockType;
    private String constructionCode;
    private String goodsIsSerial;
    private Long numbeRepository;

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getConstructionCode() {
        return constructionCode;
    }

    public void setConstructionCode(String constructionCode) {
        this.constructionCode = constructionCode;
    }

    public String getGoodsIsSerial() {
        return goodsIsSerial;
    }

    public void setGoodsIsSerial(String goodsIsSerial) {
        this.goodsIsSerial = goodsIsSerial;
    }

    public Long getNumbeRepository() {
        return numbeRepository;
    }

    public void setNumbeRepository(Long numbeRepository) {
        this.numbeRepository = numbeRepository;
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

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCntConstractCode() {
        return cntConstractCode;
    }

    public void setCntConstractCode(String cntConstractCode) {
        this.cntConstractCode = cntConstractCode;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Long getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(Long partNumber) {
        this.partNumber = partNumber;
    }

    public String getManufactureName() {
        return manufactureName;
    }

    public void setManufactureName(String manufactureName) {
        this.manufactureName = manufactureName;
    }

    public String getProductionCountryName() {
        return productionCountryName;
    }

    public void setProductionCountryName(String productionCountryName) {
        this.productionCountryName = productionCountryName;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
}
