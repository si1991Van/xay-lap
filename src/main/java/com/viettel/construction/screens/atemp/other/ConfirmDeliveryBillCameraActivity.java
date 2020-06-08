package com.viettel.construction.screens.atemp.other;

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

import com.viettel.construction.screens.commons.SelectEmployeeCameraActivity;
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
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.screens.custom.dialog.DialogConfirm;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * Màn hình activity hiển thị chi tiết sản phẩm : số lượng , serial , mã hợp đồng ....
 */

public class ConfirmDeliveryBillCameraActivity extends BaseCameraActivity implements DialogConfirm.OnClickDialogForConfirm {
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
    @BindView(R.id.img_back)
    ImageView btnBack;

    @BindView(R.id.edt_note)
    EditText edtNote;
    private DialogConfirm dialogConfirm;
    private SynStockTransDTO synStockTransDTO;
    private EmployeeApi employee;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_delivery_bill_activity);
        ButterKnife.bind(this);
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

//    @OnClick(R.id.ln_handover)
//    public void onClickHandover() {
//        dialogConfirm.show();
//        dialogConfirm.txtTitle.setText(getString(R.string.confirm_delivery_bill_title_dialog));
//        dialogConfirm.txtCodeRequire.setText(synStockTransDTO.getConsCode());
//        dialogConfirm.txtCodeBill.setText(synStockTransDTO.getCode() + "");
//    }

    @Override
    public void onClickConfirmOfConfirm() {
        if (employee != null) {
            SysUserRequest sysUserReceiver = new SysUserRequest();
            sysUserReceiver.setSysUserId(Long.parseLong(employee.getSysUserId()));
            if (!edtNote.getText().toString().isEmpty()) {
                synStockTransDTO.setDescription(edtNote.getText() + "");
            }
            ApiManager.getInstance().updateDelivery(2, synStockTransDTO, sysUserReceiver, StockTransResponse.class, new IOnRequestListener() {
                @Override
                public <T> void onResponse(T result) {
                    StockTransResponse stockTransResponse = StockTransResponse.class.cast(result);
                    ResultInfo resultInfo = stockTransResponse.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        App.getInstance().setNeedUpdateAfterConfirmBill(true);
                        Toast.makeText(ConfirmDeliveryBillCameraActivity.this, resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                        dialogConfirm.dismiss();
                        finish();
                    } else {
                        Toast.makeText(ConfirmDeliveryBillCameraActivity.this, resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                        dialogConfirm.dismiss();
                    }
                }

                @Override
                public void onError(int statusCode) {
                    dialogConfirm.dismiss();
                    Toast.makeText(ConfirmDeliveryBillCameraActivity.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            dialogConfirm.dismiss();
            Toast.makeText(ConfirmDeliveryBillCameraActivity.this, "Bạn chưa chọn người bàn giao!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.img_back)
    public void onClickBack() {
        finish();
    }
}
