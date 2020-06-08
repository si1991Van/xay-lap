package com.viettel.construction.server.api;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.viettel.construction.common.App;
import com.viettel.construction.common.PrefManager;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.BgmbTaskUpdateRequest;
import com.viettel.construction.model.RequestStockTrans;
import com.viettel.construction.model.ResultApiBgmbDashBoard;
import com.viettel.construction.model.ResultUserBgmb;
import com.viettel.construction.model.api.AppParamDTORequest;
import com.viettel.construction.model.api.AuthenticationInfo;
import com.viettel.construction.model.api.BgmbRequestGetListImage;
import com.viettel.construction.model.api.BgmbRequestListDTO;
import com.viettel.construction.model.api.CategoryComplete;
import com.viettel.construction.model.api.CategoryConstructionApi;
import com.viettel.construction.model.api.CategoryResult;
import com.viettel.construction.model.api.CategoryUserRequest;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailDTO;
import com.viettel.construction.model.api.ConstructionAcceptanceDTORequest;
import com.viettel.construction.model.api.ConstructionBGMBDTO;
import com.viettel.construction.model.api.ConstructionBGMBResponse;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.api.ConstructionMerchandiseDTORequest;
import com.viettel.construction.model.api.ConstructionScheduleDTO;
import com.viettel.construction.model.api.ConstructionScheduleDTORequest;
import com.viettel.construction.model.api.ConstructionScheduleItemDTO;
import com.viettel.construction.model.api.ConstructionStationWorkItem;
import com.viettel.construction.model.api.ConstructionTaskDTO;
import com.viettel.construction.model.api.ConstructionTaskDailyDTO;
import com.viettel.construction.model.api.ConstructionTaskDetailDTO;
import com.viettel.construction.model.api.Employee;
import com.viettel.construction.model.api.EmployeeResult;
import com.viettel.construction.model.api.ListConstructionStationWork;
import com.viettel.construction.model.api.ListConstructionTaskAll;
import com.viettel.construction.model.api.ResultApi;
import com.viettel.construction.model.api.ResultUser;
import com.viettel.construction.model.api.ResultUserStation;
import com.viettel.construction.model.api.StockTransRequest;
import com.viettel.construction.model.api.StockTransResponse;
import com.viettel.construction.model.api.SynStockTransDTO;
import com.viettel.construction.model.api.SynStockTransDetailDTO;
import com.viettel.construction.model.api.SysUserDTO;
import com.viettel.construction.model.api.SysUserRequest;
import com.viettel.construction.model.api.UpdateApi;
import com.viettel.construction.model.api.WorkItemDetailDTO;
import com.viettel.construction.model.api.WorkItemDetailDTORequest;
import com.viettel.construction.model.api.WorkItemResult;
import com.viettel.construction.model.api.WorkOnDayApi;
import com.viettel.construction.model.api.create.Create;
import com.viettel.construction.model.api.entangle.EntangleManageDTO;
import com.viettel.construction.model.api.entangle.EntangleManageDTORequest;
import com.viettel.construction.model.api.entangle.TioVolumeDTORequest;
import com.viettel.construction.model.api.history.HandOverHistoryDTORequest;
import com.viettel.construction.model.api.history.StTransactionDTO;
import com.viettel.construction.model.api.history.StTransactionDetailDTO;
import com.viettel.construction.model.api.issue.IssueDTO;
import com.viettel.construction.model.api.issue.IssueHistoryEntityDTO;
import com.viettel.construction.model.api.issue.IssueWorkItemDTO;
import com.viettel.construction.model.api.issue.IssuseRequest;
import com.viettel.construction.model.api.login.BaseRequest;
import com.viettel.construction.model.api.update.ConstructionTaskDTOUpdateRequest;
import com.viettel.construction.model.api.version.AppParamDTO;
import com.viettel.construction.model.api.version.AppParamDTOResponse;
import com.viettel.construction.model.constructionextra.ConstructionExtraDTOImageRequest;
import com.viettel.construction.model.constructionextra.ConstructionExtraDTORequest;
import com.viettel.construction.model.constructionextra.ConstructionIDExtraDTORequest;
import com.viettel.construction.server.retrofit.RetrofitService;
import com.viettel.construction.server.service.ApiClient;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.server.service.IServerResultListener;
import com.viettel.construction.server.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiManager {
    public static ApiManager instance;
    private Context mContext;
    private final String TAG = "VTApiManager";

    public static RetrofitService mRetrofitService;

    private ApiManager() {

    }

    public static ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    public static RetrofitService getRetrofitService() {
        if (mRetrofitService == null) {
            mRetrofitService = create();
        }
        return mRetrofitService;
    }

    public static RetrofitService create() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(VConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit.create(RetrofitService.class);
    }

    public String getParamsRequest(Map<String, String> paramsRequest) {
        JSONObject params = new JSONObject();
        try {
            params.put("appid", "vt");
            for (String key : paramsRequest.keySet())
                params.put(key, paramsRequest.get(key));
        } catch (JSONException e) {
            return null;
        }
        return params.toString();
    }

//    public String getUrl(String port, String api) {
//        StringBuilder sb = new StringBuilder(VConstant.BASE_URL);
//        // get ip
//        if (StringUtil.isNullOrEmpty(port))
//            sb.append("VConstant.PORT_8082");
//        else
//            sb.append(port);
//        // get api
//        if (!StringUtil.isNullOrEmpty(api))
//            sb.append(api);
//        return sb.toString();
//    }


    public String getUrl(String endApi) {
        StringBuilder sb = new StringBuilder(VConstant.BASE_URL);
        // get ip
        if (StringUtil.isNullOrEmpty(endApi))
            sb.append("");
        else
            sb.append(endApi);
        return sb.toString();
    }

//    public String getUrl2(String endApi) {
//        StringBuilder sb = new StringBuilder(VConstant.BASE_URL);
//        // get ip
//        if (StringUtil.isNullOrEmpty(endApi))
//            sb.append("");
//        else
//            sb.append(endApi);
//        return sb.toString();
//    }

    public String appendParamsRequest(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && !params.isEmpty()) {
            int size = params.size();
            int count = 0;
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key));
                count++;
                if (count < size)
                    sb.append("&");
            }
        }
        return sb.toString();
    }

    public Map<String, String> getHeader() {
        Map<String, String> headers = new HashMap<>();
        //save to local
//        String authenToken = PrefManager.getInstance().getString(VConstant.REMEMBER_AUTHEN_TOKEN);
        String authenToken = "my_token";
        if (!StringUtil.isNullOrEmpty(authenToken))
            headers.put("my request VConstant.PORT_8082", authenToken);
        return headers;
    }

    public <T> void detailCountDashboard(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_DETAIL_DASHBOARD_CV);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , ResultApi.class, header
                , convertModelRequestBody()
                , requestListener);
    }

    public <T> void getCompleteStateConstructionTask(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_DASHBOARD_HOME_CV);
        Log.d(TAG, "hshc url: " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , ResultApi.class, header
                , convertModelRequestBody()
                , requestListener);
    }

    public <T> void categoryCountDashboard(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_Category_DASHBOARD);
        Log.d(TAG, "categoryCountDashboard - url : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestBodyForCategoryDashboard()
                , requestListener);
    }

    public <T> void getConstructionBGMBStatus(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_CONSTRUCTION_BGMB_STATUS_FOR_DASHBOARD);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , ResultApiBgmbDashBoard.class, header
                , convertModelRequestBodyDashBoardBgmb()
                , requestListener);
        Log.d(TAG, "getConstructionBGMBStatus - url: " + url + " - json : " + convertModelRequestBodyDashBoardBgmb());
    }

    private String convertModelRequestBodyDashBoardBgmb() {
        Gson gson = new Gson();
        ResultUserBgmb info = new ResultUserBgmb(VConstant.getUser().getSysUserId());
        String json = gson.toJson(info);
        return json;
    }

    public <T> void getDataFromServer(APIType apiType, final Class<T> responseClass,
                                      final IServerResultListener<T> requestListener, final Object... parram) {
        switch (apiType) {
            case END_URL_GET_CONSTRUCTION_MANAGEMENT:
                getConstructionManagement1(Integer.valueOf(parram[0].toString()),
                        responseClass, requestListener);
                break;
            case END_DETAIL_CONSTRUCTION:
                getValueConstructionManagement1(parram[0].toString(),
                        ConstructionScheduleDTO.class.cast(parram[1]), responseClass, requestListener);
                break;

            case END_LIST_CONSTRUCTION_SCHELUDE_WORK_ITEM:
                getListScheduleWorkItem1(parram[0].toString(),
                        ConstructionScheduleItemDTO.class.cast(parram[1]),
                        responseClass, requestListener);
                break;
            case END_URL_LIST_PERFORM:
                getListPerformWork1(responseClass, requestListener);
                break;
            case END_URL_LIST_SUPERVISE_WORK:
                getListSuperviseWork1(responseClass, requestListener);
                break;
            case END_URL_LIST_REFLECT:
                getListReflect1(responseClass, requestListener);
                break;
            case END_URL_GET_WORK_ON_DAY:
                getWorkOnDay1(responseClass, requestListener);
                break;
            case END_URL_GET_LIST_COMPLETE_CATEGORY:
                getListWorkCompleted1(responseClass, requestListener);
                break;
            case END_URL_GET_LIST_SYN_STOCK_TRANS_DTO:
                getListSynStockTransDTO1(responseClass, requestListener);
                break;
            case END_URL_GET_LIST_ENTANGLE_MANAGE:
                getListEntangle1(responseClass, requestListener);
                break;
            case END_VOL_TIO:
//                getListEntangle1(responseClass,requestListener);
                getListVolTio(responseClass, requestListener);

            case END_URL_GET_LIST_REFUND_LEVER_1:
                getListRefundLever11(responseClass, requestListener);
                break;

            case END_URL_GET_LIST_REFUND_LEVER_2:
                getListRefundLever21(ConstructionMerchandiseDTORequest.class.cast(parram[0])
                        , responseClass, requestListener);
                break;

            case END_URL_ACCEPTANCE_LEVER_1:
                callDataListAcceptanceLever11(responseClass, requestListener);
                break;

            case END_URL_ACCEPTANCE_LEVER_2:
                getListAcceptanceLevel21(
                        ConstructionAcceptanceCertDetailDTO.class.cast(parram[0])
                        , responseClass, requestListener);
                break;
            case END_HANDOVER_HISTORY:
                getListHistoryHandOver1(responseClass, requestListener);
                break;
            case END_URL_GET_CONSTRUCTION_BGMB_LIST_BY_STATUS:
                getListBgmb(responseClass, requestListener);
                break;
        }

    }


    public <T> void getCountDeliveryBill(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_COUNT_DELIVERY_BILL);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , ResultApi.class, header
                , convertModelRequestBody()
                , requestListener);
    }


    public <T> void getListPerformWork(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_LIST_PERFORM_AND_SUPERVISE_WORK);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , ListConstructionTaskAll.class, header
                , convertModelRequestBodyForPerformWork()
                , requestListener);
    }

    public <T> void getListPerformWork1(final Class<T> clazz, final IServerResultListener requestListener) {
        String url = getUrl(VConstant.END_URL_LIST_PERFORM_AND_SUPERVISE_WORK);
        Log.d(TAG, "end url getListPerformWork1: " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post1(url
                , ListConstructionTaskAll.class, header
                , convertModelRequestBodyForPerformWork()
                , requestListener);
    }

    public <T> void getListSuperviseWork(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_LIST_PERFORM_AND_SUPERVISE_WORK);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , ListConstructionTaskAll.class, header
                , convertModelRequestBodyForSupervisemWork()
                , requestListener);
    }

    public <T> void getListSuperviseWork1(final Class<T> clazz, final IServerResultListener requestListener) {
        String url = getUrl(VConstant.END_URL_LIST_PERFORM_AND_SUPERVISE_WORK);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post1(url
                , ListConstructionTaskAll.class, header
                , convertModelRequestBodyForSupervisemWork()
                , requestListener);
    }

    public <T> void getListConstructionByID(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_LIST_CONSTRUCTION_BY_ID);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , ListConstructionStationWork.class, header
                , convertModel()
                , requestListener);
    }

    public <T> void getListConstruction2ByID(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_LIST_CONSTRUCTION_2);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , ListConstructionStationWork.class, header
                , convertModel()
                , requestListener);
    }

