package com.viettel.construction.model.api;

import java.io.Serializable;

/**
 * Created by manro on 3/9/2018.
 */

public class ConstructionTaskDTO implements Serializable {
    private String completePercent;
    private String description;
    private String status;//1: chua thuc hien, 2: Dang thuc hien, 3: Tam dung, 4: hoan thanh
    private String sourceType;
    private String deployType;
    private double vat;
    private String type;
    private long detailMonthPlanId;
    private String createDate;
    private long createUserId;
    private long createGroupId;
    private long checkEntangle;//0: Mac dinh la ko tao vuong, 1: la tao vuong
    private String updateDate;
    private long updateUserId;
    private long updateGroupId;
    private String completeState;
    private long performerWorkItemId;
    private Double supervisorId;
    private Double directorId;
    private long performerId;
    private Double quantity;
    private long constructionTaskId;
    private long sysGroupId;
    private String constructionCode;
    private String workItemName;
    private String month;
    private String year;
    private String taskName;
    private String reasonStop;
    private String startDate;
    private String endDate;
    private String baseLineStartDate;
    private String baseLineEndDate;
    private long constructionId;
    private long workItemId;
    private long catTaskId;
    private long levelId;
    private long parentId;
    private String path;
    private String performerName;
    private String quantityByDate;
    private String taskOrder;
    private Double quantityRevenue;
    private Double amount;
    private Double price;
    private long checkImage;

    public long getCheckImage() {
        return checkImage;
    }

    public void setCheckImage(long checkImage) {
        this.checkImage = checkImage;
    }

    public long getCatConstructionTypeId() {
        return catConstructionTypeId;
    }

    public void setCatConstructionTypeId(long catConstructionTypeId) {
        this.catConstructionTypeId = catConstructionTypeId;
    }

    private long catConstructionTypeId;

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    private String confirm;

    private String obstructedState;//0 là vướng, 1,2 là không vướng

    private String startingDateTK;

    private String handoverDateBuildBGMB;

    private String checkBGMB;

    public Double getTotalAmountChest() {
        return totalAmountChest;
    }

    public void setTotalAmountChest(Double totalAmountChest) {
        this.totalAmountChest = totalAmountChest;
    }

    public Double getPriceChest() {
        return priceChest;
    }

    public void setPriceChest(Double priceChest) {
        this.priceChest = priceChest;
    }

    public Double getTotalAmountGate() {
        return totalAmountGate;
    }

    public void setTotalAmountGate(Double totalAmountGate) {
        this.totalAmountGate = totalAmountGate;
    }

    public Double getPriceGate() {
        return priceGate;
    }

