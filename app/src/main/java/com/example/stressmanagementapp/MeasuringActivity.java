package com.example.stressmanagementapp;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stressmanagementapp.Dialog.LoadingDialog;
import com.example.stressmanagementapp.LineChart.AbstractCustomLineChart;
import com.example.stressmanagementapp.LineChart.PPGLineChart.PPGLineChart;
import com.example.stressmanagementapp.Model.PPG_Model;
import com.example.stressmanagementapp.Util.CustomThread;
import com.example.stressmanagementapp.Util.DateUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import org.json.JSONObject;
import org.reactivestreams.Publisher;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.socket.client.IO;
import io.socket.client.Socket;
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

    //Sensor API related
    private PolarBleApi api;
    private String DEVICE_ID = "719AF624"; // or bt address like F5:A7:B8:EF:7A:D1 // TODO replace with your device id
    private Disposable ppgDisposable;
    private Disposable autoConnectDisposable;

    //UI
    private Button startMeasureBtn, stopMeasureBtn;
    private final LoadingDialog loadingDialog = new LoadingDialog(MeasuringActivity.this);

    //Measuring data
    private boolean receiveFirstPPG,stopReceivePPGThread,stopUpdateLineChartThread;
    private int ppgIndex;
    private CustomThread PPG_DataThread;
    private CustomThread updatePPG_LineChartThread;

    private JSONObject sensorData;
    private Socket mSocket;

    private String userId, measureId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_measuring_realtime_update_chart_and_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
<<<<<<< HEAD
            mSocket = IO.socket("http://192.168.2.5:5000/realTimeData");
=======
            mSocket = IO.socket("http://192.168.1.12:5000/realTimeData");
