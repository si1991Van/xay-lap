package com.viettel.construction.screens.commons;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.model.api.ConstructionScheduleItemDTO;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.WorkItemDetailDTO;
import com.viettel.construction.model.api.WorkItemDetailDTORespone;
import com.viettel.construction.model.db.ImageCapture;
import com.viettel.construction.screens.atemp.adapter.ImageInProcessAdapter;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.common.ParramConstant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.api.ConstructionTaskDTO;
import com.viettel.construction.model.api.ResultApi;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.util.ImageUtils;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.screens.custom.dialog.CustomProgress;

/***
 * Chi tiết công việc đang trong quá trình thực hiện
 */

public class DetailInProcessCameraActivity extends BaseCameraActivity
        implements ImageInProcessAdapter.OnClickDelete {
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

    @BindView(R.id.txtSave)
    TextView btnSave;
    @BindView(R.id.btn_stop)
    LinearLayout btnStop;

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

    @BindView(R.id.ln_vuong_ct)
    LinearLayout lnVuongCt;
    @BindView(R.id.chkVuongCT)
    CheckBox cbVuongCt;
    @BindView(R.id.chkVuongKTDB)
    CheckBox cbVuongKTDB;

    @BindView(R.id.lnFooterStoping)
    LinearLayout mStopContinueContainer;

    private String isStop;
    private Double percent;
    private String filePath = "";
    private ImageInProcessAdapter mAdapter;
    public static ArrayList<ConstructionImageInfo> mListUrl;
    public ArrayList<ConstructionImageInfo> mListUrlUpload;
    private ConstructionTaskDTO mObjWork;
    private ConstructionScheduleItemDTO constructionScheduleItemDTO;
    private String email, phone;
    private final String TAG = "VTDeInProActivity";
    @BindView(R.id.prg_loading)
    CustomProgress progress;
    private long checkImage = 0;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    private String strOldPercent = "0";
    private boolean workItemStatusAvailable = false;

    private boolean allowConfirmWorkItem = false;
    private AlertDialog dialogConfirmWorkItem;

    private ProgressDialog dialogLoading;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cv_inprocess);
        ButterKnife.bind(this);
        txtHeader.setText(R.string.updateProgress);
        progress.setLoading(true);
        initData();
        initListImageFromServer();
        setUpView();
        setUpEditText();
    }

    @OnClick(R.id.btnSelectImage)
    public void selectedImage() {
        Intent selectImage = new Intent(this, SelectImagesActivity.class);
        startActivityForResult(selectImage, ParramConstant.SelectedImage_RequestCode);
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
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(int statusCode) {
                progress.setLoading(false);
            }
        });
    }



    private void setUpEditText() {
        edtProcess.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (editable.toString().trim().length() > 0) {
                        int input = Integer.parseInt(editable.toString());
                        if (input > 100 || input < 0) {
                            edtProcess.setError(getString(R.string.PercentRangWarning));
                        }
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialogLoading = null;
    }

    private void initData() {

        dialogLoading = new ProgressDialog(this);
        dialogLoading.setMessage("Đang xử lý dữ liệu...");
        dialogLoading.setCancelable(false);

        if (getIntent().getExtras() != null && getIntent().getSerializableExtra(ParramConstant.ConstructionScheduleItemDTO) != null) {
            allowConfirmWorkItem = getIntent().getBooleanExtra(ParramConstant.AllowConfirmWorkItem, false);
            constructionScheduleItemDTO = (ConstructionScheduleItemDTO)
                    getIntent().getSerializableExtra(ParramConstant.ConstructionScheduleItemDTO);
        }

        if (getIntent().getExtras() != null && getIntent().getSerializableExtra(ParramConstant.CheckImage) != null) {
            checkImage = getIntent().getLongExtra(ParramConstant.CheckImage, 0);
        }

        mListUrlUpload = new ArrayList<>();
        mListUrl = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle.getSerializable(VConstant.BUNDLE_KEY_OBJECT_CV) != null) {
            mObjWork = (ConstructionTaskDTO) bundle.getSerializable(VConstant.BUNDLE_KEY_OBJECT_CV);
            strOldPercent = mObjWork.getCompletePercent() == null ? "0" : mObjWork.getCompletePercent();
            // get id of user cho chuyen nguoi
            mTVContentName.setText(mObjWork.getTaskName());
            mTVConstructionName.setText(mObjWork.getConstructionCode());
            mTVCategoryName.setText(mObjWork.getWorkItemName());
            if (mObjWork.getDescription() != null) {
                edtDescription.setText(mObjWork.getDescription().toString().trim());
            }
            isStop = mObjWork.getStatus();
            if (mObjWork.getCompletePercent() != null
                    ) {
                percent = Double.parseDouble(mObjWork.getCompletePercent());
                if (percent > 0)
                    edtProcess.setText(percent.intValue() + "");
                edtProcess.setSelection(edtProcess.length());
            } else {
                percent = 0d;
                edtProcess.setText("");
            }

            if(mObjWork.getCheckEntangle() == 1){
                cbVuongCt.setChecked(true);
            }else if(mObjWork.getCheckEntangle() == 2){
                cbVuongKTDB.setChecked(true);
                cbVuongKTDB.setClickable(false);
                cbVuongCt.setClickable(false);
            }
            if(mObjWork.getCheckEntangle() != 2){
                mStopContinueContainer.setVisibility(View.VISIBLE);
                if (isStop.equals(String.valueOf(VConstant.ON_PAUSE).trim())) {
                    continueToStop_View();
                    showVuongThiCong(true);
                } else {
                    stopToContinue_View();
                    showVuongThiCong(false);
                }
            }else{
                btnSave.setText("");
                btnSave.setClickable(false);
                mStopContinueContainer.setVisibility(View.GONE);
            }


            cbVuongCt.setOnCheckedChangeListener(cbVuongListener);
            cbVuongKTDB.setOnCheckedChangeListener(cbVuongListener);


            mTVEstimateTime.setText(getString(R.string.start_date_end_date, mObjWork.getStartDate(), mObjWork.getEndDate()));


        }
        email = VConstant.getDTO().getEmail();
        phone = VConstant.getDTO().getPhoneNumber();
    }
    CompoundButton.OnCheckedChangeListener cbVuongListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int id = buttonView.getId();
            if(id == R.id.chkVuongCT){
                if(isChecked && mObjWork.getCheckEntangle() != 1){
                    if(mObjWork.getCheckEntangle() != 1) cbVuongCt.setChecked(false);
                    if(cbVuongKTDB.isChecked()) cbVuongKTDB.setChecked(false);
                    Intent intent = new Intent(getApplicationContext(), DashboardCVStopReflectActivity.class);
                    intent.putExtra(DashboardCVStopReflectActivity.VUONG_KTDB, 1);
                    intent.putExtra("constructionTaskId", mObjWork);
                    startActivityForResult(intent, ParramConstant.TaskContinueToStop_ResultCode);
                }
            }else if (id == R.id.chkVuongKTDB){
                if(isChecked){
                    cbVuongKTDB.setChecked(false);
                    Intent intent = new Intent(getApplicationContext(), DashboardCVStopReflectActivity.class);
                    intent.putExtra(DashboardCVStopReflectActivity.VUONG_KTDB, 2);
                    intent.putExtra("constructionTaskId", mObjWork);
                    startActivityForResult(intent, ParramConstant.TaskContinueToStop_ResultCode);
                }
            }
        }
    };

    private void continueToStop_View() {
        txtStatus.setText(getResources().getString(R.string.status_continue));
        imgStatus.setImageResource(R.drawable.accept);
        disableEditText(edtDescription);
        disableEditText(edtProcess);

        mBtnCamera.setClickable(false);
        mBtnCamera.setVisibility(View.INVISIBLE);
        btnSave.setText("");
        btnSave.setClickable(false);
        workItemStatusAvailable = false;

    }

    private void stopToContinue_View() {
        txtStatus.setText(getResources().getString(R.string.status_pause));
        imgStatus.setImageResource(R.drawable.ic_stop_signal);
        enableEditText(edtDescription);
        enableEditText(edtProcess);
        mBtnCamera.setClickable(true);
        btnStop.setClickable(true);
        btnSave.setClickable(true);
        btnSave.setText(R.string.save);
        mBtnCamera.setVisibility(View.VISIBLE);
        workItemStatusAvailable = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == ParramConstant.TaskContinueToStop_ResultCode) {
                if (data != null && data.getBooleanExtra(ParramConstant.TaskContinueToStop, false)) {
                    //Dừng thành công
                    continueToStop_View();
                    ((App) getApplication()).setNeedUpdate(true);

                    //Dừng xong thì đóng lại
                    finish();
                }
            }
            switch (requestCode) {
                case VConstant.REQUEST_CODE_CAMERA:
                    if (resultCode == Activity.RESULT_OK) {
                        filePath = mPhotoFile.getPath();
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String pictureName = "IMG_" + timeStamp + ".jpg";
                        Bitmap bitmap = ImageUtils.decodeBitmapFromFile(filePath, 200, 200);
                        Bitmap newBitmap =
                                ImageUtils.drawTextOnImage(bitmap, latitude, longitude, mObjWork.getConstructionCode(), mObjWork.getWorkItemName());
                        ConstructionImageInfo imageInfo = new ConstructionImageInfo();
                        imageInfo.setStatus(0);//
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
                case ParramConstant.SelectedImage_RequestCode:
                    if (data != null) {
                        List<ImageCapture> lsData =
                                (List<ImageCapture>) data.getSerializableExtra(ParramConstant.SelectedImage_Key);
                        if (lsData != null && lsData.size() > 0) {
                            for (ImageCapture img : lsData) {

                                //Check image exist on mList URL
                                //Server Image name đang có đuổi là: .replace(".jpg.jpg",".jpg")
                                int totalExist = Observable.from(mListUrl)
                                        .filter(x -> x.getImageName().replace(".jpg.jpg", ".jpg").equalsIgnoreCase(img.getImageName())).count().toBlocking().first();

                                if (totalExist == 0) {
                                    //Nếu đã chọn rồi thì thôi
                                    Bitmap bitmap = StringUtil.setImage(img.getImageData());
                                    Bitmap newBitmap =
                                            ImageUtils.drawTextOnImage(bitmap, img.getLattitude(), img.getLongtitude(),
                                                    mObjWork.getConstructionCode(), mObjWork.getWorkItemName());
                                    ConstructionImageInfo imageInfo = new ConstructionImageInfo();
                                    imageInfo.setStatus(0);//
                                    imageInfo.setBase64String(StringUtil.getStringImage(newBitmap));
                                    imageInfo.setImageName(img.getImageName());
                                    imageInfo.setLatitude(img.getLattitude());
                                    imageInfo.setLongtitude(img.getLongtitude());

                                    mListUrl.add(0, imageInfo);
                                }
                            }
                            mAdapter.notifyDataSetChanged();
                            filePath = "have image";// trick to pass save function
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showVuongThiCong(boolean isShow){
        lnVuongCt.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }


    @OnClick(R.id.txtCancel)
    public void onClickBtnCancel() {
        try {
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private AlertDialog dialogConfirmEntangle;

    @OnClick(R.id.btn_stop)
    public void onClickStop() {
        try {
            if (!workItemStatusAvailable) {
                if(!cbVuongCt.isChecked() && !cbVuongKTDB.isChecked()){
                    if (mObjWork.getObstructedState() != null &&
                            !mObjWork.getObstructedState().equalsIgnoreCase("0")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(R.string.warningSolveEntangle);
                        builder.setCancelable(false);
                        builder.setPositiveButton(R.string.Conitune, (dialogInterface, i) -> {
                            double percent = Double.parseDouble(edtProcess.length() == 0 ? "0" : edtProcess.getText().toString());
                            if (percent > 0) {
                                mObjWork.setStatus("2");//Đang thực hiện
                            } else
                                mObjWork.setStatus("1");//Chưa thực hiện
                            updateWorkItemToStop(percent, mObjWork.getPerformerId(),
                                    mObjWork.getSysGroupId(), email, phone, edtDescription.getText().toString(), 0);
                            showVuongThiCong(false);
                        });
                        builder.setNegativeButton(R.string.No, (dialogInterface, i) -> {
                        });

                        dialogConfirmEntangle = builder.create();
                        dialogConfirmEntangle.show();
                    } else {
                        //Tiếp tục => Save luôn
                        double percent = Double.parseDouble(edtProcess.length() == 0 ? "0" : edtProcess.getText().toString());
                        if (percent > 0) {
                            mObjWork.setStatus("2");//Đang thực hiện
                        } else
                            mObjWork.setStatus("1");//Chưa thực hiện
                        updateWorkItemToStop(percent, mObjWork.getPerformerId(),
                                mObjWork.getSysGroupId(), email, phone, edtDescription.getText().toString(), 0);
                    }
                }else{
                    Toast.makeText(this, getResources().getString(R.string.dang_vuong_khong_the_tiep_tuc), Toast.LENGTH_LONG).show();
                }
            } else {
                //Dừng thì phải có lý do
                Intent intent = new Intent(this, DashboardCVStopReflectActivity.class);
                intent.putExtra("constructionTaskId", mObjWork);
                startActivityForResult(intent, ParramConstant.TaskContinueToStop_ResultCode);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    // constructionTask performerId

    private void updateWorkItem(Double process, Long sysUserId, Long sysGroupId, String email, String phone, String des, int flag) {
        //        //filter list image for upload image , only status -1 , 0
        for (int i = 0; i < mListUrl.size(); i++) {
            if (mListUrl.get(i).getStatus() == 0) {
                mListUrlUpload.add(mListUrl.get(i));
                String splitFormatImage = mListUrlUpload.get(i).getImageName();
                mListUrlUpload.get(i).setImageName(splitFormatImage);
            }
        }

        String startingDateTK = mObjWork.getStartingDateTK();

        String handoverDateBuildBGMB = mObjWork.getHandoverDateBuildBGMB();

        String checkBGMB = mObjWork.getCheckBGMB();

        Log.d(TAG, "checkBGMB : " + checkBGMB + " - handoverDateBuildBGMB: " + handoverDateBuildBGMB + "- startingDateTK: " + startingDateTK);

        ApiManager.getInstance().updateConstructionTask1(startingDateTK, handoverDateBuildBGMB, checkBGMB, process, mListUrlUpload,
                mObjWork, flag, email, phone, sysUserId,
                sysGroupId, des, ResultApi.class, new IOnRequestListener() {
                    @Override
                    public <T> void onResponse(T result) {
                        try {
                            ResultApi resultApi = ResultApi.class.cast(result);
                            if (resultApi.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                                //Danh dau Update sau khi save detail
                                ((App) getApplication()).setNeedUpdateAfterSaveDetail(true);
                                String process = edtProcess.getText().toString().trim();
                                if (!allowConfirmWorkItem || Double.valueOf(process) < 100) {
                                    //Save thông thường
                                    Toast.makeText(DetailInProcessCameraActivity.this, getResources().getString(R.string.updated), Toast.LENGTH_SHORT).show();
                                    progress.setLoading(false);
                                    dialogLoading.dismiss();
                                    finish();
                                } else if (allowConfirmWorkItem && Double.valueOf(process) == 100) {
                                    //Confirm work item
                                    confirmWorkItem();
                                }
                            } else {
                                Toast.makeText(DetailInProcessCameraActivity.this, getResources().getString(R.string.update_fail), Toast.LENGTH_SHORT).show();
                                progress.setLoading(false);
                                dialogLoading.dismiss();
                                btnSave.setClickable(true);
                            }
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(int statusCode) {
                        progress.setLoading(false);
                        btnSave.setClickable(true);
                        dialogLoading.dismiss();
                    }
                });
    }

    private void confirmWorkItem() {
        if (allowConfirmWorkItem && constructionScheduleItemDTO != null) {
            WorkItemDetailDTO workItemDTO = new WorkItemDetailDTO();
            workItemDTO.setConstructionId(mObjWork.getConstructionId() + "");
            workItemDTO.setConstructionCode(mObjWork.getConstructionCode());
            workItemDTO.setWorkItemId(mObjWork.getWorkItemId() + "");
            workItemDTO.setName(constructionScheduleItemDTO.getName());
            workItemDTO.setStartingDate(constructionScheduleItemDTO.getStartingDate());
            workItemDTO.setQuantity(constructionScheduleItemDTO.getQuantity());

            ApiManager.getInstance().updateWork(workItemDTO, WorkItemDetailDTORespone.class, new IOnRequestListener() {
                @Override
                public <T> void onResponse(T result) {
                    try {
                        WorkItemDetailDTORespone workItemDetailDTORespone = WorkItemDetailDTORespone.class.cast(result);
                        ResultInfo resultInfo = workItemDetailDTORespone.getResultInfo();
                        if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                            Toast.makeText(DetailInProcessCameraActivity.this, R.string.updated, Toast.LENGTH_SHORT).show();
                            App.getInstance().setNeedUpdateCompleteCategory(true);
                            finish();
                        } else {
                            Toast.makeText(DetailInProcessCameraActivity.this, R.string.update_fail, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(int statusCode) {
                    Toast.makeText(DetailInProcessCameraActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void updateWorkItemToStop(Double process, Long sysUserId, Long sysGroupId, String email, String phone, String des, int flag) {

        ApiManager.getInstance().updateConstructionTask(process, mListUrlUpload,
                mObjWork, flag, email, phone, sysUserId,
                sysGroupId, des, ResultApi.class, new IOnRequestListener() {
                    @Override
                    public <T> void onResponse(T result) {
                        try {
                            progress.setLoading(false);
                            ResultApi resultApi = ResultApi.class.cast(result);
                            if (resultApi.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                                if (!workItemStatusAvailable) {
                                    stopToContinue_View();
                                    Toast.makeText(DetailInProcessCameraActivity.this,
                                            R.string.TaskStopToContinue, Toast.LENGTH_SHORT).show();
                                    //Thiết lập hết vướng
                                    mObjWork.setObstructedState("0");

                                } else {
                                    //
                                    continueToStop_View();
                                    Toast.makeText(DetailInProcessCameraActivity.this,
                                            R.string.TaskContinueToStop, Toast.LENGTH_SHORT).show();
                                }
                                ((App) getApplication()).setNeedUpdate(true);
                            } else {
                                Toast.makeText(DetailInProcessCameraActivity.this, getResources().getString(R.string.update_fail), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(int statusCode) {
                        progress.setLoading(false);
                        btnSave.setClickable(true);
                    }
                });
    }


    @OnClick(R.id.txtSave)
    public void onSave() {
        try {
            String process = edtProcess.getText().toString().trim();
            String description = edtDescription.getText().toString().trim();
            //Check percent
            if (checkValideWorkPercent()) {
                //Check Anh
                boolean isSizeImage = filePath != null && filePath.length() > 0;
                Log.d(TAG, "onSave - checkImage : " + checkImage + " - isSizeImage : " + isSizeImage);
                if ((checkImage == 1) || (filePath != null && filePath.length() > 0)) {
                    progress.setLoading(true);
                    dialogLoading.show();
                    if (allowConfirmWorkItem && Double.parseDouble(process) == 100) {
                        Log.d(TAG, "Xác nhận hạng mục hoàn thành đủ 100%");
                        //Cần phải confirm hạng mục
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailInProcessCameraActivity.this);
                        String message = getString(R.string.confirmCompletedWorkItem);
                        message = String.format(message, mObjWork.getWorkItemName());
                        builder.setMessage(message);
                        builder.setCancelable(false);
                        builder.setPositiveButton(R.string.Confirm, (dialog, i) -> {
                            updateWorkItem(Double.parseDouble(process), mObjWork.getPerformerId(), mObjWork.getSysGroupId(), email, phone, description, 0);
                        });
                        builder.setNegativeButton(getString(R.string.Cancel), (dialog, i) -> {
                            dialogConfirmWorkItem.dismiss();
                            dialogLoading.dismiss();
                        });
                        dialogConfirmWorkItem = builder.create();
                        dialogConfirmWorkItem.show();
                    } else {
                        updateWorkItem(Double.parseDouble(process), mObjWork.getPerformerId(), mObjWork.getSysGroupId(), email, phone, description, 0);
                    }
                } else {
                    Toast.makeText(this, getResources().getString(R.string.please_take_image), Toast.LENGTH_SHORT).show();
                }
            } else {
                String strPercentWarning = getString(R.string.PercentWarning);
                Toast.makeText(this, strPercentWarning, Toast.LENGTH_LONG).show();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean checkValideWorkPercent() {
        boolean result = true;
        try {
            double oldPercent = Double.parseDouble(strOldPercent);
            double newPercent = Double.parseDouble(edtProcess.length() == 0 ? "0"
                    : edtProcess.getText().toString().trim());
            if (newPercent <= oldPercent) {
                result = false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onDelete(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa ảnh");
        builder.setMessage("Bạn có muốn xóa ảnh ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Xóa", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            mListUrl.get(pos).setStatus(-1);
            if (mListUrl.get(pos).getUtilAttachDocumentId() > 0)
                mListUrlUpload.add(mListUrl.get(pos));
            mListUrl.remove(pos);
            mAdapter.notifyItemRemoved(pos);
        });
        builder.setNegativeButton("Không", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.c5));
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.c5));

    }

}
