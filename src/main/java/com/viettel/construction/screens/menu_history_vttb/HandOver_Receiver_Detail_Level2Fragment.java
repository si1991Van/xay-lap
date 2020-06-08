package com.viettel.construction.screens.menu_history_vttb;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.appbase.BaseChartFragment;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.history.HandOverHistoryDTORespone;
import com.viettel.construction.model.api.history.StTransactionDTO;
import com.viettel.construction.server.api.ApiManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Lịch sử bàn giao VTTB
 */
public class HandOver_Receiver_Detail_Level2Fragment extends BaseChartFragment implements HandOver_Receiver_Detail_Level2Adapter.OnClickHistory {
    @BindView(R.id.tv_title_history_handover_material)
    TextView mTVTitle;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.rcv_history)
    RecyclerView rcvHistory;
    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    @BindView(R.id.btn_back_pxk)
    ImageView btnBack;
    @BindView(R.id.iv_filter_cv)
    ImageView btnFilter;
    private HandOver_Receiver_Detail_Level2Adapter handOverReceiverDetailLevel2Adapter;

    private List<StTransactionDTO> listMain = new ArrayList<>();
    private List<StTransactionDTO> listBackup = new ArrayList<>();
    private List<StTransactionDTO> listBackupForSearch = new ArrayList<>();
    private List<StTransactionDTO> listReceived = new ArrayList<>();
    private List<StTransactionDTO> listHandOver = new ArrayList<>();
    private boolean isHandOver = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setupEdt();
        setupRecyclerView();
        clearData();
        setTextTitle();
        retainTab();
        loadData();
    }

    private void retainTab() {
        TabLayout.Tab tab;
        switch (App.getInstance().returnTabIndexHistoryHandOver()) {
            case 0:
                tab = mTabLayout.getTabAt(0);
                tab.select();
                break;
            case 1:
                tab = mTabLayout.getTabAt(1);
                tab.select();
                break;
            default:
                break;
        }
    }

    private void setupEdt() {
        mEdtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
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

            // mã phiếu và mã công trình
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (listBackup.size() != 0) {
                        listMain.clear();
                        String input = StringUtil.removeAccentStr(editable.toString()).toLowerCase().trim();
                        if (input.length() != 0) {
                            for (StTransactionDTO dto : listBackupForSearch) {
                                String billCode, constructionCode;
                                if (dto.getStockTransCode() != null) {
                                    billCode = dto.getStockTransCode().toLowerCase();
                                } else {
                                    billCode = "";
                                }
                                if (dto.getStockTransConstructionCode() != null) {
                                    constructionCode = dto.getStockTransConstructionCode().toLowerCase();
                                } else {
                                    constructionCode = "";
                                }
                                if (billCode.contains(input) || constructionCode.contains(input))
                                    listMain.add(dto);
                            }
                        } else {
                            listMain.addAll(listBackupForSearch);
                        }
                        handOverReceiverDetailLevel2Adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setTextTitle() {
        if (listMain.size() != 0) {
            mTVTitle.setText(getString(R.string.history_handover_material, listMain.size() + ""));
        } else {
            mTVTitle.setText(getString(R.string.history_handover_material, "0"));
        }
    }

    private void setupRecyclerView() {
        handOverReceiverDetailLevel2Adapter = new HandOver_Receiver_Detail_Level2Adapter(getContext(), listMain, this);
        rcvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvHistory.setAdapter(handOverReceiverDetailLevel2Adapter);

    }

    private void initView() {
        mTabLayout.addTab(mTabLayout.newTab().setText(getActivity().getString(R.string.receive)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getActivity().getString(R.string.handover)));
        tabLayoutListener();
    }

    @OnClick(R.id.iv_filter_cv)
    public void onClickFilter() {
        PopupMenu popup = new PopupMenu(getActivity(), btnFilter);
        popup.getMenuInflater()
                .inflate(R.menu.menu_history, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.all_history:
                        if (listBackup.size() != 0) {
                            listMain.clear();
                            listBackupForSearch.clear();
                            listMain.addAll(listBackup);
                            listBackupForSearch.addAll(listBackup);
                            setTextTitle();
                            handOverReceiverDetailLevel2Adapter.notifyDataSetChanged();
                        } else {
                            listMain.clear();
                            listBackupForSearch.clear();
                            handOverReceiverDetailLevel2Adapter.notifyDataSetChanged();
                            setTextTitle();
                        }
                        break;
                    case R.id.wait_history:
                        if (listBackup.size() != 0) {
                            filterByStatus("0");
                            setTextTitle();
                        } else {
                            listMain.clear();
                            listBackupForSearch.clear();
                            handOverReceiverDetailLevel2Adapter.notifyDataSetChanged();
                            setTextTitle();
                        }
                        break;
                    case R.id.received_history:
                        if (listBackup.size() != 0) {
                            filterByStatus("1");
                            setTextTitle();
                        } else {
                            listMain.clear();
                            listBackupForSearch.clear();
                            handOverReceiverDetailLevel2Adapter.notifyDataSetChanged();
                            setTextTitle();
                        }
                        break;
                    case R.id.refuse_history:
                        if (listBackup.size() != 0) {
                            filterByStatus("2");
                            setTextTitle();
                        } else {
                            listMain.clear();
                            listBackupForSearch.clear();
                            handOverReceiverDetailLevel2Adapter.notifyDataSetChanged();
                            setTextTitle();
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    private void filterByStatus(String status) {
        listMain.clear();
        listBackupForSearch.clear();
        for (StTransactionDTO dto : listBackup) {
            String temp;
            if (dto.getConfirm() != null) {
                temp = dto.getConfirm();
            } else {
                temp = "";
            }
            if (temp.contains(status)) {
                listMain.add(dto);
            }
        }
        listBackupForSearch.addAll(listMain);
        handOverReceiverDetailLevel2Adapter.notifyDataSetChanged();
    }

    private void controlList(List<StTransactionDTO> list) {
        listMain.clear();
        listBackup.clear();
        listBackupForSearch.clear();
        listMain.addAll(list);
        listBackup.addAll(list);
        listBackupForSearch.addAll(list);
        handOverReceiverDetailLevel2Adapter.notifyDataSetChanged();
    }

    private void clearData() {
        listMain.clear();
        listBackup.clear();
        listBackupForSearch.clear();
        listHandOver.clear();
        listReceived.clear();
    }

    private void loadData() {
        listReceived = new ArrayList<>();
        listHandOver = new ArrayList<>();

        ApiManager.getInstance().getListHistoryHandOver(HandOverHistoryDTORespone.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    HandOverHistoryDTORespone respone = HandOverHistoryDTORespone.class.cast(result);
                    if (respone.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        listReceived.clear();
                        listHandOver.clear();
                        listReceived.addAll(respone.getListStTransactionReceivePagesDTO());
                        listHandOver.addAll(respone.getListStTransactionHandoverPagesDTO());
                        switch (App.getInstance().returnTabIndexHistoryHandOver()) {
                            case 0:
                                handOverReceiverDetailLevel2Adapter.setDataForBooleanVariable(true);
                                controlList(listReceived);
                                setTextTitle();
                                break;
                            case 1:
                                handOverReceiverDetailLevel2Adapter.setDataForBooleanVariable(false);
                                controlList(listHandOver);
                                setTextTitle();
                                break;
                        }
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(getActivity(), getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(StTransactionDTO stTransactionDTO) {
        Fragment frag = new HandOver_Receiver_Detail_Level1Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("StTransactionDTO", stTransactionDTO);
        frag.setArguments(bundle);
        commitChange(frag);
    }


    private void tabLayoutListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        App.getInstance().setTabIndexHistoryHandOver(0);
                        if (listReceived.size() != 0) {
                            handOverReceiverDetailLevel2Adapter.setDataForBooleanVariable(true);
                            controlList(listReceived);
                            setTextTitle();
                        } else {
                            listMain.clear();
                            listBackup.clear();
                            listBackupForSearch.clear();
                            handOverReceiverDetailLevel2Adapter.notifyDataSetChanged();
                        }
                        break;
                    case 1:
                        App.getInstance().setTabIndexHistoryHandOver(1);
                        if (listHandOver.size() != 0) {
                            handOverReceiverDetailLevel2Adapter.setDataForBooleanVariable(false);
                            controlList(listHandOver);
                            setTextTitle();
                        } else {
                            listMain.clear();
                            listBackup.clear();
                            listBackupForSearch.clear();
                            handOverReceiverDetailLevel2Adapter.notifyDataSetChanged();
                        }
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

    @OnClick(R.id.btn_back_pxk)
    public void onBack() {
        getFragmentManager().popBackStack();
    }
}
