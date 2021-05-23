package com.example.stressmanagementapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ScheduleActivity extends AppCompatActivity {
    private Spinner categorySpinner;
    private ImageView fromDateSelectorBtn,fromTimeSelectorBtn, toDateSelectorBtn,toTimeSelectorBtn;
    private EditText fromDateText,fromTimeText,toDateText,toTimeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_schedule_measure_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initCategorySpinner();

    }
    private void initCategorySpinner(){
        categorySpinner = (Spinner) findViewById(R.id.activityCategorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.activity_category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        initDateSelector();
    }
    private void initDateSelector(){
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
