package com.viettel.construction.screens.wrac.ab;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.viettel.construction.R;
import com.viettel.construction.common.App;

public class LichSuVTTBAdapter extends FragmentStatePagerAdapter {

    public LichSuVTTBFragment.ITabConstructionChanged tabDelegate;

    public LichSuVTTBAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        TabLichSuVTTBBaseChart frg= position == 0 ? new TiepNhanVTTBChartFragment() : new BanGiaoVTTBChartFragment();
        tabDelegate = frg;
        return frg;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = App.getContext().getString(R.string.TiepNhan);
                break;
            case 1:
                title = App.getContext().getString(R.string.BanGiao);
                break;
        }
        return title;
    }
}
