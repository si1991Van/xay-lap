package com.viettel.construction.screens.menu_acceptance;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailDTO;
import com.viettel.construction.model.api.ConstructionAcceptanceDTOResponse;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class AcceptanceLevel1Fragment extends
        FragmentListBase<ConstructionAcceptanceCertDetailDTO,
                ConstructionAcceptanceDTOResponse> {

    @Override
    public void initData() {
        super.initData();
        if (getActivity() != null)
            getActivity().registerReceiver(receiverReLoading,
                    new IntentFilter(ParramConstant.AcceptanceLevelReload1));
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
            if (getActivity()!=null)
                getActivity().unregisterReceiver(receiverReLoading);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_acceptance_level_1;
    }

    @Override
    public AdapterFragmentListBase getAdapterInstance() {
        return new AcceptanceLevel1Adapter(getContext(), listData);
    }

    @Override
    public AdapterExpandableListBase getAdapterExpandInstance() {
        return null;
    }

    @Override
    public List<ConstructionAcceptanceCertDetailDTO> dataSearch(String keyWord) {
        String input = StringUtil.removeAccentStr(keyWord).toLowerCase().trim();
        List<ConstructionAcceptanceCertDetailDTO> dataSearch = new ArrayList<>();
        for (ConstructionAcceptanceCertDetailDTO dto : listData) {
            String code, address;
            if (dto.getConstructionCode() != null) {
                code = StringUtil.removeAccentStr(dto.getConstructionCode()).toLowerCase();
            } else {
                code = "";
            }
            if (dto.getAddress() != null) {
                address = StringUtil.removeAccentStr(dto.getAddress()).toLowerCase();
            } else {
                address = "";
            }
            if (code.contains(input) || address.contains(input)) {
                dataSearch.add(dto);
            }
        }
        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, ConstructionAcceptanceCertDetailDTO>> dataSearchExpandableList(String keyWord) {
        return null;
    }

    @Override
    public String getHeaderTitle() {
        return getString(R.string.NghiemThuTitle);
    }

    @Override
    public int getMenuID() {
        return R.menu.acceptance_lever_1_menu;
    }

    @Override
    public List<ConstructionAcceptanceCertDetailDTO> menuItemClick(int menuItem) {
        List<ConstructionAcceptanceCertDetailDTO> data;
        switch (menuItem) {
            default://case R.id.all:
                data = listData;
                break;
            case R.id.acceptance_have_not_finish:
                data = filterByStatus("3");
                break;
            case R.id.acceptance_finished:
                data = filterByStatus("5");
                break;
            case R.id.acceptance_pause:
                data = filterByStatus("4");
                break;
        }
        return data;
    }

    private List<ConstructionAcceptanceCertDetailDTO> filterByStatus(String status) {
        List<ConstructionAcceptanceCertDetailDTO> dataSearch = new ArrayList<>();
        for (ConstructionAcceptanceCertDetailDTO dto : listData) {
            String temp;
            if (dto.getStatusConstruction() != null) {
                temp = dto.getStatusConstruction();
            } else {
                temp = "";
            }
            if (temp.equals(status)) {
                dataSearch.add(dto);
            }
        }
        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, ConstructionAcceptanceCertDetailDTO>> menuItemClickExpandableList(int menuItem) {
        return null;
    }

    @Override
    public List<ConstructionAcceptanceCertDetailDTO> getResponseData(ConstructionAcceptanceDTOResponse result) {
        List<ConstructionAcceptanceCertDetailDTO> data = new ArrayList<>();
        ResultInfo resultInfo = result.getResultInfo();
        if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
            if (result.getListConstructionAcceptanceCertPagesDTO() != null) {
                data = result.getListConstructionAcceptanceCertPagesDTO();
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
        return APIType.END_URL_ACCEPTANCE_LEVER_1;
    }

    @Override
    public Class<ConstructionAcceptanceDTOResponse> responseEntityClass() {
        return ConstructionAcceptanceDTOResponse.class;
    }

    @Override
    public void onItemRecyclerViewclick(ConstructionAcceptanceCertDetailDTO item) {
        Fragment frag = new AcceptanceLevel2Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ConstructionAcceptanceCertDetailDTO", item);
        frag.setArguments(bundle);
        commitChange(frag, true);
    }

    @Override
    public void onItemRecyclerViewLongclick(ConstructionAcceptanceCertDetailDTO item) {

    }
}
