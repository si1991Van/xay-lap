package com.viettel.construction.screens.wrac;


import android.app.Activity;

import android.content.Context;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.screens.atemp.adapter.WorkAdapter;
import com.viettel.construction.screens.commons.ChooseWorkChartFragment;
import com.viettel.construction.screens.commons.DetailCV2CameraActivity;
import com.viettel.construction.screens.commons.DetailCVCompleteCameraActivity;
import com.viettel.construction.screens.commons.DetailInProcessCameraActivity;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionTaskDTO;
import com.viettel.construction.model.api.ListConstructionTaskAll;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.custom.dialog.CustomProgress;
import com.viettel.construction.appbase.BaseChartFragment;

/**
 * Hiển thị công việc trong ngày
 */
public class DashboardListCVOnDayChartFragment extends BaseChartFragment
        implements WorkAdapter.OnClickViewDetails {

    private WorkAdapter adapter;
    private List<ConstructionTaskDTO> mListOnDay = new ArrayList<>();

    @BindView(R.id.rcvData)
    RecyclerView rcvWork;
    @BindView(R.id.txtHeader)
    TextView txtTitle;
    @BindView(R.id.prg_loading)
    CustomProgress customProgress;
    @BindView(R.id.btnAddItem)
    FloatingActionButton btnAddItem;

    @BindView(R.id.edtSearch)
    EditText mEdtSearch;

    @BindView(R.id.txtNoData)
    TextView txtNoData;

    @BindView(R.id.imgClearTextSearch)
    View imgClearTextSearch;

    private Integer countWork = 0;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard_cv_list_on_day, container, false);
    }


    private void loadData() {
        customProgress.setLoading(true);
        ApiManager.getInstance().getWorkOnDay(ListConstructionTaskAll.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ListConstructionTaskAll listConstructionTaskAll = ListConstructionTaskAll.class.cast(result);
                    ResultInfo resultInfo = listConstructionTaskAll.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        if (listConstructionTaskAll.getLstConstructionTask() != null) {
                            Activity activity = getActivity();
                            if (activity != null && isAdded()) {
                                mListOnDay = listConstructionTaskAll.getLstConstructionTask();
                                txtTitle.setText(getString(R.string.work_day, mListOnDay.size() + ""));

                                adapter.setListData(mListOnDay);
                                adapter.notifyDataSetChanged();

                            }
                        }
                    }
                    //
                    customProgress.setLoading(false);
                    if (adapter.getListData() == null ||
                            (adapter.getListData() != null && adapter.getListData().size() == 0)) {
                        txtNoData.setVisibility(View.VISIBLE);
                    } else
                        txtNoData.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                customProgress.setLoading(false);
                //
                if (adapter.getListData() == null ||
                        (adapter.getListData() != null && adapter.getListData().size() == 0)) {
                    txtNoData.setVisibility(View.VISIBLE);
                } else
                    txtNoData.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapter = new WorkAdapter(getActivity(), mListOnDay, this, false);
        rcvWork.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvWork.setAdapter(adapter);
        txtTitle.setText(getString(R.string.work_day, countWork + ""));
        mListOnDay.clear();
        loadData();

        initEditextSearch();
    }

    private void initEditextSearch() {
        mEdtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEdtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Send the user message
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mEdtSearch.getWindowToken(), 0);
                    handled = true;
                }
                return handled;
            }
        });
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (editable.toString().length() == 0) {
                        adapter.setListData(mListOnDay);
                    } else {
                        if (editable.toString().length() > 0 && imgClearTextSearch.getVisibility() == View.INVISIBLE)
                            imgClearTextSearch.setVisibility(View.VISIBLE);
                        ArrayList<ConstructionTaskDTO> dataSearch = new ArrayList<>();
                        // check nếu chưa có dữ liệu trả về thì search sẽ crash
                        if (mListOnDay.size() > 0) {
                            // check hiển thị kiểu thường và kiểu list với header
                            if (editable.toString().length() > 0 && imgClearTextSearch.getVisibility() == View.INVISIBLE)
                                imgClearTextSearch.setVisibility(View.VISIBLE);
                            String temp = StringUtil.removeAccentStr(editable.toString() + "").toUpperCase().trim();
                            if (temp.length() != 0) {
                                for (ConstructionTaskDTO entity : mListOnDay) {
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
                            adapter.setListData(dataSearch);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    //
                    if (adapter.getListData() == null ||
                            (adapter.getListData() != null && adapter.getListData().size() == 0)) {
                        txtNoData.setVisibility(View.VISIBLE);
                    } else
                        txtNoData.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    @OnClick(R.id.imgClearTextSearch)
    public void clearTextSearch() {
        imgClearTextSearch.setVisibility(View.INVISIBLE);
        mEdtSearch.setText("");
    }


    @Override
    public void OnClickItem(ConstructionTaskDTO constructionTaskDTO) {

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

//
    }


    @OnClick(R.id.btnAddItem)
    public void onClickTest() {
        App.getInstance().setAuthor(null);
        Fragment frag = new ChooseWorkChartFragment();

        commitChange(frag,true);
    }

}
