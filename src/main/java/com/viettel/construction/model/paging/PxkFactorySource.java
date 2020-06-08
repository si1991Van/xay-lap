package com.viettel.construction.model.paging;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.viettel.construction.model.api.StockTransRequest;
import com.viettel.construction.model.api.SynStockTransDTO;
import com.viettel.construction.model.paging.abstraction.AbsFactorySource;
import com.viettel.construction.server.retrofit.RetrofitService;
import com.viettel.construction.viewmodel.ParaFilterPaging;

public class PxkFactorySource extends AbsFactorySource<SynStockTransDTO> {

    public PxkDataSource mDataSource;
    public RetrofitService mService;
    public StockTransRequest mBodyRequest;
    public ParaFilterPaging mPara;
    public MutableLiveData<PxkDataSource> mTotalItem = new MutableLiveData<>();

    public PxkFactorySource(RetrofitService repository, StockTransRequest requestBody, ParaFilterPaging para){
        mService = repository;
        mBodyRequest = requestBody;
        mPara = para;
    }

    @Override
    public DataSource create() {
        mDataSource = new PxkDataSource(mService, mBodyRequest, mPara);
        mTotalItem.postValue(mDataSource);
        return mDataSource;
    }

    public LiveData<PxkDataSource> getTotalItem() {
        return mTotalItem;
    }
}
