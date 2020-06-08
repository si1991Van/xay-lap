package com.viettel.construction.screens.menu_updateconstruction;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionStationWorkItem;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.constructionextra.ConstructionExtraDTORequest;
import com.viettel.construction.model.constructionextra.ConstructionExtraDTOResponse;
import com.viettel.construction.model.constructionextra.ConstructionIDExtraDTORequest;
import com.viettel.construction.screens.commons.SelectConstructionCameraActivity;
import com.viettel.construction.screens.custom.dialog.CustomProgress;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateConstructionDetailCameraActivity
        extends BaseCameraActivity {

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.btnSelectConstruction)
    Button btnSelectConstruction;

    @BindView(R.id.progressBar)
    CustomProgress progressBar;

    //
    private TabDesignFragment tabDesignFragment;
    private TabHandOverFragment tabHandOverFragment;
    private TabLicenseFragment tabLicenseFragment;
    private TabStartingFragment tabStartingFragment;

    private ConstructionExtraDTORequest currentConstruction = new ConstructionExtraDTORequest();

    public void setCurrentConstruction(ConstructionExtraDTORequest currentConstruction) {
        this.currentConstruction = currentConstruction;
    }

    public ConstructionExtraDTORequest getCurrentConstruction() {
        return currentConstruction;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_construction_detail);
        ButterKnife.bind(this);
        txtHeader.setText(R.string.ConstructionInfoTitle);
        //init tab
        tabHandOverFragment = new TabHandOverFragment();
        tabLicenseFragment = new TabLicenseFragment();
        tabDesignFragment = new TabDesignFragment();
        tabStartingFragment = new TabStartingFragment();
        //-------
        tabLayout.addTab(tabLayout.newTab().setText("Bàn giao"));
        tabLayout.addTab(tabLayout.newTab().setText("GPXD"));
        tabLayout.addTab(tabLayout.newTab().setText("Thiết kế"));
        tabLayout.addTab(tabLayout.newTab().setText("Khởi công"));
        //
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        FragmentStatePagerAdapter adapter = new TabUpdateConstructionAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(4);
        //
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        progressBar.setLoading(true);
    }

    private ProgressDialog updateDialog;

    @OnClick(R.id.txtSave)
    public void saveData() {
        if (currentConstruction.getConstruction_id() > 0 && btnSelectConstruction.length() > 0) {
            updateDialog = new ProgressDialog(this);
            updateDialog.setMessage("Đang xử lý...");
            updateDialog.setCancelable(false);
            updateDialog.show();
            tabHandOverFragment.updateNewImage();
            tabDesignFragment.updateNewImage();
            tabLicenseFragment.updateNewImage();
            //Update useridupdate va group id update
            if (VConstant.getUser() != null) {
                currentConstruction.setUpdated_group_id(VConstant.getUser().getSysGroupId());
                currentConstruction.setUpdated_user_id(VConstant.getUser().getSysUserId());
            }
            ApiManager.getInstance().updateConstructionExtraInfo(
                    currentConstruction
                    , ResultInfo.class, new IOnRequestListener() {
                        @Override
                        public <T> void onResponse(T result) {
                            try {
                                ResultInfo resultApi =
                                        ResultInfo.class.cast(result);
                                if (resultApi.getStatus()
                                        .equals(VConstant.RESULT_STATUS_OK)) {

                                    Toast.makeText(UpdateConstructionDetailCameraActivity.this, "Cập nhật dữ liệu thành công.", Toast.LENGTH_SHORT).show();
                                    tabHandOverFragment.reloadData(currentConstruction);
                                    tabDesignFragment.reloadData(currentConstruction);
                                    tabStartingFragment.reloadData(currentConstruction);
                                    tabLicenseFragment.reloadData(currentConstruction);
                                } else {
                                    Toast.makeText(UpdateConstructionDetailCameraActivity.this, "Có lỗi xảy ra vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(UpdateConstructionDetailCameraActivity.this, "Có lỗi xảy ra vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                            } finally {
                                progressBar.setLoading(false);
                                updateDialog.dismiss();
                            }
                        }

                        @Override
                        public void onError(int statusCode) {
                            progressBar.setLoading(false);
                            updateDialog.dismiss();
                        }
                    });
        } else {
            Toast.makeText(this, "Vui lòng chọn công trình.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnSelectConstruction)
    public void selectConstruction() {
        Intent intent = new Intent(this,
                SelectConstructionCameraActivity.class);
        startActivityForResult(intent, ParramConstant.SelectedConstruction_RequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == ParramConstant.SelectedConstruction_RequestCode) {
                if (resultCode == 38) {
                    if (data.getExtras() != null) {
                        if (data.getExtras().getSerializable("resultIntent") != null) {
                            currentConstruction = new ConstructionExtraDTORequest();
                            currentConstruction.setUserRequest(VConstant.getUser());
                            ConstructionStationWorkItem construction = (ConstructionStationWorkItem) data.getSerializableExtra("resultIntent");
                            btnSelectConstruction.setText(construction.getConstructionCode());
                            currentConstruction.setConstruction_id(construction.getConstructionId());
                            progressBar.setLoading(true);
                            //Load data theo công trình
                            ApiManager.getInstance().getConstructionExtraInfoByID(
                                    new ConstructionIDExtraDTORequest(currentConstruction.getConstruction_id())
                                    , ConstructionExtraDTOResponse.class, new IOnRequestListener() {
                                        @Override
                                        public <T> void onResponse(T result) {
                                            try {
                                                ConstructionExtraDTOResponse resultApi =
                                                        ConstructionExtraDTOResponse.class.cast(result);
                                                if (resultApi.getResultInfo().getStatus()
                                                        .equals(VConstant.RESULT_STATUS_OK)) {

                                                    copyObject(resultApi.getData(), currentConstruction);

                                                    //Load data mặc định cho các tab
                                                    tabHandOverFragment.reloadData(currentConstruction);
                                                    tabDesignFragment.reloadData(currentConstruction);
                                                    tabStartingFragment.reloadData(currentConstruction);
                                                    tabLicenseFragment.reloadData(currentConstruction);
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            } finally {
                                                progressBar.setLoading(false);
                                            }
                                        }

                                        @Override
                                        public void onError(int statusCode) {
                                            progressBar.setLoading(false);
                                        }
                                    });
                        }
                    }
                }
            }
            //Camera
            if (resultCode == Activity.RESULT_OK) {
                switch (viewPager.getCurrentItem()) {
                    case 0:
                        tabHandOverFragment.getCameraCapturePath();
                        break;
                    case 1:
                        tabLicenseFragment.getCameraCapturePath();
                        break;
                    case 2:
                        tabDesignFragment.getCameraCapturePath();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void copyObject(Object source, Object dest) {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field _field : fields) {
            try {
                Field sourceProperty = source.getClass().getDeclaredField(_field.getName());
                Field destProperty = source.getClass().getDeclaredField(_field.getName());
                if (sourceProperty != null && destProperty != null) {
                    destProperty.setAccessible(true);
                    sourceProperty.setAccessible(true);
                    destProperty.set(dest, sourceProperty.get(source));
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.imgBack)
    public void cancelScreen() {
        finish();
    }


    class TabUpdateConstructionAdapter extends FragmentStatePagerAdapter {


        public TabUpdateConstructionAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                default:
                    return tabHandOverFragment;
                case 1:
                    return tabLicenseFragment;
                case 2:
                    return tabDesignFragment;
                case 3:
                    return tabStartingFragment;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title;
            switch (position) {
                default:
                    title = "Bàn giao";
                    break;
                case 1:
                    title = "GPXD";
                    break;
                case 2:
                    title = "Thiết kế";
                    break;
                case 3:
                    title = "Khởi công";
                    break;
            }
            return title;
        }
    }


}
