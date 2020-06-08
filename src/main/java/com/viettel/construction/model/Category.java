package com.viettel.construction.model;

import java.io.Serializable;

/**
 * Created by manro on 2/27/2018.
 */

public class Category implements Serializable{
    private String id;
    private String name;
    private String time;
    private String status;

    public Category(String id, String name, String time, String status) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
