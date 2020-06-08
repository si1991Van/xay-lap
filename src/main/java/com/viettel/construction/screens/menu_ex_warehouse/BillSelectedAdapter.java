package com.viettel.construction.screens.menu_ex_warehouse;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.viettel.construction.R;
import com.viettel.construction.common.PrefManager;
import com.viettel.construction.model.api.SynStockTransDTO;
import com.viettel.construction.model.api.acceptance.SynStockTransDetailSerialDTO;
import com.viettel.construction.screens.custom.dialog.DialogConfirm;

import java.util.List;

public class BillSelectedAdapter extends RecyclerView.Adapter<BillSelectedAdapter.ViewHolder> {
    private Context mContext;
    private List<SynStockTransDTO> synStockTransDTOs;

    private itemClick itemClick;

    public void setItemClick(BillSelectedAdapter.itemClick itemClick) {
        this.itemClick = itemClick;
    }

    public BillSelectedAdapter(Context mContext, List<SynStockTransDTO> synStockTransDTOs) {
        this.mContext = mContext;
        this.synStockTransDTOs = synStockTransDTOs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View billSelectedView = layoutInflater.inflate(R.layout.selected_bill_row, parent,false);
        ViewHolder viewHolder = new ViewHolder(billSelectedView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SynStockTransDTO synStockTransDTO = synStockTransDTOs.get(position);
        if (synStockTransDTO.getStockType().contains("A") && synStockTransDTO.getCntContractCode() != null) {
            holder.ln_ContractCode.setVisibility(View.VISIBLE);
            holder.txt_contract_code.setText(synStockTransDTO.getCntContractCode());
        } else {
            holder.ln_ContractCode.setVisibility(View.GONE);
        }
        if (synStockTransDTO.getReceiverId() == null) {
            holder.tv_status_bg.setVisibility(View.GONE);
            holder.txt_condition.setVisibility(View.GONE);
        } else {
            holder.tv_status_bg.setVisibility(View.VISIBLE);
            holder.txt_condition.setVisibility(View.VISIBLE);
        }
        holder.tv_index.setText(mContext.getString(R.string.text_code_bill_with_index, position + 1));
        if (synStockTransDTO.getCode() != null)
            holder.txt_code.setText(synStockTransDTO.getCode());
        //26112018_HuyVT2_Comment_Start: Add them construction Code vao danh sach phieu xuat kho
        if (synStockTransDTO.getConsCode() != null)
            holder.txt_construction_code.setText(synStockTransDTO.getConsCode());
//        26112018_HuyVT2_Comment_End-----------------

        if (synStockTransDTO.getRealIeTransDate() != null)
            holder.txt_release_date.setText(synStockTransDTO.getRealIeTransDate());

        if (synStockTransDTO.getConfirm() != null) {
            switch (synStockTransDTO.getConfirm().trim()) {
                case "0":
                    holder.txt_status.setText(mContext.getString(R.string.wait_for_receive));// Chờ tiếp nhận
                    holder.txt_status.setTextColor(Color.GRAY);
                    break;
                case "1":
                    holder.txt_status.setText(mContext.getString(R.string.received_bill_menu));// Đã tiếp nhận
                    holder.txt_status.setTextColor(Color.BLACK);
                    break;
                case "2":
                    holder.txt_status.setText(mContext.getString(R.string.rejected)); // Đã từ chối
                    holder.txt_status.setTextColor(Color.RED);
                    break;
                default:
                    break;
            }
        } else {
            holder.txt_status.setText(mContext.getString(R.string.wait_for_receive));
            holder.txt_status.setTextColor(Color.GRAY);

        }
// receiver is null -> hide state
        if (synStockTransDTO.getState() != null) {
            switch (synStockTransDTO.getState().trim()) {
                case "0":
                    holder.txt_condition.setText(mContext.getString(R.string.wait_for_confirm_1));// Chờ xác nhận
                    holder.txt_condition.setTextColor(Color.GRAY);
                    break;
                case "1":
                    holder.txt_condition.setText(mContext.getString(R.string.confirmed));// Đã xác nhận
                    holder.txt_condition.setTextColor(Color.BLACK);
                    break;
                case "2":
                    holder.txt_condition.setText(mContext.getString(R.string.deny));// Từ chối xác nhận
                    holder.txt_condition.setTextColor(Color.RED);
                    break;
                default:
                    break;
            }
        } else {
            holder.txt_condition.setText(mContext.getString(R.string.wait_for_confirm_1));// Chờ xác nhận
            holder.txt_condition.setTextColor(Color.GRAY);
        }

//        holder.setListenes();
    }

    @Override
    public int getItemCount() {
        return synStockTransDTOs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder  {
        TextView tv_index, txt_code, txt_construction_code, txt_contract_code, txt_release_date,
                txt_status, tv_status_bg, txt_condition;
        LinearLayout ln_ContractCode;
        ImageView imv_delete;
//        int pos;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_index = itemView.findViewById(R.id.tv_index);
            txt_code = itemView.findViewById(R.id.txt_code);
            txt_construction_code = itemView.findViewById(R.id.txt_construction_code);
            txt_contract_code = itemView.findViewById(R.id.txt_contract_code);
            txt_release_date = itemView.findViewById(R.id.txt_release_date);
            txt_status = itemView.findViewById(R.id.txt_status);
            tv_status_bg = itemView.findViewById(R.id.tv_status_bg);
            txt_condition = itemView.findViewById(R.id.txt_condition);
            ln_ContractCode = itemView.findViewById(R.id.ln_ContractCode);
            imv_delete = itemView.findViewById(R.id.imv_delete);
            imv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClick.itemDelete(getLayoutPosition());
                }
            });
        }
//        public void setListenes(){
//            imv_delete.setOnClickListener(ViewHolder.this);
//        }
//        @Override
//        public void onClick(View view) {
//            dialogConfirm.show();
//            removeItem(pos);
//            notifyItemRemoved(pos);
//        }
//        public void removeItem(int pos){
//            synStockTransDTOs.remove(pos);
//        }
    }
    interface itemClick{
        void itemDelete(int pos);
    }
}
