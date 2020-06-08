package com.viettel.construction.screens.menu_construction;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ViewModelFragmentListBase;
import com.viettel.construction.model.api.ConstructionScheduleItemDTO;
import com.viettel.construction.server.util.StringUtil;

import java.util.List;

import butterknife.BindView;

public class ConstructionLevel1Adapter
        extends AdapterFragmentListBase<ConstructionScheduleItemDTO,
        ConstructionLevel1Adapter.ConstructionLevel1VM> {

    private String scheduleType = "";

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public ConstructionLevel1Adapter(Context context, List<ConstructionScheduleItemDTO> listData) {
        super(context, listData);
    }

    @Override
    public ConstructionLevel1VM onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_construction, parent, false);
        return new ConstructionLevel1VM(view);
    }

    @Override
    public void onBindViewHolder(ConstructionLevel1VM holder, int position) {
        ConstructionScheduleItemDTO constructionScheduleItemDTO = getListData().get(position);
        holder.tvCategory.setText(constructionScheduleItemDTO.getName());
        if (scheduleType.equals("1")) {
            holder.tvPerInchage.setText("Đối tác:");
            holder.tvPerformer.setText(constructionScheduleItemDTO.getCatPrtName());
        } else {
            if (constructionScheduleItemDTO.getSyuFullName() != null) {
                holder.tvPerInchage.setText("Người thực hiện:");
                holder.tvPerformer.setText(constructionScheduleItemDTO.getSyuFullName());
            }
        }

        //Gan %
        if (constructionScheduleItemDTO.getCompletePercent() != null) {
            Double progress = StringUtil.round(constructionScheduleItemDTO.getCompletePercent(), 0);
            String convert = (progress + "").replace(".0", "");
            holder.tvProgress.setText(convert + "%");
        }

        switch (constructionScheduleItemDTO.getStatus()) {
            case "1"://Chua thuc hien
                holder.tvProgress.setText("");
                holder.tvStatus.setText(context.getResources().getString(R.string.did_not_perform));
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.grey_color));
                break;
            case "2"://Đang thực hiện
                holder.tvStatus.setText(context.getResources().getString(R.string.in_process_1));
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.in_process_color));
                break;
            case "3":
                holder.tvStatus.setText(context.getResources().getString(R.string.finished));
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.complete_color));
                break;
            case "4":
                holder.tvStatus.setText(context.getResources().getString(R.string.on_pause));
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.pause_color));
                break;
        }



        if (constructionScheduleItemDTO.getCompleteState() == 2) {
            holder.imWarning.setVisibility(View.VISIBLE);
        } else {
            holder.imWarning.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setTag(constructionScheduleItemDTO);
        holder.itemView.setOnClickListener((v) ->
                itemRecyclerviewClick.onItemRecyclerViewclick((ConstructionScheduleItemDTO) v.getTag())
        );

        holder.itemView.setOnLongClickListener((view -> {
            itemRecyclerviewClick.onItemRecyclerViewLongclick((ConstructionScheduleItemDTO) view.getTag());
            return true;
        }));

        //TODO: Tam thoi chua tinh long click
//        holder.itemView.setOnLongClickListener((view) -> {
//                    onClickItem.onLongClickDetailConstruction(position);
//                    return true;
//                }
//        );
    }

    class ConstructionLevel1VM extends ViewModelFragmentListBase {
        @BindView(R.id.imv_warning)
        ImageView imWarning;
        @BindView(R.id.tv_category)
        TextView tvCategory;
        @BindView(R.id.tv_progress)
        TextView tvProgress;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_performer)
        TextView tvPerformer;
        @BindView(R.id.tv_per_in_charge)
        TextView tvPerInchage;

        public ConstructionLevel1VM(View itemView) {
            super(itemView);
        }
    }
}
