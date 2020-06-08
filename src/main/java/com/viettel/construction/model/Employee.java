package com.viettel.construction.model;

import java.io.Serializable;

public class Employee implements Serializable{
    private String name;
    private String phone;
    private String email;
    private String office;
    private String sysUserId;
    private String sysGroupId;
    private String emloyeeCode;
    private long constructionTaskId;

    public Employee(String name, String phone, String email, String office, String sysUserId) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.office = office;
        this.sysUserId = sysUserId;
    }

    public Employee(String name, String phone, String email, String office, String sysUserId, String sysGroupId, long constructionTaskId) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.office = office;
        this.sysUserId = sysUserId;
        this.sysGroupId = sysGroupId;
        this.constructionTaskId = constructionTaskId;
    }

    public Employee(String name, String phone, String email, String office, String sysUserId, String sysGroupId, String emloyeeCode, long constructionTaskId) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.office = office;
        this.sysUserId = sysUserId;
        this.sysGroupId = sysGroupId;
        this.emloyeeCode = emloyeeCode;
        this.constructionTaskId = constructionTaskId;
    }

    public Employee(String name, String phone, String email, String office, String sysUserId, String sysGroupId) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.office = office;
        this.sysUserId = sysUserId;
        this.sysGroupId = sysGroupId;
    }

    public Employee(String name, String phone, String email, String office, String sysUserId, String sysGroupId, String emloyeeCode) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.office = office;
        this.sysUserId = sysUserId;
        this.sysGroupId = sysGroupId;
        this.emloyeeCode = emloyeeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getSysGroupId() {
        return sysGroupId;
    }

    public void setSysGroupId(String sysGroupId) {
        this.sysGroupId = sysGroupId;
    }

    public String getEmloyeeCode() {
        return emloyeeCode;
    }

    public void setEmloyeeCode(String emloyeeCode) {
        this.emloyeeCode = emloyeeCode;
    }

    public long getConstructionTaskId() {
        return constructionTaskId;
    }

    public void setConstructionTaskId(long constructionTaskId) {
        this.constructionTaskId = constructionTaskId;
    }
}
