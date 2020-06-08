package com.viettel.construction.screens.atemp.vttb;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.viettel.construction.R;
import com.viettel.construction.model.MaterialDevice;
import com.viettel.construction.appbase.BaseChartFragment;
import com.viettel.construction.screens.atemp.adapter.MaterialDeviceAdapter;


public class DevicesChartFragment extends BaseChartFragment {

    private MaterialDeviceAdapter adapterSuppliesAndMaterial;
    private List<MaterialDevice> listData;
    @BindView(R.id.rcv_device)
    RecyclerView rcvDevice;

    public DevicesChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_devices, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapterSuppliesAndMaterial = new MaterialDeviceAdapter(getActivity(), listData);
        rcvDevice.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvDevice.setAdapter(adapterSuppliesAndMaterial);
    }
}
