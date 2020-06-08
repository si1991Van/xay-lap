package com.viettel.construction.model;

public class DetailConstruction {
    private String category;
    private String progress;
    private String status;

    public DetailConstruction(String category, String progress, String status) {
        this.category = category;
        this.progress = progress;
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
