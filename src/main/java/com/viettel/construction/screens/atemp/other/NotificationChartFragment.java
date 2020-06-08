package com.viettel.construction.screens.atemp.other;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseChartFragment;
import com.viettel.construction.screens.tabs.TabDashboardChartFragment;

/**
 * Created by Ramona on 1/17/2018.
 */

public class NotificationChartFragment extends BaseChartFragment {
    @BindView(R.id.ln_new_cv_in_process)
    LinearLayout mLnNewCVInProcess;
    @BindView(R.id.ln_cv_in_process_out_of_date)
    LinearLayout mLnCVInProcessOutOfDate;
    @BindView(R.id.ln_new_cv_supervise)
    LinearLayout mLnNewCVSupervise;
    @BindView(R.id.ln_cv_supervise_out_of_date)
    LinearLayout mLnCVSuperviseOutOfDate;
    @BindView(R.id.img_back)
    ImageView imgBack;

    public NotificationChartFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.img_back)
    public void onFinish() {
        commitChange(new TabDashboardChartFragment());
    }

    @OnClick(R.id.ln_new_cv_in_process)
    public void OnClickNewCVInProcess() {
//        replaceFragment(new DashboardListCV1ChartFragment(),"filter_key", "total", "5");
    }

    @OnClick(R.id.ln_cv_in_process_out_of_date)
    public void OnClickCVInProcessOutOfDate() {
//        replaceFragment(new DashboardListCV1ChartFragment(),"filter_key", "total", "5");
    }

    @OnClick(R.id.ln_new_cv_supervise)
    public void OnClickCVInProcessSupervise() {
//        replaceFragment(new DashboardListCV1ChartFragment(),"filter_key", "total", "5");
    }

    @OnClick(R.id.ln_cv_supervise_out_of_date)
    public void OnClickCVSuperviseOutOfDate() {
//        replaceFragment(new DashboardListCV1ChartFragment(),"filter_key", "total", "5");
    }

    public void replaceFragment(Fragment frag, String key, String s, String num) {
        Bundle bundle = new Bundle();
        bundle.putString(key, s);
        bundle.putString("number_of_cv", num);
        frag.setArguments(bundle);
        commitChange(frag);
    }
}
