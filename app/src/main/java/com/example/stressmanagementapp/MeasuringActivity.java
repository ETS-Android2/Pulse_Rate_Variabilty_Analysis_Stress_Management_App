package com.example.stressmanagementapp;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stressmanagementapp.LineChart.AbstractCustomLineChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import org.reactivestreams.Publisher;

import java.util.UUID;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import polar.com.sdk.api.PolarBleApi;
import polar.com.sdk.api.PolarBleApiCallback;
import polar.com.sdk.api.PolarBleApiDefaultImpl;
import polar.com.sdk.api.errors.PolarInvalidArgument;
import polar.com.sdk.api.model.PolarDeviceInfo;
import polar.com.sdk.api.model.PolarExerciseEntry;
import polar.com.sdk.api.model.PolarOhrPPGData;
import polar.com.sdk.api.model.PolarSensorSetting;

public class MeasuringActivity extends AppCompatActivity {
    private LineChart chart;
    private AbstractCustomLineChart ppgLineChart;
    private Intent intent;
    public final static String TAG = MeasuringActivity.class.getSimpleName();
    private PolarBleApi api;
    private String DEVICE_ID = "719AF624"; // or bt address like F5:A7:B8:EF:7A:D1 // TODO replace with your device id
    private PolarExerciseEntry exerciseEntry;
    private Disposable broadcastDisposable;
    private Disposable ppgDisposable;
    private Disposable autoConnectDisposable;
    private ProgressBar spinner;
    private Button startMeasureBtn, stopMeasureBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_measuring_realtime_update_chart_and_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*Loading animation
         * https://github.com/ybq/Android-SpinKit
         * */
        /*ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);*/

        startMeasureBtn = findViewById(R.id.startMeasureBtn);
        stopMeasureBtn = findViewById(R.id.stopMeasureBtn);
        setupBtnListener();

        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);


    }
    private void enableLoadingProgressBar(){

    }
    private void setupBtnListener() {
        this.startMeasureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    setupSensorApi();
                    autoConnectSensor();
                    subscribePPG();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        this.stopMeasureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    try {
                        api.disconnectFromDevice(DEVICE_ID);
                        setupSensorApi();
                    } catch (PolarInvalidArgument polarInvalidArgument) {
                        polarInvalidArgument.printStackTrace();
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    private void setupSensorApi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        api = PolarBleApiDefaultImpl.defaultImplementation(this, PolarBleApi.ALL_FEATURES);
        api.setPolarFilter(false);
        api.setApiLogger(s -> Log.d(TAG, s));
        Log.d(TAG, "version: " + PolarBleApiDefaultImpl.versionInfo());
        initApiCallBack();
    }

    private void initApiCallBack() {
        api.setApiCallback(new PolarBleApiCallback() {
            @Override
            public void blePowerStateChanged(boolean powered) {
                Log.d(TAG, "BLE power: " + powered);
            }

            @Override
            public void deviceConnected(@NonNull PolarDeviceInfo polarDeviceInfo) {
                Log.d(TAG, "[deviceConnected] CONNECTED: " + polarDeviceInfo.deviceId);
                DEVICE_ID = polarDeviceInfo.deviceId;
            }

            @Override
            public void deviceConnecting(@NonNull PolarDeviceInfo polarDeviceInfo) {
                Log.d(TAG, "[deviceConnecting] CONNECTING: " + polarDeviceInfo.deviceId);
                DEVICE_ID = polarDeviceInfo.deviceId;
            }

            @Override
            public void deviceDisconnected(@NonNull PolarDeviceInfo polarDeviceInfo) {
                Log.d(TAG, "DISCONNECTED: " + polarDeviceInfo.deviceId);
                ppgDisposable = null;
            }

            @Override
            public void ecgFeatureReady(@NonNull String identifier) {
                Log.d(TAG, "ECG READY: " + identifier);
                // ecg streaming can be started now if needed
            }

            @Override
            public void accelerometerFeatureReady(@NonNull String identifier) {
                Log.d(TAG, "ACC READY: " + identifier);
                // acc streaming can be started now if needed
            }

            @Override
            public void ppgFeatureReady(@NonNull String identifier) {
                Log.d(TAG, "PPG READY: " + identifier);
                // ohr ppg can be started
            }

            @Override
            public void ppiFeatureReady(@NonNull String identifier) {
                Log.d(TAG, "PPI READY: " + identifier);
                // ohr ppi can be started
            }


            @Override
            public void hrFeatureReady(@NonNull String identifier) {
                Log.d(TAG, "HR READY: " + identifier);
                // hr notifications are about to start
            }

            @Override
            public void disInformationReceived(@NonNull String identifier, @NonNull UUID uuid, @NonNull String value) {
                Log.d(TAG, "uuid: " + uuid + " value: " + value);

            }

            @Override
            public void batteryLevelReceived(@NonNull String identifier, int level) {
                Log.d(TAG, "BATTERY LEVEL: " + level);

            }

            @Override
            public void polarFtpFeatureReady(@NonNull String s) {
                Log.d(TAG, "FTP ready");
            }
        });
    }

    private void autoConnectSensor() {
        if (autoConnectDisposable != null) {
            autoConnectDisposable.dispose();
            autoConnectDisposable = null;
        }
        autoConnectDisposable = api.autoConnectToDevice(-50, "180D", null).subscribe(
                () -> Log.d(TAG, "auto connect search complete"),
                throwable -> Log.e(TAG, "" + throwable.toString())
        );
    }
    private void subscribePPG(){
        if(ppgDisposable == null) {
            ppgDisposable = api.requestPpgSettings(DEVICE_ID).toFlowable().flatMap((Function<PolarSensorSetting, Publisher<PolarOhrPPGData>>) polarPPGSettings -> api.startOhrPPGStreaming(DEVICE_ID,polarPPGSettings.maxSettings())).subscribe(
                    polarOhrPPGData -> {
                        for( PolarOhrPPGData.PolarOhrPPGSample data : polarOhrPPGData.samples ){
                            Log.d(TAG,"    ppg0: " + data.ppg0 + " ppg1: " + data.ppg1 + " ppg2: " + data.ppg2 + " ambient: " + data.ambient);
                        }
                    },
                    throwable -> Log.e(TAG,""+throwable.getLocalizedMessage()),
                    () -> Log.d(TAG,"complete")
            );
        } else {
            ppgDisposable.dispose();
            ppgDisposable = null;
        }
    }
}
