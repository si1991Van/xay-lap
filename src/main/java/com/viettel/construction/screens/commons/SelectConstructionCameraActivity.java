package com.viettel.construction.screens.commons;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.screens.atemp.adapter.ConstructionAdapter;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionStationWorkItem;
import com.viettel.construction.model.api.ListConstructionStationWork;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.screens.custom.dialog.CustomProgress;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Chọn công trình
 */
public class SelectConstructionCameraActivity
        extends BaseCameraActivity implements ConstructionAdapter.OnClickConstruction {
    private ConstructionAdapter adapter;
    private List<ConstructionStationWorkItem> listData = new ArrayList<>();
    @BindView(R.id.rcv_construction)
    RecyclerView recyclerView;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.prg_loading)
    CustomProgress customProgress;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.txtNoData)
    TextView txtNoData;

    @BindView(R.id.imgClearTextSearch)
    View imgClearTextSearch;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_construction);
        ButterKnife.bind(this);
        txtHeader.setText(R.string.selectConstruction);
        setupEditText();
        initializeComponents();
        loadData();
    }

    private void loadData() {
        customProgress.setLoading(true);
        ApiManager.getInstance().getListConstructionByID(ListConstructionStationWork.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ListConstructionStationWork listConstructionStationWork = ListConstructionStationWork.class.cast(result);
                    ResultInfo resultInfo = listConstructionStationWork.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        listData = listConstructionStationWork.getListConstructionStationWorkItem();
                        adapter.setListData(listData);
                        adapter.notifyDataSetChanged();
                        customProgress.setLoading(false);
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


    private void setupEditText() {
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Send the user message
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
                    if (editable.length() == 0) {
                        adapter.setListData(listData);
                    } else {
                        if (editable.toString().length() > 0 && imgClearTextSearch.getVisibility() == View.INVISIBLE)
                            imgClearTextSearch.setVisibility(View.VISIBLE);
                        if (listData != null) {
                            String input = StringUtil.removeAccentStr(editable.toString()).toUpperCase().trim();
                            ArrayList<ConstructionStationWorkItem> dataSearch = new ArrayList<>();
                            for (ConstructionStationWorkItem entity : listData) {
                                String name;
                                String temp = entity.getConstructionCode().toUpperCase() +" " + entity.getName();
                                if (entity.getName() != null) {
                                    name = StringUtil.removeAccentStr(entity.getName()).trim().toUpperCase();
                                } else {
                                    name = "";
                                }
                                if (temp.contains(input) || name.contains(input)) {
                                    dataSearch.add(entity);
                                }
                            }
                            adapter.setListData(dataSearch);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    //
                    if (adapter.getItemCount() == 0) {
                        txtNoData.setVisibility(View.VISIBLE);
                    } else
                        txtNoData.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @OnClick(R.id.imgClearTextSearch)
    public void clearTextSearch() {
        try {
            imgClearTextSearch.setVisibility(View.INVISIBLE);
            edtSearch.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void initializeComponents() {
        adapter = new ConstructionAdapter(getApplicationContext(), listData, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.imgBack)
    public void OnClickBtnBack() {
        finish();
    }


    @Override
    public void OnClickConstruction(ConstructionStationWorkItem workItem) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("resultIntent", workItem);
        setResult(38, resultIntent);
        finish();
    }


}
