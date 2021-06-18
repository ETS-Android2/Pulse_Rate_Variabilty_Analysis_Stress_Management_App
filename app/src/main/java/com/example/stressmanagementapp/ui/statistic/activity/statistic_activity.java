package com.example.stressmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.stressmanagementapp.ui.main.StatisticActivityFragment;

public class statistic_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic_activity_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, StatisticActivityFragment.newInstance())
                    .commitNow();
        }
    }
}