//    public <T> void updateWorkItem(final Class<T> clazz, final IOnRequestListener requestListener) {
//        String url = VConstant.URL_UPDATE_WORK_ITEM;
//        Map<String, String> header = new HashMap<>();
//        header.put("Content-Type", "application/json");
//        ApiClient.getInstance().post(url
//                , WorkItemResult.class, header
//                , convertWorkItem()
//                , requestListener);
//    }


    //get list category construction
    public <T> void getNameWorkItemByConstruction(final Class<T> clazz, final IOnRequestListener requestListener, long idConstruction) {
        String url = getUrl(VConstant.END_URL_LIST_CATEGORY);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , ListConstructionStationWork.class, header
                , convertModelCategoryConstruction(idConstruction)
                , requestListener);
    }

    public <T> void getListWorkWithCategory(final Class<T> clazz, final IOnRequestListener requestListener, long idConstruction, long workItemId) {
        String url = getUrl(VConstant.END_URL_GET_NAME_CAT_TASK);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , ListConstructionStationWork.class, header
                , convertModelWorkWithConstruction(idConstruction, workItemId)
                , requestListener);
    }

    public <T> void getVersionApp(int flag, AppParamDTO appParamDTO, SysUserRequest sysUserReceiver,  final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_CHECK_VERSION);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , AppParamDTOResponse.class, header
                , coverModelForAppParam(flag, appParamDTO, sysUserReceiver)
                , requestListener);
    }

    private String coverModelForAppParam(int flag, AppParamDTO appParamDTO, SysUserRequest sysUserReceiver) {
        Gson gson = new Gson();
        AppParamDTORequest appParamDTORequest = new AppParamDTORequest();
        SysUserRequest sysUserRequest = VConstant.getUser();
        sysUserRequest.setFlag(flag);
        appParamDTORequest.setSysUserRequest(sysUserRequest);
        appParamDTORequest.setAppParamDTO(appParamDTO);
        String json = gson.toJson(appParamDTORequest);
        Log.d(TAG, "convertModelAppParamDTO - json : " + json);
        return json;
    }

    public <T> void getListWorkComplete(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_LIST_COMPLETE_CATEGORY);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , WorkItemResult.class, header
                , convertModelForCategoryComplete()
                , requestListener);
    }

    public <T> void getListWorkCompleted1(final Class<T> clazz, final IServerResultListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_LIST_COMPLETE_CATEGORY);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post1(url
                , clazz, header
                , convertModelForCategoryComplete()
                , requestListener);
    }

    private String convertModelForCategoryComplete() {
        Gson gson = new Gson();
        SysUserRequest userRequest = VConstant.getUser();
        CategoryResult info = new CategoryResult(
                userRequest.getAuthenticationInfo(),
                userRequest.getSysUserId(),
                userRequest.getSysGroupId()
        );
        String json = gson.toJson(info);
        return json;
    }

    public <T> void getListUserByDepartment(final Class<T> clazz, long lastShiperId, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_LIST_USER_BY_DEPARTMENT_BY_ID);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , EmployeeResult.class, header
                , convertModelRequestBodyForEmployee(lastShiperId)
                , requestListener);
    }

    public <T> void getListSynStockTransDTO(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_LIST_SYN_STOCK_TRANS_DTO);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , StockTransResponse.class, header
                , convertModelRequestGetListSynStockTransDTO()
                , requestListener);
    }

    public <T> void getListSynStockTransDTO1(final Class<T> clazz, final IServerResultListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_LIST_SYN_STOCK_TRANS_DTO);
        Log.d(TAG, "getListSynStockTransDTO1 - url : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post1(url
                , StockTransResponse.class, header
                , convertModelRequestGetListSynStockTransDTO()
                , requestListener);
    }

    private String convertModel() {
        Gson gson = new Gson();
        SysUserRequest request = VConstant.getUser();
        ResultUserStation info = new ResultUserStation(
                request.getAuthenticationInfo(),
                request.getSysUserId(),
                request.getDepartmentId(),
                request.getSysGroupId()
        );
        String json = gson.toJson(info);
        return json;
    }

