package com.viettel.construction.screens.atemp.other;

import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.viettel.construction.R;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.screens.custom.animation.ZoomOutPageTransformer;
import com.viettel.construction.screens.images.FullScreenImageAdapter;

public class ViewFullImageActivity extends AppCompatActivity implements View.OnClickListener {

    private FullScreenImageAdapter adapter;
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.ic_close)
    View btnClose;
    private List<ConstructionImageInfo> mListUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

        mListUrl = (List<ConstructionImageInfo>) getIntent().getSerializableExtra(ParramConstant.ListImage_Key);

        setUpViewPager();
        btnClose.setOnClickListener(this);
    }

    private void setUpViewPager() {
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);

        adapter = new FullScreenImageAdapter(ViewFullImageActivity.this,
                mListUrl, listener);
        viewPager.setAdapter(adapter);
        // displaying selected image first
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                listener.setUpData(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private FullScreenImageAdapter.IOnImageListener listener = new FullScreenImageAdapter.IOnImageListener() {

        @Override
        public void setUpData(int position) {
            int currentPosition = viewPager.getCurrentItem();
            if (currentPosition != position)
                return;

        }

        @Override
        public void showImageDetails(boolean isShow) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ic_close:
                onBackPressed();
                break;
            default:
                break;
        }
    }




}
