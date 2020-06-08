package com.viettel.construction.screens.atemp.adapter;


import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailVTBDTO;
import com.viettel.construction.model.api.acceptance.ConstructionAcceptanceCertItemTBDTO;

import java.util.ArrayList;
import java.util.List;

public class DeviceBAdapter extends RecyclerView.Adapter<DeviceBAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<ConstructionAcceptanceCertDetailVTBDTO> list;
    private ConstructionAcceptanceCertDetailVTBDTO constructionAcceptanceCertDetailVTBDTO;
    private String checkType;
    private List<ConstructionAcceptanceCertItemTBDTO> listItem = new ArrayList<>();

    public DeviceBAdapter(Context context, List<ConstructionAcceptanceCertDetailVTBDTO> list, String checkType) {
        this.context = context;
        this.list = list;
        this.checkType = checkType;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public DeviceBAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_device, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        constructionAcceptanceCertDetailVTBDTO = list.get(position);

        if (constructionAcceptanceCertDetailVTBDTO.getTotalItemDetail().equals("0"))
            holder.lnContent.setVisibility(View.GONE);
        else
            holder.lnContent.setVisibility(View.VISIBLE);

        holder.tvPostion.setText((position + 1) + ".");
        if (constructionAcceptanceCertDetailVTBDTO.getGoodsName() != null)
            holder.tvName.setText(constructionAcceptanceCertDetailVTBDTO.getGoodsName());
        if (constructionAcceptanceCertDetailVTBDTO.getGoodsCode() != null)
            holder.tvCode.setText(constructionAcceptanceCertDetailVTBDTO.getGoodsCode());
        if (constructionAcceptanceCertDetailVTBDTO.getTotalItemDetail() != null)
            holder.tvToal.setText(constructionAcceptanceCertDetailVTBDTO.getTotalItemDetail());


        //check detail item for checked or not check
        if (!checkType.equals("1")){
            listItem = list.get(position).getListTBBDetail();

            for (ConstructionAcceptanceCertItemTBDTO item: listItem) {
                item.setEmployTB(1);
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.rcvItem.setLayoutManager(linearLayoutManager);

        ItemDeviceBAdapter itemDeviceBAdapter = new ItemDeviceBAdapter(context, listItem, checkType);
        holder.rcvItem.setAdapter(itemDeviceBAdapter);
        RecyclerView.OnItemTouchListener mScrollTouchListener = new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        };

        holder.rcvItem.addOnItemTouchListener(mScrollTouchListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvCode;
        private TextView tvPostion;
        private TextView tvToal;
        private RecyclerView rcvItem;
        private LinearLayout lnContent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvCode = itemView.findViewById(R.id.tv_code);
            tvPostion = itemView.findViewById(R.id.tv_postion);
            tvToal = itemView.findViewById(R.id.tv_number);
            rcvItem = itemView.findViewById(R.id.rcv_item);
            lnContent = itemView.findViewById(R.id.ln_content);
        }
    }
}
