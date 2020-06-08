package com.viettel.construction.screens.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.viettel.construction.R;

/**
 * Created by manro on 2/23/2018.
 */

public class BaseDialog extends Dialog implements View.OnClickListener {

    // view of dialog confirm
    public TextView txtCancle, txtConfirm, txtCodeRequire, txtCodeBill, txtTitle, txtCountBill;
    // view of dialog confirm
    public TextView txtCancleOfCancle,txtConfirmOfCancle;
    public EditText edtEnter;
    // view for dialog return
    public TextView txtReturnComplete;

    public RadioGroup mRadioReasonAvaiableReason;

    public BaseDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_cancle_dialog || v.getId() == R.id.txt_cancle_dialog_of_cancle) {
            dismiss();
        }
    }

}

