package com.viettel.construction.screens.wrac;

import android.app.Activity;
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

public class ListRefundLever2Adapter extends RecyclerView.Adapter<ListRefundLever2Adapter.ViewHolder> {

    private Activity activity;
    private List<ConstructionMerchandiseDetailDTO> listData;
    private OnClickDetails onClickDetails;

    public List<ConstructionMerchandiseDetailDTO> getListData() {
        return listData;
    }

    public void setListData(List<ConstructionMerchandiseDetailDTO> listData) {
        this.listData = listData;
    }

    public ListRefundLever2Adapter(Activity activity, List<ConstructionMerchandiseDetailDTO> listData, OnClickDetails onClickDetails) {
        this.activity = activity;
        this.listData = listData;
        this.onClickDetails = onClickDetails;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_acceptance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ConstructionMerchandiseDetailDTO data = listData.get(position);
        if (data.getWorkItemName() != null)
            holder.txtIDWork.setText(data.getWorkItemName());
        if (data.getStatusComplete() != null) {
            if (data.getStatusComplete().equals("1")) {
                holder.txtStatus.setText("Đã hoàn trả");
                holder.txtStatus.setTextColor(activity.getResources().getColor(R.color.gray));
            } else if (data.getStatusComplete().equals("0")) {
                holder.txtStatus.setText("Chưa hoàn trả");
                holder.txtStatus.setTextColor(activity.getResources().getColor(R.color.black_base));
            }
        }

        holder.itemView.setTag(data);
        holder.itemView.setOnClickListener((v) ->
                onClickDetails.onClickItem((ConstructionMerchandiseDetailDTO) v.getTag())
        );

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_index)
        TextView txtIndex;
        @BindView(R.id.txt_id_work)
        TextView txtIDWork;
        @BindView(R.id.txt_status)
        TextView txtStatus;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public interface OnClickDetails {
        void onClickItem(ConstructionMerchandiseDetailDTO data);
    }

}