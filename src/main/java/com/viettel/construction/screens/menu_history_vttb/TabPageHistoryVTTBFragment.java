package com.viettel.construction.screens.menu_history_vttb;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viettel.construction.R;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabPageHistoryVTTBFragment extends Fragment {

    @BindView(R.id.view_pager)
    ViewPager pager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private TabPageHistoryVTTBAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_construction_management, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapter = new TabPageHistoryVTTBAdapter(getFragmentManager());
        pager.setOffscreenPageLimit(2);
        pager.setAdapter(adapter);

        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);

        KeyboardVisibilityEvent.setEventListener(getActivity(), (isOpen) -> {
            tabLayout.setVisibility(isOpen ? View.GONE : View.VISIBLE);
        });

    }

}
