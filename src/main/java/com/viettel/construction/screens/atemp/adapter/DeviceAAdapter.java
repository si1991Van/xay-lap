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
import com.viettel.construction.model.api.ConstructionAcceptanceCertDetailVTADTO;
import com.viettel.construction.model.api.acceptance.ConstructionAcceptanceCertItemTBDTO;

import java.util.ArrayList;
import java.util.List;

public class DeviceAAdapter extends RecyclerView.Adapter<DeviceAAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<ConstructionAcceptanceCertDetailVTADTO> list;
    private ConstructionAcceptanceCertDetailVTADTO constructionAcceptanceCertDetailVTADTO;
    private String checkType;
    private List<ConstructionAcceptanceCertItemTBDTO> listItem = new ArrayList<>();

    public DeviceAAdapter(Context context, List<ConstructionAcceptanceCertDetailVTADTO> list, String checkType) {
        this.context = context;
        this.list = list;
        this.checkType = checkType;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public DeviceAAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_device, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        constructionAcceptanceCertDetailVTADTO = list.get(position);

        if (constructionAcceptanceCertDetailVTADTO.getTotalItemDetail().equals("0"))
            holder.lnContent.setVisibility(View.GONE);
        else
            holder.lnContent.setVisibility(View.VISIBLE);

        holder.tvPostion.setText((position + 1) + ".");
        if (constructionAcceptanceCertDetailVTADTO.getGoodsName() != null)
            holder.tvName.setText(constructionAcceptanceCertDetailVTADTO.getGoodsName());
        if (constructionAcceptanceCertDetailVTADTO.getGoodsCode() != null)
            holder.tvCode.setText(constructionAcceptanceCertDetailVTADTO.getGoodsCode());
        if (constructionAcceptanceCertDetailVTADTO.getTotalItemDetail() != null)
            holder.tvToal.setText(constructionAcceptanceCertDetailVTADTO.getTotalItemDetail());


        //check detail item for checked or not check
//        if (!checkType.equals("1")){
//            listItem = list.get(position).getListTBADetail();
//
//            for (ConstructionAcceptanceCertItemTBDTO item: listItem) {
//                item.setEmployTB(1);
//            }
//        }

        listItem = list.get(position).getListTBADetail();

        for (ConstructionAcceptanceCertItemTBDTO item: listItem) {
            item.setEmployTB(1);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.rcvItem.setLayoutManager(linearLayoutManager);

        ItemDeviceAAdapter itemDeviceAAdapter = new ItemDeviceAAdapter(context, listItem, checkType);
        holder.rcvItem.setAdapter(itemDeviceAAdapter);
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
