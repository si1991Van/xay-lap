package com.viettel.construction.screens.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.viettel.construction.common.VConstant;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (VConstant.getUser() != null) {
                Class clazz = HomeCameraActivity.class;
                Intent intent = new Intent(this, clazz);
                startActivity(intent);
            } else {
                Intent intent = new Intent(SplashScreenActivity.this,
                        LoginCameraActivity.class);
                startActivity(intent);
            }

            finish();
        }, 1000);
    }
}
