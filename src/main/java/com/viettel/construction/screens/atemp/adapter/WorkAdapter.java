package com.viettel.construction.screens.atemp.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.viettel.construction.R;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionTaskDTO;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<ConstructionTaskDTO> listData;


    private OnClickViewDetails onClickViewDetails;
    private boolean isSupervise;

    public List<ConstructionTaskDTO> getListData() {
        return listData;
    }

    public void setListData(List<ConstructionTaskDTO> listData) {
        this.listData = listData;
    }

    public WorkAdapter(Context context, List<ConstructionTaskDTO> listWork, OnClickViewDetails onClickViewDetails, boolean isSupervise) {
        this.context = context;
        this.listData = listWork;
        this.isSupervise = isSupervise;
        this.onClickViewDetails = onClickViewDetails;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_dashboard_cv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int i = position + 1;
        ConstructionTaskDTO work = listData.get(position);
        holder.txtWork.setText(context.getString(R.string.index_item_cv, work.getTaskName(), i));
        // nếu dữ liệu trả về null thì để trống
        if (work.getWorkItemName() != null) {
            holder.txtCategory.setText(work.getWorkItemName() + "");
        } else {
            holder.txtCategory.setText("");
        }
        // nếu dữ liệu trả về null thì để trống
        if (work.getConstructionCode() != null) {
            holder.txtNameConstruction.setText(work.getConstructionCode() + "");
        } else {
            holder.txtNameConstruction.setText("");
        }
        if (isSupervise) {
            holder.txtSuperviseInfo.setVisibility(View.VISIBLE);
            holder.txtTitleSupervisor.setVisibility(View.VISIBLE);
            if (work.getPerformerName() != null) {
                holder.txtSuperviseInfo.setText(work.getPerformerName() + "");
            } else {
                holder.txtSuperviseInfo.setText("");
            }
        } else {
            holder.txtSuperviseInfo.setVisibility(View.GONE);
            holder.txtTitleSupervisor.setVisibility(View.GONE);

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
            if (work.getCompleteState().equals("2")) {
                holder.imgWarning.setVisibility(View.VISIBLE);
            } else {
                holder.imgWarning.setVisibility(View.GONE);
            }
        }

        holder.itemView.setTag(work);
        holder.itemView.setOnClickListener((view)->
            onClickViewDetails.OnClickItem((ConstructionTaskDTO) view.getTag())
        );

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_warning)
        ImageView imgWarning;
        @BindView(R.id.txt_work)
        TextView txtWork;
        @BindView(R.id.txt_name_construction)
        TextView txtNameConstruction;
        @BindView(R.id.txt_dateline)
        TextView txtDateLine;
        @BindView(R.id.txt_category)
        TextView txtCategory;
        @BindView(R.id.txt_supervise_info)
        TextView txtSuperviseInfo;
        @BindView(R.id.txt_status)
        TextView txtStatus;
        @BindView(R.id.tv_title_supervisor)
        TextView txtTitleSupervisor;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }


    public interface OnClickViewDetails {
        void OnClickItem(ConstructionTaskDTO work);
    }
}
