package com.viettel.construction.screens.menu_bgmb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.appbase.SpinnerBgmbAdapter;
import com.viettel.construction.common.App;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.ItemSpinnerBgmb;
import com.viettel.construction.model.api.ConstructionBGMBDTO;
import com.viettel.construction.model.api.ConstructionBGMBResponse;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.screens.atemp.adapter.ImageInProcessAdapter;
import com.viettel.construction.screens.custom.dialog.CustomProgress;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.util.ImageUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BgmbUpdateActivity extends BaseCameraActivity
        implements ImageInProcessAdapter.OnClickDelete, View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static final String CAT_CONSTUCTION_TYPE = "cat_construction_type";
    public static final int CONG_TRINH_MAY_NO = 5;
    public static final int CONT_TRINH_GIA_CO = 6;
    public static final long VALUE0_L = 0;
    public static final String VALUE0_S = "0";

    private boolean mIsCTMayNoGiaCo = false;

    private final String TAG = "VTBgmbUpdateActivity";
    private String filePath = "";
    private ImageInProcessAdapter mAdapter;
    public static ArrayList<ConstructionImageInfo> mListUrl;
    public ArrayList<ConstructionImageInfo> mListUrlUpload;
    public ArrayList<ItemSpinnerBgmb> mListHouse;
    public ArrayList<ItemSpinnerBgmb> mListGrounding;
    private ConstructionBGMBDTO constructionBGMBDTO;

    private RadioButton rD_Duoidat_Update, rD_TrenMai_Update;
    private TextView txtHeader_update, txtSave_update, title_Vuong_Update, title_Vattu_Update;
    private EditText noidung_VatTu__Update, noidung_Vuong__Update;
    private ImageView imgBack_update, btn_camera_Update;
    private CheckBox checkbox_NhaCoSan_Update, checkboxXPXDUpdate, checkboxXPACUpdate;
    private CheckBox checkbox_Cotcosan_Update;
    private CheckBox checkbox_TiepDia_Update;
    private CheckBox checkbox_AC_Update;
    private CheckBox checkbox_Tuongrao__Update;
    private CheckBox checkbox_VatTu__Update;
    private CheckBox checkbox_Vuong_Update;
    private EditText edt_sommocoUpdate, edt_docaocotUpdate;
    private LinearLayout ln_docaocot_update, ln_somoco_update, ln_loainha, ln_loaitiepdia;
    private Spinner spinner_tiepdia, spinner_nha;
    private RecyclerView rv_list_image_update;
    private long checkXPXD, checkXPAC;
    CustomProgress progress;

    // ContainerView
    private LinearLayout mLnThiCong, mLnLoaiCT, mLnGiayPhep, mLnHangMuc, mLnThongTinMB, mLnThongTinMBAC;

    // Add thêm tính năng  nhận MBAC
    private EditText mColumnsAvaible, mLengthOfMeter, mTypethOfMeter, mNumNewColumn, mTypeOfColumn;
    private CheckBox mHaveStartPoint;

    // Add them tinh nang Loại Công trình nhận mặt bằng
    private RadioButton mRbGiaCo, mRbMayNo;
    private RadioGroup mGroupGiaCoMayNo;

    //thong tin can duoc update
    private String houseTypeName;
    private String haveWorkItemName;
    private String groundingTypeName;
    private long groundingTypeId;
    private long houseTypeId;
    private long numberCo;
    private long stationType;
    private long columnHeight;
    //Add them
    private String typeConstructionBgmb;
    private String numColumnsAvaible;
    private String lengthOfMeter;
    private String haveStartPoint;
    private String typeOfMeter;
    private String numNewColumn;
    private String typeOfColumn;

    private String isReceivedGoodsStr;
    private String isReceivedObstructStr;
    private String isFenceStr;
    private String receivedGoodsContent;
    private String receivedObstructContent;
    private String isACStr;

    //thong tin bat buoc
    private long assignHandoverId;
    private long catStationHouseId;
    private String cntContractCode;
    private long cntContractId;
    private String constructionCode;
    private long constructionId;
    private long sysGroupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bgmb_update);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ((App) getApplication()).setNeedUpdateBGMB(false);
        initView();
        initControls();
        progress.setLoading(true);
        loadData();
        checkAllView();
        progress.setLoading(false);
    }

    private void checkAllView() {
        txtHeader_update.setText(constructionBGMBDTO.getConstructionCode());
        rD_Duoidat_Update.setChecked(true);
        rD_TrenMai_Update.setChecked(false);

        mListGrounding = (ArrayList<ItemSpinnerBgmb>) TabPageBgmbConstruction_ItemFragment.mListGrounding;
        mListHouse = (ArrayList<ItemSpinnerBgmb>) TabPageBgmbConstruction_ItemFragment.mListHouse;
        houseTypeId = Long.parseLong(mListHouse.get(0).getCode());
        houseTypeName = mListHouse.get(0).getName();

        groundingTypeId = Long.parseLong(mListGrounding.get(0).getCode());
        groundingTypeName = mListGrounding.get(0).getName();

        Log.d(TAG, "mListGrounding : " + mListGrounding.size() + " - mListHouse : " + mListHouse.size());
        Log.d(TAG, "groundingTypeId : " + groundingTypeId + " - groundingTypeName : " + groundingTypeName);
        Log.d(TAG, "houseTypeId : " + houseTypeId + " - houseTypeName : " + houseTypeName);

        spinner_tiepdia = (Spinner) findViewById(R.id.spinner_tiepdia);
        spinner_tiepdia.setOnItemSelectedListener(this);
        SpinnerBgmbAdapter adapterTiepDia = new SpinnerBgmbAdapter(mListGrounding, this);
        spinner_tiepdia.setAdapter(adapterTiepDia);

        spinner_nha = (Spinner) findViewById(R.id.spinner_nha);
        spinner_nha.setOnItemSelectedListener(this);
        SpinnerBgmbAdapter adapterNha = new SpinnerBgmbAdapter(mListHouse, this);
        spinner_nha.setAdapter(adapterNha);

        checkbox_AC_Update.setChecked(false);
        checkbox_TiepDia_Update.setChecked(false);
        checkbox_Cotcosan_Update.setChecked(false);
        checkbox_NhaCoSan_Update.setChecked(false);

        checkbox_NhaCoSan_Update.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ln_loainha.setVisibility(View.GONE);
                } else {
                    ln_loainha.setVisibility(View.VISIBLE);
                }
            }
        });

        checkbox_Cotcosan_Update.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ln_docaocot_update.setVisibility(View.GONE);
                    ln_somoco_update.setVisibility(View.GONE);
                } else {
                    ln_docaocot_update.setVisibility(View.VISIBLE);
                    ln_somoco_update.setVisibility(View.VISIBLE);
                }
            }
        });

        checkbox_TiepDia_Update.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ln_loaitiepdia.setVisibility(View.GONE);
                } else {
                    ln_loaitiepdia.setVisibility(View.VISIBLE);
                }
            }
        });


        checkbox_Vuong_Update.setChecked(false);
        title_Vuong_Update.setVisibility(View.GONE);
        noidung_Vuong__Update.setVisibility(View.GONE);

        checkbox_Vuong_Update.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    title_Vuong_Update.setVisibility(View.VISIBLE);
                    noidung_Vuong__Update.setVisibility(View.VISIBLE);
                } else {
                    title_Vuong_Update.setVisibility(View.GONE);
                    noidung_Vuong__Update.setVisibility(View.GONE);
                }
            }
        });

        checkbox_VatTu__Update.setChecked(false);
        title_Vattu_Update.setVisibility(View.GONE);
        noidung_VatTu__Update.setVisibility(View.GONE);

        checkbox_VatTu__Update.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    title_Vattu_Update.setVisibility(View.VISIBLE);
                    noidung_VatTu__Update.setVisibility(View.VISIBLE);
                } else {
                    title_Vattu_Update.setVisibility(View.GONE);
                    noidung_VatTu__Update.setVisibility(View.GONE);
                }
            }
        });

        setShowHideLoaiCongTrinhMB(mIsCTMayNoGiaCo);
    }

    private void loadData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            constructionBGMBDTO = (ConstructionBGMBDTO) bundle.getSerializable(VConstant.BUNDLE_KEY_OBJECT_BGMB);
            assignHandoverId = constructionBGMBDTO.getAssignHandoverId();
            catStationHouseId = constructionBGMBDTO.getCatStationHouseId();
            cntContractCode = constructionBGMBDTO.getCntContractCode();
            cntContractId = constructionBGMBDTO.getCntContractId();
            constructionCode = constructionBGMBDTO.getConstructionCode();
            constructionId = constructionBGMBDTO.getConstructionId();
            sysGroupId = constructionBGMBDTO.getSysGroupId();
            mIsCTMayNoGiaCo = constructionBGMBDTO.getCatConstructionType() == CONG_TRINH_MAY_NO ||
                    constructionBGMBDTO.getCatConstructionType() == CONT_TRINH_GIA_CO;

        }
    }

    private void initControls() {
        imgBack_update.setOnClickListener(this);
        btn_camera_Update.setOnClickListener(this);
        txtSave_update.setOnClickListener(this);
        mListUrl = new ArrayList<>();
        mListUrlUpload = new ArrayList<>();
        setUpView();
    }

    private void initView() {
        rD_Duoidat_Update = (RadioButton) findViewById(R.id.rD_Duoidat_Update);
        rD_TrenMai_Update = (RadioButton) findViewById(R.id.rD_TrenMai_Update);
        txtHeader_update = (TextView) findViewById(R.id.txtHeader_update);
        txtSave_update = (TextView) findViewById(R.id.txtSave_update);
        imgBack_update = (ImageView) findViewById(R.id.imgBack_update);
        btn_camera_Update = (ImageView) findViewById(R.id.btn_camera_Update);
        checkbox_NhaCoSan_Update = (CheckBox) findViewById(R.id.checkbox_NhaCoSan_Update);
        checkbox_Cotcosan_Update = (CheckBox) findViewById(R.id.checkbox_Cotcosan_Update);
        checkbox_TiepDia_Update = (CheckBox) findViewById(R.id.checkbox_TiepDia_Update);
        checkbox_AC_Update = (CheckBox) findViewById(R.id.checkbox_AC_Update);
        edt_sommocoUpdate = (EditText) findViewById(R.id.edt_sommocoUpdate);
        edt_docaocotUpdate = (EditText) findViewById(R.id.edt_docaocotUpdate);
        ln_docaocot_update = (LinearLayout) findViewById(R.id.ln_docaocot_update);
        ln_somoco_update = (LinearLayout) findViewById(R.id.ln_somoco_update);
        ln_loainha = (LinearLayout) findViewById(R.id.ln_loainha);
        ln_loaitiepdia = (LinearLayout) findViewById(R.id.ln_loaitiepdia);
        rv_list_image_update = (RecyclerView) findViewById(R.id.rv_list_image_update);
        checkbox_Tuongrao__Update = (CheckBox) findViewById(R.id.checkbox_Tuongrao__Update);
        checkbox_VatTu__Update = (CheckBox) findViewById(R.id.checkbox_VatTu__Update);
        checkbox_Vuong_Update = (CheckBox) findViewById(R.id.checkbox_Vuong_Update);
        title_Vuong_Update = (TextView) findViewById(R.id.title_Vuong_Update);
        noidung_Vuong__Update = (EditText) findViewById(R.id.noidung_Vuong__Update);
        title_Vattu_Update = (TextView) findViewById(R.id.title_Vattu_Update);
        noidung_VatTu__Update = (EditText) findViewById(R.id.noidung_VatTu__Update);
        progress = (CustomProgress) findViewById(R.id.prg_loading_update);
        checkboxXPXDUpdate = (CheckBox) findViewById(R.id.checkboxXPXDUpdate);
        checkboxXPACUpdate = (CheckBox) findViewById(R.id.checkboxXPACUpdate);

        // add new model
        mColumnsAvaible = (EditText) findViewById(R.id.edt_so_cot_do_san);
        mLengthOfMeter = (EditText) findViewById(R.id.edt_so_met_day);
        mHaveStartPoint = (CheckBox) findViewById(R.id.checkbox_da_co_diem_dau);
        mTypethOfMeter = (EditText) findViewById(R.id.edt_chung_loai_day);
        mNumNewColumn = (EditText) findViewById(R.id.edt_so_cot_trong_moi);
        mTypeOfColumn = (EditText) findViewById(R.id.edt_chung_loai_cot);

        // add thêm loại công trình gia cố mặt bằng
        mRbMayNo = (RadioButton) findViewById(R.id.rb_MayNo);
        mRbGiaCo = (RadioButton) findViewById(R.id.rb_GiaCo);
        // container view
        mLnThiCong = (LinearLayout) findViewById(R.id.lnThiCong);
        mLnLoaiCT = (LinearLayout) findViewById(R.id.lnLoaiCT);
        mLnGiayPhep = (LinearLayout) findViewById(R.id.lnGiayPhepXD);
        mLnHangMuc = (LinearLayout) findViewById(R.id.lnHangMucCS);
        mLnThongTinMB = (LinearLayout) findViewById(R.id.lnThongtinMB);
        mLnThongTinMBAC = (LinearLayout) findViewById(R.id.lnThongTinAC);

        checkbox_AC_Update.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mLnThongTinMBAC.setVisibility(isChecked ? View.GONE : View.VISIBLE);
            }
        });

    }

    private void setUpView() {
        mAdapter = new ImageInProcessAdapter(mListUrl, this, true, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_list_image_update.setLayoutManager(linearLayoutManager);
        rv_list_image_update.setAdapter(mAdapter);
    }

    @Override
    public void onDelete(int pos) {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack_update:
                finish();
                break;
            case R.id.btn_camera_Update:
                onClickBtnCamera();
                break;
            case R.id.txtSave_update:
                onSave();
                break;
        }
    }

    private boolean caseSepecial() {
        if (checkbox_NhaCoSan_Update.isChecked() && checkbox_Cotcosan_Update.isChecked()) {
            return true;
        }
        return false;
    }

    private void onSave() {
        progress.setLoading(true);
        txtSave_update.setClickable(false);
        if (!mIsCTMayNoGiaCo) {
            //Check Anh
            if (mListUrl.size() > 0 || caseSepecial()) {
                String messageValiDate = checkValideWork();
                if (messageValiDate == null) {

                    //get thong tin thi cong
                    stationType = ToGetStationType();

                    //get hang muc co san
                    haveWorkItemName = ToGetHaveWorkItemTime();

                    //getThongtinbangiao mb


                    if (checkbox_Cotcosan_Update.isChecked()) {
                        numberCo = 0;
                    } else {
                        long numberCo1 = 0;
                        if (edt_sommocoUpdate.getText() != null) {
                            numberCo1 = Long.parseLong(edt_sommocoUpdate.getText().toString());
                        }

                        if (numberCo1 > 99) {
                            Toast.makeText(this, "Số mố co phải nhỏ hơn 100. Vui lòng nhập đúng thông tin!", Toast.LENGTH_LONG).show();
                            txtSave_update.setClickable(true);
                            progress.setLoading(false);
                            return;
                        } else {
                            numberCo = numberCo1;
                        }
                    }

                    if (checkbox_Cotcosan_Update.isChecked()) {
                        columnHeight = 0;
                    } else {
                        columnHeight = Long.parseLong(edt_docaocotUpdate.getText().toString());
                        if (columnHeight < 1) {
                            progress.setLoading(false);
                            Toast.makeText(this, "Độ cao cột phải lớn hơn 0.Vui lòng nhập đúng thông tin!", Toast.LENGTH_LONG).show();
                            txtSave_update.setClickable(true);
                            return;
                        }
                    }

                    if (checkbox_NhaCoSan_Update.isChecked()) {
                        houseTypeName = null;
                        houseTypeId = 0;
                    }

                    if (checkbox_TiepDia_Update.isChecked()) {
                        groundingTypeName = null;
                        groundingTypeId = 0;
                    }

                    //get noi dung vuong, vat tu
                    if (checkbox_Tuongrao__Update.isChecked()) {
                        isFenceStr = "1";
                    } else {
                        isFenceStr = "0";
                    }

                    //get noi dung AC

                    if (checkbox_Vuong_Update.isChecked()) {
                        isReceivedObstructStr = "1";
                        receivedObstructContent = noidung_Vuong__Update.getText().toString();
                    } else {
                        isReceivedObstructStr = "0";
                        receivedObstructContent = null;
                    }

                    if (checkbox_VatTu__Update.isChecked()) {
                        isReceivedGoodsStr = "1";
                        receivedGoodsContent = noidung_VatTu__Update.getText().toString();
                    } else {
                        isReceivedGoodsStr = "0";
                        receivedGoodsContent = null;
                    }

                    if (checkboxXPACUpdate.isChecked()) {
                        checkXPAC = 1;
                    } else {
                        checkXPAC = 0;
                    }

                    if (checkboxXPXDUpdate.isChecked()) {
                        checkXPXD = 1;
                    } else {
                        checkXPXD = 0;
                    }

                    // Add them 6 thong tin và một trường check book
                    boolean isAcUpdate = checkbox_AC_Update.isChecked();
                    isACStr = isAcUpdate ? "1" : "0";
                    numColumnsAvaible = !isAcUpdate ? mColumnsAvaible.getText().toString() : null;
                    lengthOfMeter = !isAcUpdate ? mLengthOfMeter.getText().toString() : null;
                    haveStartPoint = !isAcUpdate ? mHaveStartPoint.isChecked() ? "1" : "0" : null;
                    typeOfMeter = !isAcUpdate ? mTypethOfMeter.getText().toString() : null;
                    numNewColumn = !isAcUpdate ? mNumNewColumn.getText().toString() : null;
                    typeOfColumn = !isAcUpdate ? mTypeOfColumn.getText().toString() : null;
                    typeConstructionBgmb = "0";
                    //du thong tin se update
                    updateWorkBGMB(false, checkXPXD, checkXPAC, assignHandoverId, catStationHouseId, cntContractCode, cntContractId, constructionCode, constructionId, sysGroupId, columnHeight, groundingTypeId, groundingTypeName,
                            haveWorkItemName, houseTypeId, houseTypeName, isFenceStr, isReceivedGoodsStr, isReceivedObstructStr, numberCo, receivedObstructContent, receivedGoodsContent, stationType, isACStr,
                            numColumnsAvaible, lengthOfMeter, haveStartPoint, typeOfMeter, numNewColumn, typeOfColumn, typeConstructionBgmb);


                } else {
                    Log.d(TAG, "thieu thong tin");
                    txtSave_update.setClickable(true);
                    progress.setLoading(false);
                    Toast.makeText(this, messageValiDate, Toast.LENGTH_LONG).show();
                }
            } else {
                txtSave_update.setClickable(true);
                progress.setLoading(false);
                Toast.makeText(this, getResources().getString(R.string.please_take_image), Toast.LENGTH_LONG).show();
            }
        } else {
            // update rieng  cho cong trinh gia co may no
            if (mListUrl.size() > 0) {
                if (mRbMayNo.isChecked() || mRbGiaCo.isChecked()) {
                    String message = checkValidateVuongMayDo();
                    if (message == null) {
                        typeConstructionBgmb = mRbGiaCo.isChecked() ? "1" : "2";

                        // Vuong
                        if (checkbox_Vuong_Update.isChecked()) {
                            isReceivedObstructStr = "1";
                            receivedObstructContent = noidung_Vuong__Update.getText().toString();
                        } else {
                            isReceivedObstructStr = "0";
                            receivedObstructContent = null;
                        }
                        // Vat tu may do
                        if (checkbox_VatTu__Update.isChecked()) {
                            isReceivedGoodsStr = "1";
                            receivedGoodsContent = noidung_VatTu__Update.getText().toString();
                        } else {
                            isReceivedGoodsStr = "0";
                            receivedGoodsContent = null;
                        }
                        // Tuong rao
                        if (checkbox_Tuongrao__Update.isChecked()) {
                            isFenceStr = "1";
                        } else {
                            isFenceStr = "0";
                        }
                        updateWorkBGMB(true, VALUE0_L, VALUE0_L, assignHandoverId, catStationHouseId, cntContractCode, cntContractId, constructionCode, constructionId, sysGroupId, VALUE0_L, VALUE0_L, VALUE0_S,
                                VALUE0_S, VALUE0_L, VALUE0_S, isFenceStr, isReceivedGoodsStr, isReceivedObstructStr, VALUE0_L, receivedObstructContent, receivedGoodsContent, VALUE0_L, VALUE0_S,
                                VALUE0_S, VALUE0_S, VALUE0_S, VALUE0_S, VALUE0_S, VALUE0_S, typeConstructionBgmb);
                    } else {
                        txtSave_update.setClickable(true);
                        progress.setLoading(false);
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                    }
                } else {
                    txtSave_update.setClickable(true);
                    progress.setLoading(false);
                    Toast.makeText(this, "Vui lòng chọn loại công trình.", Toast.LENGTH_LONG).show();

                }

            } else {
                txtSave_update.setClickable(true);
                progress.setLoading(false);
                Toast.makeText(this, getResources().getString(R.string.please_take_image), Toast.LENGTH_LONG).show();
            }
        }


    }

    private String checkValidateVuongMayDo() {
        String message = null;
        if (checkbox_Vuong_Update.isChecked() && noidung_Vuong__Update.getText().toString().length() < 1) {
            return getResources().getString(R.string.please_input_vuong);
        }

        if (checkbox_VatTu__Update.isChecked() && noidung_VatTu__Update.getText().toString().length() < 1) {
            return getResources().getString(R.string.please_input_may_do);
        }

        return message;
    }


    private void updateWorkBGMB(boolean isGiaCoMayNo, long checkXPXD, long checkXPAC, long assignHandoverId, long catStationHouseId, String cntContractCode, long cntContractId, String constructionCode,
                                long constructionId, long sysGroupId, long columnHeight, long groundingTypeId, String groundingTypeName,
                                String haveWorkItemName, long houseTypeId, String houseTypeName, String isFenceStr, String isReceivedGoodsStr,
                                String isReceivedObstructStr, long numberCo, String receivedObstructContent, String receivedGoodsContent,
                                long stationType, String isACStr,
                                String numColumnsAvaible, String lengthOfMeter, String haveStartPoint, String typeOfMeter, String numNewColumn
            , String typeOfColumn, String typeConstructionBgmb) {
        //filter list image for upload image , only status -1 , 0
        for (int i = 0; i < mListUrl.size(); i++) {
            if (mListUrl.get(i).getStatus() == 0) {
                mListUrlUpload.add(mListUrl.get(i));
                String splitFormatImage = mListUrlUpload.get(i).getImageName();
                mListUrlUpload.get(i).setImageName(splitFormatImage);
            }
        }

        ApiManager.getInstance().updateBGMB(isGiaCoMayNo, checkXPXD, checkXPAC, assignHandoverId, catStationHouseId, cntContractCode, cntContractId, constructionCode,
                constructionId, sysGroupId, columnHeight, groundingTypeId, groundingTypeName,
                haveWorkItemName, houseTypeId, houseTypeName, isFenceStr, isReceivedGoodsStr,
                isReceivedObstructStr, numberCo, receivedObstructContent, receivedGoodsContent,
                stationType, isACStr, numColumnsAvaible, lengthOfMeter, haveStartPoint, typeOfMeter, numNewColumn
                , typeOfColumn, typeConstructionBgmb,
                mListUrl, ConstructionBGMBResponse.class, new IOnRequestListener() {
                    @Override
                    public <T> void onResponse(T result) {
                        try {
                            ConstructionBGMBResponse resultApi = ConstructionBGMBResponse.class.cast(result);
                            if (resultApi.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                                //Danh dau Update sau khi save detail
                                ((App) getApplication()).setNeedUpdateBGMB(true);
                                Log.d(TAG, "onResponse - OK");
                                finish();
                                Toast.makeText(BgmbUpdateActivity.this, getResources().getString(R.string.updated), Toast.LENGTH_SHORT).show();

                            } else {
                                Log.d(TAG, "onResponse - fail");
                                Toast.makeText(BgmbUpdateActivity.this, getResources().getString(R.string.update_fail), Toast.LENGTH_SHORT).show();
                                progress.setLoading(false);
                                txtSave_update.setClickable(true);
                            }
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(int statusCode) {
                        Log.d(TAG, "onResponse - onError");
                        Toast.makeText(BgmbUpdateActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                        progress.setLoading(false);
                        txtSave_update.setClickable(true);
                    }
                });
    }

    private String ToGetHaveWorkItemTime() {
        String havework = "";
        StringBuilder strBuilder = new StringBuilder("");
        boolean isFirst = false;
        if (checkbox_Cotcosan_Update.isChecked()) {
            if (!isFirst) {
                strBuilder.append("Cột");
                isFirst = true;
            } else {
                strBuilder.append(",Cột");
            }
        }

        if (checkbox_TiepDia_Update.isChecked()) {
            if (!isFirst) {
                strBuilder.append("Tiếp địa");
                isFirst = true;
            } else {
                strBuilder.append(",Tiếp địa");
            }
        }

        if (checkbox_NhaCoSan_Update.isChecked()) {
            if (!isFirst) {
                strBuilder.append("Nhà");
                isFirst = true;
            } else {
                strBuilder.append(",Nhà");
            }
        }

        if (checkbox_AC_Update.isChecked()) {
            if (!isFirst) {
                strBuilder.append("AC");
                isFirst = true;
            } else {
                strBuilder.append(",AC");
            }
        }
        havework = strBuilder.toString();
        return havework;
    }

    private long ToGetStationType() {
        long thicong = 1;
        if (rD_Duoidat_Update.isChecked()) {
            thicong = 1;
        } else if (rD_TrenMai_Update.isChecked()) {
            thicong = 2;
        }
        return thicong;
    }

    private String checkValideWork() {
        String error_msg = null;
        if (checkbox_Vuong_Update.isChecked() && noidung_Vuong__Update.getText().toString().length() < 1) {
            return getResources().getString(R.string.please_input_vuong);
        }

        if (checkbox_VatTu__Update.isChecked() && noidung_VatTu__Update.getText().toString().length() < 1) {
            return getResources().getString(R.string.please_input_may_do);
        }

        if (!checkbox_Cotcosan_Update.isChecked() && edt_docaocotUpdate.getText().toString().length() < 1) {
            return getResources().getString(R.string.please_input_do_cao_cot);
        }

        if (!checkbox_Cotcosan_Update.isChecked() && (edt_sommocoUpdate.getText().toString().length() < 1)) {
            return getResources().getString(R.string.please_input_so_mo_co);
        }
        return error_msg;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner_nha) {
            houseTypeId = Long.parseLong(mListHouse.get(position).getCode());
            houseTypeName = mListHouse.get(position).getName();
            Log.d(TAG, "houseTypeId : " + houseTypeId + " - houseTypeName : " + houseTypeName);
        } else if (parent.getId() == R.id.spinner_tiepdia) {
            groundingTypeId = Long.parseLong(mListGrounding.get(position).getCode());
            groundingTypeName = mListGrounding.get(position).getName();
            Log.d(TAG, "groundingTypeId : " + groundingTypeId + " - groundingTypeName : " + groundingTypeName);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case VConstant.REQUEST_CODE_CAMERA:
                    if (resultCode == Activity.RESULT_OK) {
                        filePath = mPhotoFile.getPath();
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String pictureName = "IMG_" + timeStamp + ".jpg";
                        Bitmap bitmap = ImageUtils.decodeBitmapFromFile(filePath, 200, 200);
                        Bitmap newBitmap =
                                ImageUtils.drawTextOnImage(bitmap, latitude, longitude, constructionBGMBDTO.getConstructionCode(), constructionBGMBDTO.getCntContractCode());
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

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setShowHideLoaiCongTrinhMB(boolean isCTGiaCoMayNo) {
        mLnLoaiCT.setVisibility(isCTGiaCoMayNo ? View.VISIBLE : View.GONE);
        mLnThiCong.setVisibility(isCTGiaCoMayNo ? View.GONE : View.VISIBLE);
        mLnGiayPhep.setVisibility(isCTGiaCoMayNo ? View.GONE : View.VISIBLE);
        mLnHangMuc.setVisibility(isCTGiaCoMayNo ? View.GONE : View.VISIBLE);
        mLnThongTinMB.setVisibility(isCTGiaCoMayNo ? View.GONE : View.VISIBLE);
        mLnThongTinMBAC.setVisibility(isCTGiaCoMayNo ? View.GONE : View.VISIBLE);
    }


}
