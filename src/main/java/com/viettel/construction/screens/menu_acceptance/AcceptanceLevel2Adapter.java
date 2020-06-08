package com.viettel.construction.screens.menu_acceptance;

import android.content.Context;
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

public class AcceptanceLevel2Adapter
        extends AdapterFragmentListBase<ConstructionAcceptanceCertDetailDTO,
        AcceptanceLevel2Adapter.AcceptanceLevel2VM> {


    public AcceptanceLevel2Adapter(Context context,
                                   List<ConstructionAcceptanceCertDetailDTO> listData) {
        super(context, listData);
    }

    @Override
    public AcceptanceLevel2VM onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_acceptance, parent, false);
        return new AcceptanceLevel2VM(view);
    }

    @Override
    public void onBindViewHolder(AcceptanceLevel2VM holder, int position) {
        ConstructionAcceptanceCertDetailDTO data = getListData().get(position);
        if (data.getWorkItemName() != null)
            holder.txtIDWork.setText(data.getWorkItemName());
        if (data.getStatusAcceptance() != null) {
            if (data.getStatusAcceptance().equals("0")) {
                holder.txtStatus.setText("Chờ nghiệm thu");
                holder.txtStatus.setTextColor(context.getResources().getColor(R.color.gray));
            } else if (data.getStatusAcceptance().equals("1")) {
                holder.txtStatus.setText("Đã nghiệm thu");
                holder.txtStatus.setTextColor(context.getResources().getColor(R.color.c4));
            }
        }
        holder.itemView.setTag(data);
        holder.itemView.setOnClickListener((v) -> {
            try {
                itemRecyclerviewClick.onItemRecyclerViewclick(
                        (ConstructionAcceptanceCertDetailDTO) v.getTag());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    class AcceptanceLevel2VM
            extends ViewModelFragmentListBase {
        @BindView(R.id.tv_index)
        TextView txtIndex;
        @BindView(R.id.txt_id_work)
        TextView txtIDWork;
        @BindView(R.id.txt_status)
        TextView txtStatus;

        public AcceptanceLevel2VM(View itemView) {
            super(itemView);
        }
    }
}
