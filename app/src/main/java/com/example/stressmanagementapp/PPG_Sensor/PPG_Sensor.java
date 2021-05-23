package com.example.stressmanagementapp.PPG_Sensor;

import androidx.appcompat.app.AppCompatActivity;

import polar.com.sdk.api.PolarBleApi;
import polar.com.sdk.api.PolarBleApiDefaultImpl;

public class PPG_Sensor extends AppCompatActivity {
    public static String DEVICE_ID = "218DDA23"; // or bt address like F5:A7:B8:EF:7A:D1 // TODO replace with your device id
    public static PolarBleApi api;

    public PPG_Sensor(){
        api = PolarBleApiDefaultImpl.defaultImplementation(this, PolarBleApi.ALL_FEATURES);
        api.setPolarFilter(false);
    }

    public static String getDeviceId() {
        return DEVICE_ID;
    }

    public static void setDeviceId(String deviceId) {
        DEVICE_ID = deviceId;
    }

    public static PolarBleApi getApi() {
        return api;
    }

    public static void setApi(PolarBleApi api) {
        com.example.stressmanagementapp.PPG_Sensor.PPG_Sensor.api = api;
    }
}
