//package com.viettel.construction.view.activity.other;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.KeyEvent;
//import android.view.WindowManager;
//import android.view.inputmethod.EditorInfo;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.viettel.construction.server.service.IOnRequestListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import com.viettel.construction.R;
//import com.viettel.construction.common.VConstant;
//import com.viettel.construction.model.api.ConstructionStationWorkItem;
//import com.viettel.construction.model.api.ListConstructionStationWork;
//import com.viettel.construction.server.api.ApiManager;
//import com.viettel.construction.view.activity.base.BaseCameraActivity;
//import com.viettel.construction.view.adapter.ItemAdapter;
//
//public class ListWorkActivity extends BaseCameraActivity implements ItemAdapter.OnClickItemAdapter {
//    private ItemAdapter itemAdapter;
//    private List<ConstructionStationWorkItem> list;
//    private List<ConstructionStationWorkItem> listBackupForSearch = new ArrayList<>();
//    @BindView(R.id.rcv_item)
//    RecyclerView rcvItem;
//    @BindView(R.id.img_back_list_cv1)
//    ImageView imgBack;
//    @BindView(R.id.edt_search)
//    EditText edtSearch;
//    private long idConstruction = 0;
//    private long workItemId = 0;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
//        setContentView(R.layout.activity_list_work);
//
//        if (getIntent().getExtras() != null) {
//            if (getIntent().getExtras().getLong("key_construction") > 0
//                    && getIntent().getExtras().getLong("key_category") > 0) {
//                idConstruction = getIntent().getExtras().getLong("key_construction");
//                workItemId = getIntent().getExtras().getLong("key_category");
//            }
//        }
//        listBackupForSearch.clear();
//        ButterKnife.bind(this);
//        initData();
//        setupEdt();
//    }
//
//    private void setupEdt() {
//        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                boolean handled = false;
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    // Send the user message
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
//                    handled = true;
//                }
//                return handled;
//            }
//        });
//        edtSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (listBackupForSearch.size() > 0) {
//                    String input = removeAccent(String.valueOf(charSequence)).toUpperCase().trim();
//                    list.clear();
//                    if (input.length() != 0) {
//                        for (ConstructionStationWorkItem item : listBackupForSearch) {
//                            String temp = removeAccent(item.getName()).toUpperCase();
//                            if (temp.contains(input)) {
//                                list.add(item);
//                            }
//                        }
//                    } else {
//                        list.addAll(listBackupForSearch);
//                    }
//                    itemAdapter.notifyDataSetChanged();
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//    }
//
//    private void initData() {
//        ApiManager.getInstance().getListWorkWithCategory(ListConstructionStationWork.class, new IOnRequestListener() {
//            @Override
//            public <T> void onResponse(T result) {
//                ListConstructionStationWork listConstructionStationWork = ListConstructionStationWork.class.cast(result);
//                if (listConstructionStationWork.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
//                    list = new ArrayList<>();
//                    list.addAll(listConstructionStationWork.getListConstructionStationWorkItem());
//                    listBackupForSearch.addAll(list);
//                    initView();
//                    itemAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onError(int statusCode) {
//                Toast.makeText(ListWorkActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
//            }
//        }, idConstruction, workItemId);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        finish();
//    }
//
//    @OnClick(R.id.img_back_list_cv1)
//    public void OnBack() {
//        Intent intent = new Intent();
//        intent.putExtra("nameWork", "");
//        setResult(1010, intent);
//        finish();
//    }
//
//    @Override
//    public void OnClickItemAdapter(int pos) {
//        Intent intent = new Intent();
//        intent.putExtra("nameWork", list.get(pos));
//        setResult(5, intent);
//        finish();
//    }
//
//    private void initView() {
//        itemAdapter = new ItemAdapter(this, list, this);
//        rcvItem.setLayoutManager(new LinearLayoutManager(this));
//        rcvItem.setAdapter(itemAdapter);
//    }
//}
