package com.viettel.construction.model.paging;

import android.util.Log;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.StockTransRequest;
import com.viettel.construction.model.api.StockTransResponse;
import com.viettel.construction.model.api.SynStockTransDTO;
import com.viettel.construction.model.paging.abstraction.AbsDataSource;
import com.viettel.construction.server.retrofit.INetWorkCallBack;
import com.viettel.construction.server.retrofit.RetrofitService;
import com.viettel.construction.viewmodel.ParaFilterPaging;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;


public class PxkDataSource extends PageKeyedDataSource<Long, SynStockTransDTO> {

    private StockTransRequest mBodyRequest;
    private ParaFilterPaging mFilter;
    private RetrofitService mService;
    private MutableLiveData<Long> mTotalItem = new MutableLiveData<>();

    private void initParaBodyRequest() {
        mBodyRequest.setConstructionType(mFilter.isABill() ? "A" : "B");
        if (mFilter.isDefault()) {
            mBodyRequest.setConfirm(-1);
            mBodyRequest.setState(-1);
        } else if (mFilter.isSearch()) {
            mBodyRequest.setKeySearch(mFilter.getTextSearch());
            mBodyRequest.setState(mFilter.getState());
            mBodyRequest.setConfirm(mFilter.getConfirm());
            mBodyRequest.setOverDateKPI(mFilter.getOverDateKpi());
        } else {
            mBodyRequest.setOverDateKPI(mFilter.getOverDateKpi());
            mBodyRequest.setState(mFilter.getState());
            mBodyRequest.setConfirm(mFilter.getConfirm());
        }
    }

    public MutableLiveData<Long> getTotalItem() {
        return mTotalItem;
    }

    public PxkDataSource(RetrofitService service, StockTransRequest bodyRequest, ParaFilterPaging para) {
        mService = service;
        mBodyRequest = bodyRequest;
        mFilter = para;

        initParaBodyRequest();
    }


    /*public List<SynStockTransDTO> filterByStatus(boolean isABill, List<SynStockTransDTO> rootList, String s, boolean isState, boolean isOverKpi) {
        List<SynStockTransDTO> dataSearch = new ArrayList<>();
        if (mFilterData.isOverKpi() == false && mFilterData.getTypeFilter().equals("")) {
            dataSearch = Observable.from(rootList).filter(x -> x.getStockType().contains(mFilterData.isABill() ? "A" : "B"))
                    .toList().toBlocking().singleOrDefault(new ArrayList<>());
            return dataSearch;
        }
        if (isOverKpi) {
            dataSearch = Observable.from(rootList).filter(x -> x.getOverDateKPI() != null && x.getOverDateKPI().equals("1")
                    && x.getStockType().contains(isABill ? "A" : "B"))
                    .toList().toBlocking().singleOrDefault(new ArrayList<>());
        } else {
            if (isState) {
                dataSearch = Observable.from(rootList).filter(x -> x.getState() != null && x.getState().contains(s)
                        && x.getStockType().contains(isABill ? "A" : "B"))
                        .toList().toBlocking().singleOrDefault(new ArrayList<>());
            } else {

                dataSearch = Observable.from(rootList).filter(x -> x.getConfirm() != null && x.getConfirm().contains(s)
                        && x.getStockType().contains(isABill ? "A" : "B"))
                        .toList().toBlocking().singleOrDefault(new ArrayList<>());
            }
        }
        for (SynStockTransDTO item : rootList) {
            if (item.getState() != null && item.getState().contains(s)) dataSearch.add(item);
        }

        return dataSearch;
    }*/

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, SynStockTransDTO> callback) {
        mBodyRequest.setPage(1l);
        mBodyRequest.setPageSize(params.requestedLoadSize);
        mService.getListPxk(VConstant.END_URL_GET_LIST_SYN_STOCK_TRANS_DTO, mBodyRequest).enqueue(new Callback<StockTransResponse>() {
            @Override
            public void onResponse(Call<StockTransResponse> call, Response<StockTransResponse> response) {
                if (response != null && response.body().resultInfo != null && response.body().resultInfo.getStatus().equals("OK")) {
                    if (response.body().getLstSynStockTransDto() != null) {
                        mTotalItem.postValue(response.body().getTotal());
                        callback.onResult(response.body().getLstSynStockTransDto(), null, 2l);
                        mBodyRequest.setPage(2);
                    }
                } else {
                    mTotalItem.postValue(0l);
                }

            }

            @Override
            public void onFailure(Call<StockTransResponse> call, Throwable t) {
                mTotalItem.postValue(0l);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, SynStockTransDTO> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, SynStockTransDTO> callback) {

        mService.getListPxk(VConstant.END_URL_GET_LIST_SYN_STOCK_TRANS_DTO, mBodyRequest).enqueue(new Callback<StockTransResponse>() {
            @Override
            public void onResponse(Call<StockTransResponse> call, Response<StockTransResponse> response) {
                if (response != null && response.body().resultInfo != null && response.body().resultInfo.getStatus().equals("OK")) {
                    if (response.body().getLstSynStockTransDto() != null) {
                        mBodyRequest.setPage(params.key + 1);
                        callback.onResult(response.body().getLstSynStockTransDto(), params.key + 1);
                    }
                }

            }

            @Override
            public void onFailure(Call<StockTransResponse> call, Throwable t) {
            }
        });
    }



    /*List<SynStockTransDTO> data = new ArrayList<>();
        if (result.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
        if (result.getLstSynStockTransDto().size() != 0) {
            List<SynStockTransDTO> list = result.getLstSynStockTransDto();
            if (isABill) {
                data =
                        Observable.from(list).filter(item -> item.getStockType().contains("A"))
                                .toList().toBlocking().single();
            } else {
                data = Observable.from(list)
                        .filter(item -> item.getStockType()
                                .contains("B")).toList().toBlocking().single();
            }
        }
    }
        return data;*/

}
