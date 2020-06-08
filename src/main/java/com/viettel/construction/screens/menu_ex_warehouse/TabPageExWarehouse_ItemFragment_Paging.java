package com.viettel.construction.screens.menu_ex_warehouse;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.viettel.construction.R;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.databinding.ABillBinding;
import com.viettel.construction.model.api.SynStockTransDTO;
import com.viettel.construction.screens.home.HomeCameraActivity;
import com.viettel.construction.viewmodel.ListPxkViewModel;
import com.viettel.construction.viewmodel.ParaFilterPaging;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;


// Lưu ý phần này sử dụng paging, không thể viết chung vào base fragmet được. (@@)
public class TabPageExWarehouse_ItemFragment_Paging extends Fragment implements TabPageExWarehouse_ItemAdapter_Paging.IItemRecyclerviewClick {

    @Nullable
    @BindView(R.id.imgFilter)
    ImageView imgFilter;

    @Nullable
    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @Nullable
    @BindView(R.id.imgBack)
    ImageView imgBack;

    @Nullable
    @BindView(R.id.refresh_layout)
    RefreshLayout refreshLayout;

    private boolean mIsABill;

    private Handler mSearchHandler;

    private TabPageExWarehouse_ItemAdapter_Paging mAdapter;

    private final long SEARCH_TRIGGER_DELAY_IN_MS = 600;
    private final int TRIGGER_SERACH = 1;
    private final int REQUEST_CODE = 1;
    private String mTextSearch;
    private String mSearchOverDate;
    private long mSearchConfirm;
    private long mSearchState;

    private ABillBinding mABillBinding;

    private ListPxkViewModel mController;

    private int mTitleType = VConstant.PxkTitleType.TYPE_TAT_CA;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    public void setAbill(boolean value) {
        mIsABill = value;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initController();
        if (getActivity() != null)
            getActivity().registerReceiver(receiverReLoading,
                    new IntentFilter(ParramConstant.ExWarehouseReload));
    }

    private void initController() {
        mController = ViewModelProviders.of(this).get(ListPxkViewModel.class);
        mAdapter = new TabPageExWarehouse_ItemAdapter_Paging(getContext(), SynStockTransDTO.DIFF_CALLBACK);
        mAdapter.setmItemClick(this);
        mSearchHandler = new Handler();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TRIGGER_SERACH) {
                startSearch();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setTitle(String title) {
        txtHeader.setText(title);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mABillBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_abill_with_paging, container, false);
        ButterKnife.bind(this, mABillBinding.getRoot());
        initView();
        return mABillBinding.getRoot();

    }

    // Init Start
    private void initView() {
        mABillBinding.rcvData.setAdapter(mAdapter);
        mABillBinding.rcvData.setLayoutManager(new LinearLayoutManager(getContext()));
        showListData(false, false, true);
        mController.loadData(createParaFilter(true, false, false, false, mTextSearch, -1, -1));
        initSearch();
        initBanGiao();
        initReload();
    }

