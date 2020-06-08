package com.viettel.construction.screens.atemp.other;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.construction.screens.atemp.adapter.SupplierAdapterWithHeader;
import com.viettel.construction.server.service.IOnRequestListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import com.viettel.construction.R;
import com.viettel.construction.common.FilterableSection;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.MerEntityDTO;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.StockTransResponse;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.appbase.BaseChartFragment;

/**
 * Created by Ramona on 3/14/2018.
 */
public class WatchPersonalDebtChartFragment extends BaseChartFragment
         {
    @BindView(R.id.rv_list_material)
    RecyclerView mRVListMaterial;
    @BindView(R.id.btn_back)
    TextView mTVBtnBack;
    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    List<MerEntityDTO> mList = new ArrayList<>();
    List<MerEntityDTO> mListSupplierA = new ArrayList<>();
    List<MerEntityDTO> mListSupplierB = new ArrayList<>();
    List<String> mListConstructionCode = new ArrayList<>();
    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watch_personal_debt, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRVListMaterial.setLayoutManager(linearLayoutManager);

        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.all)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.supplier_a)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.supplier_b)));
        getListDebt();
        tabLayoutListener();
        setupEdt();
    }

    private void setupEdt() {
        mEdtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Send the user message
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mEdtSearch.getWindowToken(), 0);
                    handled = true;
                }
                return handled;
            }
        });
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    for (Section section : mSectionedRecyclerViewAdapter.getSectionsMap().values()) {
                        if (section instanceof FilterableSection) {
                            ((FilterableSection) section).filter(editable.toString());
                        }
                    }
                    mSectionedRecyclerViewAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void tabLayoutListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        selectItem(mList);
                        mEdtSearch.setText(mEdtSearch.getText());
                        break;
                    case 1:
                        selectItem(mListSupplierA);
                        mEdtSearch.setText(mEdtSearch.getText());
                        break;
                    case 2:
                        selectItem(mListSupplierB);
                        mEdtSearch.setText(mEdtSearch.getText());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @OnClick(R.id.img_back)
    public void onClickBack() {
        getFragmentManager().popBackStack();
    }


    private void getListDebt() {
        ApiManager.getInstance().getListDebt(StockTransResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    StockTransResponse response = StockTransResponse.class.cast(result);
                    ResultInfo resultInfo = response.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        // do something here
                        Log.e("isOK", "OK");
                        mList.clear();
                        mListSupplierA.clear();
                        mListSupplierB.clear();
                        mList.addAll(response.getLstMerEntity());
                        for (MerEntityDTO merEntityDTO : mList) {
                            if (merEntityDTO.getStockType().contains("A")) {
                                mListSupplierA.add(merEntityDTO);
                            } else {
                                mListSupplierB.add(merEntityDTO);
                            }
                        }
                        // list name
                        selectItem(mList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {

            }
        });
    }

    private void selectItem(List<MerEntityDTO> list) {
        mSectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
        mListConstructionCode.clear();
        for (MerEntityDTO entity : list) {
            if (entity.getConstructionCode() != null && !mListConstructionCode.contains(entity.getConstructionCode())) {
                mListConstructionCode.add(entity.getConstructionCode());
            }
        }
        for (String name : mListConstructionCode) {
            List<MerEntityDTO> temp = filterListByConstructionName(name, list);
            if (temp.size() > 0) {
                mSectionedRecyclerViewAdapter.addSection(new SupplierAdapterWithHeader(getActivity(), name, temp));
            }
        }
        mRVListMaterial.setAdapter(mSectionedRecyclerViewAdapter);
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();

    }

    private List<MerEntityDTO> filterListByConstructionName(String s, List<MerEntityDTO> list) {
        List<MerEntityDTO> resultList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getConstructionCode() != null && list.get(i).getConstructionCode().contains(s)) {
                resultList.add(list.get(i));
            }
        }
        return resultList;
    }
}
