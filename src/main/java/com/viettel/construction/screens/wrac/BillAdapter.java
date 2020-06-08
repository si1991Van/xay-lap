package com.viettel.construction.screens.wrac;

import android.app.Activity;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.SynStockTransDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Manroid on 22/01/2018.
 */

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    private Activity activity;
    private onBillItemClick onClickListener;
    private SynStockTransDTO bill;
    private List<SynStockTransDTO> listData;

    public List<SynStockTransDTO> getListData() {
        return listData;
    }

    public void setListData(List<SynStockTransDTO> listData) {
        this.listData = listData;
    }

    public BillAdapter(Activity activity, List<SynStockTransDTO> listData, onBillItemClick onClickListener) {
        this.activity = activity;
        this.listData = listData;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_delivery_bill, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        bill = listData.get(position);
        int index = position + 1;
        if (bill.getReceiverId() == null) {
            holder.txtBG.setVisibility(View.GONE);
            holder.txtCondition.setVisibility(View.GONE);
        } else {
            holder.txtBG.setVisibility(View.VISIBLE);
            holder.txtCondition.setVisibility(View.VISIBLE);
        }
        holder.txtIndex.setText(activity.getString(R.string.text_code_bill_with_index, index));
        if (bill.getCode() != null)
            holder.txtCode.setText(bill.getCode());
        if (bill.getRealIeTransDate() != null)
            holder.txtReleaseDate.setText(bill.getRealIeTransDate());
//        if (bill.getConfirm() != null)
//            holder.txtStatus.setText(bill.getConfirm());

        if (bill.getConfirm() != null) {
            switch (bill.getConfirm().trim()) {
                case "0":
                    holder.txtStatus.setText(activity.getString(R.string.wait_for_receive));// Chờ tiếp nhận
                    holder.txtStatus.setTextColor(Color.GRAY);
                    break;
                case "1":
                    holder.txtStatus.setText(activity.getString(R.string.received_bill_menu));// Đã tiếp nhận
                    holder.txtStatus.setTextColor(Color.BLACK);
                    break;
                case "2":
                    holder.txtStatus.setText(activity.getString(R.string.rejected)); // Đã từ chối
                    holder.txtStatus.setTextColor(Color.RED);
                    break;
                default:
                    break;
            }
        } else {
            holder.txtStatus.setText(activity.getString(R.string.wait_for_receive));
            holder.txtStatus.setTextColor(Color.GRAY);
        }
// receiver is null -> hide state
        if (bill.getState() != null) {
            switch (bill.getState().trim()) {
                case "0":
                    holder.txtCondition.setText(activity.getString(R.string.wait_for_confirm_1));// Chờ xác nhận
                    holder.txtCondition.setTextColor(Color.GRAY);
                    break;
                case "1":
                    holder.txtCondition.setText(activity.getString(R.string.confirmed));// Đã xác nhận
                    holder.txtCondition.setTextColor(Color.BLACK);
                    break;
                case "2":
                    holder.txtCondition.setText(activity.getString(R.string.deny));// Từ chối xác nhận
                    holder.txtCondition.setTextColor(Color.RED);
                    break;
                default:
                    break;
            }
        } else {
            holder.txtCondition.setText(activity.getString(R.string.wait_for_confirm_1));// Chờ xác nhận
            holder.txtCondition.setTextColor(Color.GRAY);
        }


        holder.itemView.setTag(bill);
        holder.itemView.setOnClickListener((v) -> {
            onClickListener.onClickBill((SynStockTransDTO) v.getTag());
        });
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    public interface onBillItemClick {
        void onClickBill(SynStockTransDTO odt);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }
}

