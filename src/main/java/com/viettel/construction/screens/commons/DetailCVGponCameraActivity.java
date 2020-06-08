package com.viettel.construction.screens.commons;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.common.App;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.version.AppParamDTO;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.api.ConstructionScheduleItemDTO;
import com.viettel.construction.model.api.ConstructionTaskDTO;
import com.viettel.construction.model.api.EmployeeApi;
import com.viettel.construction.model.api.ResultApi;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.WorkItemDetailDTO;
import com.viettel.construction.model.api.WorkItemDetailDTORespone;
import com.viettel.construction.model.db.ImageCapture;
import com.viettel.construction.screens.atemp.adapter.ConstructionSpnAdapter;
import com.viettel.construction.screens.atemp.adapter.ImageAppParamAdapter;
import com.viettel.construction.screens.custom.dialog.CustomProgress;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.util.ImageUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

public class DetailCVGponCameraActivity extends BaseCameraActivity
        implements ImageAppParamAdapter.OnClickDelete {

    @BindView(R.id.txt_process)
    TextView txt_process;
    @BindView(R.id.tv_content_cv_in_process)
    TextView mTVContentName;
    @BindView(R.id.tv_construction_name_in_process)
    TextView mTVConstructionName;
    @BindView(R.id.tv_category_name_in_process)
    TextView mTVCategoryName;

    @BindView(R.id.txt_titleSum)
    TextView txt_titleSum;

    @BindView(R.id.title_todowork)
    TextView title_todowork;

    @BindView(R.id.btn_camera)
    ImageView mBtnCamera;

    @BindView(R.id.btnSelectImage)
    ImageView btnSelectImage;

    @BindView(R.id.btn_stop)
    LinearLayout btnStop;

    @BindView(R.id.rv_list_image_in_process)
    RecyclerView mRecyclerView;


    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.img_status)
    ImageView imgStatus;

    //
    @BindView(R.id.txtSave)
    TextView txtSave;
    @BindView(R.id.lnFooterStoping)
    View viewFooter;

    @BindView(R.id.ed_todowork)
    EditText ed_todowork;

    @BindView(R.id.tv_sum)
    TextView tv_sum;

    @BindView(R.id.tv_do_work)
    TextView tv_do_work;

    @BindView(R.id.tv_time_do_work)
    TextView tv_time_do_work;

    @BindView(R.id.tv_giatri)
    TextView tv_giatri;


    @BindView(R.id.imgBackGP)
    ImageView imgBackGP;

    @BindView(R.id.txtCancel)
    TextView txtCancel;

    @BindView(R.id.prg_loading)
    CustomProgress progress;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

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
    private Double mCurrentPrice = 0.0;
    private Double mCurrentTotal = 0.0;
    private Double mCurrentAmount = 0.0;
    private long mCurrentType = 0;
    private boolean allowConfirmWorkItem = false;
    private AppParamDTO appParamDTO;
    private boolean isCheck = false;
    private Double processParams;
    private Double sumMaxParams;//Tổng khối lượng công việc giới hạn

    //Tổng khối lượng công việc từ trước tới giờ
    private Double amountPreview = 0d;
    private int confirmDaily = 0;
    private boolean workAvailable = false;
    private boolean workItemSuccessful;
    private ConstructionScheduleItemDTO constructionScheduleItemDTO;
    private AlertDialog dialogConfirmWorkItem;
    private ProgressDialog dialogLoading;
    private int mCurrentSize = 0;

    private final String TAG = "VTDetailCVGpon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cvgpon_camera);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Log.d(TAG, "onCreate");
        ButterKnife.bind(this);

        progress.setLoading(true);
        initData();
        setUpView();
        initListImageFromServer();
        onChangeValue();
    }

    private void onChangeValue() {
        ed_todowork.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    String value = ed_todowork.getText().toString();
                    mCurrentAmount = Double.valueOf(value);

                    //set Text process

                    if (mObjWork.getQuantityByDate().equals("2")) {
                        Double newWeight = mObjWork.getTotalAmount() + mCurrentAmount;
                        if ((newWeight > mObjWork.getAmount()) || (mCurrentAmount < mObjWork.getAmountTaskDaily())) {
                            return;
                        }
//                        // set percent cho cong trinh
                        getNumberPercent(Integer.parseInt(mObjWork.getQuantityByDate()), newWeight, true);

                    } else if (mObjWork.getQuantityByDate().equals("3")) {
                        Double newWeight = mObjWork.getTotalAmount() + mCurrentAmount;
                        if ((newWeight > mObjWork.getTotalAmountGate()) || (mCurrentAmount < mObjWork.getAmountTaskDaily())) {
                            return;
                        }
//                        // set percent cho cong trinh
                        getNumberPercent(Integer.parseInt(mObjWork.getQuantityByDate()), newWeight, true);

                    } else if (mObjWork.getQuantityByDate().equals("4")) {
                        Double newWeight = mObjWork.getTotalAmount() + mCurrentAmount;
                        if ((newWeight > mObjWork.getTotalAmountChest()) || (mCurrentAmount < mObjWork.getAmountTaskDaily())) {
                            return;
                        }
//                        // set percent cho cong trinh
                        getNumberPercent(Integer.parseInt(mObjWork.getQuantityByDate()), newWeight, true);
                    }

                    //set text gia tri san luong
                    getNumberTong(Integer.parseInt(mObjWork.getQuantityByDate()), mCurrentAmount);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void disableEditText() {
        ed_todowork.setFocusable(false);
        ed_todowork.setEnabled(false);
        ed_todowork.setCursorVisible(false);
        ed_todowork.setKeyListener(null);
        ed_todowork.setBackgroundColor(Color.TRANSPARENT);
    }

    private void resetValue() {
        getNumberDaily(Integer.parseInt(mObjWork.getQuantityByDate()));
        getNumberTong(Integer.parseInt(mObjWork.getQuantityByDate()), mObjWork.getAmountTaskDaily());
        //set number cho phan khoi luong
        int intEdTodo = mObjWork.getAmountTaskDaily().intValue();
        if (intEdTodo == mObjWork.getAmountTaskDaily()) {
            ed_todowork.setText(intEdTodo + "");
        } else {
            ed_todowork.setText(mObjWork.getAmountTaskDaily() + "");
        }
    }

    private void initListImageFromServer() {
        long type = -1;
        if (mObjWork.getQuantityByDate().equals("2")) {
            type = 10;
        } else if (mObjWork.getQuantityByDate().equals("3")) {
            type = 11;
        } else if (mObjWork.getQuantityByDate().equals("4")) {
            type = 12;
        }
        Log.d(TAG, "initListImageFromServer : type " + type);
        ApiManager.getInstance().loadImageGpon(type, mObjWork.getConstructionTaskId(), ResultApi.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ResultApi resultApi = ResultApi.class.cast(result);
                    if (resultApi.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        if (resultApi.getListImage() != null) {
                            // add image to list
                            Log.d(TAG, "initListImageFromServer -ok -list image : " + resultApi.getListImage().size());
                            mListUrl.addAll(resultApi.getListImage());
                            mCurrentSize = mListUrl.size();
                            setUpView();
                            progress.setLoading(false);
                        } else {
                            progress.setLoading(false);
                            // Toast.makeText(DetailCVGponCameraActivity.this, "Người dùng không có ảnh !", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                progress.setLoading(false);
                Toast.makeText(DetailCVGponCameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setUpView() {
        //=============================
        mAdapter = new ImageAppParamAdapter(2, mListUrl, this, workItemSuccessful, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void initData() {
        dialogLoading = new ProgressDialog(this);
        dialogLoading.setMessage("Đang xử lý dữ liệu...");
        dialogLoading.setCancelable(false);

        if (getIntent().getExtras() != null && getIntent().getSerializableExtra(ParramConstant.ConstructionScheduleItemDTO) != null) {
            allowConfirmWorkItem = getIntent().getBooleanExtra(ParramConstant.AllowConfirmWorkItem, false);
            constructionScheduleItemDTO = (ConstructionScheduleItemDTO)
                    getIntent().getSerializableExtra(ParramConstant.ConstructionScheduleItemDTO);
            Log.d(TAG, "initdata - allowConfirmWorkItem : " + allowConfirmWorkItem + " - constructionScheduleItemDTO : " + constructionScheduleItemDTO.getName());
        }

        if (getIntent() != null) {
            workItemSuccessful = getIntent().getBooleanExtra(ParramConstant.DetailCV2ActivityCompleted, false);
            if (workItemSuccessful) {
                //Loai cong viec nay nhung ma da hoan thanh, nen khong sua xoa duoc
                txtSave.setText("");
                txtSave.setClickable(false);
                mBtnCamera.setVisibility(View.INVISIBLE);
                btnSelectImage.setVisibility(View.INVISIBLE);
                viewFooter.setVisibility(View.GONE);
                txtCancel.setVisibility(View.GONE);
                imgBackGP.setVisibility(View.VISIBLE);
                txtHeader.setText(R.string.detail);
            } else {
                txtCancel.setVisibility(View.VISIBLE);
                imgBackGP.setVisibility(View.GONE);
                txtHeader.setText(R.string.updateProgress);
            }
        }
        // ============================================
        mListUrlUpload = new ArrayList<>();
        mListUrl = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle.getSerializable(VConstant.BUNDLE_KEY_OBJECT_CV) != null) {
            mObjWork = (ConstructionTaskDTO) bundle.getSerializable(VConstant.BUNDLE_KEY_OBJECT_CV);

            // get id of user cho chuyen nguoi
            currentPerformerId = mObjWork.getPerformerId();
            mTVContentName.setText(mObjWork.getTaskName());
            mTVConstructionName.setText(mObjWork.getConstructionCode());
            mTVCategoryName.setText(mObjWork.getWorkItemName());

            isStop = mObjWork.getStatus();
            if (isStop.equals(String.valueOf(VConstant.ON_PAUSE).trim())) {
                continueToStop_View();
            } else {
                stopToContinue_View();
            }

            // quantityByDate = 2 => keo cap
            // quantityByDate = 3 => han noi
            // quantityByDate = 4 => treo tu
            String time = mObjWork.getStartDate() + " - " + mObjWork.getEndDate();
            tv_time_do_work.setText(time);

            //set number cho phan da thi cong
            Log.d(TAG, "getTotalAmount : " + mObjWork.getTotalAmount());
            int intToDoWork = mObjWork.getTotalAmount().intValue();
            if (intToDoWork == mObjWork.getTotalAmount()) {
                tv_do_work.setText(intToDoWork + "");
            } else {
                tv_do_work.setText(mObjWork.getTotalAmount() + "");
            }

            //set number cho phan khoi luong
            int intEdTodo = mObjWork.getAmountTaskDaily().intValue();
            if (intEdTodo == mObjWork.getAmountTaskDaily()) {
                ed_todowork.setText(intEdTodo + "");
            } else {
                ed_todowork.setText(mObjWork.getAmountTaskDaily() + "");
            }

            getNumberDaily(Integer.parseInt(mObjWork.getQuantityByDate()));
            getNumberTong(Integer.parseInt(mObjWork.getQuantityByDate()), mObjWork.getAmountTaskDaily());
            getNumberPercent(Integer.parseInt(mObjWork.getQuantityByDate()), mObjWork.getTotalAmount() + mObjWork.getAmountTaskDaily(), false);
        }
        email = VConstant.getDTO().getEmail();
        phone = VConstant.getDTO().getPhoneNumber();
    }


    private void getNumberPercent(int number, Double weight, boolean update) {
        DecimalFormat df1 = new DecimalFormat("0.0");
        Double percent;
        switch (number) {
            case 2:
                // set percent cho cong trinh
                percent = weight * 100 / mObjWork.getAmount();
                Log.d(TAG, "percent - Quanity by date 2 : " + percent);
                if (percent.intValue() == percent) {
                    txt_process.setText(percent.intValue() + "");
                } else {
                    txt_process.setText(df1.format(percent) + "");
                }

                if (!update && percent.intValue() == 100) {
                    disableEditText();
                }
                break;
            case 3:
                // set percent cho cong trinh
                percent = weight * 100 / mObjWork.getTotalAmountGate();
                Log.d(TAG, "percent - Quanity by date 3 : " + percent);
                if (percent.intValue() == percent) {
                    txt_process.setText(percent.intValue() + "");
                } else {
                    txt_process.setText(df1.format(percent) + "");
                }

                if (!update && percent.intValue() == 100) {
                    disableEditText();
                }
                break;
            case 4:
                // set percent cho cong trinh
                percent = weight * 100 / mObjWork.getTotalAmountChest();
                Log.d(TAG, "percent - Quanity by date 4 : " + percent);
                if (percent.intValue() == percent) {
                    txt_process.setText(percent.intValue() + "");
                } else {
                    txt_process.setText(df1.format(percent) + "");
                }
                if (!update && percent.intValue() == 100) {
                    disableEditText();
                }
                break;
            default:
                break;
        }
    }

    private void getNumberDaily(int number) {
        int intSum = 0;
        switch (number) {
            case 2:
                mCurrentType = 10;
                txt_titleSum.setText("Tổng tuyến");
                title_todowork.setText("Khối lượng (m):");
                // set number cho tong tuyen
                intSum = mObjWork.getAmount().intValue();

                if (intSum == mObjWork.getAmount()) {
                    // truong hop la so chan - hien thi theo kieu integer
                    tv_sum.setText(intSum + "");
                } else {
                    tv_sum.setText(mObjWork.getAmount() + "");
                }

                mCurrentPrice = mObjWork.getPrice();
                mCurrentTotal = mObjWork.getAmount();
                break;
            case 3:
                mCurrentType = 11;
                txt_titleSum.setText("Tổng số cổng");
                title_todowork.setText("Số cổng:");

                // set number cho tong tuyen
                intSum = mObjWork.getTotalAmountGate().intValue();

                if (intSum == mObjWork.getTotalAmountGate()) {
                    // truong hop la so chan - hien thi theo kieu integer
                    tv_sum.setText(intSum + "");
                } else {
                    tv_sum.setText(mObjWork.getTotalAmountGate() + "");
                }

                mCurrentPrice = mObjWork.getPriceGate();
                mCurrentTotal = mObjWork.getTotalAmountGate();

                break;
            case 4:
                mCurrentType = 12;
                txt_titleSum.setText("Tổng số tủ");
                title_todowork.setText("Số tủ:");

                // set number cho tong tuyen
                intSum = mObjWork.getTotalAmountChest().intValue();

                if (intSum == mObjWork.getTotalAmountChest()) {
                    // truong hop la so chan - hien thi theo kieu integer
                    tv_sum.setText(intSum + "");
                } else {
                    tv_sum.setText(mObjWork.getTotalAmountChest() + "");
                }

                mCurrentPrice = mObjWork.getPriceChest();
                mCurrentTotal = mObjWork.getTotalAmountChest();
                break;
            default:
                break;
        }

    }

    private void getNumberTong(int number, double amount) {
        Double dbGiatri;
        int intGiatri;
        switch (number) {
            case 2:
                //set number cho gia tri san luong
                if (mObjWork.getPrice() != null) {
                    dbGiatri = amount * mObjWork.getPrice();
                    String giatri = String.format("%,.0f", dbGiatri);
                    tv_giatri.setText(giatri);

                } else {
                    tv_giatri.setText("0");
                }

                break;
            case 3:
                //set number cho gia tri san luong
                if (mObjWork.getPriceGate() != null) {
                    dbGiatri = amount * mObjWork.getPriceGate();
                    String giatri = String.format("%,.0f", dbGiatri);
                    tv_giatri.setText(giatri);

                } else {
                    tv_giatri.setText("0");
                }

                break;
            case 4:
                //set number cho gia tri san luong
                if (mObjWork.getPriceChest() != null) {
                    dbGiatri = amount * mObjWork.getPriceChest();
                    String giatri = String.format("%,.0f", dbGiatri);
                    tv_giatri.setText(giatri);

                } else {
                    tv_giatri.setText("0");
                }

                break;
            default:
                break;
        }
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
                                        .filter(x -> x.getImageName().replace(".jpg.jpg", ".jpg").equalsIgnoreCase(img.getImageName())).count().toBlocking().first();

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
                            filePath = "have image";// trick to pass save function
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
                double percent = Double.parseDouble(txt_process.length() == 0 ? "0" : txt_process.getText().toString().replace(",", "."));
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
        mBtnCamera.setClickable(false);
        btnSelectImage.setClickable(false);
        txtSave.setVisibility(View.GONE);
        workAvailable = false;
    }

    private void stopToContinue_View() {
        txtStatus.setText(getResources().getString(R.string.status_pause));
        imgStatus.setImageResource(R.drawable.ic_stop_signal);
        mBtnCamera.setClickable(true);
        btnSelectImage.setClickable(true);
        btnStop.setEnabled(true);
        txtSave.setVisibility(View.VISIBLE);
        workAvailable = true;
    }


    private void updateWork() {
        //filter list image for upload image , only status -1 , 0
        for (int i = 0; i < mListUrl.size(); i++) {
            if (mListUrl.get(i).getStatus() == 0) {
                checkUpload = checkUpload + 1;
                mListUrlUpload.add(mListUrl.get(i));
                String splitFormatImage = mListUrlUpload.get(i).getImageName() + ".jpg";
                mListUrlUpload.get(i).setImageName(splitFormatImage);
            }
        }

        Double current = mCurrentAmount + mObjWork.getTotalAmount();
        ApiManager.getInstance().updateConstructionTaskAppParamGpon(current, mCurrentTotal, mObjWork.getStartingDateTK(), mObjWork.getPath(), mListUrl, mObjWork.getSysGroupId(), mCurrentAmount, mCurrentType, mObjWork.getPerformerId(),
                mObjWork.getConstructionTaskId(), mCurrentAmount * mCurrentPrice, mObjWork.getWorkItemId(), mObjWork.getCatTaskId(), mObjWork.getSysGroupId(), ResultApi.class, new IOnRequestListener() {
                    @Override
                    public <T> void onResponse(T result) {
                        try {
                            ResultApi resultApi = ResultApi.class.cast(result);
                            if (resultApi.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                                //Danh dau Update sau khi save detail
                                ((App) getApplication()).setNeedUpdateAfterSaveDetail(true);
                                String process = txt_process.getText().toString().trim().replace(",", ".");
                                Log.d(TAG, "RESULT_STATUS_OK - allowConfirmWorkItem : " + allowConfirmWorkItem + " - Double.valueOf(process) : " + Double.valueOf(process));

                                if (!allowConfirmWorkItem || Double.valueOf(process) < 100) {
                                    //Save thông thường
                                    Toast.makeText(DetailCVGponCameraActivity.this, getResources().getString(R.string.updated), Toast.LENGTH_SHORT).show();
                                    progress.setLoading(false);
                                    dialogLoading.dismiss();
                                    finish();
                                } else if (allowConfirmWorkItem && Double.valueOf(process) == 100) {
                                    //Confirm work item
                                    confirmWorkItem();
                                }

                            } else {
                                Toast.makeText(DetailCVGponCameraActivity.this, getResources().getString(R.string.update_fail), Toast.LENGTH_SHORT).show();
                                progress.setLoading(false);
                                dialogLoading.dismiss();
                            }
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(int statusCode) {
                        Toast.makeText(DetailCVGponCameraActivity.this, "Network error !", Toast.LENGTH_SHORT).show();
                        progress.setLoading(false);
                        dialogLoading.dismiss();
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
                            Toast.makeText(DetailCVGponCameraActivity.this,
                                    R.string.TaskStopToContinue, Toast.LENGTH_SHORT).show();


                        } else {
                            //
                            continueToStop_View();
                            Toast.makeText(DetailCVGponCameraActivity.this,
                                    R.string.TaskContinueToStop, Toast.LENGTH_SHORT).show();
                        }
                        ((App) getApplication()).setNeedUpdate(true);
                    } else {
                        Toast.makeText(DetailCVGponCameraActivity.this, getResources().getString(R.string.update_fail), Toast.LENGTH_SHORT).show();
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

    private boolean checkConditionValidate() {
        boolean isCheck = false;

        if (mObjWork.getQuantityByDate().equals("2")) {
            Double newWeight = mObjWork.getTotalAmount() + mCurrentAmount;
            if ((newWeight > mObjWork.getAmount()) || (mCurrentAmount < mObjWork.getAmountTaskDaily())) {
                resetValue();
                return true;
            }
            // set percent cho cong trinh
            getNumberPercent(Integer.parseInt(mObjWork.getQuantityByDate()), newWeight, true);

        } else if (mObjWork.getQuantityByDate().equals("3")) {
            Double newWeight = mObjWork.getTotalAmount() + mCurrentAmount;
            if ((newWeight > mObjWork.getTotalAmountGate()) || (mCurrentAmount < mObjWork.getAmountTaskDaily())) {
                resetValue();
                return true;
            }
            // set percent cho cong trinh
            getNumberPercent(Integer.parseInt(mObjWork.getQuantityByDate()), newWeight, true);

        } else if (mObjWork.getQuantityByDate().equals("4")) {
            Double newWeight = mObjWork.getTotalAmount() + mCurrentAmount;
            if ((newWeight > mObjWork.getTotalAmountChest()) || (mCurrentAmount < mObjWork.getAmountTaskDaily())) {
                resetValue();
                return true;
            }
            getNumberPercent(Integer.parseInt(mObjWork.getQuantityByDate()), newWeight, true);
        }
        return isCheck;
    }

    @OnClick(R.id.txtSave)
    public void onSave() {
        try {
            if (checkConditionValidate()) {
                resetValue();
                Toast.makeText(getApplicationContext(), "Giá trị bạn nhập đã lớn hơn giá trị cho phép hoặc nhỏ hơn giá trị cập nhập trước đó.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (mObjWork.getConfirm() == null || mObjWork.getConfirm().equals("0") || mObjWork.getConfirm().equals("2")) { // - dieu kien check san luong da duoc phe duyet chua
                Log.d(TAG, "check size image : current size : " + mCurrentSize + " - new size : " + mListUrl.size());
                //if (mListUrl.size() > mCurrentSize ) {
                if (filePath != null && filePath.length() > 0) {
                    progress.setLoading(true);
                    //double percent = Double.parseDouble(txt_process.length() == 0 ? "0" : txt_process.getText().toString());
                    Log.d(TAG, "onSave - allowConfirmWorkItem : " + allowConfirmWorkItem + " - percent: " + txt_process.getText().toString());
                    String process = txt_process.getText().toString().trim().replace(",", ".");
                    if (allowConfirmWorkItem && Double.valueOf(process) == 100) {
                        Log.d(TAG, "Xác nhận hạng mục hoàn thành đủ 100%");
                        //Cần phải confirm hạng mục
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailCVGponCameraActivity.this);
                        String message = getString(R.string.confirmCompletedWorkItem);
                        message = String.format(message, mObjWork.getWorkItemName());
                        builder.setMessage(message);
                        builder.setCancelable(false);
                        builder.setPositiveButton(R.string.Confirm, (dialog, i) -> {
                            updateWork();
                        });
                        builder.setNegativeButton(getString(R.string.Cancel), (dialog, i) -> {
                            dialogConfirmWorkItem.dismiss();
                            dialogLoading.dismiss();
                        });
                        dialogConfirmWorkItem = builder.create();
                        dialogConfirmWorkItem.show();
                    } else {
                        updateWork();
                    }

                } else {
                    Toast.makeText(this, getResources().getString(R.string.please_take_image), Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(DetailCVGponCameraActivity.this, "Sản lượng trong ngày đã được phê duyệt", Toast.LENGTH_SHORT).show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            Log.d(TAG, "NullPointerException");
            e.printStackTrace();
        }
    }

    @OnClick(R.id.imgBackGP)
    public void onClickBtnBack() {
        try {
            finish();
        } catch (Exception e) {
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
            Toast.makeText(DetailCVGponCameraActivity.this, "Sản lượng trong ngày đã được phê duyệt", Toast.LENGTH_SHORT).show();
        }
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

            Log.d(TAG, "confirmWorkItem");

            ApiManager.getInstance().updateWork(workItemDTO, WorkItemDetailDTORespone.class, new IOnRequestListener() {
                @Override
                public <T> void onResponse(T result) {
                    try {
                        WorkItemDetailDTORespone workItemDetailDTORespone = WorkItemDetailDTORespone.class.cast(result);
                        ResultInfo resultInfo = workItemDetailDTORespone.getResultInfo();
                        if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                            Toast.makeText(DetailCVGponCameraActivity.this, R.string.updated, Toast.LENGTH_SHORT).show();
                            App.getInstance().setNeedUpdateCompleteCategory(true);
                            finish();
                        } else {
                            Toast.makeText(DetailCVGponCameraActivity.this, R.string.update_fail, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(int statusCode) {
                    Toast.makeText(DetailCVGponCameraActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialogLoading = null;
    }

    @OnClick(R.id.btnSelectImage)
    public void selectedImage() {
        Intent selectImage = new Intent(this, SelectImagesActivity.class);
        startActivityForResult(selectImage, ParramConstant.SelectedImage_RequestCode);
    }
}
