package com.example.stressmanagementapp.ui.statistic;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.stressmanagementapp.MeasuringActivity;
import com.example.stressmanagementapp.R;
import com.example.stressmanagementapp.ui.statistic.activity.statistic_activity;

public class StatisticFragment extends Fragment {

    private StatisticViewModel statisticViewModel;
    private ConstraintLayout activityBtn;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statisticViewModel =
                new ViewModelProvider(this).get(StatisticViewModel.class);
        View root = inflater.inflate(R.layout.fragment_statistic, container, false);
        activityBtn = root.findViewById(R.id.stat_activity_btn_container);
        activityBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), statistic_activity.class);
                startActivity(intent);
            }

        });
        statisticViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        return root;
    }
}