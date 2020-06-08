package com.viettel.construction.model;

/**
 * Created by Manroid on 22/01/2018.
 */

public class Reflect {
    private String content;
    private String id;
    private String status;
    private int position;

    public Reflect(String content, String id, String status,int position) {
        this.content = content;
        this.id = id;
        this.status = status;
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
