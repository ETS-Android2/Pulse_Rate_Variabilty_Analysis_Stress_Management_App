package com.example.stressmanagementapp.Function.measure;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.stressmanagementapp.R;
import com.example.stressmanagementapp.Function.schedule.ScheduleActivity;
import com.example.stressmanagementapp.Function.schedule.ViewScheduledMeasureActivity;

public class MeasureFragment extends Fragment {

    private MeasureViewModel settingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingViewModel =
                new ViewModelProvider(this).get(MeasureViewModel.class);
        View root = inflater.inflate(R.layout.fragment_measure, container, false);
        final ImageButton btn_quickMeasurement = root.findViewById(R.id.quickMeasurmentBtn);
        final ImageButton btn_scheduleMeasureActivity = root.findViewById(R.id.scheduleMeasurmentBtn);
        final ImageButton btn_viewScheduledMeasureActivity = root.findViewById(R.id.viewScheduledMeasurmentBtn);
        btn_quickMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewMeasuringActivity.class);
                startActivity(intent);
            }
        });
        btn_scheduleMeasureActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                startActivity(intent);
            }
        });
        btn_viewScheduledMeasureActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewScheduledMeasureActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("You don't have Resting HR and PPI record for your profile, measure now?")
                .setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        Intent intent = new Intent(getActivity(), NewMeasuringActivity.class);
                        intent.putExtra("isMeasureRestingData",true);
                        startActivityForResult(intent,-1);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        return root;
    }
}