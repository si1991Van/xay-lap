package com.viettel.construction.screens.atemp.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.viettel.construction.R;
import com.viettel.construction.model.api.ConstructionStationWorkItem;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<ConstructionStationWorkItem> listData;
    private OnClickItemAdapter onClickItemAdapter;

    public List<ConstructionStationWorkItem> getListData() {
        return listData;
    }

    public void setListData(List<ConstructionStationWorkItem> listData) {
        this.listData = listData;
    }

    public ItemAdapter(Context context, List<ConstructionStationWorkItem> listData, OnClickItemAdapter onClickItemAdapter) {
        this.context = context;
        this.listData = listData;
        inflater = LayoutInflater.from(context);
        this.onClickItemAdapter = onClickItemAdapter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ConstructionStationWorkItem item = listData.get(position);
        holder.txtName.setText(item.getName());
        holder.itemView.setTag(item);

        holder.itemView.setOnClickListener((view) -> {
            onClickItemAdapter.OnClickItemAdapter((ConstructionStationWorkItem) view.getTag());
        });

    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_name)
        TextView txtName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickItemAdapter {
        void OnClickItemAdapter(ConstructionStationWorkItem item);
    }
}

