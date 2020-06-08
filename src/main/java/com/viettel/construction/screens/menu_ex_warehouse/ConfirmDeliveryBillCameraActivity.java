package com.viettel.construction.screens.menu_ex_warehouse;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.screens.commons.SelectEmployeeCameraActivity;
import com.viettel.construction.screens.home.HomeCameraActivity;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.EmployeeApi;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.StockTransResponse;
import com.viettel.construction.model.api.SynStockTransDTO;
import com.viettel.construction.model.api.SysUserRequest;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.custom.dialog.DialogConfirm;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * Xác nhận bàn giao
 */

public class ConfirmDeliveryBillCameraActivity extends BaseCameraActivity implements DialogConfirm.OnClickDialogForConfirm {

    public static final String KEY_BG_WITHOUT_CONFIRM = "bg_without_confirm";

    @BindView(R.id.tv_delivery_bill)
    TextView mTVDeliveryBill;

    @BindView(R.id.tv_construction)
    TextView mTVConstruction;

    @BindView(R.id.tv_warehouse)
    TextView mTVWarehouse;

    @BindView(R.id.tv_bill_creator)
    TextView mTVBillCreator;

    @BindView(R.id.tv_create_date)
    TextView mTVCreateDate;

    @BindView(R.id.tv_user_handover)
    TextView mTVHandoverUser;

    @BindView(R.id.img_user)
    ImageView imgUSer;

    @BindView(R.id.rl_add)
    RelativeLayout rlAdd;

    @BindView(R.id.rl_already_add)
    RelativeLayout rlAlreadyAdd;

    @BindView(R.id.edt_note)
    EditText edtNote;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    private DialogConfirm dialogConfirm;
    private SynStockTransDTO synStockTransDTO;
    private EmployeeApi employee;

    // Ban giao ma khong can xac nhan
    private boolean mWithoutfirm = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null)
            mWithoutfirm = getIntent().getBooleanExtra(KEY_BG_WITHOUT_CONFIRM, false);
        setContentView(R.layout.confirm_delivery_bill_activity);
        ButterKnife.bind(this);
        txtHeader.setText(R.string.confirm);
        // setup dialog
        App.getInstance().setAuthor(null);
        dialogConfirm = new DialogConfirm(this, this);
        dialogConfirm.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (getIntent() != null) {
            synStockTransDTO = (SynStockTransDTO) getIntent().getExtras().getSerializable("syn_stock_trans_dto_confirm");

            mTVDeliveryBill.setText(synStockTransDTO.getCode());
            mTVConstruction.setText(synStockTransDTO.getConsCode());
            mTVWarehouse.setText(synStockTransDTO.getSynStockName());
            mTVBillCreator.setText(synStockTransDTO.getSynCreatedByName());
            mTVCreateDate.setText(changeStringFormat(synStockTransDTO.getSynCreatedDate()));
        }
        if (employee == null) {
            rlAdd.setVisibility(View.VISIBLE);
            rlAlreadyAdd.setVisibility(View.GONE);
        } else {
            rlAdd.setVisibility(View.GONE);
            rlAlreadyAdd.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.img_delete)
    public void onClickDelete() {
        rlAdd.setVisibility(View.VISIBLE);
        rlAlreadyAdd.setVisibility(View.GONE);
        employee = null;
    }

    @OnClick(R.id.rl_add)
    public void onClickAdd() {
        Intent intent = new Intent(this, SelectEmployeeCameraActivity.class);
        intent.putExtra(SelectEmployeeCameraActivity.KEY_LAST_SHIPER_ID, synStockTransDTO.getLastShipperId());
        startActivityForResult(intent, 3);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                case 3:
                    if (resultCode == 3) {
                        if (data.getExtras() != null) {
                            employee = (EmployeeApi) data.getSerializableExtra("employeeResult");
                            rlAdd.setVisibility(View.GONE);
                            rlAlreadyAdd.setVisibility(View.VISIBLE);
                            mTVHandoverUser.setText(employee.getFullName());

                        }
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.lnBanGiao)
    public void onClickHandover() {
        if (employee != null) {
            dialogConfirm.show();
            dialogConfirm.txtTitle.setText(getString(R.string.confirm_delivery_bill_title_dialog));
            dialogConfirm.txtCodeRequire.setText(synStockTransDTO.getConsCode());
            dialogConfirm.txtCodeBill.setText(synStockTransDTO.getCode() + "");
        } else {
            Toast.makeText(ConfirmDeliveryBillCameraActivity.this, R.string.miss_input_user_deliver, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClickConfirmOfConfirm() {
        if (employee != null) {
            SysUserRequest sysUserReceiver = new SysUserRequest();
            sysUserReceiver.setSysUserId(Long.parseLong(employee.getSysUserId()));
            synStockTransDTO.setDescription(!edtNote.getText().toString().isEmpty() ? edtNote.getText().toString() : "");
            synStockTransDTO.setReceiverId(Long.parseLong(employee.getSysUserId()));
            // Khong ther viet chung hamg, qua nhieu class dung ham "updateDelivery"
            ApiManager.getInstance().updateDeliveryWithoutConfirm(2, synStockTransDTO, sysUserReceiver, StockTransResponse.class, new IOnRequestListener() {
                @Override
                public <T> void onResponse(T result) {
                    try {
                        StockTransResponse stockTransResponse = StockTransResponse.class.cast(result);
                        ResultInfo resultInfo = stockTransResponse.getResultInfo();
                        if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                            App.getInstance().setNeedUpdateAfterConfirmBill(true);
                            Toast.makeText(ConfirmDeliveryBillCameraActivity.this, resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                            dialogConfirm.dismiss();
                            //Send broadcast to reload
                            sendBroadcast(new Intent(ParramConstant.ExWarehouseReload));

                            Intent returnIntent = new Intent();
                            setResult(Activity.RESULT_OK, returnIntent);
                            Intent intent = new Intent(ConfirmDeliveryBillCameraActivity.this, HomeCameraActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ConfirmDeliveryBillCameraActivity.this, R.string.error_mes_notok, Toast.LENGTH_SHORT).show();
                            dialogConfirm.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(int statusCode) {
                    dialogConfirm.dismiss();
                    Toast.makeText(ConfirmDeliveryBillCameraActivity.this, R.string.error_mes, Toast.LENGTH_SHORT).show();
                }
            });

        }


    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        finish();
    }
}
