package com.viettel.construction.screens.menu_acceptance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import com.google.gson.Gson;
import com.viettel.construction.appbase.BaseCameraActivity;
import com.viettel.construction.screens.atemp.adapter.ImageAcceptanceAdapter;
import com.viettel.construction.screens.atemp.adapter.TabPagerAdapter;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.common.App;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailDTO;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailVTADTO;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailVTBDTO;
import com.viettel.construction.model.api.ConstructionAcceptanceDTORequest;
import com.viettel.construction.model.api.ConstructionAcceptanceDTOResponse;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.api.SysUserRequest;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.util.ImageUtils;
import com.viettel.construction.screens.custom.dialog.CustomProgress;

/***
 * Màn hình hiển thị chi tiết hạng mục nghiệm thu
 */

public class AcceptanceLevel3CameraActivity extends BaseCameraActivity implements ImageAcceptanceAdapter.OnClickDelete {

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
    private ImageAcceptanceAdapter imageAcceptanceAdapter;
    public ConstructionAcceptanceCertDetailDTO data;
    public List<ConstructionImageInfo> mListUrlUpload;
    private List<ConstructionAcceptanceCertDetailVTADTO> list1;
    private List<ConstructionAcceptanceCertDetailVTADTO> list2;
//    private List<ConstructionAcceptanceCertDetailVTBDTO> list3;
//    private List<ConstructionAcceptanceCertDetailVTBDTO> list4;

    private SuppliesAChartFragment f1;
    private DeviceAChartFragment f2;
//    private SuppliesBChartFragment f3;
//    private DeviceBChartFragment f4;
//    private SuppliesDTChartFragment f5;
//    private DeviceDTChartFragment f6;

