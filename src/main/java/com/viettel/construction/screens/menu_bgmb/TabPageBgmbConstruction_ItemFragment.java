package com.viettel.construction.screens.menu_bgmb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.App;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.ItemSpinnerBgmb;
import com.viettel.construction.model.api.ConstructionBGMBDTO;
import com.viettel.construction.model.api.ConstructionBGMBResponse;
import com.viettel.construction.screens.tabs.TabDashboardChartFragment;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class TabPageBgmbConstruction_ItemFragment extends FragmentListBase<ConstructionBGMBDTO, ConstructionBGMBResponse> {

    private final String TAG = "VTItemFragment";
    private boolean isABill;
    public static List<ItemSpinnerBgmb> mListHouse = new ArrayList<>();
    public static List<ItemSpinnerBgmb> mListGrounding = new ArrayList<>();

    @Override
    public void initData() {
        super.initData();
        if (getActivity() != null)
            getActivity().registerReceiver(receiverReLoading,
                    new IntentFilter(ParramConstant.BgmbReload));
    }

    private BroadcastReceiver receiverReLoading = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                loadData();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (getActivity() != null)
                getActivity().unregisterReceiver(receiverReLoading);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setABill(boolean ABill) {
        isABill = ABill;
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_abill;
    }

    @Override
    public AdapterFragmentListBase getAdapterInstance() {
        return new TabPageBgmbConstruction_ItemAdapter(getContext(), listData);
    }

    @Override
    public AdapterExpandableListBase getAdapterExpandInstance() {
        return null;
    }

    @Override
    public List<ConstructionBGMBDTO> dataSearch(String keyWord) {
        String input = StringUtil.removeAccentStr(keyWord).trim().toUpperCase();
        List<ConstructionBGMBDTO> dataSearch = new ArrayList<>();
        for (ConstructionBGMBDTO entity : listData) {
            String receivedDate, code, consCode;
            if (entity.getReceivedDate() != null) {
                receivedDate = new SimpleDateFormat("dd/MM/yyyy").format(entity.getReceivedDate()).toUpperCase();
            } else {
                receivedDate = "";
            }

            if (entity.getConstructionCode() != null) {
                consCode = entity.getConstructionCode().trim().toUpperCase();

            } else {
                consCode = "";

            }
            if (receivedDate.contains(input) || consCode.contains(input)) {
                dataSearch.add(entity);
            }

        }
        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, ConstructionBGMBDTO>> dataSearchExpandableList(String keyWord) {
        return null;
    }

    @Override
    public String getHeaderTitle() {
        return getString(R.string.BanGiaoMB_Title);
    }


    public String getHintSearchbox() {
        return "abc";
    }

    @Override
    public int getMenuID() {
        return R.menu.list_bgmb_menu;
    }

    private List<ConstructionBGMBDTO> filterByStatus(long s, Boolean isState) {
        List<ConstructionBGMBDTO> dataSearch;
        if (isState) {
            dataSearch = Observable.from(listData).filter(x -> x.getReceivedStatus() > 0 && x.getReceivedStatus() == s)
                    .toList().toBlocking().singleOrDefault(new ArrayList<>());
        } else {

            dataSearch = Observable.from(listData).filter(x -> x.getReceivedStatus() > 0 && x.getReceivedStatus() == s)
                    .toList().toBlocking().singleOrDefault(new ArrayList<>());
        }
        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, ConstructionBGMBDTO>> menuItemClickExpandableList(int menuItem) {
        return null;
    }

    @Override
    public List<ConstructionBGMBDTO> menuItemClick(int menuItem) {
        List<ConstructionBGMBDTO> data;
        switch (menuItem) {
            case R.id.all:
                data = listData;
                break;
            case R.id.notreceived:
                data = filterByStatus(1, false);
                break;
            case R.id.received:
                data = filterByStatus(2, false);
                break;
            case R.id.receive_vuong:
                data = filterByStatus(3, false);
                break;
            case R.id.receive_vuong_vattu:
                data = filterByStatus(4, false);
                break;
            case R.id.receive_vattu:
                data = filterByStatus(5, false);
                break;
            default:// case R.id.all:
                data = listData;
                break;
        }
        return data;
    }

    @Override
    public List<ConstructionBGMBDTO> getResponseData(ConstructionBGMBResponse result) {
        List<ConstructionBGMBDTO> data = new ArrayList<>();
        if (result.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
            Log.d(TAG, "getResponseData - Load Data is OK");
            if (result.getAssignHandoverDTO() != null && result.getAssignHandoverDTO().size() != 0) {
                data = result.getAssignHandoverDTO();
            }

            if (result.getHouseType() != null && result.getHouseType().size() > 0) {
                mListHouse = result.getHouseType();
            }

            if (result.getGroundingType() != null && result.getGroundingType().size() > 0) {
                mListGrounding = result.getGroundingType();
            }

            if (App.getInstance().isNeedUpdateBGMB()) {
                if (getActivity() != null)
                    getActivity().sendBroadcast(new Intent(ParramConstant.DashBoardReload));
            }

            App.getInstance().setNeedUpdateBGMB(false);

            if ((result.getTotalRecordReceived() + result.getTotalRecordNotReceived()) != (TabDashboardChartFragment.CheckbgmgReceived + TabDashboardChartFragment.CheckbgmbNotReceived)) {
                if (getActivity() != null)
                    getActivity().sendBroadcast(new Intent(ParramConstant.DashBoardReload));
            }
        }
        return data;
    }

    @Override
    public Object[] getParramLoading() {
        return new Object[0];
    }

    @Override
    public APIType getAPIType() {
        return APIType.END_URL_GET_CONSTRUCTION_BGMB_LIST_BY_STATUS;
    }

    @Override
    public Class<ConstructionBGMBResponse> responseEntityClass() {
        return ConstructionBGMBResponse.class;
    }

    @Override
    public void onItemRecyclerViewclick(ConstructionBGMBDTO item) {
        Intent intent;
        Log.d(TAG, "onItemRecyclerViewclick : " + item.getReceivedStatus() + "- getCatConstructionType : " + item.getCatConstructionType());
        if (item.getCatConstructionType() == 2) {
            // truong hop bgmb tuyen
            if (item.getReceivedStatus() == 1) {//update
                intent = new Intent(getActivity(), BgmbUpdateTuyenActivity.class);
                intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_BGMB, item);
                startActivity(intent);
            } else {
                intent = new Intent(getActivity(), BgmbTuyenActivity.class);
                intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_BGMB, item);
                startActivity(intent);
            }

        }else {
            // truong hop cong trinh con lai
            if (item.getReceivedStatus() == 1) {//update
                intent = new Intent(getActivity(), BgmbUpdateActivity.class);
                intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_BGMB, item);
                startActivity(intent);
            } else {
                intent = new Intent(getActivity(), BgmbDetailActivity.class);
                intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_BGMB, item);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onItemRecyclerViewLongclick(ConstructionBGMBDTO item) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (App.getInstance().isNeedUpdateBGMB()) {
            loadData();
        }
    }
}
