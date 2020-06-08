package com.viettel.construction.screens.tabs;


import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.CountConstructionTaskDTO;
import com.viettel.construction.model.api.ResultApi;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.appbase.BaseChartFragment;

/***
 * Dashboard quản lý công việc gồm : thực hiện và giám sát
 */

public class DashboardCV1ChartFragment extends BaseChartFragment {
    @BindView(R.id.frag_dashboard_cv_tv_total)
    TextView mTVTotal;
    @BindView(R.id.frag_dashboard_cv_tv_incomplete)
    TextView mTVIncomplete;
    @BindView(R.id.frag_dashboard_cv_tv_in_process)
    TextView mTVInProcess;
    @BindView(R.id.frag_dashboard_cv_tv_complete)
    TextView mTVComplete;
    @BindView(R.id.frag_dashboard_cv_tv_on_pause)
    TextView mTVOnPause;

    @BindView(R.id.tv_did_not_perform_description)
    TextView tvDidNotPerformDes;
    @BindView(R.id.tv_in_process_description)
    TextView tvInProcessDes;
    @BindView(R.id.tv_on_pause_description)
    TextView tvOnPauseDes;
    @BindView(R.id.tv_complete_description)
    TextView tvCompleteDes;

    @BindView(R.id.frag_dashboard_cv_btn_progress)
    Button mBtnProgress;
    @BindView(R.id.frag_dashboard_cv_btn_supervising)
    Button mBtnSupervising;
    @BindView(R.id.ln_total_cv)
    LinearLayout mLnTotalCV;
    @BindView(R.id.ln_incomplete)
    LinearLayout mLayoutIncomplete;
    @BindView(R.id.ln_in_process)
    LinearLayout mLayoutInProcess;
    @BindView(R.id.ln_complete)
    LinearLayout mLayoutComplete;
    @BindView(R.id.ln_pause)
    LinearLayout mLayoutPause;
    @BindView(R.id.chart_cv)
    PieChart pieChart;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    private List<Integer> listSup, listPer;
    private boolean mCheckSupervise;
    // private Bundle mBundle;
    private CountConstructionTaskDTO countConstructionTaskDTO;
    private View mViewAll;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listSup = new ArrayList<>();
        listPer = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewAll = inflater.inflate(R.layout.fragment_dashboard_cv1, container, false);
        ButterKnife.bind(this, mViewAll);
        txtHeader.setText(R.string.dashboard_cv_title);
        return mViewAll;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        App.getInstance().setAuthor(null);
        mCheckSupervise = App.getInstance().isNeedUpdateBtn();
        if (mCheckSupervise) {
            mBtnProgress.setEnabled(true);
            mBtnSupervising.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_btn_selected));
            mBtnProgress.setTextColor(ContextCompat.getColor(getActivity(), R.color.c5));
            mBtnSupervising.setEnabled(false);
            mBtnProgress.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_btn_default));
            mBtnSupervising.setTextColor(Color.WHITE);
        } else {
            mBtnProgress.setEnabled(false);
            mBtnSupervising.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_btn_default));
            mBtnProgress.setTextColor(Color.WHITE);
            mBtnSupervising.setEnabled(true);
            mBtnProgress.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_btn_selected));
            mBtnSupervising.setTextColor(ContextCompat.getColor(getActivity(), R.color.c5));
        }

        initEvent();
        loadData();
    }


    private void loadData() {
        ApiManager.getInstance().detailCountDashboard(ResultApi.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ResultApi resultApi = ResultApi.class.cast(result);
                    ResultInfo resultInfo = resultApi.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        countConstructionTaskDTO = resultApi.getCountConstructionTaskDTO();
                        //
                        listSup.clear();
                        listPer.clear();
                        listSup.add(countConstructionTaskDTO.getSup_didn_perform());
                        listSup.add(countConstructionTaskDTO.getSup_executable());
                        listSup.add(countConstructionTaskDTO.getSup_complition());
                        listSup.add(countConstructionTaskDTO.getSup_stop());
                        //
                        listPer.add(countConstructionTaskDTO.getPer_didn_perform());
                        listPer.add(countConstructionTaskDTO.getPer_executable());
                        listPer.add(countConstructionTaskDTO.getPer_complition());
                        listPer.add(countConstructionTaskDTO.getPer_stop());
                        if (mCheckSupervise) {
                            setTextSupervisor(countConstructionTaskDTO);
                            initChart4(listSup, pieChart, 0f, 0f, mParties4, 4, false);
                        } else {
                            setTextPerformer(countConstructionTaskDTO);
                            initChart4(listPer, pieChart, 0f, 0f, mParties4, 4, false);
                        }
                        //set data for chart


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {

            }
        });
    }

    /***
     * Khi Activity finish thì sẽ chạy vào Onresume từ lớp cha đến các lớp con
     */
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
    }

    private void setTextPerformer(CountConstructionTaskDTO countConstructionTaskDTO) {
        mTVTotal.setText(countConstructionTaskDTO.getPer_total() + "");
        mTVIncomplete.setText(countConstructionTaskDTO.getPer_didn_perform() + "");
        mTVInProcess.setText(countConstructionTaskDTO.getPer_executable() + "");
        mTVComplete.setText(countConstructionTaskDTO.getPer_complition() + "");
        mTVOnPause.setText(countConstructionTaskDTO.getPer_stop() + "");
    }

    private void setTextSupervisor(CountConstructionTaskDTO countConstructionTaskDTO) {
        mTVTotal.setText(countConstructionTaskDTO.getSup_total() + "");
        mTVIncomplete.setText(countConstructionTaskDTO.getSup_didn_perform() + "");
        mTVInProcess.setText(countConstructionTaskDTO.getSup_executable() + "");
        mTVComplete.setText(countConstructionTaskDTO.getSup_complition() + "");
        mTVOnPause.setText(countConstructionTaskDTO.getSup_stop() + "");
    }

    private void initEvent() {
        mBtnProgress.setOnClickListener((view) -> {
            mCheckSupervise = false;
            mBtnProgress.setEnabled(false);
            mBtnSupervising.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_btn_default));
            mBtnProgress.setTextColor(Color.WHITE);
            mBtnSupervising.setEnabled(true);
            mBtnProgress.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_btn_selected));
            mBtnSupervising.setTextColor(ContextCompat.getColor(getActivity(), R.color.c5));
            if (countConstructionTaskDTO != null) {
                setTextPerformer(countConstructionTaskDTO);
            }
            initChart4(listPer, pieChart, 50f, 0f, mParties4, 4, false);

        });
        mBtnSupervising.setOnClickListener((view) -> {
            mCheckSupervise = true;
            mBtnProgress.setEnabled(true);
            mBtnSupervising.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_btn_selected));
            mBtnProgress.setTextColor(ContextCompat.getColor(getActivity(), R.color.c5));
            mBtnSupervising.setEnabled(false);
            mBtnProgress.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_btn_default));
            mBtnSupervising.setTextColor(Color.WHITE);
            if (countConstructionTaskDTO != null) {
                setTextSupervisor(countConstructionTaskDTO);
            }
            App.getInstance().setNeedUpdateBtn(true);
            initChart4(listSup, pieChart, 50f, 0f, mParties4, 4, false);
        });

    }

    private void needRetainView() {
        if (mCheckSupervise) {
            App.getInstance().setNeedUpdateBtn(true);
        } else {
            App.getInstance().setNeedUpdateBtn(false);
        }
    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        getFragmentManager().popBackStack();
    }

    @OnClick(R.id.ln_total_cv)
    public void onClickLnTotalCV() {
        needRetainView();
        replaceFragment(VConstant.BUNDLE_KEY_FILTER, VConstant.TOTAL, "");
    }

    @OnClick(R.id.ln_incomplete)
    public void onClickLnIncomplete() {
        needRetainView();
        replaceFragment(VConstant.BUNDLE_KEY_FILTER, VConstant.DID_NOT_PERFORM, mTVIncomplete.getText() + "");
    }

    @OnClick(R.id.ln_in_process)
    public void onClickLnInProcess() {
        needRetainView();
        replaceFragment(VConstant.BUNDLE_KEY_FILTER, VConstant.IN_PROCESS, mTVInProcess.getText() + "");
    }

    @OnClick(R.id.ln_complete)
    public void onClickLnComplete() {
        needRetainView();
        replaceFragment(VConstant.BUNDLE_KEY_FILTER, VConstant.COMPLETE, mTVComplete.getText() + "");
    }

    @OnClick(R.id.ln_pause)
    public void onClickInProcess() {
        needRetainView();
        replaceFragment(VConstant.BUNDLE_KEY_FILTER, VConstant.ON_PAUSE, mTVOnPause.getText() + "");
    }


    // Đứng ở tab nào sẽ nhảy đến list công việc ứng với tab đó
    private void replaceFragment(String key, int s, String num) {
        Bundle mBundle = new Bundle();
        mBundle.putInt(key, s);
        mBundle.putString(VConstant.BUNDLE_KEY_NUMBER_OF_CV, num);
        Log.e("Key", s + "");
        // Nếu đang ở tab giám sát
        if (mCheckSupervise) {
//            Fragment mFragSupervise = new DashboardListCVSuperviseChartFragment();
            Fragment mFragSupervise = new DashboardListSuperviserFragment();
            mFragSupervise.setArguments(mBundle);
            commitChange(mFragSupervise, true);
        } else {
            // nếu đang ở tab thực hiện
//            Fragment mFragPerform = new DashboardListCV1ChartFragment();
            Fragment mFragPerform = new DashboardListMyTaskFragment();
            mFragPerform.setArguments(mBundle);
            commitChange(mFragPerform, true);
        }

    }
}
