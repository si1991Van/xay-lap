package com.viettel.construction.server.retrofit;

public interface INetWorkCallBack<T> {
    void onSuccess(T items);
    void onError();
}
