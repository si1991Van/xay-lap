package com.viettel.construction.screens.menu_construction;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterExpandableListBase;
import com.viettel.construction.appbase.ExpandableListModel;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionTaskDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConstructionExpandableAdapter
        extends AdapterExpandableListBase<String, ConstructionTaskDTO> {

    private LayoutInflater inflater;

    private boolean isSupervise;

    public ConstructionExpandableAdapter(List<ExpandableListModel<String, ConstructionTaskDTO>> listData,
                                         Context context, boolean isSupervise) {
        super(listData, context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.isSupervise = isSupervise;
    }

    @Override
    public View createGroupView(int groupPosition, boolean isExpand, View view, ViewGroup viewGroup) {
        View header = inflater.inflate(R.layout.header_expandable_item, null);
        TextView txtHeader = header.findViewById(R.id.txtHeader);
        if (isExpand) {
            txtHeader.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.collapse, 0);
        } else {
            txtHeader.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.expand, 0);
        }
        txtHeader.setText("Công trình: " + getGroup(groupPosition));
        return header;
    }

    @Override
    public View createChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        ConstructionExpandableVM itemViewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_dashboard_cv, null);
        }
        itemViewHolder = new ConstructionExpandableVM(view);
        //
        int i = childPosition + 1;
        ConstructionTaskDTO work = getChild(groupPosition,childPosition);
        itemViewHolder.txtWork.setText(context.getString(R.string.index_item_cv, work.getTaskName(), i));
        if (work.getWorkItemName() != null) {
            itemViewHolder.txtCategory.setText(work.getWorkItemName() + "");
        } else {
            itemViewHolder.txtCategory.setText("");
        }
        if (work.getConstructionCode() != null) {
            itemViewHolder.txtNameConstruction.setText(work.getConstructionCode() + "");
        } else {
            itemViewHolder.txtNameConstruction.setText("");
        }

        itemViewHolder.txtDateLine.setText(work.getStartDate() + " - " + work.getEndDate());
        if (isSupervise) {
            itemViewHolder.txtSuperviseInfo.setVisibility(View.VISIBLE);
            itemViewHolder.txtTitleSupervisor.setVisibility(View.VISIBLE);
            if (work.getPerformerName() != null) {
                itemViewHolder.txtSuperviseInfo.setText(work.getPerformerName() + "");
            } else {
                itemViewHolder.txtSuperviseInfo.setText("");
            }
        } else {
            itemViewHolder.txtSuperviseInfo.setVisibility(View.GONE);
            itemViewHolder.txtTitleSupervisor.setVisibility(View.GONE);

        }
        switch (work.getStatus()) {
            case VConstant.DID_NOT_PERFORM + "":
                itemViewHolder.txtStatus.setText(context.getString(R.string.did_not_perform));
                itemViewHolder.txtStatus.setTextColor(Color.parseColor("#c2c2c2"));
                break;
            case VConstant.IN_PROCESS + "":
                itemViewHolder.txtStatus.setText(context.getString(R.string.in_process_1));
                itemViewHolder.txtStatus.setTextColor(Color.parseColor("#ffad4e"));
                break;
            case VConstant.ON_PAUSE + "":
                itemViewHolder.txtStatus.setText(context.getString(R.string.on_pause));
                itemViewHolder.txtStatus.setTextColor(Color.parseColor("#ef1515"));
                break;
            case VConstant.COMPLETE + "":
                itemViewHolder.txtStatus.setText(context.getString(R.string.complete1));
                itemViewHolder.txtStatus.setTextColor(Color.parseColor("#25b358"));
                break;
            default:
                break;
        }
        if (work.getCompleteState() != null) {
            if (work.getCompleteState().trim().equals("2")) {
                itemViewHolder.imgWarning.setVisibility(View.VISIBLE);
            } else {
                itemViewHolder.imgWarning.setVisibility(View.GONE);
            }
        }
        view.setTag(work);
        view.setOnClickListener(v->{
            itemRecyclerviewClick.onItemRecyclerViewclick((ConstructionTaskDTO) v.getTag());
        });

        return view;
    }


     class ConstructionExpandableVM {
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

        public ConstructionExpandableVM(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
