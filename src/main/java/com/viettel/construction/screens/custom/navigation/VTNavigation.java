package com.viettel.construction.screens.custom.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.viettel.construction.server.util.StringUtil;

import com.viettel.construction.R;
import com.viettel.construction.screens.custom.common.VTAttribute;

/**
 * Created by Manroid on 18/01/2018.
 */

public class VTNavigation extends FrameLayout implements VTAttribute.VNavigationStyle,View.OnClickListener {

    private TextView title;
    private TextView navSub;
    private View icon;
    private LayoutInflater layoutInflater;
    private Context context;
    private OnSubTitleClickListener subTitleClickListener;

    public VTNavigation(@NonNull Context context) {
        super(context);
        init(context, null, 0);
    }

    public VTNavigation(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public VTNavigation(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.context = context;
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.VTView, 0, 0);
        int style = a.getInteger(R.styleable.VTView_style, 0);
        String text = a.getString(R.styleable.VTView_title);
        switch (style) {
            case NavigationTop:
                layoutInflater.inflate(R.layout.navigation_top, this, true);
                break;
            default:
                layoutInflater.inflate(R.layout.navigation, this, true);
                break;
        }
        if (findViewById(R.id.nav_title) != null) {
            title = (TextView) findViewById(R.id.nav_title);
            if (!StringUtil.isNullOrEmpty(text)) {
                title.setText(text);
            }
//            Typeface tf = FontUtil.getInstance().getTypeFace(context, NAVIGATION_TITLE_FONT_REGULAR);
//            title.setTypeface(tf);
        }
        if (findViewById(R.id.nav_back) != null) {
            icon = findViewById(R.id.nav_back);
            boolean hasBtnBack = a.getBoolean(R.styleable.VTView_hasBack, true);
            icon.setOnClickListener(this);
            int visibility = hasBtnBack ? VISIBLE : INVISIBLE;
            icon.setVisibility(visibility);
        }
        if (findViewById(R.id.nav_sub) != null) {
            navSub = (TextView) findViewById(R.id.nav_sub);
            String subTitle = a.getString(R.styleable.VTView_subTitle);
            if (!StringUtil.isNullOrEmpty(subTitle)) {
                navSub.setText(subTitle);
                navSub.setOnClickListener(this);
            }
        }
        setClickable(true);
        setFocusable(true);
        a.recycle();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.nav_back) {
            if (context instanceof Activity) {
                ((Activity) context).onBackPressed();
            }
        } else if (view.getId() == R.id.nav_sub) {
            if (subTitleClickListener != null)
                subTitleClickListener.onClick();
        } else {
//            ((MainActivity)context).changeLayout(new TabDashboardChartFragment());
        }
    }

    public void setTitle(String titleStr) {
        if (!StringUtil.isNullOrEmpty(titleStr) && title != null)
            title.setText(titleStr);
    }

    public interface OnSubTitleClickListener {
        void onClick();
    }

    public void setSubTitleClickListener(OnSubTitleClickListener onSubTitleClickListener) {
        this.subTitleClickListener = onSubTitleClickListener;
    }
}
