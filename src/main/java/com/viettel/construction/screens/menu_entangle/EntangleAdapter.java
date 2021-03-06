package com.viettel.construction.screens.menu_entangle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ViewModelFragmentListBase;
import com.viettel.construction.model.api.entangle.EntangleManageDTO;

import java.util.List;

import butterknife.BindView;

public class EntangleAdapter extends AdapterFragmentListBase<EntangleManageDTO, EntangleAdapter.EngtangleVM> {

    @Override
    public void onBindViewHolder(EngtangleVM holder, int position) {

        // 1 chua xac nhan
        // 2 xac nhan
        // 0 het vuong
        EntangleManageDTO dto = getListData().get(position);
        if (dto.getConsCode() != null)
            holder.txtIDWork.setText(dto.getConsCode());

        String hangMuc = dto.getWorkItemName() != null ? dto.getWorkItemName() : "";
        holder.txtHangMuc.setText(hangMuc);

        if (dto.getObstructedState() != null) {
            if (dto.getObstructedState().equals("0")) {
                holder.txtStatus.setText("Hết vướng");
                holder.txtStatus.setTextColor(context.getResources().getColor(R.color.c4));
            }
            if (dto.getObstructedState().equals("1")) {
                holder.txtStatus.setText("Chưa có xác nhận của CĐT");
                holder.txtStatus.setTextColor(context.getResources().getColor(R.color.gray));
            }
            if (dto.getObstructedState().equals("2")) {
                holder.txtStatus.setText("Đã xác nhận của CĐT");
                holder.txtStatus.setTextColor(context.getResources().getColor(R.color.black_base));
            }
        }

        holder.itemView.setTag(dto);
        holder.itemView.setOnClickListener((v) -> {
            try {
                itemRecyclerviewClick.onItemRecyclerViewclick((EntangleManageDTO) v.getTag());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public EngtangleVM onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entangle_hangmuc, parent, false);
        return new EngtangleVM(view);
    }

    public EntangleAdapter(Context context, List<EntangleManageDTO> listData) {
        super(context, listData);
    }

    class EngtangleVM extends ViewModelFragmentListBase {

        @BindView(R.id.tv_index)
        TextView txtIndex;
        @BindView(R.id.refund_lv1_tv_cons_code)
        TextView txtIDWork;
        @BindView(R.id.refund_lv1_tv_status)
        TextView txtStatus;
        @BindView(R.id.txtHangMuc)
        TextView txtHangMuc;

        public EngtangleVM(View itemView) {
            super(itemView);
        }
    }
}
