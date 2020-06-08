package com.viettel.construction.screens.commons;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.server.service.IOnRequestListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.viettel.construction.R;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionTaskDTO;
import com.viettel.construction.model.api.ResultApi;
import com.viettel.construction.server.api.ApiManager;

/***
 * Lý do tạm dừng
 */
public class DashboardCVStopReflectActivity extends AppCompatActivity {

    public static final String VUONG_KTDB = "vuong";

    @BindView(R.id.txtSave)
    TextView btnSave;
    @BindView(R.id.edt_reason)
    EditText edtReason;
    @BindView(R.id.txt_construction_stop)
    TextView txtConstruction;
    @BindView(R.id.txt_work_stop)
    TextView txtWork;
    @BindView(R.id.txt_category_stop)
    TextView txtCategoryStop;

    @BindView(R.id.chkEntangle)
    CheckBox chkEntangle;

    @BindView(R.id.chkStuckKTDB)
    CheckBox chkVuongKtdb;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    private ConstructionTaskDTO mObjWork;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_cv_stop_reflect);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            mObjWork = (ConstructionTaskDTO) getIntent().getExtras().getSerializable("constructionTaskId");

            txtConstruction.setText(mObjWork.getConstructionCode());
            txtCategoryStop.setText(mObjWork.getWorkItemName());
            txtWork.setText(mObjWork.getTaskName());
            int vuong = getIntent().getIntExtra(VUONG_KTDB, -1);
            if (vuong != -1) {
                if (vuong == 1) {
                    chkEntangle.setChecked(true);
                } else if (vuong == 2) {
                    chkVuongKtdb.setChecked(true);
                }
            }
            chkEntangle.setOnCheckedChangeListener(vuongListener);
            chkVuongKtdb.setOnCheckedChangeListener(vuongListener);
        }

        txtHeader.setText(R.string.Pending);
    }

    @OnClick(R.id.txtCancel)
    public void onClickCancel() {
        finish();
    }


    private ProgressDialog dialogSave;

    @OnClick(R.id.txtSave)
    public void onClickSave() {
        String input = edtReason.getText().toString().trim();
        if (input.length() > 0 && input != null) {

            if(chkVuongKtdb.isChecked()){
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage(R.string.xac_nhan_ket_thuc_dong_bo);
                dialog.setCancelable(true);
                dialog.setPositiveButton(R.string.Conitune, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executeSaveState();
                    }
                });
                dialog.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }else{
                executeSaveState();
            }


        } else {
            edtReason.requestFocus();
            Toast.makeText(this, getString(R.string.warning_user_have_not_fill_edit_text), Toast.LENGTH_SHORT).show();
        }
    }

    private void executeSaveState(){
        if (chkEntangle.isChecked())
            mObjWork.setCheckEntangle(1l);
        else if (chkVuongKtdb.isChecked()) {
            mObjWork.setCheckEntangle(2l);
        } else {
            mObjWork.setCheckEntangle(0l);
        }
        Intent intent = new Intent();
        intent.putExtra("reason", edtReason.getText().toString().trim());
        intent.putExtra("load_list", "accept_load");
        mObjWork.setReasonStop(edtReason.getText().toString());

        dialogSave = new ProgressDialog(this);
        dialogSave.setMessage(getString(R.string.Processing));
        dialogSave.setCancelable(false);
        dialogSave.show();
        ApiManager.getInstance().updateForStop(mObjWork,
                ResultApi.class,
                new IOnRequestListener() {
                    @Override
                    public <T> void onResponse(T result) {
                        try {
                            ResultApi api = ResultApi.class.cast(result);
                            if (api.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                                Intent data = new Intent();
                                data.putExtra(ParramConstant.TaskContinueToStop, true);
                                setResult(ParramConstant.TaskContinueToStop_ResultCode, data);

                                Toast.makeText(DashboardCVStopReflectActivity.this,
                                        R.string.TaskContinueToStop, Toast.LENGTH_SHORT).show();

                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            dialogSave.dismiss();
                        }
                    }

                    @Override
                    public void onError(int statusCode) {
                        Toast.makeText(DashboardCVStopReflectActivity.this, "Tạm dừng thất bại", Toast.LENGTH_SHORT).show();
                        dialogSave.dismiss();
                    }
                }
        );
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialogSave = null;
    }

    CompoundButton.OnCheckedChangeListener vuongListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int id = buttonView.getId();
            if (id == R.id.chkEntangle) {
                if (isChecked) {
                    if (chkVuongKtdb.isChecked()) chkVuongKtdb.setChecked(false);
                }
            } else if (id == R.id.chkStuckKTDB) {
                if (chkEntangle.isChecked()) chkEntangle.setChecked(false);
            }
        }
    };
}
