package com.viettel.construction.server.retrofit;

import com.viettel.construction.model.api.StockTransRequest;
import com.viettel.construction.model.api.StockTransResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Single;

public interface RetrofitService {

    @POST("{endUrl}")
    Call<StockTransResponse> getListPxk(@Path(value = "endUrl", encoded = true) String fullUrl, @Body StockTransRequest body);
}
