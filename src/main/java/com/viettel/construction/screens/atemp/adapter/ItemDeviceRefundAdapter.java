package com.viettel.construction.screens.atemp.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.ConstructionMerchandiseItemTBDTO;

import java.util.List;

public class ItemDeviceRefundAdapter extends RecyclerView.Adapter<ItemDeviceRefundAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private String checkType;
    private List<ConstructionMerchandiseItemTBDTO> list;
    private ConstructionMerchandiseItemTBDTO constructionMerchandiseItemTBDTO;

    public ItemDeviceRefundAdapter(Context context, List<ConstructionMerchandiseItemTBDTO> list, String checkType) {
        this.context = context;
        this.list = list;
        this.checkType = checkType;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ItemDeviceRefundAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_item, parent, false);
        return new ItemDeviceRefundAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ItemDeviceRefundAdapter.ViewHolder holder, int position) {
        constructionMerchandiseItemTBDTO = list.get(position);

        if (constructionMerchandiseItemTBDTO.getSerial() != null)
            holder.tvConsCode.setText(constructionMerchandiseItemTBDTO.getSerial());

        if (checkType != null) {
            if (checkType.equals("1")) {
                holder.checkBox.setEnabled(false);
                if (constructionMerchandiseItemTBDTO.getNumberHTTB() != null) {

                    if (constructionMerchandiseItemTBDTO.getNumberHTTB() != null){
                        Double employ = constructionMerchandiseItemTBDTO.getNumberHTTB();


                        if (employ == 1) {
                            holder.checkBox.setChecked(true);
                        } else {
                            holder.checkBox.setChecked(false);
                        }
                    }

                }
            } else {

                holder.checkBox.setEnabled(true);

                if (constructionMerchandiseItemTBDTO.getEmployTB() != null){

                    if (constructionMerchandiseItemTBDTO.getEmployTB() == 1)
                        holder.checkBox.setChecked(true);
                    else
                        holder.checkBox.setChecked(false);

                }



            }
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvConsCode;
        private CheckBox checkBox;


        public ViewHolder(View itemView) {
            super(itemView);
            tvConsCode = itemView.findViewById(R.id.tv_cons_code);
            checkBox = itemView.findViewById(R.id.chk_id_cons);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (compoundButton.isChecked()) {
                        list.get(getLayoutPosition()).setEmployTB(1);
                    } else {
                        list.get(getLayoutPosition()).setEmployTB(0);
                    }
                }
            });

        }
    }
}
