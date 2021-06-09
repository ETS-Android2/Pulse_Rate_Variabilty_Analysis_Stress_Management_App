package com.example.stressmanagementapp.Model;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import polar.com.sdk.api.model.PolarOhrPPGData;

public class PPG_Model implements Model {
    private int ppg_index;
    private float ppg0, ppg1, ppg2;
    private float min, median, max;
    private float ambient,ambient2;
    private float diff_MaxMed, diff_MedMin;
    private Date logDateTime;

    public PPG_Model(PolarOhrPPGData.PolarOhrPPGSample ppgSample, int sampleIndex, Date logDate) {
        this.ppg_index = sampleIndex;

        this.ppg0 = ppgSample.ppg0;
        this.ppg1 = ppgSample.ppg1;
        this.ppg2 = ppgSample.ppg2;

        this.min = calMin();
        this.median = calMedian();
        this.max=calMax();

        this.diff_MaxMed = calDiffMaxMed();
        this.diff_MedMin = calDiffMedMin();
        this.ambient = ppgSample.ambient;
        this.ambient2 = ppgSample.ambient2;

        this.logDateTime = logDate;
    }
    private float calDiffMaxMed() {
        return max-median;
    }

    private float calDiffMedMin() {
        return median-min;
    }



    public float calMin(){
        float currentMin = Math.min(Math.min(ppg0,ppg1), Math.min(ppg1,ppg2));
        return currentMin;
    }
    public float calMedian(){
        float currentMedian = Math.max(Math.min(ppg0,ppg1), Math.min(Math.max(ppg0,ppg1),ppg2));
        return currentMedian;
    }

    public float calMax(){
        float currentMin = Math.max(Math.max(ppg0,ppg1), Math.max(ppg1,ppg2));
        return currentMin;
    }

    public int getPpg_index() {
        return ppg_index;
    }

    public void setPpg_index(int ppg_index) {
        this.ppg_index = ppg_index;
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

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMedian() {
        return median;
    }

    public void setMedian(float median) {
        this.median = median;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getAmbient() {
        return ambient;
    }

    public void setAmbient(float ambient) {
        this.ambient = ambient;
    }

    public float getDiff_MaxMed() {
        return diff_MaxMed;
    }

    public void setDiff_MaxMed(float diff_MaxMed) {
        this.diff_MaxMed = diff_MaxMed;
    }

    public float getDiff_MedMin() {
        return diff_MedMin;
    }

    public void setDiff_MedMin(float diff_MedMin) {
        this.diff_MedMin = diff_MedMin;
    }

    public Date getLogDateTime() {
        return logDateTime;
    }

    public void setLogDateTime(Date logDateTime) {
        this.logDateTime = logDateTime;
    }

    @Override
    public String toString() {
        return "PPG_Model{" +
                "ppg_index=" + ppg_index +
                ", ppg0=" + ppg0 +
                ", ppg1=" + ppg1 +
                ", ppg2=" + ppg2 +
                ", min=" + min +
                ", median=" + median +
                ", max=" + max +
                ", ambient=" + ambient +
                ", diff_MaxMed=" + diff_MaxMed +
                ", diff_MedMin=" + diff_MedMin +
                ", logDateTime='" + logDateTime + '\'' +
                '}';
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
