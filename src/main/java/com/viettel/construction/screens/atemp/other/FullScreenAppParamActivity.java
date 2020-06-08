package com.viettel.construction.screens.atemp.other;

import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.viettel.construction.R;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.screens.commons.DetailCV2CameraActivity;
import com.viettel.construction.screens.commons.DetailCV2CompleteCameraActivity;
import com.viettel.construction.screens.commons.DetailCVGponCameraActivity;
import com.viettel.construction.screens.custom.animation.ZoomOutPageTransformer;
import com.viettel.construction.screens.images.FullScreenImageAdapter;
import com.viettel.construction.screens.menu_ex_warehouse.ExWarehouse_Detail_Adapter;
import com.viettel.construction.screens.menu_ex_warehouse.ExWarehouse_Detail_Fragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullScreenAppParamActivity extends AppCompatActivity implements View.OnClickListener {

    private FullScreenImageAdapter adapter;
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.ic_close)
    View btnClose;
    private List<ConstructionImageInfo> mListUrl;
    private final String TAG = "VTFullScreenActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

        mListUrl = new ArrayList<>();
        initListImageFromServer();
        btnClose.setOnClickListener(this);
    }

    private void setUpViewPager() {
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);
        int sourceCode = i.getIntExtra("source", 0);
        switch (sourceCode) {
            case 1:
                mListUrl = DetailCV2CameraActivity.mListUrl;
                break;
            case 2:
                mListUrl = DetailCVGponCameraActivity.mListUrl;
                break;
            case 3:
                mListUrl = DetailCV2CompleteCameraActivity.mListUrl;
                break;
            case 4:
                mListUrl = ExWarehouse_Detail_Fragment.mListUrl;
                break;
            default:
                break;
        }
//        String itemList = i.getStringExtra("list");
//        Gson gson = new Gson();
//        listItems = gson.fromJson(itemList, new TypeToken<List<ImageItem>>() {
//        }.getType());
        Log.d(TAG,"setUpViewPager - mListUrl : " + mListUrl.size() + " - sourceCode : " + sourceCode);
        adapter = new FullScreenImageAdapter(FullScreenAppParamActivity.this,
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
