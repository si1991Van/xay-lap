package com.viettel.construction.screens.menu_construction;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.App;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionScheduleItemDTO;
import com.viettel.construction.model.api.ConstructionTaskDTO;
import com.viettel.construction.model.api.ListConstructionTaskAll;
import com.viettel.construction.model.api.SysUserRequest;
import com.viettel.construction.screens.commons.DetailCV2CameraActivity;
import com.viettel.construction.screens.commons.DetailCVCompleteCameraActivity;
import com.viettel.construction.screens.commons.DetailCVGponCameraActivity;
import com.viettel.construction.screens.commons.DetailInProcessCameraActivity;
import com.viettel.construction.screens.commons.ChooseWorkChartFragment;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import rx.Observable;


public class ConstructionLevel2Fragment extends
        FragmentListBase<ConstructionTaskDTO, ListConstructionTaskAll> {

    private SysUserRequest sysUser;
    private ConstructionScheduleItemDTO constructionScheduleItemDTO;
    private String scheduleType = "";
    private final String TAG = "VTLevel2Fragment";

    @Override
    public void initData() {
        super.initData();
        if (getArguments() != null) {
            constructionScheduleItemDTO = (ConstructionScheduleItemDTO) getArguments().getSerializable("CONSTRUCTION_MANAGEMENT_OBJ_2");
            scheduleType = getArguments().getString("type");
            if (getArguments().getSerializable("SYS_USER_2") != null) {
                sysUser = (SysUserRequest) getArguments().getSerializable("SYS_USER_2");
            }
        }
        ((ConstructionLevel2Adapter) adapter).setConstructionScheduleItemDTO(constructionScheduleItemDTO);
        ((ConstructionLevel2Adapter) adapter).setScheduleType(scheduleType);

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
            loadData();
            App.getInstance().setNeedUpdateAfterContinue(false);
            initData();
        }
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_construction_child_list;
    }

    @Override
    public AdapterFragmentListBase getAdapterInstance() {
        return new ConstructionLevel2Adapter(getContext(), listData);
    }

    @Override
    protected boolean allowExpandableList() {
        return true;
    }

    @Override
    public AdapterExpandableListBase getAdapterExpandInstance() {
        return new ConstructionExpandableAdapter(listDataExpandable, getContext(), false);
    }


    @Override
    public List<ConstructionTaskDTO> dataSearch(String keyWord) {
        List<ConstructionTaskDTO> dataSearch = new ArrayList<>();
        String temp = StringUtil.removeAccentStr(keyWord + "").toUpperCase().trim();
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
                    dataSearch.add(entity);
                }
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
        String title = "%s";
        if (constructionScheduleItemDTO.getName() != null)
            title = constructionScheduleItemDTO.getName() + " (%s)";
        return title;
    }

    @Override
    public int getMenuID() {
        return R.menu.dashboard_cv_menu_filter;
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
                Toast.makeText(getContext(), "Đang nghĩ giải pháp", Toast.LENGTH_SHORT).show();
                break;
            case R.id.did_not_perform:
                data = filterListByStatusProgres(VConstant.DID_NOT_PERFORM);
                break;
            case R.id.in_process:
                data = filterListByStatusProgres(VConstant.IN_PROCESS);
                break;
            case R.id.on_pause:
                data = filterListByStatusProgres(VConstant.ON_PAUSE);
                break;
            case R.id.complete:
                data = filterListByStatusProgres(VConstant.COMPLETE);
                break;
            default:
                break;
        }
        return data;

    }

    @Override
    public List<ExpandableListModel<String, ConstructionTaskDTO>> menuItemClickExpandableList(int menuItem) {
        //Build expandable listview
        return buildTree(listData);
    }

    private List<ExpandableListModel<String, ConstructionTaskDTO>> buildTree(
            List<ConstructionTaskDTO> targetData) {
        List<ExpandableListModel<String, ConstructionTaskDTO>> data = new ArrayList<>();
        if (targetData != null && targetData.size() > 0) {
            List<String> constructionCodes = Observable.from(targetData).filter(x -> x.getConstructionCode() != null)
                    .map(y -> y.getConstructionCode()).distinct().toList().toBlocking()
                    .singleOrDefault(new ArrayList<>());
            if (constructionCodes != null) {

                for (String header :
                        constructionCodes) {
                    List<ConstructionTaskDTO> children =
                            Observable.from(targetData)
                                    .filter(x -> x.getConstructionCode() != null &&
                                            x.getConstructionCode().equalsIgnoreCase(header))
                                    .toList().toBlocking().singleOrDefault(new ArrayList<>());
                    data.add(new ExpandableListModel<>(header, children));
                }
            }
        }
        return data;
    }

    private List<ConstructionTaskDTO> filterListByStatusProgres(int progress) {
        List<ConstructionTaskDTO> data =
                Observable.from(listData).filter(x -> x.getStatus() != null
                        && x.getStatus().contains(progress + ""))
                        .toList().toBlocking().singleOrDefault(new ArrayList<>());
        return data;
    }

    /**
     * Filter theo tình trạng hoàn thành hay không
     *
     * @param status
     * @return
     */
    private List<ConstructionTaskDTO> filterListByStatus(String status) {
        List<ConstructionTaskDTO> data = Observable.from(listData).filter(x -> x.getCompleteState() != null
                && x.getCompleteState().trim().equals(status)).toList().toBlocking().singleOrDefault(new ArrayList<>());
        return data;
    }

    @Override
    public List<ConstructionTaskDTO> getResponseData(ListConstructionTaskAll result) {
        List<ConstructionTaskDTO> data = new ArrayList<>();
        if (result.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK) &&
                result.getListConstructionScheduleWorkItemDTO() != null) {
            data = result.getListConstructionScheduleWorkItemDTO();
        }
        return data;
    }

    @Override
    public Object[] getParramLoading() {
        constructionScheduleItemDTO.setScheduleType(scheduleType);
        String author;
        if (sysUser != null) {
            if (!sysUser.getAuthorities().isEmpty()) {
                author = sysUser.getAuthorities();
            } else {
                author = "";
            }
        } else {
            author = "";
        }
        return new Object[]{author, constructionScheduleItemDTO};
    }

    @Override
    public APIType getAPIType() {
        return APIType.END_LIST_CONSTRUCTION_SCHELUDE_WORK_ITEM;
    }

    @Override
    public Class<ListConstructionTaskAll> responseEntityClass() {
        return ListConstructionTaskAll.class;
    }

    /**
     * Kiem tra hoan thanh hang muc
     *
     * @return
     */
    private boolean isCheckCommitiongConstructionCategory() {
        boolean result = false;

        int countCompletedWorkItem = 0;
        for (ConstructionTaskDTO item :
                listData) {
            if (item.getStatus() != null && item.getStatus().equalsIgnoreCase(VConstant.COMPLETE + ""))
                countCompletedWorkItem++;
        }


        if (countCompletedWorkItem == listData.size() - 1) {
            result = true;//Have to check
        }
        return result;
    }

    @Override
    public void onItemRecyclerViewclick(ConstructionTaskDTO item) {
        try {
            ConstructionTaskDTO work = item;
            SysUserRequest sysUserRequest = (SysUserRequest) getArguments().getSerializable("SYS_USER_2");
            Intent intent;

            Log.d(TAG, "getQuantityByDate : " + item.getQuantityByDate() + "getCheckBGMB : " + item.getCheckBGMB() + " - getHandoverDateBuildBGMB : " + item.getHandoverDateBuildBGMB() +
                    " - getCatConstructionTypeId : " + item.getCatConstructionTypeId());
            if (item.getCatConstructionTypeId() == 1 && item.getCheckBGMB().equals("1") && item.getHandoverDateBuildBGMB() == null) {
                Toast.makeText(getContext(), "Hạng mục chưa được BGMB.", Toast.LENGTH_LONG).show();
                return;
            }
            if (work.getQuantityByDate() != null) {
                Log.d(TAG, "getQuantityByDate : " + work.getQuantityByDate());
                // quantityByDate = 2 => keo cap
                // quantityByDate = 3 => han noi
                // quantityByDate = 4 => treo tu
                if (work.getQuantityByDate().equals("1")) {
                    if (sysUserRequest != null) {
                        if (sysUserRequest.getAuthorities().equals(VConstant.MANAGE_PLAN)) {
                            if (scheduleType.equals("2")) {
                                Log.d(TAG, "Tuyen - update1");
                                intent = new Intent(getActivity(), DetailCV2CameraActivity.class);
                                intent.putExtra(ParramConstant.DetailCV2ActivityCompleted, true);
                                intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                                startActivity(intent);
                            } else {
                                if (work.getStatus().equals(String.valueOf(VConstant.COMPLETE))) {
                                    Log.d(TAG, "Tuyen - update2 -status : " + work.getStatus());
                                    intent = new Intent(getActivity(), DetailCV2CameraActivity.class);
                                    intent.putExtra(ParramConstant.DetailCV2ActivityCompleted, true);
                                    intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                                    startActivity(intent);
                                } else {
                                    Log.d(TAG, "Tuyen - update3");
                                    intent = new Intent(getActivity(), DetailCV2CameraActivity.class);
                                    intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                                    startActivity(intent);
                                }
                            }
                        }
                    } else {
                        if (work.getStatus().equals(String.valueOf(VConstant.COMPLETE))) {
                            Log.d(TAG, "Tuyen - update4 - status : " + work.getStatus());
                            intent = new Intent(getActivity(), DetailCV2CameraActivity.class);
                            intent.putExtra(ParramConstant.DetailCV2ActivityCompleted, true);
                            intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                            startActivity(intent);
                        } else {
                            Log.d(TAG, "Tuyen - update5");
                            intent = new Intent(getActivity(), DetailCV2CameraActivity.class);
                            intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                            startActivity(intent);
                        }
                    }
                } else if (work.getQuantityByDate().equals("2") || work.getQuantityByDate().equals("3") || work.getQuantityByDate().equals("4")) {
                    //check number cua tong tuyen

                    int number = Integer.parseInt(work.getQuantityByDate());
                    Log.d(TAG, "getQuantityByDate 1 : " + number);
                    switch (number) {
                        case 2:
                            if (work.getAmount() == null || work.getAmount().intValue() == 0) {
                                Toast.makeText(getContext(), "Công trình chưa được gán giá trị tổng tuyến.Vui lòng bổ sung!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            break;
                        case 3:
                            if (work.getTotalAmountGate() == null || work.getTotalAmountGate().intValue() == 0) {
                                Toast.makeText(getContext(), "Công trình chưa được gán giá trị tổng số cổng.Vui lòng bổ sung!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            break;
                        case 4:
                            if (work.getTotalAmountChest() == null || work.getTotalAmountChest().intValue() == 0) {
                                Toast.makeText(getContext(), "Công trình chưa được gán giá trị tổng số tủ.Vui lòng bổ sung!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            break;
                    }
                    if (sysUserRequest != null) {
                        if (sysUserRequest.getAuthorities().equals(VConstant.MANAGE_PLAN)) {
                            if (scheduleType.equals("2")) {
                                Log.d(TAG, "gpon - update1");
                                intent = new Intent(getActivity(), DetailCVGponCameraActivity.class);
                                intent.putExtra(ParramConstant.DetailCV2ActivityCompleted, true);
                                intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                                startActivity(intent);
                            } else {
                                if (work.getStatus().equals(String.valueOf(VConstant.COMPLETE))) {
                                    Log.d(TAG, "gpon - update2 -status : " + work.getStatus());
                                    intent = new Intent(getActivity(), DetailCVGponCameraActivity.class);
                                    intent.putExtra(ParramConstant.DetailCV2ActivityCompleted, true);
                                    intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                                    startActivity(intent);
                                } else {
                                    Log.d(TAG, "gpon - update3");
                                    intent = new Intent(getActivity(), DetailCVGponCameraActivity.class);
                                    intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                                    startActivity(intent);
                                }
                            }
                        }
                    } else {
                        if (work.getStatus().equals(String.valueOf(VConstant.COMPLETE))) {
                            Log.d(TAG, "gpon - update4 -status : " + work.getStatus());
                            intent = new Intent(getActivity(), DetailCVGponCameraActivity.class);
                            intent.putExtra(ParramConstant.DetailCV2ActivityCompleted, true);
                            intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                            startActivity(intent);
                        } else {
                            Log.d(TAG, "gpon - update5 - " + isCheckCommitiongConstructionCategory());
                            intent = new Intent(getActivity(), DetailCVGponCameraActivity.class);
                            intent.putExtra(ParramConstant.AllowConfirmWorkItem, isCheckCommitiongConstructionCategory());
                            intent.putExtra(ParramConstant.ConstructionScheduleItemDTO, constructionScheduleItemDTO);
                            intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                            startActivity(intent);
                        }
                    }
                } else {
                    if (sysUserRequest != null) {
                        if (sysUserRequest.getAuthorities().equals(VConstant.MANAGE_PLAN)) {
                            if (scheduleType.equals("2")) {
                                intent = new Intent(getActivity(), DetailCVCompleteCameraActivity.class);
                                intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                                startActivity(intent);
                            } else {
                                if (work.getStatus().equals(String.valueOf(VConstant.COMPLETE))) {
                                    intent = new Intent(getActivity(), DetailCVCompleteCameraActivity.class);
                                    intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                                    startActivity(intent);
                                } else {
                                    intent = new Intent(getActivity(), DetailInProcessCameraActivity.class);
                                    intent.putExtra(ParramConstant.CheckImage, work.getCheckImage());
                                    intent.putExtra(ParramConstant.AllowConfirmWorkItem, isCheckCommitiongConstructionCategory());
                                    intent.putExtra(ParramConstant.ConstructionScheduleItemDTO, constructionScheduleItemDTO);
                                    intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                                    startActivity(intent);
                                }
                            }
                        }
                    } else {
                        if (work.getStatus().equals(String.valueOf(VConstant.COMPLETE))) {
                            intent = new Intent(getActivity(), DetailCVCompleteCameraActivity.class);
                            intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                            startActivity(intent);
                        } else {
                            intent = new Intent(getActivity(), DetailInProcessCameraActivity.class);
                            intent.putExtra(ParramConstant.CheckImage, work.getCheckImage());
                            intent.putExtra(ParramConstant.AllowConfirmWorkItem, isCheckCommitiongConstructionCategory());
                            intent.putExtra(ParramConstant.ConstructionScheduleItemDTO, constructionScheduleItemDTO);
                            intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                            startActivity(intent);
                        }
                    }
                }
            } else {
                if (sysUserRequest != null) {
                    if (sysUserRequest.getAuthorities().equals(VConstant.MANAGE_PLAN)) {
                        if (scheduleType.equals("2")) {
                            Log.d(TAG, "bydatenull - update1");
                            intent = new Intent(getActivity(), DetailCVCompleteCameraActivity.class);
                            intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                            startActivity(intent);
                        } else {
                            if (work.getStatus().equals(String.valueOf(VConstant.COMPLETE))) {
                                Log.d(TAG, "bydatenull - update2 - status : " + work.getStatus());
                                intent = new Intent(getActivity(), DetailCVCompleteCameraActivity.class);
                                intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                                startActivity(intent);
                            } else {
                                Log.d(TAG, "bydatenull - update3");
                                intent = new Intent(getActivity(), DetailInProcessCameraActivity.class);
                                intent.putExtra(ParramConstant.CheckImage, work.getCheckImage());
                                intent.putExtra(ParramConstant.AllowConfirmWorkItem, isCheckCommitiongConstructionCategory());
                                intent.putExtra(ParramConstant.ConstructionScheduleItemDTO, constructionScheduleItemDTO);
                                intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                                startActivity(intent);
                            }
                        }
                    }
                } else {
                    if (work.getStatus().equals(String.valueOf(VConstant.COMPLETE))) {
                        Log.d(TAG, "bydatenull - update4 - status : " + work.getStatus());
                        intent = new Intent(getActivity(), DetailCVCompleteCameraActivity.class);
                        intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                        startActivity(intent);
                    } else {
                        Log.d(TAG, "bydatenull - update5 : " + isCheckCommitiongConstructionCategory());
                        intent = new Intent(getActivity(), DetailInProcessCameraActivity.class);
                        intent.putExtra(ParramConstant.CheckImage, work.getCheckImage());
                        intent.putExtra(ParramConstant.AllowConfirmWorkItem, isCheckCommitiongConstructionCategory());
                        intent.putExtra(ParramConstant.ConstructionScheduleItemDTO, constructionScheduleItemDTO);
                        intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                        startActivity(intent);
                    }
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
    public void addItem() {
        App.getInstance().setAuthor(null);
        commitChange(new ChooseWorkChartFragment(), true);
    }
}
