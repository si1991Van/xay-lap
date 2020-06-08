package com.viettel.construction.screens.atemp.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import com.viettel.construction.R;
import com.viettel.construction.common.FilterableSection;
import com.viettel.construction.common.VConstant;
import com.viettel.construction.model.api.ConstructionTaskDTO;

/**
 * Created by Ramona on 1/30/2018.
 */

public class AdapterHeaderRV extends StatelessSection implements FilterableSection {
    private Context mContext;
    private String mTitle;
    private List<ConstructionTaskDTO> mList;
    private List<ConstructionTaskDTO> mFilteredList;
    private OnClickHeader onClickHeader;
    private boolean isSupervise;

    public AdapterHeaderRV(OnClickHeader onClickHeader, String mTitle, List<ConstructionTaskDTO> mList, Context mContext, boolean isSupervise) {
        super(new SectionParameters.Builder(R.layout.item_dashboard_cv)
                .headerResourceId(R.layout.header_item)
                .build());
        this.mTitle = mTitle;
        this.mList = mList;
        this.onClickHeader = onClickHeader;
        this.mContext = mContext;
        this.isSupervise = isSupervise;
        this.mFilteredList = new ArrayList<>(mList);

    }

    @Override
    public int getContentItemsTotal() {
        return mFilteredList.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        int i = position + 1;
        ConstructionTaskDTO work = mFilteredList.get(position);
        itemViewHolder.txtWork.setText(mContext.getString(R.string.index_item_cv, work.getTaskName(), i));
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
                itemViewHolder.txtStatus.setText(mContext.getString(R.string.did_not_perform));
                itemViewHolder.txtStatus.setTextColor(Color.parseColor("#c2c2c2"));
                break;
            case VConstant.IN_PROCESS + "":
                itemViewHolder.txtStatus.setText(mContext.getString(R.string.in_process_1));
                itemViewHolder.txtStatus.setTextColor(Color.parseColor("#ffad4e"));
                break;
            case VConstant.ON_PAUSE + "":
                itemViewHolder.txtStatus.setText(mContext.getString(R.string.on_pause));
                itemViewHolder.txtStatus.setTextColor(Color.parseColor("#ef1515"));
                break;
            case VConstant.COMPLETE + "":
                itemViewHolder.txtStatus.setText(mContext.getString(R.string.complete1));
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
        itemViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHeader.OnClickHeader(mFilteredList.get(position));
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        headerViewHolder.tvTitle.setText(mContext.getString(R.string.header_name_construction, mTitle));
    }

    @Override
    public void filter(String query) {
        String temp = query.trim();
        if (TextUtils.isEmpty(temp)) {
            mFilteredList = new ArrayList<>(mList);
            this.setVisible(true);
        } else {
            mFilteredList.clear();
            String input = removeAccent(temp).toUpperCase();
            for (ConstructionTaskDTO entity : mList) {
                if (entity.getTaskName() != null && entity.getConstructionCode() != null) {
                    String taskName = removeAccent(entity.getTaskName()).toUpperCase().trim();
                    String constructionCode;
                    if (entity.getConstructionCode() != null) {
                        constructionCode = entity.getConstructionCode().toUpperCase().trim();
                    } else {
                        constructionCode = "";
                    }
                    if (taskName.contains(input) || constructionCode.contains(input)) {
                        mFilteredList.add(entity);
                    }
                }
            }
            this.setVisible(!mFilteredList.isEmpty());
        }
    }

    public String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_header_title)
        TextView tvTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
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
        private final View rootView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickHeader {
        void OnClickHeader(ConstructionTaskDTO work);
    }
}
