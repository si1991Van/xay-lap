//package com.viettel.construction.view.activity.other;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import com.viettel.construction.R;
//import com.viettel.construction.model.Supplies;
//import com.viettel.construction.model.api.SynStockTransDetailDTO;
//import com.viettel.construction.view.activity.home.SelectConstructionActivity;
//import com.viettel.construction.view.fragment.xuatkho.ExWarehouse_Detail_Adapter;
//
///**
// * Created by Ramona on 3/14/2018.
// */
//public class TransferMatetialActivity extends AppCompatActivity implements ExWarehouse_Detail_Adapter.OnClickDetailImplement{
//    @BindView(R.id.tv_receiver)
//    TextView mTVReceiver;
//    @BindView(R.id.iv_receiver)
//    ImageView mIVReceiver;
//    @BindView(R.id.tv_construction_name)
//    TextView mTVConstructionName;
//    @BindView(R.id.iv_construction)
//    ImageView mIVConstruction;
//    @BindView(R.id.rv_list_material)
//    RecyclerView mRVListMaterial;
//    @BindView(R.id.btn_cancel)
//    TextView mBtnCancel;
//    @BindView(R.id.btn_transfer)
//    TextView mBtnTransfer;
//    private List<Supplies> list;
//    private ExWarehouse_Detail_Adapter adapter;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_transfer_material);
//        ButterKnife.bind(this);
//
//        mBtnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//        list = new ArrayList<>();
//        //list = FakeData.getListSupplies();
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        mRVListMaterial.setLayoutManager(linearLayoutManager);
//        adapter = new ExWarehouse_Detail_Adapter(mRVListMaterial, this, list, this);
//        mRVListMaterial.setAdapter(adapter);
//    }
//
//    @OnClick(R.id.iv_receiver)
//    public void onClickReceiver() {
//        Intent intent = new Intent(this, SelectEmployeeActivity.class);
//        startActivityForResult(intent, 1);
//    }
//
//    @OnClick(R.id.iv_construction)
//    public void onClickConstruction() {
//        Intent intent = new Intent(this, SelectConstructionActivity.class);
//        startActivityForResult(intent, 2);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case 1:
//                mTVReceiver.setText(data.getStringExtra("employeeResult"));
//                break;
//            case 2:
//                mTVConstructionName.setText(data.getStringExtra("resultIntent"));
//                break;
//        }
//    }
//
//    @Override
//    public void onClickDetails(SynStockTransDetailDTO data) {
//        Intent intent = new Intent(this,InventoryDetailActivity.class);
//        intent.putExtra("Supplies",data);
//        startActivity(intent);
//    }
//
//}
