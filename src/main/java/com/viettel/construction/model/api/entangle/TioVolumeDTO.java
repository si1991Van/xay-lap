package com.viettel.construction.model.api.entangle;

import com.viettel.construction.model.api.ConstructionImageInfo;

import java.util.List;

public class TioVolumeDTO extends ObstructedDTO {
    private String coinName;
    private String volume;
    private String price;

    public String getCoinName() {
        return coinName;
    }


    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
