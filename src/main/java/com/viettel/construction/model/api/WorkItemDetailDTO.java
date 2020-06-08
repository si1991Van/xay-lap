package com.viettel.construction.model.api;

import java.io.Serializable;

/**
 * Created by hoang on 15/03/2018.
 */

public class WorkItemDetailDTO implements Serializable{

    private String defaultSortField;
    private String constructionCode;
    private String startDate;
    private String endDate;
    private String status;
    private String name;
    private String id;
    private String page;
    private String pageSize;
    private String total;
    private String keySearch;
    private String text;
    private String totalRecord;
    private String workItemId;
    private String constructionId;
    private String catWorkItemTypeId;
    private String code;
    private String isInternal;
    private String constructorId;
    private String supervisorId;
    private String startingDate;
    private String completeDate;
    private double quantity;
    private String approveQuantity;
    private String approveState;
    private String approveDate;
    private String approveUserId;
    private String approveDescription;
    private String createdDate;
    private String createdUserId;
    private String createdGroupId;
    private String updatedDate;
    private String updatedUserId;
    private String updatedGroupId;
    private String constructorName;
    private String constructorName1;
    private String constructorName2;
    private String supervisorName;
    private String performerName;
    private String sourceType;
    private String deployType;
    private String catProvinceCode;
    private String catWorkItemType;
    private String price;
    private String workDay;
    private String catStationCode;
    private String sysGroupName;
    private String catConstructionTypeId;
    private String performerId;
    private String sysGroupId;
    private String month;
    private String year;
    private String catstationCode;
    private String sysGroupName1;
    private String monthList;
    private String provinceCode;
    private String totalPrice;
    private String catWorkItemTypeName;
    private String cntContract;
    private String workItemTypeList;
    private String fwmodelId;
    private String description;

   private String endingDate;

    public WorkItemDetailDTO() {
    }

    public WorkItemDetailDTO(String defaultSortField) {
        this.defaultSortField = defaultSortField;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getDefaultSortField() {
        return defaultSortField;
    }

    public void setDefaultSortField(String defaultSortField) {
        this.defaultSortField = defaultSortField;
    }

    public String getConstructionCode() {
        return constructionCode;
    }

    public void setConstructionCode(String constructionCode) {
        this.constructionCode = constructionCode;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getKeySearch() {
        return keySearch;
    }

    public void setKeySearch(String keySearch) {
        this.keySearch = keySearch;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(String totalRecord) {
        this.totalRecord = totalRecord;
    }

    public String getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
    }

    public String getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(String constructionId) {
        this.constructionId = constructionId;
    }

    public String getCatWorkItemTypeId() {
        return catWorkItemTypeId;
    }

    public void setCatWorkItemTypeId(String catWorkItemTypeId) {
        this.catWorkItemTypeId = catWorkItemTypeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIsInternal() {
        return isInternal;
    }

    public void setIsInternal(String isInternal) {
        this.isInternal = isInternal;
    }

    public String getConstructorId() {
        return constructorId;
    }

    public void setConstructorId(String constructorId) {
        this.constructorId = constructorId;
    }

    public String getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }



    public String getApproveQuantity() {
        return approveQuantity;
    }

    public void setApproveQuantity(String approveQuantity) {
        this.approveQuantity = approveQuantity;
    }

    public String getApproveState() {
        return approveState;
    }

    public void setApproveState(String approveState) {
        this.approveState = approveState;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public String getApproveUserId() {
        return approveUserId;
    }

    public void setApproveUserId(String approveUserId) {
        this.approveUserId = approveUserId;
    }

    public String getApproveDescription() {
        return approveDescription;
    }

    public void setApproveDescription(String approveDescription) {
        this.approveDescription = approveDescription;
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

    public String getCreatedGroupId() {
        return createdGroupId;
    }

    public void setCreatedGroupId(String createdGroupId) {
        this.createdGroupId = createdGroupId;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(String updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public String getUpdatedGroupId() {
        return updatedGroupId;
    }

    public void setUpdatedGroupId(String updatedGroupId) {
        this.updatedGroupId = updatedGroupId;
    }

    public String getConstructorName() {
        return constructorName;
    }

    public void setConstructorName(String constructorName) {
        this.constructorName = constructorName;
    }

    public String getConstructorName1() {
        return constructorName1;
    }

    public void setConstructorName1(String constructorName1) {
        this.constructorName1 = constructorName1;
    }

    public String getConstructorName2() {
        return constructorName2;
    }

    public void setConstructorName2(String constructorName2) {
        this.constructorName2 = constructorName2;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getPerformerName() {
        return performerName;
    }

    public void setPerformerName(String performerName) {
        this.performerName = performerName;
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

    public String getCatProvinceCode() {
        return catProvinceCode;
    }

    public void setCatProvinceCode(String catProvinceCode) {
        this.catProvinceCode = catProvinceCode;
    }

    public String getCatWorkItemType() {
        return catWorkItemType;
    }

    public void setCatWorkItemType(String catWorkItemType) {
        this.catWorkItemType = catWorkItemType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public String getCatStationCode() {
        return catStationCode;
    }

    public void setCatStationCode(String catStationCode) {
        this.catStationCode = catStationCode;
    }

    public String getSysGroupName() {
        return sysGroupName;
    }

    public void setSysGroupName(String sysGroupName) {
        this.sysGroupName = sysGroupName;
    }

    public String getCatConstructionTypeId() {
        return catConstructionTypeId;
    }

    public void setCatConstructionTypeId(String catConstructionTypeId) {
        this.catConstructionTypeId = catConstructionTypeId;
    }

    public String getPerformerId() {
        return performerId;
    }

    public void setPerformerId(String performerId) {
        this.performerId = performerId;
    }

    public String getSysGroupId() {
        return sysGroupId;
    }

    public void setSysGroupId(String sysGroupId) {
        this.sysGroupId = sysGroupId;
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

    public String getCatstationCode() {
        return catstationCode;
    }

    public void setCatstationCode(String catstationCode) {
        this.catstationCode = catstationCode;
    }

    public String getSysGroupName1() {
        return sysGroupName1;
    }

    public void setSysGroupName1(String sysGroupName1) {
        this.sysGroupName1 = sysGroupName1;
    }

    public String getMonthList() {
        return monthList;
    }

    public void setMonthList(String monthList) {
        this.monthList = monthList;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCatWorkItemTypeName() {
        return catWorkItemTypeName;
    }

    public void setCatWorkItemTypeName(String catWorkItemTypeName) {
        this.catWorkItemTypeName = catWorkItemTypeName;
    }

    public String getCntContract() {
        return cntContract;
    }

    public void setCntContract(String cntContract) {
        this.cntContract = cntContract;
    }

    public String getWorkItemTypeList() {
        return workItemTypeList;
    }

    public void setWorkItemTypeList(String workItemTypeList) {
        this.workItemTypeList = workItemTypeList;
    }

    public String getFwmodelId() {
        return fwmodelId;
    }

    public void setFwmodelId(String fwmodelId) {
        this.fwmodelId = fwmodelId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
