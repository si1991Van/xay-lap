package com.viettel.construction.model;

/**
 * Created by manro on 4/7/2018.
 */

public class DetailsImplement {
    private String amount;
    private String serial;
    private String id;
    private String partNumber;
    private String countryProduce;
    private String manufacturer;


    public DetailsImplement() {
    }

    public DetailsImplement(String amount, String serial, String id, String partNumber, String countryProduce, String manufacturer) {
        this.amount = amount;
        this.serial = serial;
        this.id = id;
        this.partNumber = partNumber;
        this.countryProduce = countryProduce;
        this.manufacturer = manufacturer;

    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getCountryProduce() {
        return countryProduce;
    }

    public void setCountryProduce(String countryProduce) {
        this.countryProduce = countryProduce;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }


}
