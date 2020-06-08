package com.viettel.construction.model.api.history;

import java.io.Serializable;

public class StTransactionDTO implements Serializable{
    private long stTransactionId;
    private String confirmDate;
    private String description;
    private String updatedDate;
    private long updatedUserId;
    private long oldLastShipperId;
    private long newLastShipperId;
    private long stockTransId;
    private String type;
    private String confirm;
    private String createdDate;
    private String createdUserId;
    private String stockTransCode;
    private String synStockTransId;
    private String stockTransConstructionCode;
    private String stockTransCreatedByName;
    private String stockTransCreatedDate;
    private String synStockTransCode;
    private String synStockTransConstructionCode;
    private String synStockTransName;
    private String synStockTransCreatedByName;
    private String synStockTransCreatedDate;
    private String constructionCode;
    private String goodName;
    private String goodUnitName;
    private Integer amountReal;
    private String goodCode;

    public long getStTransactionId() {
        return stTransactionId;
    }

    public void setStTransactionId(long stTransactionId) {
        stTransactionId = stTransactionId;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public long getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(long updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public long getOldLastShipperId() {
        return oldLastShipperId;
    }

    public void setOldLastShipperId(long oldLastShipperId) {
        this.oldLastShipperId = oldLastShipperId;
    }

    public long getNewLastShipperId() {
        return newLastShipperId;
    }

    public void setNewLastShipperId(long newLastShipperId) {
        this.newLastShipperId = newLastShipperId;
    }

    public long getStockTransId() {
        return stockTransId;
    }

    public void setStockTransId(long stockTransId) {
        this.stockTransId = stockTransId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getStockTransCode() {
        return stockTransCode;
    }

    public void setStockTransCode(String stockTransCode) {
        this.stockTransCode = stockTransCode;
    }

    public String getSynStockTransId() {
        return synStockTransId;
    }

    public void setSynStockTransId(String synStockTransId) {
        this.synStockTransId = synStockTransId;
    }

    public String getStockTransConstructionCode() {
        return stockTransConstructionCode;
    }

    public void setStockTransConstructionCode(String stockTransConstructionCode) {
        this.stockTransConstructionCode = stockTransConstructionCode;
    }

    public String getStockTransCreatedByName() {
        return stockTransCreatedByName;
    }

    public void setStockTransCreatedByName(String stockTransCreatedByName) {
        this.stockTransCreatedByName = stockTransCreatedByName;
    }

    public String getStockTransCreatedDate() {
        return stockTransCreatedDate;
    }

    public void setStockTransCreatedDate(String stockTransCreatedDate) {
        this.stockTransCreatedDate = stockTransCreatedDate;
    }

    public String getSynStockTransCode() {
        return synStockTransCode;
    }

    public void setSynStockTransCode(String synStockTransCode) {
        this.synStockTransCode = synStockTransCode;
    }

    public String getSynStockTransConstructionCode() {
        return synStockTransConstructionCode;
    }

    public void setSynStockTransConstructionCode(String synStockTransConstructionCode) {
        this.synStockTransConstructionCode = synStockTransConstructionCode;
    }

    public String getSynStockTransName() {
        return synStockTransName;
    }

    public void setSynStockTransName(String synStockTransName) {
        this.synStockTransName = synStockTransName;
    }

    public String getSynStockTransCreatedByName() {
        return synStockTransCreatedByName;
    }

    public void setSynStockTransCreatedByName(String synStockTransCreatedByName) {
        this.synStockTransCreatedByName = synStockTransCreatedByName;
    }

    public String getSynStockTransCreatedDate() {
        return synStockTransCreatedDate;
    }

    public void setSynStockTransCreatedDate(String synStockTransCreatedDate) {
        this.synStockTransCreatedDate = synStockTransCreatedDate;
    }

    public String getConstructionCode() {
        return constructionCode;
    }

    public void setConstructionCode(String constructionCode) {
        this.constructionCode = constructionCode;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodUnitName() {
        return goodUnitName;
    }

    public void setGoodUnitName(String goodUnitName) {
        this.goodUnitName = goodUnitName;
    }

    public Integer getAmountReal() {
        return amountReal;
    }

    public void setAmountReal(Integer amountReal) {
        this.amountReal = amountReal;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }
}
