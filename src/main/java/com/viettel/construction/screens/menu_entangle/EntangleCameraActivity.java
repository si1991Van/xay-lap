package com.viettel.construction.screens.menu_entangle;

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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.screens.atemp.adapter.ImageDetailEntangleAdapter;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;
import com.viettel.construction.server.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.api.entangle.EntangleManageDTO;
import com.viettel.construction.model.api.entangle.EntangleManageDTORespone;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.util.ImageUtils;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.screens.custom.dialog.CustomProgress;

/**
 * Hiển thị chi tiết công trình vướng
 */

public class EntangleCameraActivity extends BaseCameraActivity implements ImageDetailEntangleAdapter.OnClickDelete {


    @BindView(R.id.btn_camera)
    ImageView mBtnCamera;
    @BindView(R.id.rcv_image_cumbersome)
    RecyclerView rcvCumbersome;
    @BindView(R.id.txt_construction_code)
    TextView txtConstructionCode;
    @BindView(R.id.txt_construction_name)
    TextView txtConstructionName;
    @BindView(R.id.rad_1)
    RadioButton rad1;
    @BindView(R.id.rad_2)
    RadioButton rad2;
    @BindView(R.id.rad_3)
    RadioButton rad3;
    @BindView(R.id.edt_entangle)
    EditText edtEntangle;

    @BindView(R.id.txtHangMuc)
    TextView txtHangMuc;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.txtXacNhanTitle)
    TextView txtXacNhanTitle;

    @BindView(R.id.rdoGroup)
    RadioGroup rdoGroup;

    @BindView(R.id.txtSave)
    TextView txtSave;


    private EntangleManageDTO dto;
    private String filePath = "";
    //test
    public static List<ConstructionImageInfo> mListUrl;
    public List<ConstructionImageInfo> mListUrlUpload;
    private ImageDetailEntangleAdapter mAdapter;
    @BindView(R.id.prg_loading)
    CustomProgress progress;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entangle);
        ButterKnife.bind(this);
        progress.setLoading(true);
        if (getIntent().getExtras() != null) {
            dto = (EntangleManageDTO) getIntent().getSerializableExtra("entangleManageDTO");
        }
        txtHeader.setText(R.string.Detail);
        setUpView();
        initData();
        loadImageServer();
    }

    private void loadImageServer() {
        ApiManager.getInstance().loadImageEntangle(dto,
                EntangleManageDTORespone.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    EntangleManageDTORespone respone = EntangleManageDTORespone.class.cast(result);
                    if (respone.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        mListUrl.addAll(respone.getListImg());
                        mAdapter.notifyDataSetChanged();
                        progress.setLoading(false);
                    } else {
                        progress.setLoading(false);
                        Toast.makeText(EntangleCameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                progress.setLoading(false);
                Toast.makeText(EntangleCameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {

        String hangMuc = dto.getWorkItemName() != null ? dto.getWorkItemName() : "";
        txtHangMuc.setText(hangMuc);

        if (dto.getObstructedContent() != null) {
            edtEntangle.setText(dto.getObstructedContent());
        }
        if (dto.getConsCode() != null)
            txtConstructionCode.setText(dto.getConsCode());
        if (dto.getConsName() != null)
            txtConstructionName.setText(dto.getConsName());

        if (dto.getObstructedState() != null &&
                dto.getObstructedState().equalsIgnoreCase("0")) {
            //Chỉ xem
            txtXacNhanTitle.setVisibility(View.GONE);
            rdoGroup.setVisibility(View.GONE);
            edtEntangle.setEnabled(false);
            edtEntangle.setBackgroundResource(R.color.white);

            txtSave.setText("");
            txtSave.setEnabled(false);
            mBtnCamera.setVisibility(View.INVISIBLE);

        } else if (dto.getObstructedState() != null) {
            String state = dto.getObstructedState();
            if (state.equals("0")) {
                rad3.setChecked(true);
            }
            if (state.equals("1")) {
                rad1.setChecked(true);
            }
            if (state.equals("2")) {
                rad2.setChecked(true);
                rad1.setEnabled(false);
            }
        }

    }

    @OnClick(R.id.txtCancel)
    public void onCancel() {
        finish();
    }

    @OnClick(R.id.txtSave)
    public void onSave() {
        try {
            if (validateSave()) {
                progress.setLoading(true);

                if (rad1.isChecked()) {
                    dto.setObstructedState("1");
                }
                if (rad2.isChecked()) {
                    dto.setObstructedState("2");
                }
                if (rad3.isChecked()) {
                    dto.setObstructedState("0");
                }

                for (int i = 0; i < mListUrl.size(); i++) {
                    if (mListUrl.get(i).getStatus() == 0) {
                        mListUrlUpload.add(mListUrl.get(i));
                    }
                }

                dto.setObstructedContent(edtEntangle.getText().toString().trim());
                dto.setListImage(mListUrlUpload);
                ApiManager.getInstance().updateEntangle(dto, EntangleManageDTORespone.class, new IOnRequestListener() {
                    @Override
                    public <T> void onResponse(T result) {
                        try {
                            EntangleManageDTORespone respone = EntangleManageDTORespone.class.cast(result);
                            if (respone.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                                Toast.makeText(EntangleCameraActivity.this, getResources().getString(R.string.updated), Toast.LENGTH_SHORT).show();
                                App.getInstance().setNeedUpdateEntangle(true);
                                finish();
                            } else {
                                Toast.makeText(EntangleCameraActivity.this, getResources().getString(R.string.update_fail), Toast.LENGTH_SHORT).show();
                            }

                            progress.setLoading(false);
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(int statusCode) {
                        Toast.makeText(EntangleCameraActivity.this, getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                        progress.setLoading(false);
                    }
                });
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {

                case VConstant.REQUEST_CODE_CAMERA:
                    if (resultCode == Activity.RESULT_OK) {
                        filePath = mPhotoFile.getPath();
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String pictureName = "IMG_" + timeStamp + ".jpg";
                        Bitmap bitmap = ImageUtils.decodeBitmapFromFile(filePath, 200, 200);
                        //,mObjWork.getConstructionCode(),mObjWork.getWorkItemName()
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setUpView() {
        mListUrl = new ArrayList<>();
        mListUrlUpload = new ArrayList<>();
        boolean allowShowDelete = true;
        if (dto.getObstructedState() != null && dto.getObstructedState().equalsIgnoreCase("0")) {
            allowShowDelete = false;
        }
        mAdapter = new ImageDetailEntangleAdapter(mListUrl, this, allowShowDelete, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvCumbersome.setLayoutManager(linearLayoutManager);
        rcvCumbersome.setAdapter(mAdapter);
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

    private boolean validateSave() {
        boolean isSave = false;
        if (edtEntangle.getText().toString().trim() == null || edtEntangle.getText().toString().trim().length() == 0) {
            Toast.makeText(this, getString(R.string.please_enter_entangle), Toast.LENGTH_SHORT).show();
            isSave = false;
        } else
            isSave = true;

        return isSave;
    }
}
