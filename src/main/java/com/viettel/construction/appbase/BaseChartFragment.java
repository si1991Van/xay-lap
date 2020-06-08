package com.viettel.construction.appbase;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;

import com.viettel.construction.R;
import com.viettel.construction.common.MyValueFormatter;
import com.viettel.construction.screens.home.HomeCameraActivity;

/**
 * Created by Manroid on 17/01/2018.
 */

public class BaseChartFragment extends BaseCameraFragment {

    public String[] mParties, mParties2, mParties3, mParties4, mParties5;
    public int[] colorChar1;
    public int[] colorChar2;
    public int[] colorChar3;
    public int[] colorChar4;
    public int[] colorChar5;
    public EventBus bus = EventBus.getDefault();
    public Calendar myCalendar;
    private DatePickerDialog datePickerDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        colorChar1 = new int[]
                {
                        getResources().getColor(R.color.c10),
                        getResources().getColor(R.color.c7)

                };

        colorChar2 = new int[]
                {
                        getResources().getColor(R.color.c10),
                        getResources().getColor(R.color.c9),
                        getResources().getColor(R.color.c12)
                };

        colorChar3 = new int[]
                {
                        getResources().getColor(R.color.c10),
                        getResources().getColor(R.color.c12)
                };

        colorChar4 = new int[]
                {
                        getResources().getColor(R.color.c20),
                        getResources().getColor(R.color.c22),
                        getResources().getColor(R.color.c10),
                        getResources().getColor(R.color.c19)
                };

        colorChar5 = new int[]
                {
                        getResources().getColor(R.color.c10),
                        getResources().getColor(R.color.c19)
                };

        mParties = new String[]{
                getActivity().getString(R.string.on_schedule),
                getActivity().getString(R.string.behind_schedule)};

        mParties2 = new String[]{
                getActivity().getString(R.string.received),
                getActivity().getString(R.string.also_receive),
                getActivity().getString(R.string.rejected_chart)};

        mParties3 = new String[]{
                getActivity().getString(R.string.checked_chart),
                getActivity().getString(R.string.not_controlled)};

        mParties4 = new String[]{
                getActivity().getString(R.string.did_not_perform),
                getActivity().getString(R.string.in_process_1),
                getActivity().getString(R.string.complete1),
                getActivity().getString(R.string.on_pause)
        };

        mParties5 = new String[]{
                "Chưa nhận",
                "Đã nhận"
        };
    }

    public void setTime(final TextView txtCalendar) {
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog

        datePickerDialog = new DatePickerDialog(getActivity(), R.style.datepicker,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        txtCalendar.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        //MM-dd-yyyy
        datePickerDialog.show();

//        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                if (which == DialogInterface.BUTTON_NEGATIVE) {
//                    datePickerDialog.dismiss();
//                }
//            }
//        });

    }

    public void commitChange(Fragment fragment, Boolean... isAdd) {
        if (getActivity() instanceof HomeCameraActivity)
            ((HomeCameraActivity) getActivity()).changeLayout(fragment, isAdd);
    }


    public void initChartVTTB(List<Integer> listData, PieChart pieChart, float setHoleRadius, float setSliceSpace, String[] mParties, int count, boolean isEnableLegend) {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(0, 0, 0, 0);
        pieChart.getLegend().setWordWrapEnabled(true);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
//        pieChart.setCenterTextTypeface(mTfLight);
//        pieChart.setCenterText(generateCenterSpannableText());
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(150);

        pieChart.setHoleRadius(setHoleRadius);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        // pieChart.setUnit(" €");
        // pieChart.setDrawUnitsInChart(true);

        // add a selection listener
//        pieChart.setOnChartValueSelectedListener(this);

        setData3(listData, pieChart, count, 100, setSliceSpace, mParties);

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // pieChart.spin(2000, 0, 360);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setTextSize(10);
        // set hide legend
        l.setEnabled(isEnableLegend);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);

        // entry label styling
//        pieChart.setEntryLabelTypeface(mTfRegular);
        pieChart.setDrawEntryLabels(false);
    }

    public void initChartCongViec(List<Integer> listData, PieChart pieChart, float setHoleRadius, float setSliceSpace, String[] mParties, int count, boolean isEnableLegend) {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(0, 0, 0, 0);

        pieChart.getLegend().setWordWrapEnabled(true);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawSlicesUnderHole(true);
        pieChart.setDrawCenterText(true);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(150);

        pieChart.setHoleRadius(setHoleRadius);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        setData1(listData, pieChart, count, 100, setSliceSpace, mParties);

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);


        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setTextSize(10);
        // set hide legend
        l.setEnabled(isEnableLegend);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);

        // entry label styling
