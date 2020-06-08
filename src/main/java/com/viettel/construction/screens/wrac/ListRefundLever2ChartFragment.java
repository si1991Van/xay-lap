package com.viettel.construction.screens.wrac;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
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
import com.viettel.construction.screens.menu_return_vttb.RefundItemLevel3CameraActivity;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionMerchandiseDTORequest;
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
 * Hiển thị danh sách hạng mục của hoàn trả
 */
public class ListRefundLever2ChartFragment extends BaseChartFragment implements ListRefundLever2Adapter.OnClickDetails {

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

    private boolean allowSearch = true;

    private List<ConstructionMerchandiseDetailDTO> listData = new ArrayList<>();

    private ListRefundLever2Adapter adapter;
    private ConstructionMerchandiseDetailDTO constructionMerchandiseDetailDTO;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_refund_lever_1, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            constructionMerchandiseDetailDTO = (ConstructionMerchandiseDetailDTO) getArguments().getSerializable("constructionMerchandiseDetailDTO");
        }
        setupEdt();
        return view;
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
                            String input = StringUtil.removeAccentStr(editable.toString()).trim().toUpperCase();

                            List<ConstructionMerchandiseDetailDTO> dataSearch = new ArrayList<>();
                            for (ConstructionMerchandiseDetailDTO entity : listData) {
                                String name;
                                if (entity.getConstructionCode() != null) {
                                    name = StringUtil.removeAccentStr(entity.getWorkItemName()).trim().toUpperCase();
                                } else {
                                    name = "";
                                }
                                if (name.contains(input)) {
                                    dataSearch.add(entity);
                                }
                            }

                            adapter.setListData(dataSearch);
                        }
                        adapter.notifyDataSetChanged();

                        //
                        if (adapter.getItemCount() == 0)
                            txtNoData.setVisibility(View.VISIBLE);
                        else
                            txtNoData.setVisibility(View.INVISIBLE);
                        //
                        txtHeader.setText(getString(R.string.refund_equipment, adapter.getItemCount() + ""));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        loadData();

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

    private void initView() {
        adapter = new ListRefundLever2Adapter(getActivity(), listData, this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRVRefund.setLayoutManager(manager);
        mRVRefund.setAdapter(adapter);
    }

    private void loadData() {

        ConstructionMerchandiseDTORequest request = new ConstructionMerchandiseDTORequest();
        request.setConstructionMerchandiseDetailDTO(constructionMerchandiseDetailDTO);
        request.setSysUserRequest(VConstant.getUser());

        ApiManager.getInstance().getListRefundLever2(request, ConstructionMerchandiseDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ConstructionMerchandiseDTOResponse dtoResponse = ConstructionMerchandiseDTOResponse.class.cast(result);
                    ResultInfo resultInfo = dtoResponse.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        listData = dtoResponse.getListConstructionMerchandiseWorkItemPagesDTO();
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                .inflate(R.menu.list_refund_lv2_menu, popup.getMenu());
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
                case R.id.not_controlled:
                    filterByStatus("0");
                    break;
                case R.id.checked_chart:
                    filterByStatus("1");
                    break;
            }
            return true;

        });
        popup.show();
    }

    private void filterByStatus(String status) {
        List<ConstructionMerchandiseDetailDTO> dataSearch =
                Observable.from(listData)
                        .filter(item->item!=null&&item.getStatusComplete().equals(status))
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

    @Override
    public void onClickItem(ConstructionMerchandiseDetailDTO data) {
        App.getInstance().setNeedUpdateRefund(false);
        Intent intent = new Intent(getActivity(), RefundItemLevel3CameraActivity.class);
        intent.putExtra("constructionMerchandiseDetailDTO", data);
        startActivity(intent);
    }

    @OnClick(R.id.imgBack)
    public void onBack() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (App.getInstance().isNeedUpdateRefund()) {
            loadData();
        }
    }
}
