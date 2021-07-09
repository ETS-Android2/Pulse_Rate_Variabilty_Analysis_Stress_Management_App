package com.example.stressmanagementapp.Function.statistic;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.stressmanagementapp.Function.schedule.ScheduleListAdapter;
import com.example.stressmanagementapp.Model.MeasuredRecordModel;
import com.example.stressmanagementapp.Model.MeasuredResult;
import com.example.stressmanagementapp.R;
import com.google.gson.JsonObject;


import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class QueryMeasuredResultListAdapter extends ArrayAdapter {
    private Activity context;
    private List<String> queryResult;
    private CustomButtonListener customButtonListener;

    public QueryMeasuredResultListAdapter(Activity context, List<String> queryResult) {
        super(context, R.layout.row_measure_moredetail_sample, queryResult);
        this.context = context;
        this.queryResult = queryResult;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null)
            row = inflater.inflate(R.layout.row_measure_moredetail_sample, null, true);
        TextView activityName = (TextView) row.findViewById(R.id.activityName);
        TextView categoryName = (TextView) row.findViewById(R.id.categoryName);

        TextView lastRecordTime2 = (TextView) row.findViewById(R.id.lastRecordTime2);

        TextView avgHR = (TextView) row.findViewById(R.id.avgHR);
        TextView minHR = (TextView) row.findViewById(R.id.minHR);
        TextView maxHR = (TextView) row.findViewById(R.id.avgPPI);

        TextView avgPPI = (TextView) row.findViewById(R.id.avgPPI);
        TextView minPPI = (TextView) row.findViewById(R.id.minPPI);
        TextView maxPPI = (TextView) row.findViewById(R.id.maxPPI);

        TextView avgStressLevelValue = (TextView) row.findViewById(R.id.avgStressLevelValue);
        TextView minStressLevelValue = (TextView) row.findViewById(R.id.minStressLevelValue);
        TextView maxStressLevelValue = (TextView) row.findViewById(R.id.maxStressLevelValue);
        JSONObject resultInJSONObject = null;
        try {
            resultInJSONObject = new JSONObject(queryResult.get(position));
            JSONArray measuredResult = resultInJSONObject.getJSONArray("measuredResult");
            JSONObject lastRecord = measuredResult.getJSONObject(measuredResult.length() - 1);
            Log.d("viewMeasureRecord", "getView in list position: " + position + "-->" + resultInJSONObject.toString());

            JSONObject timeObj = lastRecord.getJSONObject("timestamp");
            String lastRecordTime = timeObj.toString();
            String correctTime =timeObj.getString("$date");
            Log.d("viewMeasureRecord", "lastRecordTime = "+lastRecordTime);
            Log.d("viewMeasureRecord", "correctTime = "+correctTime);
            SimpleDateFormat fromDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date oldLastRecordTime = fromDateFormat.parse(correctTime);
            SimpleDateFormat toDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            toDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            String newLastRecordTime = toDateFormat.format(oldLastRecordTime);

            activityName.setText(resultInJSONObject.get("activityName").toString());

            categoryName.setText(resultInJSONObject.get("category").toString());

            lastRecordTime2.setText(newLastRecordTime);

            avgHR.setText(String.format("%.0f", resultInJSONObject.get("avg_BPM_Value")));
            minHR.setText(String.format("%.0f", resultInJSONObject.get("lowest_BPM_Value")));
            maxHR.setText(String.format("%.0f", resultInJSONObject.get("highest_BPM_Value")));

            avgPPI.setText(String.format("%.0f", resultInJSONObject.get("avg_PPI_Value")) + " ms");
            minPPI.setText(String.format("%.0f", resultInJSONObject.get("lowest_PPI_Value")) + " ms");
            maxPPI.setText(String.format("%.0f", resultInJSONObject.get("highest_PPI_Value")) + " ms");

            avgStressLevelValue.setText(String.format("%.2f", resultInJSONObject.get("avg_StressLevel_Value")) + "%");
            minStressLevelValue.setText(String.format("%.2f", resultInJSONObject.get("lowest_StressLevel_Value")) + "%");
            maxStressLevelValue.setText(String.format("%.2f", resultInJSONObject.get("highest_StressLevel_Value")) + "%");
            //stressLevelValue.setText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    public List<String> getQueryResult() {
        return queryResult;
    }

    public void setCustomButtonListener(CustomButtonListener customButtonListener) {
        Log.d("setCustomButtonListener", "setCustomButtonListener: ");
        this.customButtonListener = customButtonListener;
    }

    public interface CustomButtonListener {
        public void onDeleteBtnClickListener(int position, String scheduleId);
    }
}
