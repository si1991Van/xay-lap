package com.viettel.construction.model.api;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Thangtv24 on 3/9/2018.
 */

public class ConstructionBGMBDTO implements Serializable {
    private long assignHandoverId;
    private long sysGroupId;
    private String sysGroupCode;
    private String sysGroupName;
    private String constructionCode;
    private long catStationHouseId;
    private long constructionId;
    private String cntContractCode;
    private long cntContractId;
    private Date companyAssignDate;
    private long status;
    private long performentId;
    private Date departmentAssignDate;
    private long receivedStatus;
    private Date receivedObstructDate;
    private String receivedObstructContent;
    private Date receivedGoodsDate;
    private String receivedGoodsContent;
    private Date receivedDate;
    private String isFence;
    private String houseTypeName;
    private String haveWorkItemName;
    private String groundingTypeName;
    private long groundingTypeId;
    private long houseTypeId;
    private long numberCo;
    private long stationType;
    private long columnHeight;
    private List<ConstructionImageInfo> constructionImageInfo;
    private long constructionStatus;
    private String isFenceStr;
    private String isReceivedGoodsStr;
    private String isReceivedObstructStr;
    private String totalLength;
    private String hiddenImmediacy;
    private String cableInTank;
    private String cableInTankDrain;
    private String plantColunms;
    private String availableColumns;
    private long catConstructionType;
    private long checkXPXD;
    private long checkXPAC;

    private String typeConstructionBgmb;
    private String numColumnsAvaible;
    private String lengthOfMeter;
    private String haveStartPoint;
    private String typeOfMeter;
    private String numNewColumn;
    private String typeOfColumn;

    public long getCheckXPXD() {
        return checkXPXD;
    }

    public void setCheckXPXD(long checkXPXD) {
        this.checkXPXD = checkXPXD;
    }

    public long getCheckXPAC() {
        return checkXPAC;
    }

    public void setCheckXPAC(long checkXPAC) {
        this.checkXPAC = checkXPAC;
    }

    public String getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(String totalLength) {
        this.totalLength = totalLength;
    }

    public String getHiddenImmediacy() {
        return hiddenImmediacy;
    }

    public void setHiddenImmediacy(String hiddenImmediacy) {
        this.hiddenImmediacy = hiddenImmediacy;
    }

    public String getCableInTank() {
        return cableInTank;
    }

    public void setCableInTank(String cableInTank) {
        this.cableInTank = cableInTank;
    }

    public String getCableInTankDrain() {
        return cableInTankDrain;
    }

    public void setCableInTankDrain(String cableInTankDrain) {
        this.cableInTankDrain = cableInTankDrain;
    }

    public String getPlantColunms() {
        return plantColunms;
    }

    public void setPlantColunms(String plantColunms) {
        this.plantColunms = plantColunms;
    }

    public String getAvailableColumns() {
        return availableColumns;
    }

    public void setAvailableColumns(String availableColumns) {
        this.availableColumns = availableColumns;
    }

    public long getCatConstructionType() {
        return catConstructionType;
    }

    public void setCatConstructionType(long catConstructionType) {
        this.catConstructionType = catConstructionType;
    }

    public String getIsACStr() {
        return isACStr;
    }

    public void setIsACStr(String isACStr) {
        this.isACStr = isACStr;
    }

    private String isACStr;

