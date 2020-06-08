package com.viettel.construction.screens.commons;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionStationWorkItem;
import com.viettel.construction.model.api.ConstructionTaskDetailDTO;
import com.viettel.construction.model.api.EmployeeApi;
import com.viettel.construction.model.api.ResultApi;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.server.api.ApiManager;


public class CreateNewCvCameraActivity extends BaseCameraActivity {

    @BindView(R.id.tv_construction_name)
    TextView txtConstructionName;

    @BindView(R.id.tv_name_person_in_charge)
    TextView txtNamePersonInCharge;
    @BindView(R.id.tv_category_name)
    TextView txtCategoryName;
    @BindView(R.id.iv_choose_category)
    RelativeLayout imgChooseCategory;
    @BindView(R.id.iv_choose_construction)
    RelativeLayout imgChooseConstruction;
    @BindView(R.id.iv_choose_person_in_charge)
    RelativeLayout imgChooseWork;

    @BindView(R.id.img_start_calendar)
    RelativeLayout imgStartCalendar;
    @BindView(R.id.img_end_calendar)
    RelativeLayout imgEndCalendar;
    @BindView(R.id.txt_start_calendar)
    TextView txtStartCalendar;
    @BindView(R.id.txt_end_calendar)
    TextView txtEndCalendar;
    @BindView(R.id.txtCancel)
    TextView txtCancel;
    @BindView(R.id.txtSave)
    TextView btnSave;
    @BindView(R.id.edt_revenue)
    EditText edtRevenue;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    private Intent intent;
    private long idConstruction = 0;
    private long idCategory = 0;
    private ConstructionStationWorkItem construction1;
    private ConstructionStationWorkItem construction2;
    private ConstructionStationWorkItem construction3;


    private Long performerId;
    private String performerName;

