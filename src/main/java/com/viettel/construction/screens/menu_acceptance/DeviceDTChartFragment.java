package com.viettel.construction.screens.menu_acceptance;


import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.viettel.construction.R;
import com.viettel.construction.model.Device;
import com.viettel.construction.appbase.BaseChartFragment;
import com.viettel.construction.screens.atemp.adapter.DeviceAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceDTChartFragment extends BaseChartFragment {

    @BindView(R.id.rcv_dt)
    RecyclerView rcvDT;
    private List<Device> list;
    private DeviceAdapter adapter;
    private final String TAG = "VTDeviceDT";

    public DeviceDTChartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_device_dt, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvDT.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        //adapter = new DeviceAdapter(getActivity(),list);
        //rcvDT.setAdapter(adapter);
    }
}
