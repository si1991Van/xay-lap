package com.viettel.construction.screens.menu_acceptance;


import android.app.Fragment;
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

import com.viettel.construction.screens.atemp.adapter.DeviceAAdapter;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailDTO;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailVTADTO;
import com.viettel.construction.model.api.ConstructionAcceptanceDTOResponse;
import com.viettel.construction.model.api.acceptance.ConstructionAcceptanceCertItemTBDTO;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.appbase.BaseChartFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceAChartFragment extends BaseChartFragment {

    @BindView(R.id.rcv_device)
    RecyclerView rcvDevice;
    public List<ConstructionAcceptanceCertDetailVTADTO> list;
    private DeviceAAdapter adapter;
    private String checkType = "";
    private ConstructionAcceptanceCertDetailDTO constructionAcceptanceCertDetailDTO;
    private final String TAG = "VTDeviceA";

    public DeviceAChartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_device_a, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkType = ((AcceptanceLevel3CameraActivity) getActivity()).data.getStatusAcceptance();
        constructionAcceptanceCertDetailDTO = ((AcceptanceLevel3CameraActivity) getActivity()).data;
        list = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvDevice.setLayoutManager(linearLayoutManager);
        adapter = new DeviceAAdapter(getActivity(), list, checkType);
        rcvDevice.setAdapter(adapter);

        getList();
    }

    private void getList() {
        ApiManager.getInstance().getListAcceptance2(constructionAcceptanceCertDetailDTO, ConstructionAcceptanceDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    Log.d(TAG,"onResponse");
                    ConstructionAcceptanceDTOResponse response = ConstructionAcceptanceDTOResponse.class.cast(result);
                    if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        if (response.getListConstructionAcceptanceCertDetailTBAPagesDTO() != null) {
                            list.addAll(response.getListConstructionAcceptanceCertDetailTBAPagesDTO());
                            Log.d(TAG,"onResponse - size : " + response.getListConstructionAcceptanceCertDetailTBAPagesDTO().size());
                            //check null for employ
                            //====================
                            List<ConstructionAcceptanceCertItemTBDTO> listItem = new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getListTBADetail() != null) {
                                    listItem.addAll(list.get(i).getListTBADetail());
                                    for (int j = 0; j < listItem.size(); j++) {
                                        if (listItem.get(j).getEmployTB() == null)
                                            listItem.get(j).setEmployTB(1);
                                    }
                                }
                            }


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