    public ConstructionBGMBDTO(long assignHandoverId, long sysGroupId, String sysGroupCode, String sysGroupName, String constructionCode, long catStationHouseId, long constructionId, String cntContractCode, long cntContractId, Date companyAssignDate, long status, long performentId, Date departmentAssignDate, long receivedStatus, Date receivedObstructDate, String receivedObstructContent, Date receivedGoodsDate, String receivedGoodsContent, Date receivedDate, String isFence, String houseTypeName, String haveWorkItemName, String groundingTypeName, long groundingTypeId, long houseTypeId, long numberCo, long stationType, long columnHeight, List<ConstructionImageInfo> constructionImageInfo, long constructionStatus, String isFenceStr, String isReceivedGoodsStr, String isReceivedObstructStr) {
        this.assignHandoverId = assignHandoverId;
        this.sysGroupId = sysGroupId;
        this.sysGroupCode = sysGroupCode;
        this.sysGroupName = sysGroupName;
        this.constructionCode = constructionCode;
        this.catStationHouseId = catStationHouseId;
        this.constructionId = constructionId;
        this.cntContractCode = cntContractCode;
        this.cntContractId = cntContractId;
        this.companyAssignDate = companyAssignDate;
        this.status = status;
        this.performentId = performentId;
        this.departmentAssignDate = departmentAssignDate;
        this.receivedStatus = receivedStatus;
        this.receivedObstructDate = receivedObstructDate;
        this.receivedObstructContent = receivedObstructContent;
        this.receivedGoodsDate = receivedGoodsDate;
        this.receivedGoodsContent = receivedGoodsContent;
        this.receivedDate = receivedDate;
        this.isFence = isFence;
        this.houseTypeName = houseTypeName;
        this.haveWorkItemName = haveWorkItemName;
        this.groundingTypeName = groundingTypeName;
        this.groundingTypeId = groundingTypeId;
        this.houseTypeId = houseTypeId;
        this.numberCo = numberCo;
        this.stationType = stationType;
        this.columnHeight = columnHeight;
        this.constructionImageInfo = constructionImageInfo;
        this.constructionStatus = constructionStatus;
        this.isFenceStr = isFenceStr;
        this.isReceivedGoodsStr = isReceivedGoodsStr;
        this.isReceivedObstructStr = isReceivedObstructStr;
    }

    public ConstructionBGMBDTO() {
    }

    public long getAssignHandoverId() {
        return assignHandoverId;
    }

    public void setAssignHandoverId(long assignHandoverId) {
        this.assignHandoverId = assignHandoverId;
    }

    public long getSysGroupId() {
        return sysGroupId;
    }

    public void setSysGroupId(long sysGroupId) {
        this.sysGroupId = sysGroupId;
    }

    public String getSysGroupCode() {
        return sysGroupCode;
    }

    public void setSysGroupCode(String sysGroupCode) {
        this.sysGroupCode = sysGroupCode;
    }

    public String getSysGroupName() {
        return sysGroupName;
    }

    public void setSysGroupName(String sysGroupName) {
        this.sysGroupName = sysGroupName;
    }

    public String getConstructionCode() {
        return constructionCode;
    }

    public void setConstructionCode(String constructionCode) {
        this.constructionCode = constructionCode;
    }

    public long getCatStationHouseId() {
        return catStationHouseId;
    }

    public void setCatStationHouseId(long catStationHouseId) {
        this.catStationHouseId = catStationHouseId;
    }

    public long getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(long constructionId) {
        this.constructionId = constructionId;
    }

    public String getCntContractCode() {
        return cntContractCode;
    }

    public void setCntContractCode(String cntContractCode) {
        this.cntContractCode = cntContractCode;
    }

    public long getCntContractId() {
        return cntContractId;
    }

    public void setCntContractId(long cntContractId) {
        this.cntContractId = cntContractId;
    }

    public Date getCompanyAssignDate() {
        return companyAssignDate;
    }

