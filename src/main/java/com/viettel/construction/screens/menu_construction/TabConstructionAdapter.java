package com.viettel.construction.screens.menu_construction;

import android.content.Context;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ViewModelFragmentListBase;
import com.viettel.construction.model.api.ConstructionScheduleDTO;

import java.util.List;

import butterknife.BindView;

public class TabConstructionAdapter
        extends AdapterFragmentListBase<ConstructionScheduleDTO,
        TabConstructionAdapter.TabConstructorVM> {


    public TabConstructionAdapter(Context context, List<ConstructionScheduleDTO> listData) {
        super(context, listData);
    }

    @Override
    public void onBindViewHolder(TabConstructorVM holder, int position) {
        ConstructionScheduleDTO entity = getListData().get(position);
        if (entity.getConstructionCode() != null)
            holder.mTVConstructionCode.setText(entity.getConstructionCode());
        if (entity.getConstructionName() != null)
            holder.mTVConstructionLocation.setText(entity.getConstructionName());
        if (entity.getTotalTask() != null)
            holder.mTVConstructionPercentComplete.setText(entity.getUncomTotalTask());
        if (entity.getStatus() != null) {
            if (entity.getStatus().trim().equals("4")) {
                holder.mTVConstructionStatus.setText(context.getString(R.string.status_pause));
                holder.mTVConstructionStatus.setTextColor(ContextCompat.getColor(context, R.color.pause_color));
                holder.mIVStatus.setImageResource(R.drawable.ic_pause);
                holder.mLnCompleteTask.setVisibility(View.VISIBLE);
            } else if (entity.getUnCompletedTask() != null) {
                if (entity.getUnCompletedTask().trim().equals("0")) {
                    holder.mTVConstructionStatus.setText(context.getString(R.string.finished));
                    holder.mIVStatus.setImageResource(R.drawable.accept);
                    holder.mTVConstructionStatus.setTextColor(ContextCompat.getColor(context, R.color.complete_color));
                    holder.mLnCompleteTask.setVisibility(View.INVISIBLE);
                } else {
                    holder.mTVConstructionStatus.setText(context.getString(R.string.have_not_finish));
                    holder.mTVConstructionStatus.setTextColor(Color.parseColor("#ffad4e"));
                    holder.mTVConstructionStatus.setTextColor(ContextCompat.getColor(context, R.color.in_process_color));
                    holder.mIVStatus.setImageResource(R.drawable.ic_progress_48);
                    holder.mLnCompleteTask.setVisibility(View.VISIBLE);

                }
            }
        }
        holder.itemView.setTag(entity);
        holder.itemView.setOnClickListener((v) -> {
            itemRecyclerviewClick.onItemRecyclerViewclick((ConstructionScheduleDTO) v.getTag());
        });

    }

    @Override
    public TabConstructorVM onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_construction_management, parent, false);
        return new TabConstructorVM(view);
    }

    public class TabConstructorVM extends ViewModelFragmentListBase {

        @BindView(R.id.construction_code)
        TextView mTVConstructionCode;
        @BindView(R.id.construction_status)
        TextView mTVConstructionStatus;
        @BindView(R.id.construction_location)
        TextView mTVConstructionLocation;
        @BindView(R.id.construction_percent_complete)
        TextView mTVConstructionPercentComplete;
        @BindView(R.id.iv_status)
        ImageView mIVStatus;
        @BindView(R.id.ln_complete_task)
        LinearLayout mLnCompleteTask;

        public TabConstructorVM(View itemView) {
            super(itemView);
        }
    }
}
