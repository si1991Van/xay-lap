package com.viettel.construction.model.api;

/**
 * Created by manro on 3/6/2018.
 */

public class AuthenticationInfo {

    private String username;
    private String password;
    private String version;

    public AuthenticationInfo() {
    }

    public AuthenticationInfo(String username, String password, String version) {
        this.username = username;
        this.password = password;
        this.version = version;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
