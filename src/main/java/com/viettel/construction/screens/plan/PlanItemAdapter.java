package com.viettel.construction.screens.plan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ViewModelFragmentListBase;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailDTO;

import java.util.List;

import butterknife.BindView;

public class PlanItemAdapter
        extends AdapterFragmentListBase<ConstructionAcceptanceCertDetailDTO,
        PlanItemAdapter.PlanViewHolder> {

    public PlanItemAdapter(Context context, List<ConstructionAcceptanceCertDetailDTO> listData) {
        super(context, listData);
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {

        holder.tvCodeWo.setText("COGTRIH_SL");
        holder.tvName.setText("XD mong cot duoi dat");
        switch (position){
            case 1:
                holder.tvStatus.setText("Dang thuc hien");
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.c23));
                break;
            case 2:
                holder.tvStatus.setText("Tu choi");
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.c14));
                break;
                default:
                    holder.tvStatus.setText("Cho FT tiep nhan");
                    holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.gray));
                    break;
        }

        holder.itemView.setOnClickListener((v) -> {
            try {
                itemRecyclerviewClick.onItemRecyclerViewclick((ConstructionAcceptanceCertDetailDTO) v.getTag());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan, parent, false);
        return new PlanViewHolder(view);
    }

    class PlanViewHolder extends ViewModelFragmentListBase {

        @BindView(R.id.tv_code_wo)
        TextView tvCodeWo;
        @BindView(R.id.tv_name_wo)
        TextView tvName;
        @BindView(R.id.tv_status)
        TextView tvStatus;

        public PlanViewHolder(View itemView) {
            super(itemView);
        }


    }
}
