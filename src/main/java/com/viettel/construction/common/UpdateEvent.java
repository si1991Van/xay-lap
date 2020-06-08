package com.viettel.construction.common;

/**
 * Created by manro on 2/26/2018.
 */

public class UpdateEvent {
    private int value;

    public UpdateEvent(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
