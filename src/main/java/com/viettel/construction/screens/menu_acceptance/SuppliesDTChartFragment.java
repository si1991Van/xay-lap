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
import com.viettel.construction.model.Material;
import com.viettel.construction.appbase.BaseChartFragment;
import com.viettel.construction.screens.atemp.adapter.VTAAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuppliesDTChartFragment extends BaseChartFragment {


    @BindView(R.id.rcv_dt)
    RecyclerView rcvDT;
    private List<Material> list;
    private VTAAdapter adapter;

    public SuppliesDTChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supplies_dt, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvDT.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
//        adapter = new VTAAdapter(getActivity(),list);
        rcvDT.setAdapter(adapter);
    }

}
