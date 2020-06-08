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


import com.viettel.construction.appbase.BaseChartFragment;
import com.viettel.construction.screens.menu_acceptance.AcceptanceLevel3CameraActivity;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailDTO;
import com.viettel.construction.model.api.ConstructionAcceptanceDTOResponse;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.custom.dialog.CustomProgress;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * Màn hình hiển thị hạng mục nghiệm thu
 */

public class WAcceptanceLevel2ChartFragment extends BaseChartFragment implements AcceptanceLV2Adapter.OnClickDetails {
    @BindView(R.id.imgFilter)
    ImageView imgFilter;
    @BindView(R.id.rcvData)
    RecyclerView rcvData;
    @BindView(R.id.edtSearch)
    EditText edtSearch;

    @BindView(R.id.progressBar)
    CustomProgress customProgress;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.imgClearTextSearch)
    View imgClearTextSearch;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    private boolean allowSearch = true;


    private AcceptanceLV2Adapter adapter;
    private List<ConstructionAcceptanceCertDetailDTO> listData = new ArrayList<>();

    private ConstructionAcceptanceCertDetailDTO dto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_acceptance_level_2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        txtHeader.setText(getString(R.string.HangMucTitle,
                "0"));
        if (getArguments() != null) {
            dto = (ConstructionAcceptanceCertDetailDTO) getArguments().getSerializable("ConstructionAcceptanceCertDetailDTO");
        }
        setupEdt();

        adapter = new AcceptanceLV2Adapter(getActivity(), listData, this);
        rcvData.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvData.setAdapter(adapter);

        loadData();
    }


    private void cleartSearch() {
        imgClearTextSearch.setVisibility(View.INVISIBLE);
        allowSearch = false;
        edtSearch.setText("");
        allowSearch = true;
    }

    private void changeHeader() {
        txtHeader.setText(getString(R.string.HangMucTitle,
                adapter.getItemCount() + ""));
        if (adapter.getItemCount() == 0) {
            txtNoData.setVisibility(View.VISIBLE);
        } else
            txtNoData.setVisibility(View.INVISIBLE);

    }


    private void setupEdt() {
        edtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Send the user message
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
                    handled = true;
                }
                return handled;
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
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
                        if (editable.toString().length() == 0) {
                            adapter.setListData(listData);
                        } else {
                            if (editable.toString().length() > 0 && imgClearTextSearch.getVisibility() == View.INVISIBLE)
                                imgClearTextSearch.setVisibility(View.VISIBLE);
                            String input = StringUtil.removeAccentStr(editable.toString() + "").toLowerCase().trim();
                            List<ConstructionAcceptanceCertDetailDTO> dataSearch = new ArrayList<>();
                            for (ConstructionAcceptanceCertDetailDTO dto : listData) {
                                String workItemname;
                                if (dto.getConstructionCode() != null) {
                                    workItemname = StringUtil.removeAccentStr(dto.getWorkItemName()).toLowerCase();
                                } else {
                                    workItemname = "";
                                }
                                if (workItemname.contains(input)) {
                                    dataSearch.add(dto);
                                }
                            }
                            adapter.setListData(dataSearch);

                        }
                        adapter.notifyDataSetChanged();

                        //
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
        edtSearch.setText("");
    }

    @OnClick(R.id.imgFilter)
    public void onClickBtnFilter() {
        PopupMenu popup = new PopupMenu(getActivity(), imgFilter);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.acceptance_of_work_menu_filter, popup.getMenu());
        popup.setOnMenuItemClickListener((menuItem) -> {
            try {
                cleartSearch();
                switch (menuItem.getItemId()) {
                    case R.id.all:
                        adapter.setListData(listData);
                        adapter.notifyDataSetChanged();
                        changeHeader();
                        break;
                    case R.id.wait_for_acceptance:
                        filterByStatus("0");
                        break;
                    case R.id.already_acceptance:
                        filterByStatus("1");
                        break;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;

        });
        popup.show();
    }

    private void loadData() {
        ApiManager.getInstance().getListAcceptanceLevel2(dto,
                ConstructionAcceptanceDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ConstructionAcceptanceDTOResponse response = ConstructionAcceptanceDTOResponse.class.cast(result);
                    if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        if (response.getListConstructionAcceptanceCertWorkItemsPagesDTO() != null) {
                            listData = response.getListConstructionAcceptanceCertWorkItemsPagesDTO();
                            adapter.setListData(listData);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    customProgress.setLoading(false);
                    //
                    changeHeader();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                customProgress.setLoading(false);
                //
                changeHeader();
            }
        });
    }

    private void filterByStatus(String status) {
        List<ConstructionAcceptanceCertDetailDTO> dataSearch = new ArrayList<>();
        for (ConstructionAcceptanceCertDetailDTO dto : listData) {
            String temp;
            if (dto.getStatusAcceptance() != null) {
                temp = dto.getStatusAcceptance();
            } else {
                temp = "";
            }
            if (temp.equals(status)) {
                dataSearch.add(dto);
            }
        }
        adapter.setListData(dataSearch);
        adapter.notifyDataSetChanged();
        //
        changeHeader();
    }

    @OnClick(R.id.imgBack)
    public void onClickBtnBack() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onClickItemAcceptance(ConstructionAcceptanceCertDetailDTO data) {
        App.getInstance().setNeedUpdateAcceptance(false);
        Intent intent = new Intent(getActivity(), AcceptanceLevel3CameraActivity.class);
        intent.putExtra("data", data);
        startActivity(intent);
    }


}
