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
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.screens.atemp.adapter.ImageInProcessAdapter;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.api.ConstructionScheduleWorkItemDTO;
import com.viettel.construction.model.api.ConstructionTaskDTO;
import com.viettel.construction.model.api.EmployeeApi;
import com.viettel.construction.model.api.ResultApi;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.util.ImageUtils;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.screens.custom.dialog.CustomProgress;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DetailInProcessChildCameraActivity extends BaseCameraActivity implements ImageInProcessAdapter.OnClickDelete {

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
    @BindView(R.id.btn_next)
    LinearLayout btnNext;
    @BindView(R.id.rv_list_image_in_process)
    RecyclerView mRecyclerView;
    @BindView(R.id.edt_process)
    EditText edtProcess;
    @BindView(R.id.edt_description)
    EditText edtDescription;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.img_status)
    ImageView imgStatus;
    private int checkUpload;

    private String isStop;
    private long currentPerformerId, afterPerformerId;
    private Double percent;
    private String filePath = "";
    private ImageInProcessAdapter mAdapter;
    public static ArrayList<ConstructionImageInfo> mListUrl;
    public ArrayList<ConstructionImageInfo> mListUrlUpload;
    private ConstructionTaskDTO mObjWork;
    private ConstructionScheduleWorkItemDTO workItemDTO;
    private String imageBase64;
    private String email, phone;
    private long sysUserId, sysGroupId;
    @BindView(R.id.prg_loading)
    CustomProgress progress;

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
        setContentView(R.layout.activity_detail_cv_inprocess_child);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        ButterKnife.bind(this);
        progress.setLoading(true);
        initData();
        initListImageFromServer();
        setUpView();
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
                            Toast.makeText(DetailInProcessChildCameraActivity.this, "Người dùng không có ảnh !", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(DetailInProcessChildCameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                progress.setLoading(false);
            }
        });
    }



    private void setUpView() {
        mAdapter = new ImageInProcessAdapter(mListUrl, this, true, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        mListUrlUpload = new ArrayList<>();
        mListUrl = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle.getSerializable(VConstant.BUNDLE_KEY_OBJECT_CV) != null) {
            workItemDTO = (ConstructionScheduleWorkItemDTO) bundle.getSerializable(VConstant.BUNDLE_KEY_OBJECT_CV);
            // get id of user cho chuyen nguoi
            currentPerformerId = mObjWork.getPerformerId();
            mTVContentName.setText(mObjWork.getTaskName());
            mTVConstructionName.setText(mObjWork.getConstructionCode());
            mTVCategoryName.setText(mObjWork.getWorkItemName());
            if (mObjWork.getDescription() != null) {
                edtDescription.setText(mObjWork.getDescription().toString().trim());
            }
            isStop = mObjWork.getStatus();
            if (mObjWork.getCompletePercent() != null) {
                percent = Double.parseDouble(mObjWork.getCompletePercent());
                edtProcess.setText(percent.intValue() + "");
            } else {
                percent = 0d;
                edtProcess.setText(0 + "");
            }
            if (isStop.equals(String.valueOf(VConstant.ON_PAUSE).trim())) {
                txtStatus.setText(getResources().getString(R.string.status_continue));
                imgStatus.setImageResource(R.drawable.accept);
                disableEditText(edtDescription);
                disableEditText(edtProcess);
                mBtnCamera.setClickable(false);
                btnSave.setClickable(false);
            } else {
                txtStatus.setText(getResources().getString(R.string.status_pause));
                imgStatus.setImageResource(R.drawable.ic_stop_signal);
                enableEditText(edtDescription);
                enableEditText(edtProcess);
                mBtnCamera.setClickable(true);
            }

            mTVEstimateTime.setText(getString(R.string.start_date_end_date, mObjWork.getStartDate(), mObjWork.getEndDate()));
        }
        // k có data , ko su dung ham nay`
        if (getIntent().getExtras().getString("111111111") != null) {
//            filePath = getIntent().getStringExtra(VConstant.RECEIVE_URL);
//            if (!StringUtil.isNullOrEmpty(filePath)) {
//                //upload image to server
//                Bitmap bitmap = ImageUtils.decodeBitmapFromFile(filePath, 360, 240);
////                Glide.with(this).load(new File(filePath)).into(imgUpload);
//                imageBase64 = StringUtil.getStringImage(bitmap);
//                Logger.d(filePath);
//                Logger.d(imageBase64);
//            }
        }
//        mListUrl = new ArrayList<>();
//        mListUrl.clear();
//        mListUrl.addAll(FakeData.ListUrlImage());
    }

