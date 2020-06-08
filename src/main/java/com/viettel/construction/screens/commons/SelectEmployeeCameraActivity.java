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

import com.viettel.construction.screens.atemp.adapter.EmployeeAdapter;
import com.viettel.construction.server.service.IOnRequestListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.EmployeeApi;
import com.viettel.construction.model.api.EmployeeResult;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.screens.custom.dialog.CustomProgress;


public class SelectEmployeeCameraActivity extends BaseCameraActivity implements EmployeeAdapter.OnClickEmployeeAdapter {

    public static final String KEY_LAST_SHIPER_ID = "key_last_shiper_id";

    private EmployeeAdapter adapter;
    private List<EmployeeApi> listData = new ArrayList<>();
    private long mLastShiperId;

    @BindView(R.id.rcv_employee)
    RecyclerView recyclerView;

    @BindView(R.id.edtSearch)
    EditText edtSearch;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.txtNoData)
    TextView txtNoData;

    @BindView(R.id.imgClearTextSearch)
    View imgClearTextSearch;

    private int direction = -1;
    @BindView(R.id.progress_employee)
    CustomProgress customProgress;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get last shiper id
        if(getIntent() != null){
            mLastShiperId = getIntent().getLongExtra(KEY_LAST_SHIPER_ID, -1);
        }

        setContentView(R.layout.activity_list_employee);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        ButterKnife.bind(this);
        txtHeader.setText(R.string.select_employee);
        initEmployee();
        initData();
        setupEdt();
        customProgress.setLoading(true);

        edtSearch.setHint("Tìm kiếm Mã, Tên, SĐT, Email");
    }

    private void setupEdt() {
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

                            List<EmployeeApi> dataSearch = new ArrayList<>();
                            for (EmployeeApi employee : listData) {
                                String name, email, employeeCode, phone;
                                if (employee.getFullName() != null) {
                                    name = StringUtil.removeAccentStr(employee.getFullName()).toUpperCase() + "";
                                } else {
                                    name = "";
                                }
                                if (employee.getEmail() != null) {
                                    email = employee.getEmail().toUpperCase();
                                } else {
                                    email = "";
                                }
                                if (employee.getEmployeeCode() != null) {
                                    employeeCode = employee.getEmployeeCode().toUpperCase();
                                } else {
                                    employeeCode = "";
                                }
                                if (employee.getPhoneNumber() != null) {
                                    phone = employee.getPhoneNumber().toUpperCase();
                                } else {
                                    phone = "";
                                }
                                if (name.contains(input) || email.contains(input) || employeeCode.contains(input) || phone.contains(input)) {
                                    dataSearch.add(employee);
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

    private void initData() {
        customProgress.setLoading(true);
        ApiManager.getInstance().getListUserByDepartment(EmployeeResult.class,mLastShiperId ,new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    EmployeeResult employeeResult = EmployeeResult.class.cast(result);
                    ResultInfo resultInfo = employeeResult.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        listData = employeeResult.getListUser();
                        adapter.setListData(listData);
                        adapter.notifyDataSetChanged();
                        customProgress.setLoading(false);
                    } else {
                        customProgress.setLoading(false);
                        Toast.makeText(SelectEmployeeCameraActivity.this, resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                customProgress.setLoading(false);
                Toast.makeText(SelectEmployeeCameraActivity.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.imgClearTextSearch)
    public void clearTextSearch() {
        imgClearTextSearch.setVisibility(View.INVISIBLE);
        edtSearch.setText("");
    }


    public void initEmployee() {
        adapter = new EmployeeAdapter(listData, this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.imgBack)
    public void OnClickBtnBack() {
        if (customProgress.isLoading()) {
            customProgress.setLoading(false);
        }
        Intent intent = new Intent();
        intent.putExtra("employeeData", "");
        setResult(1111, intent);
        finish();
    }

    @Override
    public void OnClickEmployee(EmployeeApi employee) {
        Intent intent = new Intent();
        intent.putExtra("employeeResult", employee);
        setResult(3, intent);
        finish();
    }
}
