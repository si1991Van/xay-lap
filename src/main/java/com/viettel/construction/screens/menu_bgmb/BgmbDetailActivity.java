package com.viettel.construction.screens.menu_bgmb;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.R;
import com.viettel.construction.appbase.ImageCompleteBgmbAdapter;
import com.viettel.construction.common.App;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionBGMBDTO;
import com.viettel.construction.model.api.ConstructionBGMBResponse;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.screens.custom.dialog.CustomProgress;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;

import java.util.ArrayList;
import java.util.List;

public class BgmbDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int CONG_TRINH_MAY_NO = 5;
    public static final int CONT_TRINH_GIA_CO = 6;
    private boolean mIsCTMayNoGiaCo = false;

    private final String TAG = "VTBgmbDetailActivity";
    private TextView txtHeader_Done, noidung_Cosan_Done, sommoco_Done, docaocot_Done, title_Vuong,
            noidung_Vuong_Done, title_Vattu, noidung_VatTu_Done, loainha_Done, loaitiepdia_Done;
    private ImageView imgBack_Done;
    private RecyclerView rv_list_Detail;
    private RadioButton rD_Duoidat, rD_TrenMai;
    private CheckBox checkbox_Vuong_Done, checkbox_VatTu_Done, checkbox_Tuongrao_Done, checkboxXPXD, checkboxXPAC;
    private ConstructionBGMBDTO constructionBGMBDTO;
    CustomProgress progress;
    public static List<ConstructionImageInfo> mListUrl;
    private LinearLayout ln_docaocot_Done, ln_loainha, ln_tiepdiaDone;
    private long id;
    private ImageCompleteBgmbAdapter mAdapter;

    // Add thêm tính năng  nhận MBAC
    private TextView mColumnsAvaible, mLengthOfMeter, mTypethOfMeter, mNumNewColumn, mTypeOfColumn;
    private CheckBox mCBHaveStartPoint;
    private RadioButton mRdGiaCo, mRdMayno;

    private LinearLayout mLnThiCong, mLnLoaiCT, mLnGiayPhep, mLnHangMuc, mLnThongTinMB, mLnThongTinMBAC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bgmb_detail);
        ((App) getApplication()).setNeedUpdateBGMB(false);
        loadData();
        initView();
        initControls();
        progress.setLoading(true);
        setAllView();
        initListImageFromServer();
    }

    private void initControls() {
        mListUrl = new ArrayList<>();
        imgBack_Done.setOnClickListener(this);
    }

    private void setAllView() {
        txtHeader_Done.setText(constructionBGMBDTO.getConstructionCode());
        //thong tin thi cong
        long thicong = constructionBGMBDTO.getStationType();
        if (thicong == 1) {
            rD_Duoidat.setChecked(true);
            rD_TrenMai.setChecked(false);
            rD_Duoidat.setClickable(false);
            rD_TrenMai.setClickable(false);
        } else if (thicong == 2) {
            rD_Duoidat.setChecked(false);
            rD_TrenMai.setChecked(true);
            rD_Duoidat.setClickable(false);
            rD_TrenMai.setClickable(false);
        }

        // thong tin hang muc co san
        if (constructionBGMBDTO.getHaveWorkItemName() != null && constructionBGMBDTO.getHaveWorkItemName().length() > 0) {
            noidung_Cosan_Done.setText(constructionBGMBDTO.getHaveWorkItemName());
        } else {
            noidung_Cosan_Done.setText("Không có hạng mục nào có sẵn.");
        }

        //thong tin them
        sommoco_Done.setText(constructionBGMBDTO.getNumberCo() + "");

        // do cao cot - check hang muc co san
        ln_docaocot_Done.setVisibility(View.VISIBLE);
        if (constructionBGMBDTO.getColumnHeight() > 0) {
            docaocot_Done.setText(constructionBGMBDTO.getColumnHeight() + "");
        } else {
            ln_docaocot_Done.setVisibility(View.GONE);
            docaocot_Done.setText("Có sẵn");
        }

        // loai nha
        ln_loainha.setVisibility(View.VISIBLE);
        if (constructionBGMBDTO.getHouseTypeId() > 0) {
            loainha_Done.setText(constructionBGMBDTO.getHouseTypeName());
        } else {
            loainha_Done.setText("Có sẵn");
            ln_loainha.setVisibility(View.GONE);
        }

        // loai tiepdia
        ln_tiepdiaDone.setVisibility(View.VISIBLE);
        if (constructionBGMBDTO.getGroundingTypeId() > 0) {
            loaitiepdia_Done.setText(constructionBGMBDTO.getGroundingTypeName());
        } else {
            loaitiepdia_Done.setText("Có sẵn");
            ln_tiepdiaDone.setVisibility(View.GONE);
        }

        // noi dung vuong
        checkbox_Vuong_Done.setClickable(false);
        title_Vuong.setVisibility(View.GONE);
        noidung_Vuong_Done.setVisibility(View.GONE);
        if (constructionBGMBDTO.getReceivedObstructContent() == null || constructionBGMBDTO.getReceivedObstructContent().length() == 0) {
            checkbox_Vuong_Done.setChecked(false);
        } else {
            checkbox_Vuong_Done.setChecked(true);
            title_Vuong.setVisibility(View.VISIBLE);
            noidung_Vuong_Done.setVisibility(View.VISIBLE);
            noidung_Vuong_Done.setText(constructionBGMBDTO.getReceivedObstructContent());
        }

        //noi dung vat tu
        checkbox_VatTu_Done.setClickable(false);
        title_Vattu.setVisibility(View.GONE);
        noidung_VatTu_Done.setVisibility(View.GONE);
        if (constructionBGMBDTO.getReceivedGoodsContent() == null || constructionBGMBDTO.getReceivedGoodsContent().length() == 0) {
            checkbox_VatTu_Done.setChecked(false);
        } else {
            checkbox_VatTu_Done.setChecked(true);
            title_Vattu.setVisibility(View.VISIBLE);
            noidung_VatTu_Done.setVisibility(View.VISIBLE);
            noidung_VatTu_Done.setText(constructionBGMBDTO.getReceivedGoodsContent());
        }

        //tuong rao
        checkbox_Tuongrao_Done.setClickable(false);
        if (constructionBGMBDTO.getIsFenceStr() != null && constructionBGMBDTO.getIsFenceStr().equals("1")) {
            checkbox_Tuongrao_Done.setChecked(true);
        } else {
            checkbox_Tuongrao_Done.setChecked(false);
        }

        // giay phep xay dung
        checkboxXPAC.setClickable(false);
        if (constructionBGMBDTO.getCheckXPAC() == 1) {
            checkboxXPAC.setChecked(true);
        } else {
            checkboxXPAC.setChecked(false);
        }

        // mat bang Ac
        if (constructionBGMBDTO.getNumColumnsAvaible() != null)
            mColumnsAvaible.setText(constructionBGMBDTO.getNumColumnsAvaible());
        if (constructionBGMBDTO.getLengthOfMeter() != null)
            mLengthOfMeter.setText(constructionBGMBDTO.getLengthOfMeter());
        if (constructionBGMBDTO.getTypeOfMeter() != null)
            mTypethOfMeter.setText(constructionBGMBDTO.getTypeOfMeter());
        if (constructionBGMBDTO.getNumNewColumn() != null)
            mNumNewColumn.setText(constructionBGMBDTO.getNumNewColumn());
        if (constructionBGMBDTO.getTypeOfColumn() != null)
            mTypeOfColumn.setText(constructionBGMBDTO.getTypeOfColumn());
        if (constructionBGMBDTO.getHaveStartPoint() != null)
            mCBHaveStartPoint.setChecked(constructionBGMBDTO.getHaveStartPoint().equals("1") ? true : false);
        if (constructionBGMBDTO.getTypeConstructionBgmb() != null) {
            if (constructionBGMBDTO.getTypeConstructionBgmb().equals("1")) {
                mRdGiaCo.setChecked(true);
            } else if (constructionBGMBDTO.getTypeConstructionBgmb().equals("2")) {
                mRdMayno.setChecked(true);
            }
        }

        checkboxXPXD.setClickable(false);
        if (constructionBGMBDTO.getCheckXPXD() == 1) {
            checkboxXPXD.setChecked(true);
        } else {
            checkboxXPXD.setChecked(false);
        }
        setShowHideLoaiCongTrinhMB(mIsCTMayNoGiaCo);
        if((constructionBGMBDTO.getHaveWorkItemName() != null && constructionBGMBDTO.getHaveWorkItemName().contains("AC"))){
            mLnThongTinMBAC.setVisibility(View.GONE);
        }else{
            mLnThongTinMBAC.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "getIsFence : " + constructionBGMBDTO.getIsFence() + " - getGroundingTypeId() : " + constructionBGMBDTO.getGroundingTypeId()
                + " - getHouseTypeId : " + constructionBGMBDTO.getHouseTypeId());
    }

    private void initListImageFromServer() {
        ApiManager.getInstance().loadImageBGMB(id, ConstructionBGMBResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ConstructionBGMBResponse resultApi = ConstructionBGMBResponse.class.cast(result);
                    if (resultApi.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        if (resultApi.getConstructionImageInfo() != null && resultApi.getConstructionImageInfo().size() > 0) {
                            // add image to list
                            mListUrl.addAll(resultApi.getConstructionImageInfo());
                            Log.d(TAG, "initListImageFromServer - size : " + mListUrl.size());
                            setupRecyclerView();
                            progress.setLoading(false);
                        } else {
                            progress.setLoading(false);
                            if (resultApi.getResultInfo().getMessage() != null && resultApi.getResultInfo().getMessage().length() > 0) {
                                Toast.makeText(BgmbDetailActivity.this, resultApi.getResultInfo().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                progress.setLoading(false);
                Toast.makeText(BgmbDetailActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            constructionBGMBDTO = (ConstructionBGMBDTO) bundle.getSerializable(VConstant.BUNDLE_KEY_OBJECT_BGMB);
            id = constructionBGMBDTO.getAssignHandoverId();
            mIsCTMayNoGiaCo = constructionBGMBDTO.getCatConstructionType() == CONG_TRINH_MAY_NO ||
                    constructionBGMBDTO.getCatConstructionType() == CONT_TRINH_GIA_CO;
        }
    }

    private void setupRecyclerView() {
        mAdapter = new ImageCompleteBgmbAdapter(1, mListUrl, this, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_list_Detail.setLayoutManager(linearLayoutManager);
        rv_list_Detail.setAdapter(mAdapter);
    }

    private void initView() {
        txtHeader_Done = (TextView) findViewById(R.id.txtHeader_Done);
        noidung_Cosan_Done = (TextView) findViewById(R.id.noidung_Cosan_Done);
        sommoco_Done = (TextView) findViewById(R.id.sommoco_Done);
        docaocot_Done = (TextView) findViewById(R.id.docaocot_Done);
        title_Vuong = (TextView) findViewById(R.id.title_Vuong);
        noidung_Vuong_Done = (TextView) findViewById(R.id.noidung_Vuong_Done);
        title_Vattu = (TextView) findViewById(R.id.title_Vattu);
        noidung_VatTu_Done = (TextView) findViewById(R.id.noidung_VatTu_Done);
        imgBack_Done = (ImageView) findViewById(R.id.imgBack_Done);
        rv_list_Detail = (RecyclerView) findViewById(R.id.rv_list_Detail);
        rD_Duoidat = (RadioButton) findViewById(R.id.rD_Duoidat);
        rD_TrenMai = (RadioButton) findViewById(R.id.rD_TrenMai);
        checkbox_Vuong_Done = (CheckBox) findViewById(R.id.checkbox_Vuong_Done);
        checkbox_VatTu_Done = (CheckBox) findViewById(R.id.checkbox_VatTu_Done);
        checkbox_Tuongrao_Done = (CheckBox) findViewById(R.id.checkbox_Tuongrao_Done);
        progress = (CustomProgress) findViewById(R.id.prg_loading);
        ln_docaocot_Done = (LinearLayout) findViewById(R.id.ln_docaocot_Done);
        ln_loainha = (LinearLayout) findViewById(R.id.ln_loainha);
        ln_tiepdiaDone = (LinearLayout) findViewById(R.id.ln_tiepdiaDone);
        loaitiepdia_Done = (TextView) findViewById(R.id.loaitiepdia_Done);
        loainha_Done = (TextView) findViewById(R.id.loainha_Done);
        checkboxXPAC = (CheckBox) findViewById(R.id.checkboxXPAC);
        checkboxXPXD = (CheckBox) findViewById(R.id.checkboxXPXD);

        mColumnsAvaible = (TextView) findViewById(R.id.tv_so_cot_do_san);
        mLengthOfMeter = (TextView) findViewById(R.id.tv_so_met_day);
        mTypethOfMeter = (TextView) findViewById(R.id.tv_chung_loai_day);
        mNumNewColumn = (TextView) findViewById(R.id.tv_so_cot_trong_moi);
        mTypeOfColumn = (TextView) findViewById(R.id.tv_chung_loai_cot);
        mCBHaveStartPoint = (CheckBox) findViewById(R.id.checkbox_da_co_diem_dau);
        mRdGiaCo = (RadioButton) findViewById(R.id.cb_GiaCo);
        mRdMayno = (RadioButton) findViewById(R.id.cb_MayNo);

        mLnThiCong = (LinearLayout) findViewById(R.id.lnThiCong);
        mLnLoaiCT = (LinearLayout) findViewById(R.id.lnLoaiCT);
        mLnGiayPhep = (LinearLayout) findViewById(R.id.lnGiayPhepXD);
        mLnHangMuc = (LinearLayout) findViewById(R.id.lnHangMucCS);
        mLnThongTinMB = (LinearLayout) findViewById(R.id.lnThongtinMB);
        mLnThongTinMBAC = (LinearLayout) findViewById(R.id.lnThongTinAC);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgBack_Done) {
            finish();
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
