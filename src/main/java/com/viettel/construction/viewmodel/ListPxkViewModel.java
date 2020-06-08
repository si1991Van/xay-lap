package com.viettel.construction.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.StockTransRequest;
import com.viettel.construction.model.api.SynStockTransDTO;
import com.viettel.construction.model.paging.PxkFactorySource;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ListPxkViewModel extends ViewModel {

    private LiveData<PagedList<SynStockTransDTO>> mListData;
    private LiveData<Long> mTotalItem;
    private MutableLiveData<Boolean> mEmpty = new MutableLiveData<>();
    private RetrofitService mService;
    private Executor executor;
    private ArrayList<SynStockTransDTO> listSynStockSelected;

    public ListPxkViewModel() {
        mService = ApiManager.getRetrofitService();
        executor = Executors.newFixedThreadPool(5);
    }

    public void loadData(ParaFilterPaging para) {

        StockTransRequest body = new StockTransRequest();
        body.setSysUserRequest(VConstant.getUser());

        executor = Executors.newFixedThreadPool(5);
        PxkFactorySource factorySource = new PxkFactorySource(mService, body, para);
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(30)
                        .setPageSize(30).build();
        mTotalItem = Transformations.switchMap(factorySource.getTotalItem(),
                dataSource -> dataSource.getTotalItem());


        mListData = (new LivePagedListBuilder(factorySource, pagedListConfig))
                .setFetchExecutor(executor)
                .setBoundaryCallback(new PagedList.BoundaryCallback() {
                    @Override
                    public void onZeroItemsLoaded() {
                        super.onZeroItemsLoaded();
                        mEmpty.postValue(true);
                    }

                    @Override
                    public void onItemAtFrontLoaded(@NonNull Object itemAtFront) {
                        super.onItemAtFrontLoaded(itemAtFront);
                        mEmpty.postValue(false);
                    }

                    @Override
                    public void onItemAtEndLoaded(@NonNull Object itemAtEnd) {
                        super.onItemAtEndLoaded(itemAtEnd);
                    }
                })
                .build();

    }

    public void replaceSubscription(LifecycleOwner lifecycleOwner, ParaFilterPaging para) {
        mListData.removeObservers(lifecycleOwner);
        loadData(para);
    }

    public LiveData<Long> getTotalItem(){
        return mTotalItem;
    }


    public LiveData<PagedList<SynStockTransDTO>> getListData() {
        return mListData;
    }

    public LiveData<Boolean> getEmptyState(){
        return mEmpty;
    }

    public ArrayList<SynStockTransDTO> getListSynStockSelected() {
        return listSynStockSelected;
    }

    public void setListSynStockSelected(ArrayList<SynStockTransDTO> listSynStockSelected) {
        this.listSynStockSelected = listSynStockSelected;
    }
}
