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
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.screens.atemp.adapter.ItemAdapter;
import com.viettel.construction.server.service.IOnRequestListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionStationWorkItem;
import com.viettel.construction.model.api.ListConstructionStationWork;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.appbase.BaseCameraActivity;


/***
 * Chọn hạng mục
 */
public class SelectConsCategoryCameraActivity extends BaseCameraActivity implements ItemAdapter.OnClickItemAdapter {

    private ItemAdapter adapter;
    private List<ConstructionStationWorkItem> listData = new ArrayList<>();
    @BindView(R.id.rcv_item)
    RecyclerView rcvItem;

    @BindView(R.id.edtSearch)
    EditText edtSearch;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.txtNoData)
    TextView txtNoData;

    @BindView(R.id.imgClearTextSearch)
    View imgClearTextSearch;

    private long idConstruction;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        setContentView(R.layout.activity_list_item);
        ButterKnife.bind(this);

        txtHeader.setText(R.string.selectCategory);

        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getLong("id_construction") > 0) {
                idConstruction = getIntent().getExtras().getLong("id_construction");
            }
        }
        initView();
        loadData();
        setupEditText();

    }

    private void loadData() {
        ApiManager.getInstance().getNameWorkItemByConstruction(ListConstructionStationWork.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ListConstructionStationWork listConstructionStationWork = ListConstructionStationWork.class.cast(result);
                    if (listConstructionStationWork.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        listData = listConstructionStationWork.getListConstructionStationWorkItem();
                        adapter.setListData(listData);
                        adapter.notifyDataSetChanged();

                        //
                        if (adapter.getItemCount() == 0)
                            txtNoData.setVisibility(View.VISIBLE);
                        else
                            txtNoData.setVisibility(View.INVISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(SelectConsCategoryCameraActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
            }
        }, idConstruction);
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
                            String input = removeAccent(String.valueOf(editable.toString())).toUpperCase().trim();
                            List<ConstructionStationWorkItem> dataSearch = new ArrayList<>();
                            for (ConstructionStationWorkItem entity : listData) {
                                String categoryName = removeAccent(entity.getName()).toUpperCase();
                                if (categoryName.contains(input)) {
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

    @OnClick(R.id.imgBack)
    public void OnBack() {
        finish();
    }


    private void initView() {
        adapter = new ItemAdapter(this, listData, this);
        rcvItem.setLayoutManager(new LinearLayoutManager(this));
        rcvItem.setAdapter(adapter);
    }


    @Override
    public void OnClickItemAdapter(ConstructionStationWorkItem dt) {
        Intent intent = new Intent();
        intent.putExtra("categoryResult", dt);
        setResult(2, intent);
        finish();
    }
}
