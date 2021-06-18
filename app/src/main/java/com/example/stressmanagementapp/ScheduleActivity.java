package com.example.stressmanagementapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
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

import java.util.Calendar;
import android.provider.Settings.Secure;

import com.example.stressmanagementapp.Model.MeasuredRecordModel;
import com.example.stressmanagementapp.Util.DateUtil;

public class ScheduleActivity extends AppCompatActivity {
    private Spinner categorySpinner;
    private ImageView fromDateSelectorBtn,fromTimeSelectorBtn, toDateSelectorBtn,toTimeSelectorBtn;
    private EditText fromDateText,fromTimeText,toDateText,toTimeText;
    private ConstraintLayout fromDateLayout,fromDateContainer_nowLayout;
    private Switch startNowSwitch;
    private Button addScheduleBtn;
    private String userID, mobileID ;
    private String DEVICE_ID = "719AF624"; // or bt address like F5:A7:B8:EF:7A:D1 // TODO replace with your device id
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_schedule_measure_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userID = "605995e57194bc0568afdec1";
        mobileID= Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
        initCategorySpinner();
        initStartNowSwitch();
        initDateSelector();
        initAddScheduleBtn();

    }

    private void initAddScheduleBtn() {
        addScheduleBtn = findViewById(R.id.addScheduleBtn);
        addScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String measureID =String.format("%s_%s",userID, DateUtil.getDateStringInMeasuredRecord());


            }
        });
    }

    private void initStartNowSwitch() {
        startNowSwitch = findViewById(R.id.startNowSwitch);
        startNowSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(startNowSwitch.isChecked()){
                    fromDateLayout.setVisibility(View.INVISIBLE);
                    fromDateContainer_nowLayout.setVisibility(View.VISIBLE);
                }else{
                    fromDateLayout.setVisibility(View.VISIBLE);
                    fromDateContainer_nowLayout.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void initCategorySpinner(){
        categorySpinner = (Spinner) findViewById(R.id.activityCategorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.activity_category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }
    private void initDateSelector(){
        fromDateLayout = findViewById(R.id.fromDateContainer);
        fromDateContainer_nowLayout = findViewById(R.id.fromDateContainer_now);
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now .get(Calendar.SECOND);
        fromDateSelectorBtn = findViewById(R.id.fromDatePickerIcon);
        fromDateText = findViewById(R.id.editTextFormDate);
        fromTimeSelectorBtn = findViewById(R.id.fromTimePickerIcon);
        fromTimeText = findViewById(R.id.editTextFormTime);

        toDateSelectorBtn=findViewById(R.id.toDatePickerIcon);
        toDateText = findViewById(R.id.editTextToDate);
        toTimeSelectorBtn = findViewById(R.id.toTimePickerIcon);
        toTimeText = findViewById(R.id.editTextToTime);


        fromDateSelectorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int dayOfMonth, int monthOfYear, int year) {
                        fromDateText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        fromTimeSelectorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        ScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        fromTimeText.setText(hour + ":" + minute);
                    }
                },hour,minute,true
                );
                timePickerDialog.show();
            }
        });

        toDateSelectorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int dayOfMonth, int monthOfYear, int year) {
                        toDateText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        toTimeSelectorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        ScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        toTimeText.setText(hour + ":" + minute);
                    }
                },hour,minute,true
                );
                timePickerDialog.show();
            }
        });
    }



}
