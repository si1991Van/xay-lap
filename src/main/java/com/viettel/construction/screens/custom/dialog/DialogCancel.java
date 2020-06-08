package com.viettel.construction.screens.custom.dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.viettel.construction.R;

/**
 * Created by manro on 2/22/2018.
 */

public class DialogCancel extends BaseDialog {

    private OnClickDialogForCancel dialogCancel;

    public DialogCancel(@NonNull Context context, OnClickDialogForCancel dialogCancel) {
        super(context);
        setContentView(R.layout.dialog_cancle);
        this.dialogCancel = dialogCancel;
        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        txtCancleOfCancle = (TextView) findViewById(R.id.txt_cancle_dialog_of_cancle);
        txtConfirmOfCancle = (TextView) findViewById(R.id.txt_dialog_confirm_of_cancle);
        edtEnter = (EditText) findViewById(R.id.edt_of_cancle);

        txtConfirmOfCancle.setOnClickListener(this);
        txtCancleOfCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.txt_dialog_confirm_of_cancle) {
            dialogCancel.onClickConfirmOfCancel(edtEnter.getText().toString().trim());
        } else
            dismiss();
    }

    public interface OnClickDialogForCancel {
        void onClickConfirmOfCancel(String s);
    }

}