    private long idWork = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_cv);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        ButterKnife.bind(this);
        txtHeader.setText(R.string.create_new_cv);
        setupEdt();
    }


    private void setupEdt() {
        edtRevenue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtRevenue.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    edtRevenue.setText(formattedString);
                    edtRevenue.setSelection(edtRevenue.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                edtRevenue.addTextChangedListener(this);
            }
        });
    }

    @OnClick(R.id.img_start_calendar)
    public void onClickShowStartCalendar() {
        setTime(txtStartCalendar);
    }

    @OnClick(R.id.img_end_calendar)
    public void onClickShowEndCalendar() {
        setTime(txtEndCalendar);
    }

    @OnClick(R.id.iv_choose_construction)
    public void OnClickChooseConstruction() {
        intent = new Intent(this, SelectConstructionCameraActivity.class);
        this.startActivityForResult(intent, 1);

    }

    @OnClick(R.id.iv_choose_person_in_charge)
    public void OnClickChoosePersonInCharge() {
        intent = new Intent(this, SelectEmployeeCameraActivity.class);
        startActivityForResult(intent, 3);
    }

    @OnClick(R.id.iv_choose_category)
    public void OnClickChooseCategory() {
        if (idConstruction > 0) {
            intent = new Intent(this, SelectConsCategoryCameraActivity.class);
            intent.putExtra("id_construction", idConstruction);
            startActivityForResult(intent, 2);
        } else {
            Toast.makeText(this, getResources().getString(R.string.please_choose_category), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                case 1:
                    if (resultCode == 38) {
                        if (data.getExtras() != null) {
                            if (data.getExtras().getSerializable("resultIntent") != null) {
                                construction1 = (ConstructionStationWorkItem) data.getSerializableExtra("resultIntent");
                                idConstruction = construction1.getConstructionId();
                                txtConstructionName.setText(construction1.getConstructionCode());
                                txtCategoryName.setText("");
                            }
                        }
                    }
                    break;
                case 2:
                    if (resultCode == 2) {
                        if (data.getExtras() != null) {
                            if (data.getExtras().getSerializable("categoryResult") != null) {
                                construction2 = (ConstructionStationWorkItem) data.getExtras().getSerializable("categoryResult");
                                if (construction2 != null) {
                                    idCategory = construction2.getWorkItemId();
                                    txtCategoryName.setText(construction2.getName());
                                }
                            }
                        }
                    }
                    break;
                case 3:
                    if (resultCode == 3) {
                        if (data.getExtras() != null) {
                            EmployeeApi employee = (EmployeeApi) data.getSerializableExtra("employeeResult");
                            txtNamePersonInCharge.setText(employee.getFullName());
                            performerId = Long.parseLong(employee.getSysUserId());
                            performerName = employee.getFullName();
                        }
                    }
                case 4:
                    if (resultCode == 5) {
                        if (data.getExtras() != null) {
                            if (data.getExtras().getSerializable("nameWork") != null) {
                                construction3 = (ConstructionStationWorkItem) data.getExtras().getSerializable("nameWork");
                                idWork = construction3.getCatTaskId();
                            }
                        }
                    }
                    break;
                default:
    //                txtWork.setText(data.getStringExtra("categoryResult"));
                    break;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.txtCancel)
    public void onCancel() {
        finish();
    }

    @OnClick(R.id.txtSave)
    public void onSave() {
        try {
            String start = txtStartCalendar.getText().toString();
            String end = txtEndCalendar.getText().toString();

            if (txtConstructionName.getText().length() == 0
                    || txtCategoryName.getText().length() == 0
                    || edtRevenue.getText().length() == 0
                    || txtNamePersonInCharge.getText().length() == 0
                    || txtStartCalendar.getText().length() == 0
                    || txtEndCalendar.getText().length() == 0) {
                Toast.makeText(this, "Bạn chưa chọn đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
                // dùng để put vào api trường month
                if (!start.equals("") && !end.equals("")) {
                    Date startDate = ConvertStringToDate(start);
                    Date endDate = ConvertStringToDate(end);
                    String startMonth = getCurretnTimeStampMonth(startDate).trim();
                    String endMonth = getCurretnTimeStampMonth(endDate).trim();
                    String startYear = getCurretnTimeStampYear(startDate).trim();
                    String endYear = getCurretnTimeStampYear(endDate).trim();
                    if (startDate.before(endDate) || startDate.equals(endDate) || endDate.equals(startDate)) {
                        if (startYear.equals(endYear)) {
                            if (startMonth.equals(endMonth)) {
                                onSaveData();
                            } else {
                                Toast.makeText(this, "Bạn phải tạo công việc cùng tháng.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Bạn phải tạo công việc cùng năm.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Ngày bắt đầu hiện đang lớn hơn ngày kết thúc.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Bạn chưa nhập dữ liệu ngày bắt đầu và ngày kết thúc.", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void onSaveData() {
        String sNewRevenue = "";
        String sRevenue = edtRevenue.getText().toString().trim();
        if (sRevenue.contains(",")) {
            sNewRevenue = sRevenue.replace(",", "");
        }

        String constructionName = construction1.getName();
        String constructionCode = construction1.getConstructionCode();
        long constructionID = construction1.getConstructionId();

        String workItemName = construction2.getName();
        String workItemCode = construction2.getWorkItemCode();
        long workItemId = construction2.getWorkItemId();

//        String catTaskName = construction3.getName();
//        String catTaskCode = construction3.getCatTaskCode();
//        long catTaskId = construction3.getCatTaskId();

        ConstructionTaskDetailDTO dto = new ConstructionTaskDetailDTO();

        dto.setConstructionName(constructionName);
        dto.setConstructionCode(constructionCode);
        dto.setConstructionId(constructionID);

        dto.setWorkItemName(workItemName);
        dto.setWorkItemId(workItemId);
        dto.setWorkItemCode(workItemCode);

//        dto.setTaskName(catTaskName);
//        dto.setCatTaskCode(catTaskCode);
//        dto.setCatTaskId(catTaskId);

        dto.setPerformerIdDetail(performerId);
        dto.setPerformerName(performerName);

        dto.setStartDate(txtStartCalendar.getText().toString().trim());
        dto.setEndDate(txtEndCalendar.getText().toString().trim());
        dto.setQuantity(sNewRevenue);
        dto.setType("1");

        ApiManager.getInstance().createNewWork(dto, ResultApi.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ResultApi resultApi = ResultApi.class.cast(result);
                    ResultInfo resultInfo = resultApi.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        Toast.makeText(CreateNewCvCameraActivity.this, "Tạo công việc thành công", Toast.LENGTH_SHORT).show();
                        // key check load lại dữ liệu các màn hình list
                        App.getInstance().setNeedUpdateAfterCreateNewWork(true);
                        txtStartCalendar.setText("");
                        txtEndCalendar.setText("");
                        edtRevenue.setText("");
                        txtNamePersonInCharge.setText("");
    //                    finish();
                    } else
                        Toast.makeText(CreateNewCvCameraActivity.this, resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(CreateNewCvCameraActivity.this, "Tạo công việc thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
