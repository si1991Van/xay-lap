package com.viettel.construction.screens.menu_bgmb;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ViewModelFragmentListBase;
import com.viettel.construction.model.api.ConstructionBGMBDTO;
import com.viettel.construction.model.api.SynStockTransDTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class TabPageBgmbConstruction_ItemAdapter extends AdapterFragmentListBase<ConstructionBGMBDTO, TabPageBgmbConstruction_ItemAdapter.BgmbConstructionVM> {

    private final String TAG = "VTTabPageBgmbAdapter";

    public TabPageBgmbConstruction_ItemAdapter(Context context, List<ConstructionBGMBDTO> listData) {
        super(context, listData);
    }

    @Override
    public BgmbConstructionVM onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bgmb_construction, parent, false);
        return new BgmbConstructionVM(view);
    }

    @Override
    public void onBindViewHolder(BgmbConstructionVM holder, int position) {
        int index = position + 1;
        ConstructionBGMBDTO constBGMB = getListData().get(position);
        holder.txt_title_congtrinh.setText(index + ". Mã CT");

        Log.d(TAG, "onBindViewHolder -  index : " + index + " - getReceivedStatus : " + constBGMB.getReceivedStatus());
        // set text cho ma cong trinh

        if (constBGMB.getConstructionCode() != null) {
            holder.txtConstructionCode.setText(constBGMB.getConstructionCode());
            Log.d(TAG, "onBindViewHolder -  getConstructionCode : " + constBGMB.getConstructionCode());
        }

        // set trang thai

        switch (constBGMB.getReceivedStatus() + "") {
            case "1":
                holder.txtStatus.setText("Chưa nhận");
                holder.txtStatus.setTextColor(Color.GREEN);
                break;

            case "2":
                holder.txtStatus.setText("Đã nhận đủ điều kiện");
                holder.txtStatus.setTextColor(Color.BLACK);
                break;
            case "3":
                holder.txtStatus.setText("Đã nhận có vướng");
                holder.txtStatus.setTextColor(Color.BLACK);
                break;

            case "4":
                holder.txtStatus.setText("Đã nhận có vướng có vật tư");
                holder.txtStatus.setTextColor(Color.BLACK);
                break;
            case "5":
                holder.txtStatus.setText("Đã nhận có vật tư");
                holder.txtStatus.setTextColor(Color.BLACK);
                break;

            default:
                break;

        }

        if (constBGMB.getDepartmentAssignDate() != null){
            String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(constBGMB.getDepartmentAssignDate());
            holder.txtCNReleaseDate.setText(timeStamp);
        } else {
            holder.txtCNReleaseDate.setText("Chưa có");
        }


        // set text ngay nhan
        holder.txtReceiveDate.setVisibility(View.VISIBLE);
        holder.txtTitleNgayNhan.setVisibility(View.VISIBLE);
        String timeStamp = "";
        switch (constBGMB.getReceivedStatus() + "") {
            case "1":
                holder.txtReceiveDate.setVisibility(View.GONE);
                holder.txtTitleNgayNhan.setVisibility(View.GONE);
                break;

            case "2":
                holder.txtReceiveDate.setVisibility(View.VISIBLE);
                holder.txtTitleNgayNhan.setVisibility(View.VISIBLE);
                if (constBGMB.getReceivedDate() != null){
                    timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(constBGMB.getReceivedDate());
                    holder.txtReceiveDate.setText(timeStamp);
                    Log.d(TAG, "onBindViewHolder -  getReceivedDate : " + constBGMB.getReceivedDate());
                } else {
                    holder.txtReceiveDate.setText("Chưa có");
                }

                break;
            case "3":
                holder.txtReceiveDate.setVisibility(View.VISIBLE);
                holder.txtTitleNgayNhan.setVisibility(View.VISIBLE);
                if (constBGMB.getReceivedObstructDate() != null) {
                    timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(constBGMB.getReceivedObstructDate());
                    holder.txtReceiveDate.setText(timeStamp);
                    Log.d(TAG, "onBindViewHolder -  getReceivedObstructDate : " + constBGMB.getReceivedObstructDate());
                }else{
                    holder.txtReceiveDate.setText("Chưa có");
                }

                break;

            case "4":
                holder.txtReceiveDate.setVisibility(View.VISIBLE);
                holder.txtTitleNgayNhan.setVisibility(View.VISIBLE);
                if (constBGMB.getReceivedObstructDate() != null) {
                    timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(constBGMB.getReceivedObstructDate());
                    holder.txtReceiveDate.setText(timeStamp);
                    Log.d(TAG, "onBindViewHolder -  getReceivedObstructDate : " + constBGMB.getReceivedObstructDate());
                } else {
                    holder.txtReceiveDate.setText("Chưa có");
                }

                break;
            case "5":
                holder.txtReceiveDate.setVisibility(View.VISIBLE);
                holder.txtTitleNgayNhan.setVisibility(View.VISIBLE);
                if (constBGMB.getReceivedGoodsDate() != null) {
                    timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(constBGMB.getReceivedGoodsDate());
                    holder.txtReceiveDate.setText(timeStamp);
                    Log.d(TAG, "onBindViewHolder -  getReceivedGoodsDate : " + constBGMB.getReceivedGoodsDate());
                } else {
                    holder.txtReceiveDate.setText("Chưa có");
                }

                break;

            default:
                break;

        }

        holder.itemView.setTag(constBGMB);
        holder.itemView.setOnClickListener((v) -> {
            try {
                itemRecyclerviewClick.onItemRecyclerViewclick((ConstructionBGMBDTO) v.getTag());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    class BgmbConstructionVM extends ViewModelFragmentListBase {

        @BindView(R.id.txt_construction_code)
        TextView txtConstructionCode;
        @BindView(R.id.txt_status)
        TextView txtStatus;
        @BindView(R.id.txt_CN_release_date)
        TextView txtCNReleaseDate;
        @BindView(R.id.txt_receive_date)
        TextView txtReceiveDate;
        @BindView(R.id.txt_title_congtrinh)
        TextView txt_title_congtrinh;
        @BindView(R.id.txtTitleNgayNhan)
        TextView txtTitleNgayNhan;
        public BgmbConstructionVM(View itemView) {
            super(itemView);
        }
    }
}
