package com.viettel.construction.screens.menu_construction;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ViewModelFragmentListBase;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionScheduleItemDTO;
import com.viettel.construction.model.api.ConstructionTaskDTO;

import java.util.List;

import butterknife.BindView;

public class ConstructionLevel2Adapter extends AdapterFragmentListBase<ConstructionTaskDTO, ConstructionLevel2Adapter.ConstructionLevel2VM> {


    private ConstructionScheduleItemDTO constructionScheduleItemDTO;
    private String scheduleType;

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public void setConstructionScheduleItemDTO(ConstructionScheduleItemDTO constructionScheduleItemDTO) {
        this.constructionScheduleItemDTO = constructionScheduleItemDTO;
    }

    public ConstructionLevel2Adapter(Context context, List<ConstructionTaskDTO> listData) {
        super(context, listData);
    }

    @Override
    public ConstructionLevel2VM onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_construction, parent, false);
        return new ConstructionLevel2VM(view);
    }

    @Override
    public void onBindViewHolder(ConstructionLevel2VM holder, int position) {
        int i = position + 1;
        ConstructionTaskDTO work = getListData().get(position);
        holder.txtWork.setText(context.getString(R.string.index_item_cv, work.getTaskName(), i));
        // nếu dữ liệu trả về null thì để trống
        if (work.getConstructionCode() != null) {
            holder.txtNameConstruction.setText(work.getConstructionCode() + "");
        } else {
            holder.txtNameConstruction.setText("");
        }

        if (scheduleType.equals("1")) {
            holder.txtTitleSupervisor.setText("ĐV thực hiện:");
            if (constructionScheduleItemDTO.getCatPrtName() != null) {
                holder.txtSuperviseInfo.setText(constructionScheduleItemDTO.getCatPrtName().toString());
            }
        } else if (scheduleType.equals("0")) {
            holder.txtTitleSupervisor.setText("Hạng mục:");
            holder.txtSuperviseInfo.setText(work.getWorkItemName().toString());
        }
        holder.txtDateLine.setText(work.getStartDate() + " - " + work.getEndDate());
        switch (work.getStatus()) {
            case VConstant.DID_NOT_PERFORM + "":
                holder.txtStatus.setText(context.getString(R.string.did_not_perform));
                holder.txtStatus.setTextColor(Color.parseColor("#c2c2c2"));
                break;
            case VConstant.IN_PROCESS + "":
                holder.txtStatus.setText(context.getString(R.string.in_process_1));
                holder.txtStatus.setTextColor(Color.parseColor("#ffad4e"));
                break;
            case VConstant.ON_PAUSE + "":
                holder.txtStatus.setText(context.getString(R.string.on_pause));
                holder.txtStatus.setTextColor(Color.parseColor("#ef1515"));
                break;
            case VConstant.COMPLETE + "":
                holder.txtStatus.setText(context.getString(R.string.complete1));
                holder.txtStatus.setTextColor(Color.parseColor("#25b358"));
                break;
            default:
                break;
        }
        if (work.getCompleteState() != null) {
            if (work.getCompleteState().trim().equals("2")) {
                holder.imgWarning.setVisibility(View.VISIBLE);
            } else {
                holder.imgWarning.setVisibility(View.GONE);
            }
        }
        holder.itemView.setTag(work);
        holder.itemView.setOnClickListener(view ->
                itemRecyclerviewClick.onItemRecyclerViewclick((ConstructionTaskDTO) view.getTag())
        );
    }


    class ConstructionLevel2VM extends ViewModelFragmentListBase {
        @BindView(R.id.img_warning)
        ImageView imgWarning;
        @BindView(R.id.txt_work)
        TextView txtWork;
        @BindView(R.id.txt_name_construction)
        TextView txtNameConstruction;
        @BindView(R.id.txt_dateline)
        TextView txtDateLine;

        @BindView(R.id.txt_supervise_info)
        TextView txtSuperviseInfo;
        @BindView(R.id.txt_status)
        TextView txtStatus;
        @BindView(R.id.tv_title_supervisor)
        TextView txtTitleSupervisor;

        public ConstructionLevel2VM(View itemView) {
            super(itemView);
        }
    }
}
