package com.viettel.construction.screens.commons;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;

import java.util.Date;

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
import com.viettel.construction.appbase.BaseCameraActivity;


public class CreateNewCvOther2CameraActivity extends BaseCameraActivity {
    @BindView(R.id.txtCancel)
    TextView tvCancel;
    @BindView(R.id.tv_construction_name)
    TextView tvConstruction;
    @BindView(R.id.tv_performer)
    TextView tvPerformer;
    @BindView(R.id.tv_start_time_complete)
    TextView tvStartTime;
    @BindView(R.id.tv_finish_time_complete)
    TextView tvFinishTime;
    @BindView(R.id.imv_contruction)
    RelativeLayout imvContrustion;
    @BindView(R.id.imv_performer)
    RelativeLayout imvPerformer;
    @BindView(R.id.imv_calendar1)
    RelativeLayout imvCalendar1;
    @BindView(R.id.imv_calendar2)
    RelativeLayout imvCalendar2;
    @BindView(R.id.txtSave)
    TextView btnSave;
    @BindView(R.id.edt_content_work)
    EditText edtContentWork;
    @BindView(R.id.txtHeader)
    TextView txtHeader;

    private Intent intent;
    private long idConstruction = 0;
    private long idCategory = 0;
    private long idWork = 0;
    private Long performerId;
    private String performerName;
    private ConstructionStationWorkItem construction1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity_create_new_work_other_2
        setContentView(R.layout.activity_create_new_work_hshc);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        ButterKnife.bind(this);
        txtHeader.setText(R.string.create_new_cv);
    }

    @OnClick(R.id.imv_calendar1)
    public void onClickShowStartCalendar() {
        setTime(tvStartTime);
    }

    @OnClick(R.id.imv_calendar2)
    public void onClickShowEndCalendar() {
        setTime(tvFinishTime);
    }

    @OnClick(R.id.imv_contruction)
    public void OnClickChooseConstruction() {
        try {
            edtContentWork.setText("");
//        tvPerformer.setText("");
//        tvStartTime.setText("");
//        tvFinishTime.setText("");
            intent = new Intent(this, SelectConstructionCameraActivity.class);
            this.startActivityForResult(intent, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.imv_performer)
    public void OnClickChoosePersonInCharge() {
//        tvStartTime.setText("");
//        tvFinishTime.setText("");
        try {
            intent = new Intent(this, SelectEmployeeCameraActivity.class);
            startActivityForResult(intent, 2);
        } catch (Exception e) {
            e.printStackTrace();
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
                        if (data.getExtras().getSerializable("resultIntent") != null) {
                            construction1 = (ConstructionStationWorkItem) data.getSerializableExtra("resultIntent");
                            tvConstruction.setText(construction1.getConstructionCode());
                            idConstruction = construction1.getConstructionId();
    //                        edtContentWork.setText(this.getString(R.string.hshc_work, construction1.getConstructionCode()));
                        }
                    }
                    break;
                case 2:
                    if (resultCode == 3) {
                        EmployeeApi employee = (EmployeeApi) data.getSerializableExtra("employeeResult");
                        performerId = Long.parseLong(employee.getSysUserId());
                        performerName = employee.getFullName();
                        tvPerformer.setText(employee.getFullName());
                    }
                    break;
                default:
                    break;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.txtCancel)
    public void onClickCancel() {
        finish();
    }

    @OnClick(R.id.txtSave)
    public void onClickSave() {
        try {
            String start = tvStartTime.getText().toString();
            String end = tvFinishTime.getText().toString();
            if (edtContentWork.getText().toString().length() == 0
                    || tvPerformer.getText().length() == 0
                    || tvStartTime.getText().length() == 0
                    || tvFinishTime.getText().length() == 0) {
                Toast.makeText(this, "Bạn chưa chọn đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
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
                                Toast.makeText(this, "Bạn phải tạo công việc cùng tháng!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Bạn phải tạo công việc cùng năm!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Ngày bắt đầu hiện đang lớn hơn ngày kết thúc!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Bạn chưa nhập dữ liệu ngày bắt đầu và ngày kết thúc!", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void onSaveData() {

        ConstructionTaskDetailDTO dto = new ConstructionTaskDetailDTO();

        if (construction1 != null) {
            String constructionName = construction1.getName();
            String constructionCode = construction1.getConstructionCode();
            long constructionID = construction1.getConstructionId();

            dto.setConstructionName(constructionName);
            dto.setConstructionCode(constructionCode);
            dto.setConstructionId(constructionID);
        }

        dto.setTaskName(edtContentWork.getText().toString().trim());


        dto.setPerformerIdDetail(performerId);
        dto.setPerformerName(performerName);
        dto.setTaskName(edtContentWork.getText().toString().trim());
        dto.setStartDate(tvStartTime.getText().toString().trim());
        dto.setEndDate(tvFinishTime.getText().toString().trim());
//        dto.setCatTaskId();
        dto.setType("6");

        ApiManager.getInstance().createNewWork(dto, ResultApi.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ResultApi resultApi = ResultApi.class.cast(result);
                    ResultInfo resultInfo = resultApi.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        Toast.makeText(CreateNewCvOther2CameraActivity.this, "Tạo công việc thành công", Toast.LENGTH_SHORT).show();
                        // key check load lại dữ liệu các màn hình list
                        App.getInstance().setNeedUpdateAfterCreateNewWork(true);
                        tvConstruction.setText("");
                        tvPerformer.setText("");
                        tvStartTime.setText("");
                        tvFinishTime.setText("");
                        edtContentWork.setText("");
    //                    finish();
                    } else
                        Toast.makeText(CreateNewCvOther2CameraActivity.this, resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(CreateNewCvOther2CameraActivity.this, "Tạo công việc thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
