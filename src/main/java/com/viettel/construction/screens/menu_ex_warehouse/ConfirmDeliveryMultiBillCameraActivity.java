package com.viettel.construction.screens.menu_ex_warehouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.common.App;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.Employee;
import com.viettel.construction.model.api.EmployeeApi;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.StockTransRequest;
import com.viettel.construction.model.api.StockTransResponse;
import com.viettel.construction.model.api.SynStockTransDTO;
import com.viettel.construction.model.api.SynStockTransDetailDTO;
import com.viettel.construction.model.api.SysUserRequest;
import com.viettel.construction.screens.commons.SelectEmployeeCameraActivity;
import com.viettel.construction.screens.custom.dialog.DialogConfirm;
import com.viettel.construction.screens.home.HomeCameraActivity;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmDeliveryMultiBillCameraActivity extends BaseCameraActivity implements DialogConfirm.OnClickDialogForConfirm {

    public static final String KEY_BG_WITHOUT_CONFIRM = "bg_without_confirm";

    @BindView(R.id.rv_data)
    RecyclerView mRvData;

    @BindView(R.id.rl_already_add)
    RelativeLayout mRlAlreadyAdd;

    @BindView(R.id.tv_user_handover)
    TextView mTVHandoverUser;

    @BindView(R.id.img_delete)
    ImageView mImgDelete;

    @BindView(R.id.rl_add)
    RelativeLayout mRlAdd;

    @BindView(R.id.edt_note)
    EditText mEdtNote;

    @BindView(R.id.lnBanGiao)
    LinearLayout mLnBanGiao;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.txt_soluong)
    TextView txtSoLuong;

    private DialogConfirm dialogConfirm;
    private List<SynStockTransDTO> listSelectedSynStock;
    private EmployeeApi employeeAPI;
    //Ban giao khong can xac nhan
    private boolean mWithoutfirm = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent() != null) {
            mWithoutfirm = getIntent().getBooleanExtra(KEY_BG_WITHOUT_CONFIRM, false);
            setContentView(R.layout.activity_confirm_delivery_multi_bill_camera);
            ButterKnife.bind(this);
            txtHeader.setText(R.string.confirm);
            // setup dialog
            App.getInstance().setAuthor(null);
            dialogConfirm = new DialogConfirm(this, this);
            dialogConfirm.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            listSelectedSynStock = (List<SynStockTransDTO>) getIntent().getExtras().getSerializable("list_selected_bill");
            if(listSelectedSynStock != null && listSelectedSynStock.size() > 0){
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                mRvData.setLayoutManager(linearLayoutManager);
                BillSelectedAdapter billSelectedAdapter = new BillSelectedAdapter(ConfirmDeliveryMultiBillCameraActivity.this, listSelectedSynStock);
                billSelectedAdapter.setItemClick(new BillSelectedAdapter.itemClick() {
                    @Override
                    public void itemDelete(int pos) {
                        listSelectedSynStock.remove(pos);
                        billSelectedAdapter.notifyItemRemoved(pos);
                        txtSoLuong.setText(String.valueOf(listSelectedSynStock.size()));
                    }
                });
                mRvData.setAdapter(billSelectedAdapter);
                txtSoLuong.setText(String.valueOf(listSelectedSynStock.size()));
            }
            if (employeeAPI == null) {
                mRlAdd.setVisibility(View.VISIBLE);
                mRlAlreadyAdd.setVisibility(View.GONE);
            } else {
                mRlAdd.setVisibility(View.GONE);
                mRlAlreadyAdd.setVisibility(View.VISIBLE);
            }


        }
    }

    @OnClick(R.id.img_delete)
    public void onClickDelete() {
        mRlAdd.setVisibility(View.VISIBLE);
        mRlAlreadyAdd.setVisibility(View.GONE);
        employeeAPI = null;
    }
    @OnClick(R.id.rl_add)
    public void onClickAdd() {
        Intent intent = new Intent(this, SelectEmployeeCameraActivity.class);
        intent.putExtra(SelectEmployeeCameraActivity.KEY_LAST_SHIPER_ID, listSelectedSynStock.get(0).getLastShipperId());
        startActivityForResult(intent, 3);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                case 3:
                    if (resultCode == 3) {
                        if (data.getExtras() != null) {
                            employeeAPI = (EmployeeApi) data.getSerializableExtra("employeeResult");
                            mRlAdd.setVisibility(View.GONE);
                            mRlAlreadyAdd.setVisibility(View.VISIBLE);
                            mTVHandoverUser.setText(employeeAPI.getFullName());
                        }
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @OnClick(R.id.lnBanGiao)
    public void onClickHandover() {
        if (employeeAPI != null) {
            if(listSelectedSynStock!= null && listSelectedSynStock.size() > 0){
                dialogConfirm.show();
                dialogConfirm.txtTitle.setText(getString(R.string.confirm_delivery_bill_title_dialog));
                dialogConfirm.txtCountBill.setVisibility(View.VISIBLE);
                dialogConfirm.txtCountBill.setText(String.valueOf(listSelectedSynStock.size()) + " phiếu");
            }
            else {
                Toast.makeText(ConfirmDeliveryMultiBillCameraActivity.this, R.string.miss_input_list_deliver, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ConfirmDeliveryMultiBillCameraActivity.this, R.string.miss_input_user_deliver, Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onClickConfirmOfConfirm() {
        if(employeeAPI !=  null){
            SysUserRequest sysUserRequest = new SysUserRequest();
            sysUserRequest.setSysUserId(Long.parseLong(employeeAPI.getSysUserId()));
            String description = !mEdtNote.getText().toString().isEmpty() ? mEdtNote.getText().toString() : "";
            Long receiverId = Long.parseLong(employeeAPI.getSysUserId());
            ApiManager.getInstance().updateListDeliveryWithoutConfirm(2, listSelectedSynStock, sysUserRequest, receiverId, description, StockTransResponse.class, new IOnRequestListener() {
                @Override
                public <T> void onResponse(T result) {
                    try {
                        StockTransResponse stockTransResponse = StockTransResponse.class.cast(result);
                        ResultInfo resultInfo = stockTransResponse.getResultInfo();
                        if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                            App.getInstance().setNeedUpdateAfterConfirmBill(true);
                            Toast.makeText(ConfirmDeliveryMultiBillCameraActivity.this, resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                            dialogConfirm.dismiss();
                            //Send broadcast to reload
                            sendBroadcast(new Intent(ParramConstant.ExWarehouseReload));

                            Intent returnIntent = new Intent();
                            setResult(Activity.RESULT_OK, returnIntent);

                            Intent intent = new Intent(ConfirmDeliveryMultiBillCameraActivity.this, HomeCameraActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ConfirmDeliveryMultiBillCameraActivity.this, R.string.error_mes_notok, Toast.LENGTH_SHORT).show();
                            dialogConfirm.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(int statusCode) {
                    dialogConfirm.dismiss();
                    Toast.makeText(ConfirmDeliveryMultiBillCameraActivity.this, R.string.error_mes, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        finish();
    }

//    public void startFragmtent(){
//        Fragment fr = new TabPageExWarehouse_ItemFragment_Paging();
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.add(R.id.acceptance_finished, fr);
//    }
}
