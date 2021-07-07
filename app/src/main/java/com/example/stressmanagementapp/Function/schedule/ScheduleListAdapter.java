package com.example.stressmanagementapp.Function.schedule;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.stressmanagementapp.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ScheduleListAdapter extends ArrayAdapter {
    private Activity context;
    private List<String> queryResult;
    private String apiPath;
    private CustomButtonListener customButtonListener;
    public ScheduleListAdapter(Activity context, List<String> queryResult) {
        super(context, R.layout.row_item_schedule, queryResult);
        this.context = context;
        this.queryResult = queryResult;
        this.apiPath =  context.getString(R.string.api_path);
    }

    public CustomButtonListener getCustomButtonListener() {
        return customButtonListener;
    }

    public void setCustomButtonListener(CustomButtonListener customButtonListener) {
        Log.d("setCustomButtonListener", "setCustomButtonListener: ");
        this.customButtonListener = customButtonListener;
    }

    public List<String> getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(List<String> queryResult) {
        this.queryResult = queryResult;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null)
            row = inflater.inflate(R.layout.row_item_schedule, null, true);
        TextView textViewActivityName = (TextView) row.findViewById(R.id.scheduledActivityName);
        TextView textViewActivityCategory = (TextView) row.findViewById(R.id.scheduledActivityCategory);
        TextView textViewStartDate = (TextView) row.findViewById(R.id.scheduledActivityStartDate);
        TextView textViewEndDate = (TextView) row.findViewById(R.id.scheduledActivityEndDate);
        TextView textViewScheduleStatus = (TextView) row.findViewById(R.id.scheduleStatus);
        ImageView deleteBtn = (ImageView) row.findViewById(R.id.schedule_deleteBtn);
        ImageView editBtn = (ImageView) row.findViewById(R.id.schedule_editBtn);




        JSONObject resultInJSONObject = null;
        try {
            resultInJSONObject = new JSONObject(queryResult.get(position));

            Log.d("viewSchedule", "getView in list position: " + position + "-->" + resultInJSONObject.toString());
            //Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
            //ScheduleModel schedule = g.fromJson(queryResult.get(position), ScheduleModel.class);
            JSONObject oid = resultInJSONObject.getJSONObject("_id");
            String _id = oid.get("$oid").toString();
            String activityName=resultInJSONObject.get("activityName").toString();
            String category=resultInJSONObject.get("category").toString();
            String startDateStr = resultInJSONObject.getJSONObject("startDateTime").getString("$date").toString();
            String endDateStr = resultInJSONObject.getJSONObject("endDateTime").getString("$date").toString();
            String status=resultInJSONObject.get("status").toString();

            textViewActivityName.setText(activityName);
            textViewActivityCategory.setText(category);
            Log.d("getView", "getView: startDateStr = "+startDateStr+" endDateStr = "+endDateStr);
            SimpleDateFormat fromDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date oldFromDate = fromDateFormat.parse(startDateStr);
            Date oldToDate = fromDateFormat.parse(endDateStr);


            SimpleDateFormat toDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            toDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            String newFromDate = toDateFormat.format(oldFromDate);
            String newToDate = toDateFormat.format(oldToDate);


            textViewStartDate.setText(newFromDate);
            textViewEndDate.setText(newToDate);

            textViewScheduleStatus.setText(status);

            deleteBtn.setImageResource(android.R.drawable.ic_delete);
            editBtn.setImageResource(android.R.drawable.ic_menu_edit);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customButtonListener.onDeleteBtnClickListener(position,_id);
                }
            });
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customButtonListener.onEditBtnClickListener(position);
                }
            });



        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return row;
    }


    public interface CustomButtonListener {
        public void onEditBtnClickListener(int position);
        public void onDeleteBtnClickListener(int position,String scheduleId);
    }
}
