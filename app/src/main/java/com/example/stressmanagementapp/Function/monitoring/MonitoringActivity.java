package com.example.stressmanagementapp.Function.monitoring;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.stressmanagementapp.Function.measure.MeasureResultListAdapter;
import com.example.stressmanagementapp.Function.measure.NewMeasuringActivity;
import com.example.stressmanagementapp.Model.MeasuredResult;
import com.example.stressmanagementapp.Model.PPG_Model_Sample;
import com.example.stressmanagementapp.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.bson.Document;
import org.bson.json.JsonParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class MonitoringActivity extends AppCompatActivity {
    private Timer timer;
    private String apiPath, targetID, pairCode, userId;
    private TextView activityName, activityNameLabel, lastHRValue, lastAvgPPI, lastMaxPPI, lastMinPPI, lastSampleTime, measureStartAt, measureEndDate, avgStressLevelValue, stressLevelLabel2, lastMaxHRValue, lastMinHRValue, maxStressLevelValue, minStressLevelValue, minStressLevelLabel, maxStressLevelLabel;
    ;

    private ListView measuredResultList;
    private List<MeasuredResult> measuredResultStringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_newmeasuring_activity);
        Intent intent = getIntent();
        targetID = intent.getStringExtra("targetID");
        pairCode = intent.getStringExtra("pairCode");
        userId = intent.getStringExtra("userId");
        Log.d("onCreate", "onCreate: targetID = " + targetID);
        apiPath = getString(R.string.api_path);
        initUI();
        timer = new Timer();
        timer.schedule(new reloadRecord(), 0, 10000);
    }

    private void initUI() {
        ConstraintLayout constraintLayout = findViewById(R.id.progressbarLayout);
        ProgressBar progressBar;
        progressBar = findViewById(R.id.circular_determinative_pb);
        progressBar.setVisibility(View.GONE);
        Button startMeasureBtn = findViewById(R.id.startMeasureBtn);
        startMeasureBtn.setVisibility(View.GONE);
        Button stopMeasureBtn = findViewById(R.id.stopMeasureBtn);
        stopMeasureBtn.setVisibility(View.GONE);
        TextView measureStartAtLabel, measureStartAt, willEndLabel, measureEndDate, textView17, measuredSampleNo, lasthrlabel, progress_tv, lastMeasureTimeLabel;
        activityNameLabel = findViewById(R.id.textView17);
        activityName = findViewById(R.id.measuredSampleNo);
        measuredSampleNo = findViewById(R.id.measuredSampleNo);
        measuredSampleNo.setVisibility(View.GONE);
        lasthrlabel = findViewById(R.id.lasthrlabel);
        lastMeasureTimeLabel = findViewById(R.id.lastMeasureTimeLabel);
        progress_tv = findViewById(R.id.progress_tv);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(R.id.lasthrlabel, ConstraintSet.TOP, R.id.textView17, ConstraintSet.BOTTOM, 0);
        constraintSet.connect(R.id.lasthrlabel, ConstraintSet.START, R.id.textView17, ConstraintSet.START, 200);
        constraintSet.connect(R.id.lastMeasureTimeLabel, ConstraintSet.START, R.id.lasthrlabel, ConstraintSet.START, 0);
        progress_tv.setVisibility(View.GONE);
        constraintSet.applyTo(constraintLayout);
        measureStartAtLabel = findViewById(R.id.measureStartAtLabel);
        measureStartAt = findViewById(R.id.measureStartAt);
        willEndLabel = findViewById(R.id.willEndLabel);
        measureEndDate = findViewById(R.id.measureEndDate);

        measureStartAtLabel.setVisibility(View.GONE);
        measureStartAt.setVisibility(View.GONE);
        willEndLabel.setVisibility(View.GONE);
        measureEndDate.setVisibility(View.GONE);


        measuredResultList = findViewById(R.id.measuredResultList);
        lastMaxHRValue = findViewById(R.id.lastMaxHRValue);
        lastMinHRValue = findViewById(R.id.lastMinHRValue);
        avgStressLevelValue = findViewById(R.id.avgStressLevelValue);
        maxStressLevelValue = findViewById(R.id.maxStressLevelValue);
        minStressLevelValue = findViewById(R.id.minStressLevelValue);
        measuredSampleNo = findViewById(R.id.measuredSampleNo);
        lastHRValue = findViewById(R.id.lastHRValue);
        lastAvgPPI = findViewById(R.id.lastAvgPPI);
        lastMaxPPI = findViewById(R.id.lastMaxPPI);
        lastMinPPI = findViewById(R.id.lastMinPPI);
        lastSampleTime = findViewById(R.id.lastSampleTime);
        measureStartAt = findViewById(R.id.measureStartAt);
        measureEndDate = findViewById(R.id.measureEndDate);
        measuredResultList = findViewById(R.id.measuredResultList);
        measuredResultStringList = new ArrayList<MeasuredResult>();
    }

    class reloadRecord extends TimerTask {
        public void run() {
            getLastMeasuredRecord();
        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        timer.schedule(new reloadRecord(), 0, 10000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private void getLastMeasuredRecord() {
        String endpoint = "getLastMeasuredRecord";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = apiPath + "/" + endpoint;
        Log.d("getLastMeasuredRecord", "Connecting url = " + url);
        List<String> list = new ArrayList<String>();
        // Request a string response from the provided URL.
        JSONObject jsonRequestBody = new JSONObject();
        try {
            jsonRequestBody.put("targetID", targetID);
            jsonRequestBody.put("monitorID", userId);
            jsonRequestBody.put("pairCode", pairCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("getLastMeasuredRecord", "jsonRequestBody = " + jsonRequestBody.toString());
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonRequestBody,
                new Response.Listener<JSONObject>() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("getLastMeasuredRecord", "Response body = " + response.toString());
                        try {
                            Document latestMeasuredResult = Document.parse(response.getString("result"));

                            Document doc = latestMeasuredResult;
                            Log.d("getOverallResultForTheRecord", "run: " + response.toString());
                            activityNameLabel.setText("Activity: " + doc.getString("activityName"));
                            Double avg_BPM_Value = doc.getDouble("avg_BPM_Value");
                            Double highest_BPM_Value = doc.getDouble("highest_BPM_Value");
                            Double lowest_BPM_Value = doc.getDouble("lowest_BPM_Value");

                            Double avg_PPI_Value = doc.getDouble("avg_PPI_Value");
                            Double highest_PPI_Value = doc.getDouble("highest_PPI_Value");
                            Double lowest_PPI_Value = doc.getDouble("lowest_PPI_Value");

                            double avg_StressLevel_Value = doc.getDouble("avg_StressLevel_Value");
                            double highest_StressLevel_Value = doc.getDouble("highest_StressLevel_Value");
                            double lowest_StressLevel_Value = doc.getDouble("lowest_StressLevel_Value");

                            Integer avg_bpm = avg_BPM_Value.intValue();
                            Integer max_bpm = highest_BPM_Value.intValue();
                            Integer min_bpm = lowest_BPM_Value.intValue();

                            Integer avg_ppi = avg_PPI_Value.intValue();
                            Integer max_ppi = highest_PPI_Value.intValue();
                            Integer min_ppi = lowest_PPI_Value.intValue();

                            double avgStress = avg_StressLevel_Value;
                            double maxStress = highest_StressLevel_Value;
                            double minStress = lowest_StressLevel_Value;

                            lastHRValue.setText(String.valueOf(avg_bpm));
                            lastMaxHRValue.setText(String.valueOf(max_bpm));
                            lastMinHRValue.setText(String.valueOf(min_bpm));

                            lastAvgPPI.setText(String.valueOf(avg_ppi));
                            lastMaxPPI.setText(String.valueOf(max_ppi));
                            lastMinPPI.setText(String.valueOf(min_ppi));
                            avgStressLevelValue.setText(String.format("%.2f", avgStress) + "%");
                            maxStressLevelValue.setText(String.format("%.2f", maxStress) + "%");
                            minStressLevelValue.setText(String.format("%.2f", minStress) + "%");

                            Log.d("lastRecordStressValue", String.format("onResponse: avgStress=%s, maxStress=%s, minStress=%s", avgStress, maxStress, minStress));

                            final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Hong_Kong"), Locale.US);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
                            lastSampleTime.setText(simpleDateFormat.format(calendar.getTime()));
                            JSONObject jResult = null;
                            jResult = new JSONObject(latestMeasuredResult.toJson());
                            JSONArray measuredResultArray = jResult.getJSONArray("measuredResult");
                            List<MeasuredResult> measuredResultStringList = new ArrayList<MeasuredResult>();
                            for (int i = 0; i < measuredResultArray.length(); i++) {
                                JSONObject resultObj = measuredResultArray.getJSONObject(i);
                                JSONObject timeObj = resultObj.getJSONObject("timestamp");
                                Log.d("getOverallResultForTheRecord", "run: " + resultObj.toString());
                                long correctTime = timeObj.getLong("$date");
                                ZonedDateTime zdt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(correctTime),
                                        ZoneId.systemDefault());
                                String realDate = zdt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                                Log.d("getOverallResultForTheRecord", "run: " + realDate);
//                                    SimpleDateFormat fromDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                //fromDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));

//                                    String newDate = fromDateFormat.format(date);
//                                    Log.d("getOverallResultForTheRecord", "run: " + newDate);
                                resultObj.put("timestamp", realDate);
                                String stressValue = String.format(String.format("%.2f", resultObj.getDouble("stressValue") * 100));
                                Log.d("getOverallResultForTheRecord", "run: " + stressValue);
                                resultObj.put("stressValue", stressValue);

//                                    MeasuredResult result = new MeasuredResult();
                                ObjectMapper m = new ObjectMapper();
                                MeasuredResult result = m.readValue(resultObj.toString(), MeasuredResult.class);
                                measuredResultStringList.add(result);
                            }

                            MeasureResultListAdapter customCountryList = new MeasureResultListAdapter(MonitoringActivity.this, measuredResultStringList);
                            measuredResultList.setAdapter(customCountryList);


                        } catch (JSONException e) {
                            Log.d("onResponse", "onResponse: " + "Not yet measured");
//                            measuredResultStringList.clear();
                            if (measuredResultStringList.size() == 0) {
                                measuredResultStringList.clear();
                                MeasureResultListAdapter customCountryList = new MeasureResultListAdapter(MonitoringActivity.this, measuredResultStringList);
                                measuredResultList.setAdapter(customCountryList);
                            }

                        }catch (JsonParseException e){
                            try {
                                if (response.getString("result") != null && response.getString("result").equals("invalid relationship")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MonitoringActivity.this);
                                    builder.setMessage("Pair code regenerated by Target, please enter code again.")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //do things
                                                    finish();
                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }
                            }catch (JSONException ex){

                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("initMonitoringRelationship", "Response body = " + error.toString());
            }
        });
        queue.add(jsonRequest);
    }
}