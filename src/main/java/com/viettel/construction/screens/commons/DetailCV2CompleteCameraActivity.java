package com.viettel.construction.screens.commons;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.screens.atemp.adapter.ConstructionSpnAdapter;
import com.viettel.construction.screens.atemp.adapter.ImageAppParamAdapter;
import com.viettel.construction.screens.atemp.adapter.SpinnerAdapter;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.version.AppParamDTO;
import com.viettel.construction.model.api.AppParamManageDto;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.api.ConstructionTaskDTO;
import com.viettel.construction.model.api.EmployeeApi;
import com.viettel.construction.model.api.ResultApi;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.util.ImageUtils;
import com.viettel.construction.appbase.BaseCameraActivity;

import com.viettel.construction.screens.custom.dialog.CustomProgress;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Chi tiết sản lượng theo ngày đã hoàn thành
 */
public class DetailCV2CompleteCameraActivity extends BaseCameraActivity
        implements ImageAppParamAdapter.OnClickDelete,
        ConstructionSpnAdapter.OnClickParams {

    @BindView(R.id.tv_content_cv_in_process)
    TextView mTVContentName;
    @BindView(R.id.tv_construction_name_in_process)
    TextView mTVConstructionName;
    @BindView(R.id.tv_category_name_in_process)
    TextView mTVCategoryName;
    @BindView(R.id.tv_estimate_time_in_process)
    TextView mTVEstimateTime;
    @BindView(R.id.btn_camera)
    ImageView mBtnCamera;
    @BindView(R.id.btn_details_inprocess)
    TextView mTvBtnCancel;
    @BindView(R.id.txt_save)
    TextView btnSave;
    @BindView(R.id.btn_stop)
    LinearLayout btnStop;

    @BindView(R.id.rv_list_image_in_process)
    RecyclerView mRecyclerView;
    @BindView(R.id.rcv_construction_spn)
    RecyclerView rcvSpn;
    @BindView(R.id.edt_process)
    EditText edtProcess;
    @BindView(R.id.edt_amount)
    EditText edtAmount;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.img_status)
    ImageView imgStatus;
    @BindView(R.id.btn_add)
    ImageView imgAdd;
    @BindView(R.id.spn_construction)
    Spinner spnConstruction;
    private ConstructionSpnAdapter constructionSpnAdapter;
    private List<AppParamDTO> listAppParam = new ArrayList<>();
    private int checkUpload;
    private String isStop;
    private long currentPerformerId, afterPerformerId;
    private Double percent;
    private String filePath = "";
    private ImageAppParamAdapter mAdapter;
    public static List<ConstructionImageInfo> mListUrl;
    public List<ConstructionImageInfo> mListUrlUpload;
    private ConstructionTaskDTO mObjWork;
    private String imageBase64;
    private String email, phone;
    private long sysUserId, sysGroupId;
    @BindView(R.id.prg_loading)
    CustomProgress progress;
    private List<AppParamDTO> listSpn = new ArrayList<>();
    private SpinnerAdapter spinnerAdapter;
    private AppParamDTO appParamDTO;
    private boolean isCheck = false;
    private Double processParams;
    private Double sumParams;
    private Double sum = 0d;
    private int round = 0;
    private Double amountPreview;
    private int confirmDaily = 0;

    @Override
    protected void onResume() {
        super.onResume();
        if (App.getInstance().isNeedUpdate()) {
            finish();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cv_2_complete);
        ButterKnife.bind(this);
        progress.setLoading(true);
        initData();
        initListImageFromServer();
        setUpView();
        getListConstructionSpinner();
    }


    private void initListImageFromServer() {
        ApiManager.getInstance().loadImage(mObjWork, ResultApi.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ResultApi resultApi = ResultApi.class.cast(result);
                    if (resultApi.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        if (resultApi.getListImage() != null) {
                            // add image to list
                            mListUrl.addAll(resultApi.getListImage());
                            setUpView();
                            progress.setLoading(false);
                        } else {
                            progress.setLoading(false);
                            Toast.makeText(DetailCV2CompleteCameraActivity.this, "Người dùng không có ảnh !", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                progress.setLoading(false);
                Toast.makeText(DetailCV2CompleteCameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setUpView() {

        //recyclerview
//        listAppParam = new ArrayList<>();
        constructionSpnAdapter = new ConstructionSpnAdapter(this,false, listAppParam, this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        rcvSpn.setLayoutManager(linearLayoutManager2);
        rcvSpn.setAdapter(constructionSpnAdapter);

        //=============================
        mAdapter = new ImageAppParamAdapter(3,mListUrl, this, true, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //TODO : test data
        //spinner
//        ArrayAdapter<AppParamDTO> dataAdapter = new ArrayAdapter<AppParamDTO>(this, android.R.layout.simple_spinner_item, listSpn);
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // attaching data adapter to spinner
//        spnConstruction.setAdapter(dataAdapter);
        spinnerAdapter = new SpinnerAdapter(this, listSpn);
        spnConstruction.setAdapter(spinnerAdapter);
    }

    private void initData() {
        // ============================================
        mListUrlUpload = new ArrayList<>();
        mListUrl = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle.getSerializable(VConstant.BUNDLE_KEY_OBJECT_CV) != null) {
            mObjWork = (ConstructionTaskDTO) bundle.getSerializable(VConstant.BUNDLE_KEY_OBJECT_CV);

            //get sum params from model
            if (mObjWork.getAmount() != null) {
                sumParams = mObjWork.getAmount();
                if (sumParams == 0) {
                    Toast.makeText(this, getString(R.string.dont_update), Toast.LENGTH_SHORT).show();
                    disableEditText(edtAmount);
                }
            } else {
//                sumParams = 1000d;
                sumParams = 0d;
                disableEditText(edtAmount);
                edtProcess.setText("0");
                Toast.makeText(this, getString(R.string.dont_update), Toast.LENGTH_SHORT).show();
            }

            // get id of user cho chuyen nguoi
            currentPerformerId = mObjWork.getPerformerId();
            mTVContentName.setText(mObjWork.getTaskName());
            mTVConstructionName.setText(mObjWork.getConstructionCode());
            mTVCategoryName.setText(mObjWork.getWorkItemName());
            if (mObjWork.getDescription() != null) {
//                edtDescription.setText(mObjWork.getDescription().toString().trim());
            }
            isStop = mObjWork.getStatus();
            if (mObjWork.getCompletePercent() != null) {
                percent = Double.parseDouble(mObjWork.getCompletePercent());
//                edtProcess.setText(percent.intValue() + "");
            } else {
                percent = 0d;
//                edtProcess.setText(0 + "");
            }
            if (isStop.equals(String.valueOf(VConstant.ON_PAUSE).trim())) {
                txtStatus.setText(getResources().getString(R.string.status_continue));
                imgStatus.setImageResource(R.drawable.accept);
//                disableEditText(edtDescription);
                disableEditText(edtProcess);
                mBtnCamera.setClickable(false);
                btnSave.setClickable(false);
            } else {
                txtStatus.setText(getResources().getString(R.string.status_pause));
                imgStatus.setImageResource(R.drawable.ic_stop_signal);
                mBtnCamera.setClickable(false);
            }

            mTVEstimateTime.setText(getString(R.string.start_date_end_date, mObjWork.getStartDate(), mObjWork.getEndDate()));
        }
    }


    private void getListConstructionSpinner() {
        ApiManager.getInstance().getListConstructionSpinner(mObjWork, AppParamManageDto.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    AppParamManageDto dto = AppParamManageDto.class.cast(result);
                    if (dto.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        if (dto.getListAppParam() != null)
                            listSpn.addAll(dto.getListAppParam());
                        if (dto.getListDetail() != null)
                            listAppParam.addAll(dto.getListDetail());
                        if (dto.getProcess() != null) {
                            processParams = dto.getProcess();
                            String format = processParams + "";
                            if (format.contains(".0")) {
                                String newFormat = format.replace(".0", "");
                                edtProcess.setText(newFormat);
                            } else {
                                edtProcess.setText(format);
                            }
                        }

                        if (dto.getAmountPreview() != null && dto.getAmountPreview() > 0) {
                            amountPreview = dto.getAmountPreview();
                        } else {
                            amountPreview = 0d;
                        }

                        confirmDaily = dto.getConfirmDaily();

                        if (confirmDaily == 0) {
                            //dont something
                            //                        imgAdd.setClickable(true);
                        } else {
                            //                        imgAdd.setClickable(false);
                        }

                        spinnerAdapter.notifyDataSetChanged();
                        constructionSpnAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(DetailCV2CompleteCameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(DetailCV2CompleteCameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        try {
            switch (requestCode) {

                case VConstant.REQUEST_CODE_CAMERA:
                    if (resultCode == Activity.RESULT_OK) {
                        filePath = mPhotoFile.getPath();
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String pictureName = "IMG_" + timeStamp + ".jpg";
                        Bitmap bitmap = ImageUtils.decodeBitmapFromFile(filePath, 200, 200);
                        Bitmap newBitmap = ImageUtils.drawTextOnImage(bitmap, latitude, longitude);
                        ConstructionImageInfo imageInfo = new ConstructionImageInfo();
                        imageInfo.setStatus(0);
                        imageInfo.setBase64String(StringUtil.getStringImage(newBitmap));
                        imageInfo.setImageName(pictureName);
                        imageInfo.setLatitude(latitude);
                        imageInfo.setLongtitude(longitude);
                        mListUrl.add(0, imageInfo);
                        mAdapter.notifyItemInserted(0);
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        /** Picture wasn't taken*/
                    }
                    break;
                case 3:
                    if (resultCode == 3) {
                        EmployeeApi employee = (EmployeeApi) data.getSerializableExtra("employeeResult");
                        afterPerformerId = Long.parseLong(employee.getSysUserId());
                        if (currentPerformerId != afterPerformerId) {
                            btnSave.setClickable(true);
                        } else {
                            btnSave.setClickable(false);
                            Toast.makeText(this, "Vui lòng chuyển người khác !!!", Toast.LENGTH_SHORT).show();
                        }

                        phone = employee.getPhoneNumber();
                        email = employee.getEmail();
                        sysUserId = Long.parseLong(employee.getSysUserId());
                        sysGroupId = Long.parseLong(employee.getDepartmentId());
                        btnSave.setClickable(true);
                    }

                    break;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.btn_details_inprocess)
    public void onClickBtnCancel() {
        finish();
    }


    @OnClick(R.id.btn_stop)
    public void onClickStop() {
        try {
            if (isStop.equals(String.valueOf(VConstant.ON_PAUSE).trim())) {
                //            enableEditText(edtDescription);
                //            enableEditText(edtProcess);
                mBtnCamera.setClickable(false);
                btnStop.setClickable(false);
                btnSave.setClickable(true);
                Toast.makeText(this, "Đã chuyển sang trạng thái tiếp tục!", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(this, DashboardCVStopReflectActivity.class);
                intent.putExtra("constructionTaskId", mObjWork);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // constructionTask performerId

    private void updateWork(List<AppParamDTO> listAppParam, Double process, Long
            sysUserId, Long sysGroupId, String email, String phone, String des, int flag) {
        //filter list image for upload image , only status -1 , 0
        for (int i = 0; i < mListUrl.size(); i++) {
            if (mListUrl.get(i).getStatus() == 0) {
                checkUpload = checkUpload + 1;
                mListUrlUpload.add(mListUrl.get(i));
                String splitFormatImage = mListUrlUpload.get(i).getImageName() + ".jpg";
                mListUrlUpload.get(i).setImageName(splitFormatImage);
//                if (mListUrlUpload.get(i).getUtilAttachDocumentId() == 0)
//                    mListUrlUpload.remove(i);
            }
        }

        if (checkUpload == 0) {

            String mProcess = edtProcess.getText().toString().trim();
            Double newProcess = Double.parseDouble(mProcess);
            int svPercent = percent.intValue();
            if (flag == 1 && (svPercent == newProcess.intValue())) {

                // dont something

            } else {
                Toast.makeText(this, getResources().getString(R.string.please_take_image), Toast.LENGTH_SHORT).show();
                progress.setLoading(false);
                return;
            }
        }

        mTvBtnCancel.setClickable(false);
        btnSave.setClickable(false);

        //test api fail
//        mObjWork.setDescription("");
//        mObjWork.setMonth("5");
//        mObjWork.setPath("/6536/6560/6561/6562/");
//        mObjWork.setPerformerName("Trương Xuân Phú");
//        mObjWork.setYear("2018");
        ApiManager.getInstance().updateConstructionTaskAppParam(listAppParam, process, mListUrlUpload, mObjWork, flag, email, phone, sysUserId, sysGroupId, des, ResultApi.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ResultApi resultApi = ResultApi.class.cast(result);
                    if (resultApi.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        Toast.makeText(DetailCV2CompleteCameraActivity.this, getResources().getString(R.string.updated), Toast.LENGTH_SHORT).show();
                        progress.setLoading(false);
                        App.getInstance().setNeedUpdateAfterSaveDetail(true);
                        finish();
                    } else {
                        Toast.makeText(DetailCV2CompleteCameraActivity.this, getResources().getString(R.string.update_fail), Toast.LENGTH_SHORT).show();
                        progress.setLoading(false);
                        mTvBtnCancel.setClickable(true);
                        btnSave.setClickable(true);
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(DetailCV2CompleteCameraActivity.this, "Network error !", Toast.LENGTH_SHORT).show();
                progress.setLoading(false);
                mTvBtnCancel.setClickable(true);
                btnSave.setClickable(true);
            }
        });
    }

    @OnClick(R.id.txt_save)
    public void onSave() {
        try {
            String process = edtProcess.getText().toString().trim();
//        Long abc = Long.parseLong(process);
//        String description = edtDescription.getText().toString().trim();

            //validate for appParam
            for (int i = 0; i < listAppParam.size(); i++) {
                if (listAppParam.get(i).getAmount().trim() == null
                        || listAppParam.get(i).getAmount().trim().isEmpty()
                        || listAppParam.get(i).getAmount().trim().length() == 0) {
                    Toast.makeText(this, getResources().getString(R.string.please_insert_param), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    sum += Double.parseDouble(listAppParam.get(i).getAmount());
                }
            }

            if (sum <= sumParams) {
                if (filePath != null && filePath.length() > 0) {
                    sum = 0d;
                    //            if (checkValidate(edtDescription)) {
                    progress.setLoading(true);
                    //                if (checkValidate(edtDescription)) {
                    if (afterPerformerId > 0) {
                        if (currentPerformerId != afterPerformerId) {
                            updateWork(listAppParam, Double.parseDouble(process), sysUserId, sysGroupId, email, phone, "", 1);
                        } else {
                            updateWork(listAppParam, Double.parseDouble(process), mObjWork.getPerformerId(), mObjWork.getSysGroupId(), email, phone, "", 0);
                        }
                    } else {
                        updateWork(listAppParam, Double.parseDouble(process), mObjWork.getPerformerId(), mObjWork.getSysGroupId(), email, phone, "", 0);
                    }
                    //                }
                    //                else
                    //                    Toast.makeText(this, getResources().getString(R.string.please_choose_category), Toast.LENGTH_SHORT).show();
                    //            }
                    //            else {
                    //                if (filePath == null)
                    //                    Toast.makeText(this, getResources().getString(R.string.please_take_image), Toast.LENGTH_SHORT).show();
                    //                else {
                    //                    Toast.makeText(this, getResources().getString(R.string.please_choose), Toast.LENGTH_SHORT).show();
                    //                }
                    //            }
                } else {
                    sum = 0d;
                    Toast.makeText(this, getResources().getString(R.string.please_take_image), Toast.LENGTH_SHORT).show();
                }
            } else {
                edtAmount.requestFocus();
                edtAmount.setError("Vui lòng cập nhật lại dữ liệu !");
                sum = 0d;
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDelete(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa ảnh");
        builder.setMessage("Bạn có muốn xóa ảnh ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                mListUrl.get(pos).setStatus(-1);
                if (mListUrl.get(pos).getUtilAttachDocumentId() > 0)
                    mListUrlUpload.add(mListUrl.get(pos));
                mListUrl.remove(pos);
                mAdapter.notifyItemRemoved(pos);
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.c5));
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.c5));

    }

    @Override
    public void onClick(int pos) {
        if (listAppParam.get(pos).getConfirm().equals("0")) {
            listAppParam.remove(pos);
            constructionSpnAdapter.notifyItemRemoved(pos);
            constructionSpnAdapter.notifyDataSetChanged();

            for (int i = 0; i < listAppParam.size(); i++) {
                if (listAppParam.get(i).getAmount().trim() == null
                        || listAppParam.get(i).getAmount().trim().isEmpty()
                        || listAppParam.get(i).getAmount().trim().length() == 0) {
                    Toast.makeText(this, getResources().getString(R.string.please_insert_param), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    sum += Double.parseDouble(listAppParam.get(i).getAmount());
                }
            }

            double test = ((sum + amountPreview) / sumParams) * 100;
            round = (int) (Math.round(test * 10) / 10);
            edtProcess.setText(round + "");
            sum = 0d;

        } else {
            Toast.makeText(this, getString(R.string.dont_delete_form), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onChangeValue() {
        checkSum();
    }

    private void checkSum() {
        for (int i = 0; i < listAppParam.size(); i++) {
            if (listAppParam.get(i).getAmount().trim() == null
                    || listAppParam.get(i).getAmount().trim().isEmpty()
                    || listAppParam.get(i).getAmount().trim().length() == 0) {
                Toast.makeText(this, getResources().getString(R.string.please_insert_param), Toast.LENGTH_SHORT).show();
                return;
            } else {
                sum += Double.parseDouble(listAppParam.get(i).getAmount());
            }
        }

        if (sum > sumParams) {
            String sParam = String.valueOf(sumParams);
            String sNewParam = "";
            if (sParam.contains(".0")) {
                sNewParam = sParam.replace(".0", "");
            }
            edtAmount.requestFocus();
            edtAmount.setError("Tổng khối lượng không được quá " + sNewParam);
            sum = 0d;
            return;
        }

        double test = ((sum + amountPreview) / sumParams) * 100;
        round = (int) (Math.round(test * 10) / 10);
        edtProcess.setText(round + "");
        sum = 0d;

    }
}

