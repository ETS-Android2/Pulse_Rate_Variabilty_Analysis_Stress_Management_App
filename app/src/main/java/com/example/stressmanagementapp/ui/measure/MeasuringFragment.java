package com.example.stressmanagementapp.ui.measure;

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
import com.example.stressmanagementapp.ui.monitoring.MonitoringViewModel;

public class MeasuringFragment extends Fragment {
    private View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_measuring_realtime_update_chart_and_data, container, false);

        return root;
    }
    private View getFragment(){
        return root;
    }
}
