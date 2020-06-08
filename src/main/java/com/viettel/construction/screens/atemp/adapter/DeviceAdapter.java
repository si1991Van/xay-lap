package com.viettel.construction.screens.atemp.adapter;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.viettel.construction.R;
import com.viettel.construction.model.Device;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<com.viettel.construction.model.Device> devices;
    private Device device;

    public DeviceAdapter(Context context, List<Device> devices) {
        this.context = context;
        this.devices = devices;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_device, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        device = devices.get(position);
        holder.tvPostion.setText(position + 1 + ".");
        holder.tvName.setText(device.getName1());
//        holder.tvName2.setText(device.getName2());
//        holder.tvName3.setText(device.getName3());
        holder.tvNumber.setText(device.getNumber());
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName,tvName2,tvName3;
        private TextView tvPostion;
        private TextView tvNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
//            tvName2 = itemView.findViewById(R.id.tv_name_2);
//            tvName3 = itemView.findViewById(R.id.tv_name_3);
            tvNumber = itemView.findViewById(R.id.tv_number_1);
            tvPostion = itemView.findViewById(R.id.tv_postion);

        }
    }

}
