package com.viettel.construction.screens.plan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailDTO;
import com.viettel.construction.model.api.ConstructionAcceptanceDTOResponse;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.screens.menu_construction.ConstructionLevel1Fragment;
import com.viettel.construction.screens.tabs.IssueAddNewCameraActivity;
import com.viettel.construction.screens.wo.WOItemAdapter;
import com.viettel.construction.screens.wo.WOItemFragment;
import com.viettel.construction.server.api.APIType;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;


public class PlanningItemFragment extends FragmentListBase<ConstructionAcceptanceCertDetailDTO,
        ConstructionAcceptanceDTOResponse> {

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onResume() {
        try {
            super.onResume();
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_list_plan;
    }

    @Override
    public AdapterFragmentListBase getAdapterInstance() {
        Log.e("Tag: ", listData.size() + "");
//        return new AcceptanceLevel1Adapter(getContext(), listData);
        if (listData.size() == 0){
            listData.add(new ConstructionAcceptanceCertDetailDTO());
            listData.add(new ConstructionAcceptanceCertDetailDTO());
            listData.add(new ConstructionAcceptanceCertDetailDTO());
            listData.add(new ConstructionAcceptanceCertDetailDTO());
            listData.add(new ConstructionAcceptanceCertDetailDTO());
        }

        return new PlanItemAdapter(getContext(), listData);
    }

    @Override
    public AdapterExpandableListBase getAdapterExpandInstance() {
        return null;
    }

    @Override
    public List<ConstructionAcceptanceCertDetailDTO> dataSearch(String keyWord) {
        return null;
    }

    @Override
    public List<ExpandableListModel<String, ConstructionAcceptanceCertDetailDTO>> dataSearchExpandableList(String keyWord) {
        return null;
    }


    @Override
    public String getHeaderTitle() {
         String title = "";
        if (listData != null) {
            title = "WO (" + listData.size() + ")";
        }
        return title;
    }

    @Override
    public int getMenuID() {
        return 0;
    }

    @Override
    public List<ConstructionAcceptanceCertDetailDTO> menuItemClick(int menuItem) {
        //

        return null;
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
        try {
            Fragment frag = new WOItemFragment();
            Bundle bundle = new Bundle();
//            bundle.putSerializable("CONSTRUCTION_MANAGEMENT_OBJ", item);
//            bundle.putSerializable("SYS_USER_1", sysUser);
            frag.setArguments(bundle);
            commitChange(frag, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemRecyclerViewLongclick(ConstructionAcceptanceCertDetailDTO item) {

    }
    @OnClick(R.id.btnAddItem)
    public void add() {
        Intent addIssue = new Intent(getContext(), CreatePlanActivity.class);
        startActivity(addIssue);
    }
}
