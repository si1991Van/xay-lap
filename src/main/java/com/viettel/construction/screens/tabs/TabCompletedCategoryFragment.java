package com.viettel.construction.screens.tabs;

import android.content.Intent;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.App;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.WorkItemDetailDTO;
import com.viettel.construction.model.api.WorkItemResult;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class TabCompletedCategoryFragment extends FragmentListBase<WorkItemDetailDTO, WorkItemResult> {


    @Override
    public void onResume() {
        super.onResume();
        if (App.getInstance().isNeedUpdateCompleteCategory()) {
            App.getInstance().setNeedUpdateCompleteCategory(false);
            loadData();
        }
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_category_complete;
    }

    @Override
    public AdapterFragmentListBase getAdapterInstance() {
        return new CompletedCategoryAdapter(getContext(), listData);
    }

    @Override
    public AdapterExpandableListBase getAdapterExpandInstance() {
        return null;
    }

    @Override
    public List<WorkItemDetailDTO> dataSearch(String keyWord) {
        String input = StringUtil.removeAccentStr(keyWord).toUpperCase().trim();
        List<WorkItemDetailDTO> dataSearch = new ArrayList<>();
        for (WorkItemDetailDTO workItemDetailDTO : listData) {
            if (workItemDetailDTO.getConstructionCode() != null && workItemDetailDTO.getName() != null) {
                String constructionCode = StringUtil.removeAccentStr(workItemDetailDTO.getConstructionCode()).toUpperCase();
                String name = StringUtil.removeAccentStr(workItemDetailDTO.getName()).toString().toUpperCase();
                if (constructionCode.contains(input) || name.contains(input)) {
                    dataSearch.add(workItemDetailDTO);
                }
            }
        }
        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, WorkItemDetailDTO>> dataSearchExpandableList(String keyWord) {
        return null;
    }

    @Override
    public String getHeaderTitle() {
        return getString(R.string.complete_category1);
    }

    @Override
    public int getMenuID() {
        return R.menu.menu_complete_category;
    }

    @Override
    public List<WorkItemDetailDTO> menuItemClick(int menuItem) {
        List<WorkItemDetailDTO> data;
        switch (menuItem) {
            default:// case R.id.all:
                data = listData;
                break;
            case R.id.confirm:
                data = filterByStatus("3");
                break;
            case R.id.dont_confirm:
                data = filterByStatus("2");
                break;
        }
        return data;
    }

    private List<WorkItemDetailDTO> filterByStatus(String status) {
        List<WorkItemDetailDTO> dataSearch = rx.Observable.from(listData)
                .filter(x -> x.getStatus().contains(status))
                .toList().toBlocking().singleOrDefault(new ArrayList<>());
        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, WorkItemDetailDTO>> menuItemClickExpandableList(int menuItem) {
        return null;
    }

    @Override
    public List<WorkItemDetailDTO> getResponseData(WorkItemResult result) {
        List<WorkItemDetailDTO> data = new ArrayList<>();
        WorkItemResult itemResult = WorkItemResult.class.cast(result);
        if (itemResult.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
            if (itemResult.getListWorkItemDTO() != null) {
                data = itemResult.getListWorkItemDTO();
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
        return APIType.END_URL_GET_LIST_COMPLETE_CATEGORY;
    }

    @Override
    public Class<WorkItemResult> responseEntityClass() {
        return WorkItemResult.class;
    }

    @Override
    public void onItemRecyclerViewclick(WorkItemDetailDTO item) {

        Intent intent = new Intent(getContext(), CompleteCategoryCameraActivity.class);
        intent.putExtra("workDTO_data", item);
        startActivity(intent);
    }

    @Override
    public void onItemRecyclerViewLongclick(WorkItemDetailDTO item) {

    }
}
