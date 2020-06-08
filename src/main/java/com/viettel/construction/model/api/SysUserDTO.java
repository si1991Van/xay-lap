package com.viettel.construction.model.api;

/**
 * Created by manro on 3/16/2018.
 */

public class SysUserDTO {
    private long sysUserId;
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
    private String sysGroupName;
    private long departmentId;
    private String departmentName;
    private String vofficePass;
    private String catProvinceCode;
    private long sysGroupId;

    public SysUserDTO(long sysUserId, String loginName, String fullName, String password, String employeeCode, String email, String phoneNumber, String status, String birthday, String position, String namePhone, String sysGroupName, long departmentId, String departmentName, String vofficePass, String catProvinceCode) {
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
        this.sysGroupName = sysGroupName;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.vofficePass = vofficePass;
        this.catProvinceCode = catProvinceCode;
    }

    public SysUserDTO(long sysUserId, String email, String phoneNumber, long sysGroupId) {
        this.sysUserId = sysUserId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sysGroupId = sysGroupId;
    }

    public SysUserDTO(String phone, String email, long sysUserId, long sysGroupId) {
        this.phoneNumber = phone;
        this.email = email;
        this.sysUserId = sysGroupId;
    }

    public long getSysGroupId() {
        return sysGroupId;
    }

    public void setSysGroupId(long sysGroupId) {
        this.sysGroupId = sysGroupId;
    }

    public long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(long sysUserId) {
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

    public String getSysGroupName() {
        return sysGroupName;
    }

    public void setSysGroupName(String sysGroupName) {
        this.sysGroupName = sysGroupName;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
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
}
