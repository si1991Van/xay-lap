package com.viettel.construction.screens.tabs;

import android.content.Intent;
import androidx.fragment.app.Fragment;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.App;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionTaskDTO;
import com.viettel.construction.model.api.ListConstructionTaskAll;
import com.viettel.construction.screens.commons.ChooseWorkChartFragment;
import com.viettel.construction.screens.commons.DetailCV2CameraActivity;
import com.viettel.construction.screens.commons.DetailCVCompleteCameraActivity;
import com.viettel.construction.screens.commons.DetailInProcessCameraActivity;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class TabWorkItemToDayFragment extends FragmentListBase<ConstructionTaskDTO, ListConstructionTaskAll> {

    @Override
    public void onResume() {
        super.onResume();
        // load lại nếu có công việc mới vừa được thêm
        if (App.getInstance().isNeedUpdateAfterCreateNewWork()) {
            App.getInstance().setNeedUpdateAfterCreateNewWork(false);
            loadData();
        }
        // load lại nếu có công việc thay đổi sau khi xem chi tiết
        if (App.getInstance().isNeedUpdateAfterSaveDetail()) {
            App.getInstance().setNeedUpdateAfterSaveDetail(false);
            loadData();
        }

        if (App.getInstance().isNeedUpdate()) {
            App.getInstance().setNeedUpdate(false);
            loadData();
        }
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_dashboard_cv_list_on_day;
    }

    @Override
    public AdapterFragmentListBase getAdapterInstance() {
        return new DashboardListAdapter(getContext(), listData, false);
    }

    @Override
    public AdapterExpandableListBase getAdapterExpandInstance() {
        return null;
    }

    @Override
    public List<ConstructionTaskDTO> dataSearch(String keyWord) {
        List<ConstructionTaskDTO> data = new ArrayList<>();
        String temp = StringUtil.removeAccentStr(keyWord).toUpperCase().trim();
        if (temp.length() != 0) {
            for (ConstructionTaskDTO entity : listData) {
                String taskName = StringUtil.removeAccentStr(entity.getTaskName()).toUpperCase();
                String constructionCode;
                if (entity.getConstructionCode() != null) {
                    constructionCode = entity.getConstructionCode().toString().toUpperCase();
                } else {
                    constructionCode = "";
                }
                if (taskName.contains(temp) || constructionCode.contains(temp)) {
                    data.add(entity);
                }
            }
        }
        return data;
    }

    @Override
    public List<ExpandableListModel<String, ConstructionTaskDTO>> dataSearchExpandableList(String keyWord) {
        return null;
    }

    @Override
    public String getHeaderTitle() {
        return getString(R.string.work_day);
    }

    @Override
    public int getMenuID() {
        return 0;
    }

    @Override
    public List<ConstructionTaskDTO> menuItemClick(int menuItem) {
        return null;
    }

    @Override
    public List<ExpandableListModel<String, ConstructionTaskDTO>> menuItemClickExpandableList(int menuItem) {
        return null;
    }

    @Override
    public List<ConstructionTaskDTO> getResponseData(ListConstructionTaskAll result) {
        List<ConstructionTaskDTO> data = new ArrayList<>();
        if (result.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
            if (result.getLstConstructionTask() != null) {
                data = result.getLstConstructionTaskDTO();
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
        return APIType.END_URL_GET_WORK_ON_DAY;
    }

    @Override
    public Class<ListConstructionTaskAll> responseEntityClass() {
        return ListConstructionTaskAll.class;
    }

    @Override
    public void onItemRecyclerViewclick(ConstructionTaskDTO constructionTaskDTO) {
        try {
            Intent intent;
            if (constructionTaskDTO.getQuantityByDate() != null) {
                if (constructionTaskDTO.getQuantityByDate().equals("1")) {
                    if (constructionTaskDTO.getStatus().equals(String.valueOf(VConstant.COMPLETE))) {
                        intent = new Intent(getActivity(), DetailCV2CameraActivity.class);
                        intent.putExtra(ParramConstant.DetailCV2ActivityCompleted,true);
                        intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, constructionTaskDTO);
                        startActivity(intent);
                    } else {
                        intent = new Intent(getActivity(), DetailCV2CameraActivity.class);
                        intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, constructionTaskDTO);
                        startActivity(intent);
                    }
                } else {
                    if (constructionTaskDTO.getStatus().equals(String.valueOf(VConstant.COMPLETE))) {
                        intent = new Intent(getActivity(), DetailCVCompleteCameraActivity.class);
                        intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, constructionTaskDTO);
                        startActivity(intent);
                    } else {
                        intent = new Intent(getActivity(), DetailInProcessCameraActivity.class);
                        intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, constructionTaskDTO);
                        startActivity(intent);
                    }
                }
            } else {
                if (constructionTaskDTO.getStatus().equals(String.valueOf(VConstant.COMPLETE))) {
                    intent = new Intent(getActivity(), DetailCVCompleteCameraActivity.class);
                    intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, constructionTaskDTO);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), DetailInProcessCameraActivity.class);
                    intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, constructionTaskDTO);
                    startActivity(intent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemRecyclerViewLongclick(ConstructionTaskDTO item) {

    }

    @OnClick(R.id.btnAddItem)
    public void onClickAddItem() {
        App.getInstance().setAuthor(null);
        Fragment frag = new ChooseWorkChartFragment();

        commitChange(frag,true);
    }
}
