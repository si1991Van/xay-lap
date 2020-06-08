package com.viettel.construction.screens.menu_return_vttb;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.App;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionMerchandiseDTOResponse;
import com.viettel.construction.model.api.ConstructionMerchandiseDetailDTO;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class RefundInventoryLevel1Fragment extends FragmentListBase<ConstructionMerchandiseDetailDTO, ConstructionMerchandiseDTOResponse> {


    @Override
    public void onResume() {
        super.onResume();
        if (App.getInstance().isNeedUpdateRefund()) {
            loadData();
        }
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_list_refund_lever_1;
    }

    @Override
    public AdapterFragmentListBase getAdapterInstance() {
        return new RefundInventoryLevel1Adapter(getContext(), listData);
    }

    @Override
    public AdapterExpandableListBase getAdapterExpandInstance() {
        return null;
    }

    @Override
    public List<ConstructionMerchandiseDetailDTO> dataSearch(String keyWord) {
        String input = StringUtil.removeAccentStr(keyWord).trim().toUpperCase();

        List<ConstructionMerchandiseDetailDTO> dataSearch = new ArrayList<>();
        for (ConstructionMerchandiseDetailDTO entity : listData) {
            String code;
            if (entity.getConstructionCode() != null) {
                code = entity.getConstructionCode().trim().toUpperCase();
            } else {
                code = "";
            }
            if (code.contains(input)) {
                dataSearch.add(entity);
            }
        }
        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, ConstructionMerchandiseDetailDTO>> dataSearchExpandableList(String keyWord) {
        return null;
    }

    @Override
    public String getHeaderTitle() {
        return getString(R.string.refund_equipment);
    }

    @Override
    public int getMenuID() {
        return R.menu.list_refund_lv1_menu;
    }

    @Override
    public List<ConstructionMerchandiseDetailDTO> menuItemClick(int menuItem) {
        List<ConstructionMerchandiseDetailDTO> data;
        switch (menuItem) {
            default://case R.id.all:
                data = listData;
                break;
            case R.id.in_process:
                data = filterByStatus("3");
                break;
            case R.id.on_pause:
                data = filterByStatus("4");
                break;
            case R.id.finished:
                data = filterByStatus("5");
                break;
            case R.id.already_acceptance:
                data = filterByStatus("6");
                break;
        }
        return data;
    }

    private List<ConstructionMerchandiseDetailDTO> filterByStatus(String status) {
        List<ConstructionMerchandiseDetailDTO> dataSearch =
                Observable.from(listData).filter(item -> item.getConstructionStatus() != null && item.getConstructionStatus().equals(status))
                        .toList().toBlocking().singleOrDefault(new ArrayList<>());

        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, ConstructionMerchandiseDetailDTO>> menuItemClickExpandableList(int menuItem) {
        return null;
    }

    @Override
    public List<ConstructionMerchandiseDetailDTO> getResponseData(ConstructionMerchandiseDTOResponse result) {
        List<ConstructionMerchandiseDetailDTO> data = new ArrayList<>();
        ResultInfo resultInfo = result.getResultInfo();
        if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK))
            data = result.getListConstructionMerchandisePagesDTO();
        return data;
    }

    @Override
    public Object[] getParramLoading() {
        return new Object[0];
    }

    @Override
    public APIType getAPIType() {
        return APIType.END_URL_GET_LIST_REFUND_LEVER_1;
    }

    @Override
    public Class<ConstructionMerchandiseDTOResponse> responseEntityClass() {
        return ConstructionMerchandiseDTOResponse.class;
    }

    @Override
    public void onItemRecyclerViewclick(ConstructionMerchandiseDetailDTO item) {
        Fragment frag = new RefundInventoryLevel2Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("constructionMerchandiseDetailDTO", item);
        frag.setArguments(bundle);
        commitChange(frag, true);
    }

    @Override
    public void onItemRecyclerViewLongclick(ConstructionMerchandiseDetailDTO item) {

    }
}
