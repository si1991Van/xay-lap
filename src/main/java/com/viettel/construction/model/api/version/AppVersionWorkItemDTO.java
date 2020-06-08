package com.viettel.construction.model.api.version;

public class AppVersionWorkItemDTO {
    private long appParamId;
    private String link;
    private String version;

    public long getAppParamId() {
        return appParamId;
    }

    public void setAppParamId(long appParamId) {
        this.appParamId = appParamId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
