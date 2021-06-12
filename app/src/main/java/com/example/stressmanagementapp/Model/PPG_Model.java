package com.example.stressmanagementapp.Model;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import polar.com.sdk.api.model.PolarOhrPPGData;

public class PPG_Model implements Model {
    private float ppg0, ppg1, ppg2;
    private float ambient,ambient2;
    private long status;
    private List<Integer> ppgDataSamples;
    private Date logDateTime;
    private String userId;
    private String measureId;

    public PPG_Model(PolarOhrPPGData.PolarOhrPPGSample sample, Date logDateTime, String userId, String measureId) {
        this.ppg0=sample.ppg0;
        this.ppg1=sample.ppg1;
        this.ppg2=sample.ppg2;
        this.ambient=sample.ambient;
        this.ambient=sample.ambient2;
        this.status=sample.status;
        this.ppgDataSample=sample.ppgDataSamples;
        this.logDateTime = logDateTime;
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

    public Date getLogDateTime() {
        return logDateTime;
    }

    public void setLogDateTime(Date logDateTime) {
        this.logDateTime = logDateTime;
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
