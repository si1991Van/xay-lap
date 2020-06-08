package com.viettel.construction.screens.wrac;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.viettel.construction.R;
import com.viettel.construction.model.api.WorkItemDetailDTO;
import com.viettel.construction.screens.tabs.CompleteCategoryCameraActivity;

/**
 * Created by Manroid on 22/01/2018.
 */

public class CompleteCategoryAdapter extends RecyclerView.Adapter<CompleteCategoryAdapter.ViewHolder>
         {

    private Context context;

    private WorkItemDetailDTO category;
    private List<WorkItemDetailDTO> listData;

    public List<WorkItemDetailDTO> getListData() {
        return listData;
    }

    public void setListData(List<WorkItemDetailDTO> listData) {
        this.listData = listData;
    }

    public CompleteCategoryAdapter(Context context, List<WorkItemDetailDTO> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category_comlete, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (listData != null) {
            category = listData.get(position);
            int index = position + 1;
            holder.txtIndex.setText(context.getString(R.string.index_construction_code, index));
            holder.txtCode.setText(category.getConstructionCode());
            holder.txtName.setText(category.getName());
            String startDate, endDate;

            if (category.getStartingDate() != null) {

                startDate = changeStringFormat(category.getStartingDate());
                Log.e("Check", category.getStartingDate());

            } else {
                startDate = "";
            }


            if (category.getCompleteDate() != null) {

                endDate = changeStringFormat(category.getCompleteDate());
            } else {
                endDate = "";
            }

            holder.txtTime.setText(context.getString(R.string.start_date_end_date, startDate, endDate));
            switch (category.getStatus()) {
                case "3":
                    holder.txtStatus.setText(context.getResources().getString(R.string.confirm_category));
                    holder.txtStatus.setTextColor(Color.parseColor("#50b0ec"));
                    break;
                case "2":
                    holder.txtStatus.setText(context.getResources().getString(R.string.dont_complete));
                    holder.txtStatus.setTextColor(Color.BLACK);
                    break;
                default:
                    break;
            }

            holder.itemView.setTag(category);
            holder.itemView.setOnClickListener((v)->{
                WorkItemDetailDTO workItemDetailDTO = (WorkItemDetailDTO) v.getTag();
                Intent intent = new Intent(context, CompleteCategoryCameraActivity.class);
                intent.putExtra("workDTO_data", workItemDetailDTO);
                context.startActivity(intent);
            });
        }
    }

    private String changeStringFormat(String s) {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fromUser1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fromUser2 = new SimpleDateFormat("yyyy-MM-dd");
        String reformattedStr;
        try {
            reformattedStr = myFormat.format(s.contains("-")? fromUser2.parse(s):fromUser1.parse(s));
        } catch (ParseException e) {
            e.printStackTrace();
            reformattedStr = "";
        }
        return reformattedStr;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.index_and_construction_code)
        TextView txtIndex;
        @BindView(R.id.txt_id_work)
        TextView txtCode;
        @BindView(R.id.txt_category)
        TextView txtName;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.txt_status)
        TextView txtStatus;



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            txtStatus.setSelected(true);
            txtName.setSelected(true);
        }
    }

}