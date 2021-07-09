package com.example.stressmanagementapp.Chart.PPILineChart;

import android.graphics.Color;
import android.graphics.Typeface;

import com.example.stressmanagementapp.Chart.AbstractCustomLineChart;
import com.example.stressmanagementapp.Chart.MyMarkerView;
import com.example.stressmanagementapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class PPILineChart extends AbstractCustomLineChart {
    public LineChart chart;
    protected Typeface tfRegular;
    protected Typeface tfLight;

    public PPILineChart(LineChart chart){
        super(chart);
        this.chart=chart;
        // enable description text
        chart.getDescription().setEnabled(false);

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
        chart.setBackgroundColor(Color.BLACK);

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
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);


    }


    @Override
    public void addEntry(Object inputData) {
        float avgRestingAvgHR=0;
        Date timestamp=null;
        try {
            DateFormat simpleDateFormat=new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            timestamp = simpleDateFormat.parse( ((JSONObject) inputData).getString("timestamp"));
            avgRestingAvgHR = (float) ((JSONObject) inputData).getDouble("avgRestingAvgPPI");
        } catch (Exception e) {
            e.printStackTrace();
        }

        LineData data = chart.getData();

        if (data == null) {
            data = new LineData();
            chart.setData(data);
        }

        ILineDataSet set = data.getDataSetByIndex(0);
        // set.addEntry(...); // can be called as well

        if (set == null) {
            set = createSet();
            data.addDataSet(set);
        }
        Entry entry = new Entry();
        entry.setX(set.getEntryCount()+1);
        entry.setY(avgRestingAvgHR);
        entry.setData(timestamp);
        data.addEntry(entry, 0);

        data.notifyDataChanged();

        // let the chart know it's data has changed
        chart.notifyDataSetChanged();

        chart.setVisibleXRangeMaximum(6);
        //chart.setVisibleYRangeMaximum(15, AxisDependency.LEFT);
//
//            // this automatically refreshes the chart (calls invalidate())
        chart.moveViewTo(data.getEntryCount() - 7, 50f, YAxis.AxisDependency.LEFT);
    }
    @Override
    public LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "Resting Avg PPI");
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
