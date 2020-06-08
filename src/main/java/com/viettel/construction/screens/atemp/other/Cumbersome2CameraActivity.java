package com.viettel.construction.screens.atemp.other;


import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseCameraActivity;

public class Cumbersome2CameraActivity extends BaseCameraActivity {
    @BindView(R.id.btn_cancel_cumbersome)
    TextView btnCancel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cumbersome2);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_cancel_cumbersome)
    public void onCancel(){
        finish();
    }

}
