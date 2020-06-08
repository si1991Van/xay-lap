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

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseChartFragment;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailDTO;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailVTBDTO;
import com.viettel.construction.model.api.ConstructionAcceptanceDTOResponse;
import com.viettel.construction.model.api.acceptance.ConstructionAcceptanceCertItemTBDTO;
import com.viettel.construction.screens.atemp.adapter.DeviceBAdapter;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceBChartFragment extends BaseChartFragment {

    @BindView(R.id.rcv_device)
    RecyclerView rcvDevice;
    public List<ConstructionAcceptanceCertDetailVTBDTO> list;
    private DeviceBAdapter adapter;
    private String checkType = "";
    private ConstructionAcceptanceCertDetailDTO constructionAcceptanceCertDetailDTO;
    private final String TAG = "VTDeviceB";


    public DeviceBChartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_device_b, container, false);
        ButterKnife.bind(this,view);
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
        adapter = new DeviceBAdapter(getActivity(), list, checkType);
        rcvDevice.setAdapter(adapter);

        getList();
    }


    private void getList() {
        ApiManager.getInstance().getListAcceptance4(constructionAcceptanceCertDetailDTO, ConstructionAcceptanceDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    Log.d(TAG,"onResponse");
                    ConstructionAcceptanceDTOResponse response = ConstructionAcceptanceDTOResponse.class.cast(result);
                    if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        if (response.getListConstructionAcceptanceCertDetailTBBPagesDTO() != null) {
                            list.addAll(response.getListConstructionAcceptanceCertDetailTBBPagesDTO());
                            Log.d(TAG,"onResponse - size : " + response.getListConstructionAcceptanceCertDetailTBBPagesDTO().size());
                            //check null for employ
                            //====================
                            List<ConstructionAcceptanceCertItemTBDTO> listItem = new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getListTBBDetail() != null) {
                                    listItem.addAll(list.get(i).getListTBBDetail());
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
