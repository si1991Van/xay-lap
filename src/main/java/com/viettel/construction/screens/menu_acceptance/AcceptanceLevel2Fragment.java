package com.viettel.construction.screens.menu_acceptance;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.App;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailDTO;
import com.viettel.construction.model.api.ConstructionAcceptanceDTOResponse;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class AcceptanceLevel2Fragment
        extends FragmentListBase<ConstructionAcceptanceCertDetailDTO, ConstructionAcceptanceDTOResponse> {

    private ConstructionAcceptanceCertDetailDTO dto;
    private final String TAG = "VTAcceptanceLevel2";


    @Override
    public void initData() {
        super.initData();
        if (getArguments() != null) {
            dto = (ConstructionAcceptanceCertDetailDTO) getArguments().getSerializable("ConstructionAcceptanceCertDetailDTO");
        }
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_acceptance_level_2;
    }

    @Override
    public AdapterFragmentListBase getAdapterInstance() {
        return new AcceptanceLevel2Adapter(getContext(), listData);
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
            String workItemname;
            if (dto.getConstructionCode() != null) {
                workItemname = StringUtil.removeAccentStr(dto.getWorkItemName()).toLowerCase();
            } else {
                workItemname = "";
            }
            if (workItemname.contains(input)) {
                dataSearch.add(dto);
            }
        }
        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, ConstructionAcceptanceCertDetailDTO>>
    dataSearchExpandableList(String keyWord) {
        return null;
    }

    @Override
    public String getHeaderTitle() {
        return getString(R.string.HangMucTitle);
    }

    @Override
    public int getMenuID() {
        return R.menu.acceptance_of_work_menu_filter;
    }

    @Override
    public List<ConstructionAcceptanceCertDetailDTO> menuItemClick(int menuItem) {
        List<ConstructionAcceptanceCertDetailDTO> data;
        switch (menuItem) {
            default: //case R.id.all:
                data = listData;
                break;
            case R.id.wait_for_acceptance:
                data = filterByStatus("0");
                break;
            case R.id.already_acceptance:
                data = filterByStatus("1");
                break;

        }
        return data;
    }

    private List<ConstructionAcceptanceCertDetailDTO> filterByStatus(String status) {
        List<ConstructionAcceptanceCertDetailDTO> dataSearch = new ArrayList<>();
        for (ConstructionAcceptanceCertDetailDTO dto : listData) {
            String temp;
            if (dto.getStatusAcceptance() != null) {
                temp = dto.getStatusAcceptance();
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
    public List<ConstructionAcceptanceCertDetailDTO>
    getResponseData(ConstructionAcceptanceDTOResponse result) {
        List<ConstructionAcceptanceCertDetailDTO> data = new ArrayList<>();
        if (result.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
            if (result.getListConstructionAcceptanceCertWorkItemsPagesDTO() != null) {
                data = result.getListConstructionAcceptanceCertWorkItemsPagesDTO();
                if (App.getInstance().isNeedUpdateAcceptance()) {
                    App.getInstance().setNeedUpdateAcceptance(false);
                    //Send broadcast to reload
                    if (getActivity() != null) {
                        Log.d(TAG,"AcceptanceLevelReload1 - DashBoardReload");
                        getActivity().sendBroadcast(new Intent(ParramConstant.AcceptanceLevelReload1));
                        getActivity().sendBroadcast(new Intent(ParramConstant.DashBoardReload));
                    }
                }
            }
        }
        return data;
    }

    @Override
    public Object[] getParramLoading() {
        return new Object[]{dto};
    }

    @Override
    public APIType getAPIType() {
        return APIType.END_URL_ACCEPTANCE_LEVER_2;
    }

    @Override
    public Class<ConstructionAcceptanceDTOResponse> responseEntityClass() {
        return ConstructionAcceptanceDTOResponse.class;
    }

    @Override
    public void onItemRecyclerViewclick(ConstructionAcceptanceCertDetailDTO item) {
        //TODO: Đang thi công
        App.getInstance().setNeedUpdateAcceptance(false);
        Intent intent = new Intent(getActivity(), AcceptanceLevel3CameraActivity.class);
        intent.putExtra("data", item);
        startActivity(intent);

       // Toast.makeText(getContext(), "Đang thi công.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemRecyclerViewLongclick(ConstructionAcceptanceCertDetailDTO item) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (App.getInstance().isNeedUpdateAcceptance()) {
            loadData();
        }
    }
}
