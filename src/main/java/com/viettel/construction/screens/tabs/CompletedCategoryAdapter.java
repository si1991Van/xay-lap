package com.viettel.construction.screens.tabs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.appbase.AdapterFragmentListBase;
import com.viettel.construction.appbase.ViewModelFragmentListBase;
import com.viettel.construction.model.api.WorkItemDetailDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;

public class CompletedCategoryAdapter
        extends AdapterFragmentListBase<WorkItemDetailDTO, CompletedCategoryAdapter.CompletedCategoryVM> {

    public CompletedCategoryAdapter(Context context, List<WorkItemDetailDTO> listData) {
        super(context, listData);
    }

    @Override
    public void onBindViewHolder(CompletedCategoryVM holder, int position) {
        WorkItemDetailDTO category = getListData().get(position);
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


        if (category.getEndingDate() != null) {

            endDate = changeStringFormat(category.getEndingDate());
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
        holder.itemView.setOnClickListener((v) -> {
            try {
                itemRecyclerviewClick.onItemRecyclerViewclick((WorkItemDetailDTO) v.getTag());

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public CompletedCategoryVM onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category_comlete, parent, false);
        return new CompletedCategoryVM(view);
    }

    class CompletedCategoryVM extends ViewModelFragmentListBase {

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

        public CompletedCategoryVM(View itemView) {
            super(itemView);
        }

    }

    private String changeStringFormat(String s) {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fromUser1 = new SimpleDateFormat("yyyy/MM/dd");
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
}
