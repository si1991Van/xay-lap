package com.viettel.construction.screens.tabs;

import android.content.Intent;
import android.util.Log;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.App;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionTaskDTO;
import com.viettel.construction.model.api.ListConstructionTaskAll;
import com.viettel.construction.screens.commons.DetailCV2CameraActivity;
import com.viettel.construction.screens.commons.DetailCVCompleteCameraActivity;
import com.viettel.construction.screens.commons.DetailInProcessCameraActivity;
import com.viettel.construction.screens.commons.ChooseWorkChartFragment;
import com.viettel.construction.screens.menu_construction.ConstructionExpandableAdapter;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import rx.Observable;

/**
 * KePham: Cong viec toi lam
 */
public class DashboardListMyTaskFragment
        extends FragmentListBase<ConstructionTaskDTO, ListConstructionTaskAll> {

    private int mKey = VConstant.TOTAL;

    @Override
    public void initData() {
        super.initData();
        if (getArguments() != null) {
            mKey = getArguments().getInt(VConstant.BUNDLE_KEY_FILTER);
            Log.e("Key1", mKey + "");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // load lại nếu như có công việc đang thực hiện chuyển sang tạm dừng
        if (App.getInstance().isNeedUpdate()) {
            App.getInstance().setNeedUpdate(false);
            loadData();
        }
        // load lại nếu có công việc tạo mới
        if (App.getInstance().isNeedUpdateAfterCreateNewWork()) {
            App.getInstance().setNeedUpdateAfterCreateNewWork(false);
            loadData();
        }
        // load lại nếu có công việc thay đổi sau khi xem detail
        if (App.getInstance().isNeedUpdateAfterSaveDetail()) {
            App.getInstance().setNeedUpdateAfterSaveDetail(false);
            loadData();
        }
        // load lại nếu có công việc đang tạm dừng chuyển sang tiếp tục thực hiện
        if (App.getInstance().isNeedUpdateAfterContinue()) {
            App.getInstance().setNeedUpdateAfterContinue(false);
            loadData();
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
        return R.menu.dashboard_cv_menu_filter;
    }

    @Override
    public List<ConstructionTaskDTO> menuItemClick(int menuItem) {
        List<ConstructionTaskDTO> data = new ArrayList<>();
        switch (menuItem) {
            default:
                data = listData;
                break;
            case R.id.low_process:
                data = filterPopupData(2);
                break;
            case R.id.on_schedule:
                data = filterPopupData(1);
                break;
            case R.id.construction:
                break;
            case R.id.did_not_perform:
                data = filterPopupData(VConstant.DID_NOT_PERFORM);
                break;
            case R.id.in_process:
                data = filterPopupData(VConstant.IN_PROCESS);
                break;
            case R.id.on_pause:
                data = filterPopupData(VConstant.ON_PAUSE);
                break;
            case R.id.complete:
                data = filterPopupData(VConstant.COMPLETE);
                break;
        }
        return data;
    }

    private List<ConstructionTaskDTO> filterPopupData(int status) {

        List<ConstructionTaskDTO> dataSearch =
                Observable.from(listData).filter(item -> item.getStatus().equals(String.valueOf(status)))
                        .toList().toBlocking().singleOrDefault(new ArrayList<>());
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
            data = filterLoading_ByStatus(result.getLstConstructionTaskDTO());
        }

        return data;
    }

    private List<ConstructionTaskDTO> filterLoading_ByStatus(List<ConstructionTaskDTO> data) {
        List<ConstructionTaskDTO> results;
        switch (mKey) {
            default:
                results = data;
                break;
            case VConstant.DID_NOT_PERFORM://Chua thuc hien
                results = getData(VConstant.DID_NOT_PERFORM, data);
                break;
            case VConstant.IN_PROCESS://Dang thuc hien
                results = getData(VConstant.IN_PROCESS, data);
                break;
            case VConstant.ON_PAUSE://Tam dung
                results = getData(VConstant.ON_PAUSE, data);
                break;
            case VConstant.COMPLETE://Da hoan thanh
                results = getData(VConstant.COMPLETE, data);
                break;
        }
        return results;
    }

    private List<ConstructionTaskDTO> getData(int status, List<ConstructionTaskDTO> data) {

        List<ConstructionTaskDTO> dataSearch =
                Observable.from(data).filter(item -> item.getStatus() != null && item.getStatus().equals(String.valueOf(status)))
                        .toList().toBlocking().singleOrDefault(new ArrayList<>());
        return dataSearch;
    }

    @Override
    public Object[] getParramLoading() {
        return null;
    }

    @Override
    public APIType getAPIType() {
        return APIType.END_URL_LIST_PERFORM;
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

    @OnClick(R.id.btnAddItem)
    public void onClickCreate() {
        commitChange(new ChooseWorkChartFragment(), true);
    }
}
