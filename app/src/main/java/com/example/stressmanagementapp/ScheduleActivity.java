package com.example.stressmanagementapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.SymbolTable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.provider.Settings.Secure;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stressmanagementapp.Model.MeasuredRecordModel;
import com.example.stressmanagementapp.Util.DateUtil;
import com.example.stressmanagementapp.Util.HTTPRequestClientHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class ScheduleActivity extends AppCompatActivity {
    private Spinner categorySpinner;
    private ImageView fromDateSelectorBtn, fromTimeSelectorBtn, toDateSelectorBtn, toTimeSelectorBtn;
    private EditText eventNameText, fromDateText, fromTimeText, toDateText, toTimeText;
    private ConstraintLayout fromDateLayout, fromDateContainer_nowLayout;
    private Switch startNowSwitch;
    private Button addScheduleBtn, clearBtn;
    private String userID, mobileID, measureID, eventName, category;
    private String DEVICE_ID = "719AF624"; // or bt address like F5:A7:B8:EF:7A:D1 // TODO replace with your device id
    private Date startDate, endDate;
    private boolean isStartNow;
    private String getCategorySelected, activityName;
    private String api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_schedule_measure_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        api = getString(R.string.api_path);
        userID = "605995e57194bc0568afdec1";
        mobileID = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
        initEventNameEditText();
        initCategorySpinner();
        initStartNowSwitch();
        initDateSelector();
        initAddScheduleBtn();
        initClearBtn();

    }

    private void initEventNameEditText() {
        eventNameText = findViewById(R.id.inputEventName);
    }

    private void initClearBtn() {
        clearBtn = findViewById(R.id.resetInfoBtn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventNameText.setText(null);
                fromDateContainer_nowLayout.setVisibility(View.INVISIBLE);
                fromDateLayout.setVisibility(View.VISIBLE);
                fromDateText.setText(null);
                toDateText.setText(null);
            }
        });
    }

    private void initAddScheduleBtn() {
        addScheduleBtn = findViewById(R.id.addScheduleBtn);
        addScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStartNow == true) {
                    //start measure now, call measuring activity
                    startMeasuringNow();
                } else {
                    //Add to schedule collection
                    createNewSchedule();
                }
                measureID = String.format("%s_%s", userID, DateUtil.getDateStringInMeasuredRecord());
                //MeasuredRecordModel model = new MeasuredRecordModel(String userID,  measureID,  mobileID,  DEVICE_ID,  activityID,  activityName,  category,  startDateTime,  endDateTime);


            }
        });
    }
    private void createNewSchedule(){
        activityName=eventNameText.getText().toString();
        String endpoint = "addNewActivityIfNotExist";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(ScheduleActivity.this);
        String url = api + "/" + endpoint;
        Log.d("addNewActivityIfNotExist", "Connecting url = " + url);
        // Request a string response from the provided URL.
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("userID",userID);
            requestBody.put("category", getCategorySelected.toString());
            requestBody.put("name", eventNameText.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url,requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("addNewActivityIfNotExist", "Response body = " + response.get("activityID").toString());
                            Intent measureNow = new Intent(ScheduleActivity.this, MeasuringActivity.class);
                            measureNow.putExtra("userId", userID);
                            measureNow.putExtra("deviceId", mobileID);
                            measureNow.putExtra("sensorId", DEVICE_ID);
                            measureNow.putExtra("activityId", response.get("activityID").toString());
                            measureNow.putExtra("activityName", activityName);
                            Log.d("addNewActivityIfNotExist", "onResponse: "+endDate.toString());
                            measureNow.putExtra("endDateTime", endDate.toString());
                            startActivity(measureNow);
                        }catch (Exception e){
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("addNewActivityIfNotExist", "Error Response body = " + error.toString());
            }
        }){
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
    private void startMeasuringNow(){
        activityName=eventNameText.getText().toString();
        String endpoint = "addNewActivityIfNotExist";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(ScheduleActivity.this);
        String url = api + "/" + endpoint;
        Log.d("addNewActivityIfNotExist", "Connecting url = " + url);
        // Request a string response from the provided URL.
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("userID",userID);
            requestBody.put("category", getCategorySelected.toString());
            requestBody.put("name", eventNameText.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url,requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("addNewActivityIfNotExist", "Response body = " + response.get("activityID").toString());
                            Intent measureNow = new Intent(ScheduleActivity.this, MeasuringActivity.class);
                            measureNow.putExtra("userId", userID);
                            measureNow.putExtra("deviceId", mobileID);
                            measureNow.putExtra("sensorId", DEVICE_ID);
                            measureNow.putExtra("activityId", response.get("activityID").toString());
                            measureNow.putExtra("activityName", activityName);
                            Log.d("addNewActivityIfNotExist", "onResponse: "+toDateText.toString());
                            measureNow.putExtra("endDateTime","Measurement will end at: " +  toDateText.getText().toString());
                            startActivity(measureNow);
                        }catch (Exception e){
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("addNewActivityIfNotExist", "Error Response body = " + error.toString());
            }
        }){
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
    private void initStartNowSwitch() {
        startNowSwitch = findViewById(R.id.startNowSwitch);
        startNowSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (startNowSwitch.isChecked()) {
                    fromDateLayout.setVisibility(View.INVISIBLE);
                    fromDateContainer_nowLayout.setVisibility(View.VISIBLE);
                    isStartNow = true;
                } else {
                    fromDateLayout.setVisibility(View.VISIBLE);
                    fromDateContainer_nowLayout.setVisibility(View.INVISIBLE);
                    isStartNow = false;
                }
                Log.d("startNowSwitch", "onClick: " + isStartNow);
            }
        });
    }

    private void initCategorySpinner() {
        categorySpinner = (Spinner) findViewById(R.id.activityCategorySpinner);

        String endpoint = "getAllActivityCategory";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = api + "/" + endpoint;
        Log.d("getAllActivityCategory", "Connecting url = " + url);
        List<String> list = new ArrayList<String>();
        // Request a string response from the provided URL.


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arr = new JSONArray(response);
                            list.add("Category");
                            for (int i = 0; i < arr.length(); i++) {
                                list.add(arr.getString(i));
                            }
                            Log.d("getAllActivityCategory", "Response body = " + response);
                            ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(ScheduleActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item, list);
                            categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            categorySpinnerAdapter.notifyDataSetChanged();
                            Log.d("getAllActivityCategory", "Set adapter");
                            categorySpinner.setAdapter(categorySpinnerAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("getAllActivityCategory", "JSONException = " + e.getMessage());
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

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("getAllActivityCategory", "onItemSelected: " + adapterView.getSelectedItem().toString());
                getCategorySelected = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("getAllActivityCategory", "onNothingSelected: ");
            }
        });
    }

    private Calendar showDateTimeDialog(final EditText date_time_in) {
        final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Hong_Kong"), Locale.US);
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
                        date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(ScheduleActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }

        };
        new DatePickerDialog(ScheduleActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        return calendar;
    }

    private void initDateSelector() {
        fromDateLayout = findViewById(R.id.fromDateContainer);
        fromDateContainer_nowLayout = findViewById(R.id.fromDateContainer_now);
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        fromDateSelectorBtn = findViewById(R.id.fromDatePickerIcon);
        fromDateText = findViewById(R.id.editTextFormDate);
        fromTimeSelectorBtn = findViewById(R.id.fromTimePickerIcon);
        fromTimeText = findViewById(R.id.editTextFormTime);

        toDateSelectorBtn = findViewById(R.id.toDatePickerIcon);
        toDateText = findViewById(R.id.editTextToDate);
        toTimeSelectorBtn = findViewById(R.id.toTimePickerIcon);
        toTimeText = findViewById(R.id.editTextToTime);


        fromDateSelectorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = showDateTimeDialog(fromDateText);
                startDate = c.getTime();
//                DatePickerDialog datePickerDialog = new DatePickerDialog(
//                        ScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int dayOfMonth, int monthOfYear, int year) {
//                        fromDateText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                    }
//                }, year, month, day);
//                datePickerDialog.show();
            }
        });
//        fromTimeSelectorBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TimePickerDialog timePickerDialog = new TimePickerDialog(
//                        ScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
//                        fromTimeText.setText(hour + ":" + minute);
//                    }
//                }, hour, minute, true
//                );
//                timePickerDialog.show();
//            }
//        });

        toDateSelectorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = showDateTimeDialog(toDateText);
                endDate = c.getTime();
//                DatePickerDialog datePickerDialog = new DatePickerDialog(
//                        ScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int dayOfMonth, int monthOfYear, int year) {
//                        toDateText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                    }
//                }, year, month, day);
//                datePickerDialog.show();
            }
        });
//        toTimeSelectorBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TimePickerDialog timePickerDialog = new TimePickerDialog(
//                        ScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
//                        toTimeText.setText(hour + ":" + minute);
//                    }
//                }, hour, minute, true
//                );
//                timePickerDialog.show();
//            }
//        });
    }


}
