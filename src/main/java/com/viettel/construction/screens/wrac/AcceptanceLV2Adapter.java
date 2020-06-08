package com.viettel.construction.screens.wrac;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AcceptanceLV2Adapter extends RecyclerView.Adapter<AcceptanceLV2Adapter.ViewHolder> {

    private Activity activity;
    private List<ConstructionAcceptanceCertDetailDTO> listData;

    public void setListData(List<ConstructionAcceptanceCertDetailDTO> listData) {
        this.listData = listData;
    }

    private OnClickDetails onClickDetails;
    private ConstructionAcceptanceCertDetailDTO data;

    public AcceptanceLV2Adapter(Activity activity, List<ConstructionAcceptanceCertDetailDTO> listData, OnClickDetails onClickDetails) {
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

        // status
        // 0 : Cho nghiem thu
        // 1 : Da nghiem thu

        data = listData.get(position);
        if (data.getWorkItemName() != null)
            holder.txtIDWork.setText(data.getWorkItemName());
        if (data.getStatusAcceptance() != null) {
            if (data.getStatusAcceptance().equals("0")) {
                holder.txtStatus.setText("Chờ nghiệm thu");
                holder.txtStatus.setTextColor(activity.getResources().getColor(R.color.gray));
            } else if (data.getStatusAcceptance().equals("1")) {
                holder.txtStatus.setText("Đã nghiệm thu");
                holder.txtStatus.setTextColor(activity.getResources().getColor(R.color.c4));
            }
        }
        holder.itemView.setTag(data);
        holder.itemView.setOnClickListener((v) -> {
            onClickDetails.onClickItemAcceptance((ConstructionAcceptanceCertDetailDTO) v.getTag());

        });
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
        void onClickItemAcceptance(ConstructionAcceptanceCertDetailDTO data);
    }

}