    public void setPriceGate(Double priceGate) {
        this.priceGate = priceGate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    //add to impliment Gpon
    private Double totalAmountChest;
    private Double priceChest;
    private Double totalAmountGate;
    private Double priceGate;
    private Double totalAmount;
    private Double amountTaskDaily;

    public Double getAmountTaskDaily() {
        return amountTaskDaily;
    }

    public void setAmountTaskDaily(Double amountTaskDaily) {
        this.amountTaskDaily = amountTaskDaily;
    }

    public String getStartingDateTK() {
        return startingDateTK;
    }

    public void setStartingDateTK(String startingDateTK) {
        this.startingDateTK = startingDateTK;
    }

    public String getHandoverDateBuildBGMB() {
        return handoverDateBuildBGMB;
    }

    public void setHandoverDateBuildBGMB(String handoverDateBuildBGMB) {
        this.handoverDateBuildBGMB = handoverDateBuildBGMB;
    }

    public String getCheckBGMB() {
        return checkBGMB;
    }

    public void setCheckBGMB(String checkBGMB) {
        this.checkBGMB = checkBGMB;
    }

    public ConstructionTaskDTO(){}

    public ConstructionTaskDTO(long catConstructionTypeId,String confirm,Double amountTaskDaily, String completePercent, String description, String status, String sourceType, String deployType, double vat, String type, long detailMonthPlanId, String createDate, long createUserId, long createGroupId, long checkEntangle, String updateDate, long updateUserId, long updateGroupId, String completeState, long performerWorkItemId, Double supervisorId, Double directorId, long performerId, Double quantity, long constructionTaskId, long sysGroupId, String constructionCode, String workItemName, String month, String year, String taskName, String reasonStop, String startDate, String endDate, String baseLineStartDate, String baseLineEndDate, long constructionId, long workItemId, long catTaskId, long levelId, long parentId, String path, String performerName, String quantityByDate, String taskOrder, Double quantityRevenue, Double amount, Double price, String obstructedState, String startingDateTK, String handoverDateBuildBGMB, String checkBGMB, Double totalAmountChest, Double priceChest, Double totalAmountGate, Double priceGate, Double totalAmount) {
        this.catConstructionTypeId = catConstructionTypeId;
        this.confirm = confirm;
        this.amountTaskDaily = amountTaskDaily;
        this.completePercent = completePercent;
        this.description = description;
        this.status = status;
        this.sourceType = sourceType;
        this.deployType = deployType;
        this.vat = vat;
        this.type = type;
        this.detailMonthPlanId = detailMonthPlanId;
        this.createDate = createDate;
        this.createUserId = createUserId;
        this.createGroupId = createGroupId;
        this.checkEntangle = checkEntangle;
        this.updateDate = updateDate;
        this.updateUserId = updateUserId;
        this.updateGroupId = updateGroupId;
        this.completeState = completeState;
        this.performerWorkItemId = performerWorkItemId;
        this.supervisorId = supervisorId;
        this.directorId = directorId;
        this.performerId = performerId;
        this.quantity = quantity;
        this.constructionTaskId = constructionTaskId;
        this.sysGroupId = sysGroupId;
        this.constructionCode = constructionCode;
        this.workItemName = workItemName;
        this.month = month;
        this.year = year;
        this.taskName = taskName;
        this.reasonStop = reasonStop;
        this.startDate = startDate;
        this.endDate = endDate;
        this.baseLineStartDate = baseLineStartDate;
        this.baseLineEndDate = baseLineEndDate;
        this.constructionId = constructionId;
        this.workItemId = workItemId;
        this.catTaskId = catTaskId;
        this.levelId = levelId;
        this.parentId = parentId;
        this.path = path;
        this.performerName = performerName;
        this.quantityByDate = quantityByDate;
        this.taskOrder = taskOrder;
        this.quantityRevenue = quantityRevenue;
        this.amount = amount;
        this.price = price;
        this.obstructedState = obstructedState;
        this.startingDateTK = startingDateTK;
        this.handoverDateBuildBGMB = handoverDateBuildBGMB;
        this.checkBGMB = checkBGMB;
        this.totalAmountChest = totalAmountChest;
        this.priceChest = priceChest;
        this.totalAmountGate = totalAmountGate;
        this.priceGate = priceGate;
        this.totalAmount = totalAmount;
    }

    public String getObstructedState() {
        return obstructedState;
    }

    public void setObstructedState(String obstructedState) {
        this.obstructedState = obstructedState;
    }

    public long getCheckEntangle() {
        return checkEntangle;
    }

    public void setCheckEntangle(long checkEntangle) {
        this.checkEntangle = checkEntangle;
    }

    public String getPerformerName() {
        return performerName;
    }

    public void setPerformerName(String performerName) {
        this.performerName = performerName;
    }

    public ConstructionTaskDTO(long constructionTaskId) {
        this.constructionTaskId = constructionTaskId;
    }

    public ConstructionTaskDTO(String completePercent, String description, long performerId, long constructionTaskId, String taskName, String startDate, String endDate, long catTaskId) {
        this.completePercent = completePercent;
        this.description = description;
        this.performerId = performerId;
        this.constructionTaskId = constructionTaskId;
        this.taskName = taskName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.catTaskId = catTaskId;
    }

    public ConstructionTaskDTO(String completePercent, String description, long performerId, long constructionTaskId, String startDate, String endDate) {
        this.completePercent = completePercent;
        this.description = description;
        this.performerId = performerId;
        this.constructionTaskId = constructionTaskId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ConstructionTaskDTO(String completePercent, String description, long performerId, long constructionTaskId,long workItemId, long catTaskId ,String startDate, String endDate) {
        this.completePercent = completePercent;
        this.description = description;
        this.performerId = performerId;
        this.constructionTaskId = constructionTaskId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.workItemId = workItemId;
        this.catTaskId = catTaskId;
    }

    public ConstructionTaskDTO(String completePercent, String description, long performerId,
                               long constructionTaskId, String reasonStop,
                               String startDate, String endDate, long workItemId, long catTaskId) {
        this.completePercent = completePercent;
        this.description = description;
        this.performerId = performerId;
        this.constructionTaskId = constructionTaskId;
        this.reasonStop = reasonStop;
        this.startDate = startDate;
        this.endDate = endDate;
        this.workItemId = workItemId;
        this.catTaskId = catTaskId;
    }

    //    public ConstructionTaskDTO(String completePercent, String description, long performerId, long constructionTaskId, String month, String year) {
//        this.completePercent = completePercent;
//        this.description = description;
//        this.performerId = performerId;
//        this.constructionTaskId = constructionTaskId;
//        this.month = month;
//        this.year = year;
//    }

    public ConstructionTaskDTO(String completePercent, String description, String status, String sourceType, String deployType, double vat, String type, long detailMonthPlanId, String createDate, long createUserId, long createGroupId, String updateDate, long updateUserId, long updateGroupId, String completeState, long performerWorkItemId, double supervisorId, double directorId, long performerId, double quantity, long constructionTaskId, long sysGroupId, String month, String year, String taskName, String reasonStop, String startDate, String endDate, String baseLineStartDate, String baseLineEndDate, long constructionId, long workItemId, long catTaskId, long levelId, long parentId, String path) {
        this.completePercent = completePercent;
        this.description = description;
        this.status = status;
        this.sourceType = sourceType;
        this.deployType = deployType;
        this.vat = vat;
        this.type = type;
        this.detailMonthPlanId = detailMonthPlanId;
        this.createDate = createDate;
        this.createUserId = createUserId;
        this.createGroupId = createGroupId;
        this.updateDate = updateDate;
        this.updateUserId = updateUserId;
        this.updateGroupId = updateGroupId;
        this.completeState = completeState;
        this.performerWorkItemId = performerWorkItemId;
        this.supervisorId = supervisorId;
        this.directorId = directorId;
        this.performerId = performerId;
        this.quantity = quantity;
        this.constructionTaskId = constructionTaskId;
        this.sysGroupId = sysGroupId;
        this.month = month;
        this.year = year;
        this.taskName = taskName;
        this.reasonStop = reasonStop;
        this.startDate = startDate;
        this.endDate = endDate;
        this.baseLineStartDate = baseLineStartDate;
        this.baseLineEndDate = baseLineEndDate;
        this.constructionId = constructionId;
        this.workItemId = workItemId;
        this.catTaskId = catTaskId;
        this.levelId = levelId;
        this.parentId = parentId;
        this.path = path;
    }

    public ConstructionTaskDTO(String completePercent, String description, String status, String sourceType, String deployType, double vat, String type, long detailMonthPlanId, String createDate, long createUserId, long createGroupId, String updateDate, long updateUserId, long updateGroupId, String completeState, long performerWorkItemId, double supervisorId, double directorId, long performerId, double quantity, long constructionTaskId, long sysGroupId, String constructionCode, String workItemName, String month, String year, String taskName, String reasonStop, String startDate, String endDate, String baseLineStartDate, String baseLineEndDate, long constructionId, long workItemId, long catTaskId, long levelId, long parentId, String path) {
        this.completePercent = completePercent;
        this.description = description;
        this.status = status;
        this.sourceType = sourceType;
        this.deployType = deployType;
        this.vat = vat;
        this.type = type;
        this.detailMonthPlanId = detailMonthPlanId;
        this.createDate = createDate;
        this.createUserId = createUserId;
        this.createGroupId = createGroupId;
        this.updateDate = updateDate;
        this.updateUserId = updateUserId;
        this.updateGroupId = updateGroupId;
        this.completeState = completeState;
        this.performerWorkItemId = performerWorkItemId;
        this.supervisorId = supervisorId;
        this.directorId = directorId;
        this.performerId = performerId;
        this.quantity = quantity;
        this.constructionTaskId = constructionTaskId;
        this.sysGroupId = sysGroupId;
        this.constructionCode = constructionCode;
        this.workItemName = workItemName;
        this.month = month;
        this.year = year;
        this.taskName = taskName;
        this.reasonStop = reasonStop;
        this.startDate = startDate;
        this.endDate = endDate;
        this.baseLineStartDate = baseLineStartDate;
        this.baseLineEndDate = baseLineEndDate;
        this.constructionId = constructionId;
        this.workItemId = workItemId;
        this.catTaskId = catTaskId;
        this.levelId = levelId;
        this.parentId = parentId;
        this.path = path;
    }

    public String getCompletePercent() {
        return completePercent;
    }

    public void setCompletePercent(String completePercent) {
        this.completePercent = completePercent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getDeployType() {
        return deployType;
    }

    public void setDeployType(String deployType) {
        this.deployType = deployType;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getDetailMonthPlanId() {
        return detailMonthPlanId;
    }

    public void setDetailMonthPlanId(long detailMonthPlanId) {
        this.detailMonthPlanId = detailMonthPlanId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
    }

    public long getCreateGroupId() {
        return createGroupId;
    }

    public void setCreateGroupId(long createGroupId) {
        this.createGroupId = createGroupId;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public long getUpdateGroupId() {
        return updateGroupId;
    }

    public void setUpdateGroupId(long updateGroupId) {
        this.updateGroupId = updateGroupId;
    }

    public String getCompleteState() {
        return completeState;
    }

    public void setCompleteState(String completeState) {
        this.completeState = completeState;
    }

    public long getPerformerWorkItemId() {
        return performerWorkItemId;
    }

    public void setPerformerWorkItemId(long performerWorkItemId) {
        this.performerWorkItemId = performerWorkItemId;
    }

    public double getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(double supervisorId) {
        this.supervisorId = supervisorId;
    }

    public double getDirectorId() {
        return directorId;
    }

    public void setDirectorId(double directorId) {
        this.directorId = directorId;
    }

    public long getPerformerId() {
        return performerId;
    }

    public void setPerformerId(long performerId) {
        this.performerId = performerId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public long getConstructionTaskId() {
        return constructionTaskId;
    }

    public void setConstructionTaskId(long constructionTaskId) {
        this.constructionTaskId = constructionTaskId;
    }

    public long getSysGroupId() {
        return sysGroupId;
    }

    public void setSysGroupId(long sysGroupId) {
        this.sysGroupId = sysGroupId;
    }

    public String getConstructionCode() {
        return constructionCode;
    }

    public void setConstructionCode(String constructionCode) {
        this.constructionCode = constructionCode;
    }

    public String getWorkItemName() {
        return workItemName;
    }

    public void setWorkItemName(String workItemName) {
        this.workItemName = workItemName;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getReasonStop() {
        return reasonStop;
    }

    public void setReasonStop(String reasonStop) {
        this.reasonStop = reasonStop;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBaseLineStartDate() {
        return baseLineStartDate;
    }

    public void setBaseLineStartDate(String baseLineStartDate) {
        this.baseLineStartDate = baseLineStartDate;
    }

    public String getBaseLineEndDate() {
        return baseLineEndDate;
    }

    public void setBaseLineEndDate(String baseLineEndDate) {
        this.baseLineEndDate = baseLineEndDate;
    }

    public long getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(long constructionId) {
        this.constructionId = constructionId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(long workItemId) {
        this.workItemId = workItemId;
    }

    public long getCatTaskId() {
        return catTaskId;
    }

    public void setCatTaskId(long catTaskId) {
        this.catTaskId = catTaskId;
    }

    public long getLevelId() {
        return levelId;
    }

    public void setLevelId(long levelId) {
        this.levelId = levelId;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getQuantityByDate() {
        return quantityByDate;
    }

    public void setQuantityByDate(String quantityByDate) {
        this.quantityByDate = quantityByDate;
    }

    public String getTaskOrder() {
        return taskOrder;
    }

    public void setTaskOrder(String taskOrder) {
        this.taskOrder = taskOrder;
    }

    public void setSupervisorId(Double supervisorId) {
        this.supervisorId = supervisorId;
    }

    public void setDirectorId(Double directorId) {
        this.directorId = directorId;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getQuantityRevenue() {
        return quantityRevenue;
    }

    public void setQuantityRevenue(Double quantityRevenue) {
        this.quantityRevenue = quantityRevenue;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
