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

import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailDTO;
import com.viettel.construction.model.api.ConstructionAcceptanceDTOResponse;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.custom.dialog.CustomProgress;
import com.viettel.construction.appbase.BaseChartFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * Màn hình hiển thị công trình nghiệm thu
 */
public class WAcceptanceLevel1ChartFragment extends BaseChartFragment
        implements AcceptanceLV1Adapter.OnClickAcceptanceLever1 {
    @BindView(R.id.rcvData)
    RecyclerView rcvData;
    @BindView(R.id.imgFilter)
    ImageView imgFilter;
    @BindView(R.id.edtSearch)
    EditText mEdtSearch;

    @BindView(R.id.progressBar)
    CustomProgress customProgress;

    @BindView(R.id.txtNoData)
    TextView txtNoData;

    @BindView(R.id.imgClearTextSearch)
    View imgClearTextSearch;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    private boolean allowSearch = true;

    private AcceptanceLV1Adapter adapter;
    private List<ConstructionAcceptanceCertDetailDTO> listData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acceptance_level_1, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        txtHeader.setText(getString(R.string.NghiemThuTitle,
                "0"));
        setupRecyclerView();
        setupEdt();
        loadData();
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
                                String code, address;
                                if (dto.getConstructionCode() != null) {
                                    code = StringUtil.removeAccentStr(dto.getConstructionCode()).toLowerCase();
                                } else {
                                    code = "";
                                }
                                if (dto.getAddress() != null) {
                                    address = StringUtil.removeAccentStr(dto.getAddress()).toLowerCase();
                                } else {
                                    address = "";
                                }
                                if (code.contains(input) || address.contains(input)) {
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
        mEdtSearch.setText("");
    }

    private void cleartSearch() {
        imgClearTextSearch.setVisibility(View.INVISIBLE);
        allowSearch = false;
        mEdtSearch.setText("");
        allowSearch = true;
    }

    private void changeHeader() {
        txtHeader.setText(getString(R.string.NghiemThuTitle,
                adapter.getItemCount() + ""));
        if (adapter.getItemCount() == 0) {
            txtNoData.setVisibility(View.VISIBLE);
        } else
            txtNoData.setVisibility(View.INVISIBLE);

    }

    private void setupRecyclerView() {
        adapter = new AcceptanceLV1Adapter(getActivity(), listData, this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rcvData.setLayoutManager(manager);
        rcvData.setAdapter(adapter);
    }

    private void loadData() {
        ApiManager.getInstance().callDataListAcceptanceLever1(ConstructionAcceptanceDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                ConstructionAcceptanceDTOResponse dtoResponse = ConstructionAcceptanceDTOResponse.class.cast(result);
                ResultInfo resultInfo = dtoResponse.getResultInfo();
                if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                    if (dtoResponse.getListConstructionAcceptanceCertPagesDTO() != null) {
                        listData = dtoResponse.getListConstructionAcceptanceCertPagesDTO();
                        adapter.setListData(listData);
                        adapter.notifyDataSetChanged();
                    }
                }
                customProgress.setLoading(false);
                //
                changeHeader();
            }

            @Override
            public void onError(int statusCode) {
                customProgress.setLoading(false);
                changeHeader();
            }
        });
    }

    @OnClick(R.id.imgFilter)
    public void onClickFilter() {
        PopupMenu popup = new PopupMenu(getActivity(), imgFilter);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.acceptance_lever_1_menu, popup.getMenu());
        popup.setOnMenuItemClickListener((menuItem) -> {
            cleartSearch();
            switch (menuItem.getItemId()) {
                case R.id.all:
                    adapter.setListData(listData);
                    adapter.notifyDataSetChanged();
                    changeHeader();
                    break;
                case R.id.acceptance_have_not_finish:
                    filterByStatus("3");
                    break;
                case R.id.acceptance_finished:
                    filterByStatus("5");
                    break;
                case R.id.acceptance_pause:
                    filterByStatus("4");
                    break;
            }
            return true;
        });
        popup.show();
    }

    private void filterByStatus(String status) {
        List<ConstructionAcceptanceCertDetailDTO> dataSearch = new ArrayList<>();
        for (ConstructionAcceptanceCertDetailDTO dto : listData) {
            String temp;
            if (dto.getStatusConstruction() != null) {
                temp = dto.getStatusConstruction();
            } else {
                temp = "";
            }
            if (temp.equals(status)) {
                dataSearch.add(dto);
            }
        }
        adapter.setListData(dataSearch);
        adapter.notifyDataSetChanged();

        changeHeader();
    }

    @Override
    public void onClick(ConstructionAcceptanceCertDetailDTO data) {
        Fragment frag = new WAcceptanceLevel2ChartFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ConstructionAcceptanceCertDetailDTO", data);
        frag.setArguments(bundle);
        commitChange(frag, true);
    }

    @OnClick(R.id.imgBack)
    public void onBack() {
        getFragmentManager().popBackStack();
    }
}
