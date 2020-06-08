package com.viettel.construction.screens.custom.dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.viettel.construction.R;

/**
 * Created by manro on 2/22/2018.
 */

public class DialogConfirm extends BaseDialog {

    private OnClickDialogForConfirm onClickDialog;

    public DialogConfirm(@NonNull Context context, OnClickDialogForConfirm onClickDialog) {
        super(context);
        setContentView(R.layout.dialog_confirm);
        this.onClickDialog = onClickDialog;
        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        txtTitle = findViewById(R.id.tv_title_dialog);
        txtCancle = (TextView) findViewById(R.id.txt_cancle_dialog);
        txtConfirm = (TextView) findViewById(R.id.txt_dialog_confirm);
        txtCodeRequire = (TextView) findViewById(R.id.txt_code_require_dialog);
        txtCodeBill = (TextView) findViewById(R.id.txt_code_bill);
        txtCountBill = findViewById(R.id.txt_count_bill);

        txtConfirm.setOnClickListener(this);
        txtCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.txt_dialog_confirm) {
            onClickDialog.onClickConfirmOfConfirm();
        }else
            dismiss();
    }

    public interface OnClickDialogForConfirm {
        void onClickConfirmOfConfirm();
    }

}
