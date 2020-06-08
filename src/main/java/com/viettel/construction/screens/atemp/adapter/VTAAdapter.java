package com.viettel.construction.screens.atemp.adapter;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import com.viettel.construction.R;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailVTADTO;

public class VTAAdapter extends RecyclerView.Adapter<VTAAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<ConstructionAcceptanceCertDetailVTADTO> list;
    private ConstructionAcceptanceCertDetailVTADTO constructionAcceptanceCertDetailVTADTO;
    private String checkType;
    private final String TAG = "VTVTAAdapter";

    public VTAAdapter(Context context, List<ConstructionAcceptanceCertDetailVTADTO> list, String checkType) {
        this.context = context;
        this.list = list;
        this.checkType = checkType;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public VTAAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_supplies, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        constructionAcceptanceCertDetailVTADTO = list.get(position);

        // 0 : cho nghiem thu
        // 1 : da nghiem thu
        holder.tvPostion.setText((position + 1) + ".");
        Double quantity = constructionAcceptanceCertDetailVTADTO.getQuantity();
        int intQuantity = quantity.intValue();

        Double nghiemthu = constructionAcceptanceCertDetailVTADTO.getNumberNghiemthu();
        int intNghiemthu = nghiemthu.intValue();

        holder.tvName.setText(constructionAcceptanceCertDetailVTADTO.getGoodsName() + " (" + constructionAcceptanceCertDetailVTADTO.getGoodsUnitName() + ")");
        Log.d(TAG, "onBindViewHolder - checkType : " + checkType);
        if (intQuantity == quantity) {
            // truong hop la so chan - hien thi theo kieu integer
            holder.tvNumber.setText(intQuantity + "");
        } else {
            holder.tvNumber.setText(quantity + "");
        }

//        if (intNghiemthu == nghiemthu) {
//            holder.edtNumberReal.setText(intNghiemthu + "");
//        } else {
//            holder.edtNumberReal.setText(nghiemthu + "");
//        }

        if (checkType.equals("1")) {
//            if (intQuantity == quantity) {
//                // truong hop la so chan - hien thi theo kieu integer
//                holder.tvNumber.setText(intQuantity + "");
//            } else {
//                holder.tvNumber.setText(quantity + "");
//            }

            if (intNghiemthu == nghiemthu) {
                holder.edtNumberReal.setText(intNghiemthu + "");
            } else {
                holder.edtNumberReal.setText(nghiemthu + "");
            }
        } else {
            if (intQuantity == quantity) {
                // truong hop la so chan - hien thi theo kieu integer
               // holder.tvNumber.setText(intQuantity + "");
                holder.edtNumberReal.setText(intQuantity + "");
            } else {
               // holder.tvNumber.setText(quantity + "");
                holder.edtNumberReal.setText(quantity + "");
            }
        }

        if (checkType.equals("1")) {
            holder.edtNumberReal.setClickable(false);
            holder.edtNumberReal.setCursorVisible(false);
            holder.edtNumberReal.setFocusable(false);
            //holder.edtNumberReal.setFocusableInTouchMode(false);
        } else {
            holder.edtNumberReal.setClickable(true);
            holder.edtNumberReal.setCursorVisible(true);
            holder.edtNumberReal.setFocusable(true);
            //holder.edtNumberReal.setFocusableInTouchMode(true);
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
                        if (editable.toString().length() > 0 && !editable.toString().isEmpty()) {
                            if (Double.parseDouble(editable.toString()) > list.get(getLayoutPosition()).getQuantity()) {
                                edtNumberReal.setError("Vui lòng nhập lại số liệu!");
                            } else {
                                if (editable.toString().length() > 0 && !editable.toString().isEmpty()) {
                                    list.get(getLayoutPosition()).setNumberNghiemthu(Double.parseDouble(editable.toString()));
                                }
                            }
                        } else {
                            list.get(getLayoutPosition()).setNumberNghiemthu(Double.parseDouble("0"));
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
