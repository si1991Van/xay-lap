package com.viettel.construction.model;

/**
 * Created by manro on 3/14/2018.
 */

public class Device {
    private String name1;
    private String name2;
    private String name3;
    private String number;


    public Device(String name1, String name2, String name3, String number) {
        this.name1 = name1;
        this.name2 = name2;
        this.name3 = name3;
        this.number = number;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
