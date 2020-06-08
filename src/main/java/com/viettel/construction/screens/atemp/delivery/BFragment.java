package com.viettel.construction.screens.atemp.delivery;


import android.os.Bundle;
import androidx.annotation.Nullable;
import android.app.Fragment;
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
import com.viettel.construction.model.api.SynStockTransDTO;
import com.viettel.construction.screens.wrac.BillAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class BFragment extends Fragment implements BillAdapter.onBillItemClick {

    private BillAdapter adapter;
    @BindView(R.id.rcv_bill_b)
    RecyclerView rcvBill;
    private List<SynStockTransDTO> listBill;


    public BFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_b, container, false);
        ButterKnife.bind(view, getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listBill = new ArrayList<>();
        adapter = new BillAdapter(getActivity(), listBill, this);
        rcvBill.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvBill.setAdapter(adapter);
    }

    @Override
    public void onClickBill(SynStockTransDTO odt) {

    }
}
