package com.viettel.construction.model.api.login;

import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.SysUserDTO;

/**
 * Created by manro on 3/23/2018.
 */

public class LoginResult {
    private ResultInfo resultInfo;
    private SysUserDTO userLogin;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public SysUserDTO getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(SysUserDTO userLogin) {
        this.userLogin = userLogin;
    }
}
