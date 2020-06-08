package com.viettel.construction.screens.menu_construction;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.widget.Toast;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.App;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionScheduleDTO;
import com.viettel.construction.model.api.ConstructionScheduleDTOResponse;
import com.viettel.construction.model.api.ConstructionScheduleItemDTO;
import com.viettel.construction.model.api.EmployeeApi;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.SysUserRequest;
import com.viettel.construction.screens.commons.SelectEmployeeCameraActivity;
import com.viettel.construction.screens.custom.dialog.CustomBottomSheetDialogFragment;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class ConstructionLevel1Fragment
        extends FragmentListBase<ConstructionScheduleItemDTO, ConstructionScheduleDTOResponse> implements CustomBottomSheetDialogFragment.BottomSheetDialogFragmentOnClick {

    private String scheduleType = "";
    private EmployeeApi employee;
    private SysUserRequest sysUser;
    private ConstructionScheduleDTO constructionScheduleDTO;
    private CustomBottomSheetDialogFragment bottomSheetDialogFragment;


    @Override
    public void initData() {
        super.initData();
        if (getArguments() != null) {
            constructionScheduleDTO = (ConstructionScheduleDTO) getArguments().getSerializable("CONSTRUCTION_MANAGEMENT_OBJ");
            scheduleType = getArguments().getString("type");
            if (getArguments().getSerializable("SYS_USER_1") != null) {
                sysUser = (SysUserRequest) getArguments().getSerializable("SYS_USER_1");
            }
            constructionScheduleDTO.setScheduleType(scheduleType);
        }
    }


    @Override
    public void onResume() {
        try {
            super.onResume();
            // load lại nếu như có công việc đang thực hiện chuyển sang tạm dừng
            if (App.getInstance().isNeedUpdate()) {
                loadData();
            }
            // load lại nếu có công việc tạo mới
            if (App.getInstance().isNeedUpdateAfterCreateNewWork()) {
                loadData();
            }
            // load lại nếu có công việc thay đổi sau khi xem detail
            if (App.getInstance().isNeedUpdateAfterSaveDetail()) {
                loadData();
            }
            // load lại nếu có công việc đang tạm dừng chuyển sang tiếp tục thực hiện
            if (App.getInstance().isNeedUpdateAfterContinue()) {
                loadData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_list_base;
    }

    @Override
    public AdapterFragmentListBase getAdapterInstance() {
        return new ConstructionLevel1Adapter(getContext(), listData);
    }

    @Override
    public AdapterExpandableListBase getAdapterExpandInstance() {
        return null;
    }

    @Override
    public List<ConstructionScheduleItemDTO> dataSearch(String keyWord) {
        String input = StringUtil.removeAccentStr(keyWord).toLowerCase().trim();
        List<ConstructionScheduleItemDTO> dataSearch = new ArrayList<>();
        if (!input.isEmpty()) {
            for (ConstructionScheduleItemDTO itemDto : listData) {
                String name, performer;
                if (itemDto.getName() != null) {
                    name = StringUtil.removeAccentStr(itemDto.getName()).toLowerCase();
                } else {
                    name = "";
                }
                if (itemDto.getSyuFullName() != null) {
                    performer = StringUtil.removeAccentStr(itemDto.getSyuFullName()).toLowerCase();
                } else {
                    performer = "";
                }
                if (name.contains(input) || performer.contains(input)) {
                    dataSearch.add(itemDto);
                }
            }
        }
        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, ConstructionScheduleItemDTO>> dataSearchExpandableList(String keyWord) {
        return null;
    }

    @Override
    public String getHeaderTitle() {
        String title = "Hạng mục (%)";
        if (constructionScheduleDTO != null) {
            title = constructionScheduleDTO.getConstructionCode() + "(%s)";
        }
        return title;
    }

    @Override
    public int getMenuID() {
        return R.menu.detail_construction_management_menu;
    }

    @Override
    public List<ConstructionScheduleItemDTO> menuItemClick(int menuItem) {
        List<ConstructionScheduleItemDTO> dataSearch = new ArrayList<>();
        switch (menuItem) {
            case R.id.all:
                dataSearch = listData;
                break;
            case R.id.did_not_perform:
                dataSearch = filterByStatus("1", false);
                break;
            case R.id.in_process:
                dataSearch = filterByStatus("2", false);
                break;
            case R.id.finished:
                dataSearch = filterByStatus("3", false);
                break;
            case R.id.low_process:
                dataSearch = filterByStatus("2", true);
                break;
            case R.id.on_schedule:
                dataSearch = filterByStatus("1", true);
                break;
            case R.id.in_Pending:
                dataSearch=  filterByStatus("4");
                break;
        }
        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, ConstructionScheduleItemDTO>> menuItemClickExpandableList(int menuItem) {
        return null;
    }

    private List<ConstructionScheduleItemDTO> filterByStatus(String status) {
        List<ConstructionScheduleItemDTO> dataSearch = Observable.from(listData).filter(x -> x.getStatus() != null
                && x.getStatus().equalsIgnoreCase(status)).toList().toBlocking().singleOrDefault(new ArrayList<>());

        return dataSearch;
    }

    /***
     * // 1 chưa thực hiện
     // 2 đang thực hiện
     // 3 Đã hoàn thành
     * @param status
     * @param isLowProcess
     */
    private List<ConstructionScheduleItemDTO> filterByStatus(String status, boolean isLowProcess) {
        List<ConstructionScheduleItemDTO> dataSearch = new ArrayList<>();
        for (ConstructionScheduleItemDTO itemDTO : listData) {
            if (isLowProcess) {
                String temp;
                if (itemDTO.getCompleteState() > 0) {
                    temp = itemDTO.getCompleteState() + "";
                } else {
                    temp = 0 + "";
                }
                if (temp.contains(status))
                    dataSearch.add(itemDTO);
            } else {
                Long temp1;
                if (itemDTO.getCompletePercent() != null) {
                    temp1 = itemDTO.getCompletePercent();
                } else {
                    temp1 = null;
                }
                if (status.equals("1") && temp1 != null && temp1 == 0L) {
                    dataSearch.add(itemDTO);
                } else if (status.equals("2") && temp1 != null && temp1 > 0L && temp1 < 100L) {
                    dataSearch.add(itemDTO);
                } else if (status.equals("3") && temp1 != null && temp1 == 100L) {
                    dataSearch.add(itemDTO);
                } else if (status.equals("1") && temp1 == null) {
                    dataSearch.add(itemDTO);
                }
            }
        }
        return dataSearch;
    }

    @Override
    public List<ConstructionScheduleItemDTO> getResponseData(ConstructionScheduleDTOResponse result) {
        List<ConstructionScheduleItemDTO> data = new ArrayList<>();
        ConstructionScheduleDTOResponse response = ConstructionScheduleDTOResponse.class.cast(result);
        if (response != null) {
            ResultInfo resultInfo = response.getResultInfo();
            if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                data = response.getListConstructionScheduleItemDTO();
            }
        }
        return data;
    }

    @Override
    public Object[] getParramLoading() {
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
        return new Object[]{author, constructionScheduleDTO};
    }

    @Override
    public APIType getAPIType() {
        return APIType.END_DETAIL_CONSTRUCTION;
    }

    @Override
    public Class<ConstructionScheduleDTOResponse> responseEntityClass() {
        return ConstructionScheduleDTOResponse.class;
    }

    @Override
    public void onItemRecyclerViewclick(ConstructionScheduleItemDTO item) {
//        Fragment frag = new ConstructionLevel2ChartFragment_rac();
        Fragment frag = new ConstructionLevel2Fragment();
        item.setConstructionCode(constructionScheduleDTO.getConstructionCode());
        ConstructionScheduleItemDTO constructionScheduleItemDTO = item;
        Bundle bundle = new Bundle();
        bundle.putString("type", scheduleType);
        bundle.putSerializable("CONSTRUCTION_MANAGEMENT_OBJ_2", constructionScheduleItemDTO);
        bundle.putSerializable("SYS_USER_2", sysUser);
        frag.setArguments(bundle);
        commitChange(frag, true);
    }

    @Override
    public void onItemRecyclerViewLongclick(ConstructionScheduleItemDTO item) {
        try {
            if (sysUser != null) {
                if (sysUser.getAuthorities().equals(VConstant.MANAGE_PLAN)
                        //                    && ((constructionList.get(pos).getIsInternal().trim().equals("1")) || (constructionList.get(pos).getIsInternal().trim().equals("2")))
                        && scheduleType.equals("2")) {
                    App.getInstance().setAuthor(VConstant.MANAGE_PLAN);
                    bottomSheetDialogFragment = new CustomBottomSheetDialogFragment();
                    bottomSheetDialogFragment.mOnClick = this;
                    bottomSheetDialogFragment.setSelectedItem(item);
                    bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), "BOTTOM_SHEET_CHANGE_PERSON");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickBottonSheetChange(Object selectedItem) {
        try {
            bottomSheetDialogFragment.dismiss();
            Intent intent = new Intent(getActivity(), SelectEmployeeCameraActivity.class);
            startActivityForResult(intent, 3003);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickBottomSheetCancel() {
        try {
            bottomSheetDialogFragment.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 3003 && data != null) {
                employee = (EmployeeApi) data.getSerializableExtra("employeeResult");
                handoverPerson(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handoverPerson(EmployeeApi employee) {
        if (employee != null) {
            progressBar.setLoading(true);
            SysUserRequest sysUserRequest = new SysUserRequest();
            String author = "";
            if (sysUser != null) {
                if (!sysUser.getAuthorities().isEmpty()) {
                    author = sysUser.getAuthorities();
                } else {
                    author = "";
                }
            } else {
                author = "";
            }
            sysUserRequest.setSysUserId(Long.parseLong(employee.getSysUserId()));

            ConstructionScheduleItemDTO dto =
                    (ConstructionScheduleItemDTO) bottomSheetDialogFragment.getSelectedItem();

            ApiManager.getInstance().requestHandoverPersonCategory(author, sysUserRequest, dto,
                    ConstructionScheduleDTOResponse.class, new IOnRequestListener() {
                        @Override
                        public <T> void onResponse(T result) {
                            try {
                                ConstructionScheduleDTOResponse Response = ConstructionScheduleDTOResponse.class.cast(result);
                                ResultInfo resultInfo = Response.getResultInfo();
                                if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                                    progressBar.setLoading(false);
                                    App.getInstance().setAuthor(null);
                                    Toast.makeText(getActivity(), resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                                    loadData();

                                } else {
                                    progressBar.setLoading(false);
                                    Toast.makeText(getActivity(), resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(int statusCode) {
                            try {
                                progressBar.setLoading(false);
                                Toast.makeText(getActivity(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                            } catch (Resources.NotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

}
