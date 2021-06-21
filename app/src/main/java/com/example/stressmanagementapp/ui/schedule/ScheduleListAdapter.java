package com.example.stressmanagementapp.ui.schedule;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stressmanagementapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class ScheduleListAdapter extends ArrayAdapter {
    private Activity context;
    private List<String> queryResult;

    public ScheduleListAdapter(Activity context, List<String> queryResult) {
        super(context, R.layout.row_item_schedule, queryResult);
        this.context = context;
        this.queryResult=queryResult;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.row_item_schedule, null, true);
        TextView textViewActivityName = (TextView) row.findViewById(R.id.scheduledActivityName);
        TextView textViewActivityCategory = (TextView) row.findViewById(R.id.scheduledActivityCategory);
        TextView textViewStartDate = (TextView) row.findViewById(R.id.scheduledActivityStartDate);
        TextView textViewEndDate = (TextView) row.findViewById(R.id.scheduledActivityEndDate);
        TextView textViewScheduleStatus = (TextView) row.findViewById(R.id.scheduleStatus);
        ImageView deleteBtn = (ImageView) row.findViewById(R.id.schedule_deleteBtn);
        ImageView editBtn = (ImageView) row.findViewById(R.id.schedule_editBtn);

        JSONObject resultInJSONObject= null;
        try {
            resultInJSONObject = new JSONObject(queryResult.get(position));

        Log.d("viewSchedule", "getView in list position: "+position+"-->"+resultInJSONObject.toString());
        //Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
        //ScheduleModel schedule = g.fromJson(queryResult.get(position), ScheduleModel.class);

        textViewActivityName.setText(resultInJSONObject.get("activityName").toString());
        textViewActivityCategory.setText(resultInJSONObject.get("category").toString());
        String startDateStr = resultInJSONObject.getJSONObject("startDateTime").getString("$date").toString();
        String endDateStr = resultInJSONObject.getJSONObject("endDateTime").getString("$date").toString();
        textViewStartDate.setText(startDateStr);
        textViewEndDate.setText(endDateStr);
        textViewScheduleStatus.setText(resultInJSONObject.get("status").toString());
        deleteBtn.setImageResource(android.R.drawable.ic_delete);
        editBtn.setImageResource(android.R.drawable.ic_menu_edit);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return row;
    }
}
