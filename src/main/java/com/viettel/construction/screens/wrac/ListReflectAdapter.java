package com.viettel.construction.screens.wrac;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.viettel.construction.R;
import com.viettel.construction.model.api.issue.IssueWorkItemDTO;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Manroid on 22/01/2018.
 */

public class ListReflectAdapter extends RecyclerView.Adapter<ListReflectAdapter.ViewHolder> {

    //for load more
    private Context context;
    private List<IssueWorkItemDTO> listData;
    private IssueWorkItemDTO issueWorkItemDTO;
    private LayoutInflater inflater;
    private OnClickItem onClickItem;

    public List<IssueWorkItemDTO> getListData() {
        return listData;
    }

    public void setListData(List<IssueWorkItemDTO> listData) {
        this.listData = listData;
    }

    public ListReflectAdapter(Context context, List<IssueWorkItemDTO> listData, OnClickItem onClickItem) {
        this.context = context;
        this.listData = listData;
        inflater = LayoutInflater.from(context);
        this.onClickItem = onClickItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_reflect, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        issueWorkItemDTO = listData.get(position);

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
        holder.itemView.setOnClickListener((v)->{
            onClickItem.onClickReflect((IssueWorkItemDTO) v.getTag());
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_content)
        TextView tvContent;

        @BindView(R.id.tv_id)
        TextView tvId;

        @BindView(R.id.tv_status)
        TextView tvStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public interface OnClickItem {
        void onClickReflect(IssueWorkItemDTO data);
    }

}