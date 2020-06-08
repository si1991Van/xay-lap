package com.viettel.construction.screens.wo;

import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailDTO;
import com.viettel.construction.model.api.ConstructionAcceptanceDTOResponse;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.screens.tabs.CompleteCategoryCameraActivity;
import com.viettel.construction.server.api.APIType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WOItemFragment extends FragmentListBase<ConstructionAcceptanceCertDetailDTO,
        ConstructionAcceptanceDTOResponse> {

    @BindView(R.id.sp_construction)
    Spinner spConstruction;
    @BindView(R.id.sp_type_wo)
    Spinner spTypeWo;

    String[] itemConstruction = {"HNM", "SL2", "TEST206", "COGTRIH3005"};
    String[] itemType = {"dong", "mo", "noi bo"};

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onResume() {
        try {
            super.onResume();
            loadData();
            codeSpinner(itemConstruction, spConstruction);
            codeSpinner(itemType, spTypeWo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_list_wo;
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

        return new WOItemAdapter(getContext(), listData);
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
        return R.menu.menu_wo_status;
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
        Intent intent = new Intent(getContext(), DetailWOActivity.class);
        intent.putExtra("Item_WO", item);
        startActivity(intent);
    }

    @Override
    public void onItemRecyclerViewLongclick(ConstructionAcceptanceCertDetailDTO item) {

    }

    private void codeSpinner(String[] item, Spinner spinner){
        ArrayAdapter<String> langAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, item );
        langAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(langAdapter);
    }
}
