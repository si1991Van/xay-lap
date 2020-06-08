package com.viettel.construction.common;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class MyValueFormatter implements IValueFormatter {
    private DecimalFormat mFormat;

    public MyValueFormatter() {
        mFormat = new DecimalFormat("###,###,##0.0"); // use one decimal
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        // write your logic here
        String percent = mFormat.format(value);
        try {
            int indexDot = percent.lastIndexOf(".");//100.0
            indexDot = indexDot == -1 ? percent.lastIndexOf(",") : indexDot;
            if (indexDot != -1) {
                String afterDot = percent.substring(indexDot);
                if (afterDot.equals(".0")||afterDot.equals(",0"))
                    percent = percent.substring(0, indexDot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return percent + "%"; // e.g. append a dollar-sign
    }
}
