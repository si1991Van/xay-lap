package com.viettel.construction.model.db;

import com.viettel.construction.server.util.StringUtil;

import java.io.Serializable;

public class ImageCapture implements Serializable {
    private int id;
    private String imageData;
    private String createdDate;
    private double longtitude;
    private double lattitude;
    private boolean isSelected;
    private String imageName;

    public ImageCapture() {

    }

    public ImageCapture(int id, String imageData, String createdDate,
                        double longtitude, double lattitude, String name) {
        this.id = id;
        this.imageData = imageData;
        this.createdDate = createdDate;
        this.longtitude = longtitude;
        this.lattitude = lattitude;
        this.imageName = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
