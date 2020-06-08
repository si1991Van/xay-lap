package com.viettel.construction.screens.wrac;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.viettel.construction.screens.menu_ex_warehouse.ExWarehouse_Detail_Fragment;
import com.viettel.construction.screens.menu_ex_warehouse.TabPageExWareHouseFragment;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.StockTransResponse;
import com.viettel.construction.model.api.SynStockTransDTO;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.home.HomeCameraActivity;
import com.viettel.construction.screens.custom.dialog.CustomProgress;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

public class TabDeliveryBillChildBase extends Fragment
        implements BillAdapter.onBillItemClick,
        TabPageExWareHouseFragment.ITabDeliveryChanged {

    @BindView(R.id.imgFilter)
    ImageView btnFilter;
    @BindView(R.id.rcvData)
    RecyclerView rcvData;

    @BindView(R.id.edtSearch)
    EditText mEdtSearch;
    @BindView(R.id.txtHeader)
    TextView txtHeader;


    @BindView(R.id.progressBar)
    CustomProgress customProgress;
    @BindView(R.id.txtNoData)
    TextView txtNoData;

    @BindView(R.id.imgClearTextSearch)
    View imgClearTextSearch;

    private List<SynStockTransDTO> listData = new ArrayList<>();

    private BillAdapter adapter;

    private boolean allowSearch = true;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        adapter = new BillAdapter(getActivity(), listData, this);
        rcvData.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvData.setAdapter(adapter);
        //
        setupEdt();
        loadData();
    }

    private void cleartSearch() {
        imgClearTextSearch.setVisibility(View.INVISIBLE);
        allowSearch = false;
        mEdtSearch.setText("");
        allowSearch = true;
    }

    private void changeHeader() {
        txtHeader.setText(getString(R.string.PhieuXuatKhoTitle,
                adapter.getItemCount() + ""));
        if (adapter.getItemCount() == 0) {
            txtNoData.setVisibility(View.VISIBLE);
        } else
            txtNoData.setVisibility(View.INVISIBLE);

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
                    if (allowSearch) {
                        if (editable.toString().trim().length() == 0) {
                            adapter.setListData(listData);
                        } else {
                            if (editable.length() > 0 && imgClearTextSearch.getVisibility() == View.INVISIBLE)
                                imgClearTextSearch.setVisibility(View.VISIBLE);
                            String input = StringUtil.removeAccentStr(editable.toString() + "").trim().toUpperCase();

                            List<SynStockTransDTO> dataSearch = new ArrayList<>();
                            for (SynStockTransDTO entity : listData) {
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
                                    dataSearch.add(entity);
                                }
                            }
                            adapter.setListData(dataSearch);
                        }
                        adapter.notifyDataSetChanged();
                        changeHeader();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick(R.id.imgClearTextSearch)
    public void clearTextSearch() {
        imgClearTextSearch.setVisibility(View.INVISIBLE);
        mEdtSearch.setText("");
    }


    @Override
    public void onClickBill(SynStockTransDTO dto) {
        try {
            Fragment fragment = new ExWarehouse_Detail_Fragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(VConstant.BUNDLE_KEY_BILL_ENTITY, dto);
            fragment.setArguments(bundle);
            commitChange(fragment, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void commitChange(Fragment fragment, Boolean... isAdd) {
        if (getActivity() instanceof HomeCameraActivity)
            ((HomeCameraActivity) getActivity()).changeLayout(fragment, isAdd);
    }

    @OnClick(R.id.imgFilter)
    public void onClickFilter() {
        PopupMenu popup = new PopupMenu(getActivity(), btnFilter);
        popup.getMenuInflater()
                .inflate(R.menu.menu_delivery_bill_filter_a_bill, popup.getMenu());
        popup.setOnMenuItemClickListener((menuItem) -> {
            try {
                cleartSearch();
                switch (menuItem.getItemId()) {
                    case R.id.all:
                        adapter.setListData(listData);
                        adapter.notifyDataSetChanged();
                        //
                        changeHeader();
                        break;
                    case R.id.received:
                        filterByStatus("1", false);
                        break;
                    case R.id.wait_for_receive:
                        filterByStatus("0", false);
                        break;
                    case R.id.rejected:
                        filterByStatus("2", false);
                        break;
                    //cho xac nhan : 0
                    //da xac nhan : 1
                    //Tu choi xac nhan : 2
                    case R.id.wait_confirm:
                        filterByStatus("0", true);
                        break;
                    case R.id.confirm:
                        filterByStatus("1", true);
                        break;
                    case R.id.dont_confirm:
                        filterByStatus("2", true);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;

        });
        popup.show();
    }

    //state
    private void filterByStatus(String s, Boolean isState) {
        List<SynStockTransDTO> dataSearch;
        if (isState) {
            dataSearch = Observable.from(listData).filter(x -> x.getState() != null && x.getState().contains(s))
                    .toList().toBlocking().singleOrDefault(new ArrayList<>());
        } else {

            dataSearch = Observable.from(listData).filter(x -> x.getConfirm() != null && x.getConfirm().contains(s))
                    .toList().toBlocking().singleOrDefault(new ArrayList<>());
        }

        //
        adapter.setListData(dataSearch);
        adapter.notifyDataSetChanged();

        //
        changeHeader();

    }

    public boolean isABill() {
        return true;
    }

    private void loadData() {
        customProgress.setLoading(true);
        ApiManager.getInstance().getListSynStockTransDTO(StockTransResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    StockTransResponse stockTransResponse = StockTransResponse.class.cast(result);
                    ResultInfo resultInfo = stockTransResponse.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        if (stockTransResponse.getLstSynStockTransDto().size() != 0) {
                            List<SynStockTransDTO> list = stockTransResponse.getLstSynStockTransDto();
                            if (isABill()) {
                                List<SynStockTransDTO> temp =
                                        Observable.from(list).filter(item -> item.getStockType().contains("A"))
                                                .toList().toBlocking().single();

                                listData = temp;
                            } else {
                                List<SynStockTransDTO> temp = Observable.from(list)
                                        .filter(item -> item.getStockType()
                                                .contains("B")).toList().toBlocking().single();

                                listData = temp;

                            }
                            adapter.setListData(listData);
                            adapter.notifyDataSetChanged();
                        }
                        customProgress.setLoading(false);
                        //
                        changeHeader();

                    } else {
                        customProgress.setLoading(false);
                        changeHeader();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                customProgress.setLoading(false);
            }
        });
    }


    @Override
    public void tabChanged() {

    }

    @OnClick(R.id.imgBack)
    public void backScreen() {
        getFragmentManager().popBackStack();
    }
}
