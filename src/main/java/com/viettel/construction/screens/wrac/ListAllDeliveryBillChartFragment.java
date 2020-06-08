package com.viettel.construction.screens.wrac;

import android.app.Activity;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.appbase.BaseChartFragment;
import com.viettel.construction.screens.menu_ex_warehouse.ExWarehouse_Detail_Fragment;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;
import com.viettel.construction.server.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.StockTransResponse;
import com.viettel.construction.model.api.SynStockTransDTO;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.custom.dialog.CustomProgress;

/***
 * Hiển thị danh sách phiếu xuất kho gồm 2 tab A cấp và B cấp
 */

public class ListAllDeliveryBillChartFragment extends BaseChartFragment implements
        BillAdapter.onBillItemClick {
    @BindView(R.id.supplier_a)
    Button mBtnSupplierA;
    @BindView(R.id.supplier_b)
    Button mBtnSupplierB;
    @BindView(R.id.btn_back_pxk)
    ImageView btnBack;
    @BindView(R.id.iv_filter_cv)
    ImageView btnFilter;
    @BindView(R.id.rcv_bill)
    RecyclerView rcvBill;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    @BindView(R.id.tv_title_delivery_bill)
    TextView mTVTitle;
    @BindView(R.id.list_delivery_bill_dialog)
    CustomProgress customProgress;
    private List<SynStockTransDTO> listBill = new ArrayList<>();
    private List<SynStockTransDTO> listBillBackup = new ArrayList<>();
    private List<SynStockTransDTO> listBillBackupForSearch = new ArrayList<>();
    private List<SynStockTransDTO> listBillSupplierA = new ArrayList<>();
    private List<SynStockTransDTO> listBillSupplierB = new ArrayList<>();
    private BillAdapter adapter;
    private boolean mIsSupplierB = true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_delivery_bill_all, container, false);
        ButterKnife.bind(this, view);
        customProgress.setLoading(false);
        mTabLayout.addTab(mTabLayout.newTab().setText(getActivity().getString(R.string.supplier_a)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getActivity().getString(R.string.supplier_b)));
        mEdtSearch.setText("");
        if (listBill.size() != 0) {
            mTVTitle.setText(getString(R.string.delivery_bill_1, listBill.size()));
        } else {
            mTVTitle.setText(getString(R.string.delivery_bill_1, 0));
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listBill.clear();
        adapter = new BillAdapter(getActivity(), listBill, this);
        rcvBill.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvBill.setAdapter(adapter);
        mIsSupplierB = App.getInstance().isNeedUpdateBtnSupplier();
        if (mIsSupplierB) {
            TabLayout.Tab tab = mTabLayout.getTabAt(1);
            tab.select();
        } else {
            TabLayout.Tab tab = mTabLayout.getTabAt(0);
            tab.select();
        }
        tabLayoutListener();
        setupEdt();
//        App.getInstance().setNeedUpdateAfterConfirmBill(true);
//        App.getInstance().setNeedUpdateAfterRejectedBill(true);
//        boolean isNeedUpdate = App.getInstance().isNeedUpdateAfterConfirmBill();
//        boolean isNeedUpdate1 = App.getInstance().isNeedUpdateAfterRejectedBill();
//        if (isNeedUpdate || isNeedUpdate1)
        if (App.getInstance().isNeedCallGetDataFromDeliveryBill())
            getData();
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
                    if (listBillBackup.size() != 0) {
                        listBill.clear();
                        String input = StringUtil.removeAccentStr(editable.toString()).trim().toUpperCase();
                        if (input.length() != 0) {
                            for (SynStockTransDTO entity : listBillBackupForSearch) {
                                String oderCode, code;
                                if (entity.getOrderCode() != null) {
                                    oderCode = entity.getOrderCode().trim().toUpperCase();
                                } else {
                                    oderCode = "";
                                }
                                if (entity.getCode() != null) {
                                    code = entity.getCode().trim().toUpperCase();
                                } else {
                                    code = "";
                                }
                                if (oderCode.contains(input) || code.contains(input)) {
                                    listBill.add(entity);
                                }
                            }
                        } else {
                            listBill.addAll(listBillBackupForSearch);
                        }
                    }
                    adapter.notifyDataSetChanged();
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
//                        String temp = mEdtSearch.getText()+"";
                        mIsSupplierB = false;
                        if (listBillSupplierA.size() != 0) {
                            controlList(listBillSupplierA);
                            mTVTitle.setText(getString(R.string.delivery_bill_1, listBillSupplierA.size()));
                            rcvBill.scrollToPosition(0);
                        } else {
                            listBill.clear();
                            listBillBackup.clear();
                            listBillBackupForSearch.clear();
                            mTVTitle.setText(getString(R.string.delivery_bill_1, 0));
                            adapter.notifyDataSetChanged();
                        }
                        mEdtSearch.setText(mEdtSearch.getText() + "");
                        mEdtSearch.setSelection(mEdtSearch.getText().length());
                        App.getInstance().setNeedUpdateBtnSupplier(false);
                        break;
                    case 1:
                        mIsSupplierB = true;
                        if (listBillSupplierB.size() != 0) {
                            controlList(listBillSupplierB);
                            mTVTitle.setText(getString(R.string.delivery_bill_1, listBillSupplierB.size()));
                            rcvBill.scrollToPosition(0);
                        } else {
                            listBill.clear();
                            listBillBackup.clear();
                            listBillBackupForSearch.clear();
                            mTVTitle.setText(getString(R.string.delivery_bill_1, 0));
                            adapter.notifyDataSetChanged();
                        }
                        mEdtSearch.setText(mEdtSearch.getText() + "");
                        mEdtSearch.setSelection(mEdtSearch.getText().length());
                        App.getInstance().setNeedUpdateBtnSupplier(true);
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
    public void onClick() {
        App.getInstance().setNeedUpdateBtnSupplier(false);
        getFragmentManager().popBackStack();
    }



    @OnClick(R.id.iv_filter_cv)
    public void onClickFilter() {
        PopupMenu popup = new PopupMenu(getActivity(), btnFilter);
        popup.getMenuInflater()
                .inflate(R.menu.menu_delivery_bill_filter_a_bill, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                try {
                    switch (menuItem.getItemId()) {
                        case R.id.all:
                            mEdtSearch.setText("");
                            if (listBillBackup.size() != 0) {
                                listBill.clear();
                                listBillBackupForSearch.clear();
                                listBill.addAll(listBillBackup);
                                listBillBackupForSearch.addAll(listBillBackup);
                                mTVTitle.setText(getString(R.string.delivery_bill_1, listBill.size()));
                                adapter.notifyDataSetChanged();
                                rcvBill.scrollToPosition(0);
                            } else {
                                listBill.clear();
                                listBillBackupForSearch.clear();
                                adapter.notifyDataSetChanged();
                                mTVTitle.setText(getString(R.string.delivery_bill_1, 0));
                            }
                            break;
                        case R.id.received:
                            mEdtSearch.setText("");
                            if (listBillBackup.size() != 0) {
                                filterByStatus("1",false);
                                rcvBill.scrollToPosition(0);
                                mTVTitle.setText(getString(R.string.delivery_bill_1, listBill.size()));
                            } else {
                                listBill.clear();
                                listBillBackupForSearch.clear();
                                adapter.notifyDataSetChanged();
                                mTVTitle.setText(getString(R.string.delivery_bill_1, 0));
                            }
                            break;
                        case R.id.wait_for_receive:
                            mEdtSearch.setText("");
                            if (listBillBackup.size() != 0) {
                                filterByStatus("0",false);
                                rcvBill.scrollToPosition(0);
                                mTVTitle.setText(getString(R.string.delivery_bill_1, listBill.size()));
                            } else {
                                listBill.clear();
                                listBillBackupForSearch.clear();
                                adapter.notifyDataSetChanged();
                                mTVTitle.setText(getString(R.string.delivery_bill_1, 0));
                            }
                            break;
                        case R.id.rejected:
                            mEdtSearch.setText("");
                            if (listBillBackup.size() != 0) {
                                filterByStatus("2",false);
                                rcvBill.scrollToPosition(0);
                                mTVTitle.setText(getString(R.string.delivery_bill_1, listBill.size()));
                            } else {
                                listBill.clear();
                                listBillBackupForSearch.clear();
                                adapter.notifyDataSetChanged();
                                mTVTitle.setText(getString(R.string.delivery_bill_1, 0));
                            }
                            break;
                            //cho xac nhan : 0
                            //da xac nhan : 1
                            //Tu choi xac nhan : 2
                        case R.id.wait_confirm:
                            mEdtSearch.setText("");
                            if (listBillBackup.size() != 0) {
                                filterByStatus("0",true);
                                rcvBill.scrollToPosition(0);
                                mTVTitle.setText(getString(R.string.delivery_bill_1, listBill.size()));
                            } else {
                                listBill.clear();
                                listBillBackupForSearch.clear();
                                adapter.notifyDataSetChanged();
                                mTVTitle.setText(getString(R.string.delivery_bill_1, 0));
                            }
                            break;
                        case R.id.confirm:
                            mEdtSearch.setText("");
                            if (listBillBackup.size() != 0) {
                                filterByStatus("1",true);
                                rcvBill.scrollToPosition(0);
                                mTVTitle.setText(getString(R.string.delivery_bill_1, listBill.size()));
                            } else {
                                listBill.clear();
                                listBillBackupForSearch.clear();
                                adapter.notifyDataSetChanged();
                                mTVTitle.setText(getString(R.string.delivery_bill_1, 0));
                            }
                            break;
                        case R.id.dont_confirm:
                            mEdtSearch.setText("");
                            if (listBillBackup.size() != 0) {
                                filterByStatus("2",true);
                                rcvBill.scrollToPosition(0);
                                mTVTitle.setText(getString(R.string.delivery_bill_1, listBill.size()));
                            } else {
                                listBill.clear();
                                listBillBackupForSearch.clear();
                                adapter.notifyDataSetChanged();
                                mTVTitle.setText(getString(R.string.delivery_bill_1, 0));
                            }
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
        popup.show();
    }
    //state
    private void filterByStatus(String s,Boolean isState) {
        listBill.clear();
        listBillBackupForSearch.clear();
        if (isState){
            for (SynStockTransDTO entity : listBillBackup) {
                if (entity.getState() != null) {
                    String status = entity.getState();
                    if (status.contains(s)) {
                        listBill.add(entity);
                    }
                }
            }
        }else {
            for (SynStockTransDTO entity : listBillBackup) {
                if (entity.getConfirm() != null) {
                    String status = entity.getConfirm();
                    if (status.contains(s)) {
                        listBill.add(entity);
                    }
                }
            }
        }

        listBillBackupForSearch.addAll(listBill);
        adapter.notifyDataSetChanged();
    }

    private void getData() {
        customProgress.setLoading(true);
        ApiManager.getInstance().getListSynStockTransDTO(StockTransResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    StockTransResponse stockTransResponse = StockTransResponse.class.cast(result);
                    ResultInfo resultInfo = stockTransResponse.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        listBillSupplierA.clear();
                        listBillSupplierB.clear();
                        if (stockTransResponse.getLstSynStockTransDto().size() != 0) {
                            List<SynStockTransDTO> list = stockTransResponse.getLstSynStockTransDto();
                            Log.e("All", list.size() + "");
                            for (SynStockTransDTO synStockTransDTO : list) {
                                String stockType = synStockTransDTO.getStockType();
                                if (stockType.contains("A")) {
                                    listBillSupplierA.add(synStockTransDTO);
                                } else {
                                    listBillSupplierB.add(synStockTransDTO);
                                }
                            }
                            if (!mIsSupplierB) {
                                controlList(listBillSupplierA);
                                Activity activity = getActivity();
                                if (activity != null && isAdded()) {
                                    mTVTitle.setText(getString(R.string.delivery_bill_1, listBillSupplierA.size()));
                                }
                            } else {
                                controlList(listBillSupplierB);
                                Activity activity = getActivity();
                                if (activity != null && isAdded()) {
                                    mTVTitle.setText(getString(R.string.delivery_bill_1, listBillSupplierB.size()));
                                }
                            }
                            mEdtSearch.setText(mEdtSearch.getText() + "");
                            mEdtSearch.setSelection(mEdtSearch.getText().length());
                        }
                        customProgress.setLoading(false);
                    }else {
                        customProgress.setLoading(false);
                        Toast.makeText(getActivity(), resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                customProgress.setLoading(false);
                Toast.makeText(getActivity(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void controlList(List<SynStockTransDTO> list) {
        listBill.clear();
        listBillBackup.clear();
        listBillBackupForSearch.clear();

        listBill.addAll(list);
        listBillBackup.addAll(list);
        listBillBackupForSearch.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void needRetainView() {
        if (mIsSupplierB) {
            App.getInstance().setNeedUpdateBtnSupplier(true);
        } else {
            App.getInstance().setNeedUpdateBtnSupplier(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (App.getInstance().isNeedUpdateAfterConfirmBill()) {
            App.getInstance().setNeedUpdateAfterConfirmBill(false);
            mEdtSearch.setText("");
            getData();
        }
        if (App.getInstance().isNeedUpdateAfterRejectedBill()) {
            App.getInstance().setNeedUpdateAfterRejectedBill(false);
            mEdtSearch.setText("");
            getData();
        }
    }

    @Override
    public void onClickBill(SynStockTransDTO odt) {
        needRetainView();
        App.getInstance().setNeedCallGetDataFromDeliveryBill(false);
        Fragment fragment = new ExWarehouse_Detail_Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(VConstant.BUNDLE_KEY_BILL_ENTITY, odt);
        fragment.setArguments(bundle);
        commitChange(fragment,true);
    }
}
