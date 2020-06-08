package com.viettel.construction.screens.menu_entangle;

import android.content.Intent;
import android.widget.ImageButton;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.App;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.entangle.TioVolumeDTO;
import com.viettel.construction.model.api.entangle.TioVolumeDTORespone;
//import com.viettel.construction.model.api.entangle.TioVolumeDTO;
import com.viettel.construction.model.api.entangle.TioVolumeDTORespone;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

public class VolTioFragment extends FragmentListBase<TioVolumeDTO, TioVolumeDTORespone> {

    @BindView(R.id.btnAddItem)
    ImageButton btnCreate;
    @Override
    public void onResume() {
        super.onResume();
        if (App.getInstance().isNeedUpdateEntangle()) {
            App.getInstance().setNeedUpdateIssue(false);
            loadData();
        }
    }

    @Override
    public void initData() {
        super.initData();
        //TODO: Tạm thời ẩn đi
       // btnCreate.setVisibility(View.GONE);
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_list_construction_entangle;
    }

    @Override
    public AdapterFragmentListBase getAdapterInstance() {
        return new VolTioAdapter(getContext(), listData);
    }

    @Override
    public AdapterExpandableListBase getAdapterExpandInstance() {
        return null;
    }

    @Override
    public List<TioVolumeDTO> dataSearch(String keyWord) {
        String input = StringUtil.removeAccentStr(keyWord).toLowerCase().trim();

        List<TioVolumeDTO> dataSearch = new ArrayList<>();

        for (TioVolumeDTO itemDto : listData) {
            String code;
            if (itemDto.getCoinName() != null) {
                code = StringUtil.removeAccentStr(itemDto.getCoinName()).toLowerCase();
            } else {
                code = "";
            }
            if (code.contains(input)) {
                dataSearch.add(itemDto);
            }
        }
        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, TioVolumeDTO>> dataSearchExpandableList(String keyWord) {
        return null;
    }

    @Override
    public String getHeaderTitle() {
        return getString(R.string.ComplainTitle);
    }

    @Override
    public int getMenuID() {
        return R.menu.list_entangle_menu;
    }

    @Override
    public List<TioVolumeDTO> menuItemClick(int menuItem) {
        List<TioVolumeDTO> data;
        switch (menuItem) {
            default: //case R.id.all:
                data = listData;
                break;
            case R.id.wait_for_confirm_cnt:
                data = filterByStatus("1");
                break;
            case R.id.confirmed_cnt:
                data = filterByStatus("2");
                break;
        }
        return data;
    }

    private List<TioVolumeDTO> filterByStatus(String status) {
//      List<TioVolumeDTO> dataSearch = Observable.from(listData)
        return listData;
    }

    @Override
    public List<ExpandableListModel<String, TioVolumeDTO>> menuItemClickExpandableList(int menuItem) {
        return null;
    }

    @Override
    public List<TioVolumeDTO> getResponseData(TioVolumeDTORespone result) {
        List<TioVolumeDTO> data = new ArrayList<>();
        ResultInfo resultInfo = result.getResultInfo();
        if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
            data = result.getListTiovolumeDTO();
        }
        return data;
    }

    @Override
    public Object[] getParramLoading() {
        return new Object[0];
    }

    @Override
    public APIType getAPIType() {
        return APIType.END_URL_GET_LIST_ENTANGLE_MANAGE;
//        return APIType.END_VOL_TIO;
    }

    @Override
    public Class<TioVolumeDTORespone> responseEntityClass() {
        return TioVolumeDTORespone.class;
    }

    @Override
    public void onItemRecyclerViewclick(TioVolumeDTO item) {
        App.getInstance().setNeedUpdateEntangle(false);
        Intent intent = new Intent(getContext(), EntangleCameraActivity.class);
        intent.putExtra("entangleManageDTO", item);
        startActivity(intent);
    }

    @Override
    public void onItemRecyclerViewLongclick(TioVolumeDTO item) {

    }

    @OnClick(R.id.btnAddItem)
    public void onClickCreate() {
        App.getInstance().setNeedUpdateEntangle(false);
        Intent intent = new Intent(getActivity().getBaseContext(), CreateEntangleCameraActivity.class);
        intent.putExtra("isCreate", 300);
        startActivity(intent);
    }
}
