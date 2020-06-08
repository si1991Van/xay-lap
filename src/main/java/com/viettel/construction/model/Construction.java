package com.viettel.construction.model;

import java.io.Serializable;

/**
 * Created by hoang on 18/01/2018.
 */

public class Construction implements Serializable {

    private String constructionCode;
    private String name;
    private String address;
    private long id;

    public Construction(String constructionCode, String name, String address, long id) {
        this.constructionCode = constructionCode;
        this.name = name;
        this.address = address;
        this.id = id;
    }

    public Construction(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Construction(String name, String address, long id) {
        this.name = name;
        this.address = address;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
