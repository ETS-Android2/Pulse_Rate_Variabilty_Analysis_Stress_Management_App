package com.example.stressmanagementapp.Function.measure;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stressmanagementapp.R;
import com.example.stressmanagementapp.Function.schedule.ScheduleActivity;
import com.example.stressmanagementapp.Function.schedule.ViewScheduledMeasureActivity;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

import static android.content.Context.TELEPHONY_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;
import static io.realm.Realm.getApplicationContext;

public class MeasureFragment extends Fragment {

    private String api;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_measure, container, false);
        final ImageButton btn_quickMeasurement = root.findViewById(R.id.quickMeasurmentBtn);
        final ImageButton btn_scheduleMeasureActivity = root.findViewById(R.id.scheduleMeasurmentBtn);
        final ImageButton btn_viewScheduledMeasureActivity = root.findViewById(R.id.viewScheduledMeasurmentBtn);
        api = getString(R.string.api_path);

        createUserIfNotExist();

        btn_quickMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                startActivity(intent);
            }
        });
        btn_scheduleMeasureActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), NewMeasuringActivity.class);
                        intent.putExtra("isMeasureRestingData",true);
                        startActivityForResult(intent,-1);
            }
        });
        btn_viewScheduledMeasureActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewScheduledMeasureActivity.class);
                startActivity(intent);
            }
        });
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setMessage("You don't have Resting HR and PPI record for your profile, measure now?")
//                .setCancelable(false)
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                })
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        //do things
//                        Intent intent = new Intent(getActivity(), NewMeasuringActivity.class);
//                        intent.putExtra("isMeasureRestingData",true);
//                        startActivityForResult(intent,-1);
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//
//        return root;
        return root;
    }
    public void createUserIfNotExist(){
        String android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("mobileId",android_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("create user id", String.format("onCreate: mobileID=%s",android_id));

        String endpoint = "checkUserIDExist";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = api + "/" + endpoint;
        Log.d("getAllActivityCategory", "Connecting url = " + url);
        // Request a string response from the provided URL.


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url,requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Context context = getActivity();
                        SharedPreferences sharedPref = context.getSharedPreferences(
                                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                       try {
                           String userId = response.getString("result");
                           editor.putString("user_id", userId);
                           editor.putString("mobile_id",android_id);
                           editor.commit();
                       } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("createUserIfNotExist", "onResponse: "+response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("createUserIfNotExist", "Response body = " + error.toString());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}