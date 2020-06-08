package com.viettel.construction.screens.menu_return_vttb;

import android.content.Context;
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

public class RefundInventoryLevel2Adapter extends AdapterFragmentListBase<ConstructionMerchandiseDetailDTO, RefundInventoryLevel2Adapter.RefundInventoryLevel2VM> {

    public RefundInventoryLevel2Adapter(Context context, List<ConstructionMerchandiseDetailDTO> listData) {
        super(context, listData);
    }

    @Override
    public void onBindViewHolder(RefundInventoryLevel2VM holder, int position) {
        ConstructionMerchandiseDetailDTO data = getListData().get(position);
        if (data.getWorkItemName() != null)
            holder.txtIDWork.setText(data.getWorkItemName());
        if (data.getStatusComplete() != null) {
            if (data.getStatusComplete().equals("1")) {
                holder.txtStatus.setText("Đã hoàn trả");
                holder.txtStatus.setTextColor(context.getResources().getColor(R.color.gray));
            } else if (data.getStatusComplete().equals("0")) {
                holder.txtStatus.setText("Chưa hoàn trả");
                holder.txtStatus.setTextColor(context.getResources().getColor(R.color.black_base));
            }
        }

        holder.itemView.setTag(data);
        holder.itemView.setOnClickListener((v) -> {
                    try {
                        itemRecyclerviewClick.onItemRecyclerViewclick((ConstructionMerchandiseDetailDTO) v.getTag());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    @Override
    public RefundInventoryLevel2VM onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_acceptance, parent, false);
        return new RefundInventoryLevel2VM(view);
    }

    class RefundInventoryLevel2VM extends ViewModelFragmentListBase {

        @BindView(R.id.tv_index)
        TextView txtIndex;
        @BindView(R.id.txt_id_work)
        TextView txtIDWork;
        @BindView(R.id.txt_status)
        TextView txtStatus;

        public RefundInventoryLevel2VM(View itemView) {
            super(itemView);
        }

    }
}
