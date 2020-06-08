package com.viettel.construction.common;

import androidx.annotation.DrawableRes;

import com.google.gson.Gson;

import com.google.gson.JsonSyntaxException;
import com.viettel.construction.R;
import com.viettel.construction.model.api.SysUserDTO;
import com.viettel.construction.model.api.SysUserRequest;

/**
 * Created by Manroid on 15/01/2018.
 */

public class VConstant {


    public static final @DrawableRes
    int[] IMAGES = new int[]{
            R.drawable.photo_1,
            R.drawable.photo_2,
            R.drawable.photo_3,
            R.drawable.photo_4,
            R.drawable.photo_5,
            R.drawable.photo_6,
    };

    public static class TypeFilerPxk {
        public static final int TYPE_ALL = 1;
        public static final int TYPE_DA_TIEP_NHAN = 2;
        public static final int TYPE_CHO_TIEP_NHAN = 3;
        public static final int TYPE_DA_TU_CHOI = 4;
        public static final int TYPE_CHO_XAC_NHAN = 5;
        public static final int TYPE_DA_XAC_NHAN = 6;
        public static final int TYPE_TU_CHOI_XAC_NHAN = 7;
        public static final int TYPE_QUA_HAN = 8;

    }

    public static class PxkTitleType {
        public static final int TYPE_TAT_CA = 1;
        public static final int TYPE_DA_TIEP_NHAN = 2;
        public static final int TYPE_CHO_TIEP_NHAN = 3;
        public static final int TYPE_DA_TU_CHOI = 4;
        public static final int TYPE_CHO_XAC_NHAN = 5;
        public static final int TYPE_DA_XAC_NHAN = 6;
        public static final int TYPE_TU_CHOI_XAC_NHAN = 7;
        public static final int TYPE_QUA_HAN = 8;
        public static final int TYPE_SEARCH = 9;
    }

    //TODO: Khi phát hành phải sửa thành false
    public static final boolean Debug = false;


    //Type image PXK
    public static final String TYPE_IMG_PXK = "XNP_TEST";

    //Bundle key
    public static final String BUNDLE_KEY_FILTER = "filter_key";
    public static final String BUNDLE_KEY_NUMBER_OF_CV = "number_of_cv";
    public static final String BUNDLE_KEY_BILL_ENTITY = "bill_entity";
    public static final String BUNDLE_KEY_MATERIAL_ENTITY = "material_entity";


    public static final String BUNDLE_KEY_OBJECT_CV = "objectCV";
    public static final String BUNDLE_KEY_OBJECT_BGMB = "objectBGMB";


    public static final String MOBILE_PMXL_VERSION = "MOBILE_PMXL_VERSION";

    public static final String RESULT_STATUS_OK = "OK";
    // Request code camera
    public static final int REQUEST_CODE_CAMERA = 101;
    //URL API
    public static final String BASE_URL = "http://10.61.19.230:9702/coms-service/service/";
    //    real
//    public static final String BASE_URL = "http://10.30.145.74:8752/coms-service/service/";

    // New real server
   // public static final String BASE_URL = "http://coms.congtrinhviettel.com.vn/coms-service/service/";
    //public static final String NAME_FRAGMENT_CATEGORY = "com.viettel.construction.view.fragment.qlct.ConstructionLevel1ChartFragment_rac";

    public static final String END_URL_LOGIN = "SysUserRestService/service/auth";