//    public void onLaunchCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Create a File reference to access to future access
//        String fileName = FileUtils.getFileName();
//        mPhotoFile = FileUtils.getInstance().getPhotoFileUri(this, fileName);
//        // required for API >= 24
//        Uri fileProvider = FileProvider.getUriForFile(this,
//                getResources().getString(R.string.camera_provider), mPhotoFile);
//
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
//            intent.setClipData(ClipData.newRawUri("", fileProvider));
//            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        }
//        // check app can handle
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            // Start the image capture intent to take photo
//            startActivityForResult(intent, VConstant.REQUEST_CODE_CAMERA);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {

                case VConstant.REQUEST_CODE_CAMERA:
                    if (resultCode == Activity.RESULT_OK) {


                        //=============================== data of image

                        filePath = mPhotoFile.getPath();
                        Bitmap bitmap = ImageUtils.decodeBitmapFromFile(filePath, 200, 200);
                        Bitmap newBitmap = ImageUtils.drawTextOnImage(bitmap, latitude, longitude, mObjWork.getConstructionCode(), mObjWork.getWorkItemName());

                        ConstructionImageInfo imageInfo = new ConstructionImageInfo();

                        imageInfo.setStatus(0);
                        imageInfo.setBase64String(StringUtil.getStringImage(newBitmap));
                        imageInfo.setImageName(filePath);
                        imageInfo.setLatitude(latitude);
                        imageInfo.setLongtitude(longitude);
    //                    constructionImageInfo.setImagePath("");

    //                    Toast.makeText(this, latitude + longitude + "", Toast.LENGTH_SHORT).show();

                        mListUrl.add(0, imageInfo);
                        mAdapter.notifyItemInserted(0);
    //                    finish();
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
        try {
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_next)
    public void onClickNext() {
        try {
            Intent intent = new Intent(this, SelectEmployeeCameraActivity.class);
            startActivityForResult(intent, 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_stop)
    public void onClickStop() {
        try {
            if (isStop.equals(String.valueOf(VConstant.ON_PAUSE).trim())) {
                enableEditText(edtDescription);
                enableEditText(edtProcess);
                mBtnCamera.setClickable(true);
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

    private void updateWork(Double process, Long sysUserId, Long sysGroupId, String email, String phone, String des, int flag) {
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

            int svPercent = percent.intValue();
            int edtPercent = Integer.parseInt(edtProcess.getText().toString().trim());
            if (flag == 1 && (svPercent == edtPercent)) {

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
        ApiManager.getInstance().updateConstructionTask(process, mListUrlUpload, mObjWork, flag, email, phone, sysUserId, sysGroupId, des, ResultApi.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ResultApi resultApi = ResultApi.class.cast(result);
                    if (resultApi.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        Toast.makeText(DetailInProcessChildCameraActivity.this, getResources().getString(R.string.updated), Toast.LENGTH_SHORT).show();
                        progress.setLoading(false);
                        App.getInstance().setNeedUpdateAfterSaveDetail(true);
                        finish();
                    } else {
                        Toast.makeText(DetailInProcessChildCameraActivity.this, getResources().getString(R.string.update_fail), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(DetailInProcessChildCameraActivity.this, "Network error !", Toast.LENGTH_SHORT).show();
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
            String description = edtDescription.getText().toString().trim();
            if (checkValidate(edtProcess, filePath)) {
                if (checkValidate(edtDescription)) {
                    progress.setLoading(true);
                    if (checkValidate(edtDescription)) {
                        if (afterPerformerId > 0) {
                            if (currentPerformerId != afterPerformerId) {
                                updateWork(Double.parseDouble(process), sysUserId, sysGroupId, email, phone, description, 1);
                            } else {
                                updateWork(Double.parseDouble(process), mObjWork.getPerformerId(), mObjWork.getSysGroupId(), email, phone, description, 0);
                            }
                        } else {
                            updateWork(Double.parseDouble(process), mObjWork.getPerformerId(), mObjWork.getSysGroupId(), email, phone, description, 0);
                        }
                    } else
                        Toast.makeText(this, getResources().getString(R.string.please_choose_category), Toast.LENGTH_SHORT).show();
                } else {
                    if (filePath == null)
                        Toast.makeText(this, getResources().getString(R.string.please_take_image), Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(this, getResources().getString(R.string.please_choose), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
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
}
