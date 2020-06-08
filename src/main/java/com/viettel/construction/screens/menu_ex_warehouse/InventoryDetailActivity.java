package com.viettel.construction.screens.menu_ex_warehouse;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.MerEntityDTO;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.StockTransResponse;
import com.viettel.construction.model.api.SynStockTransDTO;
import com.viettel.construction.model.api.SynStockTransDetailDTO;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.custom.dialog.CustomProgress;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * Chi tiết hàng hóa bên A
 */
public class InventoryDetailActivity extends AppCompatActivity {
    private InventoryDetailAdapter inventoryDetailAdapter;
    private List<MerEntityDTO> detailsImplementList = new ArrayList<>();
    private SynStockTransDetailDTO synStockTransDetailDTO = new SynStockTransDetailDTO();
    private SynStockTransDTO synStockTransDTO = new SynStockTransDTO();
    private RecyclerView recyclerView;
    @BindView(R.id.tv_material_name)
    TextView mTVMaterialName;
    @BindView(R.id.tv_material_code)
    TextView mTVMaterialCode;
    @BindView(R.id.detail_implement_dialog)
    CustomProgress customProgress;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_implement);
        ButterKnife.bind(this);
        txtHeader.setText(R.string.detail);
        customProgress.setLoading(false);
        initComponent();
        initData();
    }

    private void initComponent() {
        inventoryDetailAdapter = new InventoryDetailAdapter(this, detailsImplementList);
        recyclerView = (RecyclerView) findViewById(R.id.rv_detail_material);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(inventoryDetailAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initData() {
        if (getIntent() != null) {
//            Intent intent = getIntent();
//            Bundle bundle = intent.getExtras();
            Bundle bundle = getIntent().getExtras();
            synStockTransDetailDTO = (SynStockTransDetailDTO) bundle.getSerializable(VConstant.BUNDLE_KEY_MATERIAL_ENTITY);
            synStockTransDTO = (SynStockTransDTO) bundle.getSerializable(VConstant.BUNDLE_KEY_BILL_ENTITY);
            getListDetailMerDTO();
        }

    }

    @OnClick(R.id.imgBack)
    public void OnClickBack(View view) {
        finish();
    }

    private void getListDetailMerDTO() {
        // fake model , should use model from class other
        customProgress.setLoading(true);
        ApiManager.getInstance().getListMerEntityDTO(synStockTransDetailDTO, synStockTransDTO, StockTransResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    StockTransResponse response = StockTransResponse.class.cast(result);
                    ResultInfo resultInfo = response.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        // do something here
                        Log.e("1", "ok");
                        if (response.getLstMerEntity().size() != 0) {
                            detailsImplementList.addAll(response.getLstMerEntity());
                            inventoryDetailAdapter.notifyDataSetChanged();
                            MerEntityDTO merEntityDTO = detailsImplementList.get(0);
                            mTVMaterialCode.setText(merEntityDTO.getGoodsCode().toString());
                            mTVMaterialName.setText(merEntityDTO.getGoodsName().toString());
                        }

                        customProgress.setLoading(false);

                    } else {
                        customProgress.setLoading(false);
                        Toast.makeText(InventoryDetailActivity.this, resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                customProgress.setLoading(false);
                Toast.makeText(InventoryDetailActivity.this, getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
