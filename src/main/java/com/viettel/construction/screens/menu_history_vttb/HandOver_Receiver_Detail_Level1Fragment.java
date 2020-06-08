package com.viettel.construction.screens.menu_history_vttb;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.history.HandOverHistoryDTORespone;
import com.viettel.construction.model.api.history.StTransactionDTO;
import com.viettel.construction.model.api.history.StTransactionDetailDTO;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.appbase.BaseChartFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HandOver_Receiver_Detail_Level1Fragment extends BaseChartFragment {

    private StTransactionDTO stTransactionDTO;
    @BindView(R.id.rcv_child_history)
    RecyclerView rcvHistory;
    private HandOver_Receiver_Detail_Level1Adapter historyAdapter;
    private List<StTransactionDetailDTO> listDetail;
    @BindView(R.id.tv_bill)
    TextView tvBill;
    @BindView(R.id.tv_construction)
    TextView tvConstruction;
    @BindView(R.id.tv_inventory)
    TextView tvInventory;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.tv_date)
    TextView tvDate;

    @BindView(R.id.txtHeader)
    TextView txtHeader;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_child, container, false);
        ButterKnife.bind(this, view);
        txtHeader.setText("Vật tư");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            stTransactionDTO = (StTransactionDTO) getArguments().getSerializable("StTransactionDTO");
            if (stTransactionDTO.getStockTransCode() != null)
                tvBill.setText(stTransactionDTO.getStockTransCode());
            if (stTransactionDTO.getStockTransConstructionCode() != null)
                tvConstruction.setText(stTransactionDTO.getStockTransConstructionCode());
            if (stTransactionDTO.getSynStockTransName() != null)
                tvInventory.setText(stTransactionDTO.getSynStockTransName());
            if (stTransactionDTO.getStockTransCreatedByName() != null)
                tvUser.setText(stTransactionDTO.getStockTransCreatedByName());
            if (stTransactionDTO.getStockTransCreatedDate() != null)
                tvDate.setText(stTransactionDTO.getStockTransCreatedDate());
        }

        loadData();
    }

    private void loadData() {
        ApiManager.getInstance().getListHistoryDetailHandOver(stTransactionDTO, HandOverHistoryDTORespone.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    HandOverHistoryDTORespone respone = HandOverHistoryDTORespone.class.cast(result);
                    ResultInfo resultInfo = respone.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        listDetail = new ArrayList<>();
                        listDetail = respone.getListStTransactionVTTBDTO();
                        historyAdapter = new HandOver_Receiver_Detail_Level1Adapter(getActivity(), listDetail, new HandOver_Receiver_Detail_Level1Adapter.OnClickHistory() {
                            @Override
                            public void onClick(StTransactionDetailDTO stTransactionDetailDTO) {
                                Intent intent = new Intent(getActivity(), DetailHistoryActivity.class);
                                intent.putExtra("stTransactionDetailDTO", stTransactionDetailDTO);
                                intent.putExtra("stTransactionDTO", stTransactionDTO);
                                startActivity(intent);
                            }
                        });
                        rcvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rcvHistory.setAdapter(historyAdapter);
                        historyAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {

            }
        });
    }

    @OnClick(R.id.imgBack)
    public void onBack() {
        getFragmentManager().popBackStack();
    }
}
