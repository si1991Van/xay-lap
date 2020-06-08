package com.viettel.construction.screens.atemp.other;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseChartFragment;

public class ListDeliveryBillChartFragment extends BaseChartFragment {
    public ListDeliveryBillChartFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_delivery_bill,container,false);
        return view;
    }
}
