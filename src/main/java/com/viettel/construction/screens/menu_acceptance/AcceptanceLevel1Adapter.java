package com.viettel.construction.screens.menu_acceptance;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ViewModelFragmentListBase;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailDTO;

import java.util.List;

import butterknife.BindView;

public class AcceptanceLevel1Adapter
        extends AdapterFragmentListBase<ConstructionAcceptanceCertDetailDTO,
        AcceptanceLevel1Adapter.AcceptanceLevelVM> {

    public AcceptanceLevel1Adapter(Context context, List<ConstructionAcceptanceCertDetailDTO> listData) {
        super(context, listData);
    }

    @Override
    public void onBindViewHolder(AcceptanceLevelVM holder, int position) {
        ConstructionAcceptanceCertDetailDTO dto = getListData().get(position);
        if (dto.getConstructionCode() != null)
            holder.mTVConstructionCode.setText(dto.getConstructionCode());
        if (dto.getAddress() != null)
            holder.mTVConstructionLocation.setText(dto.getAddress());
        if (dto.getStatusConstruction() != null) {
            if (dto.getStatusConstruction().equals("3")) {
                holder.mTVStatus.setText("Đang thực hiện");
                holder.mTVStatus.setTextColor(ContextCompat.getColor(context, R.color.in_process_color));
            } else if (dto.getStatusConstruction().equals("4")) {
                holder.mTVStatus.setText("Tạm dừng");
                holder.mTVStatus.setTextColor(ContextCompat.getColor(context, R.color.pause_color));
            } else {
                holder.mTVStatus.setText("Đã hoàn thành");
                holder.mTVStatus.setTextColor(ContextCompat.getColor(context, R.color.complete_color));
            }
        }
        holder.itemView.setTag(dto);
        holder.itemView.setOnClickListener((v) -> {
            try {
                itemRecyclerviewClick.onItemRecyclerViewclick((ConstructionAcceptanceCertDetailDTO) v.getTag());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public AcceptanceLevelVM onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_acceptance_lever_1, parent, false);
        return new AcceptanceLevelVM(view);
    }

    class AcceptanceLevelVM extends ViewModelFragmentListBase {

        @BindView(R.id.construction_code)
        TextView mTVConstructionCode;

        @BindView(R.id.construction_location)
        TextView mTVConstructionLocation;

        @BindView(R.id.construction_status)
        TextView mTVStatus;

        public AcceptanceLevelVM(View itemView) {
            super(itemView);
        }
    }
}
