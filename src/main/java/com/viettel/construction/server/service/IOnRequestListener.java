package com.viettel.construction.server.service;

/**
 * Created by Manroid on 11/01/2018.
 */

public interface IOnRequestListener {
    <T> void onResponse(T result);

    void onError(int statusCode);
}
