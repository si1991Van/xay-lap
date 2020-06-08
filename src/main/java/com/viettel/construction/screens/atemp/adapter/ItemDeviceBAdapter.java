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
import com.viettel.construction.model.api.acceptance.ConstructionAcceptanceCertItemTBDTO;

import java.util.List;

public class ItemDeviceBAdapter extends RecyclerView.Adapter<ItemDeviceBAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private String checkType;
    private List<ConstructionAcceptanceCertItemTBDTO> list;
    private ConstructionAcceptanceCertItemTBDTO constructionAcceptanceCertItemTBDTO;

    public ItemDeviceBAdapter(Context context, List<ConstructionAcceptanceCertItemTBDTO> list, String checkType) {
        this.context = context;
        this.list = list;
        this.checkType = checkType;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ItemDeviceBAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_item, parent, false);
        return new ItemDeviceBAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ItemDeviceBAdapter.ViewHolder holder, int position) {
        constructionAcceptanceCertItemTBDTO = list.get(position);

        if (constructionAcceptanceCertItemTBDTO.getSerial() != null)
            holder.tvConsCode.setText(constructionAcceptanceCertItemTBDTO.getSerial());

        if (checkType != null) {
            if (checkType.equals("1")) {
                holder.checkBox.setEnabled(false);
                if (constructionAcceptanceCertItemTBDTO.getNumberSDTB() != null) {
                    Integer employ = constructionAcceptanceCertItemTBDTO.getNumberSDTB();

                    if (employ == 1) {
                        holder.checkBox.setChecked(true);
                    } else {
                        holder.checkBox.setChecked(false);
                    }

                }
            } else {


                if (constructionAcceptanceCertItemTBDTO.getEmployTB() == 1)
                    holder.checkBox.setChecked(true);
                else
                    holder.checkBox.setChecked(false);

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
