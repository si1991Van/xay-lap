package com.viettel.construction.screens.wrac;

import android.content.Context;
import androidx.core.content.ContextCompat;
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

public class AcceptanceLV1Adapter extends RecyclerView.Adapter<AcceptanceLV1Adapter.AcceptanceLever1ViewHolder> {
    private Context mContext;
    private List<ConstructionAcceptanceCertDetailDTO> listData;
    private OnClickAcceptanceLever1 onClickAcceptanceLever1;

    public List<ConstructionAcceptanceCertDetailDTO> getListData() {
        return listData;
    }

    public void setListData(List<ConstructionAcceptanceCertDetailDTO> listData) {
        this.listData = listData;
    }

    public AcceptanceLV1Adapter(Context mContext, List<ConstructionAcceptanceCertDetailDTO> listData, OnClickAcceptanceLever1 onClickAcceptanceLever1) {
        this.mContext = mContext;
        this.listData = listData;
        this.onClickAcceptanceLever1 = onClickAcceptanceLever1;
    }

    @Override
    public AcceptanceLever1ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_acceptance_lever_1, parent, false);
        return new AcceptanceLever1ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AcceptanceLever1ViewHolder holder, int position) {
        ConstructionAcceptanceCertDetailDTO dto = listData.get(position);
        if (dto.getConstructionCode() != null)
            holder.mTVConstructionCode.setText(dto.getConstructionCode());
        if (dto.getAddress() != null)
            holder.mTVConstructionLocation.setText(dto.getAddress());
        if (dto.getStatusConstruction() != null) {
            if (dto.getStatusConstruction().equals("3")) {
                holder.mTVStatus.setText("Đang thực hiện");
                holder.mTVStatus.setTextColor(ContextCompat.getColor(mContext, R.color.complete_color));
            } else if (dto.getStatusConstruction().equals("4")) {
                holder.mTVStatus.setText("Tạm dừng");
                holder.mTVStatus.setTextColor(ContextCompat.getColor(mContext, R.color.pause_color));
            } else {
                holder.mTVStatus.setText("Đã hoàn thành");
                holder.mTVStatus.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
            }
        }
        holder.itemView.setTag(dto);
        holder.itemView.setOnClickListener((v) -> {
            onClickAcceptanceLever1.onClick((ConstructionAcceptanceCertDetailDTO) v.getTag());
        });

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    public class AcceptanceLever1ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.construction_code)
        TextView mTVConstructionCode;

        @BindView(R.id.construction_location)
        TextView mTVConstructionLocation;

        @BindView(R.id.construction_status)
        TextView mTVStatus;

        public AcceptanceLever1ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public interface OnClickAcceptanceLever1 {
        void onClick(ConstructionAcceptanceCertDetailDTO data);
    }
}
