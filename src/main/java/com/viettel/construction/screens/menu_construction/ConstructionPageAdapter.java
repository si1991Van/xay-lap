package com.viettel.construction.screens.menu_construction;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.viettel.construction.common.App;
import com.viettel.construction.R;

public class ConstructionPageAdapter extends FragmentStatePagerAdapter {

    private Context context;

    public ConstructionPageAdapter(Context context,FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    public TabPageConstructionFragment.ITabConstructionChanged tabDelegate;

    @Override
    public Fragment getItem(int position) {
//        switch (position) {
//            default: {
//                BuildFragment buildFragment = new BuildFragment();
//                tabDelegate = buildFragment;
//                return buildFragment;
//            }
//            case 1: {
//                MonitoringFragment monitoringFragment = new MonitoringFragment();
//                tabDelegate = monitoringFragment;
//                return monitoringFragment;
//            }
//
//            case 2: {
//                MonthPlaningFragment monitoringFragment = new MonthPlaningFragment();
//                tabDelegate = monitoringFragment;
//                return monitoringFragment;
//            }
//        }
        switch (position) {
            case 0:
                TabPageConstructionItemFragment tab1 = new TabPageConstructionItemFragment();
                tab1.setScheduleType("0");
                tab1.setLoadType(0);
                tab1.setTitle(context.getString(R.string.ThiCongTitle));
                return tab1;

            case 1:
                TabPageConstructionItemFragment tab2 = new TabPageConstructionItemFragment();
                tab2.setScheduleType("1");
                tab2.setLoadType(1);
                tab2.setTitle(context.getString(R.string.GiamSatTitle));
                return tab2;
            case 2:
                TabPageConstructionItemFragment tab3 = new TabPageConstructionItemFragment();
                tab3.setMonthPlan(true);
                tab3.setScheduleType("2");
                tab3.setLoadType(2);
                tab3.setTitle(context.getString(R.string.KHThangTitle));
                return tab3;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = App.getContext().getString(R.string.progress1);
                break;
            case 1:
                title = App.getContext().getString(R.string.supervising);
                break;
            case 2:
                title = App.getContext().getString(R.string.month_plan);
                break;
        }

        return title;
    }
}
