package com.viettel.construction.screens.wrac;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.construction.server.util.StringUtil;
import com.viettel.construction.R;
import com.viettel.construction.model.api.ConstructionScheduleItemDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailConstructionAdapter_rac extends RecyclerView.Adapter<DetailConstructionAdapter_rac.ViewHolder> {

    private List<ConstructionScheduleItemDTO> detailConstructionList;

    public List<ConstructionScheduleItemDTO> getDetailConstructionList() {
        return detailConstructionList;
    }

    private LayoutInflater inflater;
    private Context context;
    private OnClickItem onClickItem;
    private String scheduleType;

    public DetailConstructionAdapter_rac(String scheduleType, Context context, List<ConstructionScheduleItemDTO> detailConstructionList, OnClickItem onClickItem) {
        this.scheduleType = scheduleType;
        this.context = context;
        this.detailConstructionList = detailConstructionList;
        this.onClickItem = onClickItem;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_detail_construction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ConstructionScheduleItemDTO constructionScheduleItemDTO = detailConstructionList.get(position);
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

        if (constructionScheduleItemDTO.getCompletePercent() != null) {
            Double progress = StringUtil.round(constructionScheduleItemDTO.getCompletePercent(), 0);
            String convert = (progress + "").replace(".0", "");
            holder.tvProgress.setText(convert + "%");
            if (progress.compareTo(100d) == 0) {
                holder.tvStatus.setText(context.getResources().getString(R.string.finished));
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.complete_color));
            } else if (progress.compareTo(0d) == 0) {
                holder.tvStatus.setText(context.getResources().getString(R.string.did_not_perform));
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.pause_color));
            } else {
                holder.tvStatus.setText(context.getResources().getString(R.string.in_process_1));
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.in_process_color));
            }
        } else {
            holder.tvProgress.setText("");
            holder.tvStatus.setText(context.getResources().getString(R.string.did_not_perform));
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.pause_color));
        }
        if (constructionScheduleItemDTO.getCompleteState() == 2) {
            holder.imWarning.setVisibility(View.VISIBLE);
        } else {
            holder.imWarning.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener((view) ->
                onClickItem.onClickDetailConstruction(position)
        );
        holder.itemView.setOnLongClickListener((view) -> {
                    onClickItem.onLongClickDetailConstruction(position);
                    return true;
                }
        );
    }

    @Override
    public int getItemCount() {
        return detailConstructionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickItem {
        void onClickDetailConstruction(int pos);

        void onLongClickDetailConstruction(int pos);
    }
}
