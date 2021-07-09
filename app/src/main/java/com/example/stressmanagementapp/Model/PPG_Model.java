package com.example.stressmanagementapp.Model;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import polar.com.sdk.api.model.PolarOhrPPGData;

public class PPG_Model {
    private float ppg0, ppg1, ppg2;
    private float ambient,ambient2;
    private long status;
    private String eventTime;
    private String userId;
    private String measureId;

    public PPG_Model(PolarOhrPPGData.PolarOhrPPGSample sample, String eventTime, String userId, String measureId) {
        this.ppg0=sample.ppg0;
        this.ppg1=sample.ppg1;
        this.ppg2=sample.ppg2;
        this.ambient=sample.ambient;
        this.ambient=sample.ambient2;
        this.status=sample.status;
        this.eventTime = eventTime;
        this.userId = userId;
        this.measureId = measureId;
    }

    public float getPpg0() {
        return ppg0;
    }

    public void setPpg0(float ppg0) {
        this.ppg0 = ppg0;
    }

    public float getPpg1() {
        return ppg1;
    }

    public void setPpg1(float ppg1) {
        this.ppg1 = ppg1;
    }

    public float getPpg2() {
        return ppg2;
    }

    public void setPpg2(float ppg2) {
        this.ppg2 = ppg2;
    }

    public float getAmbient() {
        return ambient;
    }

    public void setAmbient(float ambient) {
        this.ambient = ambient;
    }

    public float getAmbient2() {
        return ambient2;
    }

    public void setAmbient2(float ambient2) {
        this.ambient2 = ambient2;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMeasureId() {
        return measureId;
    }

    public void setMeasureId(String measureId) {
        this.measureId = measureId;
    }

    public JSONObject toJsonObject(){
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(this);
            JSONObject obj = new JSONObject(jsonString);
            return obj;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }
}
