package com.viettel.construction.screens.wrac;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.screens.atemp.adapter.AdapterHeaderRV;
import com.viettel.construction.screens.commons.DetailCV2CompleteCameraActivity;
import com.viettel.construction.screens.commons.DetailCVCompleteCameraActivity;
import com.viettel.construction.screens.commons.DetailInProcessCameraActivity;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.common.FilterableSection;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionScheduleItemDTO;
import com.viettel.construction.model.api.ConstructionTaskDTO;
import com.viettel.construction.model.api.ListConstructionTaskAll;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.SysUserRequest;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.commons.DetailCV2CameraActivity;
import com.viettel.construction.screens.custom.dialog.CustomProgress;
import com.viettel.construction.appbase.BaseChartFragment;
import com.viettel.construction.screens.commons.ChooseWorkChartFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/***
 * Menu Quản lý công trình , màn hình 3
 */
public class ConstructionLevel2ChartFragment_rac extends BaseChartFragment implements ManageWorkAdapter.OnClickViewDetails, AdapterHeaderRV.OnClickHeader {

    private ManageWorkAdapter adapter;
    private List<ConstructionTaskDTO> mListConstruction = new ArrayList<>();
    private List<ConstructionTaskDTO> mListBackUpForSearch = new ArrayList<>();

    private List<ConstructionTaskDTO> mListAllConstruction = new ArrayList<>();
    private List<ConstructionTaskDTO> mListConstructionDidNotPerform = new ArrayList<>();
    private List<ConstructionTaskDTO> mListConstructionInProcess = new ArrayList<>();
    private List<ConstructionTaskDTO> mListConstructionOnPause = new ArrayList<>();
    private List<ConstructionTaskDTO> mListConstructionComplete = new ArrayList<>();

    private List<ConstructionTaskDTO> mListConstructionTemp = new ArrayList<>();

