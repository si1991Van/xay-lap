package com.viettel.construction.screens.atemp.adapter;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.viettel.construction.R;
import com.viettel.construction.model.api.version.AppParamDTO;
import java.util.List;

public class ConstructionSpnAdapter extends RecyclerView.Adapter<ConstructionSpnAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<AppParamDTO> listAppParam;
    private AppParamDTO appParamDTO;
    private OnClickParams onClickParams;

    private boolean workItemFinished = false;

    public ConstructionSpnAdapter(Context context, boolean workItemFinished, List<AppParamDTO> listAppParam, OnClickParams onClickParams) {
        this.context = context;
        this.listAppParam = listAppParam;
        this.onClickParams = onClickParams;
        inflater = LayoutInflater.from(context);
        this.workItemFinished = workItemFinished;
    }

    @Override
    public ConstructionSpnAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_construction_spn, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        appParamDTO = listAppParam.get(position);
        holder.tvPostion.setText(position + 1 + ".");

        if (appParamDTO.getConfirm() != null || appParamDTO.getConfirm().isEmpty()){
            if (appParamDTO.getConfirm().equals("0") || appParamDTO.getConfirm().isEmpty()){
                holder.edtNumber.setClickable(true);
                holder.edtNumber.setCursorVisible(true);
                holder.edtNumber.setFocusable(true);
                holder.edtNumber.setFocusableInTouchMode(true);
                holder.btnDelete.setVisibility(View.VISIBLE);
            }else {
                //Các hình thức thi công đã phê duyệt hoặc từ chối đều bị đóng lại
                holder.edtNumber.setClickable(false);
                holder.edtNumber.setCursorVisible(false);
                holder.edtNumber.setFocusable(false);
                holder.edtNumber.setFocusableInTouchMode(false);
                holder.btnDelete.setVisibility(View.INVISIBLE);
            }
        }
        if (workItemFinished){
            holder.edtNumber.setEnabled(false);
            holder.btnDelete.setVisibility(View.GONE);
        }

        if (appParamDTO.getName() != null)
            holder.tvName.setText(appParamDTO.getName());
        if (appParamDTO.getAmount() != null)
            holder.edtNumber.setText((appParamDTO.getAmount() + "").replace(".0", ""));
    }

    @Override
    public int getItemCount() {
        return listAppParam.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvPostion;
        private EditText edtNumber;
        private ImageView btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            edtNumber = itemView.findViewById(R.id.edt_number);
            tvPostion = itemView.findViewById(R.id.tv_postion);
            btnDelete = itemView.findViewById(R.id.btn_delete);

            edtNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try {
                        listAppParam.get(getLayoutPosition()).setAmount(editable.toString());
                        onClickParams.onChangeValue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickParams.onClick(getLayoutPosition());
                }
            });
        }
    }

    public interface OnClickParams {
        void onClick(int pos);
        void onChangeValue();
    }

}