    private void initReload() {
        if (refreshLayout != null) {
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    refreshLayout.autoRefresh();
                    replateDataForSearchAndFilter(createParaFilter(true, false, false, false, mTextSearch, 0, -1));
                }
            });
        }
    }

    public void initBanGiao(){
        mABillBinding.lnBanGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mController.getListSynStockSelected() != null && mController.getListSynStockSelected().size() > 0){
                    Intent intent = new Intent(getActivity(), ConfirmDeliveryMultiBillCameraActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list_selected_bill",mController.getListSynStockSelected());
                    intent.putExtras(bundle);
                    intent.putExtra(ConfirmDeliveryMultiBillCameraActivity.KEY_BG_WITHOUT_CONFIRM, true);
                    startActivityForResult(intent, REQUEST_CODE);
                }
//                Toast.makeText(getActivity(), String.valueOf(mController.getListSynStockSelected().get(0).code), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Init SearchBox
    private void initSearch() {
        mABillBinding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTextSearch = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mABillBinding.edtSearch.isFocused()){
                    handler.removeMessages(TRIGGER_SERACH);
                    handler.sendEmptyMessageDelayed(TRIGGER_SERACH, SEARCH_TRIGGER_DELAY_IN_MS);
                }
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observerDataPxk();
        observerTotalItem();
    }

    public int getMenuID() {
        return mIsABill ? R.menu.menu_delivery_bill_filter_a_bill : R.menu.menu_delivery_bill_filter_b_bill;
    }

    @Optional
    @OnClick(R.id.imgFilter)
    public void onClickFilter() {
        PopupMenu popup = new PopupMenu(getActivity(), imgFilter);
        popup.getMenuInflater()
                .inflate(getMenuID(), popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ParaFilterPaging para = null;
                mABillBinding.edtSearch.clearFocus();
                mABillBinding.edtSearch.setText("");
                switch (item.getItemId()) {
                    case R.id.received:
                        para = createParaFilter(false, false, true, false, null, 1, -1);
                        mTitleType = VConstant.PxkTitleType.TYPE_DA_TIEP_NHAN;
                        break;
                    case R.id.wait_for_receive:
                        para = createParaFilter(false, false, true, false, null, 0, -1);
                        mTitleType = VConstant.PxkTitleType.TYPE_CHO_TIEP_NHAN;
                        break;
                    case R.id.rejected:
                        para = createParaFilter(false, false, true, false, null, 2, -1);
                        mTitleType = VConstant.PxkTitleType.TYPE_DA_TU_CHOI;
                        break;
                    //cho xac nhan : 0
                    //da xac nhan : 1
                    //Tu choi xac nhan : 2
                    case R.id.wait_confirm:
                        para = createParaFilter(false, false, false, false, null, -1, 0);
                        mTitleType = VConstant.PxkTitleType.TYPE_CHO_XAC_NHAN;
                        break;
                    case R.id.confirm:
                        para = createParaFilter(false, false, false, false, null, -1, 1);
                        mTitleType = VConstant.PxkTitleType.TYPE_DA_XAC_NHAN;
                        break;
                    case R.id.dont_confirm:
                        para = createParaFilter(false, false, false, false, null, -1, 2);
                        mTitleType = VConstant.PxkTitleType.TYPE_TU_CHOI_XAC_NHAN;
                        break;
                    case R.id.over_date_kpi:
                        para = createParaFilter(false, false, false, true, null, -1, -1);
                        mTitleType = VConstant.PxkTitleType.TYPE_QUA_HAN;
                        break;
                    default:
                        para = createParaFilter(true, false, false, false, mTextSearch, 0, -1);
                        mTitleType = VConstant.PxkTitleType.TYPE_TAT_CA;
                        break;
                }

                replateDataForSearchAndFilter(para);
                return true;
            }
        });
        popup.show();
    }

    // Start request server again
    private void replateDataForSearchAndFilter(ParaFilterPaging para) {
        showListData(false, false, true);
        mController.replaceSubscription(getViewLifecycleOwner(), para);
        observerDataPxk();
        observerTotalItem();
    }

    // Show hide listview, dialog process
    private void showListData(boolean isHaveData, boolean isNoData, boolean isLoading) {
        mABillBinding.progressBar.setLoading(isLoading ? true : false);
        mABillBinding.txtNoData.setVisibility((isNoData && !isLoading) ? View.VISIBLE : View.GONE);
    }


    private ParaFilterPaging createParaFilter(boolean isDefault, boolean isSearch, boolean isState, boolean isOverDateKPI,
                                              String textSearch, long confirm, long state) {
        ParaFilterPaging para = new ParaFilterPaging();
        para.setABill(ismIsABill());
        if (isDefault) {
            mSearchState = -1;
            mSearchConfirm = -1;
            mSearchOverDate = null;
            para.setDefault(true);
            return para;
        }
        // Abill
        para.setOverDateKpi(isOverDateKPI ? "1" : null);
        // For Seach
        if (isSearch) {
            para.setSearch(isSearch);
            para.setTextSearch(textSearch);
        }
        mSearchConfirm = confirm;
        mSearchOverDate = isOverDateKPI ? "1" : null;
        mSearchState = state;

        // For filter
        para.setState(state);
        para.setConfirm(confirm);


        return para;
    }

    public void startSearch() {
        mTitleType = VConstant.PxkTitleType.TYPE_SEARCH;
        ParaFilterPaging para = createParaFilter(false, true, false, mSearchOverDate == null ? false : true, mTextSearch, mSearchConfirm, mSearchState);
        replateDataForSearchAndFilter(para);
    }


    private void observerDataPxk() {
        mController.getListData().observe(this, new Observer<PagedList<SynStockTransDTO>>() {
            @Override
            public void onChanged(PagedList<SynStockTransDTO> synStockTransDTOS) {
                if (synStockTransDTOS != null) {
                    mAdapter.submitList(synStockTransDTOS);
                    onRefreshSucces();
                }

            }
        });
    }

    private void observerTotalItem() {
        mController.getTotalItem().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                setTitle(aLong);
                if(aLong > 0){
                    showListData(true, false, false);
                }else{
                    showListData(false, true, false);
                }
            }
        });
    }

    private void setTitle(long numRecord) {
        String title = null;
        switch (mTitleType) {
            case VConstant.PxkTitleType.TYPE_TAT_CA:
                title = String.format(getResources().getString(R.string.pxk_paging_all), numRecord);
                break;
            case VConstant.PxkTitleType.TYPE_DA_TIEP_NHAN:
                title = String.format(getResources().getString(R.string.pxk_paging_da_tiep_nhan), numRecord);
                break;
            case VConstant.PxkTitleType.TYPE_CHO_TIEP_NHAN:
                title = String.format(getResources().getString(R.string.pxk_paging_cho_tiep_nhan), numRecord);
                break;
            case VConstant.PxkTitleType.TYPE_DA_TU_CHOI:
                title = String.format(getResources().getString(R.string.pxk_paging_da_tu_choi), numRecord);
                break;
            case VConstant.PxkTitleType.TYPE_CHO_XAC_NHAN:
                title = String.format(getResources().getString(R.string.pxk_paging_cho_xac_nhan), numRecord);
                break;
            case VConstant.PxkTitleType.TYPE_DA_XAC_NHAN:
                title = String.format(getResources().getString(R.string.pxk_paging_da_xac_nhan), numRecord);
                break;
            case VConstant.PxkTitleType.TYPE_TU_CHOI_XAC_NHAN:
                title = String.format(getResources().getString(R.string.pxk_paging_tu_choi_xac_nhan), numRecord);
                break;
            case VConstant.PxkTitleType.TYPE_QUA_HAN:
                title = String.format(getResources().getString(R.string.pxk_paging_qua_han), numRecord);
                break;
            case VConstant.PxkTitleType.TYPE_SEARCH:
                title = String.format(getResources().getString(R.string.pxk_paging_search), numRecord);
                break;
        }
        setTitle(title);
    }


    private void observerEmptyState() {
        mController.getEmptyState().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    showListData(false, true, false);
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (getActivity()!=null)
                getActivity().unregisterReceiver(receiverReLoading);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean ismIsABill() {
        return mIsABill;
    }

    @OnClick(R.id.imgBack)
    public void back() {
        getFragmentManager().popBackStack();
    }


    @Override
    public void onItemRecyclerViewclick(SynStockTransDTO item) {
        Fragment fragment = new ExWarehouse_Detail_Fragment();
        ((ExWarehouse_Detail_Fragment) fragment).setABill(ismIsABill());
        Bundle bundle = new Bundle();
        bundle.putSerializable(VConstant.BUNDLE_KEY_BILL_ENTITY, item);
        fragment.setArguments(bundle);
        if (getActivity() instanceof HomeCameraActivity)
            ((HomeCameraActivity) getActivity()).changeLayout(fragment, true);
    }

    @Override
    public void onCheckboxItemSelected(ArrayList listSelected) {
        mController.setListSynStockSelected(listSelected);
        if(listSelected != null && listSelected.size() >0){
            mABillBinding.lnBanGiao.setVisibility(View.VISIBLE);
            mABillBinding.txtSelected.setText(String.valueOf(listSelected.size()));
        } else mABillBinding.lnBanGiao.setVisibility(View.GONE);
    }

    private BroadcastReceiver receiverReLoading = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                mTitleType = VConstant.PxkTitleType.TYPE_TAT_CA;
                replateDataForSearchAndFilter(createParaFilter(true, false, false, false, mTextSearch, -1, -1));
                if (getActivity()!=null)
                    getActivity().sendBroadcast(new Intent(ParramConstant.DashBoardReload));
            }
        }
    };

    private void onRefreshSucces(){
        refreshLayout.finishRefresh();
    }
}
