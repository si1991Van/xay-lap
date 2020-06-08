package com.viettel.construction.appbase;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.model.ItemSpinnerBgmb;

import java.util.List;

public class SpinnerBgmbAdapter extends BaseAdapter {
    private List<ItemSpinnerBgmb> listData;
    private Activity activity;
    private LayoutInflater inflater;

    public SpinnerBgmbAdapter(List<ItemSpinnerBgmb> listData, Activity activity) {
        this.listData = listData;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = inflater.inflate(R.layout.spinner_item_bgmb,null);
        }
        TextView textView = view.findViewById(R.id.txtNameSpinner);
        textView.setText(listData.get(position).getName());
        return view;
    }
}

