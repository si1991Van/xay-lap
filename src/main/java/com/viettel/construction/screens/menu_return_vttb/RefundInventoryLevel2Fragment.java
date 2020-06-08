package com.viettel.construction.screens.menu_return_vttb;

import android.widget.Toast;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.App;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionMerchandiseDTORequest;
import com.viettel.construction.model.api.ConstructionMerchandiseDTOResponse;
import com.viettel.construction.model.api.ConstructionMerchandiseDetailDTO;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class RefundInventoryLevel2Fragment extends FragmentListBase<ConstructionMerchandiseDetailDTO, ConstructionMerchandiseDTOResponse> {

    private ConstructionMerchandiseDetailDTO constructionMerchandiseDetailDTO;

    @Override
    public void onResume() {
        super.onResume();
        if (App.getInstance().isNeedUpdateRefund()) {
            App.getInstance().setNeedUpdateRefund(false);
            loadData();
        }
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_list_refund_lever_1;
    }

    @Override
    public void initData() {
        super.initData();
        if (getArguments() != null) {
            constructionMerchandiseDetailDTO = (ConstructionMerchandiseDetailDTO) getArguments().getSerializable("constructionMerchandiseDetailDTO");
        }
    }

    @Override
    public AdapterFragmentListBase getAdapterInstance() {
        return new RefundInventoryLevel2Adapter(getContext(), listData);
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
            String name;
            if (entity.getConstructionCode() != null) {
                name = StringUtil.removeAccentStr(entity.getWorkItemName()).trim().toUpperCase();
            } else {
                name = "";
            }
            if (name.contains(input)) {
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
        return R.menu.list_refund_lv2_menu;
    }

    @Override
    public List<ConstructionMerchandiseDetailDTO> menuItemClick(int menuItem) {
        List<ConstructionMerchandiseDetailDTO> data;
        switch (menuItem) {
            default:// case R.id.all:
                data = listData;
                break;
            case R.id.not_controlled:
                data = filterByStatus("0");
                break;
            case R.id.checked_chart:
                data = filterByStatus("1");
                break;
        }
        return data;
    }

    private List<ConstructionMerchandiseDetailDTO> filterByStatus(String status) {
        List<ConstructionMerchandiseDetailDTO> dataSearch =
                Observable.from(listData)
                        .filter(item -> item != null && item.getStatusComplete().equals(status))
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
            data = result.getListConstructionMerchandiseWorkItemPagesDTO();
        return data;
    }

    @Override
    public Object[] getParramLoading() {
        ConstructionMerchandiseDTORequest request = new ConstructionMerchandiseDTORequest();
        request.setConstructionMerchandiseDetailDTO(constructionMerchandiseDetailDTO);
        request.setSysUserRequest(VConstant.getUser());
        return new Object[]{request};
    }

    @Override
    public APIType getAPIType() {
        return APIType.END_URL_GET_LIST_REFUND_LEVER_2;
    }

    @Override
    public Class<ConstructionMerchandiseDTOResponse> responseEntityClass() {
        return ConstructionMerchandiseDTOResponse.class;
    }

    @Override
    public void onItemRecyclerViewclick(ConstructionMerchandiseDetailDTO item) {
        //TODO: Đang thi công
//        App.getInstance().setNeedUpdateRefund(false);
//        Intent intent = new Intent(getActivity(), RefundItemLevel3Activity.class);
//        intent.putExtra("constructionMerchandiseDetailDTO", item);
//        startActivity(intent);

        Toast.makeText(getContext(), "Đang thi công.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemRecyclerViewLongclick(ConstructionMerchandiseDetailDTO item) {

    }
}
