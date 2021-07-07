package com.example.stressmanagementapp.Function.schedule;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stressmanagementapp.R;
import com.example.stressmanagementapp.Util.CustomJsonRequest;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class ViewScheduledMeasureActivity extends AppCompatActivity implements ScheduleListAdapter.CustomButtonListener {
    private Spinner scheduleFilterSpinner;
    private String userID;
    private TextView noScheduleMessage;
    private ListView scheduledList;
    private List<String> queryResultList;
    private String api;
    private String query;
    private JSONObject requestBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_scheduled_measure_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d("onCreate", "onCreate: ");
        api = getString(R.string.api_path);
        setTitle("All Schedule");
        userID = "605995e57194bc0568afdec1";
        initSortByFilterSpinner();
        scheduledList = findViewById(R.id.scheduleList);
    }
    @Override
    protected void onResume() {

        super.onResume();
        Log.d("onResume", "onResume: ");
    }

    private void initSortByFilterSpinner() {
        scheduleFilterSpinner = (Spinner) findViewById(R.id.scheduleFilterSpinner);
        noScheduleMessage = (TextView) findViewById(R.id.noScheduleMessage);
        noScheduleMessage.setVisibility(View.GONE);
        Resources res = getResources();
        ArrayAdapter<String> apModeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, res.getStringArray(R.array.scheduledRecordSortBy));
        Log.d("getAllActivityCategory", "Set adapter");
        scheduleFilterSpinner.setAdapter(apModeAdapter);
        scheduleFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                switch (i) {
                    case 1:
                        requestBody = queryScheduleSortByDateDSC();
                        query = "queryScheduleSortByDateDSC";
                        break;
                    case 2:
                        requestBody = queryScheduleSortByActivityNameASC();
                        query = "queryScheduleSortByActivityNameASC";
                        break;
                    case 3:
                        requestBody = queryScheduleSortByActivityNameDSC();
                        query = "queryScheduleSortByActivityNameDSC";
                        break;
                    case 4:
                        requestBody = queryScheduleWhichIsScheduled();
                        query = "queryScheduleWhichIsScheduled";
                        break;
                    case 5:
                        requestBody = queryScheduleWhichIsRunning();
                        query = "queryScheduleWhichIsRunning";
                        break;
                    case 6:
                        requestBody = queryScheduleWhichIsCompleted();
                        query = "queryScheduleWhichIsCompleted";
                        break;
                    default:
                        requestBody = queryScheduleSortByDateASC();
                        query = "queryScheduleSortByDateASC";
                }
                executeQuery(requestBody, query);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        scheduleFilterSpinner.setSelection(0);
    }

    private JSONObject queryScheduleWhichIsCompleted() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("status", "Completed");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryScheduleWhichIsRunning() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("status", "Running");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryScheduleWhichIsScheduled() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("status", "Scheduled");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryScheduleSortByActivityNameDSC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "activityName");
            requestBody.put("sortOrder", false);
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryScheduleSortByActivityNameASC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "activityName");
            requestBody.put("sortOrder", true);
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryScheduleSortByDateASC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "startDateTime");
            requestBody.put("sortOrder", true);
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryScheduleSortByDateDSC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "startDateTime");
            requestBody.put("sortOrder", false);
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void executeQuery(JSONObject requestBody, String queryName) {
        String endpoint = "getScheduleInfoForOwner";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(ViewScheduledMeasureActivity.this);
        String url = api + "/" + endpoint;
        Log.d(queryName, "Connecting url = " + url);
        // Request a string response from the provided URL.
        queryResultList = new ArrayList<String>();
        String queryLabel = "Query=" + queryName;

        CustomJsonRequest jsonArrayRequest = new CustomJsonRequest<JSONObject>(Request.Method.POST, url, requestBody, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for (int i = 0; i < response.length(); i++) {
                        queryResultList.add(response.getString(i));
                    }
                    // For populating list data
                    Log.d(queryLabel, "onResponse: [listSize]= " + queryResultList.size());
                    ScheduleListAdapter scheduleListAdapter = new ScheduleListAdapter(ViewScheduledMeasureActivity.this, queryResultList);
                    scheduleListAdapter.setCustomButtonListener(ViewScheduledMeasureActivity.this);
                    if(scheduleListAdapter.getQueryResult().size()==0){
                        //no schedule
                        noScheduleMessage.setVisibility(View.VISIBLE);
                    }else{
                        noScheduleMessage.setVisibility(View.GONE);
                    }
                    scheduledList.setAdapter(scheduleListAdapter);
//                    scheduledList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                            Toast.makeText(getApplicationContext(), "You Selected " + position, Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    Log.d(queryLabel, "Response body = " + response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(queryLabel, "JSONException = " + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(queryLabel, "Exception = " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(queryLabel, "Error Response body = " + error.toString());
            }
        }) {
            @Override
            public byte[] getBody() {
                return requestBody.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue.add(jsonArrayRequest);
    }


    @Override
    public void onEditBtnClickListener(int position) {
        try {
            Log.d("onEditBtnClickListener", "onEditBtnClickListener: clicked " + position);
            JSONObject resultInJSONObject = new JSONObject(queryResultList.get(position));
            String _id = resultInJSONObject.get("_id").toString();
            String activityName = resultInJSONObject.get("activityName").toString();
            String category = resultInJSONObject.get("category").toString();
            String startDateStr = resultInJSONObject.getJSONObject("startDateTime").getString("$date").toString();
            String endDateStr = resultInJSONObject.getJSONObject("endDateTime").getString("$date").toString();
            String status = resultInJSONObject.get("status").toString();
            String activityIdInActivityCollection = resultInJSONObject.getString("activityIdInActivityCollection").toString();
            Intent editSchedule = new Intent(ViewScheduledMeasureActivity.this, ScheduleActivity.class);
            editSchedule.putExtra("action", "edit");
            editSchedule.putExtra("_id",_id);
            editSchedule.putExtra("activityName", activityName);
            editSchedule.putExtra("category", category);
            editSchedule.putExtra("startDateStr", startDateStr);
            editSchedule.putExtra("endDateStr", endDateStr);
            editSchedule.putExtra("status", status);
            editSchedule.putExtra("activityIdInActivityCollection",activityIdInActivityCollection);
            startActivity(editSchedule);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDeleteBtnClickListener(int position, String scheduleId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewScheduledMeasureActivity.this);
        builder.setMessage("Are you sure to delete this schedule?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        deleteSchedule(scheduleId);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //cancel
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void deleteSchedule(String id) {

        Log.i("deleteSchedule", "ScheduleID = " + id);

        String endpoint = "deleteSchedule";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = api + "/" + endpoint;
        Log.d("deleteSchedule", "Connecting url = " + url);

        OkHttpClient client = new OkHttpClient();
        try {
            JSONObject jObject = new JSONObject();
            jObject.put("_id", id);

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url,jObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d("deleteSchedule", "Response body = " + response.toString());
                                if(response.getInt("deletedResult") >=0)
                                    executeQuery(requestBody, query); //update new listView
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("deleteSchedule", "error = " + e.getMessage());
                            }
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("getAllActivityCategory", "Response body = " + error.toString());
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
