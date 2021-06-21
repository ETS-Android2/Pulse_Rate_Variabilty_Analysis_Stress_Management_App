package com.example.stressmanagementapp.ui.statistic.activity;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;


import com.example.stressmanagementapp.R;

import java.util.ArrayList;

public class ActivityStatisticAdapter extends ArrayAdapter<ActivityStatisticModel> implements View.OnClickListener {
    private ArrayList<ActivityStatisticModel> dataSet;
    Context mContext;

    public ActivityStatisticAdapter(ArrayList<ActivityStatisticModel> data, Context context) {
        super(context, R.layout.row_item_statistic, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View view) {

    }
}
