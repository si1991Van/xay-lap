package com.viettel.construction.screens.menu_history_vttb;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.history.StTransactionDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HandOver_Receiver_Detail_Level2Adapter extends RecyclerView.Adapter<HandOver_Receiver_Detail_Level2Adapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<StTransactionDTO> listData;
    private OnClickHistory onClickHistory;
    private boolean isReceiver;

    public List<StTransactionDTO> getListData() {
        return listData;
    }

    public void setListData(List<StTransactionDTO> listData) {
        this.listData = listData;
    }

    public HandOver_Receiver_Detail_Level2Adapter(Context context, List<StTransactionDTO> list, OnClickHistory onClickHistory) {
        this.context = context;
        this.listData = list;
        inflater = LayoutInflater.from(context);
        this.onClickHistory = onClickHistory;
    }

    @Override
    public HandOver_Receiver_Detail_Level2Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StTransactionDTO stTransactionDTO = listData.get(position);
        if (stTransactionDTO.getStockTransCode() != null)
            holder.tvBillId.setText(stTransactionDTO.getStockTransCode());
        if (stTransactionDTO.getStockTransConstructionCode() != null)
            holder.tvConstructionId.setText(stTransactionDTO.getStockTransConstructionCode());
//        if (stTransactionDTO.getStockTransCreatedByName() != null)
//            holder.tvUser.setText(stTransactionDTO.getStockTransCreatedByName());
        if (stTransactionDTO.getSynStockTransCreatedDate() != null)
            holder.tvDate.setText(stTransactionDTO.getSynStockTransCreatedDate());
        // Tiếp nhận : old_last_shipper
        // Bàn Giao : new_last_shipper
        if (isReceiver) {
            holder.tvHandover.setText(context.getString(R.string.handover_person));
            holder.tvUser.setText(stTransactionDTO.getNewLastShipperId() + "");
        } else {
            holder.tvHandover.setText(context.getString(R.string.receiver_with));
            holder.tvUser.setText(stTransactionDTO.getOldLastShipperId() + "");
        }
        if (stTransactionDTO.getConfirm() != null) {
            // 0 : cho xac nhan
            // 1 : da xac nhan
            // 2 : da tu choi
            String status = stTransactionDTO.getConfirm().trim();
            switch (status) {
                case "0":
                    holder.tvStatus.setText(context.getString(R.string.wait_for_confirm_1));
                    holder.tvStatus.setTextColor(context.getResources().getColor(R.color.c1));
                    break;
                case "1":
                    holder.tvStatus.setText(context.getString(R.string.confirm_category));
                    holder.tvStatus.setTextColor(context.getResources().getColor(R.color.c4));
                    break;
                case "2":
                    holder.tvStatus.setText(context.getString(R.string.rejected));
                    holder.tvStatus.setTextColor(context.getResources().getColor(R.color.c14));
                    break;
                default:
                    break;
            }
        }
        holder.itemView.setTag(stTransactionDTO);
        holder.itemView.setOnClickListener((view) -> {
            onClickHistory.onClick((StTransactionDTO) view.getTag());
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_code_bill)
        TextView tvBillId;
        @BindView(R.id.tv_construction_id)
        TextView tvConstructionId;
        @BindView(R.id.tv_user_receive)
        TextView tvUser;
        @BindView(R.id.tv_date_receive)
        TextView tvDate;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_handover_receiver)
        TextView tvHandover;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public interface OnClickHistory {
        void onClick(StTransactionDTO stTransactionDTO);
    }

    public void setDataForBooleanVariable(boolean isReceiver) {
        this.isReceiver = isReceiver;
    }
}
