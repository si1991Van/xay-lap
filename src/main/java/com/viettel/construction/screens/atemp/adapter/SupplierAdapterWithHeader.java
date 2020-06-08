package com.viettel.construction.screens.atemp.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import com.viettel.construction.R;
import com.viettel.construction.common.FilterableSection;
import com.viettel.construction.model.api.MerEntityDTO;

public class SupplierAdapterWithHeader extends StatelessSection implements FilterableSection {
    private Context mContext;
    private String mTitle;
    private List<MerEntityDTO> mList;
    private List<MerEntityDTO> mFilteredList;

    public SupplierAdapterWithHeader(Context mContext, String mTitle, List<MerEntityDTO> mList) {
        super(new SectionParameters.Builder(R.layout.item_mer_entity)
                .headerResourceId(R.layout.header_item)
                .build());
        this.mContext = mContext;
        this.mTitle = mTitle;
        this.mList = mList;
        this.mFilteredList = new ArrayList<>(mList);
    }

    @Override
    public int getContentItemsTotal() {
        return mFilteredList.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new SupplierViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        SupplierViewHolder supplierViewHolder = (SupplierViewHolder) holder;
        int index = position + 1;
        MerEntityDTO merEntityDTO = mFilteredList.get(position);
        if (merEntityDTO.getGoodsName() != null) {
            supplierViewHolder.tvName.setText(index + ". " + merEntityDTO.getGoodsName());
        }
        if (merEntityDTO.getNumbeRepository() != null) {
            supplierViewHolder.tvNumber.setText(merEntityDTO.getNumbeRepository() + "");
        }
        if (merEntityDTO.getSerial() != null) {
            supplierViewHolder.tvSerial.setVisibility(View.VISIBLE);
            supplierViewHolder.tvSerial.setText(mContext.getString(R.string.serial_material, merEntityDTO.getSerial()));
        } else {
            supplierViewHolder.tvSerial.setVisibility(View.GONE);
        }
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        headerViewHolder.tvTitle.setText(mTitle);
    }

    @Override
    public void filter(String query) {
        String temp = query.trim();
        if (TextUtils.isEmpty(temp)) {
            mFilteredList = new ArrayList<>(mList);
            this.setVisible(true);
        } else {
            mFilteredList.clear();
            String input = removeAccent(temp).toUpperCase();
            for (MerEntityDTO entity : mList) {
                String serial, name;
                if (entity.getSerial() != null) {
                    serial = removeAccent(entity.getSerial()).trim().toUpperCase();
                } else {
                    serial = "";
                }
                if (entity.getGoodsName() != null) {
                    name = removeAccent(entity.getGoodsName()).trim().toUpperCase();
                } else {
                    name = "";
                }
                if (serial.contains(input) || name.contains(input)) {
                    mFilteredList.add(entity);
                }
//                if (entity.getTaskName() != null && entity.getConstructionCode() != null) {
////                    String taskName = removeAccent(entity.getTaskName()).toUpperCase().trim();
////                    String constructionCode;
////                    if (entity.getConstructionCode() != null) {
////                        constructionCode = entity.getConstructionCode().toUpperCase().trim();
////                    } else {
////                        constructionCode = "";
////                    }
////                    if (taskName.contains(input) || constructionCode.contains(input)) {
////                        mFilteredList.add(entity);
////                    }
//                }
            }
            this.setVisible(!mFilteredList.isEmpty());
        }
    }

    public class SupplierViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_serial)
        TextView tvSerial;

        public SupplierViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_header_title)
        TextView tvTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
