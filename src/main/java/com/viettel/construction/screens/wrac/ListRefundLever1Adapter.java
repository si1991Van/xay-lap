package com.viettel.construction.screens.wrac;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.ConstructionMerchandiseDetailDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListRefundLever1Adapter extends RecyclerView.Adapter<ListRefundLever1Adapter.RefundLever1ViewHolder> {
    private List<ConstructionMerchandiseDetailDTO> listData;
    private Context mContext;
    private RefundLv1ItemClick mOnClick;

    public List<ConstructionMerchandiseDetailDTO> getListData() {
        return listData;
    }

    public void setListData(List<ConstructionMerchandiseDetailDTO> listData) {
        this.listData = listData;
    }

    public ListRefundLever1Adapter(List<ConstructionMerchandiseDetailDTO> listData, Context mContext, RefundLv1ItemClick mOnClick) {
        this.listData = listData;
        this.mContext = mContext;
        this.mOnClick = mOnClick;
    }

    @Override
    public RefundLever1ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_entangle, parent, false);
        return new RefundLever1ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RefundLever1ViewHolder holder, int position) {
        ConstructionMerchandiseDetailDTO vtdto = listData.get(position);
        if (vtdto.getConstructionCode() != null)
            holder.mTVConsCode.setText(vtdto.getConstructionCode());
        if (vtdto.getConstructionStatus() != null) {
            if (vtdto.getConstructionStatus().equals("3")) {
                holder.mTVStatus.setText(mContext.getString(R.string.in_process_1));
                holder.mTVStatus.setTextColor(ContextCompat.getColor(mContext, R.color.in_process_color));
            } else if (vtdto.getConstructionStatus().equals("4")) {
                holder.mTVStatus.setText(mContext.getString(R.string.on_pause));
                holder.mTVStatus.setTextColor(ContextCompat.getColor(mContext, R.color.pause_color));
            } else if (vtdto.getConstructionStatus().equals("5")) {
                holder.mTVStatus.setText(mContext.getString(R.string.finished));
                holder.mTVStatus.setTextColor(ContextCompat.getColor(mContext, R.color.complete_color));
            } else if (vtdto.getConstructionStatus().equals("6")){
                holder.mTVStatus.setText(mContext.getString(R.string.already_acceptance));
                holder.mTVStatus.setTextColor(ContextCompat.getColor(mContext, R.color.grey_color));
            }
        } else {
            holder.mTVStatus.setText("");
        }

        holder.itemView.setTag(vtdto);
        holder.itemView.setOnClickListener((v) -> {
            mOnClick.onItemClick((ConstructionMerchandiseDetailDTO) v.getTag());
        });

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class RefundLever1ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.refund_lv1_tv_cons_code)
        TextView mTVConsCode;
        @BindView(R.id.refund_lv1_tv_status)
        TextView mTVStatus;

        public RefundLever1ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public interface RefundLv1ItemClick {
        void onItemClick(ConstructionMerchandiseDetailDTO vtdto);
    }
}
