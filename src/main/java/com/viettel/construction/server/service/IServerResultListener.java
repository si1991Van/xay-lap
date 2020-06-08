package com.viettel.construction.server.service;

public interface IServerResultListener<T> {
    void onResponse(T result);
    void onError(int statusCode);
}
