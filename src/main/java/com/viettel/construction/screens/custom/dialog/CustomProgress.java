package com.viettel.construction.screens.custom.dialog;

import android.content.Context;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.viettel.construction.R;

public class CustomProgress extends FrameLayout {
    private ImageView imgLoading;
    private ImageView imgVietel;
    private FrameLayout content;
    private boolean isLoading = false;

    public CustomProgress(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CustomProgress(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomProgress(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.my_progress_bar_layout, this);
        imgLoading = (ImageView) findViewById(R.id.img_loading);
        imgVietel = (ImageView) findViewById(R.id.img_viettel);
        content = (FrameLayout) findViewById(R.id.content);
    }

    public void setLoading(boolean isLoading) {
        if (this.isLoading == isLoading) {
            if (!isLoading)
                content.setVisibility(View.GONE);
            return;
        }
        this.isLoading = isLoading;
        if (isLoading) {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate);
            animation.setRepeatCount(Animation.INFINITE);
            animation.setRepeatMode(Animation.RESTART);
            content.setVisibility(View.VISIBLE);
            imgLoading.startAnimation(animation);
        } else {
            content.setVisibility(View.GONE);
            imgLoading.clearAnimation();
        }
    }

    public boolean isLoading() {
        return isLoading;
    }
}

