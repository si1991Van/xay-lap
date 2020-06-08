package com.viettel.construction.screens.atemp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.construction.R;
import com.viettel.construction.model.api.version.AppParamDTO;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter
        implements android.widget.SpinnerAdapter {

    private Context context;
    private List<AppParamDTO> list;
    private AppParamDTO appParamDTO;

    public SpinnerAdapter(Context context, List<AppParamDTO> list) {
        this.list = list;
        this.context = context;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int i) {
        return list.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
//        TextView txt = new TextView(context);
        appParamDTO = list.get(position);
//        txt.setTextSize(16);
//        txt.setGravity(Gravity.CENTER_VERTICAL);
//        txt.setText(appParamDTO.getName());
//        txt.setTextColor(Color.parseColor("#000000"));

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View item = inflater.inflate(R.layout.spinner_item, null);
//        appParamDTO = list.get(i);

        TextView txtName = item.findViewById(R.id.txt_titleSpinner);
        txtName.setText(appParamDTO.getName());
        return item;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
//        TextView txt = new TextView(context);
//        appParamDTO = list.get(i);
//        txt.setGravity(Gravity.CENTER);
//        txt.setTextSize(16);
////        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
//        txt.setText(appParamDTO.getName());
//        txt.setTextColor(Color.parseColor("#000000"));
//        return txt;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View item = inflater.inflate(R.layout.spinner_item, null);
        appParamDTO = list.get(i);

        TextView txtName = item.findViewById(R.id.txt_titleSpinner);
        txtName.setText(appParamDTO.getName());
        return item;
    }

}
