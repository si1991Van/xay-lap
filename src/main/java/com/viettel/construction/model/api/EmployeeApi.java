package com.viettel.construction.model.api;

import java.io.Serializable;

/**
 * Created by manro on 3/12/2018.
 */

public class EmployeeApi implements Serializable{
    private String defaultSortField;
    private String id;
    private String page;
    private String pageSize;
    private String total;
    private String keySearch;
    private String text;
    private String totalRecord;
    private String sysUserId;
    private String loginName;
    private String fullName;
    private String password;
    private String employeeCode;
    private String email;
    private String phoneNumber;
    private String status;
    private String birthday;
    private String position;
    private String namePhone;
    private String departmentId;
    private String departmentName;
    private String vofficePass;
    private String catProvinceCode;
    private String name;
    private String fwmodelId;

    public EmployeeApi(String defaultSortField, String id, String page, String pageSize, String total, String keySearch, String text, String totalRecord, String sysUserId, String loginName, String fullName, String password, String employeeCode, String email, String phoneNumber, String status, String birthday, String position, String namePhone, String departmentId, String departmentName, String vofficePass, String catProvinceCode, String name, String fwmodelId) {
        this.defaultSortField = defaultSortField;
        this.id = id;
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.keySearch = keySearch;
        this.text = text;
        this.totalRecord = totalRecord;
        this.sysUserId = sysUserId;
        this.loginName = loginName;
        this.fullName = fullName;
        this.password = password;
        this.employeeCode = employeeCode;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.birthday = birthday;
        this.position = position;
        this.namePhone = namePhone;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.vofficePass = vofficePass;
        this.catProvinceCode = catProvinceCode;
        this.name = name;
        this.fwmodelId = fwmodelId;
    }

    public String getDefaultSortField() {
        return defaultSortField;
    }

    public void setDefaultSortField(String defaultSortField) {
        this.defaultSortField = defaultSortField;
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

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNamePhone() {
        return namePhone;
    }

    public void setNamePhone(String namePhone) {
        this.namePhone = namePhone;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getVofficePass() {
        return vofficePass;
    }

    public void setVofficePass(String vofficePass) {
        this.vofficePass = vofficePass;
    }

    public String getCatProvinceCode() {
        return catProvinceCode;
    }

    public void setCatProvinceCode(String catProvinceCode) {
        this.catProvinceCode = catProvinceCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFwmodelId() {
        return fwmodelId;
    }

    public void setFwmodelId(String fwmodelId) {
        this.fwmodelId = fwmodelId;
    }
}
