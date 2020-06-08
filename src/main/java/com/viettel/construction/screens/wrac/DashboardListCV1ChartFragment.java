package com.viettel.construction.screens.wrac;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.common.App;
import com.viettel.construction.common.FilterableSection;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionTaskDTO;
import com.viettel.construction.model.api.ListConstructionTaskAll;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.screens.atemp.adapter.AdapterHeaderRV;
import com.viettel.construction.screens.atemp.adapter.WorkAdapter;
import com.viettel.construction.screens.commons.ChooseWorkChartFragment;
import com.viettel.construction.appbase.BaseChartFragment;
import com.viettel.construction.screens.commons.DetailInProcessCameraActivity;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.commons.DetailCV2CameraActivity;
import com.viettel.construction.screens.commons.DetailCVCompleteCameraActivity;

import com.viettel.construction.screens.custom.dialog.CustomProgress;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import rx.Observable;

/**
 * Chi tiết của Dashboard ở Tab thực hiện
 */
public class DashboardListCV1ChartFragment extends BaseChartFragment implements WorkAdapter.OnClickViewDetails, AdapterHeaderRV.OnClickHeader {
    private WorkAdapter adapter;
    private List<ConstructionTaskDTO> listData = new ArrayList<>();


    private List<String> mListConstructionName = new ArrayList<>();

    @BindView(R.id.rcvData)
    RecyclerView rcvWork;
    @BindView(R.id.imgFilter)
    ImageView imgFilter;
    @BindView(R.id.txtHeader)
    TextView txtTitle;
    @BindView(R.id.btnAddItem)
    FloatingActionButton btnCreateNewWork;
    @BindView(R.id.edtSearch)
    EditText edtSearchWork;
    private int mKey = VConstant.TOTAL;

    @BindView(R.id.progressBar)
    CustomProgress customProgress;
    @BindView(R.id.txtNoData)
    TextView txtNoData;

    @BindView(R.id.imgClearTextSearch)
    View imgClearTextSearch;

    private boolean mIsHeaderView;

    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;
    private View view;

    private boolean allowSearch = true;