//    private String convertWorkItem() {
//        Gson gson = new Gson();
//        WorkApi workApi = new WorkApi(new SysUserRequest(new AuthenticationInfo("anhtien", "123", "1.0"), 111111, 166571), new WorkItemDetailDTO(68 + ""));
//        String json = gson.toJson(workApi);
//        return json;
//    }

    private String convertModelCategoryConstruction(long idConstructon) {
        Gson gson = new Gson();
        SysUserRequest request = VConstant.getUser();
        CategoryConstructionApi info = new CategoryConstructionApi(new SysUserRequest(
                request.getAuthenticationInfo()
                , request.getSysUserId()
                , request.getDepartmentId())
                , new ConstructionStationWorkItem(idConstructon));
        String json = gson.toJson(info);
        return json;
    }

    private String convertModelWorkWithConstruction(long idConstruction, long workItemId) {
        Gson gson = new Gson();
        SysUserRequest request = VConstant.getUser();
        CategoryConstructionApi info = new CategoryConstructionApi(
                new SysUserRequest(
                        request.getAuthenticationInfo()
                        , request.getSysUserId()
                        , request.getDepartmentId()), new ConstructionStationWorkItem(idConstruction, workItemId));
        String json = gson.toJson(info);
        return json;
    }

    private String convertModelRequestBodyForPerformWork() {
        Gson gson = new Gson();
        SysUserRequest sysUserRequest = VConstant.getUser();
        sysUserRequest.setFlag(1);
        ResultUser info = new ResultUser(sysUserRequest.getAuthenticationInfo(),
                sysUserRequest.getSysUserId(),
                sysUserRequest.getDepartmentId(),
                sysUserRequest.getFlag());
        String json = gson.toJson(info);
        Log.d(TAG, "convertModelRequestBodyForPerformWork: " + json);
        return json;
    }

    private String convertModelRequestBodyForSupervisemWork() {
        Gson gson = new Gson();
        SysUserRequest sysUserRequest = VConstant.getUser();
        sysUserRequest.setFlag(2);
        ResultUser info = new ResultUser(sysUserRequest.getAuthenticationInfo(),
                sysUserRequest.getSysUserId(),
                sysUserRequest.getDepartmentId(),
                sysUserRequest.getFlag());
        String json = gson.toJson(info);
        return json;
    }

    private String convertModelRequestBody() {
        Gson gson = new Gson();
        ResultUser info = new ResultUser(VConstant.getUser().getAuthenticationInfo()
                , VConstant.getUser().getSysUserId()
                , VConstant.getUser().getDepartmentId());
        String json = gson.toJson(info);
        return json;
    }

    private String convertModelRequestBodyForCategoryDashboard() {
        Gson gson = new Gson();
        SysUserRequest request = VConstant.getUser();
        CategoryUserRequest data = new CategoryUserRequest();
        data.setSysUserRequest(request);
        String json = gson.toJson(data);
        Log.d(TAG, "convertModelRequestBodyForCategoryDashboard - json : " + json);
        return json;
    }

    private String convertModelRequestBodyForEmployee(long lastShiperId) {
        Gson gson = new Gson();
        SysUserRequest request = VConstant.getUser();
        Employee info = new Employee();
        info.setAuthenticationInfo(request.getAuthenticationInfo());
        info.setSysUserId(request.getSysUserId());
        info.setDepartmentId(request.getDepartmentId());
        info.setSysGroupId(request.getSysGroupId());
        info.setAuthorities(App.getInstance().getAuthor());
        if(lastShiperId != -1){
            info.setLastShipperId(lastShiperId);
        }
        String json = gson.toJson(info);
        return json;
    }

    private String  convertModelForUpdateConstruction(Double process,
                                                     List<ConstructionImageInfo> listConstructionImageInfo,
                                                     ConstructionTaskDTO constructionDTO,
                                                     int flag, String email, String phone,
                                                     long sysUserId, long sysGroupId, String des) {
        constructionDTO.setPerformerId(sysUserId);
        constructionDTO.setDescription(des);
        constructionDTO.setCompletePercent(String.valueOf(process));
        SysUserRequest sysUserRequest = VConstant.getUser();
        ConstructionTaskDTOUpdateRequest request;
        Gson gson = new Gson();
        String json;
        if (flag == 1) {
//            constructionTaskDTOUpdateRequest = new ConstructionTaskDTOUpdateRequest(listConstructionImageInfo, VConstant.getUser(), constructionDTO, new SysUserDTO(phone, email, sysUserId, sysGroupId), flag);
            request = new ConstructionTaskDTOUpdateRequest();
            request.setListConstructionImageInfo(listConstructionImageInfo);
            request.setSysUserRequest(sysUserRequest);
            request.setConstructionTaskDTO(constructionDTO);
            request.setSysUserDto(new SysUserDTO(phone, email, sysUserId, sysGroupId));
            request.setFlag(flag);
            return json = gson.toJson(request);
        } else {
//            constructionTaskDTOUpdateRequest = new ConstructionTaskDTOUpdateRequest(listConstructionImageInfo, VConstant.getUser(), constructionDTO, new SysUserDTO(phone, email, sysUserId, sysGroupId), flag);
            request = new ConstructionTaskDTOUpdateRequest();
            request.setListConstructionImageInfo(listConstructionImageInfo);
            request.setSysUserRequest(sysUserRequest);
            request.setConstructionTaskDTO(constructionDTO);
            request.setSysUserDto(new SysUserDTO(phone, email, sysUserId, sysGroupId));
            request.setFlag(flag);
            return json = gson.toJson(request);
        }
    }

    private String convertModelForUpdateConstructionAppParam(List<AppParamDTO> listParam, Double process, List<ConstructionImageInfo> listConstructionImageInfo, ConstructionTaskDTO constructionDTO, int flag, String email, String phone, long sysUserId, long sysGroupId, String des) {
        constructionDTO.setPerformerId(sysUserId);
        constructionDTO.setDescription(des);
        constructionDTO.setCompletePercent(String.valueOf(process));
        ConstructionTaskDTOUpdateRequest request;
        SysUserRequest sysUserRequest = VConstant.getUser();
        Gson gson = new Gson();
        String json;
        if (flag == 1) {
//            constructionTaskDTOUpdateRequest = new ConstructionTaskDTOUpdateRequest(listConstructionImageInfo, VConstant.getUser(), constructionDTO, new SysUserDTO(phone, email, sysUserId, sysGroupId), flag);
            request = new ConstructionTaskDTOUpdateRequest();
            request.setListConstructionImageInfo(listConstructionImageInfo);
            request.setSysUserRequest(sysUserRequest);
            request.setConstructionTaskDTO(constructionDTO);
            request.setListParamDto(listParam);
            request.setSysUserDto(new SysUserDTO(phone, email, sysUserId, sysGroupId));
            request.setFlag(flag);

            //check log request
            ConstructionTaskDTOUpdateRequest request1;
            request1 = new ConstructionTaskDTOUpdateRequest();
            request1.setSysUserRequest(sysUserRequest);
            request1.setConstructionTaskDTO(constructionDTO);
            request1.setListParamDto(listParam);
            request1.setSysUserDto(new SysUserDTO(phone, email, sysUserId, sysGroupId));
            request1.setFlag(flag);
            Log.d(TAG, "convertModelForUpdateConstructionAppParam - flag =1 - json : " + gson.toJson(request1));
            return json = gson.toJson(request);
        } else {
//            constructionTaskDTOUpdateRequest = new ConstructionTaskDTOUpdateRequest(listConstructionImageInfo, VConstant.getUser(), constructionDTO, new SysUserDTO(phone, email, sysUserId, sysGroupId), flag);
            request = new ConstructionTaskDTOUpdateRequest();
            request.setListConstructionImageInfo(listConstructionImageInfo);
            request.setSysUserRequest(sysUserRequest);
            request.setListParamDto(listParam);
            request.setConstructionTaskDTO(constructionDTO);
            request.setSysUserDto(new SysUserDTO(phone, email, sysUserId, sysGroupId));
            request.setFlag(flag);
            //check log request
            ConstructionTaskDTOUpdateRequest request2;
            request2 = new ConstructionTaskDTOUpdateRequest();
            request2.setSysUserRequest(sysUserRequest);
            request2.setConstructionTaskDTO(constructionDTO);
            request2.setListParamDto(listParam);
            request2.setSysUserDto(new SysUserDTO(phone, email, sysUserId, sysGroupId));
            request2.setFlag(flag);
            Log.d(TAG, "convertModelForUpdateConstructionAppParam - flag else - json : " + gson.toJson(request2));
            return json = gson.toJson(request);
        }
    }

    //THANGTV24 - start 15022019 - update for gpon
    private String convertModelForUpdateConstructionAppParamGpon(Double current, Double total, String startingDateTK, String path, List<ConstructionImageInfo> listConstructionImageInfo, long sysGroupId,
                                                                 Double amount, long type, long createdUserId, long constructionTaskId, Double quantity, long workItemId, long catTaskId, long createdGroupId) {

        ConstructionTaskDTOUpdateRequest request;
        ConstructionTaskDailyDTO constructionTaskDailyDTO = new ConstructionTaskDailyDTO();
        constructionTaskDailyDTO.setAmount(amount);
        constructionTaskDailyDTO.setSysGroupId(sysGroupId);
        constructionTaskDailyDTO.setCatTaskId(catTaskId);
        constructionTaskDailyDTO.setType(type);
        constructionTaskDailyDTO.setSysGroupId(sysGroupId);
        constructionTaskDailyDTO.setConstructionTaskId(constructionTaskId);
        constructionTaskDailyDTO.setCreatedUserId(createdUserId);
        constructionTaskDailyDTO.setWorkItemId(workItemId);
        constructionTaskDailyDTO.setQuantity(quantity);
        constructionTaskDailyDTO.setCreatedGroupId(createdGroupId);
        constructionTaskDailyDTO.setCurrentAmount(current);
        constructionTaskDailyDTO.setTotalAmount(total);

        if (startingDateTK != null) {
            constructionTaskDailyDTO.setStartingDateTK(startingDateTK);
        }

        if (path != null) {
            constructionTaskDailyDTO.setPath(path);
        }


        Gson gson = new Gson();
        String json;
        request = new ConstructionTaskDTOUpdateRequest();
        request.setListConstructionImageInfo(listConstructionImageInfo);
        request.setConstructionTaskDailyDTO(constructionTaskDailyDTO);

        //log file json to check error
        Gson gson1 = new Gson();
        String json1;
        ConstructionTaskDTOUpdateRequest request1;
        request1 = new ConstructionTaskDTOUpdateRequest();
        request1.setConstructionTaskDailyDTO(constructionTaskDailyDTO);
        json1 = gson1.toJson(request1);
        Log.d(TAG, "convertModelForUpdateConstructionAppParamGpon - json1 : " + json1);
        Log.d(TAG, "convertModelForUpdateConstructionAppParamGpon - base64 : " + listConstructionImageInfo.get(0).getBase64String());
        // end

        return json = gson.toJson(request);
    }

    //THANGTV24 - update BGMB - end
    public <T> void updateConstructionTask1(String startingDateTK, String handoverDateBuildBGMB, String checkBGMB, Double process, List<ConstructionImageInfo> listImage,
                                            ConstructionTaskDTO constructionDTO,
                                            int flag, String email, String phone,
                                            long sysUserId, long sysGroupId, String des,
                                            final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_UPDATE_WORK);
        Map<String, String> header = new HashMap<>();
        Log.d(TAG, "updateConstructionTask1 url : " + url);
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , ResultApi.class, header
                , convertModelForUpdateConstruction1(startingDateTK, handoverDateBuildBGMB, checkBGMB, process, listImage, constructionDTO, flag,
                        email, phone, sysUserId, sysGroupId, des)
                , requestListener);
    }

    private String convertModelForUpdateConstruction1(String startingDateTK, String handoverDateBuildBGMB, String checkBGMB, Double process,
                                                      List<ConstructionImageInfo> listConstructionImageInfo,
                                                      ConstructionTaskDTO constructionDTO,
                                                      int flag, String email, String phone,
                                                      long sysUserId, long sysGroupId, String des) {
        constructionDTO.setCheckBGMB(checkBGMB);
        constructionDTO.setCheckBGMB(handoverDateBuildBGMB);
        constructionDTO.setStartingDateTK(startingDateTK);
        constructionDTO.setPerformerId(sysUserId);
        constructionDTO.setDescription(des);
        constructionDTO.setCompletePercent(String.valueOf(process));
        SysUserRequest sysUserRequest = VConstant.getUser();
        ConstructionTaskDTOUpdateRequest request;
        Gson gson = new Gson();
        String json;
        if (flag == 1) {
//            constructionTaskDTOUpdateRequest = new ConstructionTaskDTOUpdateRequest(listConstructionImageInfo, VConstant.getUser(), constructionDTO, new SysUserDTO(phone, email, sysUserId, sysGroupId), flag);
            request = new ConstructionTaskDTOUpdateRequest();
            request.setListConstructionImageInfo(listConstructionImageInfo);
            request.setSysUserRequest(sysUserRequest);
            request.setConstructionTaskDTO(constructionDTO);
            request.setSysUserDto(new SysUserDTO(phone, email, sysUserId, sysGroupId));
            request.setFlag(flag);
            json = gson.toJson(request);
            Log.d(TAG, "Model updateConstructionTask1: " + json);
            return json;
        } else {
//            constructionTaskDTOUpdateRequest = new ConstructionTaskDTOUpdateRequest(listConstructionImageInfo, VConstant.getUser(), constructionDTO, new SysUserDTO(phone, email, sysUserId, sysGroupId), flag);
            request = new ConstructionTaskDTOUpdateRequest();
            request.setListConstructionImageInfo(listConstructionImageInfo);
            request.setSysUserRequest(sysUserRequest);
            request.setConstructionTaskDTO(constructionDTO);
            request.setSysUserDto(new SysUserDTO(phone, email, sysUserId, sysGroupId));
            request.setFlag(flag);
            json = gson.toJson(request);
            Log.d(TAG, "Model updateConstructionTask1: " + json);
            return json;
        }
    }

    public <T> void updateConstructionTask(Double process, List<ConstructionImageInfo> listImage,
                                           ConstructionTaskDTO constructionDTO,
                                           int flag, String email, String phone,
                                           long sysUserId, long sysGroupId, String des,
                                           final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_UPDATE_WORK);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , ResultApi.class, header
                , convertModelForUpdateConstruction(process, listImage, constructionDTO, flag,
                        email, phone, sysUserId, sysGroupId, des)
                , requestListener);
    }

    //test AppParam
    public <T> void updateConstructionTaskAppParam(List<AppParamDTO> listParam, Double process, List<ConstructionImageInfo> listImage, ConstructionTaskDTO constructionDTO, int flag, String email, String phone, long sysUserId, long sysGroupId, String des, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_UPDATE_WORK);
        Log.d(TAG, "updateConstructionTaskAppParam - url : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , ResultApi.class, header
                , convertModelForUpdateConstructionAppParam(listParam, process, listImage, constructionDTO, flag, email, phone, sysUserId, sysGroupId, des)
                , requestListener);
    }


    //THANG TV24 - start 15022019 update for gpon
    public <T> void updateConstructionTaskAppParamGpon(Double current, Double total, String startingDateTK, String path, List<ConstructionImageInfo> listImage, long sysGroupId, Double amount, long type, long createdUserId,
                                                       long constructionTaskId, Double quantity, long workItemId, long catTaskId, long createdGroupId,
                                                       final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_INSERT_CONTRUCTION_TASK_DAILY);
        Log.d(TAG, "updateConstructionTaskAppParamGpon - url : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , ResultApi.class, header
                , convertModelForUpdateConstructionAppParamGpon(current, total, startingDateTK, path, listImage, sysGroupId,
                        amount, type, createdUserId, constructionTaskId, quantity, workItemId, catTaskId, createdGroupId)
                , requestListener);
    }

    public <T> void updateForStop(ConstructionTaskDTO dto,
                                  final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_UPDATE_STOP_WORK);
        Log.d(TAG, "DashboardCVStopReflec UpDate - url : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , ResultApi.class, header
                , convertModelStopWork(dto)
                , requestListener);
    }

    private String convertModelStopWork(ConstructionTaskDTO dto) {
        Gson gson = new Gson();
        SysUserRequest request = VConstant.getUser();

        UpdateApi info = new UpdateApi(new SysUserRequest(
                request.getAuthenticationInfo(), request.getSysUserId(), request.getDepartmentId(),
                request.getSysGroupId())
                , dto);
        String json = gson.toJson(info);
        Log.d(TAG, "DashboardCVStopReflec Update body: " + json);
        return json;
    }

    private String convertModelRequestWorkOnDay() {
        Gson gson = new Gson();
        SysUserRequest sysUserRequest = VConstant.getUser();
        sysUserRequest.setFlag(2);
        WorkOnDayApi info = new WorkOnDayApi(
                sysUserRequest.getAuthenticationInfo(),
                sysUserRequest.getDepartmentId(),
                sysUserRequest.getFlag(),
                sysUserRequest.getSysGroupId(),
                sysUserRequest.getSysUserId()
        );
        String json = gson.toJson(info);
        return json;
    }

    public <T> void getWorkOnDay(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_WORK_ON_DAY);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestWorkOnDay()
                , requestListener);
    }

    public <T> void getWorkOnDay1(final Class<T> clazz, final IServerResultListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_WORK_ON_DAY);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post1(url
                , clazz, header
                , convertModelRequestWorkOnDay()
                , requestListener);
    }

    public <T> void updateWork(WorkItemDetailDTO workItemDetailDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_UPDATE_WORK_ITEM);
        Log.d(TAG, "update - hoanthanh - url :  " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestUpdateWork(workItemDetailDTO)
                , requestListener);
    }

    private String convertModelRequestUpdateWork(WorkItemDetailDTO workItemDetailDTO) {
        Gson gson = new Gson();
        WorkItemDetailDTORequest request = new WorkItemDetailDTORequest(VConstant.getUser(), workItemDetailDTO);
        String json = gson.toJson(request);
        Log.d(TAG, "update - convertModelRequestUpdateWork - json :  " + json);
        return json;

    }
