package com.viettel.construction.screens.wrac;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.view.WindowManager;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.screens.atemp.adapter.TabPagerAdapter;
import com.viettel.construction.screens.custom.dialog.DialogReturn;
import com.viettel.construction.screens.atemp.vttb.DevicesChartFragment;
import com.viettel.construction.screens.atemp.vttb.FileChartFragment;
import com.viettel.construction.screens.atemp.vttb.SuppliesChartFragment;
import com.viettel.construction.screens.home.HomeCameraActivity;

public class MaterialAndEquipmentCameraActivity extends BaseCameraActivity {
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.btn_cancel_vttb)
    TextView btnCancel;
    @BindView(R.id.btn_return)
    TextView btnReturn;
    private DialogReturn dialogReturn;
    private TabPagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_materrial);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        hideKeyBoard();
        dialogReturn = new DialogReturn(this);
        setupViewPager(viewPager, tabLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setupViewPager(ViewPager viewPager, TabLayout tabLayout) {
        adapter = new TabPagerAdapter(getSupportFragmentManager(), this, tabLayout);
        adapter.addFragment(new DevicesChartFragment(), getResources().getString(R.string.text_material));
        adapter.addFragment(new SuppliesChartFragment(), getResources().getString(R.string.text_equipment));
        adapter.addFragment(new FileChartFragment(), getResources().getString(R.string.file));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @OnClick(R.id.btn_cancel_vttb)
    public void onClickCancel() {
        try {
            Intent i = new Intent(this, HomeCameraActivity.class);
            i.putExtra(CHANGE_LAYOUT_2, COMMIT_INTENT);
            startActivity(i);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_return)
    public void onClickReturnComplete() {
        dialogReturn.show();
    }
}
