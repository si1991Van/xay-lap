package com.viettel.construction.screens.menu_return_vttb;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionMerchandiseDTORequest;
import com.viettel.construction.model.api.ConstructionMerchandiseDTOResponse;
import com.viettel.construction.model.api.ConstructionMerchandiseDetailDTO;
import com.viettel.construction.model.api.ConstructionMerchandiseDetailVTDTO;
import com.viettel.construction.server.api.ApiManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabDeviceRefundFragment extends Fragment {

    @BindView(R.id.rcv_device)
    RecyclerView rcvDevice;
    public List<ConstructionMerchandiseDetailVTDTO> list;
    private ConstructionMerchandiseDetailDTO constructionMerchandiseDetailDTO;
    private TabDeviceRefundAdapter adapter;
    private String checkType = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_device_refund, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list = new ArrayList<>();
        checkType = ((RefundItemLevel3CameraActivity) getActivity()).constructionMerchandiseDetailDTO.getStatusComplete();
        constructionMerchandiseDetailDTO = ((RefundItemLevel3CameraActivity) getActivity()).constructionMerchandiseDetailDTO;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvDevice.setLayoutManager(linearLayoutManager);
        adapter = new TabDeviceRefundAdapter(getActivity(), list, checkType);
        rcvDevice.setAdapter(adapter);

        getList();
    }

    private void getList() {

        ConstructionMerchandiseDTORequest request = new ConstructionMerchandiseDTORequest();
        request.setConstructionMerchandiseDetailDTO(constructionMerchandiseDetailDTO);
        request.setSysUserRequest(VConstant.getUser());

        ApiManager.getInstance().getListRefundLeverDetailTBLV3(request, ConstructionMerchandiseDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ConstructionMerchandiseDTOResponse response = ConstructionMerchandiseDTOResponse.class.cast(result);
                    if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        if (response.getListConstructionMerchandiseTB() != null) {
                            list.addAll(response.getListConstructionMerchandiseTB());
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
