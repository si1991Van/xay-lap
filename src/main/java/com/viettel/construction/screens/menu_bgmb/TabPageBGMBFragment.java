package com.viettel.construction.screens.menu_bgmb;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viettel.construction.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabPageBGMBFragment extends Fragment {

    @BindView(R.id.view_pager)
    ViewPager pager;

//    @BindView(R.id.tab_layout)
//    TabLayout tabLayout;

    private TabPageBGMBAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_bgmb_construction_management, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapter = new TabPageBGMBAdapter(getFragmentManager());
        pager.setOffscreenPageLimit(1);
        pager.setAdapter(adapter);

//        tabLayout.setupWithViewPager(pager);
//        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setTabsFromPagerAdapter(adapter);
//
//        KeyboardVisibilityEvent.setEventListener(getActivity(), (isOpen) -> {
//            tabLayout.setVisibility(isOpen ? View.GONE : View.VISIBLE);
//        });
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if (adapter.tabDelegate != null)
//                    adapter.tabDelegate.tabChanged();
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

    public interface ITabConstructionChanged {
        void tabChanged(int position);
    }
}
