package com.viettel.construction.screens.menu_construction;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.appbase.FragmentListBase;
import com.viettel.construction.common.App;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionScheduleDTO;
import com.viettel.construction.model.api.ConstructionScheduleDTOResponse;
import com.viettel.construction.server.api.APIType;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;
import java.util.List;


public class TabPageConstructionItemFragment extends
        FragmentListBase<ConstructionScheduleDTO, ConstructionScheduleDTOResponse> {


    private String scheduleType = "0";
    private boolean isMonthPlan = false;
    private int loadType = 0;

    private String title = "";

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void onResume() {
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
    }

    public void setLoadType(int loadType) {
        this.loadType = loadType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public void setMonthPlan(boolean monthPlan) {
        isMonthPlan = monthPlan;
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_list_base;
    }

    @Override
    public AdapterFragmentListBase getAdapterInstance() {
        return new TabConstructionAdapter(getContext(), listData);
    }

    @Override
    public AdapterExpandableListBase getAdapterExpandInstance() {
        return null;
    }

    @Override
    public List<ConstructionScheduleDTO> dataSearch(String keyWord) {
        List<ConstructionScheduleDTO> dataSearch = new ArrayList<>();
        keyWord = StringUtil.removeAccentStr(keyWord.toString().trim()).toLowerCase();
        for (ConstructionScheduleDTO dto : listData
        ) {
            String code = "", name = "";
            if (dto.getConstructionCode() != null) {
                code = dto.getConstructionCode().toLowerCase();
            }
            if (dto.getConstructionName() != null) {
                name = StringUtil.removeAccentStr(dto.getConstructionName()).toLowerCase();
            }
            if (code.contains(keyWord) || name.contains(keyWord))
                dataSearch.add(dto);
        }
        return dataSearch;
    }

    @Override
    public List<ExpandableListModel<String, ConstructionScheduleDTO>> dataSearchExpandableList(String keyWord) {
        return null;
    }

    @Override
    public String getHeaderTitle() {
        return title;
    }

    @Override
    public int getMenuID() {
        return R.menu.construction_management_menu;
    }

    @Override
    public List<ConstructionScheduleDTO> menuItemClick(int menuItem) {
        List<ConstructionScheduleDTO> data;
        switch (menuItem) {
            default://case R.id.all:
                data = listData;
                break;
            case R.id.have_not_finish:
                data = filterByStatus("", false);
                break;
            case R.id.finished:
                data = filterByStatus("0", false);
                break;
            case R.id.construction_with_problem:
                data = filterByStatus("4", false);
                break;

        }
        return data;
    }

    @Override
    public List<ExpandableListModel<String, ConstructionScheduleDTO>> menuItemClickExpandableList(int menuItem) {
        return null;
    }

    @Override
    public List<ConstructionScheduleDTO> getResponseData(ConstructionScheduleDTOResponse result) {
        if (result.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
            if (result.getSysUser() != null) {
                sysUser = result.getSysUser();
            }
            switch (loadType) {
                case 0:
                    if (result.getListConstructionScheduleRealizationDTO() != null)
                        return result.getListConstructionScheduleRealizationDTO();
                case 1:
                    if (result.getListConstructionSchedulePartnerDTO() != null)
                        return result.getListConstructionSchedulePartnerDTO();
                case 2:
                    if (result.getListConstructionScheduleDirectorByDTO() != null)
                        return result.getListConstructionScheduleDirectorByDTO();
            }

        }
        return new ArrayList<>();
    }


    private List<ConstructionScheduleDTO> filterByStatus(String status, boolean isByMonth) {
        List<ConstructionScheduleDTO> dataSearch = new ArrayList<>();
        for (ConstructionScheduleDTO dto : listData) {
            String temp, byMonth;
            if (isByMonth) {
                if (dto.getStatus() != null) {
                    byMonth = dto.getStatus();
                } else {
                    byMonth = "";
                }
                if (byMonth.contains(status)) {
                    dataSearch.add(dto);
                }
            } else {
                //filter stoped
                if (status.equals("4")) {
                    if (dto.getStatus() != null) {
                        temp = dto.getStatus();
                    } else {
                        temp = "";
                    }
                    if (temp.contains(status)) {
                        dataSearch.add(dto);
                    }
                    //filter complete
                } else if (status.equals("0")) {
                    if (dto.getUnCompletedTask() != null) {
                        temp = dto.getUnCompletedTask();
                    } else {
                        temp = "";
                    }
                    if (temp.contains(status)) {
                        dataSearch.add(dto);
                    }
                    //filter uncomplete
                } else {
                    if (dto.getUnCompletedTask() != null) {
                        temp = dto.getUnCompletedTask();
                    } else {
                        temp = "";
                    }
                    if (!temp.contains("0") && !temp.contains("4")) {
                        dataSearch.add(dto);
                    }
                }
            }

        }
        return dataSearch;
    }


    @Override
    public Object[] getParramLoading() {
        return new Object[]{loadType};
    }

    @Override
    public APIType getAPIType() {
        return APIType.END_URL_GET_CONSTRUCTION_MANAGEMENT;
    }

    @Override
    public Class<ConstructionScheduleDTOResponse> responseEntityClass() {
        return ConstructionScheduleDTOResponse.class;
    }


    @Override
    public void onItemRecyclerViewclick(ConstructionScheduleDTO item) {
        try {
            Fragment frag = new ConstructionLevel1Fragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("CONSTRUCTION_MANAGEMENT_OBJ", item);
            bundle.putSerializable("SYS_USER_1", sysUser);
            bundle.putString("type", scheduleType);
            bundle.putBoolean("IS_THE_MONTH_PLAN", isMonthPlan);
            frag.setArguments(bundle);
            commitChange(frag, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemRecyclerViewLongclick(ConstructionScheduleDTO item) {
        //Do nothing
    }


}
