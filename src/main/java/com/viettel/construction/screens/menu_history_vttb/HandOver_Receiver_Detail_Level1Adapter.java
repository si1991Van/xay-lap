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

public class HandOver_Receiver_Detail_Level1Adapter extends RecyclerView.Adapter<HandOver_Receiver_Detail_Level1Adapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<StTransactionDetailDTO> list;
    private StTransactionDetailDTO stTransactionDetailDTO;
    private OnClickHistory onClickHistory;

    public HandOver_Receiver_Detail_Level1Adapter(Context context, List<StTransactionDetailDTO> list, OnClickHistory onClickHistory) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        this.onClickHistory = onClickHistory;
    }

    @Override
    public HandOver_Receiver_Detail_Level1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_child_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        stTransactionDetailDTO = list.get(position);
        if (list.size() > 0)
            holder.tvPosition.setText((position + 1 + "."));
        if (stTransactionDetailDTO.getGoodName() != null)
            holder.tvName.setText(stTransactionDetailDTO.getGoodName());
        if (stTransactionDetailDTO.getGoodUnitName() != null)
            holder.tvUnit.setText(" (" + stTransactionDetailDTO.getGoodUnitName()+ ")");
        if (stTransactionDetailDTO.getAmountReal() != null)
            holder.tvNumber.setText((stTransactionDetailDTO.getAmountReal() + "").replace(".0",""));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvNumber, tvPosition, tvUnit;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.txt_name);
            tvNumber = itemView.findViewById(R.id.txt_number);
            tvPosition = itemView.findViewById(R.id.txt_postion);
            tvUnit = itemView.findViewById(R.id.txt_unit);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickHistory.onClick(list.get(getLayoutPosition()));
                }
            });

        }
    }

    public interface OnClickHistory {
        void onClick(StTransactionDetailDTO stTransactionDetailDTO);
    }

}

