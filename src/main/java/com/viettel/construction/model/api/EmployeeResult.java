package com.viettel.construction.model.api;

import java.util.List;

/**
 * Created by manro on 3/12/2018.
 */

public class EmployeeResult {
    private ResultInfo resultInfo;
    private List<EmployeeApi> listUser;

    public EmployeeResult(ResultInfo resultInfo, List<EmployeeApi> listUser) {
        this.resultInfo = resultInfo;
        this.listUser = listUser;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public List<EmployeeApi> getListUser() {
        return listUser;
    }

    public void setListUser(List<EmployeeApi> listUser) {
        this.listUser = listUser;
    }
}
