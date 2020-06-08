package com.viettel.construction.screens.custom.image;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by manro on 3/7/2018.
 */

public class DynamicWidthImageView extends androidx.appcompat.widget.AppCompatImageView {
    private float whRatio = 0;

    public DynamicWidthImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicWidthImageView(Context context) {
        super(context);
    }

    public void setRatio(float ratio) {
        whRatio = ratio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (whRatio != 0) {
            int height = getMeasuredHeight();
            int width = (int) (whRatio * height);
            setMeasuredDimension(width, height);
        }
    }
}