>>>>>>> 62c2e15bad79791bbb50cb1e5304aeefc566ea98
            mSocket.connect();
            mSocket.emit("PPG_Signal",1);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        startMeasureBtn = findViewById(R.id.startMeasureBtn);
        stopMeasureBtn = findViewById(R.id.stopMeasureBtn);
        initMeasureRecord();
        initThreadState();
        setupLineChart();
        setupSensorApi();
        setupBtnListener();

    }
    private void initMeasureRecord(){
        this.userId="6058ba30ba59f62decefbe3d";
        this.measureId=String.format("%s_%s",userId, DateUtil.getDateStringInMeasuredRecord());
        Log.i(TAG, "Measure Id = "+this.measureId);
    }

    private void initThreadState() {
        stopReceivePPGThread = false;
        stopUpdateLineChartThread = false;
    }

    private void setupLineChart(){
        chart=findViewById(R.id.realTimeLineChart);
        ppgLineChart = new PPGLineChart(chart);
    }
    private void setupBtnListener() {
        this.startMeasureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    loadingDialog.startLoadingDialog();
                    stopReceivePPGThread=false;
                    stopUpdateLineChartThread=false;
                    autoConnectSensorAndStartMeasuring();
                } catch (Exception ex) {
                    loadingDialog.dismissDialog();
                    ex.printStackTrace();
                }
            }
        });
        this.stopMeasureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    try {
                        loadingDialog.startLoadingDialog();
                        api.disconnectFromDevice(DEVICE_ID);
                        PPG_DataThread.stopThread();
                        updatePPG_LineChartThread.stopThread();
                        initThreadState();
                        loadingDialog.dismissDialog();
                        mSocket.close();
                        ppgLineChart.chart.clear();
                    } catch (PolarInvalidArgument polarInvalidArgument) {
                        loadingDialog.dismissDialog();
                        polarInvalidArgument.printStackTrace();
                    }
                } catch (Exception ex) {
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
                loadingDialog.dismissDialog();
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
                subscribePPG();
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

    private void autoConnectSensorAndStartMeasuring() {

        if (autoConnectDisposable != null) {
            autoConnectDisposable.dispose();
            autoConnectDisposable = null;
        }
        autoConnectDisposable = api.autoConnectToDevice(-50, "180D", null).subscribe(
                () -> {
                    Log.d(TAG, "auto connect search complete");
                    autoConnectDisposable.dispose();
                    autoConnectDisposable = null;
                },
                throwable -> Log.e(TAG, "" + throwable.toString())
        );

    }

    private void subscribePPG() {
        if (ppgDisposable == null) {
            ppgIndex=0;
            receiveFirstPPG=false;
            loadingDialog.dismissDialog();
            feedMultiple();
        } else {
            ppgDisposable.dispose();
            ppgDisposable = null;
        }
    }
    private void feedMultiple() {
        if(updatePPG_LineChartThread!=null)
            updatePPG_LineChartThread.stopThread();
        if(PPG_DataThread!=null)
            PPG_DataThread.stopThread();

        PPG_DataThread=initPPGDataThread(); //Thread to update received data to PPG model object
        updatePPG_LineChartThread=initUpdatePPG_RealTimeLineChart(); // Thread to update line chart

        PPG_DataThread.startThread();
        updatePPG_LineChartThread.startThread();
    }

    private PPG_Model ppgModel;

    private CustomThread initPPGDataThread(){
        Runnable getPPGDataRunnable;
        Thread getPPGData_OnUIThread;

        getPPGDataRunnable = (new Runnable() {
            @Override
            public void run() {
                if( stopReceivePPGThread==false && ppgDisposable == null ) {
                    ppgDisposable = api.requestPpgSettings(DEVICE_ID).toFlowable().flatMap((Function<PolarSensorSetting, Publisher<PolarOhrPPGData>>) polarPPGSettings -> api.startOhrPPGStreaming(DEVICE_ID,polarPPGSettings.maxSettings())).subscribe(
                            polarOhrPPGData -> {
                                for( PolarOhrPPGData.PolarOhrPPGSample sample : polarOhrPPGData.samples ){
<<<<<<< HEAD
                                    SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                                    Date currentTime = new Date(System.currentTimeMillis());
                                    ppgModel=new PPG_Model(sample,dft.format(currentTime),userId,measureId);
                                    Log.d(TAG,"Detect ppgModel: "+ppgModel.toJsonObject().toString());
                                    try {
                                        mSocket.emit("PPG_Signal",ppgModel.toJsonObject().toString()+"\n");
=======
                                    SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                                    Date currentTime = Calendar.getInstance().getTime();
                                    ppgModel=new PPG_Model(sample,currentTime);
                                    Log.d(TAG,"Detect ppgModel: "+ppgModel.toJsonObject().toString());
                                    try {
                                        mSocket.emit("PPG_Signal",ppgModel.toJsonObject().toString());
>>>>>>> 62c2e15bad79791bbb50cb1e5304aeefc566ea98
                                        System.out.println("emitted");
                                    }catch (Exception ex){
                                        ex.printStackTrace();
                                        System.out.println("ERROR during emitting message");
                                    }
                                }
                                if(receiveFirstPPG==true && loadingDialog.getLoadingDialog().isShowing()==true)
                                    loadingDialog.dismissDialog();
                            },

                            throwable -> Log.e(TAG,"getPPGDataRunnable() "+throwable.getMessage()),
                            () -> Log.d(TAG,"complete")
                    );
//                    if (dataSampleID%5==0 && (ppgList!=null&&ppgList.size()>0)){
//                        ++slidingWindowIndex;
//                        sample.setPPGModelList(ppgList);
//                        SlidingWindow w = new SlidingWindow(sample);
//                        list.addNewSlidingWindow(w);
//                        sample = new PPG_DataSample(5);
//                    }
                }
            }
        });
        getPPGData_OnUIThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {

                    //Assign the getPPGDataRunnable to UI Thread
                    runOnUiThread(getPPGDataRunnable);

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return new CustomThread(getPPGDataRunnable,getPPGData_OnUIThread);
    }
    private CustomThread initUpdatePPG_RealTimeLineChart(){
        Runnable updateLineChartRunnable;
        Thread updateLineChart_OnUIThread;

        updateLineChartRunnable = (new Runnable() {
            @Override
            public void run() {
                if(stopReceivePPGThread==false  && ppgModel!=null) {
                    Log.d(TAG, "run: " + ppgModel.toString());
                    sensorData = new JSONObject();
                    ppgLineChart.addEntry(ppgModel);
                }
            }
        });

        updateLineChart_OnUIThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    //Assign the updateLineChartRunnable to UI Thread
                    runOnUiThread(updateLineChartRunnable);
                    if(i==999)
                        i=0;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        return new CustomThread(updateLineChartRunnable,updateLineChart_OnUIThread);
    }

}
