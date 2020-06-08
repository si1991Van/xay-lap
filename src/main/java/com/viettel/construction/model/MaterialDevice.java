package com.viettel.construction.model;



public class MaterialDevice {
    private String name;
    private int numberReturn;

    public MaterialDevice(String name, int numberReturn) {
        this.name = name;
        this.numberReturn = numberReturn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberReturn() {
        return numberReturn;
    }

    public void setNumberReturn(int numberReturn) {
        this.numberReturn = numberReturn;
    }
}
