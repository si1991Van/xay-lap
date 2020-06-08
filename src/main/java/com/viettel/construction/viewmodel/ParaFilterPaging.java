package com.viettel.construction.viewmodel;

public class ParaFilterPaging {
    private boolean isDefault;
    private boolean isABill;

    // For search
    private boolean isSearch;
    private String textSearch;

    // Not search
    private boolean isStateRequest;
    private long state;
    private long confirm;

    // Over date kpi
    private String overDateKpi;

    public boolean isABill() {
        return isABill;
    }

    public void setABill(boolean ABill) {
        isABill = ABill;
    }

    public boolean isSearch() {
        return isSearch;
    }

    public void setSearch(boolean search) {
        isSearch = search;
    }

    public String getTextSearch() {
        return textSearch;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    public long getConfirm() {
        return confirm;
    }

    public void setConfirm(long confirm) {
        this.confirm = confirm;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public boolean isStateRequest() {
        return isStateRequest;
    }

    public void setStateRequest(boolean stateRequest) {
        isStateRequest = stateRequest;
    }

    public String getOverDateKpi() {
        return overDateKpi;
    }

    public void setOverDateKpi(String overDateKpi) {
        this.overDateKpi = overDateKpi;
    }

}
