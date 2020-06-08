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
import android.view.LayoutInflater;
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
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionMerchandiseDTOResponse;
import com.viettel.construction.model.api.ConstructionMerchandiseDetailDTO;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.custom.dialog.CustomProgress;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * Hiển thị danh sách công trinh của hoàn trả
 */

public class ListRefundLever1ChartFragment extends BaseChartFragment implements ListRefundLever1Adapter.RefundLv1ItemClick {
    private List<ConstructionMerchandiseDetailDTO> listData = new ArrayList<>();


    @BindView(R.id.rcvData)
    RecyclerView mRVRefund;
    @BindView(R.id.edtSearch)
    EditText mEdtSearch;
    @BindView(R.id.imgFilter)
    ImageView mIVFilter;

    @BindView(R.id.progressBar)
    CustomProgress customProgress;
    @BindView(R.id.txtNoData)
    TextView txtNoData;

    @BindView(R.id.imgClearTextSearch)
    View imgClearTextSearch;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    private ListRefundLever1Adapter adapter;

    private boolean allowSearch = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_refund_lever_1, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtHeader.setText(getString(R.string.refund_equipment, "0"));
        setupEdt();
        setupRecyclerView();
        getData();
    }

    private void setupRecyclerView() {
        adapter = new ListRefundLever1Adapter(listData, getActivity(), this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRVRefund.setLayoutManager(manager);
        mRVRefund.setAdapter(adapter);
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
                        if (editable.toString().trim().length() == 0)
                            adapter.setListData(listData);
                        else {
                            if (editable.toString().length() > 0 && imgClearTextSearch.getVisibility() == View.INVISIBLE)
                                imgClearTextSearch.setVisibility(View.VISIBLE);

                            String input = StringUtil.removeAccentStr(editable.toString() + "").trim().toUpperCase();

                            List<ConstructionMerchandiseDetailDTO> dataSearch = new ArrayList<>();
                            for (ConstructionMerchandiseDetailDTO entity : listData) {
                                String code;
                                if (entity.getConstructionCode() != null) {
                                    code = entity.getConstructionCode().trim().toUpperCase();
                                } else {
                                    code = "";
                                }
                                if (code.contains(input)) {
                                    dataSearch.add(entity);
                                }
                            }
                            adapter.setListData(dataSearch);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    //
                    if (adapter.getItemCount() == 0)
                        txtNoData.setVisibility(View.VISIBLE);
                    else
                        txtNoData.setVisibility(View.INVISIBLE);
                    //
                    txtHeader.setText(getString(R.string.refund_equipment, adapter.getItemCount() + ""));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
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

    private void getData() {
        customProgress.setLoading(true);
        ApiManager.getInstance().getListRefundLever1(ConstructionMerchandiseDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                ConstructionMerchandiseDTOResponse dtoResponse = ConstructionMerchandiseDTOResponse.class.cast(result);
                ResultInfo resultInfo = dtoResponse.getResultInfo();
                if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                    listData = dtoResponse.getListConstructionMerchandisePagesDTO();
                    adapter.setListData(listData);
                    adapter.notifyDataSetChanged();

                    if (adapter.getItemCount() == 0) {
                        txtNoData.setVisibility(View.VISIBLE);
                    } else
                        txtNoData.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(getActivity(), resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                }
                customProgress.setLoading(false);
                txtHeader.setText(getString(R.string.refund_equipment, adapter.getItemCount() + ""));

            }

            @Override
            public void onError(int statusCode) {
                customProgress.setLoading(false);
                if (adapter.getItemCount() == 0) {
                    txtNoData.setVisibility(View.VISIBLE);
                } else
                    txtNoData.setVisibility(View.INVISIBLE);
            }
        });
    }

    @OnClick(R.id.imgFilter)
    public void onClickFilter() {
        PopupMenu popup = new PopupMenu(getActivity(), mIVFilter);
        popup.getMenuInflater()
                .inflate(R.menu.list_refund_lv1_menu, popup.getMenu());
        popup.setOnMenuItemClickListener((menuItem) -> {
            cleartSearch();
            switch (menuItem.getItemId()) {
                case R.id.all:
                    adapter.setListData(listData);
                    adapter.notifyDataSetChanged();
                    //
                    if (adapter.getItemCount() == 0)
                        txtNoData.setVisibility(View.VISIBLE);
                    else
                        txtNoData.setVisibility(View.INVISIBLE);
                    //
                    txtHeader.setText(getString(R.string.refund_equipment, adapter.getItemCount() + ""));
                    break;
                case R.id.in_process:
                    filterByStatus("3");
                    break;
                case R.id.on_pause:
                    filterByStatus("4");
                    break;
                case R.id.finished:
                    filterByStatus("5");
                    break;
                case R.id.already_acceptance:
                    filterByStatus("6");
                    break;

            }
            return true;
        });
        popup.show();
    }

    private void filterByStatus(String status) {
        List<ConstructionMerchandiseDetailDTO> dataSearch =
                Observable.from(listData).filter(item -> item.getConstructionStatus() != null && item.getConstructionStatus().equals(status))
                        .toList().toBlocking().singleOrDefault(new ArrayList<>());

        adapter.setListData(dataSearch);

        adapter.notifyDataSetChanged();
        //
        if (adapter.getItemCount() == 0)
            txtNoData.setVisibility(View.VISIBLE);
        else
            txtNoData.setVisibility(View.INVISIBLE);
        //
        txtHeader.setText(getString(R.string.refund_equipment, adapter.getItemCount() + ""));
    }

    @OnClick(R.id.imgBack)
    public void onBack() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onItemClick(ConstructionMerchandiseDetailDTO vtdto) {
        Fragment frag = new ListRefundLever2ChartFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("constructionMerchandiseDetailDTO", vtdto);
        frag.setArguments(bundle);
        commitChange(frag, true);
    }

}
