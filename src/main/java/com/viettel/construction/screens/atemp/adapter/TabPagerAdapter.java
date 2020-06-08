package com.viettel.construction.screens.atemp.adapter;


import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.res.ColorStateList;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.viettel.construction.R;


public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater layoutInflater;
    private TabLayout tabLayout;

    public TabPagerAdapter(FragmentManager fm, Context context, TabLayout tabLayout) {
        super(fm);
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
        this.tabLayout = tabLayout;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public View getTabView(int position) {
        View view = null;
        int resId = R.layout.content_supplies;
        if (position != 1) {
            resId = R.layout.content_devices;
        }
        view = layoutInflater.inflate(resId, null);
        // Set title
        TextView txtTitle = (TextView) view.findViewById(android.R.id.text1);
        txtTitle.setText(mFragmentTitleList.get(position));
        ColorStateList textColor = tabLayout.getTabTextColors();
        txtTitle.setTextColor(textColor);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }
}
