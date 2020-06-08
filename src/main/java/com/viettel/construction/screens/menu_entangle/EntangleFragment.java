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
import com.viettel.construction.model.api.entangle.EntangleManageDTO;
import com.viettel.construction.model.api.entangle.EntangleManageDTORespone;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

public class EntangleFragment extends FragmentListBase<EntangleManageDTO, EntangleManageDTORespone> {

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
        return new EntangleAdapter(getContext(), listData);
    }

    @Override
    public AdapterExpandableListBase getAdapterExpandInstance() {
        return null;
    }

    @Override
    public List<EntangleManageDTO> dataSearch(String keyWord) {
        String input = StringUtil.removeAccentStr(keyWord).toLowerCase().trim();

        List<EntangleManageDTO> dataSearch = new ArrayList<>();

        for (EntangleManageDTO itemDto : listData) {
            String code;
            if (itemDto.getConsCode() != null) {
                code = StringUtil.removeAccentStr(itemDto.getConsCode()).toLowerCase();
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
    public List<ExpandableListModel<String, EntangleManageDTO>> dataSearchExpandableList(String keyWord) {
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
    public List<EntangleManageDTO> menuItemClick(int menuItem) {
        List<EntangleManageDTO> data;
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

    private List<EntangleManageDTO> filterByStatus(String status) {
        List<EntangleManageDTO> dataSearch = Observable.from(listData)
                .filter(x -> x.getObstructedState() != null && x.getObstructedState().equals(status)).toList().toBlocking().singleOrDefault(new ArrayList<>());

        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, EntangleManageDTO>> menuItemClickExpandableList(int menuItem) {
        return null;
    }

    @Override
    public List<EntangleManageDTO> getResponseData(EntangleManageDTORespone result) {
        List<EntangleManageDTO> data = new ArrayList<>();
        ResultInfo resultInfo = result.getResultInfo();
        if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
            data = result.getListEntangleManageDTO();
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
    }

    @Override
    public Class<EntangleManageDTORespone> responseEntityClass() {
        return EntangleManageDTORespone.class;
    }

    @Override
    public void onItemRecyclerViewclick(EntangleManageDTO item) {
        App.getInstance().setNeedUpdateEntangle(false);
        Intent intent = new Intent(getContext(), EntangleCameraActivity.class);
        intent.putExtra("entangleManageDTO", item);
        startActivity(intent);
    }

    @Override
    public void onItemRecyclerViewLongclick(EntangleManageDTO item) {

    }

    @OnClick(R.id.btnAddItem)
    public void onClickCreate() {
        App.getInstance().setNeedUpdateEntangle(false);
        Intent intent = new Intent(getActivity().getBaseContext(), CreateEntangleCameraActivity.class);
        intent.putExtra("isCreate", 300);
        startActivity(intent);
    }
}
