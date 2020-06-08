package com.viettel.construction.screens.menu_return_vttb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.common.App;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.api.ConstructionMerchandiseDTORequest;
import com.viettel.construction.model.api.ConstructionMerchandiseDTOResponse;
import com.viettel.construction.model.api.ConstructionMerchandiseDetailDTO;
import com.viettel.construction.model.api.ConstructionMerchandiseDetailVTDTO;
import com.viettel.construction.model.api.SysUserRequest;

import com.viettel.construction.screens.atemp.adapter.ImageRefundAdapter;
import com.viettel.construction.screens.atemp.adapter.TabPagerAdapter;
import com.viettel.construction.screens.custom.dialog.CustomProgress;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.util.ImageUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * Hiển thị chi tiet các tab danh sách các thiết bị hoàn trả gồm thiết bị và vật tư
 */

public class RefundItemLevel3CameraActivity extends BaseCameraActivity implements ImageRefundAdapter.OnClickDelete {

    @BindView(R.id.btn_camera)
    ImageView mBtnCamera;

    @BindView(R.id.txt_construction_code)
    TextView txtConstructionCode;
    @BindView(R.id.txt_construction_name)
    TextView txtConstructionName;

    @BindView(R.id.rcv_image)
    RecyclerView rcvImage;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.prg_loading)
    CustomProgress progress;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.txtFooter)
    TextView txtFooter;

    private TabPagerAdapter adapter;
    private String filePath = "";
    public static List<ConstructionImageInfo> mListUrl;
    public List<ConstructionImageInfo> mListUrlUpload;
    private List<ConstructionMerchandiseDetailVTDTO> list1;
    private List<ConstructionMerchandiseDetailVTDTO> list2;
    private TabSuppliesRefundFragment f1;
    private TabDeviceRefundFragment f2;
    public ConstructionMerchandiseDetailDTO constructionMerchandiseDetailDTO;

    private ImageRefundAdapter imageRefundAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_refund_lever3);
        ButterKnife.bind(this);
        progress.setLoading(true);
        txtHeader.setText(R.string.detail);
        if (getIntent() != null) {
            constructionMerchandiseDetailDTO = (ConstructionMerchandiseDetailDTO) getIntent().getExtras().getSerializable("constructionMerchandiseDetailDTO");
            if (constructionMerchandiseDetailDTO.getConstructionCode() != null)
                txtConstructionCode.setText("Mã công trình: " + constructionMerchandiseDetailDTO.getConstructionCode());
            if (constructionMerchandiseDetailDTO.getWorkItemName() != null)
                txtConstructionName.setText("Hạng mục: " + constructionMerchandiseDetailDTO.getWorkItemName());
        }

        if (constructionMerchandiseDetailDTO.getStatusComplete() != null) {
            if (constructionMerchandiseDetailDTO.getStatusComplete().equals("1")
                    && constructionMerchandiseDetailDTO.getConstructionIsReturn() == null) {
                txtFooter.setText(R.string.DelFunding);
                mBtnCamera.setVisibility(View.INVISIBLE);
            } else {
                txtFooter.setText(R.string.Funding);
                mBtnCamera.setVisibility(View.VISIBLE);
            }
        }

        setupViewPager(viewPager, tabLayout);
        initRcvImage();
        loadImageServer();
    }


    private void loadImageServer() {
        final ConstructionMerchandiseDTORequest request = new ConstructionMerchandiseDTORequest();
        SysUserRequest sysUserRequest = VConstant.getUser();
        request.setSysUserRequest(sysUserRequest);
        request.setConstructionMerchandiseDetailDTO(constructionMerchandiseDetailDTO);

        ApiManager.getInstance().loadImageRefund(request, ConstructionMerchandiseDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ConstructionMerchandiseDTOResponse respone = ConstructionMerchandiseDTOResponse.class.cast(result);
                    if (respone.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        mListUrl.addAll(respone.getListImage());
                        imageRefundAdapter.notifyDataSetChanged();
                        progress.setLoading(false);
                    } else {
                        progress.setLoading(false);
                        Toast.makeText(RefundItemLevel3CameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                progress.setLoading(false);
                Toast.makeText(RefundItemLevel3CameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setupViewPager(ViewPager viewPager, TabLayout tabLayout) {
        adapter = new TabPagerAdapter(getSupportFragmentManager(), this, tabLayout);
        f1 = new TabSuppliesRefundFragment();
        f2 = new TabDeviceRefundFragment();
        adapter.addFragment(f1, "Vật tư");
        adapter.addFragment(f2, "Thiết bị");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void initRcvImage() {
        mListUrl = new ArrayList<>();
        mListUrlUpload = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvImage.setLayoutManager(linearLayoutManager);
        imageRefundAdapter = new ImageRefundAdapter(mListUrl, this, true, this);
        rcvImage.setAdapter(imageRefundAdapter);
    }


    @OnClick(R.id.imgBack)
    public void onCancel() {
        finish();
    }


    private void onDeleteRefund() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Xóa hoàn trả");
            builder.setMessage("Bạn có muốn xóa hoàn trả?");
            builder.setCancelable(false);
            builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    SysUserRequest sysUserRequest = VConstant.getUser();
                    ConstructionMerchandiseDTORequest request = new ConstructionMerchandiseDTORequest();
                    request.setSysUserRequest(sysUserRequest);
                    request.setConstructionMerchandiseDetailDTO(constructionMerchandiseDetailDTO);

                    ApiManager.getInstance().deleteRefund(request, ConstructionMerchandiseDTOResponse.class, new IOnRequestListener() {
                        @Override
                        public <T> void onResponse(T result) {
                            try {
                                ConstructionMerchandiseDTOResponse response = ConstructionMerchandiseDTOResponse.class.cast(result);
                                if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                                    App.getInstance().setNeedUpdateRefund(true);
                                    Toast.makeText(RefundItemLevel3CameraActivity.this, getString(R.string.deleted), Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(RefundItemLevel3CameraActivity.this, getString(R.string.delete_fail), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(int statusCode) {
                            Toast.makeText(RefundItemLevel3CameraActivity.this, getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                        }
                    });

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
                        Bitmap newBitmap = ImageUtils.drawTextOnImage(bitmap, latitude, longitude);
                        ConstructionImageInfo imageInfo = new ConstructionImageInfo();
                        imageInfo.setStatus(0);
                        imageInfo.setBase64String(StringUtil.getStringImage(newBitmap));
                        imageInfo.setImageName(pictureName);
                        imageInfo.setLatitude(latitude);
                        imageInfo.setLongtitude(longitude);
                        mListUrl.add(0, imageInfo);
                        imageRefundAdapter.notifyItemInserted(0);
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        /** Picture wasn't taken*/
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.txtFooter)
    public void fundingAction() {
        if (constructionMerchandiseDetailDTO.getStatusComplete() != null) {
            if (constructionMerchandiseDetailDTO.getStatusComplete().equals("1")
                    && constructionMerchandiseDetailDTO.getConstructionIsReturn() == null) {
                onDeleteRefund();

            } else {
                onSaveFunding();
            }
        }
    }


    private void onSaveFunding() {
        try {
            list1 = new ArrayList<>();
            list2 = new ArrayList<>();

            list1 = f1.list;
            list2 = f2.list;

            if (checkList(list1)) {
                if (checkListImage())
                    saveRefund();
                else {
                    Toast.makeText(this, getString(R.string.please_take_image), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, getString(R.string.please_update), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void saveRefund() {
        SysUserRequest sysUserRequest = VConstant.getUser();
        final ConstructionMerchandiseDTORequest request = new ConstructionMerchandiseDTORequest();
        request.setListDSVT(list1);
        request.setListDSTB(list2);
        request.setSysUserRequest(sysUserRequest);
        request.setConstructionMerchandiseDetailDTO(constructionMerchandiseDetailDTO);
        request.setListImage(mListUrlUpload);

        ApiManager.getInstance().updateRefund(request, ConstructionMerchandiseDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    ConstructionMerchandiseDTOResponse response = ConstructionMerchandiseDTOResponse.class.cast(result);
                    if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        App.getInstance().setNeedUpdateRefund(true);
                        Toast.makeText(RefundItemLevel3CameraActivity.this, getString(R.string.updated), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RefundItemLevel3CameraActivity.this, getString(R.string.update_fail), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(RefundItemLevel3CameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean checkListImage() {
        boolean isCheck = false;
        for (int i = 0; i < mListUrl.size(); i++) {
            if (mListUrl.get(i).getStatus() == 0) {
                isCheck = !isCheck;
                mListUrlUpload.add(mListUrl.get(i));
            }
        }
        return isCheck;
    }

    private boolean checkList(List<ConstructionMerchandiseDetailVTDTO> list) {
        boolean isCheck = true;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getNumberHoanTra() > list.get(i).getQuantity())
                isCheck = !isCheck;
        }
        return isCheck;
    }

    @Override
    public void onDelete(final int pos) {

        if (constructionMerchandiseDetailDTO.getStatusComplete() != null) {
            if (constructionMerchandiseDetailDTO.getStatusComplete().equals("1")) {
                Toast.makeText(this, getString(R.string.dont_delete_image_refund), Toast.LENGTH_SHORT).show();
            } else {
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
                        imageRefundAdapter.notifyItemRemoved(pos);
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


    }
}
