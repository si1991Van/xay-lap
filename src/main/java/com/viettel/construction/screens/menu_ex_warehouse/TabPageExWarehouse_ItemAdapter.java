package com.viettel.construction.screens.menu_ex_warehouse;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ViewModelFragmentListBase;
import com.viettel.construction.model.api.SynStockTransDTO;

import java.util.List;

import butterknife.BindView;

public class TabPageExWarehouse_ItemAdapter extends AdapterFragmentListBase<SynStockTransDTO, TabPageExWarehouse_ItemAdapter.ExWarehouseVM> {
    private final String TAG = "VTItemAdapter";

    public TabPageExWarehouse_ItemAdapter(Context context, List<SynStockTransDTO> listData) {
        super(context, listData);
    }

    @Override
    public ExWarehouseVM onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_warehouse, parent, false);
        return new ExWarehouseVM(view);
    }

    @Override
    public void onBindViewHolder(ExWarehouseVM holder, int position) {
        SynStockTransDTO bill = getListData().get(position);
        int index = position + 1;
        if (bill.getStockType().contains("A") && bill.getCntContractCode() != null) {
            holder.ln_ContractCode.setVisibility(View.VISIBLE);
            holder.txt_contract_code.setText(bill.getCntContractCode());
        } else {
            holder.ln_ContractCode.setVisibility(View.GONE);
        }
        holder.txtBG.setVisibility(bill.getReceiverId() == null ? View.GONE : View.VISIBLE);
        holder.txtCondition.setVisibility(bill.getReceiverId() == null ? View.GONE : View.VISIBLE);
//        if (bill.getReceiverId() == null) {
//            holder.txtBG.setVisibility(View.GONE);
//            holder.txtCondition.setVisibility(View.GONE);
//        } else {
//            holder.txtBG.setVisibility(View.VISIBLE);
//            holder.txtCondition.setVisibility(View.VISIBLE);
//        }
        holder.txtIndex.setText(context.getString(R.string.text_code_bill_with_index, index));
        if (bill.getCode() != null)
            holder.txtCode.setText(bill.getCode());
        //26112018_HuyVT2_Comment_Start: Add them construction Code vao danh sach phieu xuat kho
        if (bill.getConsCode() != null)
            holder.txtConstructionCode.setText(bill.getConsCode());
//        26112018_HuyVT2_Comment_End-----------------

        if (bill.getRealIeTransDate() != null)
            holder.txtReleaseDate.setText(bill.getRealIeTransDate());

        if (bill.getConfirm() != null) {
            switch (bill.getConfirm().trim()) {
                case "0":
                    holder.txtStatus.setText(context.getString(R.string.wait_for_receive));// Chờ tiếp nhận
                    holder.txtStatus.setTextColor(Color.GRAY);
                    break;
                case "1":
                    holder.txtStatus.setText(context.getString(R.string.received_bill_menu));// Đã tiếp nhận
                    holder.txtStatus.setTextColor(Color.BLACK);
                    break;
                case "2":
                    holder.txtStatus.setText(context.getString(R.string.rejected)); // Đã từ chối
                    holder.txtStatus.setTextColor(Color.RED);
                    break;
                default:
                    break;
            }
        } else {
            holder.txtStatus.setText(context.getString(R.string.wait_for_receive));
            holder.txtStatus.setTextColor(Color.GRAY);
        }
// receiver is null -> hide state
        if (bill.getState() != null) {
            switch (bill.getState().trim()) {
                case "0":
                    holder.txtCondition.setText(context.getString(R.string.wait_for_confirm_1));// Chờ xác nhận
                    holder.txtCondition.setTextColor(Color.GRAY);
                    break;
                case "1":
                    holder.txtCondition.setText(context.getString(R.string.confirmed));// Đã xác nhận
                    holder.txtCondition.setTextColor(Color.BLACK);
                    break;
                case "2":
                    holder.txtCondition.setText(context.getString(R.string.deny));// Từ chối xác nhận
                    holder.txtCondition.setTextColor(Color.RED);
                    break;
                default:
                    break;
            }
        } else {
            holder.txtCondition.setText(context.getString(R.string.wait_for_confirm_1));// Chờ xác nhận
            holder.txtCondition.setTextColor(Color.GRAY);
        }


        holder.itemView.setTag(bill);
        holder.itemView.setOnClickListener((v) -> {
            try {
                Log.d(TAG, "setOnClickListener");
                itemRecyclerviewClick.onItemRecyclerViewclick((SynStockTransDTO) v.getTag());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    class ExWarehouseVM extends ViewModelFragmentListBase {

        @BindView(R.id.tv_index)
        TextView txtIndex;
        @BindView(R.id.txt_code)
        TextView txtCode;
        @BindView(R.id.txt_release_date)
        TextView txtReleaseDate;
        @BindView(R.id.txt_status)
        TextView txtStatus;
        @BindView(R.id.txt_condition)
        TextView txtCondition;
        @BindView(R.id.tv_status_bg)
        TextView txtBG;
        @BindView(R.id.txt_construction_code)
        TextView txtConstructionCode;
        @BindView(R.id.ln_ContractCode)
        LinearLayout ln_ContractCode;
        @BindView(R.id.txt_contract_code)
        TextView txt_contract_code;


        public ExWarehouseVM(View itemView) {
            super(itemView);
        }
    }
}
