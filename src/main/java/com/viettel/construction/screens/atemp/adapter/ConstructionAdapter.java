package com.viettel.construction.screens.atemp.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.ConstructionStationWorkItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConstructionAdapter extends
        RecyclerView.Adapter<ConstructionAdapter.ViewHolder> {
    private Context context;
    private List<ConstructionStationWorkItem> listData;
    private OnClickConstruction onClickConstruction;

    public List<ConstructionStationWorkItem> getListData() {
        return listData;
    }

    public void setListData(List<ConstructionStationWorkItem> listData) {
        this.listData = listData;
    }

    public ConstructionAdapter(Context context,
                               List<ConstructionStationWorkItem> listData, OnClickConstruction onClickConstruction) {
        this.context = context;
        this.listData = listData;
        this.onClickConstruction = onClickConstruction;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_construction, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ConstructionStationWorkItem workItem = listData.get(position);
        holder.tvName.setText(workItem.getConstructionCode());
        holder.tvAddress.setText(workItem.getName());

        holder.lnConstruction.setTag(workItem);
        holder.lnConstruction.setOnClickListener((view) ->
                onClickConstruction.OnClickConstruction((ConstructionStationWorkItem) view.getTag())
        );

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.ln_construction)
        LinearLayout lnConstruction;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickConstruction {
        void OnClickConstruction(ConstructionStationWorkItem workItem);
    }

}
