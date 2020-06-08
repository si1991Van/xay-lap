package com.viettel.construction.model.api.history;

public class StTransactionDetailDTO extends StTransactionDTO {
    private long stockTransDetailId;
    private String detailGoodName;
    private String detailGoodCode;
    private String detailQuantity;
    private String detailSerial;
    private String detailCntContractCode;
    private String detailStockName;
    private String detailPartNumber;
    private String detailMerManufacturer;
    private String detailProducingCountryName;

    public long getStockTransDetailDTO() {
        return stockTransDetailId;
    }

    public void setStockTransDetailDTO(long stockTransDetailDTO) {
        this.stockTransDetailId = stockTransDetailDTO;
    }

    public String getDetailGoodName() {
        return detailGoodName;
    }

    public void setDetailGoodName(String detailGoodName) {
        this.detailGoodName = detailGoodName;
    }

    public String getDetailGoodCode() {
        return detailGoodCode;
    }

    public void setDetailGoodCode(String detailGoodCode) {
        this.detailGoodCode = detailGoodCode;
    }

    public String getDetailQuantity() {
        return detailQuantity;
    }

    public void setDetailQuantity(String detailQuantity) {
        this.detailQuantity = detailQuantity;
    }

    public String getDetailSerial() {
        return detailSerial;
    }

    public void setDetailSerial(String detailSerial) {
        this.detailSerial = detailSerial;
    }

    public String getDetailCntContractCode() {
        return detailCntContractCode;
    }

    public void setDetailCntContractCode(String detailCntContractCode) {
        this.detailCntContractCode = detailCntContractCode;
    }

    public String getDetailStockName() {
        return detailStockName;
    }

    public void setDetailStockName(String detailStockName) {
        this.detailStockName = detailStockName;
    }

    public String getDetailPartNumber() {
        return detailPartNumber;
    }

    public void setDetailPartNumber(String detailPartNumber) {
        this.detailPartNumber = detailPartNumber;
    }

    public String getDetailMerManufacturer() {
        return detailMerManufacturer;
    }

    public void setDetailMerManufacturer(String detailMerManufacturer) {
        this.detailMerManufacturer = detailMerManufacturer;
    }

    public String getDetailProducingCountryName() {
        return detailProducingCountryName;
    }

    public void setDetailProducingCountryName(String detailProducingCountryName) {
        this.detailProducingCountryName = detailProducingCountryName;
    }
}
