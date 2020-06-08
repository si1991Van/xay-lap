package com.viettel.construction.screens.menu_ex_warehouse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.StockTransResponse;
import com.viettel.construction.model.api.SynStockTransDTO;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class TabPageExWarehouse_ItemFragment extends FragmentListBase<SynStockTransDTO, StockTransResponse> {
    private final String TAG = "VTFragment1";
    private boolean isABill;


    @Override
    public void initData() {
        super.initData();
        if (getActivity() != null)
            getActivity().registerReceiver(receiverReLoading,
                    new IntentFilter(ParramConstant.ExWarehouseReload));
    }

    private BroadcastReceiver receiverReLoading = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                loadData();
                if (getActivity()!=null)
                    getActivity().sendBroadcast(new Intent(ParramConstant.DashBoardReload));
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (getActivity()!=null)
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
        return new TabPageExWarehouse_ItemAdapter(getContext(), listData);
    }

    @Override
    public AdapterExpandableListBase getAdapterExpandInstance() {
        return null;
    }

    @Override
    public List<SynStockTransDTO> dataSearch(String keyWord) {
        String input = StringUtil.removeAccentStr(keyWord).trim().toUpperCase();
        List<SynStockTransDTO> dataSearch = new ArrayList<>();
        for (SynStockTransDTO entity : listData) {
            String oderCode, code, consCode;
            if (entity.getOrderCode() != null) {
                oderCode = entity.getOrderCode().trim().toUpperCase();
            } else {
                oderCode = "";
            }
            if (entity.getCode() != null) {
                code = entity.getCode().trim().toUpperCase();
            } else {
                code = "";
            }
            if (entity.getConsCode() != null) {
                consCode = entity.getConsCode().trim().toUpperCase();

            } else {
                consCode ="";

            }
            if (oderCode.contains(input) || code.contains(input) || consCode.contains(input)) {
                dataSearch.add(entity);
            }

        }
        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, SynStockTransDTO>> dataSearchExpandableList(String keyWord) {
        return null;
    }

    @Override
    public String getHeaderTitle() {
        return getString(R.string.PhieuXuatKhoTitle);
    }

    @Override
    public int getMenuID() {
        return isABill ? R.menu.menu_delivery_bill_filter_a_bill : R.menu.menu_delivery_bill_filter_b_bill;
    }

    @Override
    public List<SynStockTransDTO> menuItemClick(int menuItem) {
        List<SynStockTransDTO> data;
        switch (menuItem) {
            default:// case R.id.all:
                data = listData;
                break;
            case R.id.received:
                data = filterByStatus("1", false, false);
                break;
            case R.id.wait_for_receive:
                data = filterByStatus("0", false, false);
                break;
            case R.id.rejected:
                data = filterByStatus("2", false, false);
                break;
            //cho xac nhan : 0
            //da xac nhan : 1
            //Tu choi xac nhan : 2
            case R.id.wait_confirm:
                data = filterByStatus("0", true, false);
                break;
            case R.id.confirm:
                data = filterByStatus("1", true, false);
                break;
            case R.id.dont_confirm:
                data = filterByStatus("2", true, false);
                break;
            case R.id.over_date_kpi:
                data = filterByStatus("", false, true);
                break;
        }
        return data;
    }

    private List<SynStockTransDTO> filterByStatus(String s, boolean isState, boolean isOverKpi) {
        List<SynStockTransDTO> dataSearch;
        if(isOverKpi){
            dataSearch = Observable.from(listData).filter(x -> x.getOverDateKPI() != null && x.getOverDateKPI().equals("1"))
                    .toList().toBlocking().singleOrDefault(new ArrayList<>());
        }else{
            if (isState) {
                dataSearch = Observable.from(listData).filter(x -> x.getState() != null && x.getState().contains(s))
                        .toList().toBlocking().singleOrDefault(new ArrayList<>());
            } else {

                dataSearch = Observable.from(listData).filter(x -> x.getConfirm() != null && x.getConfirm().contains(s))
                        .toList().toBlocking().singleOrDefault(new ArrayList<>());
            }
        }
        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, SynStockTransDTO>> menuItemClickExpandableList(int menuItem) {
        return null;
    }

    @Override
    public List<SynStockTransDTO> getResponseData(StockTransResponse result) {
        List<SynStockTransDTO> data = new ArrayList<>();
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
        return data;
    }

    @Override
    public Object[] getParramLoading() {
        return new Object[0];
    }

    @Override
    public APIType getAPIType() {
        return APIType.END_URL_GET_LIST_SYN_STOCK_TRANS_DTO;
    }

    @Override
    public Class<StockTransResponse> responseEntityClass() {
        return StockTransResponse.class;
    }

    @Override
    public void onItemRecyclerViewclick(SynStockTransDTO item) {
        Log.d(TAG,"onItemRecyclerViewclick");
        Fragment fragment = new ExWarehouse_Detail_Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(VConstant.BUNDLE_KEY_BILL_ENTITY, item);
        fragment.setArguments(bundle);
        commitChange(fragment, true);
    }

    @Override
    public void onItemRecyclerViewLongclick(SynStockTransDTO item) {

    }
}
