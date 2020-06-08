package com.viettel.construction.model;

/**
 * Created by Manroid on 22/01/2018.
 */

public class Acceptance {
    private String id;
    private String status;

    public Acceptance() {
    }

    public Acceptance(String id, String status) {
        this.id = id;
        this.status = status;
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
}