//    private String convertModelRequestCreateNewHSHC(ConstructionTaskDetailDTO constructionTaskDetailDTO) {
//        Gson gson = new Gson();
//        //WorkItemDetailDTORequest request = new WorkItemDetailDTORequest(VConstant.getRequest(), workItemDetailDTO);
//        //String json = gson.toJson(request);
//        return json;
//    }

    public <T> void loadImage(ConstructionTaskDTO constructionTaskDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_LIST_IMAGE);
        Log.d(TAG, "loadImageFromServer url: " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestLoadImage(constructionTaskDTO)
                , requestListener);
    }

    //THANGTV24 - start16022019- load image for gpon
    public <T> void loadImageGpon(long type, long constructionTaskDailyId, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_IMAGE_CONSTRUCTION_IMAGE_DAILY);
        Log.d(TAG, "loadImageGpon - url : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestLoadImageGpon(type, constructionTaskDailyId)
                , requestListener);
    }
    //THANGTV24 - load image BGMB - start

    private String convertModelRequestLoadImageBGMB(long id) {
        Gson gson = new Gson();
        BgmbRequestGetListImage requestGetListImage = new BgmbRequestGetListImage();
        requestGetListImage.setAssignHandoverId(id);
        String json = gson.toJson(requestGetListImage);
        return json;

    }

    public <T> void loadImageBGMB(long id, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_CONSTRUCTION_BGMB_LIST_IMAGE);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestLoadImageBGMB(id)
                , requestListener);
        Log.d(TAG, "loadImageBGMB -  url : " + url + " - convertModelRequestLoadImageBGMB : " + convertModelRequestLoadImageBGMB(id));
    }

    public <T> void loadImageBGMBTuyen(long id, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_IMAGE_CONSTRUCTION_IMAGE_TUYEN);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestLoadImageBGMB(id)
                , requestListener);
        Log.d(TAG, "loadImageBGMBTuyen -  url : " + url + " - convertModelRequestLoadImageBGMB : " + convertModelRequestLoadImageBGMB(id));
    }

    //THANGTV24 - load image BGMB - end

    public <T> void loadImageEntangle(EntangleManageDTO entangleManageDTO,
                                      final Class<T> clazz,
                                      final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_LIST_IMAGE_ENTANGLE);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestLoadImageEntangle(entangleManageDTO)
                , requestListener);
    }

    public <T> void loadImageAcceptance(ConstructionAcceptanceDTORequest request, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_LIST_IMAGE_ACCEPTANCE);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestLoadImageAcceptance(request)
                , requestListener);
    }

    public <T> void getConstructionExtraInfoByID(ConstructionIDExtraDTORequest request,
                                                 final Class<T> clazz,
                                                 final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GetConstructionExtraInfoByID);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().postDate(url
                , clazz, header
                , new Gson().toJson(request)
                , requestListener);
    }

    public <T> void getImagesByConstructionExtraIDType(ConstructionExtraDTOImageRequest request,
                                                       final Class<T> clazz,
                                                       final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GetImagesByConstructionExtraIDType);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , new Gson().toJson(request)
                , requestListener);
    }

    public <T> void updateConstructionExtraInfo(ConstructionExtraDTORequest request,
                                                final Class<T> clazz,
                                                final IOnRequestListener requestListener) {
        GsonBuilder builder = new GsonBuilder();
        //Parse to json
        builder.registerTypeAdapter(Date.class, serializerDate);
        Gson gson = builder.create();

        String url = getUrl(VConstant.END_URL_UpdateConstructionExtraInfo);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , gson.toJson(request)
                , requestListener);
    }


    JsonSerializer<Date> serializerDate = new JsonSerializer<Date>() {
        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext
                context) {
            return src == null ? null : new JsonPrimitive(src.getTime());
        }
    };

    JsonDeserializer<Date> deserializerDate = new JsonDeserializer<Date>() {
        @Override
        public Date deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {
            return json == null ? null : new Date(json.getAsLong());
        }
    };


    public <T> void loadImageRefund(ConstructionMerchandiseDTORequest request, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_LIST_IMAGE_REFUND_DETAIL_LEVER_3);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestGetListRefundDetailLever3(request)
                , requestListener);
    }

    private String convertModelRequestLoadImageAcceptance(ConstructionAcceptanceDTORequest request) {
        Gson gson = new Gson();
        String json = gson.toJson(request);
        return json;

    }

    private String convertModelRequestLoadImage(ConstructionTaskDTO constructionTaskDTO) {
        Gson gson = new Gson();
//        ConstructionTaskDTOUpdateRequest constructionTaskDTOUpdateRequest = new ConstructionTaskDTOUpdateRequest(VConstant.getUser(), constructionTaskDTO);
        ConstructionTaskDTOUpdateRequest constructionTaskDTOUpdateRequest = new ConstructionTaskDTOUpdateRequest();
        constructionTaskDTOUpdateRequest.setSysUserRequest(VConstant.getUser());
        constructionTaskDTOUpdateRequest.setConstructionTaskDTO(constructionTaskDTO);
        String json = gson.toJson(constructionTaskDTOUpdateRequest);
        Log.d(TAG, "loadImageFromServer body: " + json);
        return json;

    }

    private String convertModelRequestLoadImageGpon(long type, long constructionTaskDailyId) {
        Gson gson = new Gson();
//        ConstructionTaskDTOUpdateRequest constructionTaskDTOUpdateRequest = new ConstructionTaskDTOUpdateRequest(VConstant.getUser(), constructionTaskDTO);
        ConstructionTaskDTOUpdateRequest constructionTaskDTOUpdateRequest = new ConstructionTaskDTOUpdateRequest();
        ConstructionTaskDailyDTO constructionTaskDailyDTO = new ConstructionTaskDailyDTO();

        constructionTaskDailyDTO.setConstructionTaskId(constructionTaskDailyId);
        constructionTaskDailyDTO.setType(type);
        constructionTaskDTOUpdateRequest.setConstructionTaskDailyDTO(constructionTaskDailyDTO);
        String json = gson.toJson(constructionTaskDTOUpdateRequest);

        Log.d(TAG, "convertModelRequestLoadImageGpon - json : " + json);
        return json;

    }

    private String convertModelRequestLoadImageEntangle(EntangleManageDTO entangleManageDTO) {
        Gson gson = new Gson();
        EntangleManageDTORequest request = new EntangleManageDTORequest();
        SysUserRequest sysUserRequest = VConstant.getUser();
        request.setEntangleManageDTODetail(entangleManageDTO);
        request.setSysUserRequest(sysUserRequest);
        String json = gson.toJson(request);
        return json;

    }

    private String convertModelRequestCreateNewWork(ConstructionTaskDetailDTO constructionTaskDetailDTO) {
        Gson gson = new Gson();
        Create create = new Create();
        create.setConstructionTaskDetail(constructionTaskDetailDTO);
        create.setSysUserRequest(VConstant.getUser());
        String json = gson.toJson(create);
        return json;
    }

    public <T> void createNewWork(ConstructionTaskDetailDTO constructionTaskDetailDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_CREATE_NEW_WORK);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestCreateNewWork(constructionTaskDetailDTO)
                , requestListener);
    }

    public <T> void getListImageForCategoryComplete(WorkItemDetailDTO workItemDetailDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_LIST_IMAGE_CATEGORY_COMPLETE);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestGetListForCompleteCategory(workItemDetailDTO)
                , requestListener);
    }


    private String convertModelRequestGetListForCompleteCategory(WorkItemDetailDTO workItemDetailDTO) {
        Gson gson = new Gson();
        CategoryComplete categoryComplete = new CategoryComplete();
        categoryComplete.setSysUserRequest(VConstant.getUser());
        categoryComplete.setWorkItemDetailDTO(workItemDetailDTO);
        String json = gson.toJson(categoryComplete);
        return json;
    }

    public <T> void login(String username, String password, String version, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_LOGIN);
        Log.d(TAG, "login : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestGetListForCompleteCategory(username, password, version)
                , requestListener);
    }

    private String convertModelRequestGetListForCompleteCategory(String username, String password, String version) {
        Gson gson = new Gson();
        BaseRequest request = new BaseRequest();
        request.setAuthenticationInfo(new AuthenticationInfo(username, password, version));
        String json = gson.toJson(request);
        Log.d(TAG, "convertModelRequestGetListForCompleteCategory json : " + json);
        return json;
    }

    private String convertModelRequestGetListSynStockTransDTO() {
        Gson gson = new Gson();
        StockTransRequest request = new StockTransRequest();
        request.setSysUserRequest(VConstant.getUser());
        String json = gson.toJson(request);
        Log.d(TAG, "convertModelRequestGetListSynStockTransDTO - json : " + json);
        return json;
    }

    public <T> void getDetailSynStockTransDTO(SynStockTransDTO synStockTransDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_DETAIL_SYN_STOCK_TRANS_DTO);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestGetDetailSynStockTransDTO(synStockTransDTO)
                , requestListener);
    }

    private String convertModelRequestGetDetailSynStockTransDTO(SynStockTransDTO synStockTransDTO) {
        Gson gson = new Gson();
        RequestStockTrans requestStockTrans = new RequestStockTrans();
        SysUserRequest sysUserRequest = new SysUserRequest();
        sysUserRequest.setAuthenticationInfo(VConstant.getUser().getAuthenticationInfo());
        sysUserRequest.setSysUserId(VConstant.getUser().getSysUserId());
        sysUserRequest.setDepartmentId(VConstant.getUser().getDepartmentId());
        requestStockTrans.setSysUserRequest(sysUserRequest);

        requestStockTrans.setSynStockTransDto(synStockTransDTO);
        String json = gson.toJson(requestStockTrans);
        return json;
    }

    public <T> void getListMerEntityDTO(SynStockTransDetailDTO synStockTransDetailDTO, SynStockTransDTO synStockTransDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_LIST_MER_ENTITY_DTO);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestGetListMerEntityDTO(synStockTransDetailDTO, synStockTransDTO)
                , requestListener);
    }

    private String convertModelRequestGetListMerEntityDTO(SynStockTransDetailDTO synStockTransDetailDTO, SynStockTransDTO synStockTransDTO) {
        Gson gson = new Gson();
        RequestStockTrans requestStockTrans = new RequestStockTrans();

        SysUserRequest sysUserRequest = new SysUserRequest();
        sysUserRequest.setAuthenticationInfo(VConstant.getUser().getAuthenticationInfo());
        sysUserRequest.setSysUserId(VConstant.getUser().getSysUserId());
        sysUserRequest.setDepartmentId(VConstant.getUser().getDepartmentId());
        requestStockTrans.setSysUserRequest(sysUserRequest);
        requestStockTrans.setSynStockTransDto(synStockTransDTO);
        requestStockTrans.setSynStockTransDetailDto(synStockTransDetailDTO);
        String json = gson.toJson(requestStockTrans);
        return json;
    }

    public <T> void getListDebt(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_DEBT);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestGetDebt()
                , requestListener);
    }

    public <T> void getListReflect(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_LIST_REFLECT);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelToReflect()
                , requestListener);
    }

    public <T> void getListReflect1(final Class<T> clazz, final IServerResultListener requestListener) {
        String url = getUrl(VConstant.END_URL_LIST_REFLECT);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post1(url
                , clazz, header
                , convertModelToReflect()
                , requestListener);
    }

    private String convertModelRequestGetDebt() {
        Gson gson = new Gson();
        SysUserRequest sysUserRequest = VConstant.getUser();
        String json = gson.toJson(sysUserRequest);
        return json;
    }


    public <T> void updateStockTrans(int flag, SynStockTransDTO synStockTransDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_UPDATE_STOCK_TRANS);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestUpdateStockTrans(flag, synStockTransDTO)
                , requestListener);
    }

    private String convertModelRequestUpdateStockTrans(int flag, SynStockTransDTO synStockTransDTO) {
        Gson gson = new Gson();
        RequestStockTrans requestStockTrans = new RequestStockTrans();
        SysUserRequest sysUserRequest = VConstant.getUser();
        sysUserRequest.setFlag(flag);
        requestStockTrans.setSysUserRequest(sysUserRequest);
        requestStockTrans.setSynStockTransDto(synStockTransDTO);
        String json = gson.toJson(requestStockTrans);
        return json;
    }

    public <T> void updateSynStockTrans(int flag, SynStockTransDTO synStockTransDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_UPDATE_SYN_STOCK_TRANS);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestUpdateSynStockTrans(flag, synStockTransDTO)
                , requestListener);
    }

    private String convertModelRequestUpdateSynStockTrans(int flag, SynStockTransDTO synStockTransDTO) {
        Gson gson = new Gson();
        RequestStockTrans requestStockTrans = new RequestStockTrans();
        SysUserRequest sysUserRequest = VConstant.getUser();
        sysUserRequest.setFlag(flag);
        requestStockTrans.setSysUserRequest(sysUserRequest);
        requestStockTrans.setSynStockTransDto(synStockTransDTO);
        String json = gson.toJson(requestStockTrans);
        return json;
    }

    private String convertModelToReflect() {
        Gson gson = new Gson();
        SysUserRequest sysUserRequest = VConstant.getUser();
        IssuseRequest issuseRequest = new IssuseRequest();
        issuseRequest.setSysUserRequest(sysUserRequest);
        String json = gson.toJson(issuseRequest);
        return json;
    }

    public <T> void updateIssue(IssueWorkItemDTO issueWorkItemDTO, IssueHistoryEntityDTO issueHistoryContentIssueDetail, IssueHistoryEntityDTO issueHistoryContentHandingDetail, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_UPDATE_ISSUE);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertIssueToJson(issueWorkItemDTO, issueHistoryContentIssueDetail, issueHistoryContentHandingDetail)
                , requestListener);
    }

    private String convertIssueToJson(IssueWorkItemDTO issueWorkItemDTO, IssueHistoryEntityDTO issueHistoryContentIssueDetail, IssueHistoryEntityDTO issueHistoryContentHandingDetail) {
        Gson gson = new Gson();
        IssuseRequest issuseRequest = new IssuseRequest();
        issuseRequest.setSysUserRequest(VConstant.getUser());
        issuseRequest.setIssueDetail(issueWorkItemDTO);
        issuseRequest.setType(1);
        issuseRequest.setIssueHistoryContentIssueDetail(issueHistoryContentIssueDetail);
        issuseRequest.setIssueHistoryContentHandingDetail(issueHistoryContentHandingDetail);
        String json = gson.toJson(issuseRequest);
        return json;
    }

    public <T> void registerIssue(IssueDTO issueDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_CREATE_ISSUE);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertCreateIssueToJson(issueDTO)
                , requestListener);
    }

    private String convertCreateIssueToJson(IssueDTO issueDTO) {
        Gson gson = new Gson();
        IssuseRequest issuseRequest = new IssuseRequest();
        issuseRequest.setSysUserRequest(VConstant.getUser());
        issuseRequest.setIssueDTODetail(issueDTO);
        String json = gson.toJson(issuseRequest);
        return json;
    }

    public <T> void getValueConstructionManagement(String author,
                                                   ConstructionScheduleDTO constructionScheduleDTO,
                                                   final Class<T> clazz,
                                                   final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_DETAIL_CONSTRUCTION);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestGetValueConstruction(author, constructionScheduleDTO)
                , requestListener);
    }

    public <T> void getValueConstructionManagement1(String author,
                                                    ConstructionScheduleDTO constructionScheduleDTO,
                                                    final Class<T> clazz,
                                                    final IServerResultListener requestListener) {
        String url = getUrl(VConstant.END_DETAIL_CONSTRUCTION);
        Log.d(TAG, "getValueConstructionManagement1 - url : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post1(url
                , clazz, header
                , convertModelRequestGetValueConstruction(author, constructionScheduleDTO)
                , requestListener);
    }

    private String convertModelRequestGetValueConstruction(String author, ConstructionScheduleDTO constructionScheduleDTO) {
        Gson gson = new Gson();
        ConstructionScheduleDTORequest constructionScheduleDTORequest = new ConstructionScheduleDTORequest();
        SysUserRequest sysUserRequest = new SysUserRequest();

//        sysUserRequest.setAuthorities(VConstant.getUser().getAuthorities());
        sysUserRequest.setSysUserId(VConstant.getUser().getSysUserId());
        sysUserRequest.setAuthenticationInfo(VConstant.getUser().getAuthenticationInfo());
        sysUserRequest.setFlag(VConstant.getUser().getFlag());
        sysUserRequest.setDepartmentId(VConstant.getUser().getDepartmentId());
        sysUserRequest.setSysGroupId(VConstant.getUser().getSysGroupId());
        sysUserRequest.setAuthorities(author);

        constructionScheduleDTORequest.setSysUserRequest(sysUserRequest);
        constructionScheduleDTORequest.setConstructionScheduleDTO(constructionScheduleDTO);
        String json = gson.toJson(constructionScheduleDTORequest);
        Log.d(TAG, "convertModelRequestGetValueConstruction - json : " + json);
        return json;
    }


    public <T> void getConstructionManagement(int flag, final Class<T> clazz,
                                              final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_CONSTRUCTION_MANAGEMENT);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelGetConstructionManagement(flag)
                , requestListener);
    }

    public <T> void getConstructionManagement1(int flag, final Class<T> clazz,
                                               final IServerResultListener<T> requestListener) {
        String url = getUrl(VConstant.END_URL_GET_CONSTRUCTION_MANAGEMENT);
        Log.d(TAG, "getConstructionManagement1 - url : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post1(url
                , clazz, header
                , convertModelGetConstructionManagement(flag)
                , requestListener);
    }

    private String convertModelGetConstructionManagement(int flag) {
        Gson gson = new Gson();
        ConstructionScheduleDTORequest request = new ConstructionScheduleDTORequest();
        SysUserRequest sysUserRequest = VConstant.getUser();
        sysUserRequest.setFlag(flag);
//        SysUserRequest sysUserRequest = new SysUserRequest();
//
//        sysUserRequest.setAuthorities(VConstant.getUser().getAuthorities());
//        sysUserRequest.setSysUserId(VConstant.getUser().getSysUserId());
//        sysUserRequest.setAuthenticationInfo(VConstant.getUser().getAuthenticationInfo());
//        sysUserRequest.setFlag(VConstant.getUser().getFlag());
//        sysUserRequest.setDepartmentId(VConstant.getUser().getDepartmentId());
//        sysUserRequest.setSysGroupId(VConstant.getUser().getSysGroupId());

        request.setSysUserRequest(sysUserRequest);
        String json = gson.toJson(request);
        Log.d(TAG, "convertModelGetConstructionManagement - json : " + json);
        return json;
    }

    public <T> void getListScheduleWorkItem(String author, ConstructionScheduleItemDTO constructionScheduleItemDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_LIST_CONSTRUCTION_SCHELUDE_WORK_ITEM);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestScheduleWorkItem(author, constructionScheduleItemDTO)
                , requestListener);
    }

    public <T> void getListScheduleWorkItem1(String author, ConstructionScheduleItemDTO
            constructionScheduleItemDTO, final Class<T> clazz, final IServerResultListener requestListener) {
        String url = getUrl(VConstant.END_LIST_CONSTRUCTION_SCHELUDE_WORK_ITEM);
        Log.d(TAG, "getListScheduleWorkItem1 - url : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post1(url
                , clazz, header
                , convertModelRequestScheduleWorkItem(author, constructionScheduleItemDTO)
                , requestListener);
    }

    // THANGTv24 - add get list BGMB - start
    public <T> void getListBgmb(final Class<T> clazz, final IServerResultListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_CONSTRUCTION_BGMB_LIST_BY_STATUS);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post1(url
                , clazz, header
                , convertModelRequestListBgmb()
                , requestListener);

        Log.d(TAG, "getListBgmb - url : " + url + " - convertModelRequestListBgmb : " + convertModelRequestListBgmb());
    }

    private String convertModelRequestListBgmb() {
        Gson gson = new Gson();
        BgmbRequestListDTO request = new BgmbRequestListDTO();
        request.setSysUserId(VConstant.getUser().getSysUserId());
        request.setKeySearch("");
        request.setRowNum("");
        request.setStatus("1");
        String json = gson.toJson(request);
        return json;
    }

    //  // THANGTv24 - add get list BGMB - end
    private String convertModelRequestScheduleWorkItem(String author, ConstructionScheduleItemDTO constructionScheduleItemDTO) {
        Gson gson = new Gson();
        ConstructionScheduleDTORequest request = new ConstructionScheduleDTORequest();
        SysUserRequest sysUserRequest = new SysUserRequest();

        sysUserRequest.setSysUserId(VConstant.getUser().getSysUserId());
        sysUserRequest.setAuthenticationInfo(VConstant.getUser().getAuthenticationInfo());
        sysUserRequest.setFlag(VConstant.getUser().getFlag());
        sysUserRequest.setDepartmentId(VConstant.getUser().getDepartmentId());
        sysUserRequest.setSysGroupId(VConstant.getUser().getSysGroupId());
        sysUserRequest.setAuthorities(author);
        request.setSysUserRequest(sysUserRequest);
        request.setConstructionScheduleItemDTO(constructionScheduleItemDTO);
        String json = gson.toJson(request);
        Log.d(TAG, "convertModelRequestScheduleWorkItem - json : " + json);
        return json;
    }

    public <T> void getListHistoryHandOver(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_HANDOVER_HISTORY);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestHandOverHistory()
                , requestListener);
    }

    public <T> void getListHistoryHandOver1(final Class<T> clazz, final IServerResultListener requestListener) {
        String url = getUrl(VConstant.END_HANDOVER_HISTORY);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post1(url
                , clazz, header
                , convertModelRequestHandOverHistory()
                , requestListener);
    }


    private String convertModelRequestHandOverHistory() {
        Gson gson = new Gson();
        HandOverHistoryDTORequest request = new HandOverHistoryDTORequest();
        SysUserRequest sysUserRequest = VConstant.getUser();
        request.setRequest(sysUserRequest);
        String json = gson.toJson(request);
        return json;
    }

    public <T> void getListHistoryDetailHandOver(StTransactionDTO stTransactionDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_HANDOVER_DETAIL_LIST_HISTORY);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestDetailHandOverHistory(stTransactionDTO)
                , requestListener);
    }

    private String convertModelRequestDetailHandOverHistory(StTransactionDTO stTransactionDTO) {
        Gson gson = new Gson();
        HandOverHistoryDTORequest request = new HandOverHistoryDTORequest();
        SysUserRequest sysUserRequest = VConstant.getUser();
        request.setRequest(sysUserRequest);
        request.setStTransactionDTO(stTransactionDTO);
        String json = gson.toJson(request);
        return json;
    }


    public <T> void getDetailItemHandOver(StTransactionDTO stTransactionDTO, StTransactionDetailDTO stTransactionDetailDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_HANDOVER_DETAIL_ITEM_HISTORY);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestDetailItemHandOver(stTransactionDTO, stTransactionDetailDTO)
                , requestListener);
    }

    private String convertModelRequestDetailItemHandOver(StTransactionDTO stTransactionDTO, StTransactionDetailDTO stTransactionDetailDTO) {
        Gson gson = new Gson();
        HandOverHistoryDTORequest request = new HandOverHistoryDTORequest();
        SysUserRequest sysUserRequest = VConstant.getUser();
        request.setRequest(sysUserRequest);
        request.setStTransactionDTO(stTransactionDTO);
        request.setStTransactionDetailDTO(stTransactionDetailDTO);
        String json = gson.toJson(request);
        return json;
    }


    public <T> void getListEntangle(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_LIST_ENTANGLE_MANAGE);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestListEntangle()
                , requestListener);
    }

    public <T> void getListEntangle1(final Class<T> clazz, final IServerResultListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_LIST_ENTANGLE_MANAGE);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post1(url
                , clazz, header
                , convertModelRequestListEntangle()
                , requestListener);
    }

    public <T> void getListVolTio(final Class<T> clazz, final IServerResultListener requestListener) {
//        String url = getUrl(VConstant.END_URL_GET_LIST_ENTANGLE_MANAGE);
        String url = "https://api.exchange.trade.io/marketdata-ws/24hr";
        Map<String, String> header = new HashMap<>();
//     header.put("Content-Type", "application/json");
//        ApiClient.getInstance().get(url,clazz,header,requestListener);

        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post1(url
                , clazz, header
                , convertModelRequestListVolTio()
                , requestListener);
    }

    private String convertModelRequestListEntangle() {
        Gson gson = new Gson();
        EntangleManageDTORequest request = new EntangleManageDTORequest();
        SysUserRequest sysUserRequest = VConstant.getUser();
        request.setSysUserRequest(sysUserRequest);
        String json = gson.toJson(request);
        return json;
    }

    private String convertModelRequestListVolTio() {
        Gson gson = new Gson();
        TioVolumeDTORequest request = new TioVolumeDTORequest();
        SysUserRequest sysUserRequest = VConstant.getUser();
        request.setSysUserRequest(sysUserRequest);
        String json = gson.toJson(request);
        return json;
    }

    public <T> void updateEntangle(EntangleManageDTO entangleManageDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_UPDATE_ENTANGLE);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestUpdateEntangle(entangleManageDTO)
                , requestListener);
    }

    private String convertModelRequestUpdateEntangle(EntangleManageDTO entangleManageDTO) {
        Gson gson = new Gson();
        EntangleManageDTORequest request = new EntangleManageDTORequest();
        SysUserRequest sysUserRequest = VConstant.getUser();
        request.setSysUserRequest(sysUserRequest);
        request.setEntangleManageDTODetail(entangleManageDTO);

        String json = gson.toJson(request);

        return json;
    }

    public <T> void updateDelivery(int flag, SynStockTransDTO synStockTransDTO, SysUserRequest sysUserReceiver, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_UPDATE_DELIVERY);
        Log.d(TAG, "updateDelivery - url : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestUpdateDelivery(flag, synStockTransDTO, sysUserReceiver)
                , requestListener);
    }
    public <T> void updateDeliveryWithoutConfirm(int flag, SynStockTransDTO synStockTransDTO, SysUserRequest sysUserReceiver, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_UPDATE_DELIVERY_WITH_OUT_CONFIRM);
        Log.d(TAG, "updateDelivery - url : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestUpdateDelivery(flag, synStockTransDTO, sysUserReceiver)
                , requestListener);
    }

    public <T> void updateListDeliveryWithoutConfirm(int flag, List<SynStockTransDTO> lstSynStockTransDto, SysUserRequest sysUserReceiver, Long receiverId, String confirmDescription,  final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_UPDATE_LIST_DELIVERY_WITH_OUT_CONFIRM);
        Log.d(TAG, "updateDelivery - url : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestUpdateMultiDelivery(flag, lstSynStockTransDto, sysUserReceiver, receiverId,confirmDescription )
                , requestListener);
    }

    private String convertModelRequestUpdateMultiDelivery(int flag, List<SynStockTransDTO> lstSynStockTransDto, SysUserRequest sysUserReceiver, Long receiverId, String confirmDescription) {
        Gson gson = new Gson();
        StockTransRequest stockTransRequest = new StockTransRequest();
        SysUserRequest sysUserRequest = VConstant.getUser();
        sysUserRequest.setFlag(flag);
        stockTransRequest.setSysUserRequest(sysUserRequest);
        stockTransRequest.setSysUserReceiver(sysUserReceiver);
        stockTransRequest.setLstSynStockTransDto(lstSynStockTransDto);
        stockTransRequest.setConfirmDescription(confirmDescription);
        stockTransRequest.setReceiverId(receiverId);
        String json = gson.toJson(stockTransRequest);
        Log.d(TAG, "convertModelRequestUpdateDelivery - json : " + json);
        return json;
    }

    private String convertModelRequestUpdateDelivery(int flag, SynStockTransDTO synStockTransDTO, SysUserRequest sysUserReceiver) {
        Gson gson = new Gson();
        StockTransRequest stockTransRequest = new StockTransRequest();
        SysUserRequest sysUserRequest = VConstant.getUser();
        sysUserRequest.setFlag(flag);
        stockTransRequest.setSysUserRequest(sysUserRequest);
        stockTransRequest.setSysUserReceiver(sysUserReceiver);
        stockTransRequest.setSynStockTransDto(synStockTransDTO);
        String json = gson.toJson(stockTransRequest);
        Log.d(TAG, "convertModelRequestUpdateDelivery - json : " + json);
        return json;
    }

    public <T> void createEntangle(EntangleManageDTO entangleManageDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_CREATE_ENTANGLE);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestCreateEntangle(entangleManageDTO)
                , requestListener);
    }

    private String convertModelRequestCreateEntangle(EntangleManageDTO entangleManageDTO) {
        Gson gson = new Gson();
        EntangleManageDTORequest request = new EntangleManageDTORequest();
        SysUserRequest sysUserRequest = VConstant.getUser();
        request.setSysUserRequest(sysUserRequest);
        request.setEntangleManageDTODetail(entangleManageDTO);
        String json = gson.toJson(request);
        return json;
    }

    public <T> void callDataForAcceptanceChart(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_COUNT_ACCEPTANCE_FOR_CHART);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestDataForAcceptanceChart()
                , requestListener);
    }


    public <T> void getValuePercentRefund(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_REFUND_HOME);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestPercentRefund()
                , requestListener);
    }

    private String convertModelRequestPercentRefund() {
        Gson gson = new Gson();
        SysUserRequest sysUserRequest = VConstant.getUser();
        ConstructionMerchandiseDTORequest request = new ConstructionMerchandiseDTORequest();
        request.setSysUserRequest(sysUserRequest);
        String json = gson.toJson(request);
        return json;
    }


    private String convertModelRequestDataForAcceptanceChart() {
        Gson gson = new Gson();
        SysUserRequest sysUserRequest = VConstant.getUser();
        ConstructionAcceptanceDTORequest request = new ConstructionAcceptanceDTORequest();
        request.setSysUserRequest(sysUserRequest);
        String json = gson.toJson(request);
        return json;
    }

    private String convertModelRequestGetListConstruction(ConstructionTaskDTO constructionTaskDTO) {
        Gson gson = new Gson();
        SysUserRequest sysUserRequest = VConstant.getUser();
        ConstructionTaskDTOUpdateRequest request = new ConstructionTaskDTOUpdateRequest();
        request.setSysUserRequest(sysUserRequest);
        request.setConstructionTaskDTO(constructionTaskDTO);
        String json = gson.toJson(request);
        Log.d(TAG, "convertModelRequestGetListConstruction - json : " + json);
        return json;
    }

    //TODO test api
