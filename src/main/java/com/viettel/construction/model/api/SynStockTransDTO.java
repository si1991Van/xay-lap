package com.viettel.construction.model.api;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SynStockTransDTO implements Serializable {

    @SerializedName("synStockTransId")
    @Expose
    public Long synStockTransId;
    @SerializedName("orderId")
    @Expose
    public Long orderId;
    @SerializedName("orderCode")
    @Expose
    public String orderCode;
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("stockId")
    @Expose
    public Long stockId;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("signState")
    @Expose
    public String signState;
    @SerializedName("fromStockTransId")
    @Expose
    public Long fromStockTransId;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("createdByName")
    @Expose
    public String createdByName;
    @SerializedName("createdDeptId")
    @Expose
    public Long createdDeptId;
    @SerializedName("createdDeptName")
    @Expose
    public String createdDeptName;
    @SerializedName("updatedBy")
    @Expose
    public Long updatedBy;
    @SerializedName("stockTransId")
    @Expose
    public Long stockTransId;
    @SerializedName("sysType")
    @Expose
    public Long sysType;
    @SerializedName("userLogin")
    @Expose
    public Long userLogin;
    @SerializedName("stockType")
    @Expose
    public String stockType;
    @SerializedName("confirmDescription")
    @Expose
    public String confirmDescription;
    @SerializedName("lastShipperId")
    @Expose
    public Long lastShipperId;
    @SerializedName("constructionCode")
    @Expose
    public String constructionCode;
    @SerializedName("scheduleType")
    @Expose
    public String scheduleType;

    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("receiverId")
    @Expose
    public Long receiverId;
    @SerializedName("consCode")
    @Expose
    public String consCode;
    @SerializedName("synStockName")
    @Expose
    public String synStockName;
    @SerializedName("synCreatedByName")
    @Expose
    public String synCreatedByName;
    @SerializedName("synCreatedDate")
    @Expose
    public String synCreatedDate;

    @SerializedName("updatedDate")
    @Expose
    public String updatedDate;
    @SerializedName("realIeTransDate")
    @Expose
    public String realIeTransDate;
    @SerializedName("realIeUserId")
    @Expose
    public String realIeUserId;
    @SerializedName("realIeUserName")
    @Expose
    public String realIeUserName;
    @SerializedName("shipperId")
    @Expose
    public Long shipperId;
    @SerializedName("shipperName")
    @Expose
    public String shipperName;

    @SerializedName("cancelDate")
    @Expose
    public String cancelDate;
    @SerializedName("cancelBy")
    @Expose
    public Long cancelBy;
    @SerializedName("cancelReasonName")
    @Expose
    public String cancelReasonName;
    @SerializedName("cancelDescription")
    @Expose
    public String cancelDescription;
    @SerializedName("vofficeTransactionCode")
    @Expose
    public String vofficeTransactionCode;
    @SerializedName("shipmentCode")
    @Expose
    public String shipmentCode;
    @SerializedName("contractCode")
    @Expose
    public String contractCode;
    @SerializedName("projectCode")
    @Expose
    public String projectCode;
    @SerializedName("custId")
    @Expose
    public Long custId;
    @SerializedName("createdBy")
    @Expose
    public Long createdBy;

    @SerializedName("createdDate")
    @Expose
    public String createdDate;
    @SerializedName("cancelByName")
    @Expose
    public String cancelByName;
    @SerializedName("bussinessTypeName")
    @Expose
    public String bussinessTypeName;
    @SerializedName("inRoal")
    @Expose
    public String inRoal;
    @SerializedName("deptReceiveName")
    @Expose
    public String deptReceiveName;
    @SerializedName("deptReceiveId")
    @Expose
    public Long deptReceiveId;
    @SerializedName("stockReceiveId")
    @Expose
    public Long stockReceiveId;
    @SerializedName("stockReceiveCode")
    @Expose
    public String stockReceiveCode;
    @SerializedName("partnerId")
    @Expose
    public Long partnerId;
    @SerializedName("typeExport")
    @Expose
    public Long typeExport;
    @SerializedName("codeTinhTruong")
    @Expose
    public Long codeTinhTruong;

    @SerializedName("synTransType")
    @Expose
    public String synTransType;
    @SerializedName("stockCode")
    @Expose
    public String stockCode;
    @SerializedName("stockName")
    @Expose
    public String stockName;
    @SerializedName("confirm")
    @Expose
    public String confirm;
    @SerializedName("buss")
    @Expose
    public String buss;

    @SerializedName("isSyn")
    @Expose
    public String isSyn;
    @SerializedName("constructionId")
    @Expose
    public Long constructionId;
    @SerializedName("cntContractCode")
    @Expose
    public String cntContractCode;
    @SerializedName("overDateKPI")
    @Expose
    public String overDateKPI;

    @SerializedName("filePaths")
    @Expose
    public List<ConstructionImageInfo> filePaths;
    @SerializedName("realConfirmTransDate")
    @Expose
    public String realConfirmTransDate;
    @SerializedName("listSynStockTransDetailDto")
    @Expose
    public List<SynStockTransDetailDTO> listSynStockTransDetailDto;

    public String getCntContractCode() {
        return cntContractCode;
    }

    public void setCntContractCode(String cntContractCode) {
        this.cntContractCode = cntContractCode;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getConstructionCode() {
        return constructionCode;
    }

    public void setConstructionCode(String constructionCode) {
        this.constructionCode = constructionCode;
    }

    public Long getLastShipperId() {
        return lastShipperId;
    }

    public void setLastShipperId(Long lastShipperId) {
        this.lastShipperId = lastShipperId;
    }

    public Long getCodeTinhTruong() {
        return codeTinhTruong;
    }

    public void setCodeTinhTruong(Long codeTinhTruong) {
        this.codeTinhTruong = codeTinhTruong;
    }

    public String getIsSyn() {
        return isSyn;
    }

    public void setIsSyn(String isSyn) {
        this.isSyn = isSyn;
    }

    public Long getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(Long constructionId) {
        this.constructionId = constructionId;
    }

    public String getConfirmDescription() {
        return confirmDescription;
    }

    public void setConfirmDescription(String confirmDescription) {
        this.confirmDescription = confirmDescription;
    }

    public Long getSynStockTransId() {
        return synStockTransId;
    }

    public void setSynStockTransId(Long synStockTransId) {
        this.synStockTransId = synStockTransId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        this.signState = signState;
    }

    public Long getFromStockTransId() {
        return fromStockTransId;
    }

    public void setFromStockTransId(Long fromStockTransId) {
        this.fromStockTransId = fromStockTransId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Long getCreatedDeptId() {
        return createdDeptId;
    }

    public void setCreatedDeptId(Long createdDeptId) {
        this.createdDeptId = createdDeptId;
    }

    public String getCreatedDeptName() {
        return createdDeptName;
    }

    public void setCreatedDeptName(String createdDeptName) {
        this.createdDeptName = createdDeptName;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getStockTransId() {
        return stockTransId;
    }

    public void setStockTransId(Long stockTransId) {
        this.stockTransId = stockTransId;
    }

    public Long getSysType() {
        return sysType;
    }

    public void setSysType(Long sysType) {
        this.sysType = sysType;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getRealIeTransDate() {
        return realIeTransDate;
    }

    public void setRealIeTransDate(String realIeTransDate) {
        this.realIeTransDate = realIeTransDate;
    }

    public String getRealIeUserId() {
        return realIeUserId;
    }

    public void setRealIeUserId(String realIeUserId) {
        this.realIeUserId = realIeUserId;
    }

    public String getRealIeUserName() {
        return realIeUserName;
    }

    public void setRealIeUserName(String realIeUserName) {
        this.realIeUserName = realIeUserName;
    }

    public Long getShipperId() {
        return shipperId;
    }

    public void setShipperId(Long shipperId) {
        this.shipperId = shipperId;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

    public Long getCancelBy() {
        return cancelBy;
    }

    public void setCancelBy(Long cancelBy) {
        this.cancelBy = cancelBy;
    }

    public String getCancelReasonName() {
        return cancelReasonName;
    }

    public void setCancelReasonName(String cancelReasonName) {
        this.cancelReasonName = cancelReasonName;
    }

    public String getCancelDescription() {
        return cancelDescription;
    }

    public void setCancelDescription(String cancelDescription) {
        this.cancelDescription = cancelDescription;
    }

    public Long getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(Long userLogin) {
        this.userLogin = userLogin;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getConsCode() {
        return consCode;
    }

    public void setConsCode(String consCode) {
        this.consCode = consCode;
    }

    public String getSynStockName() {
        return synStockName;
    }

    public void setSynStockName(String synStockName) {
        this.synStockName = synStockName;
    }

    public String getSynCreatedByName() {
        return synCreatedByName;
    }

    public void setSynCreatedByName(String synCreatedByName) {
        this.synCreatedByName = synCreatedByName;
    }

    public String getSynCreatedDate() {
        return synCreatedDate;
    }

    public void setSynCreatedDate(String synCreatedDate) {
        this.synCreatedDate = synCreatedDate;
    }

    public String getVofficeTransactionCode() {
        return vofficeTransactionCode;
    }

    public void setVofficeTransactionCode(String vofficeTransactionCode) {
        this.vofficeTransactionCode = vofficeTransactionCode;
    }

    public String getShipmentCode() {
        return shipmentCode;
    }

    public void setShipmentCode(String shipmentCode) {
        this.shipmentCode = shipmentCode;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCancelByName() {
        return cancelByName;
    }

    public void setCancelByName(String cancelByName) {
        this.cancelByName = cancelByName;
    }

    public String getBussinessTypeName() {
        return bussinessTypeName;
    }

    public void setBussinessTypeName(String bussinessTypeName) {
        this.bussinessTypeName = bussinessTypeName;
    }

    public String getInRoal() {
        return inRoal;
    }

    public void setInRoal(String inRoal) {
        this.inRoal = inRoal;
    }

    public String getDeptReceiveName() {
        return deptReceiveName;
    }

    public void setDeptReceiveName(String deptReceiveName) {
        this.deptReceiveName = deptReceiveName;
    }

    public Long getDeptReceiveId() {
        return deptReceiveId;
    }

    public void setDeptReceiveId(Long deptReceiveId) {
        this.deptReceiveId = deptReceiveId;
    }

    public Long getStockReceiveId() {
        return stockReceiveId;
    }

    public void setStockReceiveId(Long stockReceiveId) {
        this.stockReceiveId = stockReceiveId;
    }

    public String getStockReceiveCode() {
        return stockReceiveCode;
    }

    public void setStockReceiveCode(String stockReceiveCode) {
        this.stockReceiveCode = stockReceiveCode;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getTypeExport() {
        return typeExport;
    }

    public void setTypeExport(Long typeExport) {
        this.typeExport = typeExport;
    }

    public String getSynTransType() {
        return synTransType;
    }

    public void setSynTransType(String synTransType) {
        this.synTransType = synTransType;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getBuss() {
        return buss;
    }

    public void setBuss(String buss) {
        this.buss = buss;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getOverDateKPI() {
        return overDateKPI;
    }

    public void setOverDateKPI(String overDateKPI) {
        this.overDateKPI = overDateKPI;
    }

    public List<ConstructionImageInfo> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(List<ConstructionImageInfo> filesPath) {
        this.filePaths = filesPath;
    }

    public String getRealConfirmTransDate() {
        return realConfirmTransDate;
    }

    public void setRealConfirmTransDate(String realConfirmTransDate) {
        this.realConfirmTransDate = realConfirmTransDate;
    }

    public List<SynStockTransDetailDTO> getListSynStockTransDetailDto() {
        return listSynStockTransDetailDto;
    }

    public void setListSynStockTransDetailDto(List<SynStockTransDetailDTO> listSynStockTransDetailDto) {
        this.listSynStockTransDetailDto = listSynStockTransDetailDto;
    }

    public static DiffUtil.ItemCallback<SynStockTransDTO> DIFF_CALLBACK = new DiffUtil.ItemCallback<SynStockTransDTO>() {
        @Override
        public boolean areItemsTheSame(@NonNull SynStockTransDTO oldItem, @NonNull SynStockTransDTO newItem) {
            return (oldItem.synStockTransId == newItem.synStockTransId);
        }

        @Override
        public boolean areContentsTheSame(@NonNull SynStockTransDTO oldItem, @NonNull SynStockTransDTO newItem) {
            return oldItem.code.equals(newItem.code) ;
        }
    };

    private boolean isSelected;
    public void setSelected(boolean selected){
        isSelected = selected;
    }
    public boolean isSelected(){
        return isSelected;
    }
}
