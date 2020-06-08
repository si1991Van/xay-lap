package com.viettel.construction.model;


import java.io.Serializable;

public class Item implements Serializable{
    private String name;
    private long workItemId;

    public Item(String name, long workItemId) {
        this.name = name;
        this.workItemId = workItemId;
    }

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(long workItemId) {
        this.workItemId = workItemId;
    }
}