//    private String convertModelRequestGetListConstructionAndSpinner() {
//        Gson gson = new Gson();
//        SysUserRequest sysUserRequest = VConstant.getUser();
//        String json = gson.toJson(sysUserRequest);
//        return json;
//    }

    public <T> void callDataListAcceptanceLever1(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_ACCEPTANCE_LEVER_1);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestDataForAcceptanceLever1()
                , requestListener);
    }

    public <T> void callDataListAcceptanceLever11(final Class<T> clazz,
                                                  final IServerResultListener requestListener) {
        String url = getUrl(VConstant.END_URL_ACCEPTANCE_LEVER_1);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post1(url
                , clazz, header
                , convertModelRequestDataForAcceptanceLever1()
                , requestListener);
    }

    public <T> void getListConstructionSpinner(ConstructionTaskDTO constructionTaskDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_LIST_CONSTRUCTION_SPINNER);
        Log.d(TAG, "getListConstructionSpinner - url : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestGetListConstruction(constructionTaskDTO)
                , requestListener);
    }

    private String convertModelRequestDataForAcceptanceLever1() {
        Gson gson = new Gson();
        SysUserRequest sysUserRequest = VConstant.getUser();
        ConstructionAcceptanceDTORequest request = new ConstructionAcceptanceDTORequest();
        request.setSysUserRequest(sysUserRequest);
        String json = gson.toJson(request);
        return json;
    }

    public <T> void requestHandoverPersonCategory(String author, SysUserRequest sysUserReceiver, ConstructionScheduleItemDTO constructionScheduleItemDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_CHANGE_PERSON_CATEGORY);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestHandoverPersonCategory(author, sysUserReceiver, constructionScheduleItemDTO)
                , requestListener);
    }


    private String convertModelRequestHandoverPersonCategory(String author, SysUserRequest sysUserReceiver, ConstructionScheduleItemDTO constructionScheduleItemDTO) {
        Gson gson = new Gson();
        SysUserRequest sysUserRequest = new SysUserRequest();

//        sysUserRequest.setAuthorities(VConstant.getUser().getAuthorities());
        sysUserRequest.setSysUserId(VConstant.getUser().getSysUserId());
        sysUserRequest.setAuthenticationInfo(VConstant.getUser().getAuthenticationInfo());
        sysUserRequest.setFlag(VConstant.getUser().getFlag());
        sysUserRequest.setDepartmentId(VConstant.getUser().getDepartmentId());
        sysUserRequest.setSysGroupId(VConstant.getUser().getSysGroupId());
        sysUserRequest.setAuthorities(author);
        ConstructionScheduleDTORequest request = new ConstructionScheduleDTORequest();
        request.setConstructionScheduleItemDTO(constructionScheduleItemDTO);
        request.setSysUserRequest(sysUserRequest);
        request.setSysUserReceiver(sysUserReceiver);
        String json = gson.toJson(request);
        return json;
    }

    public <T> void getListAcceptanceLevel2(ConstructionAcceptanceCertDetailDTO constructionAcceptanceCertDetailDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_ACCEPTANCE_LEVER_2);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestAcceptanceLevel2(constructionAcceptanceCertDetailDTO)
                , requestListener);
    }

    public <T> void getListAcceptanceLevel21(ConstructionAcceptanceCertDetailDTO
                                                     constructionAcceptanceCertDetailDTO, final Class<T> clazz,
                                             final IServerResultListener requestListener) {
        String url = getUrl(VConstant.END_URL_ACCEPTANCE_LEVER_2);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post1(url
                , clazz, header
                , convertModelRequestAcceptanceLevel2(constructionAcceptanceCertDetailDTO)
                , requestListener);
    }

    private String convertModelRequestAcceptanceLevel2(ConstructionAcceptanceCertDetailDTO constructionAcceptanceCertDetailDTO) {
        Gson gson = new Gson();
        SysUserRequest sysUserRequest = VConstant.getUser();
        ConstructionAcceptanceDTORequest request = new ConstructionAcceptanceDTORequest();
        request.setConstructionAcceptanceCertDetailDTO(constructionAcceptanceCertDetailDTO);
        request.setSysUserRequest(sysUserRequest);
        String json = gson.toJson(request);
        return json;
    }


