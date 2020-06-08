package com.viettel.construction.screens.menu_bgmb;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.construction.R;
import com.viettel.construction.appbase.BaseChartFragment;
import com.viettel.construction.common.App;
import com.viettel.construction.common.Common;
import com.viettel.construction.common.ParramConstant;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.StockTransResponse;
import com.viettel.construction.model.api.SynStockTransDTO;
import com.viettel.construction.model.api.SynStockTransDetailDTO;
import com.viettel.construction.model.api.SysUserRequest;
import com.viettel.construction.screens.custom.dialog.CustomProgress;
import com.viettel.construction.screens.custom.dialog.DialogCancel;
import com.viettel.construction.screens.custom.dialog.DialogConfirm;
import com.viettel.construction.screens.menu_ex_warehouse.ConfirmDeliveryBillCameraActivity;
import com.viettel.construction.screens.menu_ex_warehouse.ExWarehouse_Detail_Adapter;
import com.viettel.construction.screens.menu_ex_warehouse.InventoryDetailActivity;
import com.viettel.construction.server.api.ApiManager;
import com.viettel.construction.server.service.IOnRequestListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Chi tiết VTTB
 */

public class Bgmb_Detail_Fragment extends BaseChartFragment
        implements ExWarehouse_Detail_Adapter.OnClickDetailImplement
        , DialogConfirm.OnClickDialogForConfirm
        , DialogCancel.OnClickDialogForCancel {

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

    @BindView(R.id.rcv_supplies)
    RecyclerView rcvSupplies;
    @BindView(R.id.ln_supplies_confirm)
    LinearLayout lnConfirm;
    @BindView(R.id.ln_supplies_cancel)
    LinearLayout lnCancel;

    @BindView(R.id.lnBanGiao)
    LinearLayout lnHandOver;


    @BindView(R.id.rl_wait_for_receive)
    View rlWaitForReceive;

    @BindView(R.id.list_material_dialog)
    CustomProgress customProgress;
    private ExWarehouse_Detail_Adapter adapter;
    private List<SynStockTransDetailDTO> listData = new ArrayList<>();
    private DialogConfirm dialogConfirm;
    private DialogCancel dialogCancel;
    private SynStockTransDTO synStockTransDTO;
    private boolean isConfirm = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        }
        SysUserRequest sysUserRequest = VConstant.getUser();
        Long loginSysUserId = sysUserRequest.getSysUserId();
        Long receiverId;
        if (synStockTransDTO.getReceiverId() != null) {
            receiverId = synStockTransDTO.getReceiverId();
        } else {
            receiverId = 1L;
        }
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


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //init dialog
        dialogConfirm = new DialogConfirm(getActivity(), this);
        dialogCancel = new DialogCancel(getActivity(), this);
        dialogConfirm.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogCancel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // setup data
        adapter = new ExWarehouse_Detail_Adapter(rcvSupplies, getActivity(), listData, this);
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

    private void getDetailData() {
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
    }


    @OnClick(R.id.imgBack)
    public void onBack() {
        getFragmentManager().popBackStack();
    }

    @OnClick(R.id.ln_supplies_cancel)
    public void onClickCancel() {
        dialogCancel.show();
    }

    @OnClick(R.id.ln_supplies_confirm)
    public void onClickConfirm() {
        dialogConfirm.show();
        dialogConfirm.txtCodeRequire.setText(synStockTransDTO.getConsCode());
        dialogConfirm.txtCodeBill.setText(synStockTransDTO.getCode() + "");
    }

    @OnClick(R.id.lnBanGiao)
    public void onClickHandover() {

        Intent intent = new Intent(getActivity(), ConfirmDeliveryBillCameraActivity.class);
        intent.putExtra("syn_stock_trans_dto_confirm", synStockTransDTO);
        startActivity(intent);

    }

    @Override
    public void onResume() {
        try {
            super.onResume();
            if (App.getInstance().isNeedUpdateAfterConfirmBill()){
                App.getInstance().setNeedUpdateAfterConfirmBill(false);
                lnHandOver.setVisibility(View.INVISIBLE);
            }
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
        ApiManager.getInstance().updateDelivery(0, synStockTransDTO, sysUserReceiver, StockTransResponse.class, new IOnRequestListener() {
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
                        if (getActivity()!=null)
                            getActivity().sendBroadcast(new Intent(ParramConstant.ExWarehouseReload));
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
        customProgress.setLoading(true);
        SysUserRequest sysUserReceiver = new SysUserRequest();
        ApiManager.getInstance().updateDelivery(1, synStockTransDTO, sysUserReceiver, StockTransResponse.class, new IOnRequestListener() {
            @Override
            public <T> void onResponse(T result) {
                try {
                    StockTransResponse stockTransResponse = StockTransResponse.class.cast(result);
                    ResultInfo resultInfo = stockTransResponse.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
                        App.getInstance().setNeedUpdateAfterConfirmBill(true);
                        Toast.makeText(getActivity(), getActivity().getString(R.string.confirm_success), Toast.LENGTH_SHORT).show();
                        rlWaitForReceive.setVisibility(View.GONE);
                        lnHandOver.setVisibility(View.VISIBLE);
                        isConfirm = true;
                        customProgress.setLoading(false);
                        recallApiToGetUpdateObject();

                        //Send broadcast to reload
                        if (getActivity()!=null)
                        getActivity().sendBroadcast(new Intent(ParramConstant.ExWarehouseReload));

                    } else {
                        customProgress.setLoading(false);
                        Toast.makeText(getContext(), resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int statusCode) {
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
                    StockTransResponse stockTransResponse = StockTransResponse.class.cast(result);
                    ResultInfo resultInfo = stockTransResponse.getResultInfo();
                    if (resultInfo.getStatus().equals(VConstant.RESULT_STATUS_OK)) {
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
                        customProgress.setLoading(false);
                        Toast.makeText(getActivity(), resultInfo.getMessage(), Toast.LENGTH_SHORT).show();
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

}