    @Override
    public void onResume() {
        super.onResume();
        // load lại nếu như có công việc đang thực hiện chuyển sang tạm dừng
        if (App.getInstance().isNeedUpdate()) {
//            mKey = VConstant.ON_PAUSE;
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
    }

    @OnClick(R.id.imgClearTextSearch)
    public void clearTextSearch() {
        imgClearTextSearch.setVisibility(View.INVISIBLE);
        edtSearchWork.setText("");
    }

    private void initData() {
        mIsHeaderView = false;
        if (getArguments() != null) {
            if (mKey == VConstant.TOTAL) {
                txtTitle.setText(getString(R.string.work));
            }
        }
        // setup adapter for normal recyclerview
        adapter = new WorkAdapter(getActivity(), listData, this, false);
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
        txtTitle.setText("Công việc");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mKey = getArguments().getInt(VConstant.BUNDLE_KEY_FILTER);
            Log.e("Key1", mKey + "");
        }
        initData();
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
                try {
                    if (editable.length() > 0)
                        imgClearTextSearch.setVisibility(View.VISIBLE);
                    if (allowSearch) {
                        // check hiển thị kiểu thường và kiểu list với header
                        if (!mIsHeaderView) {
                            if (editable.toString().trim().length() > 0) {
                                String temp = StringUtil.removeAccentStr(editable.toString() + "").toUpperCase().trim();
                                List<ConstructionTaskDTO> dataSearch = new ArrayList<>();
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
                                adapter.setListData(dataSearch);
                            } else
                                adapter.setListData(listData);
                            adapter.notifyDataSetChanged();
                        } else {
                            for (Section section : mSectionedRecyclerViewAdapter.getSectionsMap().values()) {
                                if (section instanceof FilterableSection) {
                                    ((FilterableSection) section).filter(editable.toString() + "");
                                }
                            }
                            mSectionedRecyclerViewAdapter.notifyDataSetChanged();

                            txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv,
                                    mSectionedRecyclerViewAdapter.getItemCount() + ""));
                        }
                        changeHeader();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void cleartSearch() {
        imgClearTextSearch.setVisibility(View.INVISIBLE);
        allowSearch = false;
        edtSearchWork.setText("");
        allowSearch = true;
    }


    // dựa vào key từ fragment dashboard CV để phân loại list theo status
    private void controlListCV(List<ConstructionTaskDTO> data) {
        Activity activity = getActivity();
        switch (mKey) {
            default:
                if (activity != null && isAdded()) {
                    showHideButtonCreateNewWork();
                    listData = data;
                    txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv,
                            adapter.getItemCount() + ""));
                }
                break;
            case VConstant.DID_NOT_PERFORM://Chua thuc hien
                if (activity != null && isAdded()) {
                    showHideButtonCreateNewWork();
                    listData = getData(VConstant.DID_NOT_PERFORM, data);
                }
                break;
            case VConstant.IN_PROCESS://Dang thuc hien
                if (activity != null && isAdded()) {
                    showHideButtonCreateNewWork();
                    listData = getData(VConstant.IN_PROCESS, data);
                }
                break;
            case VConstant.ON_PAUSE://Tam dung
                if (activity != null && isAdded()) {
                    showHideButtonCreateNewWork();
                    listData = getData(VConstant.ON_PAUSE, data);
                }
                break;
            case VConstant.COMPLETE://Da hoan thanh
                if (activity != null && isAdded()) {
                    showHideButtonCreateNewWork();
                    listData = getData(VConstant.COMPLETE, data);
                }
                break;
        }
    }

    private List<ConstructionTaskDTO> getData(int status, List<ConstructionTaskDTO> data) {

        List<ConstructionTaskDTO> dataSearch =
                Observable.from(data).filter(item -> item.getStatus() != null && item.getStatus().equals(String.valueOf(status)))
                        .toList().toBlocking().singleOrDefault(new ArrayList<>());
        return dataSearch;
    }

    // thêm data vào list chính để hiển thị dựa theo trạng thái của công việc
    private void filterPopupData(int status) {

        List<ConstructionTaskDTO> dataSearch =
                Observable.from(listData).filter(item -> item.getStatus().equals(String.valueOf(status)))
                        .toList().toBlocking().singleOrDefault(new ArrayList<>());
        adapter.setListData(dataSearch);
        if (mIsHeaderView)
            rcvWork.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        rcvWork.scrollToPosition(0);
        changeHeader();
    }


    private void loadData() {
        customProgress.setLoading(true);
        ApiManager.getInstance().getListPerformWork(ListConstructionTaskAll.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ListConstructionTaskAll listConstructionTaskAll = ListConstructionTaskAll.class.cast(result);
                    ResultInfo resultInfo = listConstructionTaskAll.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK) && listConstructionTaskAll.getLstConstructionTaskDTO() != null) {
                        List<ConstructionTaskDTO> list = listConstructionTaskAll.getLstConstructionTaskDTO();
                        if (list != null) {
                            controlListCV(list);
                            adapter.setListData(listData);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    changeHeader();
                    customProgress.setLoading(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                //
                changeHeader();
                customProgress.setLoading(false);
            }
        });
    }

    private void changeHeader() {
        txtTitle.setText(getString(R.string.title_dashboard_list_cv,
                adapter.getItemCount() + ""));
        if (adapter.getItemCount() == 0) {
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
                mSectionedRecyclerViewAdapter.addSection(
                        new AdapterHeaderRV(this, mListConstructionName.get(i), temp, getActivity(), false));
            }
        }
        rcvWork.setAdapter(mSectionedRecyclerViewAdapter);
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
        rcvWork.scrollToPosition(0);
    }


    @OnClick(R.id.imgFilter)
    public void onClickFilter() {
        try {
            PopupMenu popup = new PopupMenu(getActivity(), imgFilter);
            //Inflating the Popup using xml file
            if (mKey == VConstant.TOTAL) {
                popupMenuForTotalConstruction(popup);
            } else {
                popupMenuForTheRest(popup);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // popup menu dành cho tất cả các công việc
    private void popupMenuForTotalConstruction(PopupMenu popup) {
        popup.getMenuInflater()
                .inflate(R.menu.dashboard_cv_menu_filter, popup.getMenu());
        popup.setOnMenuItemClickListener((menuItem) -> {
            cleartSearch();
            switch (menuItem.getItemId()) {
                default:
                    showHideButtonCreateNewWork();
                    adapter.setListData(listData);
                    if (mIsHeaderView)
                        rcvWork.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv,
                            adapter.getItemCount() + ""));
                    rcvWork.scrollToPosition(0);
                    mIsHeaderView = false;

                    break;
                case R.id.low_process:
                    showHideButtonCreateNewWork();
                    rcvWork.setAdapter(adapter);
                    filterPopupData(2);
                    mIsHeaderView = false;
                    break;
                case R.id.on_schedule:
                    showHideButtonCreateNewWork();
                    filterPopupData(1);
                    mIsHeaderView = false;
                    break;
                case R.id.construction:
                    if (listData.size() > 0) {
                        btnCreateNewWork.setVisibility(View.GONE);
                        selectItem(listData);

                        mIsHeaderView = true;
                    } else {
                        txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, "0"));
                    }
                    break;
                case R.id.did_not_perform:
                    showHideButtonCreateNewWork();
                    rcvWork.setAdapter(adapter);
                    filterPopupData(VConstant.DID_NOT_PERFORM);
                    mIsHeaderView = false;
                    break;
                case R.id.in_process:
                    showHideButtonCreateNewWork();
                    filterPopupData(VConstant.IN_PROCESS);
                    mIsHeaderView = false;
                    break;
                case R.id.on_pause:
                    showHideButtonCreateNewWork();
                    filterPopupData(VConstant.ON_PAUSE);
                    mIsHeaderView = false;
                    break;
                case R.id.complete:
                    showHideButtonCreateNewWork();
                    filterPopupData(VConstant.COMPLETE);
                    mIsHeaderView = false;
                    break;
            }
            return true;
        });
        popup.show();
    }


    // popup menu dành cho các thuộc tính còn lại
    private void popupMenuForTheRest(PopupMenu popup) {
        popup.getMenuInflater()
                .inflate(R.menu.dashboard_cv_filter_menu_1, popup.getMenu());
        popup.setOnMenuItemClickListener((menuItem) -> {
            cleartSearch();
            switch (menuItem.getItemId()) {
                default:
                    showHideButtonCreateNewWork();
                    adapter.setListData(listData);
                    adapter.notifyDataSetChanged();
                    txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv,
                            adapter.getItemCount() + ""));
                    rcvWork.scrollToPosition(0);
                    mIsHeaderView = false;

                    break;
                case R.id.low_process:
                    showHideButtonCreateNewWork();
                    filterPopupData(VConstant.IN_PROCESS);
                    mIsHeaderView = false;

                    break;
                case R.id.on_schedule:
                    showHideButtonCreateNewWork();
                    filterPopupData(VConstant.DID_NOT_PERFORM);
                    mIsHeaderView = false;


                    break;
                case R.id.construction:
                    if (listData.size() > 0) {
                        btnCreateNewWork.setVisibility(View.GONE);
                        selectItem(listData);
                        txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv,
                                adapter.getItemCount() + ""));
                        mIsHeaderView = true;
                    } else {
                        txtTitle.setText(getActivity().getString(R.string.title_dashboard_list_cv, "0"));
                    }

                    break;
            }
            return true;
        });
        popup.show();
    }

    private void showHideButtonCreateNewWork() {
        if (mKey == VConstant.TOTAL) {
            btnCreateNewWork.setVisibility(View.VISIBLE);
        } else {
            btnCreateNewWork.setVisibility(View.GONE);
        }
    }

    @Override
    public void OnClickItem(ConstructionTaskDTO data) {
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
    public void OnClickHeader(ConstructionTaskDTO work) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btnAddItem)
    public void onClickCreate() {
        commitChange(new ChooseWorkChartFragment(), true);
    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        getFragmentManager().popBackStack();
    }

}
