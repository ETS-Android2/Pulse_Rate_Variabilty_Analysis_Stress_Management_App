package com.example.stressmanagementapp.ui.measure;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.stressmanagementapp.MainActivity;
import com.example.stressmanagementapp.MeasuringActivity;
import com.example.stressmanagementapp.R;
import com.example.stressmanagementapp.ScheduleActivity;
import com.example.stressmanagementapp.ViewScheduledMeasureActivity;
import com.example.stressmanagementapp.ui.setting.SettingViewModel;

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
                Intent intent = new Intent(getActivity(), MeasuringActivity.class);
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


        return root;
    }

}