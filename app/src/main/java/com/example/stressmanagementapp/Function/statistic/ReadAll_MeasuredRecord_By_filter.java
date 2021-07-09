package com.example.stressmanagementapp.Function.statistic;

import androidx.appcompat.app.AppCompatActivity;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.stressmanagementapp.Function.schedule.ScheduleListAdapter;
import com.example.stressmanagementapp.Function.schedule.ViewScheduledMeasureActivity;
import com.example.stressmanagementapp.R;
import com.example.stressmanagementapp.Util.CustomJsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReadAll_MeasuredRecord_By_filter extends AppCompatActivity implements QueryMeasuredResultListAdapter.CustomButtonListener{
    private Spinner recordFilterSpinner;
    private TextView noRecordMessage2;
    private String query;
    private JSONObject requestBody;
    private String api;
    private String userID;
    private Intent intent;
    private List<String> queryResultList;
    private ListView recordList2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_all_measured_record_by_filter_activity);
        api = getString(R.string.api_path);
        intent=getIntent();
        userID= intent.getStringExtra("userId");
        initSortByFilterSpinner();
        recordList2 = findViewById(R.id.recordList2);
    }

    private void initSortByFilterSpinner() {
        recordFilterSpinner = (Spinner) findViewById(R.id.recordFilterSpinner2);
        noRecordMessage2 = (TextView) findViewById(R.id.noRecordMessage2);
        noRecordMessage2.setVisibility(View.INVISIBLE);
        Resources res = getResources();
        ArrayAdapter<String> apModeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, res.getStringArray(R.array.measuredRecordSortBy));
        Log.d("getAllActivityCategory", "Set adapter");
        recordFilterSpinner.setAdapter(apModeAdapter);
        recordFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("onItemSelected", "onItemSelected: selected "+i);
                switch (i) {
                    case 1:
                        requestBody =  queryMeasuredRecordSortByDateASC();
                        query = "queryMeasuredRecordSortByDateASC";
                        break;

                    case 2:
                        requestBody = queryMeasuredRecordSortByActivityNameASC();
                        query = "queryMeasuredRecordSortByActivityNameASC";
                        break;
                    case 3:
                        requestBody = queryMeasuredRecordSortByActivityNameDSC();
                        query = "queryMeasuredRecordSortByActivityNameDSC";
                        break;

                    case 4:
                        requestBody = queryMeasuredRecordSortByCategoryNameASC();
                        query = "queryMeasuredRecordSortByCategoryNameASC";
                        break;
                    case 5:
                        requestBody = queryMeasuredRecordSortByCategoryNameDSC();
                        query = "queryMeasuredRecordSortByCategoryNameDSC";
                        break;

                    case 6:
                        requestBody = queryMeasuredRecordSortByAvgBpmASC();
                        query = "queryMeasuredRecordSortByAvgBpmASC";
                        break;
                    case 7:
                        requestBody = queryMeasuredRecordSortByAvgBpmDSC();
                        query = "queryMeasuredRecordSortByAvgBpmDSC";
                        break;

                    case 8:
                        requestBody = queryMeasuredRecordSortByAvgPpiASC();
                        query = "queryMeasuredRecordSortByAvgPpiASC";
                        break;
                    case 9:
                        requestBody = queryMeasuredRecordSortByAvgPpiDSC();
                        query = "queryMeasuredRecordSortByAvgPpiDSC";
                        break;

                    case 10:
                        requestBody = queryMeasuredRecordSortByAvgStressLevelASC();
                        query = "queryMeasuredRecordSortByAvgStressLevelASC";
                        break;
                    case 11:
                        requestBody = queryMeasuredRecordSortByAvgStressLevelDSC();
                        query = "queryMeasuredRecordSortByAvgStressLevelDSC";
                        break;

                    case 12:
                        requestBody = queryMeasuredRecordSortByLowestBpmASC();
                        query = "queryMeasuredRecordSortByLowestStressLevelASC";
                        break;
                    case 13:
                        requestBody = queryMeasuredRecordSortByLowestBpmDSC();
                        query = "queryMeasuredRecordSortByLowestStressLevelDSC";
                        break;

                    case 14:
                        requestBody = queryMeasuredRecordSortByLowestPpiASC();
                        query = "queryMeasuredRecordSortByLowestStressLevelASC";
                        break;
                    case 15:
                        requestBody = queryMeasuredRecordSortByLowestPpiDSC();
                        query = "queryMeasuredRecordSortByLowestStressLevelDSC";
                        break;

                    case 16:
                        requestBody = queryMeasuredRecordSortByLowestStressLevelASC();
                        query = "queryMeasuredRecordSortByLowestStressLevelASC";
                        break;
                    case 17:
                        requestBody = queryMeasuredRecordSortByLowestStressLevelDSC();
                        query = "queryMeasuredRecordSortByLowestStressLevelDSC";
                        break;

                    case 18:
                        requestBody = queryMeasuredRecordSortByHighestBpmASC();
                        query = "queryMeasuredRecordSortByHighestBpmASC";
                        break;
                    case 19:
                        requestBody = queryMeasuredRecordSortByHighestBpmDSC();
                        query = "queryMeasuredRecordSortByHighestBpmDSC";
                        break;

                    case 20:
                        requestBody = queryMeasuredRecordSortByHighestPpiASC();
                        query = "queryMeasuredRecordSortByHighestPpiASC";
                        break;
                    case 21:
                        requestBody = queryMeasuredRecordSortByHighestPpiDSC();
                        query = "queryMeasuredRecordSortByHighestPpiDSC";
                        break;

                    case 22:
                        requestBody = queryMeasuredRecordSortByHighestStressLevelASC();
                        query = "queryMeasuredRecordSortByHighestStressLevelASC";
                        break;
                    case 23:
                        requestBody = queryMeasuredRecordSortByHighestStressLevelDSC();
                        query = "queryMeasuredRecordSortByHighestStressLevelASC";
                        break;
                    default:
                        requestBody = queryScheduleSortByDateDSC();
                        query = "queryScheduleSortByDateDSC";
                }
                executeQuery(requestBody, query);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        recordFilterSpinner.setSelection(0);
    }

    private JSONObject queryMeasuredRecordSortByLowestPpiDSC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "lowest_PPI_Value");
            requestBody.put("sortOrder", "false");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryMeasuredRecordSortByLowestPpiASC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "lowest_PPI_Value");
            requestBody.put("sortOrder", "true");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryMeasuredRecordSortByHighestPpiDSC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "highest_PPI_Value");
            requestBody.put("sortOrder", "false");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryMeasuredRecordSortByHighestPpiASC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "highest_PPI_Value");
            requestBody.put("sortOrder", "true");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryMeasuredRecordSortByHighestBpmDSC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "highest_BPM_Value");
            requestBody.put("sortOrder", "false");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryMeasuredRecordSortByHighestBpmASC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "highest_BPM_Value");
            requestBody.put("sortOrder", "true");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryMeasuredRecordSortByHighestStressLevelASC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "highest_StressLevel_Value");
            requestBody.put("sortOrder", "true");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryMeasuredRecordSortByHighestStressLevelDSC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "highest_StressLevel_Value");
            requestBody.put("sortOrder", "false");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryMeasuredRecordSortByLowestBpmDSC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "lowest_BPM_Value");
            requestBody.put("sortOrder", "false");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryMeasuredRecordSortByLowestBpmASC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "lowest_BPM_Value");
            requestBody.put("sortOrder", "true");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryMeasuredRecordSortByLowestStressLevelASC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "lowest_StressLevel_Value");
            requestBody.put("sortOrder", "true");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryMeasuredRecordSortByLowestStressLevelDSC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "lowest_StressLevel_Value");
            requestBody.put("sortOrder", "false");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryMeasuredRecordSortByAvgStressLevelDSC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "avg_StressLevel_Value");
            requestBody.put("sortOrder", "false");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryMeasuredRecordSortByAvgStressLevelASC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "avg_StressLevel_Value");
            requestBody.put("sortOrder", "true");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryMeasuredRecordSortByAvgPpiDSC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "avg_PPI_Value");
            requestBody.put("sortOrder", "false");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryMeasuredRecordSortByAvgPpiASC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "avg_PPI_Value");
            requestBody.put("sortOrder", "true");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryMeasuredRecordSortByAvgBpmASC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "avg_BPM_Value");
            requestBody.put("sortOrder", "true");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryMeasuredRecordSortByAvgBpmDSC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "avg_BPM_Value");
            requestBody.put("sortOrder", "false");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryMeasuredRecordSortByCategoryNameDSC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "category");
            requestBody.put("sortOrder", "false");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryMeasuredRecordSortByCategoryNameASC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "category");
            requestBody.put("sortOrder", "true");
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject queryMeasuredRecordSortByActivityNameDSC() {
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

    private JSONObject queryMeasuredRecordSortByActivityNameASC() {
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

    private JSONObject queryMeasuredRecordSortByDateASC() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("userID", userID);
            requestBody.put("sortByField", "_id");
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
            requestBody.put("sortByField", "_id");
            requestBody.put("sortOrder", false);
            return requestBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void executeQuery(JSONObject requestBody, String queryName) {
        String endpoint = "getMeasuredRecordListByOwner_FieldName";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = api + "/" + endpoint;
        Log.d(queryName, "Connecting url = " + url+" RequestBody = "+requestBody.toString());
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
                    QueryMeasuredResultListAdapter measuredResultListAdapter = new QueryMeasuredResultListAdapter(ReadAll_MeasuredRecord_By_filter.this, queryResultList);
                    measuredResultListAdapter.setCustomButtonListener(ReadAll_MeasuredRecord_By_filter.this);
                    if(measuredResultListAdapter.getQueryResult().size()==0){
                        //no schedule
                        noRecordMessage2.setVisibility(View.VISIBLE);
                    }else{
                        noRecordMessage2.setVisibility(View.GONE);
                    }
                    recordList2.setAdapter(measuredResultListAdapter);

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
    public void onDeleteBtnClickListener(int position, String scheduleId) {

    }
}