    public static final String END_URL_DASHBOARD_HOME_CV = "ConstructionTaskRestService/service/getCompleteStateConstructionTask";
    public static final String END_URL_GET_NAME_CAT_TASK = "WorkItemRestService/service/getNameCatTask";
    public static final String END_URL_DETAIL_DASHBOARD_CV = "ConstructionTaskRestService/service/getConstructionTaskByPerformerSupervisor";
    public static final String END_URL_Category_DASHBOARD = "ConstructionRestService/service/getChartWorkItem";
    public static final String END_URL_LIST_USER_BY_DEPARTMENT_BY_ID = "SysUserRestService/service/getListUserByDepartmentId";
    public static final String END_URL_LIST_CATEGORY = "WorkItemRestService/service/getNameWorkItemByConstructionId";
    public static final String END_URL_LIST_WORK_COMPLETE = "WorkItemRestService/service/getListWorkItemByUser";
    public static final String END_URL_LIST_PERFORM_AND_SUPERVISE_WORK = "ConstructionTaskRestService/service/getConstructionTaskById";
    public static final String END_URL_LIST_CONSTRUCTION_BY_ID = "ConstructionRestService/service/getNameConstructionIDbySysUserId";
    public static final String END_URL_UPDATE_STOP_WORK = "ConstructionTaskRestService/service/updateStopConstructionTask";
    public static final String END_URL_UPDATE_WORK = "ConstructionTaskRestService/service/updatePercentConstructionTask";
    public static final String END_URL_GET_LIST_COMPLETE_CATEGORY = "WorkItemRestService/service/getListWorkItemByUser";
    public static final String END_URL_GET_WORK_ON_DAY = "ConstructionTaskRestService/service/getConstructionTaskByOnDay";
    public static final String END_URL_UPDATE_WORK_ITEM = "WorkItemRestService/service/updateWorkItem";
    public static final String END_URL_GET_LIST_IMAGE = "ConstructionTaskRestService/service/getImagesByConstructionTaskId";
    public static final String END_URL_CREATE_NEW_WORK = "ConstructionTaskRestService/service/insertCompleteRevenueTaskOther";
    public static final String END_URL_GET_LIST_IMAGE_CATEGORY_COMPLETE = "WorkItemRestService/service/getImageByWorkItem";
    public static final String END_URL_GET_COUNT_DELIVERY_BILL = "SynStockTransRestService/service/totalDelivery";
    public static final String END_URL_GET_LIST_SYN_STOCK_TRANS_DTO = "SynStockTransRestService/service/getListSynStockTransDTO";
    public static final String END_URL_GET_DETAIL_SYN_STOCK_TRANS_DTO = "SynStockTransRestService/service/getListSynStockTransDetailDTO";
    public static final String END_URL_GET_LIST_MER_ENTITY_DTO = "SynStockTransRestService/service/getListMerEntityDTO";
    public static final String END_URL_GET_DEBT = "SynStockTransRestService/service/getCongNo";
    public static final String END_URL_UPDATE_STOCK_TRANS = "SynStockTransRestService/service/updateStockTrans";
    public static final String END_URL_UPDATE_SYN_STOCK_TRANS = "SynStockTransRestService/service/updateSynStockTrans";
    public static final String END_URL_CHECK_VERSION = "SynStockTransRestService/service/getAppParam";
    public static final String END_URL_LIST_REFLECT = "IssueRestService/service/getValueToInitIssuePages";
    public static final String END_UPDATE_ISSUE = "IssueRestService/service/updateIssueItemDetail";
    public static final String END_CREATE_ISSUE = "IssueRestService/service/registerIssueItemDetail";
    public static final String END_DETAIL_CONSTRUCTION = "ConstructionRestService/service/getValueToInitContructionManagementItem";
    public static final String END_LIST_CONSTRUCTION_SCHELUDE_WORK_ITEM = "ConstructionRestService/service/getValueToInitConstructionScheduleWorkItem";
    public static final String END_HANDOVER_HISTORY = "HandOverHistoryRestService/service/getValueToInitHandOverHistoryPages";
    public static final String END_HANDOVER_DETAIL_LIST_HISTORY = "HandOverHistoryRestService/service/getValueToInitHandOverHistoryVTTBPages";
    public static final String END_HANDOVER_DETAIL_ITEM_HISTORY = "HandOverHistoryRestService/service/getValueToInitHandOverHistoryVTTBDetail";
    public static final String END_URL_GET_CONSTRUCTION_MANAGEMENT = "ConstructionRestService/service/getValueToInitContructionManagement";
    // refund
    public static final String END_URL_GET_LIST_REFUND_LEVER_1 = "ConstructionMerchandiseRestService/service/getValueToInitConstructionMerchandisePages";
    public static final String END_URL_GET_LIST_REFUND_LEVER_2 = "ConstructionMerchandiseRestService/service/getValueToInitConstructionMerchandiseWorkItemsPages";