//        pieChart.setEntryLabelTypeface(mTfRegular);
        pieChart.setDrawEntryLabels(false);
    }

    public void initChartBGMB(List<Integer> listData, PieChart pieChart, float setHoleRadius, float setSliceSpace, String[] mParties, int count, boolean isEnableLegend) {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(0, 0, 0, 0);

        pieChart.getLegend().setWordWrapEnabled(true);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawSlicesUnderHole(true);
        pieChart.setDrawCenterText(true);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(150);

        pieChart.setHoleRadius(setHoleRadius);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        setData1(listData, pieChart, count, 100, setSliceSpace, mParties);

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);


        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setTextSize(10);
        l.setEnabled(isEnableLegend);
        pieChart.setDrawEntryLabels(false);
    }

    public void initChartXuatKho(List<Integer> listData, PieChart pieChart, float setHoleRadius, float setSliceSpace, String[] mParties, int count, boolean isEnableLegend) {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(0, 0, 0, 0);
        pieChart.getLegend().setWordWrapEnabled(true);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
//        pieChart.setCenterTextTypeface(mTfLight);
//        pieChart.setCenterText(generateCenterSpannableText());
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(150);

        pieChart.setHoleRadius(setHoleRadius);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        // pieChart.setUnit(" €");
        // pieChart.setDrawUnitsInChart(true);

        // add a selection listener
//        pieChart.setOnChartValueSelectedListener(this);

        setData1(listData, pieChart, count, 100, setSliceSpace, mParties);

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // pieChart.spin(2000, 0, 360);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setTextSize(10);
        // set hide legend
        l.setEnabled(isEnableLegend);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);

        // entry label styling
        //pieChart.setEntryLabelTypeface(mTfRegular);
        pieChart.setDrawEntryLabels(false);
    }


    public void initChart4(List<Integer> listData, PieChart pieChart, float setHoleRadius, float setSliceSpace, String[] mParties, int count, boolean isEnableLegend) {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(0, 0, 0, 0);
        pieChart.getLegend().setWordWrapEnabled(true);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
//        pieChart.setCenterTextTypeface(mTfLight);
//        pieChart.setCenterText(generateCenterSpannableText());
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(150);

        pieChart.setHoleRadius(setHoleRadius);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        // pieChart.setUnit(" €");
        // pieChart.setDrawUnitsInChart(true);

        // add a selection listener
//        pieChart.setOnChartValueSelectedListener(this);

        setData4(listData, pieChart, count, 100, setSliceSpace, mParties);

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // pieChart.spin(2000, 0, 360);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        // set hide legend
        l.setEnabled(isEnableLegend);
        l.setTextSize(12f);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);

        // entry label styling