//    public <T> void getListAcceptanceLevel3(ConstructionAcceptanceCertDetailDTO data, final Class<T> clazz, final IOnRequestListener requestListener) {
//        String url = getUrl(VConstant.END_URL_ACCEPTANCE_LEVER_3);
//        Map<String, String> header = new HashMap<>();
//        header.put("Content-Type", "application/json");
//        ApiClient.getInstance().post(url
//                , clazz, header
//                , convertModelRequestListAcceptance(data)
//                , requestListener);
//    }

    private String convertModelRequestListAcceptance(ConstructionAcceptanceCertDetailDTO constructionAcceptanceCertDetailDTO) {
        Gson gson = new Gson();
        SysUserRequest sysUserRequest = VConstant.getUser();
        ConstructionAcceptanceDTORequest request = new ConstructionAcceptanceDTORequest();
        request.setConstructionAcceptanceCertDetailDTO(constructionAcceptanceCertDetailDTO);
        request.setSysUserRequest(sysUserRequest);
        String json = gson.toJson(request);
        Log.d(TAG, "convertModelRequestListAcceptance - json : " + json);
        return json;
    }

    public <T> void getListAcceptance1(ConstructionAcceptanceCertDetailDTO constructionAcceptanceCertDetailDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_ACCEPTANCE_LIST_1);
        Log.d(TAG, "getListAcceptance1 - url : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestListAcceptance(constructionAcceptanceCertDetailDTO)
                , requestListener);
    }


    public <T> void getListAcceptance2(ConstructionAcceptanceCertDetailDTO constructionAcceptanceCertDetailDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_ACCEPTANCE_LIST_2);
        Log.d(TAG, "getListAcceptance2 - url : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestListAcceptance(constructionAcceptanceCertDetailDTO)
                , requestListener);
    }

    public <T> void getListAcceptance3(ConstructionAcceptanceCertDetailDTO constructionAcceptanceCertDetailDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_ACCEPTANCE_LIST_3);
        Log.d(TAG, "getListAcceptance3 - url : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestListAcceptance(constructionAcceptanceCertDetailDTO)
                , requestListener);
    }


    public <T> void getListAcceptance4(ConstructionAcceptanceCertDetailDTO constructionAcceptanceCertDetailDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_ACCEPTANCE_LIST_4);
        Log.d(TAG, "getListAcceptance4 - url : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestListAcceptance(constructionAcceptanceCertDetailDTO)
                , requestListener);
    }


    public <T> void updateAcceptance(ConstructionAcceptanceDTORequest constructionAcceptanceDTORequest, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_UPDATE_ACCEPTANCE);
        Map<String, String> header = new HashMap<>();
        Log.d(TAG, "updateAcceptance - url : " + url);
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestUpdateAcceptance(constructionAcceptanceDTORequest)
                , requestListener);
    }


    public <T> void deleteAcceptance(ConstructionAcceptanceDTORequest constructionAcceptanceDTORequest, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_DELETE_ACCEPTANCE);
        Log.d(TAG, "deleteAcceptance - url : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestUpdateAcceptance(constructionAcceptanceDTORequest)
                , requestListener);
    }

    public <T> void deleteRefund(ConstructionAcceptanceDTORequest constructionAcceptanceDTORequest, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_DELETE_ACCEPTANCE);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestUpdateAcceptance(constructionAcceptanceDTORequest)
                , requestListener);
    }


    private String convertModelRequestUpdateAcceptance(ConstructionAcceptanceDTORequest constructionAcceptanceDTORequest) {
        Gson gson = new Gson();
        String json = gson.toJson(constructionAcceptanceDTORequest);
        Log.d(TAG, "convertModelRequestUpdateAcceptance - json :  " + json);
        return json;
    }

    public <T> void getListRefundLever1(final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_LIST_REFUND_LEVER_1);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestGetListRefundLever1()
                , requestListener);
    }

    public <T> void getListRefundLever11(final Class<T> clazz, final IServerResultListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_LIST_REFUND_LEVER_1);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post1(url
                , clazz, header
                , convertModelRequestGetListRefundLever1()
                , requestListener);
    }


    private String convertModelRequestGetListRefundLever1() {
        Gson gson = new Gson();
        SysUserRequest sysUserRequest = VConstant.getUser();
        ConstructionMerchandiseDTORequest constructionMerchandiseDTORequest = new ConstructionMerchandiseDTORequest();
        constructionMerchandiseDTORequest.setSysUserRequest(sysUserRequest);
        String json = gson.toJson(constructionMerchandiseDTORequest);
        return json;
    }


    public <T> void getListRefundLever2(ConstructionMerchandiseDTORequest constructionMerchandiseDTORequest, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_LIST_REFUND_LEVER_2);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestGetListRefundLever2(constructionMerchandiseDTORequest)
                , requestListener);
    }


    public <T> void getListRefundLever21(ConstructionMerchandiseDTORequest
                                                 constructionMerchandiseDTORequest,
                                         final Class<T> clazz, final IServerResultListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_LIST_REFUND_LEVER_2);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post1(url
                , clazz, header
                , convertModelRequestGetListRefundLever2(constructionMerchandiseDTORequest)
                , requestListener);
    }


    private String convertModelRequestGetListRefundLever2(ConstructionMerchandiseDTORequest request) {
        Gson gson = new Gson();
        String json = gson.toJson(request);
        return json;
    }


    public <T> void getListRefundLeverDetailVTLV3(ConstructionMerchandiseDTORequest constructionMerchandiseDTORequest, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_LIST_REFUND_DETAIL_VT_LEVER_3);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestGetListRefundDetailLever3(constructionMerchandiseDTORequest)
                , requestListener);
    }

    public <T> void getListRefundLeverDetailTBLV3(ConstructionMerchandiseDTORequest constructionMerchandiseDTORequest, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_LIST_REFUND_DETAIL_TB_LEVER_3);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestGetListRefundDetailLever3(constructionMerchandiseDTORequest)
                , requestListener);
    }

    public <T> void updateRefund(ConstructionMerchandiseDTORequest constructionMerchandiseDTORequest, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_UPDATE_REFUND);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestGetListRefundDetailLever3(constructionMerchandiseDTORequest)
                , requestListener);
    }

    public <T> void deleteRefund(ConstructionMerchandiseDTORequest constructionMerchandiseDTORequest, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_DELETE_REFUND);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestDeleteRefund(constructionMerchandiseDTORequest)
                , requestListener);
    }

    private String convertModelRequestDeleteRefund(ConstructionMerchandiseDTORequest request) {
        Gson gson = new Gson();
        String json = gson.toJson(request);
        return json;
    }


    private String convertModelRequestGetListRefundDetailLever3(ConstructionMerchandiseDTORequest request) {
        Gson gson = new Gson();
        String json = gson.toJson(request);
        return json;
    }


    public <T> void getAttachment( SynStockTransDTO synStockTransDTO, final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_IMAGE_ATTACHMENT);
        Log.d(TAG, "updateDelivery - url : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , clazz, header
                , convertModelRequestAttachment(synStockTransDTO)
                , requestListener);
    }

    private String convertModelRequestAttachment(SynStockTransDTO synStockTransDTO) {
        Gson gson = new Gson();
        StockTransRequest stockTransRequest = new StockTransRequest();
        SysUserRequest sysUserRequest = VConstant.getUser();
        stockTransRequest.setSysUserRequest(sysUserRequest);
        synStockTransDTO.setSynTransType(VConstant.TYPE_IMG_PXK);
        stockTransRequest.setSynStockTransDto(synStockTransDTO);
        String json = gson.toJson(stockTransRequest);
        Log.d(TAG, "convertModelRequestUpdateDelivery - json : " + json);
        return json;
    }
