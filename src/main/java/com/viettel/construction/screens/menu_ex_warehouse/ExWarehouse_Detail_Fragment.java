package com.viettel.construction.screens.menu_ex_warehouse;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseChartFragment;
import com.viettel.construction.appbase.ImageAppAdapter;
import com.viettel.construction.common.App;
import com.viettel.construction.common.Common;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.ListImage;
import com.viettel.construction.model.api.ConstructionImageInfo;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.StockTransResponse;
import com.viettel.construction.model.api.SynStockTransDTO;
import com.viettel.construction.model.api.SynStockTransDetailDTO;
import com.viettel.construction.model.api.SysUserRequest;
import com.viettel.construction.screens.custom.dialog.CustomProgress;
import com.viettel.construction.screens.custom.dialog.DialogCancelWithAvaiableReason;
import com.viettel.construction.screens.custom.dialog.DialogConfirm;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;
import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.util.ImageUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Chi tiết VTTB
 */

public class ExWarehouse_Detail_Fragment extends BaseChartFragment
        implements ExWarehouse_Detail_Adapter.OnClickDetailImplement
        , DialogConfirm.OnClickDialogForConfirm
        , DialogCancelWithAvaiableReason.OnClickDialogForCancel, ImageAppAdapter.OnClickDelete{

    private static final int REQUEST_CODE_NEED_FINISH_AFTER_DILIVERY = 1;

    @BindView(R.id.tv_title)
    TextView mTVTitle;

    @BindView(R.id.tv_delivery_bill)
    TextView mTVDeliveryBill;

    @BindView(R.id.tv_construction)
    TextView mTVConstruction;

    @BindView(R.id.tv_warehouse)
    TextView mTVWarehouse;

    @BindView(R.id.tv_bill_creator)
    TextView mTVBillCreator;

    @BindView(R.id.tv_create_date)
    TextView mTVCreateDate;

    @BindView(R.id.tv_contract)
    TextView tv_contract;

    @BindView(R.id.rcv_supplies)
    RecyclerView rcvSupplies;

    @BindView(R.id.ln_supplies_confirm)
    LinearLayout lnConfirm;

    @BindView(R.id.ln_supplies_cancel)
    LinearLayout lnCancel;

    @BindView(R.id.lnBanGiao)
    LinearLayout lnHandOver;

    @BindView(R.id.rl_wait_for_receive)
    LinearLayout rlWaitForReceive;

    @BindView(R.id.lnBanGiaoWithoutConfirm)
    LinearLayout lnBangiaoWithoutConfirm;

    @BindView(R.id.list_material_dialog)
    CustomProgress customProgress;
    @BindView(R.id.ln_ContractCode)
    LinearLayout ln_ContractCode;
    @BindView(R.id.txtConfirm)
    TextView txtConfirm;

    @BindView(R.id.ln_themanh)
    LinearLayout ln_themanh;
    @BindView(R.id.btn_camera)
    ImageView btn_camera;
    @BindView(R.id.rv_list_image_in_process)
    RecyclerView rv_list_image_in_process;
    @BindView(R.id.edt_ngaythucnhan)
    EditText edt_ngaythucnhan;
    @BindView(R.id.ll_ngaythucnhan)
    LinearLayout llNgayThucNhan;
    @BindView(R.id.ll_image_pxk)
    LinearLayout llImagePxk;


    private String filePath = "";
    public static List<ConstructionImageInfo> mListUrl;
    private ImageAppAdapter mAdapter;
    final Calendar myCalendar = Calendar.getInstance();

    private ExWarehouse_Detail_Adapter adapter;
    private List<SynStockTransDetailDTO> listData = new ArrayList<>();
    private DialogConfirm dialogConfirm;
    private DialogCancelWithAvaiableReason dialogCancel;
    private SynStockTransDTO synStockTransDTO;
    private boolean isConfirm = false;
    private final String TAG = "VTExWareDetail";

    private boolean isABill;

    public boolean isABill() {
        return isABill;
    }

    public void setABill(boolean ABill) {
        isABill = ABill;
    }

    // Neu xac nhan trong khi da ban giao truoc do, khoong hien ban giao sau khi xac nhan
    private boolean mConfirmAfterDiliver = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mListUrl = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_list_material, container, false);
        ButterKnife.bind(this, view);
        customProgress.setLoading(false);
        rlWaitForReceive.setVisibility(View.GONE);
        if (getArguments() != null && isConfirm == false) {
            synStockTransDTO = (SynStockTransDTO) getArguments().getSerializable(VConstant.BUNDLE_KEY_BILL_ENTITY);
            mTVDeliveryBill.setText(synStockTransDTO.getCode());
            mTVConstruction.setText(synStockTransDTO.getConsCode());
            mTVWarehouse.setText(synStockTransDTO.getSynStockName());
            mTVBillCreator.setText(synStockTransDTO.getSynCreatedByName());
            mTVCreateDate.setText(changeStringFormat(synStockTransDTO.getSynCreatedDate()));
            if (synStockTransDTO.getStockType().contains("A") && synStockTransDTO.getCntContractCode() != null) {
                ln_ContractCode.setVisibility(View.VISIBLE);
                tv_contract.setText(synStockTransDTO.getCntContractCode());
            } else {
                ln_ContractCode.setVisibility(View.GONE);
            }
            /*if(synStockTransDTO.getStockType().contains("A")) {
                txtConfirm.setText("Xác nhận BG");
            }else {
                txtConfirm.setText("Xác nhận");
            }*/
        }
        SysUserRequest sysUserRequest = VConstant.getUser();
        Long loginSysUserId = sysUserRequest.getSysUserId();
        Long receiverId;
        if (synStockTransDTO.getReceiverId() != null) {
            receiverId = synStockTransDTO.getReceiverId();
        } else {
            receiverId = 1L;
        }
        if(isABill){
            if (synStockTransDTO != null) {
                if ((synStockTransDTO.getLastShipperId() != null && synStockTransDTO.getConfirm().trim().equals("0") && receiverId == 1) ||
                        (synStockTransDTO.getLastShipperId().compareTo(receiverId) != 0 && receiverId.compareTo(loginSysUserId) == 0 && synStockTransDTO.getState().trim().equals("0"))
                        || (synStockTransDTO.state.trim().equals("2") && synStockTransDTO.confirm.trim().equals("0") && synStockTransDTO.lastShipperId.compareTo(loginSysUserId) == 0)
                        || (synStockTransDTO.confirm.trim().equals("1") && synStockTransDTO.state.trim().equals("0")&& receiverId.compareTo(loginSysUserId) == 0)) {
                    // có từ chối và xác nhận
                    lnHandOver.setVisibility(View.GONE);
                    rlWaitForReceive.setVisibility(View.VISIBLE);
                    btn_camera.setVisibility(View.VISIBLE);
                    openChooseDate();
                } else if ((synStockTransDTO.getLastShipperId() != null && synStockTransDTO.getConfirm().trim().equals("1") && receiverId == 1) ||
                        (receiverId != 1 && synStockTransDTO.getState().trim().equals("2") && synStockTransDTO.getLastShipperId().compareTo(loginSysUserId) == 0) ||
                        (synStockTransDTO.getLastShipperId().compareTo(receiverId) == 0 && synStockTransDTO.getState().trim().equals("1"))) {
                    // có bàn giao và quay lại
//                rlForTheRest.setVisibility(View.VISIBLE);
                    rlWaitForReceive.setVisibility(View.GONE);
                    lnHandOver.setVisibility(View.VISIBLE);
                    setDate();
                } else if ((synStockTransDTO.getLastShipperId().compareTo(loginSysUserId) == 0 && (synStockTransDTO.getLastShipperId().compareTo(loginSysUserId) == 0 && synStockTransDTO.getState().trim().equals("0") || synStockTransDTO.getConfirm().trim().equals("2"))
                        && receiverId != 1 && synStockTransDTO.getState().trim().equals("0") && receiverId.compareTo(loginSysUserId) != 0 && !synStockTransDTO.getConfirm().trim().equals("0")) ||
                        (synStockTransDTO.getLastShipperId() != null && synStockTransDTO.getConfirm().trim().equals("1") && receiverId.compareTo(loginSysUserId) == 0 && synStockTransDTO.getState().trim().equals("2")) ||
                        (synStockTransDTO.getLastShipperId().compareTo(loginSysUserId) == 0 && receiverId == 1 && synStockTransDTO.getConfirm().trim().equals("2"))
                        || (synStockTransDTO.state.trim().equals("2") && synStockTransDTO.confirm.trim().equals("0") && receiverId.compareTo(loginSysUserId) == 0)
                        || receiverId != 1) {
                    // quay lại only
//                rlForTheRest.setVisibility(View.VISIBLE);
                    rlWaitForReceive.setVisibility(View.GONE);
                    lnHandOver.setVisibility(View.GONE);
                    setDate();
                }
            }
        }else{
            lnBangiaoWithoutConfirm.setVisibility(View.GONE);
            llNgayThucNhan.setVisibility(View.GONE);
            llImagePxk.setVisibility(View.GONE);
            if (synStockTransDTO != null) {
                if ((synStockTransDTO.getLastShipperId() != null && synStockTransDTO.getConfirm().trim().equals("0")) ||
                        (synStockTransDTO.getLastShipperId().compareTo(receiverId) != 0 && receiverId.compareTo(loginSysUserId) == 0 && synStockTransDTO.getState().trim().equals("0"))) {
                    // có từ chối và xác nhận
                    lnHandOver.setVisibility(View.GONE);
                    rlWaitForReceive.setVisibility(View.VISIBLE);
                } else if ((synStockTransDTO.getLastShipperId() != null && synStockTransDTO.getConfirm().trim().equals("1") && receiverId == 1) ||
                        (receiverId != 1 && synStockTransDTO.getState().trim().equals("2") && synStockTransDTO.getLastShipperId().compareTo(loginSysUserId) == 0) ||
                        (synStockTransDTO.getLastShipperId().compareTo(receiverId) == 0 && synStockTransDTO.getState().trim().equals("1"))) {
                    // có bàn giao và quay lại
//                rlForTheRest.setVisibility(View.VISIBLE);
                    rlWaitForReceive.setVisibility(View.GONE);
                    lnHandOver.setVisibility(View.VISIBLE);
                } else if ((synStockTransDTO.getLastShipperId().compareTo(loginSysUserId) == 0 && (synStockTransDTO.getConfirm().trim().equals("1") || synStockTransDTO.getConfirm().trim().equals("2")) && receiverId != 1 && synStockTransDTO.getState().trim().equals("0")) ||
                        (synStockTransDTO.getLastShipperId() != null && synStockTransDTO.getConfirm().trim().equals("1") && receiverId.compareTo(loginSysUserId) == 0 && synStockTransDTO.getState().trim().equals("2")) ||
                        (synStockTransDTO.getLastShipperId().compareTo(loginSysUserId) == 0 && receiverId == 1 && synStockTransDTO.getConfirm().trim().equals("2"))) {
                    // quay lại only
//                rlForTheRest.setVisibility(View.VISIBLE);
                    rlWaitForReceive.setVisibility(View.GONE);
                    lnHandOver.setVisibility(View.GONE);
                }
            }
        }
        return view;
    }

    private void openChooseDate() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                synStockTransDTO.setRealConfirmTransDate(sdf.format(myCalendar.getTime()));
                updateLabel();
            }
        };
        edt_ngaythucnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void setDate(){
        edt_ngaythucnhan.setEnabled(false);
        edt_ngaythucnhan.setText(synStockTransDTO.getRealConfirmTransDate());
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edt_ngaythucnhan.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //init dialog
        dialogConfirm = new DialogConfirm(getActivity(), this);
        dialogCancel = new DialogCancelWithAvaiableReason(getActivity(), this);
        dialogConfirm.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogCancel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // setup data
        adapter = new ExWarehouse_Detail_Adapter(rcvSupplies, getActivity(), listData, this, synStockTransDTO, isABill);
        rcvSupplies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvSupplies.setAdapter(adapter);
        getDetailData();
    }

    private void setTitle() {
        Activity activity = getActivity();
        if (activity != null && isAdded()) {
            if (listData.size() != 0) {
                mTVTitle.setText(getActivity().getString(R.string.implement_with_index,
                        adapter.getItemCount() + ""));
            } else {
                mTVTitle.setText(getActivity().getString(R.string.implement_with_index, "0"));
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void getDetailData() {
        if (mListUrl.size() <= 0)
            mListUrl = new ArrayList<>();
        mAdapter = new ImageAppAdapter(4, mListUrl, this.getContext(), false, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_list_image_in_process.setLayoutManager(linearLayoutManager);
        rv_list_image_in_process.setHasFixedSize(true);
        rv_list_image_in_process.setAdapter(mAdapter);

        customProgress.setLoading(true);
        ApiManager.getInstance().getDetailSynStockTransDTO(synStockTransDTO, StockTransResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    StockTransResponse response = StockTransResponse.class.cast(result);
                    ResultInfo resultInfo = response.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {

                        listData = response.getLisSynStockTransDetail();
                        adapter.setListData(listData);
                        adapter.notifyDataSetChanged();

                        customProgress.setLoading(false);
                    } else {
                        customProgress.setLoading(false);
                        Toast.makeText(getContext(), resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    setTitle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                customProgress.setLoading(false);
                Toast.makeText(getActivity(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
        ApiManager.getInstance().getAttachment(synStockTransDTO, StockTransResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    StockTransResponse response = StockTransResponse.class.cast(result);
                    ResultInfo resultInfo = response.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        for (ListImage item:response.utilAttachDocumentDTOs
                             ) {
                            ConstructionImageInfo imageInfo = new ConstructionImageInfo();
                            imageInfo.setName(item.getName());
                            imageInfo.setBase64String(item.getBase64String());
                            mListUrl.add(imageInfo);
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        customProgress.setLoading(false);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                customProgress.setLoading(false);
                Toast.makeText(getActivity(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick(R.id.lnBanGiaoWithoutConfirm)
    public void banGiaoWithoutConfirm() {
        Intent intent = new Intent(getActivity(), ConfirmDeliveryBillCameraActivity.class);
        intent.putExtra(ConfirmDeliveryBillCameraActivity.KEY_BG_WITHOUT_CONFIRM, true);
        intent.putExtra("syn_stock_trans_dto_confirm", synStockTransDTO);
        startActivityForResult(intent, REQUEST_CODE_NEED_FINISH_AFTER_DILIVERY);
    }

    @OnClick(R.id.imgBack)
    public void onConfirmBack(){
        getFragmentManager().popBackStack();
    }

    @OnClick(R.id.ln_supplies_cancel)
    public void onClickCancel() {
        if(isABill){
            if(mListUrl.size() > 0 ){
                dialogCancel.show();
            } else Toast.makeText(getActivity(), getString(R.string.deficiency_image), Toast.LENGTH_SHORT).show();
        } else dialogCancel.show();

    }

    @OnClick(R.id.ln_supplies_confirm)
    public void onClickConfirm() {
        if(isABill){
            if(mListUrl.size() > 0){
                if(!edt_ngaythucnhan.getText().toString().isEmpty()){
                    if(isAmountOK() == true){
                        showDialog();
                    } else Toast.makeText(getActivity(), "Số lượng thực nhận không hợp lệ", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getActivity(), getString(R.string.real_date_confirm), Toast.LENGTH_SHORT).show();
            } else Toast.makeText(getActivity(), getString(R.string.deficiency_image), Toast.LENGTH_SHORT).show();
        } else {
            showDialog();
        }
    }

    private void showDialog() {
        dialogConfirm.show();
        dialogConfirm.txtCodeRequire.setText(synStockTransDTO.getConsCode());
        dialogConfirm.txtCodeBill.setText(synStockTransDTO.getCode() + "");
    }

    private boolean isAmountOK() {
        boolean isAmountOk = true;
        for (SynStockTransDetailDTO item:listData
             ) {
            if(item.getAmountReal() > 0 && item.getAmountReal() <= item.getAmountOrder()){
                isAmountOk = true;
            } else return false;
        }
        return true;
    }

    @OnClick(R.id.lnBanGiao)
    public void onClickHandover() {
        Intent intent = new Intent(getActivity(), ConfirmDeliveryBillCameraActivity.class);
        intent.putExtra(ConfirmDeliveryBillCameraActivity.KEY_BG_WITHOUT_CONFIRM, false);
        intent.putExtra("syn_stock_trans_dto_confirm", synStockTransDTO);
        startActivityForResult(intent, REQUEST_CODE_NEED_FINISH_AFTER_DILIVERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == VConstant.REQUEST_CODE_CAMERA) {
                filePath = mPhotoFile.getPath();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String pictureName = "IMG_" + timeStamp + ".jpg";
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;

                Bitmap bitmap = ImageUtils.decodeBitmapFromFile(filePath, width / 3, height / 3);
                //Bitmap bitmap = ImageUtils.decodeBitmapFromFile(filePath, 200, 200);
                String nameCustomer = synStockTransDTO.getLastShipperId() + "-" + synStockTransDTO.getCreatedBy();
                Bitmap newBitmap = ImageUtils.drawTextOnImage(bitmap, latitude, longitude, synStockTransDTO.getCode(), "");
                ConstructionImageInfo imageInfo = new ConstructionImageInfo();
                imageInfo.setStatus(0);
                Log.d(TAG, "base64 : " + StringUtil.getStringImage(newBitmap));
                String bitmapStr = StringUtil.getStringImage(newBitmap).replaceAll("\\s","");
                imageInfo.setBase64String(bitmapStr);
                imageInfo.setName(pictureName);
                imageInfo.setLatitude(latitude);
                imageInfo.setLongtitude(longitude);
                imageInfo.setType(VConstant.TYPE_IMG_PXK);
                mListUrl.add(0, imageInfo);
                Log.d(TAG, "REQUEST_CODE_CAMERA - name : " + pictureName + " - size : " + mListUrl.size());
                mAdapter.notifyItemInserted(0);
                if (mListUrl.size() == 0) {
                    rv_list_image_in_process.setVisibility(View.GONE);
                } else {
                    rv_list_image_in_process.setVisibility(View.VISIBLE);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        try {
            super.onResume();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClickDetails(SynStockTransDetailDTO data) {
        Intent intent = new Intent(getActivity(), InventoryDetailActivity.class);
        intent.putExtra(VConstant.BUNDLE_KEY_MATERIAL_ENTITY, data);
        intent.putExtra(VConstant.BUNDLE_KEY_BILL_ENTITY, synStockTransDTO);
        startActivity(intent);
    }

    /***
     * Từ chối
     * @param s
     */
    @Override
    public void onClickConfirmOfCancel(String s) {
        dialogCancel.dismiss();
        customProgress.setLoading(true);
        SysUserRequest sysUserReceiver = new SysUserRequest();
        synStockTransDTO.setConfirmDescription(s);
        synStockTransDTO.setFilePaths(mListUrl);
        ApiManager.getInstance().updateDeliveryWithoutConfirm(0, synStockTransDTO, sysUserReceiver, StockTransResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    StockTransResponse stockTransResponse = StockTransResponse.class.cast(result);
                    ResultInfo resultInfo = stockTransResponse.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        App.getInstance().setNeedUpdateAfterRejectedBill(true);
                        customProgress.setLoading(false);
                        Toast.makeText(getContext(), resultInfo.getMessage(), Toast.LENGTH_SHORT).show();

                        //Send broadcast to reload
                        if (getActivity() != null)
                            getActivity().sendBroadcast(new Intent(ParramConstant.ExWarehouseReload));
                        onConfirmBack();
                    } else {
                        Toast.makeText(getContext(), resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                        customProgress.setLoading(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                customProgress.setLoading(false);
                Toast.makeText(getActivity(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Xác nhận
     */
    @Override
    public void onClickConfirmOfConfirm() {
        dialogConfirm.dismiss();
        Log.d(TAG, "onClickConfirmOfConfirm");
        customProgress.setLoading(true);
        SysUserRequest sysUserReceiver = new SysUserRequest();
        synStockTransDTO.setFilePaths(mListUrl);
        synStockTransDTO.setListSynStockTransDetailDto(listData);
        ApiManager.getInstance().updateDeliveryWithoutConfirm(1, synStockTransDTO, sysUserReceiver, StockTransResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    Log.d(TAG, "onClickConfirmOfConfirm - onResponse");
                    StockTransResponse stockTransResponse = StockTransResponse.class.cast(result);
                    ResultInfo resultInfo = stockTransResponse.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        if (!mConfirmAfterDiliver) {
                            //Sau khi xac nhận thành công, update luôn confirm = 1: trạng thái đã xác nhận
                            synStockTransDTO.setConfirm("1");

                            Log.d(TAG, "onClickConfirmOfConfirm - OK");
                            App.getInstance().setNeedUpdateAfterConfirmBill(true);
                            Toast.makeText(getActivity(), getActivity().getString(R.string.confirm_success), Toast.LENGTH_SHORT).show();
                            rlWaitForReceive.setVisibility(View.GONE);
                            lnHandOver.setVisibility(View.VISIBLE);
                            isConfirm = true;
                            customProgress.setLoading(false);
                            recallApiToGetUpdateObject();
                            //Send broadcast to reload
                            if (getActivity() != null){
                                getActivity().sendBroadcast(new Intent(ParramConstant.ExWarehouseReload));
                                adapter.notifyDataSetChanged();

                            }
                        } else {
                            if (getActivity() != null){
                                getActivity().sendBroadcast(new Intent(ParramConstant.ExWarehouseReload));
                                adapter.notifyDataSetChanged();
                            }
                        }


                    } else {
                        Log.d(TAG, "onClickConfirmOfConfirm - Not OK");
                        customProgress.setLoading(false);
                        Toast.makeText(getContext(), resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.d(TAG, "onClickConfirmOfConfirm - Exception");
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                Log.d(TAG, "onClickConfirmOfConfirm - onError");
                customProgress.setLoading(false);
                Common.showToastError(getContext());
            }
        });
    }

    public void recallApiToGetUpdateObject() {
        customProgress.setLoading(true);
        ApiManager.getInstance().getListSynStockTransDTO(StockTransResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    Log.d(TAG, "recallApiToGetUpdateObject - onResponse");
                    StockTransResponse stockTransResponse = StockTransResponse.class.cast(result);
                    ResultInfo resultInfo = stockTransResponse.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        Log.d(TAG, "recallApiToGetUpdateObject - OK");
                        String type = synStockTransDTO.getStockType().trim();
                        Long id = synStockTransDTO.getSynStockTransId();
                        if (stockTransResponse.getLstSynStockTransDto().size() != 0) {
                            List<SynStockTransDTO> list = stockTransResponse.getLstSynStockTransDto();
                            for (SynStockTransDTO dto : list) {
                                String type1 = dto.getStockType().trim();
                                Long id1 = dto.getSynStockTransId();
                                if (type1.equals(type) && id1.compareTo(id) == 0) {
                                    synStockTransDTO = dto;
                                    break;
                                }
                            }
                        }
                        customProgress.setLoading(false);
//                        Toast.makeText(getActivity(), resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "recallApiToGetUpdateObject -NOT OK");
                        customProgress.setLoading(false);
                        Toast.makeText(getActivity(), resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.d(TAG, "recallApiToGetUpdateObject - Exception");
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
                Log.d(TAG, "recallApiToGetUpdateObject - onError");
                customProgress.setLoading(false);
                Toast.makeText(getActivity(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDelete(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setIcon(R.drawable.ic_delete_forever_red_24dp);
        builder.setTitle("Xóa ảnh");
        builder.setMessage("Bạn có muốn xóa ảnh ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Xóa", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            mListUrl.get(pos).setStatus(-1);
//            if (mViewModel.mListUrl.get(pos).getUtilAttachDocumentId() > 0)
//                mViewModel.mListUrlUpload.add(mViewModel.mListUrl.get(pos));
            mListUrl.remove(pos);
            mAdapter.notifyItemRemoved(pos);
            if (mListUrl.size() == 0) {
                rv_list_image_in_process.setVisibility(View.GONE);
            } else {
                rv_list_image_in_process.setVisibility(View.VISIBLE);
            }
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