//        pieChart.setEntryLabelTypeface(mTfRegular);
        pieChart.setDrawEntryLabels(false);
    }

    public void setData4(List<Integer> listData, PieChart pieChart, int count, float range, float setSliceSpace, String[] mParties) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

        Activity activity = getActivity();
        if (activity != null && isAdded()) {
            if (listData != null && listData.size() > 0) {
                for (int i = 0; i < count; i++) {
                    if (listData.get(i) > 0) {
                        entries.add(
                                new PieEntry((float) listData.get(i)
                                        / (listData.get(0) + listData.get(1) + listData.get(2) + listData.get(3)),
                                        mParties[i % mParties.length],
                                        getResources().getDrawable(R.mipmap.ic_launcher)));

                    }
                }
            }
        }

        if (activity != null && isAdded()) {
            PieDataSet dataSet = new PieDataSet(entries, "");

            dataSet.setDrawIcons(false);

            dataSet.setSliceSpace(setSliceSpace);
            dataSet.setIconsOffset(new MPPointF(0, 40));
            dataSet.setSelectionShift(5f);

            // add a lot of colors

            ArrayList<Integer> colors = new ArrayList<Integer>();

            //set color for every Piechart
            int colorTemp[];

            switch (pieChart.getId()) {
                case R.id.chartCongViec:
                    colorTemp = colorChar1;
                    break;
                case R.id.chartXuatKho:
                    colorTemp = colorChar2;
                    break;
                case R.id.chartBGMB:
                    colorTemp = colorChar5;
                    break;
//                case R.id.chartVTTB:
//                    colorTemp = colorChar3;
//                    break;
                default:
                    colorTemp = colorChar4;
                    break;
            }

            for (int i = 0; i < listData.size(); i++) {
                if (listData.get(i) > 0) {
                    colors.add(colorTemp[i]);
                }
            }


            colors.add(ColorTemplate.getHoloBlue());

            dataSet.setColors(colors);
            //dataSet.setSelectionShift(0f);

            PieData data = new PieData(dataSet);
            data.setValueFormatter(new MyValueFormatter());
            data.setValueTextSize(12f);
            data.setValueTextColor(getResources().getColor(R.color.black_base));
//        data.setValueTypeface(mTfLight);
            pieChart.setData(data);

            // undo all highlights
            pieChart.highlightValues(null);

            pieChart.invalidate();
        }

    }

    public void setData1(List<Integer> listData, PieChart pieChart, int count, float range, float setSliceSpace, String[] mParties) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        Activity activity = getActivity();
        if (activity != null && isAdded()) {
            if (listData != null && listData.size() > 0) {
                for (int i = 0; i < count; i++) {
                    if (listData.get(i) > 0) {
                        entries.add(
                                new PieEntry((float) listData.get(i)
                                        / (listData.get(0) + listData.get(1)),
                                        mParties[i % mParties.length],
                                        getResources().getDrawable(R.mipmap.ic_launcher)));

                    }
                }
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(setSliceSpace);
        dataSet.setIconsOffset(new MPPointF(0, 40));
//        dataSet.setSelectionShift(5f);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        //set color for every Piechart
        int colorTemp[];

        switch (pieChart.getId()) {
            case R.id.chartCongViec:
                colorTemp = colorChar1;
                break;
            case R.id.chartXuatKho:
                colorTemp = colorChar2;
                break;
            case R.id.chartBGMB:
                colorTemp = colorChar5;
                break;
//            case R.id.chartVTTB:
//                colorTemp = colorChar3;
//                break;
            default:
                colorTemp = colorChar4;
                break;
        }

        for (int i = 0; i < listData.size(); i++) {
            if (listData.get(i) > 0) {
                colors.add(colorTemp[i]);
            }
        }

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
        data.setValueFormatter(new MyValueFormatter());

        data.setValueTextSize(12f);
        data.setValueTextColor(getResources().getColor(R.color.black_base));

//        data.setValueTypeface(mTfLight);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    public void setData3(List<Integer> listData, PieChart pieChart, int count, float range, float setSliceSpace, String[] mParties) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        Activity activity = getActivity();
        if (activity != null && isAdded()) {
            if (listData != null && listData.size() > 0) {
                for (int i = 0; i < count; i++) {
                    if (listData.get(i) > 0) {
                        entries.add(
                                new PieEntry((float) listData.get(i)
                                        / (listData.get(0) + listData.get(1)),
                                        mParties[i % mParties.length],
                                        getResources().getDrawable(R.mipmap.ic_launcher)));

                    }
                }
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(setSliceSpace);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        //set color for every Piechart
        int colorTemp[];

        switch (pieChart.getId()) {
            case R.id.chartCongViec:
                colorTemp = colorChar1;
                break;
            case R.id.chartXuatKho:
                colorTemp = colorChar2;
                break;
            case R.id.chartBGMB:
                colorTemp = colorChar5;
                break;
//            case R.id.chartVTTB:
//                colorTemp = colorChar3;
//                break;
            default:
                colorTemp = colorChar4;
                break;
        }

        for (int i = 0; i < listData.size(); i++) {
            if (listData.get(i) > 0) {
                colors.add(colorTemp[i]);
            }
        }

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(12f);
        data.setValueTextColor(getResources().getColor(R.color.black_base));
//        data.setValueTypeface(mTfLight);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    public void setData2(List<Integer> listData, PieChart pieChart, int count, float range, float setSliceSpace, String[] mParties) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        Activity activity = getActivity();
        if (activity != null && isAdded()) {
            if (listData != null && listData.size() > 0) {
                for (int i = 0; i < count; i++) {
                    if (listData.get(i) > 0) {
                        entries.add(
                                new PieEntry((float) listData.get(i)
                                        / (listData.get(0) + listData.get(1) + listData.get(2)),
                                        mParties[i % mParties.length],
                                        getResources().getDrawable(R.mipmap.ic_launcher)));

                    }
                }
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(setSliceSpace);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        //set color for every Piechart
        int colorTemp[];

        switch (pieChart.getId()) {
            case R.id.chartCongViec:
                colorTemp = colorChar1;
                break;
            case R.id.chartXuatKho:
                colorTemp = colorChar2;
                break;
            case R.id.chartBGMB:
                colorTemp = colorChar5;
                break;
//            case R.id.chartVTTB:
//                colorTemp = colorChar3;
//                break;
            default:
                colorTemp = colorChar4;
                break;
        }

        for (int i = 0; i < listData.size(); i++) {
            if (listData.get(i) > 0) {
                colors.add(colorTemp[i]);
            }
        }

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(12f);
        data.setValueTextColor(getResources().getColor(R.color.black_base));
//        data.setValueTypeface(mTfLight);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    public void setData(PieChart pieChart, int count, float range, float setSliceSpace, String[] mParties) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) (50f / 2),
                    mParties[i % mParties.length],
                    getResources().getDrawable(R.mipmap.ic_launcher)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(setSliceSpace);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        //set color for every Piechart
        int colorTemp[];

        switch (pieChart.getId()) {
            case R.id.chartCongViec:
                colorTemp = colorChar1;
                break;
            case R.id.chartXuatKho:
                colorTemp = colorChar2;
                break;
            case R.id.chartBGMB:
                colorTemp = colorChar5;
                break;
//            case R.id.chartVTTB:
//                colorTemp = colorChar3;
//                break;
            default:
                colorTemp = colorChar4;
                break;
        }

        for (int c : colorTemp)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(12f);
        //enable percent for piechart
        data.setDrawValues(false);
        data.setValueTextColor(getResources().getColor(R.color.black_base));
//        data.setValueTypeface(mTfLight);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    public void initBarChart(BarChart mChart4) {
        float barWidth;
        float barSpace;
        float groupSpace;

        barWidth = 0.3f;
        barSpace = 0.05f;
        groupSpace = 0.3f;

        mChart4.setDescription(null);
        mChart4.setPinchZoom(true);
        mChart4.setScaleEnabled(false);
        mChart4.setDrawBarShadow(false);
        mChart4.setDrawGridBackground(false);

        int groupCount = 3;

        ArrayList xVals = new ArrayList();

        //diable title for barchart
//        xVals.add(getResources().getString(R.string.sl));
//        xVals.add(getResources().getString(R.string.hshc));
//        xVals.add(getResources().getString(R.string.dt));

        ArrayList yVals1 = new ArrayList();
        ArrayList yVals2 = new ArrayList();
        //disable value for barchart
//        yVals1.add(new BarEntry(1, (float) 0));
//        yVals2.add(new BarEntry(1, (float) 0));
//        yVals1.add(new BarEntry(2, (float) 0));
//        yVals2.add(new BarEntry(2, (float) 0));
//        yVals1.add(new BarEntry(3, (float) 0));
//        yVals2.add(new BarEntry(3, (float) 0));

        BarDataSet set1, set2;
        set1 = new BarDataSet(yVals1, "Kế hoạch");
        set1.setColor(getResources().getColor(R.color.c5));
        set1.setValueTextSize(12f);
        set2 = new BarDataSet(yVals2, "Thực tế");
        set2.setColor(getResources().getColor(R.color.c6));
        set2.setValueTextSize(12f);
        BarData data = new BarData(set1, set2);
        data.setValueFormatter(new LargeValueFormatter());
        data.setValueTextSize(12f);
        mChart4.setData(data);
        mChart4.getBarData().setBarWidth(barWidth);
        mChart4.getXAxis().setAxisMinimum(0);
        mChart4.getXAxis().setAxisMaximum(0 + mChart4.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        mChart4.groupBars(0, groupSpace, barSpace);
        mChart4.getData().setHighlightEnabled(false);
        mChart4.animateY(2000);
        mChart4.invalidate();


        Legend l = mChart4.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setEnabled(false);
        l.setTextSize(12f);
//        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setDrawInside(true);
        l.setYOffset(20f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(15f);


        //X-axis
        XAxis xAxis = mChart4.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(3);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
        //Y-axis
        mChart4.getAxisRight().setEnabled(false);
        YAxis leftAxis = mChart4.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f);

    }


    public long getCurretnTimeStampMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format.format(date);
        String res = strDate.substring(5, 7);
        return Long.parseLong(res);
    }

    public long getCurretnTimeStampYear(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format.format(date);
        String res = strDate.substring(0, 4);
        return Long.parseLong(res);
    }

    public Date ConvertStringToDate(String s) {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = df.parse(s);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String changeStringFormat(String s) {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
        String reformattedStr;
        try {
            reformattedStr = myFormat.format(fromUser.parse(s));
        } catch (ParseException e) {
            e.printStackTrace();
            reformattedStr = "";
        }
        return reformattedStr;
    }
}
