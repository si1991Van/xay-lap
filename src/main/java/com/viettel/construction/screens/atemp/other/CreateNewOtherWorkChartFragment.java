package com.viettel.construction.screens.atemp.other;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseChartFragment;

/**
 * Created by Ramona on 3/20/2018.
 */

public class CreateNewOtherWorkChartFragment extends BaseChartFragment {
    @BindView(R.id.txt_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_performer)
    TextView tvPerformer;
    @BindView(R.id.tv_start_time_complete)
    TextView tvStartTime;
    @BindView(R.id.tv_finish_time_complete)
    TextView tvFinishTime;
    @BindView(R.id.imv_performer)
    RelativeLayout imvPerformer;
    @BindView(R.id.imv_calendar1)
    RelativeLayout imvCalendar1;
    @BindView(R.id.imv_calendar2)
    RelativeLayout imvCalendar2;
    @BindView(R.id.btn_save)
    TextView btnSave;
    @BindView(R.id.edt_content_work)
    EditText edtContentWork;
    private Intent intent;
    private long idConstruction = 0;
    private long idCategory = 0;
    private long idWork = 0;

    public CreateNewOtherWorkChartFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_other_work, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
