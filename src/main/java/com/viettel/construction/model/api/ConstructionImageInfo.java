package com.viettel.construction.model.api;

import java.io.Serializable;

/**
 * Created by manro on 3/17/2018.
 */

public class ConstructionImageInfo implements Serializable{

    // status 1 la new , 0 la server ,xoa la -1

    private long utilAttachDocumentId;
    private long status;
    private String imageName;
    private String base64String;
    private double longtitude;
    private double latitude;
    private String imagePath;
    private String type;
    private String name;

    public ConstructionImageInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUtilAttachDocumentId() {
        return utilAttachDocumentId;
    }

    public void setUtilAttachDocumentId(long utilAttachDocumentId) {
        this.utilAttachDocumentId = utilAttachDocumentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getBase64String() {
        return base64String;
    }

    public void setBase64String(String base64String) {
        this.base64String = base64String;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
