package com.example.stressmanagementapp.LineChart;

import android.graphics.Color;
import android.graphics.Typeface;

import com.example.stressmanagementapp.Model.Model;
import com.example.stressmanagementapp.Model.PPG_Model;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

public abstract class AbstractCustomLineChart {
    public LineChart chart;
    protected Typeface tfRegular;
    protected Typeface tfLight;
    public AbstractCustomLineChart(LineChart chart){
        this.chart=chart;
        // enable description text
        chart.getDescription().setEnabled(true);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true
        );
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);

        // set an alternative background color
        chart.setBackgroundColor(Color.LTGRAY);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        chart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(tfLight);
        l.setTextColor(Color.WHITE);

        XAxis xl = chart.getXAxis();
        xl.setTypeface(tfLight);
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    public abstract void addEntry(Object inputData) ;
//        //Sample code for addEntry
//        LineData data = chart.getData();
//        if (data != null) {
//            ILineDataSet set = data.getDataSetByIndex(0);
//            // set.addEntry(...); // can be called as well
//            if (set == null) {
//                set = createSet();
//                data.addDataSet(set);
//            }
//            data.addEntry(new Entry(set.getEntryCount(), inputData), 0);
//            data.notifyDataChanged();
//
//            // let the chart know it's data has changed
//            chart.notifyDataSetChanged();
//
//            // limit the number of visible entries
//            chart.setVisibleXRangeMaximum(120);
//            // chart.setVisibleYRange(30, AxisDependency.LEFT);
//
//            // move to the latest entry
//            chart.moveViewToX(data.getEntryCount());
//            // this automatically refreshes the chart (calls invalidate())
//            // chart.moveViewTo(data.getXValCount()-7, 55f,
//            // AxisDependency.LEFT);
//        }

    protected void updateMaxMinYAxisRealTime(LineChart chart, Model model) {
        YAxis leftAxis = chart.getAxisLeft();
        float realTimeMaxY=0;
        float realTimeMinY=0;
        if(model.getClass().isInstance(PPG_Model.class)) {
            PPG_Model ppg_model = (PPG_Model) model;
            float max0 = Math.max(ppg_model .getPpg0(), ppg_model.getPpg1());
            float max1 = Math.max(max0, ppg_model.getPpg2());

            float min0 = Math.min(ppg_model.getPpg0(), ppg_model.getPpg1());
            float min1 = Math.min(min0, ppg_model.getPpg2());
        }
//         leftAxis.setAxisMaximum(realTimeMaxY +10000);
//        if(realTimeMinY -10000 >=0)
//            leftAxis.setAxisMinimum(realTimeMinY -10000);
    }

    public LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }
}
