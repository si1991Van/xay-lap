package com.viettel.construction.screens.menu_return_vttb;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ViewModelFragmentListBase;
import com.viettel.construction.model.api.ConstructionMerchandiseDetailDTO;

import java.util.List;

import butterknife.BindView;

public class RefundInventoryLevel1Adapter extends AdapterFragmentListBase<ConstructionMerchandiseDetailDTO,RefundInventoryLevel1Adapter.RefundInventoryLevel1VM> {

    public RefundInventoryLevel1Adapter(Context context, List<ConstructionMerchandiseDetailDTO> listData) {
        super(context, listData);
    }

    @Override
    public RefundInventoryLevel1VM onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entangle, parent, false);
        return new RefundInventoryLevel1VM(view);
    }

    @Override
    public void onBindViewHolder(RefundInventoryLevel1VM holder, int position) {
        ConstructionMerchandiseDetailDTO vtdto = getListData().get(position);
        if (vtdto.getConstructionCode() != null)
            holder.mTVConsCode.setText(vtdto.getConstructionCode());
        if (vtdto.getConstructionStatus() != null) {
            if (vtdto.getConstructionStatus().equals("3")) {
                holder.mTVStatus.setText(context.getString(R.string.in_process_1));
                holder.mTVStatus.setTextColor(ContextCompat.getColor(context, R.color.in_process_color));
            } else if (vtdto.getConstructionStatus().equals("4")) {
                holder.mTVStatus.setText(context.getString(R.string.on_pause));
                holder.mTVStatus.setTextColor(ContextCompat.getColor(context, R.color.pause_color));
            } else if (vtdto.getConstructionStatus().equals("5")) {
                holder.mTVStatus.setText(context.getString(R.string.finished));
                holder.mTVStatus.setTextColor(ContextCompat.getColor(context, R.color.complete_color));
            } else if (vtdto.getConstructionStatus().equals("6")){
                holder.mTVStatus.setText(context.getString(R.string.already_acceptance));
                holder.mTVStatus.setTextColor(ContextCompat.getColor(context, R.color.grey_color));
            }
        } else {
            holder.mTVStatus.setText("");
        }

        holder.itemView.setTag(vtdto);
        holder.itemView.setOnClickListener((v) -> {
            itemRecyclerviewClick.onItemRecyclerViewclick((ConstructionMerchandiseDetailDTO) v.getTag());
        });

    }

    class RefundInventoryLevel1VM extends ViewModelFragmentListBase{

        @BindView(R.id.refund_lv1_tv_cons_code)
        TextView mTVConsCode;
        @BindView(R.id.refund_lv1_tv_status)
        TextView mTVStatus;
        public RefundInventoryLevel1VM(View itemView) {
            super(itemView);
        }
    }
}
