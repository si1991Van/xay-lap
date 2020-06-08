package com.viettel.construction.model.api.version;

public class AppParamDTO {

    private String code;
    private String name;
    private String partOrder;
    private String parType;
    private String status;
    private Long appParamId;
    private String amount;
    private String confirm;//0: Thêm mới, 1: Đã phê duyệt, 2: Từ chối
    private Long constructionTaskDailyId;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartOrder() {
        return partOrder;
    }

    public void setPartOrder(String partOrder) {
        this.partOrder = partOrder;
    }

    public String getParType() {
        return parType;
    }

    public void setParType(String partType) {
        this.parType = partType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAppParamId() {
        return appParamId;
    }

    public void setAppParamId(Long appParamId) {
        this.appParamId = appParamId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public Long getConstructionTaskDailyId() {
        return constructionTaskDailyId;
    }

    public void setConstructionTaskDailyId(Long constructionTaskDailyId) {
        this.constructionTaskDailyId = constructionTaskDailyId;
    }
}