    public static final String END_URL_GET_LIST_REFUND_DETAIL_VT_LEVER_3 = "ConstructionMerchandiseRestService/service/getValueToInitConstructionMerchandiseDetailVTPages";
    public static final String END_URL_GET_LIST_REFUND_DETAIL_TB_LEVER_3 = "ConstructionMerchandiseRestService/service/getValueToInitConstructionMerchandiseDetailTBPages";
    public static final String END_URL_GET_LIST_IMAGE_REFUND_DETAIL_LEVER_3 = "ConstructionMerchandiseRestService/service/getListImageReturn";
    public static final String END_URL_UPDATE_REFUND = "ConstructionMerchandiseRestService/service/updateVTTBConstructionReturn";
    public static final String END_URL_DELETE_REFUND = "ConstructionMerchandiseRestService/service/deleteVTTBConstructionReturn";
    public static final String END_URL_REFUND_HOME = "ConstructionMerchandiseRestService/service/getValueToInitConstructionReturnRatePages";

    //    public static final String END_URL_CREATE_WORK = "WorkItemRestService/service/getListWorkItemByUser";
    public static final String END_URL_GET_LIST_ENTANGLE_MANAGE = "EntangleManageRestService/service/getValueToInitEntangleManage";
    public static final String END_URL_UPDATE_ENTANGLE = "EntangleManageRestService/service/getUpdateEntangleManage";
    public static final String END_URL_CREATE_ENTANGLE = "EntangleManageRestService/service/addEntangle";
    public static final String END_URL_GET_LIST_CONSTRUCTION_2 = "EntangleManageRestService/service/getConstructionCode";
    public static final String END_URL_GET_LIST_IMAGE_ENTANGLE = "EntangleManageRestService/service/getListImageEntagle";
    public static final String END_URL_UPDATE_DELIVERY = "SynStockTransRestService/service/DeliveryMaterials";
    public static final String END_URL_UPDATE_DELIVERY_WITH_OUT_CONFIRM = "SynStockTransRestService/service/DeliveryMaterialsNew";
    public static final String END_URL_UPDATE_LIST_DELIVERY_WITH_OUT_CONFIRM = "SynStockTransRestService/service/DeliveryMaterialsNewV2";
    public static final String END_URL_IMAGE_ATTACHMENT = "SynStockTransRestService/service/getAttachment";
    public static final String END_URL_COUNT_ACCEPTANCE_FOR_CHART = "ConstructionAcceptanceRestService/service/getValueToInitConstructionAcceptanceRatePages";
    public static final String END_URL_GET_LIST_CONSTRUCTION_SPINNER = "ConstructionTaskRestService/service/getHTTC";
    public static final String END_URL_ACCEPTANCE_LEVER_1 = "ConstructionAcceptanceRestService/service/getValueToInitConstructionAcceptancePages";
    public static final String END_URL_ACCEPTANCE_LEVER_2 = "ConstructionAcceptanceRestService/service/getValueToInitConstructionAcceptanceWorkItemsPages";
    //    public static final String END_URL_ACCEPTANCE_LEVER_3 = "ConstructionAcceptanceRestService/service/getValueToInitConstructionAcceptanceDTODetailPages";
    public static final String END_URL_CHANGE_PERSON_CATEGORY = "ConstructionRestService/service/handlingByOtherPerson";

    public static final String END_URL_ACCEPTANCE_LIST_1 = "ConstructionAcceptanceRestService/service/getValueToInitConstructionAcceptanceDTODetailVTAPages";
    public static final String END_URL_ACCEPTANCE_LIST_2 = "ConstructionAcceptanceRestService/service/getValueToInitConstructionAcceptanceDTODetailTBAPages";
    public static final String END_URL_ACCEPTANCE_LIST_3 = "ConstructionAcceptanceRestService/service/getValueToInitConstructionAcceptanceDTODetailVTBPages";
    public static final String END_URL_ACCEPTANCE_LIST_4 = "ConstructionAcceptanceRestService/service/getValueToInitConstructionAcceptanceDTODetailTBBPages";


