package com.viettel.construction.screens.wrac.ab;

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

import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.history.HandOverHistoryDTORespone;
import com.viettel.construction.model.api.history.StTransactionDTO;
import com.viettel.construction.screens.menu_history_vttb.HandOver_Receiver_Detail_Level1Fragment;
import com.viettel.construction.screens.menu_history_vttb.HandOver_Receiver_Detail_Level2Adapter;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.custom.dialog.CustomProgress;
import com.viettel.construction.appbase.BaseChartFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

public class TabLichSuVTTBBaseChart extends BaseChartFragment
        implements LichSuVTTBFragment.ITabConstructionChanged, HandOver_Receiver_Detail_Level2Adapter.OnClickHistory {

    @BindView(R.id.imgFilter)
    ImageView ivFilter;
    @BindView(R.id.rcvData)
    RecyclerView rcvData;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.edtSearch)
    EditText mEdtSearch;

    @BindView(R.id.txtNoData)
    TextView txtNoData;

    @BindView(R.id.progressBar)
    CustomProgress customProgress;

    @BindView(R.id.imgClearTextSearch)
    View imgClearTextSearch;

    private boolean allowSearch = true;

    private HandOver_Receiver_Detail_Level2Adapter adapter;
    private List<StTransactionDTO> listData = new ArrayList<>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        txtHeader.setText(getString(R.string.history_handover_material, "0"));
        setupRecyclerView();
        loadData();
        setupEdt();
    }

    public int loadType() {
        return 0;//0: Tiếp nhận, 1: Chuyển giao
    }


    private void loadData() {
        customProgress.setLoading(true);
        ApiManager.getInstance().getListHistoryHandOver(HandOverHistoryDTORespone.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                HandOverHistoryDTORespone respone = HandOverHistoryDTORespone.class.cast(result);
                if (respone.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                    switch (loadType()) {
                        case 0:
                            listData = respone.getListStTransactionReceivePagesDTO();
                            adapter.setDataForBooleanVariable(true);
                            break;
                        case 1:
                            listData = respone.getListStTransactionHandoverPagesDTO();
                            adapter.setDataForBooleanVariable(false);
                            break;
                    }

                }
                customProgress.setLoading(false);
                adapter.setListData(listData);
                adapter.notifyDataSetChanged();

                changeHeader();
            }

            @Override
            public void onError(int statusCode) {
                customProgress.setLoading(true);
                changeHeader();
            }
        });
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
                    if (allowSearch) {
                        if (editable.toString().trim().length() == 0)
                            adapter.setListData(listData);
                        else {
                            if (editable.toString().length() > 0 && imgClearTextSearch.getVisibility() == View.INVISIBLE)
                                imgClearTextSearch.setVisibility(View.VISIBLE);
                            String input = StringUtil.removeAccentStr(editable.toString() + "").toLowerCase().trim();
                            List<StTransactionDTO> dataSearch = new ArrayList<>();
                            for (StTransactionDTO dto : listData) {
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
                                    dataSearch.add(dto);
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


    @OnClick(R.id.imgFilter)
    public void onClickFilter() {
        PopupMenu popup = new PopupMenu(getActivity(), ivFilter);
        popup.getMenuInflater()
                .inflate(R.menu.menu_history, popup.getMenu());
        popup.setOnMenuItemClickListener((menuItem) -> {
            try {
                cleartSearch();
                switch (menuItem.getItemId()) {
                    case R.id.all_history:
                        adapter.setListData(listData);
                        adapter.notifyDataSetChanged();
                        changeHeader();
                        break;
                    case R.id.wait_history:
                        filterByStatus("0");
                        break;
                    case R.id.received_history:
                        filterByStatus("1");
                        break;
                    case R.id.refuse_history:
                        filterByStatus("2");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        });
        popup.show();
    }

    private void filterByStatus(String status) {
        List<StTransactionDTO> dataSearch =
                Observable.from(listData).filter(x -> x.getConfirm() != null && x.getConfirm().contains(status))
                        .toList().toBlocking().singleOrDefault(new ArrayList<>());
        adapter.setListData(dataSearch);
        adapter.notifyDataSetChanged();
        changeHeader();
    }


    private void changeHeader() {
        if (adapter.getItemCount() == 0)
            txtNoData.setVisibility(View.VISIBLE);
        else
            txtNoData.setVisibility(View.INVISIBLE);
        txtHeader.setText(getString(R.string.history_handover_material, adapter.getItemCount() + ""));
    }

    @Override
    public void tabChanged() {
        if (mEdtSearch.length() == 0 && adapter.getItemCount() == 0) {
            loadData();
        }
    }

    private void setupRecyclerView() {
        adapter = new HandOver_Receiver_Detail_Level2Adapter(getContext(), listData, this);
        rcvData.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvData.setAdapter(adapter);

    }

    private void cleartSearch() {
        imgClearTextSearch.setVisibility(View.INVISIBLE);
        allowSearch = false;
        mEdtSearch.setText("");
        allowSearch = true;
    }


    @OnClick(R.id.imgClearTextSearch)
    public void clearTextSearch() {
        imgClearTextSearch.setVisibility(View.INVISIBLE);
        mEdtSearch.setText("");
    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onClick(StTransactionDTO stTransactionDTO) {
        try {
            Fragment frag = new HandOver_Receiver_Detail_Level1Fragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("StTransactionDTO", stTransactionDTO);
            frag.setArguments(bundle);
            commitChange(frag, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
