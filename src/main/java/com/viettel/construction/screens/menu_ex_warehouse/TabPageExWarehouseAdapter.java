package com.viettel.construction.screens.menu_ex_warehouse;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabPageExWarehouseAdapter extends FragmentStatePagerAdapter {


    public TabPageExWareHouseFragment.ITabDeliveryChanged tabDelegate;

    public TabPageExWarehouseAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            default: {
                TabPageExWarehouse_ItemFragment_Paging aBillFragment = new TabPageExWarehouse_ItemFragment_Paging();
                aBillFragment.setAbill(true);
                /*TabPageExWarehouse_ItemFragment aBillFragment = new TabPageExWarehouse_ItemFragment();
                aBillFragment.setABill(true);*/
                return aBillFragment;
            }
            case 1: {
                TabPageExWarehouse_ItemFragment_Paging bBillFragment = new TabPageExWarehouse_ItemFragment_Paging();
                bBillFragment.setAbill(false);
                /*TabPageExWarehouse_ItemFragment bBillFragment = new TabPageExWarehouse_ItemFragment();
                bBillFragment.setABill(false);*/
                return bBillFragment;
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
                title = "A Cấp";
                break;
            case 1:
                title = "B Cấp";
                break;
        }

        return title;
    }
}
