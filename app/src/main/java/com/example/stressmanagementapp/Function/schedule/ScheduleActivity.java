package com.example.stressmanagementapp.Function.schedule;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.provider.Settings.Secure;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stressmanagementapp.Function.MainActivity;
import com.example.stressmanagementapp.Function.measure.MeasureFragment;
import com.example.stressmanagementapp.Function.measure.MeasuringActivity;
import com.example.stressmanagementapp.Function.measure.NewMeasuringActivity;
import com.example.stressmanagementapp.R;
import com.example.stressmanagementapp.Util.DateUtil;

import org.bson.BsonObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScheduleActivity extends AppCompatActivity {
    private Spinner categorySpinner;
    private ImageView fromDateSelectorBtn, fromTimeSelectorBtn, toDateSelectorBtn, toTimeSelectorBtn;
    private EditText eventNameText, fromDateText, fromTimeText, toDateText, toTimeText;
    private ConstraintLayout fromDateLayout, fromDateContainer_nowLayout;
    private Switch startNowSwitch;
    private Button addScheduleBtn, clearBtn;
    private String userID, mobileID, measureID, eventName, category, activityID;
    private String DEVICE_ID = "719AF624"; // or bt address like F5:A7:B8:EF:7A:D1 // TODO replace with your device id
    private Date startDate, endDate;
    private boolean isStartNow;
    private String getCategorySelected, activityName;
    private String api;
    private String startDateStr, endDateStr;
    private ArrayAdapter<String> categorySpinnerAdapter;
    private List<String> categoryList;
    private String action = "";
    private Intent intent;
    private String _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_schedule_measure_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        api = getString(R.string.api_path);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        this.userID = sharedPref.getString("user_id",null);
        this.mobileID = sharedPref.getString("mobile_id",null);
        intent = getIntent();
        action = intent.getStringExtra("action");
        initEventNameEditText();
        initCategorySpinner();
        initStartNowSwitch();
        initDateSelector();
        initAddScheduleBtn();
        initClearBtn();
        updateUIForIntent();  //Add or Edit


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (action != null && action.equals("edit")) {
                    Intent intent = new Intent(this, ViewScheduledMeasureActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUIForIntent() {
        if (action != null && action.equals("edit")) {
            setTitle("Edit Schedule");

            String eventName = intent.getStringExtra("activityName");
            String getStartDate = intent.getStringExtra("startDateStr");
            String getEndDate = intent.getStringExtra("endDateStr");
            getCategorySelected = intent.getStringExtra("category");
            try {
                _id = intent.getStringExtra("_id");
                JSONObject idObject = new JSONObject(_id);
                _id = idObject.getString("$oid");
            } catch (Exception e) {
                e.printStackTrace();
            }

            eventNameText.setText(eventName);

            try {
                SimpleDateFormat fromDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                fromDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date oldFromDate = fromDateFormat.parse(getStartDate);
                Date oldToDate = fromDateFormat.parse(getEndDate);


                SimpleDateFormat toDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                toDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                String newFromDate = toDateFormat.format(oldFromDate);
                String newToDate = toDateFormat.format(oldToDate);

                fromDateText.setText(newFromDate);
                toDateText.setText(newToDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            addScheduleBtn.setText("UPDATE");
            clearBtn.setText("RESET");


        } else {
            setTitle("Schedule");


        }
//        Intent editSchedule = new Intent(ViewScheduledMeasureActivity.this, ScheduleActivity.class);
//        editSchedule.putExtra("action", "edit");
//        editSchedule.putExtra("activityName", activityName);
//        editSchedule.putExtra("category", category);
//        editSchedule.putExtra("startDateStr", startDateStr);
//        editSchedule.putExtra("endDateStr", endDateStr);
//        editSchedule.putExtra("status", status);
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
                isStartNow = false;
                fromDateContainer_nowLayout.setVisibility(View.INVISIBLE);
                fromDateLayout.setVisibility(View.VISIBLE);
                startNowSwitch.setChecked(false);
                fromDateText.setText(null);
                toDateText.setText(null);
                categorySpinner.setSelection(0);
            }
        });
    }

    private void initAddScheduleBtn() {
        addScheduleBtn = findViewById(R.id.addScheduleBtn);
        addScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start measure now, call measuring activity
                prepareSchedulingProcedure();
                measureID = String.format("%s_%s", userID, DateUtil.getDateStringInMeasuredRecord());
                //MeasuredRecordModel model = new MeasuredRecordModel(String userID,  measureID,  mobileID,  DEVICE_ID,  activityID,  activityName,  category,  startDateTime,  endDateTime);


            }
        });
    }

    private void createNewSchedule() {
        activityName = eventNameText.getText().toString();
        String endpoint = "addSchedule";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(ScheduleActivity.this);
        String url = api + "/" + endpoint;
        Log.d("addSchedule", "Connecting url = " + url);
        // Request a string response from the provided URL.
        JSONObject requestBody = new JSONObject();

        try {
//            SimpleDateFormat fromDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            fromDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//            Date oldFromDate = fromDateFormat.parse(startDateStr);
//            Date oldToDate = fromDateFormat.parse(endDateStr);
//
//
//            SimpleDateFormat toDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//            toDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//            String newFromDate = toDateFormat.format(oldFromDate);
//            String newToDate = toDateFormat.format(oldToDate);

            requestBody.put("ownerInUserCollection", userID);
            requestBody.put("activityIdInActivityCollection", activityID);
            requestBody.put("activityName", activityName);
            requestBody.put("category", getCategorySelected);
            Log.d("addSchedule", "startDateTime = " + startDateStr);
            Log.d("addSchedule", "endDateTime = " + endDateStr);
            requestBody.put("startDateTime", startDateStr);
            requestBody.put("endDateTime", endDateStr);


            requestBody.put("status", "Scheduled");
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
                            Log.d("createNewSchedule", "Response body = " + response.get("_id for inserted schedule").toString());
                            measureID = response.get("_id for inserted schedule").toString();
                            AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleActivity.this);
                            builder.setMessage("Created new schedule")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do things
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }

    private void prepareSchedulingProcedure() {

        activityName = eventNameText.getText().toString();
        String endpoint = "addNewActivityIfNotExist";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(ScheduleActivity.this);
        String url = api + "/" + endpoint;
        Log.d("addNewActivityIfNotExist", "Connecting url = " + url);
        // Request a string response from the provided URL.
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("userID", userID);
            requestBody.put("category", getCategorySelected.toString());
            requestBody.put("name", eventNameText.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("addNewActivityIfNotExist", "Response body = " + response.get("activityID").toString());
                            activityID = response.get("activityID").toString();
                            if (action!=null && action.equals("edit")) {
                                updateSchedule();
                                if (isStartNow == true) {
                                    startMeasuringNow();
                                }
                            }else{
                                if (isStartNow == true) {
                                    //start measure now, call measuring activity
                                    startMeasuringNow();
                                } else if (isStartNow == false) {
                                    //Add to schedule collection
                                    createNewSchedule();
                                }
                            }


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

    private void updateSchedule() {
        activityName = eventNameText.getText().toString();
        String endpoint = "updateSchedule";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(ScheduleActivity.this);
        String url = api + "/" + endpoint;
        Log.d("updateSchedule", "Connecting url = " + url);
        // Request a string response from the provided URL.
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("_id", _id);
            requestBody.put("ownerInUserCollection", userID);
            Log.d("updateSchedule", "_id = " + _id+" activityID_oid="+activityID);
            requestBody.put("activityIdInActivityCollection", activityID);
            requestBody.put("activityName", activityName);
            requestBody.put("category", getCategorySelected);
            requestBody.put("status","Scheduled");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            Date from = simpleDateFormat.parse(fromDateText.getText().toString());
            Date to = simpleDateFormat.parse(fromDateText.getText().toString());

            SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            startDateStr = dbDateFormat.format(from).toString();
            endDateStr = dbDateFormat.format(to).toString();

            Log.d("updateSchedule", "startDateTime = " + startDateStr);
            Log.d("updateSchedule", "endDateTime = " + endDateStr);
            requestBody.put("startDateTime", startDateStr);
            requestBody.put("endDateTime", endDateStr);
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
                            String result = response.get("result").toString();
                            Log.d("updateSchedule", "Response body = " + result);
                            AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleActivity.this);
                            builder.setMessage("Updated schedule")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do things
                                            Intent intent = new Intent(ScheduleActivity.this, ViewScheduledMeasureActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
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

    private void startMeasuringNow() {
        Intent measureNow = new Intent(ScheduleActivity.this, NewMeasuringActivity.class);
        measureNow.putExtra("userId", userID);
        measureNow.putExtra("deviceId", mobileID);
        measureNow.putExtra("sensorId", DEVICE_ID);
        measureNow.putExtra("activityId", activityID);
        measureNow.putExtra("activityName", activityName);
        measureNow.putExtra("category",getCategorySelected);
        Log.d("addNewActivityIfNotExist", "onResponse: " + toDateText.toString());
        measureNow.putExtra("endDateTime", toDateText.getText().toString());
        startActivity(measureNow);
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
                } else if (startNowSwitch.isChecked() == false) {
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
        categoryList = new ArrayList<String>();
        // Request a string response from the provided URL.


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arr = new JSONArray(response);
                            categoryList.add("Category");
                            for (int i = 0; i < arr.length(); i++) {
                                categoryList.add(arr.getString(i));
                            }
                            Log.d("getAllActivityCategory", "Response body = " + response);
                            if (categoryList != null) {
                                categorySpinnerAdapter = new ArrayAdapter<String>(ScheduleActivity.this,
                                        android.R.layout.simple_spinner_dropdown_item, categoryList);
                                categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                categorySpinnerAdapter.notifyDataSetChanged();
                                Log.d("getAllActivityCategory", "Set adapter");
                                categorySpinner.setAdapter(categorySpinnerAdapter);
                                if (action != null && action.equals("edit")) {
                                    int categoryPosition = -1;
                                    int iterator = 0;
                                    String category = intent.getStringExtra("category");
                                    Log.d("checkIntent", "checkIntent: categoryList size =" + categoryList.size());
                                    Log.d("checkIntent", "checkIntent: category name =" + category);
                                    for (String categoryName : categoryList) {
                                        if (categoryName.equals(category)) {
                                            categoryPosition = iterator;
                                            break;
                                        } else {
                                            iterator++;
                                        }
                                    }
                                    Log.d("checkIntent", "checkIntent: default selected spinner =" + categoryList.get(categoryPosition));
                                    categorySpinner.setSelection(categoryPosition);
                                }
                            }

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

    private Calendar showDateTimeDialog(final EditText date_time_in, boolean isStartDate) {
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

                        SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                        if (isStartDate == true) {
                            startDateStr = dbDateFormat.format(calendar.getTime());
                        } else if (isStartDate == false) {
                            endDateStr = dbDateFormat.format(calendar.getTime());
                        }

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
                Calendar c = showDateTimeDialog(fromDateText, true);
                startDate = c.getTime();
            }
        });


        toDateSelectorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = showDateTimeDialog(toDateText, false);
                endDate = c.getTime();
            }
        });

    }


}
