package com.viettel.construction.screens.atemp.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.EmployeeApi;

/**
 * Created by hoang on 25/01/2018.
 */

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private List<EmployeeApi> listData;
    private LayoutInflater inflater;
    private Context context;
    private OnClickEmployeeAdapter onClickEmployeeAdapter;

    public List<EmployeeApi> getListData() {
        return listData;
    }

    public void setListData(List<EmployeeApi> listData) {
        this.listData = listData;
    }

    public EmployeeAdapter(List<EmployeeApi> listData, Context context, OnClickEmployeeAdapter onClickEmployeeAdapter) {
        this.listData = listData;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.onClickEmployeeAdapter = onClickEmployeeAdapter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_employee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EmployeeApi employee = listData.get(position);
        holder.tvName.setText(employee.getFullName());
        holder.tvOffice.setText(employee.getDepartmentName());
        holder.tvEmail.setText(employee.getEmail());
        holder.tvPhone.setText(employee.getPhoneNumber());
        holder.itemView.setTag(employee);
        holder.itemView.setOnClickListener((view) -> {
            onClickEmployeeAdapter.OnClickEmployee((EmployeeApi) view.getTag());
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_office)
        TextView tvOffice;
        @BindView(R.id.tv_email)
        TextView tvEmail;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.imv_avatar)
        CircleImageView imvAvarta;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public interface OnClickEmployeeAdapter {
        void OnClickEmployee(EmployeeApi employee);
    }
}
