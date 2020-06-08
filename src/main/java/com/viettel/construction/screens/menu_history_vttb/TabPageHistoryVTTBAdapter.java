package com.viettel.construction.screens.menu_history_vttb;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabPageHistoryVTTBAdapter extends FragmentStatePagerAdapter {


    public TabPageHistoryVTTBAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            default: {
                TabItemReceiver_HandOverFragment receiver = new TabItemReceiver_HandOverFragment();
                receiver.setReceiver(true);
                return receiver;
            }
            case 1: {
                TabItemReceiver_HandOverFragment handOver = new TabItemReceiver_HandOverFragment();
                handOver.setReceiver(false);
                return handOver;
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
                title = "Tiếp nhận";
                break;
            case 1:
                title = "Bàn giao";
                break;
        }

        return title;
    }
}