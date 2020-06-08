package com.viettel.construction.screens.atemp.adapter;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.viettel.construction.R;
import com.viettel.construction.model.MaterialB;

public class MaterialBAdapter extends RecyclerView.Adapter<MaterialBAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<MaterialB> materials;
    private MaterialB material;

    public MaterialBAdapter(Context context, List<MaterialB> materials) {
        this.context = context;
        this.materials = materials;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MaterialBAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_acceptance_material_b, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        material = materials.get(position);
        holder.tvNumber.setText(position + 1 + ".");
        holder.tvName.setText(material.getName());


    }

    @Override
    public int getItemCount() {
        return materials.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvNumber;


        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvNumber = itemView.findViewById(R.id.tv_number);
        }

    }
}