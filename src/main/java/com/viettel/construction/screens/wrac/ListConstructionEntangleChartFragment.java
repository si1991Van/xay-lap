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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.viettel.construction.appbase.BaseChartFragment;
import com.viettel.construction.screens.menu_entangle.CreateEntangleCameraActivity;
import com.viettel.construction.screens.menu_entangle.EntangleCameraActivity;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.entangle.EntangleManageDTO;
import com.viettel.construction.model.api.entangle.EntangleManageDTORespone;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.custom.dialog.CustomProgress;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * Danh sách công trình vướng
 */

public class ListConstructionEntangleChartFragment extends BaseChartFragment implements EntangleAdapter1.OnClickDetails {

    @BindView(R.id.btnAddItem)
    ImageButton btnCreate;

    @BindView(R.id.imgFilter)
    ImageView ivFilter;
    @BindView(R.id.rcvData)
    RecyclerView rcvEntangle;

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

    private List<EntangleManageDTO> listData = new ArrayList<>();
    private EntangleAdapter1 adapter;

    private boolean allowSearch = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_construction_entangle, container, false);
        ButterKnife.bind(this, view);
        txtHeader.setText(getString(R.string.ComplainTitle, "0"));
        //TODO: Tạm thời ẩn đi
        btnCreate.setVisibility(View.GONE);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        loadData();
        setupEdt();
    }

    private void loadData() {
        customProgress.setLoading(true);
        ApiManager.getInstance().getListEntangle(EntangleManageDTORespone.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                EntangleManageDTORespone respone = EntangleManageDTORespone.class.cast(result);
                ResultInfo resultInfo = respone.getResultInfo();
                if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                    listData = respone.getListEntangleManageDTO();
                    adapter.setListData(listData);
                    adapter.notifyDataSetChanged();
                }

                if (adapter.getItemCount() == 0) {
                    txtNoData.setVisibility(View.VISIBLE);
                } else
                    txtNoData.setVisibility(View.INVISIBLE);
                customProgress.setLoading(false);
                txtHeader.setText(getString(R.string.ComplainTitle, adapter.getItemCount() + ""));
            }

            @Override
            public void onError(int statusCode) {
                if (adapter.getItemCount() == 0) {
                    txtNoData.setVisibility(View.VISIBLE);
                } else
                    txtNoData.setVisibility(View.INVISIBLE);
                customProgress.setLoading(false);
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

    private void initView() {
        listData = new ArrayList<>();
        adapter = new EntangleAdapter1(getActivity(), listData, this);
        rcvEntangle.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvEntangle.setAdapter(adapter);
    }

    @OnClick(R.id.btnAddItem)
    public void onClickCreate() {
        App.getInstance().setNeedUpdateEntangle(false);
        Intent intent = new Intent(getActivity().getBaseContext(), CreateEntangleCameraActivity.class);
        intent.putExtra("isCreate", 300);
        startActivity(intent);
    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onClickItemEntangle(EntangleManageDTO dto) {
        App.getInstance().setNeedUpdateEntangle(false);
        Intent intent = new Intent(getContext(), EntangleCameraActivity.class);
        intent.putExtra("entangleManageDTO", dto);
        startActivity(intent);
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
                        if (editable.toString().trim().length() == 0)
                            adapter.setListData(listData);
                        else {
                            if (editable.toString().length() > 0 && imgClearTextSearch.getVisibility() == View.INVISIBLE)
                                imgClearTextSearch.setVisibility(View.VISIBLE);
                            String input = StringUtil.removeAccentStr(editable.toString() + "").toLowerCase().trim();

                            List<EntangleManageDTO> dataSearch = new ArrayList<>();

                            for (EntangleManageDTO itemDto : listData) {
                                String code;
                                if (itemDto.getConsCode() != null) {
                                    code = StringUtil.removeAccentStr(itemDto.getConsCode()).toLowerCase();
                                } else {
                                    code = "";
                                }
                                if (code.contains(input)) {
                                    dataSearch.add(itemDto);
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
                        txtHeader.setText(getString(R.string.ComplainTitle, adapter.getItemCount() + ""));
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
                .inflate(R.menu.list_entangle_menu, popup.getMenu());
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
                    txtHeader.setText(getString(R.string.ComplainTitle, adapter.getItemCount() + ""));
                    break;
                case R.id.wait_for_confirm_cnt:
                    filterByStatus("1");
                    break;
                case R.id.confirmed_cnt:
                    filterByStatus("2");
                    break;
            }
            return true;

        });
        popup.show();
    }

    private void filterByStatus(String status) {
        List<EntangleManageDTO> dataSearch = Observable.from(listData)
                .filter(x -> x.getObstructedState() != null && x.getObstructedState().equals(status)).toList().toBlocking().singleOrDefault(new ArrayList<>());

        adapter.setListData(dataSearch);

        adapter.notifyDataSetChanged();
        //
        if (adapter.getItemCount() == 0)
            txtNoData.setVisibility(View.VISIBLE);
        else
            txtNoData.setVisibility(View.INVISIBLE);
        //
        txtHeader.setText(getString(R.string.ComplainTitle, adapter.getItemCount() + ""));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (App.getInstance().isNeedUpdateEntangle()) {
           cleartSearch();
            loadData();
        }
    }

}
