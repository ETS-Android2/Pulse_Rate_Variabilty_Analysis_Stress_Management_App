package com.example.stressmanagementapp.Function.statistic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.stressmanagementapp.Chart.AbstractCustomLineChart;
import com.example.stressmanagementapp.Chart.HRLineChart.HRLineChart;
import com.example.stressmanagementapp.Chart.MyMarkerView;
import com.example.stressmanagementapp.Chart.PPILineChart.PPILineChart;
import com.example.stressmanagementapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StatisticFragment extends Fragment {

    private ListView activityListView;
    private String api;
    private View root;
    private String userId;
    private TextView highestBPMValue,avgBPMValue,lowestBPMValue;
    private TextView top1PosActivity,top2PosActivity,top3PosActivity;
    private TextView top1NegActivity,top2NegActivity,top3NegActivity;
    private LineChart avgHR_LineChart,avgPPI_LineChart;
    private AbstractCustomLineChart custom_avgHR_LineChart,custom_avgPPILineChart;
    private Button viewAllMeasuredRecordButton;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_statistic_activity_result, container, false);
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        //this.userId = sharedPref.getString("user_id", null);
        this.userId = "6058ba30ba59f62decefbe3d";
        api = getString(R.string.api_path);
        initUI();
        getAllStatisticData();
        return root;
    }

    private void initUI() {
        highestBPMValue  = root.findViewById(R.id.highestBPMValue);
        avgBPMValue  = root.findViewById(R.id.avgBPMValue);
        lowestBPMValue  = root.findViewById(R.id.lowestBPMValue);

        top1PosActivity  = root.findViewById(R.id.top1PosActivity);
        top2PosActivity  = root.findViewById(R.id.top2PosActivity);
        top3PosActivity  = root.findViewById(R.id.top3PosActivity);

        top1NegActivity  = root.findViewById(R.id.top1NegActivity);
        top2NegActivity  = root.findViewById(R.id.top2NegActivity);
        top3NegActivity  = root.findViewById(R.id.top3NegActivity);

        avgHR_LineChart = root.findViewById(R.id.restingAvgHRLineChart);
        custom_avgHR_LineChart = new HRLineChart(avgHR_LineChart);

        avgPPI_LineChart = root.findViewById(R.id.restingAvgPPIILineChart);
        custom_avgPPILineChart = new PPILineChart(avgPPI_LineChart);

        viewAllMeasuredRecordButton = root.findViewById(R.id.viewAllMeasuredRecordButton);
        viewAllMeasuredRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ReadAll_MeasuredRecord_By_filter.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });
    }

    private void getAllStatisticData(){
        getBPMStatistic();
        getHighestAndLowestStressLevelActivity();
        getRestingAvgHRAndPPI();
    }

    private void getRestingAvgHRAndPPI() {
        String endpoint = "getRestingAvgHRAndPPI";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = api + "/" + endpoint;
        Log.d("getRestingAvgHRAndPPI", "Connecting url = " + url);
        // Request a string response from the provided URL.
        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("userID", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("getRestingAvgHRAndPPI", "onResponse: "+response.toString());
                            JSONArray result = new JSONArray(response.getString("restingAvgHRArray").toString());
                            Log.d("getRestingAvgHRAndPPI", "onResponse: "+result.toString());
                            custom_avgHR_LineChart.chart.clear();
                            LineDataSet set1 =custom_avgHR_LineChart.createSet();
                            for(int i=0; i< result.length();i++){
                                JSONObject jsonObject = result.getJSONObject(i);
                                Log.d("getRestingAvgHRAndPPI", "onResponse: "+jsonObject.toString());
                                custom_avgHR_LineChart.addEntry(jsonObject);
                            }
                            MyMarkerView mv = new MyMarkerView(getContext(), R.layout.custom_marker_view);
                            custom_avgHR_LineChart.chart.setMarker(mv);

                            JSONArray result2 = new JSONArray(response.getString("restingAvgPPIArray").toString());
                            Log.d("getRestingAvgHRAndPPI", "onResponse: "+result2.toString());
                            custom_avgPPILineChart.chart.clear();

                            for(int i=0; i< result2.length();i++){
                                JSONObject jsonObject = result2.getJSONObject(i);
                                Log.d("getRestingAvgHRAndPPI", "onResponse: "+jsonObject.toString());
                                custom_avgPPILineChart.addEntry(jsonObject);
                            }
                            MyMarkerView mv2 = new MyMarkerView(getContext(), R.layout.custom_marker_view);

                            custom_avgPPILineChart.chart.setMarker(mv2);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("addNewActivityIfNotExist", "Error Response body = " + error.toString());
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
        // Add the request to the RequestQueue.
        queue.add(jsonObjReq);
    }

    private void getHighestAndLowestStressLevelActivity() {
        String endpoint = "getHighestAndLowestStressLevelActivity";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = api + "/" + endpoint;
        Log.d("getHighestAndLowestStressLevelActivity", "Connecting url = " + url);
        // Request a string response from the provided URL.
        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("userID", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("getHighestAndLowestStressLevelActivity", "onResponse: "+response.toString());
                            JSONArray result = new JSONArray(response.getString("lowestStressLevel_ActivityList").toString());
                            Log.d("getHighestAndLowestStressLevelActivity", "onResponse: "+result.toString());
                            top1PosActivity.setText(result.getString(0));
                            top2PosActivity.setText(result.getString(1));
                            top3PosActivity.setText(result.getString(2));

                            JSONArray result2 = new JSONArray(response.getString("highestStressLevel_ActivityList").toString());
                            Log.d("getHighestAndLowestStressLevelActivity", "onResponse: "+result2.toString());
                            top1NegActivity.setText(result2.getString(0));
                            top2NegActivity.setText(result2.getString(1));
                            top3NegActivity.setText(result2.getString(2));
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("addNewActivityIfNotExist", "Error Response body = " + error.toString());
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
        // Add the request to the RequestQueue.
        queue.add(jsonObjReq);
    }

    private void getBPMStatistic() {
        String endpoint = "getBPMStatistic";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = api + "/" + endpoint;
        Log.d("getBPMStatistic", "Connecting url = " + url);
        // Request a string response from the provided URL.
        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("userID", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("getBPMStatistic", "onResponse: "+response.toString());
                            JSONObject result = new JSONObject(response.getString("result").toString());
                            Log.d("getBPMStatistic", "onResponse: "+result.toString());
                            highestBPMValue.setText(String.format("%.0f BPM", result.getDouble("max")));
                            avgBPMValue.setText(String.format("%.0f BPM", result.getDouble("avg")));
                            lowestBPMValue.setText(String.format("%.0f BPM", result.getDouble("min")));

                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("addNewActivityIfNotExist", "Error Response body = " + error.toString());
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
        // Add the request to the RequestQueue.
        queue.add(jsonObjReq);
    }
}