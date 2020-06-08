package com.viettel.construction.screens.custom.dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.viettel.construction.R;

/**
 * Created by manro on 2/22/2018.
 */

public class DialogReturn extends BaseDialog {

    public DialogReturn(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_return);
        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        txtReturnComplete = (TextView) findViewById(R.id.txt_dialog_return);
        txtReturnComplete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.txt_dialog_return) {
            dismiss();
        } else
            dismiss();
    }

    public interface OnClickDialog {
        void onClickConfrim();
    }

}