//    public <T> void getListAcceptance4(ConstructionAcceptanceCertDetailDTO data, final Class<T> clazz, final IOnRequestListener requestListener) {
//        String url = getUrl(VConstant.END_URL_ACCEPTANCE_LEVER_3);
//        Map<String, String> header = new HashMap<>();
//        header.put("Content-Type", "application/json");
//        ApiClient.getInstance().post(url
//                , clazz, header
//                , convertModelRequestListAcceptance(data)
//                , requestListener);
//    }

    //THANGTV24 - update BGMB - start
    public <T> void updateBGMB(boolean isGiaCoMayno, long checkXPXD, long checkXPAC, long assignHandoverId, long catStationHouseId, String cntContractCode, long cntContractId, String constructionCode,
                               long constructionId, long sysGroupId, long columnHeight, long groundingTypeId, String groundingTypeName,
                               String haveWorkItemName, long houseTypeId, String houseTypeName, String isFenceStr, String isReceivedGoodsStr,
                               String isReceivedObstructStr, long numberCo, String receivedObstructContent, String receivedGoodsContent,
                               long stationType, String isACStr, String numColumnsAvaible, String lengthOfMeter, String haveStartPoint, String typeOfMeter, String numNewColumn
            , String typeOfColumn, String typeConstructionBgmb, List<ConstructionImageInfo> listConstructionImageInfo,
                               final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = isGiaCoMayno ? getUrl(VConstant.END_URL_GET_CONSTRUCTION_BGMB_UPDATE_GIACO_MAY_NO) : getUrl(VConstant.END_URL_GET_CONSTRUCTION_BGMB_UPDATE);
        Log.d(TAG, "updateBGMB - urrl : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , ConstructionBGMBResponse.class, header
                , convertModelForUpdateBGMB(checkXPXD, checkXPAC, assignHandoverId, catStationHouseId, cntContractCode, cntContractId, constructionCode,
                        constructionId, sysGroupId, columnHeight, groundingTypeId, groundingTypeName,
                        haveWorkItemName, houseTypeId, houseTypeName, isFenceStr, isReceivedGoodsStr,
                        isReceivedObstructStr, numberCo, receivedObstructContent, receivedGoodsContent,
                        stationType, isACStr, numColumnsAvaible, lengthOfMeter, haveStartPoint, typeOfMeter, numNewColumn
                        , typeOfColumn, typeConstructionBgmb, listConstructionImageInfo)
                , requestListener);
    }

    private String convertModelForUpdateBGMB(long checkXPXD, long checkXPAC, long assignHandoverId, long catStationHouseId, String cntContractCode, long cntContractId, String constructionCode,
                                             long constructionId, long sysGroupId, long columnHeight, long groundingTypeId, String groundingTypeName,
                                             String haveWorkItemName, long houseTypeId, String houseTypeName, String isFenceStr, String isReceivedGoodsStr,
                                             String isReceivedObstructStr, long numberCo, String receivedObstructContent, String receivedGoodsContent,
                                             long stationType, String isACStr, String numColumnsAvaible, String lengthOfMeter, String haveStartPoint, String typeOfMeter, String numNewColumn
            , String typeOfColumn, String typeConstructionBgmb, List<ConstructionImageInfo> listConstructionImageInfo) {

        ConstructionBGMBDTO assignHandoverDTO = new ConstructionBGMBDTO();
        assignHandoverDTO.setAssignHandoverId(assignHandoverId);
        assignHandoverDTO.setCatStationHouseId(catStationHouseId);
        assignHandoverDTO.setCntContractCode(cntContractCode);
        assignHandoverDTO.setCntContractId(cntContractId);
        assignHandoverDTO.setConstructionCode(constructionCode);
        assignHandoverDTO.setConstructionId(constructionId);
        assignHandoverDTO.setSysGroupId(sysGroupId);
        assignHandoverDTO.setColumnHeight(columnHeight);
        assignHandoverDTO.setGroundingTypeId(groundingTypeId);
        assignHandoverDTO.setGroundingTypeName(groundingTypeName);
        assignHandoverDTO.setHaveWorkItemName(haveWorkItemName);
        assignHandoverDTO.setHouseTypeId(houseTypeId);
        assignHandoverDTO.setHouseTypeName(houseTypeName);
        assignHandoverDTO.setIsFenceStr(isFenceStr);
        assignHandoverDTO.setIsReceivedGoodsStr(isReceivedGoodsStr);
        assignHandoverDTO.setIsReceivedObstructStr(isReceivedObstructStr);
        assignHandoverDTO.setNumberCo(numberCo);
        assignHandoverDTO.setReceivedGoodsContent(receivedGoodsContent);
        assignHandoverDTO.setReceivedObstructContent(receivedObstructContent);
        assignHandoverDTO.setStationType(stationType);
        assignHandoverDTO.setIsACStr(isACStr);
        assignHandoverDTO.setCheckXPAC(checkXPAC);
        assignHandoverDTO.setCheckXPXD(checkXPXD);
        // Add Them
        assignHandoverDTO.setNumColumnsAvaible(numColumnsAvaible);
        assignHandoverDTO.setLengthOfMeter(lengthOfMeter);
        assignHandoverDTO.setHaveStartPoint(haveStartPoint);
        assignHandoverDTO.setTypeOfMeter(typeOfMeter);
        assignHandoverDTO.setNumNewColumn(numNewColumn);
        assignHandoverDTO.setTypeOfColumn(typeOfColumn);
        assignHandoverDTO.setTypeConstructionBgmb(typeConstructionBgmb);

        BgmbTaskUpdateRequest request1 = new BgmbTaskUpdateRequest();
        Gson gson1 = new Gson();
        String json1;
        request1.setSysUserId(VConstant.getUser().getSysUserId());
        request1.setAssignHandoverDTO(assignHandoverDTO);
        json1 = gson1.toJson(request1);

        Log.d(TAG, "convertModelForUpdateBGMB - json1 : " + json1);
        // bo list anh de check thong tin ok chua
        assignHandoverDTO.setConstructionImageInfo(listConstructionImageInfo);

        BgmbTaskUpdateRequest request = new BgmbTaskUpdateRequest();
        Gson gson = new Gson();
        String json;
        request.setSysUserId(VConstant.getUser().getSysUserId());
        request.setAssignHandoverDTO(assignHandoverDTO);
        json = gson.toJson(request);

        return json;

    }

    //THANGTV24 - update BGMB - start - update cong trinh tuyen
    public <T> void updateBGMBTuyen(long assignHandoverId, long catStationHouseId, String cntContractCode, long cntContractId, String constructionCode,
                                    long constructionId, long sysGroupId, long stationType, String totalLength,
                                    String hiddenImmediacy,
                                    String cableInTank,
                                    String cableInTankDrain,
                                    String plantColunms,
                                    String availableColumns, String receivedObstructContent, List<ConstructionImageInfo> listConstructionImageInfo,
                                    final Class<T> clazz, final IOnRequestListener requestListener) {
        String url = getUrl(VConstant.END_URL_GET_CONSTRUCTION_BGMB_UPDATE_TUYEN);
        Log.d(TAG, "updateBGMB - url : " + url);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ApiClient.getInstance().post(url
                , ConstructionBGMBResponse.class, header
                , convertModelForUpdateBGMBTuyen(assignHandoverId, catStationHouseId, cntContractCode, cntContractId, constructionCode,
                        constructionId, sysGroupId, stationType, totalLength, hiddenImmediacy,
                        cableInTank, cableInTankDrain, plantColunms, availableColumns, receivedObstructContent, listConstructionImageInfo)
                , requestListener);
    }

    private String convertModelForUpdateBGMBTuyen(long assignHandoverId, long catStationHouseId, String cntContractCode, long cntContractId, String constructionCode,
                                                  long constructionId, long sysGroupId, long stationType, String totalLength,
                                                  String hiddenImmediacy,
                                                  String cableInTank,
                                                  String cableInTankDrain,
                                                  String plantColunms,
                                                  String availableColumns,
                                                  String receivedObstructContent, List<ConstructionImageInfo> listConstructionImageInfo) {

        ConstructionBGMBDTO assignHandoverDTO = new ConstructionBGMBDTO();
        assignHandoverDTO.setAssignHandoverId(assignHandoverId);
        assignHandoverDTO.setCatStationHouseId(catStationHouseId);
        assignHandoverDTO.setCntContractCode(cntContractCode);
        assignHandoverDTO.setCntContractId(cntContractId);
        assignHandoverDTO.setConstructionCode(constructionCode);
        assignHandoverDTO.setConstructionId(constructionId);
        assignHandoverDTO.setSysGroupId(sysGroupId);
        assignHandoverDTO.setStationType(stationType);
        assignHandoverDTO.setTotalLength(totalLength);
        assignHandoverDTO.setHiddenImmediacy(hiddenImmediacy);
        assignHandoverDTO.setCableInTank(cableInTank);
        assignHandoverDTO.setCableInTankDrain(cableInTankDrain);
        assignHandoverDTO.setPlantColunms(plantColunms);
        assignHandoverDTO.setAvailableColumns(availableColumns);
        assignHandoverDTO.setReceivedObstructContent(receivedObstructContent);

        BgmbTaskUpdateRequest request1 = new BgmbTaskUpdateRequest();
        Gson gson1 = new Gson();
        String json1;
        request1.setSysUserId(VConstant.getUser().getSysUserId());
        request1.setAssignHandoverDTO(assignHandoverDTO);
        json1 = gson1.toJson(request1);

        Log.d(TAG, "convertModelForUpdateBGMBTuyen - json1 : " + json1);
        // bo list anh de check thong tin ok chua
        assignHandoverDTO.setConstructionImageInfo(listConstructionImageInfo);
        Log.d(TAG, "convertModelForUpdateBGMBTuyen - listConstructionImageInfo : " + listConstructionImageInfo.size());

        BgmbTaskUpdateRequest request = new BgmbTaskUpdateRequest();
        Gson gson = new Gson();
        String json;
        request.setSysUserId(VConstant.getUser().getSysUserId());
        request.setAssignHandoverDTO(assignHandoverDTO);
        json = gson.toJson(request);

        return json;

    }
}
