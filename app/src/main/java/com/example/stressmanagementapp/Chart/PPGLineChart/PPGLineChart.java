package com.example.stressmanagementapp.Chart.PPGLineChart;

import android.graphics.Color;
import android.graphics.Typeface;

import com.example.stressmanagementapp.Chart.AbstractCustomLineChart;
import com.example.stressmanagementapp.Model.PPG_Model;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


public class PPGLineChart extends AbstractCustomLineChart {
    public LineChart chart;
    protected Typeface tfRegular;
    protected Typeface tfLight;

    public PPGLineChart(LineChart chart){
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
        data.setValueTextColor(Color.RED);

        // add empty data
        chart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(tfLight);
        l.setTextColor(Color.RED);

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

    @Override
    public void addEntry(Object inputData) {
        PPG_Model inputModel = (PPG_Model) inputData;
        LineData data = chart.getData();
        if (data != null) {
            //ILineDataSet minSet = data.getDataSetByIndex(0);
            ILineDataSet dataSet1 = data.getDataSetByIndex(0);
            //ILineDataSet maxSet = data.getDataSetByIndex(2);
//            ILineDataSet combineSet = data.getDataSetByIndex(3);
            // set.addEntry(...); // can be called as well
            /*if (minSet == null) {
                minSet = createSet();
                data.addDataSet(minSet);
            }*/
            if (dataSet1 == null) {
                dataSet1 = createSet();
                data.addDataSet(dataSet1);
            }
            /*if (maxSet == null) {
                maxSet = createSet();
                data.addDataSet(maxSet);
            }*/
//            if(combineSet == null){
//                combineSet = createSet();
//                data.addDataSet(combineSet);
//            }
            if(inputData!=null) {
                super.updateMaxMinYAxisRealTime(chart, inputModel);
                //data.addEntry(new Entry(minSet.getEntryCount(), inputModel.getMin()), 0);
                data.addEntry(new Entry(dataSet1.getEntryCount(), inputModel.getPpg0()), 0);
                //data.addEntry(new Entry(maxSet.getEntryCount(), inputModel.getMax()), 2);
                data.notifyDataChanged();
            }
            // let the chart know it's data has changed
            chart.notifyDataSetChanged();

            // limit the number of visible entries
            chart.setVisibleXRangeMaximum(120);
            // chart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            chart.moveViewToX(data.getEntryCount());

            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }
}
