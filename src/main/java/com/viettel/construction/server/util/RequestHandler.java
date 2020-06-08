package com.viettel.construction.server.util;


import com.viettel.construction.server.data.model.RequestInfor;

import java.util.ArrayList;
import java.util.List;


public class RequestHandler {
    private static RequestHandler instance;
    private List<RequestInfor> listRequestInfor = new ArrayList<>();

    private RequestHandler() {

    }

    public static RequestHandler getInstance() {
        if (instance == null)
            instance = new RequestHandler();
        return instance;
    }

    public void addRequest(RequestInfor requestInfor) {
        listRequestInfor.add(requestInfor);
    }

    public List<RequestInfor> getListRequestInfor() {
        return listRequestInfor;
    }
}
