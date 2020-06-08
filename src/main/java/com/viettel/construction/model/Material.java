package com.viettel.construction.model;


public class Material {
    private String name;
    private int amount;
    private int amountReal;

    public Material(String name, int amount, int amountReal) {
        this.name = name;
        this.amount = amount;
        this.amountReal = amountReal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmountReal() {
        return amountReal;
    }

    public void setAmountReal(int amountReal) {
        this.amountReal = amountReal;
    }
}
