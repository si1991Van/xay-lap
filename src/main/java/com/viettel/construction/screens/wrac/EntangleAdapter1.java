package com.viettel.construction.screens.wrac;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.entangle.EntangleManageDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EntangleAdapter1 extends RecyclerView.Adapter<EntangleAdapter1.ViewHolder> {

    private Activity activity;
    private List<EntangleManageDTO> listData;
    private OnClickDetails onClickDetails;

    public List<EntangleManageDTO> getListData() {
        return listData;
    }

    public void setListData(List<EntangleManageDTO> listData) {
        this.listData = listData;
    }

    public EntangleAdapter1(Activity activity, List<EntangleManageDTO> listData, OnClickDetails onClickDetails) {
        this.activity = activity;
        this.listData = listData;
        this.onClickDetails = onClickDetails;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_entangle_hangmuc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 1 chua xac nhan
        // 2 xac nhan
        // 0 het vuong
        EntangleManageDTO dto = listData.get(position);
        if (dto.getConsCode() != null)
            holder.txtIDWork.setText(dto.getConsCode());

        String hangMuc = dto.getWorkItemName() != null ? dto.getWorkItemName() : "";
        holder.txtHangMuc.setText(hangMuc);

        if (dto.getObstructedState() != null) {
            if (dto.getObstructedState().equals("0")) {
                holder.txtStatus.setText("Hết vướng");
                holder.txtStatus.setTextColor(activity.getResources().getColor(R.color.c4));
            }
            if (dto.getObstructedState().equals("1")) {
                holder.txtStatus.setText("Chưa có xác nhận của CĐT");
                holder.txtStatus.setTextColor(activity.getResources().getColor(R.color.gray));
            }
            if (dto.getObstructedState().equals("2")) {
                holder.txtStatus.setText("Đã xác nhận của CĐT");
                holder.txtStatus.setTextColor(activity.getResources().getColor(R.color.black_base));
            }
        }

        holder.itemView.setTag(dto);
        holder.itemView.setOnClickListener((v) -> {
            onClickDetails.onClickItemEntangle((EntangleManageDTO) v.getTag());
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_index)
        TextView txtIndex;
        @BindView(R.id.refund_lv1_tv_cons_code)
        TextView txtIDWork;
        @BindView(R.id.refund_lv1_tv_status)
        TextView txtStatus;
        @BindView(R.id.txtHangMuc)
        TextView txtHangMuc;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public interface OnClickDetails {
        void onClickItemEntangle(EntangleManageDTO dto);
    }

}