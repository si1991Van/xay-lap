package com.viettel.construction.screens.atemp.other;

import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.viettel.construction.R;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.screens.custom.animation.ZoomOutPageTransformer;
import com.viettel.construction.screens.images.FullScreenImageAdapter;
import com.viettel.construction.screens.tabs.CompleteCategoryCameraActivity;

public class FullScreenCategoryActivity extends AppCompatActivity implements View.OnClickListener {

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

        mListUrl = new ArrayList<>();
        initListImageFromServer();
        btnClose.setOnClickListener(this);
    }

    private void setUpViewPager() {
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);
//        String itemList = i.getStringExtra("list");
//        Gson gson = new Gson();
//        listItems = gson.fromJson(itemList, new TypeToken<List<ImageItem>>() {
//        }.getType());
        adapter = new FullScreenImageAdapter(FullScreenCategoryActivity.this,
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
        mListUrl = CompleteCategoryCameraActivity.mListUrl;
        setUpViewPager();
//        ApiManager.getInstance().loadImage(DetailInProcessCameraActivity.mListUrl, ResultApi.class, new IOnRequestListener() {
//            @Override
//            public <T> void onResponse(T result) {
//                ResultApi resultApi = ResultApi.class.cast(result);
//                if (resultApi.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)){
//                    if (resultApi.getListImage() != null){
//                        // add image to list
//                        mListUrl = resultApi.getListImage();
//                        setUpViewPager();
//                    }else {
//                        Toast.makeText(FullScreenCompleteActivity.this, "Người dùng không có ảnh !", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onError(int statusCode) {
//
//            }
//        });
    }

}
