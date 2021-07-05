package com.example.stressmanagementapp.Function.measure;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.stressmanagementapp.Model.MeasuredResult;
import com.example.stressmanagementapp.R;


import java.util.List;

public class MeasureResultListAdapter extends ArrayAdapter {
    private Activity context;
    private List<MeasuredResult> measuredResults;

    public MeasureResultListAdapter(Activity context, List<MeasuredResult> measuredResults) {
        super(context, R.layout.row_measure_sample, measuredResults);
        this.context = context;
        this.measuredResults = measuredResults;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null)
            row = inflater.inflate(R.layout.row_measure_sample, null, true);
        TextView lastRecordTime = (TextView) row.findViewById(R.id.lastRecordTime);
        TextView avgHR = (TextView) row.findViewById(R.id.avgHR);
        TextView avgPPI = (TextView) row.findViewById(R.id.avgPPI);
        TextView minPPI = (TextView) row.findViewById(R.id.minPPI);
        TextView maxPPI = (TextView) row.findViewById(R.id.maxPPI);
        TextView stressLevelValue = (TextView) row.findViewById(R.id.stressLevelValue);

        try {
            MeasuredResult result = measuredResults.get(position);

            Log.d("viewSchedule", "getView in list position: " + position + "-->" + result.toString());
            //Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
            //ScheduleModel schedule = g.fromJson(queryResult.get(position), ScheduleModel.class);

            lastRecordTime.setText(result.getTimestamp().toString());
            avgHR.setText(String.valueOf((int)result.getAvg_overall_bpm())+" bpm");
            avgPPI.setText(String.valueOf((int)result.getAvg_overall_ppi())+" ms");
            minPPI.setText(String.valueOf((int)result.getMin_ppi())+" ms");
            maxPPI.setText(String.valueOf((int)result.getMax_ppi())+" ms");
            //stressLevelValue.setText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }
}
