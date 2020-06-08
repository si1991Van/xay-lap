package com.viettel.construction.screens.tabs;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionTaskDTO;
import com.viettel.construction.model.api.ListConstructionTaskAll;
import com.viettel.construction.screens.commons.DetailCV2CameraActivity;
import com.viettel.construction.screens.commons.DetailCVCompleteCameraActivity;
import com.viettel.construction.screens.commons.DetailInProcessCameraActivity;
import com.viettel.construction.screens.menu_construction.ConstructionExpandableAdapter;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;

public class DashboardListSuperviserFragment
        extends FragmentListBase<ConstructionTaskDTO, ListConstructionTaskAll> {

    private int mKey = VConstant.TOTAL;

    @BindView(R.id.btnAddItem)
    View btnAddItem;

    @Override
    public void initData() {
        super.initData();
        btnAddItem.setVisibility(View.GONE);
        if (getArguments() != null) {
            mKey = getArguments().getInt(VConstant.BUNDLE_KEY_FILTER);
            Log.e("Key1", mKey + "");
        }
    }

    @Override
    protected boolean allowExpandableList() {
        return true;
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_construction_child_list;
    }

    @Override
    public AdapterFragmentListBase getAdapterInstance() {
        return new DashboardListAdapter(getContext(), listData, false);
    }

    @Override
    public AdapterExpandableListBase getAdapterExpandInstance() {
        return new ConstructionExpandableAdapter(listDataExpandable, getContext(), false);
    }

    @Override
    public List<ConstructionTaskDTO> dataSearch(String keyWord) {
        List<ConstructionTaskDTO> dataSearch = new ArrayList<>();
        String temp = StringUtil.removeAccentStr(keyWord).toUpperCase().trim();
        for (ConstructionTaskDTO entity : listData) {
            String taskName = StringUtil.removeAccentStr(entity.getTaskName()).toUpperCase();
            String constructionCode;
            if (entity.getConstructionCode() != null) {
                constructionCode = entity.getConstructionCode().toString().toUpperCase();
            } else {
                constructionCode = "";
            }
            if (taskName.contains(temp) || constructionCode.contains(temp)) {
                dataSearch.add(entity);
            }
        }
        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, ConstructionTaskDTO>> dataSearchExpandableList(String keyWord) {
        List<ConstructionTaskDTO> dataSearch = dataSearch(keyWord);
        return buildTree(dataSearch);
    }

    @Override
    public String getHeaderTitle() {
        return getString(R.string.title_dashboard_list_cv);
    }

    @Override
    public int getMenuID() {
        return R.menu.dashboard_cv_filter_menu_1;
    }

    @Override
    public List<ConstructionTaskDTO> menuItemClick(int menuItem) {
        List<ConstructionTaskDTO> data = new ArrayList<>();

        switch (menuItem) {
            case R.id.all:

                data = listData;
                break;
            case R.id.low_process:
                data = filterListByStatus("2");
                break;
            case R.id.on_schedule:
                data = filterListByStatus("1");
                break;
            case R.id.construction:
                break;
        }

        return data;
    }

    private List<ConstructionTaskDTO> filterListByStatus(String status) {
        List<ConstructionTaskDTO> dataSearch =
                Observable.from(listData).filter(x -> x.getCompleteState() != null && x.getCompleteState()
                        .equals(status)).toList().toBlocking().singleOrDefault(new ArrayList<>());

        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, ConstructionTaskDTO>> menuItemClickExpandableList(int menuItem) {
        //Build
        return buildTree(listData);
    }

    private List<ExpandableListModel<String, ConstructionTaskDTO>> buildTree(
            List<ConstructionTaskDTO> targetData) {
        List<ExpandableListModel<String, ConstructionTaskDTO>> data = new ArrayList<>();
        if (targetData != null && targetData.size() > 0) {
            List<String> constructionCodes = Observable.from(targetData).filter(x -> x.getConstructionCode() != null)
                    .map(y -> y.getConstructionCode().trim()).distinct().toList().toBlocking()
                    .singleOrDefault(new ArrayList<>());
            if (constructionCodes != null) {
                for (String header :
                        constructionCodes) {
                    List<ConstructionTaskDTO> children =
                            Observable.from(targetData)
                                    .filter(x -> x.getConstructionCode() != null &&
                                            x.getConstructionCode().trim().equalsIgnoreCase(header))
                                    .toList().toBlocking().singleOrDefault(new ArrayList<>());
                    data.add(new ExpandableListModel<>(header, children));
                }
            }
        }
        return data;
    }

    @Override
    public List<ConstructionTaskDTO> getResponseData(ListConstructionTaskAll result) {
        List<ConstructionTaskDTO> data = new ArrayList<>();
        if (result.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK) &&
                result.getLstConstructionTaskDTO() != null) {
            //TODO: Doan code cu chuoi, theo server binh thuong khong phai filter tu client nhu the nay
            data = loadDataBy_Loading(result.getLstConstructionTaskDTO(), mKey);
        }
        return data;
    }

    private List<ConstructionTaskDTO> loadDataBy_Loading(List<ConstructionTaskDTO> data, int status) {
        List<ConstructionTaskDTO> temp;
        if (mKey == VConstant.TOTAL)
            temp = data;
        else
            temp = Observable.from(data).filter(x -> x.getStatus() != null &&
                    x.getStatus().contains(status + "")).toList().toBlocking().singleOrDefault(new ArrayList<>());
        return temp;
    }


    @Override
    public Object[] getParramLoading() {
        return null;
    }

    @Override
    public APIType getAPIType() {
        return APIType.END_URL_LIST_SUPERVISE_WORK;
    }

    @Override
    public Class<ListConstructionTaskAll> responseEntityClass() {
        return ListConstructionTaskAll.class;
    }

    @Override
    public void onItemRecyclerViewclick(ConstructionTaskDTO data) {
        try {
            Intent intent;
            if (data.getStatus().equals(String.valueOf(VConstant.COMPLETE))) {
                //Hoan thanh
                intent = new Intent(getActivity(), DetailCVCompleteCameraActivity.class);
                intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, data);
                startActivity(intent);
            } else if (data.getQuantityByDate() != null) {
                if (data.getQuantityByDate().equals("1")) {
                    //Công việc theo ngày: Nhiều hình thức thi công
                    intent = new Intent(getActivity(), DetailCV2CameraActivity.class);
                    intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, data);
                    startActivity(intent);
                } else {
                    //Dang thuc hien
                    intent = new Intent(getActivity(), DetailInProcessCameraActivity.class);
                    intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, data);
                    startActivity(intent);
                }

            } else {
                //Dang thuc hien
                intent = new Intent(getActivity(), DetailInProcessCameraActivity.class);
                intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, data);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemRecyclerViewLongclick(ConstructionTaskDTO item) {

    }

}