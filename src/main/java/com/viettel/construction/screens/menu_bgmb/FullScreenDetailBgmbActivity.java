package com.viettel.construction.screens.menu_bgmb;

import android.content.Intent;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.viettel.construction.R;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.screens.custom.animation.ZoomOutPageTransformer;
import com.viettel.construction.screens.images.FullScreenImageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullScreenDetailBgmbActivity extends AppCompatActivity implements View.OnClickListener {

    private FullScreenImageAdapter adapter;
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.ic_close)
    View btnClose;
    private List<ConstructionImageInfo> mListUrl;
    private int number = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_detail_bgmb);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

        mListUrl = new ArrayList<>();
        setUpViewPager();
        //initListImageFromServer();
        btnClose.setOnClickListener(this);
    }

    private void setUpViewPager() {
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);
        number = i.getIntExtra("listimage", 0);
        switch (number) {
            case 1:
                mListUrl = BgmbDetailActivity.mListUrl;
                break;
            case 2:
                mListUrl = BgmbTuyenActivity.mListUrl;
                break;
            default:
                mListUrl = BgmbDetailActivity.mListUrl;
                break;
        }
        adapter = new FullScreenImageAdapter(FullScreenDetailBgmbActivity.this,
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
                int currentPosition = position;
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
            ConstructionImageInfo item = mListUrl.get(position);

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


    private void initListImageFromServer() {
        setUpViewPager();
    }

}
