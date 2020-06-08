package com.viettel.construction.screens.wrac;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseChartFragment;

public class AcceptanceLever2ChartFragment extends BaseChartFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acceptance_level_1, container, false);
        getData();
        return view;
    }

    private void getData() {

    }
}
