package com.viettel.construction.screens.tabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ViewModelFragmentListBase;
import com.viettel.construction.model.api.issue.IssueWorkItemDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IssueListAdapter extends AdapterFragmentListBase<IssueWorkItemDTO, IssueListAdapter.IssueListVM> {

    public IssueListAdapter(Context context, List<IssueWorkItemDTO> listData) {
        super(context, listData);
    }

    @Override
    public IssueListVM onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reflect, parent, false);
        return new IssueListVM(view);
    }

    @Override
    public void onBindViewHolder(IssueListVM holder, int position) {
        IssueWorkItemDTO issueWorkItemDTO = getListData().get(position);

        if (issueWorkItemDTO.getStatus().equals("1")) {
            holder.tvStatus.setText(R.string.open);
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.gray));
        } else if (issueWorkItemDTO.getStatus().equals("0")) {
            holder.tvStatus.setText(R.string.close);
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.c30));
        }
        holder.tvContent.setText(issueWorkItemDTO.getContent());
        holder.tvId.setText(issueWorkItemDTO.getCode() + "");
        holder.itemView.setTag(issueWorkItemDTO);
        holder.itemView.setOnClickListener((v) -> {
            itemRecyclerviewClick.onItemRecyclerViewclick((IssueWorkItemDTO) v.getTag());
        });
    }

    class IssueListVM extends ViewModelFragmentListBase {
        @BindView(R.id.tv_content)
        TextView tvContent;

        @BindView(R.id.tv_id)
        TextView tvId;

        @BindView(R.id.tv_status)
        TextView tvStatus;

        public IssueListVM(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
