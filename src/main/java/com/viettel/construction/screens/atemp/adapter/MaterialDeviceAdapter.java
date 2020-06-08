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
import com.viettel.construction.model.MaterialDevice;

public class MaterialDeviceAdapter extends RecyclerView.Adapter<MaterialDeviceAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<MaterialDevice> materialDevices;
    private MaterialDevice materialDevice;

    public MaterialDeviceAdapter(Context context, List<MaterialDevice> materialDevices) {
        this.context = context;
        this.materialDevices = materialDevices;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MaterialDeviceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_material_device, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        materialDevice = materialDevices.get(position);
        holder.tvName.setText(materialDevice.getName());
        holder.tvPostion.setText(position + 1 + ".");
        holder.tvNumberReturn.setText(materialDevice.getNumberReturn() + "");
    }


    @Override
    public int getItemCount() {
        return materialDevices.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_postion)
        TextView tvPostion;
        @BindView(R.id.tv_number_return)
        TextView tvNumberReturn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
