package com.example.stressmanagementapp.ui.monitoring;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.stressmanagementapp.R;

public class MonitoringFragment extends Fragment {

    private MonitoringViewModel monitoringViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        monitoringViewModel =
                new ViewModelProvider(this).get(MonitoringViewModel.class);
        View root = inflater.inflate(R.layout.fragment_monitoring_norecord, container, false);
//        final TextView textView = root.findViewById(R.id.text_notifications);
        monitoringViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        return root;
    }
}