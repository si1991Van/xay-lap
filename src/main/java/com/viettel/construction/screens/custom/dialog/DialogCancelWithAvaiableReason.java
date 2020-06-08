package com.viettel.construction.screens.custom.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.viettel.construction.R;

public class DialogCancelWithAvaiableReason extends BaseDialog{
    private OnClickDialogForCancel dialogCancel;

    public DialogCancelWithAvaiableReason(@NonNull Context context) {
        super(context);
    }

    public DialogCancelWithAvaiableReason(@NonNull Context context, OnClickDialogForCancel dialogCancel) {
        super(context);
        setContentView(R.layout.dialog_cancel_with_avaiable_reason);
        this.dialogCancel = dialogCancel;
        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        txtCancleOfCancle = (TextView) findViewById(R.id.txt_cancle_dialog_of_cancle);
        txtConfirmOfCancle = (TextView) findViewById(R.id.txt_dialog_confirm_of_cancle);
        mRadioReasonAvaiableReason = findViewById(R.id.radio_reason_group);

        txtConfirmOfCancle.setOnClickListener(this);
        txtCancleOfCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.txt_dialog_confirm_of_cancle) {
            int idRadioItemSelect = mRadioReasonAvaiableReason.getCheckedRadioButtonId();
            String message = (idRadioItemSelect == R.id.radio_sai_don_vi_nhan)? "Sai đơn bị nhận": "Sai số lượng, chủng loại vật tư/Sai mảng công việc xây lắp";
            dialogCancel.onClickConfirmOfCancel(message);
        } else
            dismiss();
    }

    public interface OnClickDialogForCancel {
        void onClickConfirmOfCancel(String s);
    }
}
