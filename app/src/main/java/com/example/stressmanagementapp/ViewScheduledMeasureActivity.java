package com.example.stressmanagementapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ViewScheduledMeasureActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_scheduled_measure_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
