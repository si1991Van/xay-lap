package com.viettel.construction.screens.menu_return_vttb;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.ConstructionMerchandiseDetailVTDTO;

import java.util.List;

public class TabSuppliesRefundAdapter extends RecyclerView.Adapter<TabSuppliesRefundAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<ConstructionMerchandiseDetailVTDTO> list;
    private ConstructionMerchandiseDetailVTDTO constructionMerchandiseDetailVTDTO;
    private String checkType;

    public TabSuppliesRefundAdapter(Context context, List<ConstructionMerchandiseDetailVTDTO> list, String checkType) {
        this.context = context;
        this.list = list;
        this.checkType = checkType;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public TabSuppliesRefundAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_supplies, parent, false);
        return new TabSuppliesRefundAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TabSuppliesRefundAdapter.ViewHolder holder, int position) {
        constructionMerchandiseDetailVTDTO = list.get(position);

        holder.tvPostion.setText((position + 1) + ".");
        holder.tvName.setText(constructionMerchandiseDetailVTDTO.getGoodsName() + " (" + constructionMerchandiseDetailVTDTO.getGoodUnitName() + ")");
        holder.tvNumber.setText(constructionMerchandiseDetailVTDTO.getQuantity() + "");
        holder.edtNumberReal.setText(constructionMerchandiseDetailVTDTO.getNumberHoanTra() + "");

        if (checkType.equals("1")) {
            holder.tvNumber.setText(constructionMerchandiseDetailVTDTO.getQuantity() + "");
            holder.edtNumberReal.setText(constructionMerchandiseDetailVTDTO.getNumberHoanTra() + "");
        } else {
            holder.tvNumber.setText(constructionMerchandiseDetailVTDTO.getQuantity() + "");
            holder.edtNumberReal.setText(constructionMerchandiseDetailVTDTO.getQuantity() + "");
        }

        if (checkType.equals("1")) {
            holder.edtNumberReal.setClickable(false);
            holder.edtNumberReal.setCursorVisible(false);
            holder.edtNumberReal.setFocusable(false);
            holder.edtNumberReal.setFocusableInTouchMode(false);
        } else {
            holder.edtNumberReal.setClickable(true);
            holder.edtNumberReal.setCursorVisible(true);
            holder.edtNumberReal.setFocusable(true);
            holder.edtNumberReal.setFocusableInTouchMode(true);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvPostion;
        private TextView tvNumber;
        private EditText edtNumberReal;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvNumber = itemView.findViewById(R.id.tv_number_1);
            tvPostion = itemView.findViewById(R.id.tv_postion);
            edtNumberReal = itemView.findViewById(R.id.edt_number_real);

            edtNumberReal.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try {
                        if (list.get(getLayoutPosition()).getQuantity() != null){
                            if (editable.toString().length() > 0 && !editable.toString().isEmpty()){
                                if (Integer.parseInt(editable.toString()) > list.get(getLayoutPosition()).getQuantity().intValue()) {
                                    edtNumberReal.setError("Vui lòng nhập lại số liệu!");
                                } else {
                                    if (editable.toString().length() > 0 && !editable.toString().isEmpty()) {
                                        list.get(getLayoutPosition()).setNumberHoanTra(
                                                Integer.parseInt(editable.toString()));
                                    }
                                }
                            }else {
                                list.get(getLayoutPosition()).setNumberHoanTra(0);
                            }
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}

