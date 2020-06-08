package com.viettel.construction.screens.menu_ex_warehouse;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.viettel.construction.R;
import com.viettel.construction.model.api.MerEntityDTO;

/**
 * Created by manro on 4/7/2018.
 */

public class InventoryDetailAdapter extends RecyclerView.Adapter<InventoryDetailAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<MerEntityDTO> detailsImplementList;

    public InventoryDetailAdapter(Context context, List<MerEntityDTO> detailsImplementList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.detailsImplementList = detailsImplementList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_details_implement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MerEntityDTO merEntityDTO = detailsImplementList.get(position);
        int pos = position + 1;
        holder.tvPos.setText(context.getString(R.string.detail_impelent_pos, pos));
        if (merEntityDTO.getQuantity() != null)
            holder.tvAmount.setText(merEntityDTO.getQuantity());
        if (merEntityDTO.getManufactureName() != null)
            holder.tvManufacturer.setText(merEntityDTO.getManufactureName());
        if (merEntityDTO.getProductionCountryName() != null)
            holder.tvCountryProduce.setText(merEntityDTO.getProductionCountryName());
        if (merEntityDTO.getPartNumber() != null)
            holder.tvPartNumber.setText(merEntityDTO.getPartNumber() + "");
        if (merEntityDTO.getSerial() != null)
            holder.tvSerial.setText(merEntityDTO.getSerial());
        if (merEntityDTO.getCntConstractCode() != null)
            holder.tvId.setText(merEntityDTO.getCntConstractCode());
    }

    @Override
    public int getItemCount() {
        return detailsImplementList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvAmount;
        private TextView tvId;
        private TextView tvSerial;
        private TextView tvPartNumber;
        private TextView tvCountryProduce;
        private TextView tvManufacturer;
        private TextView tvPos;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            tvId = itemView.findViewById(R.id.tv_id);
            tvSerial = itemView.findViewById(R.id.tv_serial);
            tvPartNumber = itemView.findViewById(R.id.tv_part_number);
            tvCountryProduce = itemView.findViewById(R.id.tv_country_produce);
            tvManufacturer = itemView.findViewById(R.id.tv_manufacturer);
            tvPos = itemView.findViewById(R.id.tv_pos);
        }
    }

}
