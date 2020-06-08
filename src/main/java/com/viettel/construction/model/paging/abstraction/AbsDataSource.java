package com.viettel.construction.model.paging.abstraction;

import androidx.paging.PageKeyedDataSource;
import androidx.annotation.NonNull;
import android.util.Log;

import com.viettel.construction.server.retrofit.RetrofitService;


public abstract class AbsDataSource<T> extends PageKeyedDataSource<Long, T> {

    public RetrofitService mService;

    public AbsDataSource(RetrofitService service) {
        mService = service;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, T> callback) {
        loadInitialPage(1l, params.requestedLoadSize, callback);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, T> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, T> callback) {
        long nextPage = params.key + 1;
        loadPage(nextPage, params.requestedLoadSize, callback);

    }


    public abstract void loadInitialPage(Long page, int pageSize, LoadInitialCallback<Long, T> callback);

    public abstract void loadPage(Long page, int pageSize, LoadCallback<Long, T> callback);
}
