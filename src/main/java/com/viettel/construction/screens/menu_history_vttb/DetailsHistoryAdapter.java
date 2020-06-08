package com.viettel.construction.screens.menu_history_vttb;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.history.StTransactionDetailDTO;

import java.util.List;

/**
 * Created by manro on 4/7/2018.
 */

public class DetailsHistoryAdapter extends RecyclerView.Adapter<DetailsHistoryAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<StTransactionDetailDTO> list;

    public DetailsHistoryAdapter(Context context, List<StTransactionDetailDTO> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_details_implement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        StTransactionDetailDTO stTransactionDetailDTO = list.get(position);
//        int pos = position + 1;
//        holder.tvPos.setText(context.getString(R.string.detail_impelent_pos, pos));
        if (stTransactionDetailDTO.getDetailQuantity() != null)
            holder.tvAmount.setText(stTransactionDetailDTO.getDetailQuantity().replace(".0",""));
        if (stTransactionDetailDTO.getDetailSerial() != null)
            holder.tvSerial.setText(stTransactionDetailDTO.getDetailSerial());
        if (stTransactionDetailDTO.getDetailCntContractCode() != null)
            holder.tvId.setText(stTransactionDetailDTO.getDetailCntContractCode());
        if (stTransactionDetailDTO.getDetailPartNumber() != null)
            holder.tvPartNumber.setText(stTransactionDetailDTO.getDetailPartNumber());
        if (stTransactionDetailDTO.getDetailMerManufacturer() != null)
            holder.tvManufacturer.setText(stTransactionDetailDTO.getDetailMerManufacturer());
        if (stTransactionDetailDTO.getDetailProducingCountryName() != null)
            holder.tvCountryProduce.setText(stTransactionDetailDTO.getDetailProducingCountryName());
    }

    @Override
    public int getItemCount() {
        return list.size();
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
