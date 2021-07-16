package com.example.stressmanagementapp.Function.monitoring;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.stressmanagementapp.Function.schedule.ScheduleActivity;
import com.example.stressmanagementapp.Model.MeasuredResult;
import com.example.stressmanagementapp.R;

import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static io.realm.Realm.getApplicationContext;

public class MonitoringFragment extends Fragment {

    private Button monitorNowBtn,resetBtn;
    private EditText monitoringCode;
    private String apiPath;
    private String userId,mobileID;
    private TextView generatedCode;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_monitoring_record, container, false);
        SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        this.userId = sharedPref.getString("user_id", null);
        this.mobileID = sharedPref.getString("mobile_id", null);
        apiPath = getString(R.string.api_path);
        initUI(root);
        return root;
    }

    private void initUI(View root) {
        monitoringCode = root.findViewById(R.id.monitoringCode);
        monitorNowBtn = root.findViewById(R.id.monitorNowBtn);
        resetBtn = root.findViewById(R.id.resetBtn);
        generatedCode = root.findViewById(R.id.generatedCode);
        monitorNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode();

            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
                StringBuilder sb = new StringBuilder(20);
                Random random = new Random();
                for (int i = 0; i < 4; i++) {
                    char c = chars[random.nextInt(chars.length)];
                    sb.append(c);
                }
                String uuid = sb.toString();
                initMonitoringRelationship(uuid);
            }
        });

    }

    private void verifyCode() {

        String code = monitoringCode.getText().toString();
        String endpoint = "verifyCode";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = apiPath + "/" + endpoint;
        Log.d("verifyCode", "Connecting url = " + url);
        List<String> list = new ArrayList<String>();
        // Request a string response from the provided URL.
        JSONObject jsonRequestBody = new JSONObject();

        try {
            jsonRequestBody.put("pairCode", code);
            jsonRequestBody.put("monitorID", userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonRequestBody,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("verifyCode", "Response body = " + response.toString());
                            if (response.getString("result").equals("false")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("Wrong code, please try again")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //do things
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            } else {
                                Intent intent = new Intent(getContext(), MonitoringActivity.class);
                                intent.putExtra("targetID", response.getString("result"));
                                intent.putExtra("pairCode", code);
                                intent.putExtra("userId",userId);
                                startActivity(intent);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("initMonitoringRelationship", "Response body = " + error.toString());
            }
        });
        queue.add(jsonRequest);
    }

    private void initMonitoringRelationship(String code) {
        String endpoint = "initMonitoringRelationship";
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = apiPath + "/" + endpoint;
        Log.d("initMonitoringRelationship", "Connecting url = " + url);
        List<String> list = new ArrayList<String>();
        // Request a string response from the provided URL.
        JSONObject jsonRequestBody = new JSONObject();
        generatedCode.setText(code);
        try {
            jsonRequestBody.put("targetID", userId);
            jsonRequestBody.put("pairCode", code);
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("initMonitoringRelationship", "jsonRequestBody = " + jsonRequestBody.toString());
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonRequestBody,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("initMonitoringRelationship", "Response body = " + response.toString());
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                        builder.setMessage("Regenerated code")
//                                .setCancelable(false)
//                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        //do things
//                                    }
//                                });
//                        AlertDialog alert = builder.create();
//                        alert.show();
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("initMonitoringRelationship", "Response body = " + error.toString());
            }
        });
        queue.add(jsonRequest);
    }

}