package com.viettel.construction.screens.menu_bgmb;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.viettel.construction.screens.menu_ex_warehouse.TabPageExWareHouseFragment;

public class TabPageBGMBAdapter extends FragmentStatePagerAdapter {

    //create by THANGTV24 on 11/01/2019

    public TabPageExWareHouseFragment.ITabDeliveryChanged tabDelegate;

    public TabPageBGMBAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            default: {
                TabPageBgmbConstruction_ItemFragment aContructionFragment = new TabPageBgmbConstruction_ItemFragment();
                aContructionFragment.setABill(true);
                return aContructionFragment;
            }


        }

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
                title = "Đã Nhận";
                break;
            case 1:
                title = "Chưa Nhận";
                break;
        }

        return title;
    }
}