    public static final String END_URL_UPDATE_ACCEPTANCE = "ConstructionAcceptanceRestService/service/updateVTTBConstructionMerchandise";
    public static final String END_URL_DELETE_ACCEPTANCE = "ConstructionAcceptanceRestService/service/deleteVTTBConstructionMerchandise";
    public static final String END_URL_GET_LIST_IMAGE_ACCEPTANCE = "ConstructionAcceptanceRestService/service/getListImageAcceptance";

    //Constructionextra
    public static final String END_URL_GetConstructionExtraInfoByID = "ConstructionRestService/service/getConstructionExtraInfoByID";
    public static final String END_URL_UpdateConstructionExtraInfo = "ConstructionRestService/service/updateConstructionExtraInfo";
    public static final String END_URL_GetImagesByConstructionExtraIDType = "ConstructionRestService/service/getImagesByConstructionExtraIDType";

    //ConstructionBGMB
    public static final String END_URL_GET_CONSTRUCTION_BGMB_LIST_BY_STATUS = "ConstructionRestService/service/doSearchAssign/";
    public static final String END_URL_GET_CONSTRUCTION_BGMB_STATUS_FOR_DASHBOARD = "ConstructionRestService/service/doSearchDashBoard/";
    public static final String END_URL_GET_CONSTRUCTION_BGMB_LIST_IMAGE = "ConstructionRestService/service/doSearchImage/";
    public static final String END_URL_GET_CONSTRUCTION_BGMB_UPDATE = "ConstructionRestService/service/updateHandoverGround/";
    public static final String END_URL_GET_CONSTRUCTION_BGMB_UPDATE_GIACO_MAY_NO = "ConstructionRestService/service/updateHandoverMachine/";
    public static final String END_URL_GET_CONSTRUCTION_BGMB_UPDATE_TUYEN = "AssignHandoverWsRsService/service/update/";
    public static final String END_URL_IMAGE_CONSTRUCTION_IMAGE_TUYEN = "AssignHandoverWsRsService/service/doSearchImage/";

    public static final String END_URL_IMAGE_CONSTRUCTION_IMAGE_DAILY = "ConstructionRestService/service/imageConstructionTaskDaily/";
    public static final String END_URL_INSERT_CONTRUCTION_TASK_DAILY = "ConstructionRestService/service/insertConstructionTaskDaily/";

    public static final String FORMAT_TIME_CURRENT = "yyyy-mm-dd'T'HH:mm:ss.SSS";
    public static final int TOTAL = 0;
    public static final int DID_NOT_PERFORM = 1;
    public static final int IN_PROCESS = 2;
    public static final int ON_PAUSE = 3;
    public static final int COMPLETE = 4;
    public static final String MANAGE_PLAN = "MANAGE PLAN";

    //SharedPreferences

    public static final String KEY_REMEMBER_LOGIN = "REMEMBER_USER";

    public static final String UserName = "UserName";


    public static final String KEY_LOGIN_USER = PrefManager.getInstance().getString(VConstant.KEY_LOGIN_USER);

    static Gson gson;

    public static SysUserRequest getUser() {
        SysUserRequest sysUserRequest = null;
        try {
            if (gson == null)
                gson = new Gson();
            String request = PrefManager.getInstance().getString(VConstant.KEY_LOGIN_USER);
            sysUserRequest = gson.fromJson(request, SysUserRequest.class);
            if (sysUserRequest != null)
                sysUserRequest.setAuthorities(null);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return sysUserRequest;
    }


    public static SysUserDTO getDTO() {
        SysUserDTO user = null;
        try {
            if (gson == null)
                gson = new Gson();
            String dto = PrefManager.getInstance().getString(VConstant.KEY_REMEMBER_LOGIN);
            if (dto != null)
                user = gson.fromJson(dto, SysUserDTO.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return user;
    }


}
