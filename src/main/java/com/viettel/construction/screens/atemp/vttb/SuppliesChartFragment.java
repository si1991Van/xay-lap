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


public class SuppliesChartFragment extends BaseChartFragment {

    private MaterialDeviceAdapter adapterSuppliesAndMaterial;
    private List<MaterialDevice> listData;
    @BindView(R.id.rcv_supplies)
    RecyclerView rcvSupplies;

    public SuppliesChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_supplies, container, false);
        ButterKnife.bind(this,view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapterSuppliesAndMaterial = new MaterialDeviceAdapter(getActivity(), listData);
        rcvSupplies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvSupplies.setAdapter(adapterSuppliesAndMaterial);
    }

}
