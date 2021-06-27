package com.example.stressmanagementapp.Function.statistic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.stressmanagementapp.R;

public class StatisticFragment extends Fragment {

    private StatisticViewModel statisticViewModel;
    private ListView activityListView;
    private String api;
    private View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statisticViewModel =
                new ViewModelProvider(this).get(StatisticViewModel.class);
        root = inflater.inflate(R.layout.fragment_statistic_activity_result, container, false);

        api = getString(R.string.api_path);

        inuitMeasuredListView();
        return root;
    }

    private void inuitMeasuredListView() {
        activityListView = root.findViewById(R.id.measuredList);
//        String endpoint = "getMeasuredRecordList";
//        // Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = api + "/" + endpoint;
//        Log.d("getAllActivityCategory", "Connecting url = " + url);
//        List<String> list = new ArrayList<String>();
//        // Request a string response from the provided URL.
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONArray arr = new JSONArray(response);
//                            list.add("Category");
//                            for (int i = 0; i < arr.length(); i++) {
//                                list.add(arr.getString(i));
//                            }
//                            Log.d("getAllActivityCategory", "Response body = " + response);
//                            ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(ScheduleActivity.this,
//                                    android.R.layout.simple_spinner_dropdown_item, list);
//                            categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            categorySpinnerAdapter.notifyDataSetChanged();
//                            Log.d("getAllActivityCategory", "Set adapter");
//                            activityListView.setAdapter(categorySpinnerAdapter);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Log.e("getAllActivityCategory", "JSONException = " + e.getMessage());
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("getAllActivityCategory", "Response body = " + error.toString());
//            }
//        });
//
//        // Add the request to the RequestQueue.
//        queue.add(stringRequest);
//
//        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("getAllActivityCategory", "onItemSelected: " + adapterView.getSelectedItem().toString());
//                getCategorySelected = adapterView.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                Log.d("getAllActivityCategory", "onNothingSelected: ");
//            }
//        });
    }
//        activityBtn = root.findViewById(R.id.stat_activity_btn_container);
//        activityBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), statistic_activity.class);
//                startActivity(intent);
//            }
//
//        });
//        statisticViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
////                textView.setText(s);
//            }
//        });
//        return root;
//    }
}