    private final String TAG = "VTAcceptanceLevel3";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance_level_3);
        ButterKnife.bind(this);
        txtHeader.setText(R.string.detail);

        progress.setLoading(true);

        if (getIntent() != null) {
            data = (ConstructionAcceptanceCertDetailDTO) getIntent().getExtras().getSerializable("data");
            if (data.getConstructionCode() != null)
                txtConstructionCode.setText("Mã công trình: " + data.getConstructionCode());
            if (data.getWorkItemName() != null)
                txtConstructionName.setText("Hạng mục: " + data.getWorkItemName());
        }

        if (data.getStatusAcceptance() != null) {
            if (data.getStatusAcceptance().equals("1")) {
                txtFooter.setText(R.string.DelAcceptance);
                mBtnCamera.setVisibility(View.INVISIBLE);
                if (data.getCountWorkItemComplete() > 0)
                    txtFooter.setVisibility(View.GONE);
                else
                    txtFooter.setVisibility(View.VISIBLE);
            } else {
                txtFooter.setText(R.string.Acceptance);
                mBtnCamera.setVisibility(View.VISIBLE);
            }
        }

        setupViewPager(viewPager, tabLayout);
        initRcvImage();
        loadImageServer();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setupViewPager(ViewPager viewPager, TabLayout tabLayout) {
        // add app:tabMode="scrollable"
        // to check number tablayout
        viewPager.setOffscreenPageLimit(2);
        adapter = new TabPagerAdapter(getSupportFragmentManager(), this, tabLayout);
        f1 = new SuppliesAChartFragment();
        f2 = new DeviceAChartFragment();
//        f3 = new SuppliesBChartFragment();
//        f4 = new DeviceBChartFragment();
//        f5 = new SuppliesDTChartFragment();
//        f6 = new DeviceDTChartFragment();
        adapter.addFragment(f1, "Vật tư A");
        adapter.addFragment(f2, "Thiết bị A");
//        adapter.addFragment(f3, "Vật tư B");
//        adapter.addFragment(f4, "Thiết bị B");
//        adapter.addFragment(f5, "Vật tư ĐT");
//        adapter.addFragment(f6, "Thiết bị ĐT");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

    public void initRcvImage() {
        mListUrl = new ArrayList<>();
        mListUrlUpload = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvImage.setLayoutManager(linearLayoutManager);
        imageAcceptanceAdapter = new ImageAcceptanceAdapter(mListUrl, this, true, this);
        rcvImage.setAdapter(imageAcceptanceAdapter);
    }

    @OnClick(R.id.imgBack)
    public void onCancel() {
        finish();
    }

    @OnClick(R.id.txtFooter)
    public void onAcceptanceAction() {
        if (data.getStatusAcceptance() != null) {
            if (data.getStatusAcceptance().equals("1")) {
                onDeleteAcceptance();
            } else {
                onSaveAcceptance();
            }
        }
    }


    private void onSaveAcceptance() {

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
//        list3 = new ArrayList<>();
//        list4 = new ArrayList<>();

        list1 = f1.list;
        list2 = f2.list;
//
// list4 = f3.list;
//        list4 = f4.list;

//        if (checkListA(list1) || checkListB(list3)) {
//            if (checkListImage())
//                saveAcceptance();
//            else {
//                Toast.makeText(this, getString(R.string.please_take_image), Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(this, getString(R.string.please_update), Toast.LENGTH_SHORT).show();
//        }

        if (checkListA(list1)) {
            if (checkListImage())
                saveAcceptance();
            else {
                Toast.makeText(this, getString(R.string.please_take_image), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.please_update), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadImageServer() {
        ConstructionAcceptanceDTORequest request = new ConstructionAcceptanceDTORequest();
        SysUserRequest sysUserRequest = VConstant.getUser();
        request.setSysUserRequest(sysUserRequest);
        request.setConstructionAcceptanceCertDetailDTO(data);

        ApiManager.getInstance().loadImageAcceptance(request, ConstructionAcceptanceDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                ConstructionAcceptanceDTOResponse respone = ConstructionAcceptanceDTOResponse.class.cast(result);
                if (respone.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                    mListUrl.addAll(respone.getListImage());

                    imageAcceptanceAdapter.notifyDataSetChanged();
                    progress.setLoading(false);
                } else {
                    progress.setLoading(false);
                    Toast.makeText(AcceptanceLevel3CameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int statusCode) {
                progress.setLoading(false);
                Toast.makeText(AcceptanceLevel3CameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveAcceptance() {
        SysUserRequest sysUserRequest = VConstant.getUser();
        final ConstructionAcceptanceDTORequest request = new ConstructionAcceptanceDTORequest();
        request.setListDSVTA(list1);
        request.setListDSTBA(list2);
//        request.setListDSVTB(list3);
//        request.setListDSTBB(list4);
        request.setSysUserRequest(sysUserRequest);
        request.setConstructionAcceptanceCertDetailDTO(data);
        request.setListImage(mListUrlUpload);

        //THANGTV24 add code to test update
        final ConstructionAcceptanceDTORequest request1 = new ConstructionAcceptanceDTORequest();
        request1.setListDSVTA(list1);
        request1.setListDSTBA(list2);
        Log.d(TAG, "convertModelRequestUpdateAcceptance - list1 : " + list1.size() + " - list2 : " + list2.size());
//        request1.setListDSVTB(list3);
//        request1.setListDSTBB(list4);
        request1.setSysUserRequest(sysUserRequest);
        request1.setConstructionAcceptanceCertDetailDTO(data);

        Gson gson = new Gson();
        String json = gson.toJson(request1);
        Log.d(TAG, "convertModelRequestUpdateAcceptance - json : " + json);
        //add code to test update

        ApiManager.getInstance().updateAcceptance(request, ConstructionAcceptanceDTOResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                ConstructionAcceptanceDTOResponse response = ConstructionAcceptanceDTOResponse.class.cast(result);
                if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                    App.getInstance().setNeedUpdateAcceptance(true);
                    Toast.makeText(AcceptanceLevel3CameraActivity.this, getString(R.string.updated), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AcceptanceLevel3CameraActivity.this, getString(R.string.update_fail), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(AcceptanceLevel3CameraActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void onDeleteAcceptance() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa nghiệm thu");
        builder.setMessage("Bạn có muốn xóa nghiệm thu?");
        builder.setCancelable(false);
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                SysUserRequest sysUserRequest = VConstant.getUser();
                ConstructionAcceptanceDTORequest request = new ConstructionAcceptanceDTORequest();
                request.setSysUserRequest(sysUserRequest);
                request.setConstructionAcceptanceCertDetailDTO(data);

                ApiManager.getInstance().deleteAcceptance(request, ConstructionAcceptanceDTOResponse.class, new IOnRequestListener() {
                    @Override
                    public <T> void onResponse(T result) {
                        ConstructionAcceptanceDTOResponse response = ConstructionAcceptanceDTOResponse.class.cast(result);
                        if (response.getResultInfo().getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                            App.getInstance().setNeedUpdateAcceptance(true);
                            Toast.makeText(AcceptanceLevel3CameraActivity.this, getString(R.string.deleted), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AcceptanceLevel3CameraActivity.this, getString(R.string.delete_fail), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(int statusCode) {
                        Toast.makeText(AcceptanceLevel3CameraActivity.this, getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
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
                        //                    ,mObjWork.getConstructionCode(),mObjWork.getWorkItemName()
                        ConstructionImageInfo imageInfo = new ConstructionImageInfo();
                        imageInfo.setStatus(0);
                        imageInfo.setBase64String(StringUtil.getStringImage(newBitmap));
                        imageInfo.setImageName(pictureName);
                        imageInfo.setLatitude(latitude);
                        imageInfo.setLongtitude(longitude);
                        mListUrl.add(0, imageInfo);
                        imageAcceptanceAdapter.notifyItemInserted(0);
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        /** Picture wasn't taken*/
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDelete(final int pos) {

        if (data.getStatusAcceptance() != null) {
            if (data.getStatusAcceptance().equals("1")) {
                Toast.makeText(this, getString(R.string.dont_delete_image_acceptance), Toast.LENGTH_SHORT).show();
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
                        imageAcceptanceAdapter.notifyItemRemoved(pos);
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

    private boolean checkListA(List<ConstructionAcceptanceCertDetailVTADTO> list) {
        boolean isCheck = true;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getNumberNghiemthu() > list.get(i).getQuantity())
                isCheck = !isCheck;
        }
        return isCheck;
    }

    private boolean checkListB(List<ConstructionAcceptanceCertDetailVTBDTO> list) {
        boolean isCheck = true;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getNumberSuDung() > list.get(i).getQuantity())
                isCheck = !isCheck;
        }
        return isCheck;
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
}
