package com.viettel.construction.screens.custom.dialog;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.viettel.construction.R;

/**
 * Created by manro on 2/22/2018.
 */

public class DialogPleaseComment extends BaseDialog {

    private OnClickDialogPleaseComment dialogCancel;

    public DialogPleaseComment(@NonNull Context context, OnClickDialogPleaseComment dialogCancel) {
        super(context);
        setContentView(R.layout.dialog_please_comment);
        this.dialogCancel = dialogCancel;
        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        txtCancleOfCancle = (TextView) findViewById(R.id.txt_cancle_dialog_of_cancle);
        txtConfirmOfCancle = (TextView) findViewById(R.id.txt_dialog_confirm_of_cancle);

        txtConfirmOfCancle.setOnClickListener(this);
        txtCancleOfCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.txt_dialog_confirm_of_cancle) {
            dialogCancel.OnClickDialogPleaseComment(edtEnter.getText().toString().trim());
        } else
            dismiss();
    }

    public interface OnClickDialogPleaseComment {
        void OnClickDialogPleaseComment(String s);
    }

}
