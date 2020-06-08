package com.viettel.construction.model.paging.abstraction;


import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public abstract class AbsFactorySource<T> extends DataSource.Factory{

    public T mDataSource;
    public MutableLiveData<T> mutableLiveData;

    @Override
    public DataSource create() {
        return null;
    }

}
