package com.viettel.construction.model.api.login;

        import com.viettel.construction.model.api.AuthenticationInfo;

/**
 * Created by manro on 3/23/2018.
 */

public class BaseRequest {
    private AuthenticationInfo authenticationInfo;

    public AuthenticationInfo getAuthenticationInfo() {
        return authenticationInfo;
    }

    public void setAuthenticationInfo(AuthenticationInfo authenticationInfo) {
        this.authenticationInfo = authenticationInfo;
    }
}
