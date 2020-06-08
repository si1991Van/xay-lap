package com.viettel.construction.screens.menu_acceptance;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.viettel.construction.appbase.BaseChartFragment;
import com.viettel.construction.screens.atemp.adapter.VTAAdapter;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailDTO;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailVTADTO;
import com.viettel.construction.model.api.ConstructionAcceptanceDTOResponse;
import com.viettel.construction.server.api.ApiManager;

public class SuppliesAChartFragment extends BaseChartFragment {

    @BindView(R.id.rcv_a)
    RecyclerView rcvA;
    public List<ConstructionAcceptanceCertDetailVTADTO> list;
    private ConstructionAcceptanceCertDetailDTO constructionAcceptanceCertDetailDTO;
    private VTAAdapter adapter;
    private String checkType = "";
    private final String TAG = "VTSuppliesA";


    public SuppliesAChartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_supplies_a, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        checkType = ((AcceptanceLevel3CameraActivity) getActivity()).data.getStatusAcceptance();
        constructionAcceptanceCertDetailDTO = ((AcceptanceLevel3CameraActivity) getActivity()).data;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvA.setLayoutManager(linearLayoutManager);
        adapter = new VTAAdapter(getActivity(), list, checkType);
        rcvA.setAdapter(adapter);

        getList();
    }


    private void getList() {
        ApiManager.getInstance().getListAcceptance1(constructionAcceptanceCertDetailDTO, ConstructionAcceptanceDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    Log.d(TAG,"onResponse");
                    ConstructionAcceptanceDTOResponse response = ConstructionAcceptanceDTOResponse.class.cast(result);
                    if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        if (response.getListConstructionAcceptanceCertDetailVTAPagesDTO() != null) {
                            list.addAll(response.getListConstructionAcceptanceCertDetailVTAPagesDTO());
                            Log.d(TAG,"onResponse - ok - Size : " + response.getListConstructionAcceptanceCertDetailVTAPagesDTO().size());
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getActivity(), response.getResultInfo().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(getActivity(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
