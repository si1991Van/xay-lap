package com.viettel.construction.screens.home;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.model.api.version.AppParamDTO;
import com.viettel.construction.model.api.SysUserRequest;
import com.viettel.construction.model.api.version.AppParamDTOResponse;
import com.viettel.construction.screens.tabs.TabCompletedCategoryFragment;
import com.viettel.construction.screens.tabs.TabDashboardChartFragment;
import com.viettel.construction.screens.tabs.TabMenuChartFragment;
import com.viettel.construction.screens.tabs.TabWorkItemToDayFragment;
import com.viettel.construction.screens.wrac.IssueChartFragment;
import com.viettel.construction.screens.tabs.TabIssueListFragment;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.common.UpdateEvent;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.wrac.DashboardListCVOnDayChartFragment;
import com.viettel.construction.screens.wrac.CompleteCategoryFragment;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class HomeCameraActivity extends BaseCameraActivity
        implements TabMenuChartFragment.HighlightBottomBar {
    private final String TAG = "VTHomeActivity";
    BottomNavigationViewEx mBottomNavigationView;
    private EventBus bus = EventBus.getDefault();
    private boolean doubleBackToExitPressedOnce = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try {
            //Init DB
//            DBInit.getInstance();

            ButterKnife.bind(this);
            bus.register(this);
            initControl();
            initEvent();

            onEvent(new UpdateEvent(0));

            //Check Upgrade app
            checkAppVersion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkAppVersion() {
        SysUserRequest sysUserReceiver = new SysUserRequest();
        AppParamDTO appParamDTO = new AppParamDTO();
        appParamDTO.setParType(VConstant.MOBILE_PMXL_VERSION);
        appParamDTO.setStatus("1");
        ApiManager.getInstance().getVersionApp(2, appParamDTO ,sysUserReceiver, AppParamDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    AppParamDTOResponse response = AppParamDTOResponse.class.cast(result);
                    if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        final String link = response.getAppParamDTO().getDescription();
                        String versionServer = response.getAppParamDTO().getName();
                        String versionApp = StringUtil.getVersionApp(getApplicationContext());
                        Log.d(TAG, "checkAppVersion - versionServer : " + versionServer + " - versionApp : " + versionApp);
                        if (!versionServer.isEmpty() && versionServer != null) {
                            if (Float.valueOf(versionApp) < Float.valueOf(versionServer)) {
                                final AlertDialog.Builder alertDialogBuilder =
                                        new AlertDialog.Builder(HomeCameraActivity.this, R.style.AlertDialogTheme);
                                alertDialogBuilder.setCancelable(false);
                                alertDialogBuilder.setMessage(getString(R.string.update_version));
                                alertDialogBuilder.setPositiveButton("Cập nhật", (dl, arg1) -> {

                                    if (link.contains("https://play.google.com")) {
                                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                        try {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                        } catch (ActivityNotFoundException anfe) {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                        }
                                    } else {
                                        Intent intent = null;
                                        intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse(link));
                                        try {
                                            startActivity(intent);
                                        } catch (ActivityNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    //Finish
                                    finish();
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.setCancelable(false);
                                alertDialog.show();
                            }
                        } else {
                            Toast.makeText(HomeCameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(HomeCameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
            }
        });
    }

    public void onEvent(UpdateEvent event) {
        if (mBottomNavigationView != null)
            mBottomNavigationView.getMenu().getItem(event.getValue()).setChecked(true);
        else {
            mBottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottom_bar);
            mBottomNavigationView.getMenu().getItem(event.getValue()).setChecked(true);
        }
    }

    @Override
    public void onBackPressed() {
        try {
            mBottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottom_bar);
            FragmentManager manager = getSupportFragmentManager();
            Fragment currentFragment = manager.findFragmentById(R.id.contentPanel);
            if (currentFragment instanceof TabDashboardChartFragment) {
                if (doubleBackToExitPressedOnce) {
                    finish();
                    return;
                }
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, getResources().getString(R.string.text_again_exit), Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(() -> {
                    doubleBackToExitPressedOnce = false;
                }, 2000);
            } else {
                if (currentFragment instanceof DashboardListCVOnDayChartFragment
                        || currentFragment instanceof IssueChartFragment ||
                        currentFragment instanceof CompleteCategoryFragment ||
                        currentFragment instanceof TabMenuChartFragment) {
                    changeLayout(new TabDashboardChartFragment());
                    App.getInstance().setNeedUpdateBtn(false);
                    App.getInstance().setNeedUpdateBtnSupplier(false);
                    mBottomNavigationView.getMenu().getItem(0).setChecked(true);
                } else {
                    manager.popBackStack();
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            onBackPressed();
        }

    }

    private void initControl() {
        mBottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottom_bar);
        mBottomNavigationView.enableShiftingMode(false);
        mBottomNavigationView.enableItemShiftingMode(false);
        //mBottomNavigationView.setIconSize(widthDp, heightDp);
        mBottomNavigationView.setTextVisibility(false);
        changeLayout(new TabDashboardChartFragment());
    }


    private void initEvent() {
        mBottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            try {
                switch (item.getItemId()) {
                    case R.id.home:
                        //Dashboard
                        changeLayout(new TabDashboardChartFragment());
                        App.getInstance().setNeedUpdateBtn(false);
                        App.getInstance().setNeedUpdateBtnSupplier(false);
                        break;
                    case R.id.frag1:
                        //Công việc hôm nay
//                        Fragment frag = new DashboardListCVOnDayChartFragment();
                        Fragment frag = new TabWorkItemToDayFragment();
                        changeLayout(frag);
                        App.getInstance().setNeedUpdateBtn(false);
                        App.getInstance().setNeedUpdateBtnSupplier(false);
                        break;
                    case R.id.frag2:
                        //Quản lý phản ảnh
//                        changeLayout(new IssueChartFragment());
                        changeLayout(new TabIssueListFragment());
                        App.getInstance().setNeedUpdateBtn(false);
                        App.getInstance().setNeedUpdateBtnSupplier(false);
                        break;
                    case R.id.frag3:
                        //Hạng mục hoàn thành
//                        changeLayout(new CompleteCategoryFragment());
                        changeLayout(new TabCompletedCategoryFragment());
                        App.getInstance().setNeedUpdateBtn(false);
                        App.getInstance().setNeedUpdateBtnSupplier(false);
                        break;
                    case R.id.frag4:
                        //Màn hình menu
                        changeLayout(new TabMenuChartFragment());
                        App.getInstance().setNeedUpdateBtn(false);
                        App.getInstance().setNeedUpdateBtnSupplier(false);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        });
    }

    public void changeLayout(Fragment frag, Boolean... isAdd) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (isAdd != null && isAdd.length > 0 && isAdd[0])
            ft.add(R.id.contentPanel, frag);
        else
            ft.replace(R.id.contentPanel, frag);
        ft.addToBackStack(null);
        ft.commit();
    }


    @Override
    public void highLight() {
        mBottomNavigationView.getMenu().getItem(0).setChecked(true);
    }

}
