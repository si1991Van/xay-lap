package com.viettel.construction.screens.commons;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.common.App;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.version.AppParamDTO;
import com.viettel.construction.model.api.AppParamManageDto;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.api.ConstructionTaskDTO;
import com.viettel.construction.model.api.EmployeeApi;
import com.viettel.construction.model.api.ResultApi;
import com.viettel.construction.model.db.ImageCapture;
import com.viettel.construction.screens.atemp.adapter.ConstructionSpnAdapter;
import com.viettel.construction.screens.atemp.adapter.ImageAppParamAdapter;
import com.viettel.construction.screens.atemp.adapter.SpinnerAdapter;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.util.ImageUtils;

import com.viettel.construction.screens.custom.dialog.CustomProgress;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/***
 * Chi tiết công việc sản lượng theo ngày
 */
public class DetailCV2CameraActivity extends BaseCameraActivity
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
    @BindView(R.id.btnSelectImage)
    ImageView btnSelectImage;

    @BindView(R.id.btn_stop)
    LinearLayout btnStop;

    @BindView(R.id.rv_list_image_in_process)
    RecyclerView mRecyclerView;
    @BindView(R.id.rcv_construction_spn)
    RecyclerView rcvSpn;
    @BindView(R.id.edt_process)
    EditText edtProcess;

    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.img_status)
    ImageView imgStatus;
    @BindView(R.id.btn_add)
    ImageView imgAdd;
    @BindView(R.id.spn_construction)
    Spinner spnConstruction;
    //
    @BindView(R.id.txtSave)
    TextView txtSave;
    @BindView(R.id.lnFooterStoping)
    View viewFooter;
    @BindView(R.id.lnHinhThucTC)
    View lnHinhThucTC;


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
    private ConstructionTaskDTO mObjWork;//Tổng khối lượng công việc giới hạn amount
    private String imageBase64;
    private String email, phone;
    private long sysUserId, sysGroupId;

    @BindView(R.id.prg_loading)
    CustomProgress progress;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    private List<AppParamDTO> listSpn = new ArrayList<>();
    private SpinnerAdapter spinnerAdapter;
    private AppParamDTO appParamDTO;
    private boolean isCheck = false;
    private Double processParams;
    private Double sumMaxParams;//Tổng khối lượng công việc giới hạn

    //Tổng khối lượng công việc từ trước tới giờ
    private Double amountPreview = 0d;
    private int confirmDaily = 0;

    //
    private boolean workAvailable = false;

    private boolean workItemSuccessful;

    private final String TAG = "VTDetailCV2CaAc";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cv_2);
        Log.d(TAG,"onCreate");
        ButterKnife.bind(this);
        progress.setLoading(true);
        initData();
        initListImageFromServer();
        setUpView();
        getListConstructionSpinner();
    }

    @OnClick(R.id.btn_add)
    public void onClickAdd() {
        try {
            if (sumMaxParams == 0) {
                Toast.makeText(this, getString(R.string.dont_update), Toast.LENGTH_SHORT).show();
            } else {
                if (confirmDaily == 0) {//Được phép phê duyệt hàng ngày
                    appParamDTO = (AppParamDTO) spnConstruction.getSelectedItem();

                    //Check exist hinh thuc thi cong
                    AppParamDTO itemExist = Observable.from(listAppParam)
                            .filter(item -> item.getName().equals(appParamDTO.getName())).toBlocking().firstOrDefault(null);
                    if (itemExist == null) {
                        appParamDTO.setAmount("");
                        appParamDTO.setConfirm("0");
                        listAppParam.add(0, appParamDTO);
                        constructionSpnAdapter.notifyItemInserted(0);
                        constructionSpnAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Hình thức thi công này bạn đã thêm rồi.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                            Toast.makeText(DetailCV2CameraActivity.this, "Người dùng không có ảnh !", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                progress.setLoading(false);
                Toast.makeText(DetailCV2CameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setUpView() {

        //recyclerview
        constructionSpnAdapter = new ConstructionSpnAdapter(this, workItemSuccessful, listAppParam, this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        rcvSpn.setLayoutManager(linearLayoutManager2);
        rcvSpn.setAdapter(constructionSpnAdapter);

        //=============================
        mAdapter = new ImageAppParamAdapter(1,mListUrl, this, workItemSuccessful, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        spinnerAdapter = new SpinnerAdapter(this, listSpn);
        spnConstruction.setAdapter(spinnerAdapter);
    }

    private void initData() {
        if (getIntent() != null) {
            workItemSuccessful = getIntent().getBooleanExtra(ParramConstant.DetailCV2ActivityCompleted, false);
            if (workItemSuccessful) {
                //Loai cong viec nay nhung ma da hoan thanh, nen khong sua xoa duoc
                txtSave.setText("");
                txtSave.setClickable(false);
                mBtnCamera.setVisibility(View.INVISIBLE);
                viewFooter.setVisibility(View.GONE);
                lnHinhThucTC.setVisibility(View.GONE);
                txtHeader.setText(R.string.detail);
            } else {
                txtHeader.setText(R.string.updateProgress);
            }
        }
        // ============================================
        mListUrlUpload = new ArrayList<>();
        mListUrl = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle.getSerializable(VConstant.BUNDLE_KEY_OBJECT_CV) != null) {
            mObjWork = (ConstructionTaskDTO) bundle.getSerializable(VConstant.BUNDLE_KEY_OBJECT_CV);

            //get sum params from model
            if (mObjWork.getAmount() != null) {
                sumMaxParams = mObjWork.getAmount();

            } else {
                sumMaxParams = 0d;
                edtProcess.setText("0");

            }

            // get id of user cho chuyen nguoi
            currentPerformerId = mObjWork.getPerformerId();
            mTVContentName.setText(mObjWork.getTaskName());
            mTVConstructionName.setText(mObjWork.getConstructionCode());
            mTVCategoryName.setText(mObjWork.getWorkItemName());

            isStop = mObjWork.getStatus();
            if (mObjWork.getCompletePercent() != null) {
                percent = Double.parseDouble(mObjWork.getCompletePercent());
                edtProcess.setText(percent.intValue() + "");
            } else {
                percent = 0d;
                edtProcess.setText(0 + "");
            }

            if (isStop.equals(String.valueOf(VConstant.ON_PAUSE).trim())) {
                continueToStop_View();
            } else {
                stopToContinue_View();
            }

            mTVEstimateTime.setText(getString(R.string.start_date_end_date, mObjWork.getStartDate(), mObjWork.getEndDate()));

        }
        email = VConstant.getDTO().getEmail();
        phone = VConstant.getDTO().getPhoneNumber();
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
                                edtProcess.setText(newFormat + "");
                            } else {
                                edtProcess.setText(format + "");
                            }
                        }

                        if (dto.getAmountPreview() != null && dto.getAmountPreview() > 0) {
                            amountPreview = dto.getAmountPreview();
                        } else {
                            amountPreview = 0d;
                        }

                        confirmDaily = dto.getConfirmDaily();

                        if (confirmDaily == 0) {
                        } else {
                            mBtnCamera.setClickable(false);
                        }

                        spinnerAdapter.notifyDataSetChanged();
                        constructionSpnAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(DetailCV2CameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(DetailCV2CameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick(R.id.btnSelectImage)
    public void selectedImage() {
        Intent selectImage = new Intent(this, SelectImagesActivity.class);
        startActivityForResult(selectImage, ParramConstant.SelectedImage_RequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == ParramConstant.TaskContinueToStop_ResultCode) {
                if (data != null && data.getBooleanExtra(ParramConstant.TaskContinueToStop, false)) {
                    //Dừng thành công
                    continueToStop_View();
                    ((App) getApplication()).setNeedUpdate(true);
                }
            }

            switch (requestCode) {
                case VConstant.REQUEST_CODE_CAMERA:
                    if (resultCode == Activity.RESULT_OK) {
                        filePath = mPhotoFile.getPath();
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String pictureName = "IMG_" + timeStamp + ".jpg";
                        Bitmap bitmap = ImageUtils.decodeBitmapFromFile(filePath, 200, 200);
                        Bitmap newBitmap = ImageUtils.drawTextOnImage(bitmap, latitude, longitude, mObjWork.getConstructionCode(), mObjWork.getWorkItemName());
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
                        } else {
                            Toast.makeText(this, "Vui lòng chuyển người khác !!!", Toast.LENGTH_SHORT).show();
                        }

                        phone = employee.getPhoneNumber();
                        email = employee.getEmail();
                        sysUserId = Long.parseLong(employee.getSysUserId());
                        sysGroupId = Long.parseLong(employee.getDepartmentId());
                    }

                    break;

                case ParramConstant.SelectedImage_RequestCode:
                    if (data != null) {
                        List<ImageCapture> lsData =
                                (List<ImageCapture>) data.getSerializableExtra(ParramConstant.SelectedImage_Key);
                        if (lsData != null && lsData.size() > 0) {
                            for (ImageCapture img : lsData) {

                                //Check image exist on mList URL
                                int totalExist = Observable.from(mListUrl)
                                        .filter(x -> x.getImageName().replace(".jpg.jpg",".jpg").equalsIgnoreCase(img.getImageName())).count().toBlocking().first();

                                if (totalExist == 0) {
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
                            filePath ="have image";// trick to pass save function
                        }
                    }
                    break;

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.txtCancel)
    public void onClickBtnCancel() {
        try {
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.btn_stop)
    public void onClickStop() {
        try {
            if (!workAvailable) {
                mBtnCamera.setClickable(true);

                //Lưu luôn
                double percent = Double.parseDouble(edtProcess.length() == 0 ? "0" : edtProcess.getText().toString());
                if (percent > 0) {
                    mObjWork.setStatus("2");//Đang thực hiện
                } else
                    mObjWork.setStatus("1");//Chưa thực hiện
                updateWorkStatus(percent, mObjWork.getPerformerId(),
                        mObjWork.getSysGroupId(), email, phone, "", 0);
            } else {
                Intent intent = new Intent(this, DashboardCVStopReflectActivity.class);
                intent.putExtra("constructionTaskId", mObjWork);
                startActivityForResult(intent, ParramConstant.TaskContinueToStop_ResultCode);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    private void continueToStop_View() {
        txtStatus.setText(getResources().getString(R.string.status_continue));
        imgStatus.setImageResource(R.drawable.accept);
        imgAdd.setEnabled(false);
        spnConstruction.setEnabled(false);
        mBtnCamera.setClickable(false);
        btnSelectImage.setClickable(false);
        workAvailable = false;
    }

    private void stopToContinue_View() {
        txtStatus.setText(getResources().getString(R.string.status_pause));
        imgStatus.setImageResource(R.drawable.ic_stop_signal);
        imgAdd.setEnabled(true);
        spnConstruction.setEnabled(true);
        mBtnCamera.setClickable(true);
        btnSelectImage.setClickable(true);
        btnStop.setEnabled(true);
        workAvailable = true;
    }


    private void updateWork(List<AppParamDTO> listAppParam, Double process, Long
            sysUserId, Long sysGroupId, String email, String phone, String des, int flag) {

        //filter list image for upload image , only status -1 , 0
        for (int i = 0; i < mListUrl.size(); i++) {
            if (mListUrl.get(i).getStatus() == 0) {
                checkUpload = checkUpload + 1;
                mListUrlUpload.add(mListUrl.get(i));
                String splitFormatImage = mListUrlUpload.get(i).getImageName() + ".jpg";
                mListUrlUpload.get(i).setImageName(splitFormatImage);
            }
        }

        ApiManager.getInstance().updateConstructionTaskAppParam(listAppParam, process, mListUrlUpload, mObjWork, flag, email, phone, sysUserId, sysGroupId, des, ResultApi.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ResultApi resultApi = ResultApi.class.cast(result);
                    if (resultApi.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        Toast.makeText(DetailCV2CameraActivity.this, getResources().getString(R.string.updated), Toast.LENGTH_SHORT).show();
                        progress.setLoading(false);
                        App.getInstance().setNeedUpdateAfterSaveDetail(true);
                        finish();
                    } else {
                        Toast.makeText(DetailCV2CameraActivity.this, getResources().getString(R.string.update_fail), Toast.LENGTH_SHORT).show();
                        progress.setLoading(false);
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(DetailCV2CameraActivity.this, "Network error !", Toast.LENGTH_SHORT).show();
                progress.setLoading(false);
            }
        });
    }

    private void updateWorkStatus(Double process, Long sysUserId, Long sysGroupId, String email,
                                  String phone, String des, int flag) {

        ApiManager.getInstance().updateConstructionTaskAppParam(listAppParam, process, mListUrlUpload, mObjWork, flag, email, phone, sysUserId, sysGroupId, des, ResultApi.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    progress.setLoading(false);
                    ResultApi resultApi = ResultApi.class.cast(result);
                    if (resultApi.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        if (!workAvailable) {
                            stopToContinue_View();
                            Toast.makeText(DetailCV2CameraActivity.this,
                                    R.string.TaskStopToContinue, Toast.LENGTH_SHORT).show();


                        } else {
                            //
                            continueToStop_View();
                            Toast.makeText(DetailCV2CameraActivity.this,
                                    R.string.TaskContinueToStop, Toast.LENGTH_SHORT).show();
                        }
                        ((App) getApplication()).setNeedUpdate(true);
                    } else {
                        Toast.makeText(DetailCV2CameraActivity.this, getResources().getString(R.string.update_fail), Toast.LENGTH_SHORT).show();
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                progress.setLoading(false);
            }
        });
    }

    @OnClick(R.id.txtSave)
    public void onSave() {
        try {
            if (confirmDaily == 0) {
                String process = edtProcess.getText().toString().trim();
                //validate for appParam
                double sum = 0d;
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
                if ((sum + amountPreview) > sumMaxParams) {
                    String sParam = String.valueOf(sumMaxParams);
                    String sNewParam = "";
                    if (sParam.contains(".0")) {
                        sNewParam = sParam.replace(".0", "");
                    }
                    Toast.makeText(this, "Tổng khối lượng không được vượt quá: " + sNewParam, Toast.LENGTH_SHORT).show();
                } else {
                    if (filePath != null && filePath.length() > 0) {
                        progress.setLoading(true);
                        updateWork(listAppParam, Double.parseDouble(process), mObjWork.getPerformerId(), mObjWork.getSysGroupId(), email, phone, "", 0);

                    } else {
                        Toast.makeText(this, getResources().getString(R.string.please_take_image), Toast.LENGTH_SHORT).show();
                    }
                }


            } else {
                Toast.makeText(DetailCV2CameraActivity.this, "Sản lượng trong ngày đã được phê duyệt", Toast.LENGTH_SHORT).show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDelete(final int pos) {
        if (confirmDaily == 0) {
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
        } else {
            Toast.makeText(DetailCV2CameraActivity.this, "Sản lượng trong ngày đã được phê duyệt", Toast.LENGTH_SHORT).show();
        }

    }

    /***
     * Xóa hình thức thi công
     * @param pos
     */
    @Override
    public void onClick(int pos) {
        if (listAppParam.get(pos).getConfirm().equals("0")) {
            listAppParam.remove(pos);
            constructionSpnAdapter.notifyItemRemoved(pos);
            constructionSpnAdapter.notifyDataSetChanged();

            checkSum();
        } else {
            Toast.makeText(this, getString(R.string.dont_delete_form), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onChangeValue() {
        checkSum();
    }

    private void checkSum() {
        double sum = 0d;
        for (int i = 0; i < listAppParam.size(); i++) {
            if (listAppParam.get(i).getAmount() != null
                    && listAppParam.get(i).getAmount().trim().length() > 0) {
                sum += Double.parseDouble(listAppParam.get(i).getAmount());
            }
        }
        sum += amountPreview;//Lấy toàn bộ khối lượng công việc vừa thêm + với
        //khối lượng đã có từ trước tới giờ
        if (sum > sumMaxParams) {
            String sParam = String.valueOf(sumMaxParams);
            String sNewParam = "";
            if (sParam.contains(".0")) {
                sNewParam = sParam.replace(".0", "");
            }
            Toast.makeText(this, "Tổng khối lượng không được vượt quá: " + sNewParam, Toast.LENGTH_SHORT).show();
        } else {

            double test = (sum / sumMaxParams) * 100;
            int round = (int) (Math.round(test * 10) / 10);
            edtProcess.setText(round + "");//tự động update %
        }
    }
}

