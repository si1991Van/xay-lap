package com.viettel.construction.screens.menu_history_vttb;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.viettel.construction.model.api.history.HandOverHistoryDTORespone;
import com.viettel.construction.model.api.history.StTransactionDTO;
import com.viettel.construction.model.api.history.StTransactionDetailDTO;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.server.api.ApiManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailHistoryActivity extends AppCompatActivity {

    private StTransactionDetailDTO stTransactionDetailDTO;
    private StTransactionDTO stTransactionDTO;
    private DetailsHistoryAdapter adapter;
    private List<StTransactionDetailDTO> list;
    @BindView(R.id.rcv_detail_history)
    RecyclerView recyclerView;
    @BindView(R.id.tv_material_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_material_code)
    TextView tvDeviceCode;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);
        ButterKnife.bind(this);
        txtHeader.setText(R.string.detail);
        initComponent();
        initData();
    }

    private void initComponent() {
        list = new ArrayList<>();
        adapter = new DetailsHistoryAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    private void initData() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            stTransactionDetailDTO = (StTransactionDetailDTO) bundle.getSerializable("stTransactionDetailDTO");
            stTransactionDTO = (StTransactionDTO) bundle.getSerializable("stTransactionDTO");
            if (stTransactionDetailDTO.getGoodName() != null && stTransactionDetailDTO.getGoodUnitName() != null)
                tvDeviceName.setText(stTransactionDetailDTO.getGoodName() + " (" + stTransactionDetailDTO.getGoodUnitName() + ")");
            else
                tvDeviceName.setText("");

            if (stTransactionDetailDTO.getGoodCode() != null)
                tvDeviceCode.setText(stTransactionDetailDTO.getGoodCode());
            else
                tvDeviceCode.setText("");

            getListDetailItemHistory();
        }
    }


    private void getListDetailItemHistory() {
        ApiManager.getInstance().getDetailItemHandOver(stTransactionDTO, stTransactionDetailDTO, HandOverHistoryDTORespone.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    HandOverHistoryDTORespone respone = HandOverHistoryDTORespone.class.cast(result);
                    ResultInfo resultInfo = respone.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        list.addAll(respone.getListStTransactionVTTBDetailDTO());
                        adapter.notifyDataSetChanged();
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
    public void backScreen(){
        finish();
    }
}
