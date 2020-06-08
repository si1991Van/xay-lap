package com.viettel.construction.screens.atemp.other;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.WindowManager;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseCameraActivity;

public class ChangeMaterialCameraActivity extends BaseCameraActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_materrial);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        hideKeyBoard();
    }
}
