package com.viettel.construction.screens.wrac;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.viettel.construction.screens.atemp.adapter.AdapterHeaderRV;
import com.viettel.construction.screens.atemp.adapter.WorkAdapter;
import com.viettel.construction.screens.commons.DetailInProcessCameraActivity;
import com.viettel.construction.server.service.IOnRequestListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import rx.Observable;

import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.common.FilterableSection;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionTaskDTO;
import com.viettel.construction.model.api.ListConstructionTaskAll;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.commons.DetailCVCompleteCameraActivity;
import com.viettel.construction.screens.custom.dialog.CustomProgress;
import com.viettel.construction.appbase.BaseChartFragment;

/**
 * Dashboard công việc giám sát
 */

public class DashboardListCVSuperviseChartFragment extends BaseChartFragment
        implements WorkAdapter.OnClickViewDetails, AdapterHeaderRV.OnClickHeader {
    private WorkAdapter adapter;
    private List<ConstructionTaskDTO> listData = new ArrayList<>();
    private List<String> mListConstructionName = new ArrayList<>();

    @BindView(R.id.imgBack)
    ImageView imgBack;
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

    @BindView(R.id.progressBar)
    CustomProgress customProgress;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.imgClearTextSearch)
    View imgClearTextSearch;

    private int mKey = VConstant.TOTAL;
    private boolean mIsHeaderView;

    private boolean allowSearch = true;

    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard_cv_list, container, false);
        ButterKnife.bind(this, view);
        mIsHeaderView = false;
        if (getArguments() != null) {
            mKey = getArguments().getInt(VConstant.BUNDLE_KEY_FILTER);
            txtTitle.setText(getString(R.string.title_dashboard_list_cv, "0"));
        }

        btnCreateNewWork.setVisibility(View.GONE);
        // setup adapter for normal recyclerview
        adapter = new WorkAdapter(getActivity(), listData, this, true);
        rcvWork.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvWork.setAdapter(adapter);
        listenInputEdt();

        loadData();
        return view;
    }

    private void cleartSearch() {
        imgClearTextSearch.setVisibility(View.INVISIBLE);
        allowSearch = false;
        edtSearchWork.setText("");
        allowSearch = true;
    }

    private void listenInputEdt() {
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
                        if (!mIsHeaderView) {
                            if (editable.toString().length() > 0) {
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
                            } else {
                                adapter.setListData(listData);
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
                        changeHeader();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @OnClick(R.id.imgClearTextSearch)
    public void clearTextSearch() {
        imgClearTextSearch.setVisibility(View.INVISIBLE);
        edtSearchWork.setText("");
    }

    private void loadData() {
        customProgress.setLoading(true);
        ApiManager.getInstance().getListSuperviseWork(ListConstructionTaskAll.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ListConstructionTaskAll listConstructionTaskAll = ListConstructionTaskAll.class.cast(result);
                    ResultInfo resultInfo = listConstructionTaskAll.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK) && listConstructionTaskAll.getLstConstructionTaskDTO() != null) {
                        List<ConstructionTaskDTO> list = listConstructionTaskAll.getLstConstructionTaskDTO();


                        if (mKey==VConstant.TOTAL)
                            listData = list;
                        else
                            listData = loadDataByStatus(list, mKey);

                        adapter.setListData(listData);
                        adapter.notifyDataSetChanged();
                        customProgress.setLoading(false);
                        changeHeader();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                adapter.notifyDataSetChanged();
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


    /***
     * // Lọc list theo status
     * @param
     * @return
     */
    private List<ConstructionTaskDTO> loadDataByStatus(List<ConstructionTaskDTO> data, int status) {


        List<ConstructionTaskDTO> temp = Observable.from(data).filter(x -> x.getStatus() != null &&
                x.getStatus().contains(status + "")).toList().toBlocking().singleOrDefault(new ArrayList<>());
        return temp;
    }

    // Lọc list theo chậm tiến độ và đúng tiến độ
    private void filterListByStatus(String status) {
        List<ConstructionTaskDTO> dataSearch =
                Observable.from(listData).filter(x -> x.getCompleteState()!=null&&x.getCompleteState()
                        .equals(status)).toList().toBlocking().singleOrDefault(new ArrayList<>());

        adapter.setListData(dataSearch);
        if (mIsHeaderView)
            rcvWork.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        rcvWork.scrollBy(0, 0);
        changeHeader();
    }

    // Lọc list theo tên công trình
    private List<ConstructionTaskDTO> filterListByConstructionName(String s, List<ConstructionTaskDTO> list) {
        List<ConstructionTaskDTO> resultList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getConstructionCode() != null) {
                if (list.get(i).getConstructionCode().contains(s)) {
                    resultList.add(list.get(i));
                }
            }
        }
        return resultList;
    }

    private void selectItem(List<ConstructionTaskDTO> list) {
        mSectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
        // truyền vào list các construction name để làm header cho phần hiển thị theo tên công trình
        mListConstructionName.clear();
        for (ConstructionTaskDTO work : list) {
            if (!mListConstructionName.contains(work.getConstructionCode())) {
                mListConstructionName.add(work.getConstructionCode());
            }
        }
        // truyền vào các list construction theo từng tên công trình
        for (int i = 0; i < mListConstructionName.size(); i++) {
            if (mListConstructionName.get(i) != null) {
                List<ConstructionTaskDTO> temp = filterListByConstructionName(mListConstructionName.get(i), list);
                if (temp.size() > 0) {
                    mSectionedRecyclerViewAdapter.addSection(new AdapterHeaderRV(this, mListConstructionName.get(i), temp, getActivity(), true));
                }
            }
        }
        rcvWork.setAdapter(mSectionedRecyclerViewAdapter);
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
        rcvWork.scrollToPosition(0);
        changeHeader();
    }


    @OnClick(R.id.imgFilter)
    public void onClickFilter() {
        PopupMenu popup = new PopupMenu(getActivity(), imgFilter);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.dashboard_cv_filter_menu_1, popup.getMenu());
        popup.setOnMenuItemClickListener((menuItem) -> {
            cleartSearch();
            switch (menuItem.getItemId()) {
                case R.id.all:

                    adapter.setListData(listData);
                    if (mIsHeaderView)
                        rcvWork.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    changeHeader();

                    mIsHeaderView = false;
                    break;
                case R.id.low_process:

                    filterListByStatus("2");

                    mIsHeaderView = false;
                    break;
                case R.id.on_schedule:
                    filterListByStatus("1");
                    mIsHeaderView = false;
                    break;
                case R.id.construction:
                    selectItem(listData);
                    mIsHeaderView = true;
                    break;
                default:
                    break;
            }
            return true;
        });
        popup.show();
    }

    // popup menu dành cho các thuộc tính còn lại
    private void popupMenuForTheRest(PopupMenu popup) {

    }


    @Override
    public void OnClickItem(ConstructionTaskDTO dto) {
        Intent intent = new Intent(getActivity(), DetailCVCompleteCameraActivity.class);
        intent.putExtra(VConstant.BUNDLE_KEY_OBJECT_CV, dto);
        startActivity(intent);

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

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        getFragmentManager().popBackStack();
    }

}