    public void setCompanyAssignDate(Date companyAssignDate) {
        this.companyAssignDate = companyAssignDate;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public long getPerformentId() {
        return performentId;
    }

    public void setPerformentId(long performentId) {
        this.performentId = performentId;
    }

    public Date getDepartmentAssignDate() {
        return departmentAssignDate;
    }

    public void setDepartmentAssignDate(Date departmentAssignDate) {
        this.departmentAssignDate = departmentAssignDate;
    }

    public long getReceivedStatus() {
        return receivedStatus;
    }

    public void setReceivedStatus(long receivedStatus) {
        this.receivedStatus = receivedStatus;
    }

    public Date getReceivedObstructDate() {
        return receivedObstructDate;
    }

    public void setReceivedObstructDate(Date receivedObstructDate) {
        this.receivedObstructDate = receivedObstructDate;
    }

    public String getReceivedObstructContent() {
        return receivedObstructContent;
    }

    public void setReceivedObstructContent(String receivedObstructContent) {
        this.receivedObstructContent = receivedObstructContent;
    }

    public Date getReceivedGoodsDate() {
        return receivedGoodsDate;
    }

    public void setReceivedGoodsDate(Date receivedGoodsDate) {
        this.receivedGoodsDate = receivedGoodsDate;
    }

    public String getReceivedGoodsContent() {
        return receivedGoodsContent;
    }

    public void setReceivedGoodsContent(String receivedGoodsContent) {
        this.receivedGoodsContent = receivedGoodsContent;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getIsFence() {
        return isFence;
    }

    public void setIsFence(String isFence) {
        this.isFence = isFence;
    }

    public String getHouseTypeName() {
        return houseTypeName;
    }

    public void setHouseTypeName(String houseTypeName) {
        this.houseTypeName = houseTypeName;
    }

    public String getHaveWorkItemName() {
        return haveWorkItemName;
    }

    public void setHaveWorkItemName(String haveWorkItemName) {
        this.haveWorkItemName = haveWorkItemName;
    }

    public String getGroundingTypeName() {
        return groundingTypeName;
    }

    public void setGroundingTypeName(String groundingTypeName) {
        this.groundingTypeName = groundingTypeName;
    }

    public long getGroundingTypeId() {
        return groundingTypeId;
    }

    public void setGroundingTypeId(long groundingTypeId) {
        this.groundingTypeId = groundingTypeId;
    }

    public long getHouseTypeId() {
        return houseTypeId;
    }

    public void setHouseTypeId(long houseTypeId) {
        this.houseTypeId = houseTypeId;
    }

    public long getNumberCo() {
        return numberCo;
    }

    public void setNumberCo(long numberCo) {
        this.numberCo = numberCo;
    }

    public long getStationType() {
        return stationType;
    }

    public void setStationType(long stationType) {
        this.stationType = stationType;
    }

    public long getColumnHeight() {
        return columnHeight;
    }

    public void setColumnHeight(long columnHeight) {
        this.columnHeight = columnHeight;
    }

    public List<ConstructionImageInfo> getConstructionImageInfo() {
        return constructionImageInfo;
    }

    public void setConstructionImageInfo(List<ConstructionImageInfo> constructionImageInfo) {
        this.constructionImageInfo = constructionImageInfo;
    }

    public long getConstructionStatus() {
        return constructionStatus;
    }

    public void setConstructionStatus(long constructionStatus) {
        this.constructionStatus = constructionStatus;
    }

    public String getIsFenceStr() {
        return isFenceStr;
    }

    public void setIsFenceStr(String isFenceStr) {
        this.isFenceStr = isFenceStr;
    }

    public String getIsReceivedGoodsStr() {
        return isReceivedGoodsStr;
    }

    public void setIsReceivedGoodsStr(String isReceivedGoodsStr) {
        this.isReceivedGoodsStr = isReceivedGoodsStr;
    }

    public String getIsReceivedObstructStr() {
        return isReceivedObstructStr;
    }

    public void setIsReceivedObstructStr(String isReceivedObstructStr) {
        this.isReceivedObstructStr = isReceivedObstructStr;
    }

    public String getNumColumnsAvaible() {
        return numColumnsAvaible;
    }

    public void setNumColumnsAvaible(String numColumnsAvaible) {
        this.numColumnsAvaible = numColumnsAvaible;
    }

    public String getLengthOfMeter() {
        return lengthOfMeter;
    }

    public void setLengthOfMeter(String lengthOfMeter) {
        this.lengthOfMeter = lengthOfMeter;
    }

    public String getHaveStartPoint() {
        return haveStartPoint;
    }

    public void setHaveStartPoint(String haveStartPoint) {
        this.haveStartPoint = haveStartPoint;
    }

    public String getTypeOfMeter() {
        return typeOfMeter;
    }

    public void setTypeOfMeter(String typeOfMeter) {
        this.typeOfMeter = typeOfMeter;
    }

    public String getNumNewColumn() {
        return numNewColumn;
    }

    public void setNumNewColumn(String numNewColumn) {
        this.numNewColumn = numNewColumn;
    }

    public String getTypeOfColumn() {
        return typeOfColumn;
    }

    public void setTypeOfColumn(String typeOfColumn) {
        this.typeOfColumn = typeOfColumn;
    }

    public String getTypeConstructionBgmb() {
        return typeConstructionBgmb;
    }

    public void setTypeConstructionBgmb(String typeConstructionBgmb) {
        this.typeConstructionBgmb = typeConstructionBgmb;
    }
}
