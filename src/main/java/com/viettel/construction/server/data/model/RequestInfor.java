package com.viettel.construction.server.data.model;

import java.util.Map;


public class RequestInfor {
    private String url;
    private String response;
    private String header;
    private String params;

    public RequestInfor(String url, Map<String, String> header, String params) {
        this.url = url;
        if (header != null) {
            this.header = header.toString();
        }
        this.params = params;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public RequestInfor() {
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
