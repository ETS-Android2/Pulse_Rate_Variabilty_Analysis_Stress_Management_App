package com.example.stressmanagementapp.ui.schedule;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.stressmanagementapp.R;
import com.example.stressmanagementapp.Util.CustomJsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewScheduledMeasureActivity extends AppCompatActivity {
    private Spinner scheduleFilterSpinner;
    private String userID;
    private ListView scheduledList;
    String api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_scheduled_measure_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        api = getString(R.string.api_path);
        setTitle("All Schedule");
        userID = "605995e57194bc0568afdec1";
        initSortByFilterSpinner();
        scheduledList = findViewById(R.id.scheduleList);
    }

    private void initSortByFilterSpinner() {
        scheduleFilterSpinner = (Spinner) findViewById(R.id.scheduleFilterSpinner);
        Resources res = getResources();
        ArrayAdapter<String> apModeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, res.getStringArray(R.array.scheduledRecordSortBy));
        Log.d("getAllActivityCategory", "Set adapter");
        scheduleFilterSpinner.setAdapter(apModeAdapter);
        scheduleFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                JSONObject requestBody = new JSONObject();
                String query;
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
        List<String> list = new ArrayList<String>();
        String queryLabel = "Query=" + queryName;

        CustomJsonRequest jsonArrayRequest = new CustomJsonRequest<JSONObject>(Request.Method.POST, url, requestBody, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for (int i = 0; i < response.length(); i++) {
                        list.add(response.getString(i));
                    }
                    // For populating list data
                    Log.d(queryLabel, "onResponse: [listSize]= "+list.size());
                    ScheduleListAdapter customCountryList = new ScheduleListAdapter(ViewScheduledMeasureActivity.this, list);
                    scheduledList.setAdapter(customCountryList);

                    scheduledList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Toast.makeText(getApplicationContext(), "You Selected " + position, Toast.LENGTH_SHORT).show();
                        }
                    });
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


}