    private List<String> mListConstructionName = new ArrayList<>();
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.rcvData)
    RecyclerView rcvWork;
    @BindView(R.id.imgFilter)
    ImageView imgFilter;
    @BindView(R.id.txtHeader)
    TextView txtTitle;


    @BindView(R.id.edtSearch)
    EditText edtSearchWork;

    @BindView(R.id.txtNoData)
    TextView txtNoData;

    @BindView(R.id.imgClearTextSearch)
    View imgClearTextSearch;

    private SysUserRequest sysUser;
    private int mKey = VConstant.TOTAL;
    private boolean mIsHeaderView;
    private ConstructionScheduleItemDTO constructionScheduleItemDTO;
    private String scheduleType = "";
    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;
    private View view;
    @BindView(R.id.progressBar)
    CustomProgress customProgress;


    @Override
    public void onResume() {
        super.onResume();
        // load lại nếu như có công việc đang thực hiện chuyển sang tạm dừng
        if (App.getInstance().isNeedUpdate()) {
            mKey = VConstant.ON_PAUSE;
            App.getInstance().setNeedUpdate(false);
            initData();
        }
        // load lại nếu có công việc tạo mới
        if (App.getInstance().isNeedUpdateAfterCreateNewWork()) {
            edtSearchWork.setText("");
            App.getInstance().setNeedUpdateAfterCreateNewWork(false);
            initData();
        }
        // load lại nếu có công việc thay đổi sau khi xem detail
        if (App.getInstance().isNeedUpdateAfterSaveDetail()) {
            edtSearchWork.setText("");
            App.getInstance().setNeedUpdateAfterSaveDetail(false);
            initData();
        }
        // load lại nếu có công việc đang tạm dừng chuyển sang tiếp tục thực hiện
        if (App.getInstance().isNeedUpdateAfterContinue()) {
            mKey = VConstant.TOTAL;
            App.getInstance().setNeedUpdateAfterContinue(false);
            initData();
        }
    }

    private void initData() {
        mIsHeaderView = false;
        if (getArguments() != null) {
            if (mKey == VConstant.TOTAL) {
//                txtTitle.setText(getString(R.string.work));
            }
        }
        // setup adapter for normal recyclerview
        adapter = new ManageWorkAdapter(scheduleType, constructionScheduleItemDTO, getActivity(), mListConstruction, this, false);
        rcvWork.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvWork.setAdapter(adapter);
        listenInputEdt();
        loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_construction_child_list, container, false);
        ButterKnife.bind(this, view);
        edtSearchWork.setText("");
        if (getArguments() != null) {
            constructionScheduleItemDTO = (ConstructionScheduleItemDTO) getArguments().getSerializable("CONSTRUCTION_MANAGEMENT_OBJ_2");
            mKey = getArguments().getInt(VConstant.BUNDLE_KEY_FILTER);
            scheduleType = getArguments().getString("type");
            if (getArguments().getSerializable("SYS_USER_2") != null) {
                sysUser = (SysUserRequest) getArguments().getSerializable("SYS_USER_2");
            }
            Log.e("Key1", mKey + "");
            txtTitle.setText(constructionScheduleItemDTO.getName());
        }
        initData();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void listenInputEdt() {
        edtSearchWork.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        edtSearchWork.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Send the user message
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtSearchWork.getWindowToken(), 0);
                    handled = true;
                }
                return handled;
            }
        });
        edtSearchWork.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // check nếu chưa có dữ liệu trả về thì search sẽ crash
                if (mListBackUpForSearch.size() > 0) {
                    if (editable.toString().length() > 0 &&
                            imgClearTextSearch.getVisibility() == View.INVISIBLE)
                        imgClearTextSearch.setVisibility(View.VISIBLE);
                    // check hiển thị kiểu thường và kiểu list với header
                    if (!mIsHeaderView) {
                        String temp = StringUtil.removeAccentStr(editable.toString() + "").toUpperCase().trim();
                        mListConstruction.clear();
                        if (temp.length() != 0) {
                            for (ConstructionTaskDTO entity : mListBackUpForSearch) {
                                String taskName = StringUtil.removeAccentStr(entity.getTaskName()).toUpperCase();
                                String constructionCode;
                                if (entity.getConstructionCode() != null) {
                                    constructionCode = entity.getConstructionCode().toString().toUpperCase();
                                } else {
                                    constructionCode = "";
                                }
                                if (taskName.contains(temp) || constructionCode.contains(temp)) {
                                    mListConstruction.add(entity);
                                }
                            }
                        } else {
                            mListConstruction.addAll(mListBackUpForSearch);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        for (Section section : mSectionedRecyclerViewAdapter.getSectionsMap().values()) {
                            if (section instanceof FilterableSection) {
                                ((FilterableSection) section).filter(editable.toString() + "");
                            }
                        }
                        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
                    }
                    //
                    if (mListConstruction == null ||
                            (mListConstruction != null &&
                                    mListConstruction.size() == 0)) {
                        txtNoData.setVisibility(View.VISIBLE);
                    } else
                        txtNoData.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @OnClick(R.id.imgClearTextSearch)
    public void clearTextSearch() {
        imgClearTextSearch.setVisibility(View.INVISIBLE);
        edtSearchWork.setText("");
    }

    private void fakeData() {
        mListAllConstruction.clear();
        // truyền dữ liệu vào các list tạm để phục vụ công việc phân loại theo các tiêu chí
        mListConstructionDidNotPerform.clear();
        mListConstructionDidNotPerform.addAll(filterList(VConstant.DID_NOT_PERFORM));
        mListConstructionInProcess.clear();
        mListConstructionInProcess.addAll(filterList(VConstant.IN_PROCESS));
        mListConstructionOnPause.clear();
        mListConstructionOnPause.addAll(filterList(VConstant.ON_PAUSE));
        mListConstructionComplete.clear();
        mListConstructionComplete.addAll(filterList(VConstant.COMPLETE));
        controlListCV();
    }

    // dựa vào key từ fragment dashboard CV để phân loại list theo status
    private void controlListCV() {
        Activity activity = getActivity();
        switch (mKey) {
            case VConstant.TOTAL:
                if (activity != null && isAdded()) {
                    showHideButtonCreateNewWork();
                    addDataForMainList(mListAllConstruction);
//                    txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, mListConstruction.size() + ""));
                }
                break;
            case VConstant.DID_NOT_PERFORM:
                if (activity != null && isAdded()) {
                    showHideButtonCreateNewWork();
                    addDataForMainList(mListConstructionDidNotPerform);
                    mListConstructionTemp.clear();
                    mListConstructionTemp.addAll(mListConstructionDidNotPerform);
//                    txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, mListConstruction.size() + ""));
                }
                break;
            case VConstant.IN_PROCESS:
                if (activity != null && isAdded()) {
                    showHideButtonCreateNewWork();
                    addDataForMainList(mListConstructionInProcess);
                    mListConstructionTemp.clear();
                    mListConstructionTemp.addAll(mListConstructionInProcess);
//                    txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, mListConstruction.size() + ""));
                }
                break;
            case VConstant.ON_PAUSE:
                if (activity != null && isAdded()) {
                    showHideButtonCreateNewWork();
                    addDataForMainList(mListConstructionOnPause);
                    mListConstructionTemp.clear();
                    mListConstructionTemp.addAll(mListConstructionOnPause);
//                    txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, mListConstruction.size() + ""));
                }
                break;
            case VConstant.COMPLETE:
                if (activity != null && isAdded()) {
                    showHideButtonCreateNewWork();
                    addDataForMainList(mListConstructionComplete);
                    mListConstructionTemp.clear();
                    mListConstructionTemp.addAll(mListConstructionComplete);
//                    txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, mListConstruction.size() + ""));
                }
                break;
            default:
                if (activity != null && isAdded()) {
//                    btnCreateNewWork.setVisibility(View.VISIBLE);
                    addDataForMainList(mListAllConstruction);
//                    txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, mListConstruction.size() + ""));
                }
                break;
        }
    }

    // thêm data vào list chính để hiển thị dựa theo trạng thái của công việc
    private void addDataForMainList(List<ConstructionTaskDTO> list) {
        mListConstruction.clear();
        mListConstruction.addAll(list);
        mListBackUpForSearch.clear();
        mListBackUpForSearch.addAll(list);
        adapter.notifyDataSetChanged();
    }


    private void loadData() {
        customProgress.setLoading(true);
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
        ApiManager.getInstance().getListScheduleWorkItem(author, constructionScheduleItemDTO, ListConstructionTaskAll.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ListConstructionTaskAll listConstructionTaskAll = ListConstructionTaskAll.class.cast(result);
                    ResultInfo resultInfo = listConstructionTaskAll.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK) &&
                            listConstructionTaskAll.getListConstructionScheduleWorkItemDTO() != null) {
                        List<ConstructionTaskDTO> list = listConstructionTaskAll.getListConstructionScheduleWorkItemDTO();
                        if (list != null) {
                            // truyền dữ liệu vào các list tạm để phục vụ công việc phân loại theo các tiêu chí
                            mListAllConstruction.clear();
                            mListConstructionDidNotPerform.clear();
                            mListConstructionInProcess.clear();
                            mListConstructionOnPause.clear();
                            mListConstructionComplete.clear();
                            mListAllConstruction.addAll(list);
                            mListConstructionDidNotPerform.addAll(filterList(VConstant.DID_NOT_PERFORM));
                            mListConstructionInProcess.addAll(filterList(VConstant.IN_PROCESS));
                            mListConstructionOnPause.addAll(filterList(VConstant.ON_PAUSE));
                            mListConstructionComplete.addAll(filterList(VConstant.COMPLETE));
                            controlListCV();
                            edtSearchWork.setText(edtSearchWork.getText() + "");
                            edtSearchWork.setSelection(edtSearchWork.getText().length());
                            txtTitle.setText(constructionScheduleItemDTO.getName() + "(" + list.size() + ")");
                        } else {
                            Toast.makeText(getActivity(), resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        customProgress.setLoading(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                customProgress.setLoading(false);

            }
        });
    }


    // Lọc list theo status
    private List<ConstructionTaskDTO> filterList(int a) {
        List<ConstructionTaskDTO> list = new ArrayList<>();
        for (int i = 0; i < mListAllConstruction.size(); i++) {
            if (mListAllConstruction.get(i).getStatus().contains(a + "")) {
                list.add(mListAllConstruction.get(i));
            }
        }
        return list;
    }

    // Lọc list theo chậm tiến độ và đúng tiến độ
    private void filterListByStatus(String s, List<ConstructionTaskDTO> list) {
        mListConstruction.clear();
        mListBackUpForSearch.clear();
        for (ConstructionTaskDTO entity : list) {
            if (entity.getCompleteState() != null) {
                if (entity.getCompleteState().trim().equals(s)) {
                    mListConstruction.add(entity);
                }
            }
        }
        mListBackUpForSearch.addAll(mListConstruction);
        adapter.notifyDataSetChanged();

        //
        if (mListConstruction == null ||
                (mListConstruction != null &&
                        mListConstruction.size() == 0)) {
            txtNoData.setVisibility(View.VISIBLE);
        } else
            txtNoData.setVisibility(View.INVISIBLE);
    }

    // Lọc list theo tên công trình
    private List<ConstructionTaskDTO> filterListByConstructionName(String s, List<ConstructionTaskDTO> list) {
        List<ConstructionTaskDTO> resultList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getConstructionCode() != null && list.get(i).getConstructionCode().contains(s)) {
                resultList.add(list.get(i));
            }
        }
        return resultList;
    }

    private void selectItem(List<ConstructionTaskDTO> list) {
        mSectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
        // truyền vào list các construction name để làm header cho phần hiển thị theo tên công trình
        mListConstructionName.clear();
        for (ConstructionTaskDTO work : list) {
            if (work.getConstructionCode() != null && !mListConstructionName.contains(work.getConstructionCode())) {
                mListConstructionName.add(work.getConstructionCode());
            }
        }
        // truyền vào các list construction theo từng tên công trình
        for (int i = 0; i < mListConstructionName.size(); i++) {
            List<ConstructionTaskDTO> temp = filterListByConstructionName(mListConstructionName.get(i), list);
            if (temp.size() > 0) {
                mSectionedRecyclerViewAdapter.addSection(new AdapterHeaderRV(this, mListConstructionName.get(i), temp, getActivity(), false));
            }
        }
        rcvWork.setAdapter(mSectionedRecyclerViewAdapter);
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
        rcvWork.scrollToPosition(0);
    }


    @OnClick(R.id.imgFilter)
    public void onClickFilter() {
        PopupMenu popup = new PopupMenu(getActivity(), imgFilter);
        //Inflating the Popup using xml file
        if (mKey == VConstant.TOTAL) {
            popupMenuForTotalConstruction(popup);
        } else {
            popupMenuForTheRest(popup);
        }
    }

    // popup menu dành cho tất cả các công việc
    private void popupMenuForTotalConstruction(PopupMenu popup) {
        popup.getMenuInflater()
                .inflate(R.menu.dashboard_cv_menu_filter, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.all:
                        rcvWork.setAdapter(adapter);
                        if (mListAllConstruction.size() > 0) {
                            edtSearchWork.setText("");
                            showHideButtonCreateNewWork();
                            addDataForMainList(mListAllConstruction);
//                            txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, mListConstruction.size() + ""));
                            rcvWork.scrollToPosition(0);
                            mIsHeaderView = false;
                        } else {
                            mListConstruction.clear();
                            mListBackUpForSearch.clear();
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case R.id.low_process:
                        rcvWork.setAdapter(adapter);
                        if (mListAllConstruction.size() > 0) {
                            edtSearchWork.setText("");
                            showHideButtonCreateNewWork();
                            filterListByStatus("2", mListAllConstruction);
//                            txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, mListConstruction.size() + ""));
                            rcvWork.scrollToPosition(0);
                            mIsHeaderView = false;
                        } else {
                            mListConstruction.clear();
                            mListBackUpForSearch.clear();
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case R.id.on_schedule:
                        rcvWork.setAdapter(adapter);
                        if (mListAllConstruction.size() > 0) {
                            edtSearchWork.setText("");
                            showHideButtonCreateNewWork();
                            filterListByStatus("1", mListAllConstruction);
//                            txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, mListConstruction.size() + ""));
                            rcvWork.scrollToPosition(0);
                            mIsHeaderView = false;
                        } else {
                            mListConstruction.clear();
                            mListBackUpForSearch.clear();
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case R.id.construction:
                        if (mListAllConstruction.size() > 0) {
                            edtSearchWork.setText("");
//                            btnCreateNewWork.setVisibility(View.GONE);
                            selectItem(mListAllConstruction);
//                            txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, mListConstruction.size() + ""));
                            mIsHeaderView = true;
                        } else {
                            mListConstruction.clear();
                            mListBackUpForSearch.clear();
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case R.id.did_not_perform:
                        rcvWork.setAdapter(adapter);
                        if (mListConstructionDidNotPerform.size() > 0) {
                            edtSearchWork.setText("");
                            showHideButtonCreateNewWork();
                            addDataForMainList(mListConstructionDidNotPerform);
//                            txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, mListConstruction.size() + ""));
                            mIsHeaderView = false;
                        } else {
                            mListConstruction.clear();
                            mListBackUpForSearch.clear();
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case R.id.in_process:
                        rcvWork.setAdapter(adapter);
                        if (mListConstructionInProcess.size() > 0) {
                            edtSearchWork.setText("");
                            showHideButtonCreateNewWork();
                            addDataForMainList(mListConstructionInProcess);
//                            txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, mListConstruction.size() + ""));
                            mIsHeaderView = false;
                        } else {
                            mListConstruction.clear();
                            mListBackUpForSearch.clear();
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case R.id.on_pause:
                        rcvWork.setAdapter(adapter);
                        if (mListConstructionOnPause.size() > 0) {
                            edtSearchWork.setText("");
                            showHideButtonCreateNewWork();
                            addDataForMainList(mListConstructionOnPause);
//                            txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, mListConstruction.size() + ""));
                            mIsHeaderView = false;
                        } else {
                            mListConstruction.clear();
                            mListBackUpForSearch.clear();
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case R.id.complete:
                        rcvWork.setAdapter(adapter);
                        if (mListConstructionComplete.size() > 0) {
                            edtSearchWork.setText("");
                            showHideButtonCreateNewWork();
                            addDataForMainList(mListConstructionComplete);
//                            txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, mListConstruction.size() + ""));
                            mIsHeaderView = false;
                        } else {
                            mListConstruction.clear();
                            mListBackUpForSearch.clear();
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    default:
                        break;
                }
                Toast.makeText(getActivity(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        popup.show();
    }


    // popup menu dành cho các thuộc tính còn lại
    private void popupMenuForTheRest(PopupMenu popup) {
        popup.getMenuInflater()
                .inflate(R.menu.dashboard_cv_filter_menu_1, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.all:
                        if (mListConstructionTemp.size() > 0) {
                            edtSearchWork.setText("");
                            showHideButtonCreateNewWork();
                            rcvWork.setAdapter(adapter);
                            addDataForMainList(mListConstructionTemp);
//                            txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, mListConstruction.size() + ""));
                            rcvWork.scrollToPosition(0);
                            mIsHeaderView = false;
                        } else {
//                            txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, "0"));
                        }

                        break;
                    case R.id.low_process:
                        if (mListConstructionTemp.size() > 0) {
                            edtSearchWork.setText("");
                            showHideButtonCreateNewWork();
                            rcvWork.setAdapter(adapter);
                            filterListByStatus("2", mListConstructionTemp);
//                            txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, mListConstruction.size() + ""));
                            rcvWork.scrollToPosition(0);
                            mIsHeaderView = false;
                        } else {
//                            txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, "0"));
                        }

                        break;
                    case R.id.on_schedule:
                        if (mListConstructionTemp.size() > 0) {
                            edtSearchWork.setText("");
                            showHideButtonCreateNewWork();
                            rcvWork.setAdapter(adapter);
                            filterListByStatus("1", mListConstructionTemp);
//                            txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, mListConstruction.size() + ""));
                            rcvWork.scrollToPosition(0);
                            mIsHeaderView = false;
                        } else {
//                            txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, "0"));
                        }

                        break;
                    case R.id.construction:
                        if (mListConstructionTemp.size() > 0) {
                            edtSearchWork.setText("");
//                            btnCreateNewWork.setVisibility(View.GONE);
                            selectItem(mListConstructionTemp);
//                            txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, mListConstruction.size() + ""));
                            mIsHeaderView = true;
                        } else {
//                            txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, "0"));
                        }

                        break;
                    default:
                        break;
                }
                Toast.makeText(getActivity(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        popup.show();
    }

    private void showHideButtonCreateNewWork() {
        if (mKey == VConstant.TOTAL) {
//            btnCreateNewWork.setVisibility(View.VISIBLE);
        } else {
//            btnCreateNewWork.setVisibility(View.GONE);
        }
    }

    @Override
    public void OnClickItem(int pos) {
//        App.getInstance().setNeedUpdate(false);

        ConstructionTaskDTO work = mListConstruction.get(pos);
        SysUserRequest sysUserRequest = (SysUserRequest) getArguments().getSerializable("SYS_USER_2");
        Intent intent;

        if (work.getQuantityByDate() != null) {
            if (work.getQuantityByDate().equals("1")) {
                if (sysUserRequest != null) {
                    if (sysUserRequest.getAuthorities().equals(VConstant.MANAGE_PLAN)) {
                        if (scheduleType.equals("2")) {
                            intent = new Intent(getActivity(), DetailCV2CompleteCameraActivity.class);
                            intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                            startActivity(intent);
                        } else {
                            if (work.getStatus().equals(String.valueOf(VConstant.COMPLETE))) {
                                intent = new Intent(getActivity(), DetailCV2CompleteCameraActivity.class);
                                intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                                startActivity(intent);
                            } else {
                                intent = new Intent(getActivity(), DetailCV2CameraActivity.class);
                                intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                                startActivity(intent);
                            }
                        }
                    }
                } else {
                    if (work.getStatus().equals(String.valueOf(VConstant.COMPLETE))) {
                        intent = new Intent(getActivity(), DetailCV2CompleteCameraActivity.class);
                        intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                        startActivity(intent);
                    } else {
                        intent = new Intent(getActivity(), DetailCV2CameraActivity.class);
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
                        intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                        startActivity(intent);
                    }
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
                    intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public void OnClickHeader(ConstructionTaskDTO work) {
        Intent intent;
        if (work.getStatus().equals(String.valueOf(VConstant.COMPLETE))) {
            intent = new Intent(getActivity(), DetailCVCompleteCameraActivity.class);
            intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
            startActivity(intent);
        } else {
            intent = new Intent(getActivity(), DetailInProcessCameraActivity.class);
            intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, work);
            startActivity(intent);
        }
    }

    @OnClick(R.id.btnAddItem)
    public void onClickCreate() {
        App.getInstance().setAuthor(null);
        commitChange(new ChooseWorkChartFragment(), true);

//        Fragment frag = new ChooseWorkChartFragment();
//        FragmentManager manager = getActivity().getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.contentPanel, frag, null);
//        transaction.addToBackStack(null);
//        transaction.commit();
    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        getFragmentManager().popBackStack();
    }

}
