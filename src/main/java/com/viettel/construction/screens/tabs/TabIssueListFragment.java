package com.viettel.construction.screens.tabs;

import android.content.Intent;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.App;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.issue.IssueDTOResponse;
import com.viettel.construction.model.api.issue.IssueWorkItemDTO;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import rx.Observable;

public class TabIssueListFragment extends FragmentListBase<IssueWorkItemDTO, IssueDTOResponse> {

    private IssueDTOResponse response;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_list_reflect;
    }

    @Override
    public AdapterFragmentListBase getAdapterInstance() {
        return new IssueListAdapter(getContext(), listData);
    }

    @Override
    public AdapterExpandableListBase getAdapterExpandInstance() {
        return null;
    }

    @Override
    public List<IssueWorkItemDTO> dataSearch(String keyWord) {
        List<IssueWorkItemDTO> dataSearch = new ArrayList<>();
        String input = StringUtil.removeAccentStr(keyWord).toLowerCase().trim();
        if (input.length() != 0) {
            for (IssueWorkItemDTO dto : listData) {
                String code, content;
                if (dto.getCode() != null) {
                    code = dto.getCode().toLowerCase();
                } else {
                    code = "";
                }
                if (dto.getContent() != null) {
                    content = StringUtil.removeAccentStr(dto.getContent()).toLowerCase();
                } else {
                    content = "";
                }
                if (code.contains(input) || content.contains(input)) {
                    dataSearch.add(dto);
                }
            }
        }
        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, IssueWorkItemDTO>> dataSearchExpandableList(String keyWord) {
        return null;
    }

    @Override
    public String getHeaderTitle() {
        return getString(R.string.list_reflect);
    }

    @Override
    public int getMenuID() {
        return R.menu.menu_reflect;
    }

    @Override
    public List<IssueWorkItemDTO> menuItemClick(int menuItem) {
        List<IssueWorkItemDTO> dataSearch = listData;
        switch (menuItem) {
            case R.id.open:
                dataSearch = filterByStatus("1");
                break;
            case R.id.close:
                dataSearch = filterByStatus("0");
                break;
        }
        return dataSearch;
    }

    private List<IssueWorkItemDTO> filterByStatus(String status) {
        List<IssueWorkItemDTO> dataSearch = Observable.from(listData).filter(x -> x.getStatus() != null && x.getStatus().contains(status))
                .toList().toBlocking().singleOrDefault(new ArrayList<>());
        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, IssueWorkItemDTO>> menuItemClickExpandableList(int menuItem) {
        return null;
    }

    @Override
    public List<IssueWorkItemDTO> getResponseData(IssueDTOResponse result) {
        response = result;
        List<IssueWorkItemDTO> data = new ArrayList<>();
        if (result.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
            data = result.getListIssueEntityDTO();
        }
        return data;
    }

    @Override
    public Object[] getParramLoading() {
        return new Object[0];
    }

    @Override
    public APIType getAPIType() {
        return APIType.END_URL_LIST_REFLECT;
    }

    @Override
    public Class<IssueDTOResponse> responseEntityClass() {
        return IssueDTOResponse.class;
    }

    @Override
    public void onItemRecyclerViewclick(IssueWorkItemDTO item) {
        try {
            App.getInstance().setNeedUpdateIssue(false);

            Intent intent = new Intent(getContext(), IssueDetailCameraActivity.class);
            intent.putExtra(ParramConstant.IssueDTOType, response.getType());
            intent.putExtra("reflect", item);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemRecyclerViewLongclick(IssueWorkItemDTO item) {

    }

    @OnClick(R.id.btnAddItem)
    public void add() {
        Intent addIssue = new Intent(getContext(),IssueAddNewCameraActivity.class);
        startActivity(addIssue);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (App.getInstance().isNeedUpdateIssue()) {
            App.getInstance().setNeedUpdateIssue(false);
            loadData();
        }
    }
}
