package com.example.stressmanagementapp.Model;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MeasuredResult {
    private String measureID;
    private int avg_bpm1,avg_bpm2,avg_bpm3;
    private double avg_ppi_1,avg_ppi_2,avg_ppi_3;
    private double avg_overall_bpm;
    private double avg_overall_ppi,min_ppi,max_ppi;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date timestamp;
    private int stressValue;
    private String stressLabel;


    public MeasuredResult() {
    }

    public MeasuredResult(String measureID, int avg_bpm1, int avg_bpm2, int avg_bpm3, double avg_ppi_1, double avg_ppi_2, double avg_ppi_3, double avg_overall_bpm, double avg_overall_ppi, double min_ppi, double max_ppi, Date timestamp, int stressValue, String stressLabel) {
        this.measureID = measureID;
        this.avg_bpm1 = avg_bpm1;
        this.avg_bpm2 = avg_bpm2;
        this.avg_bpm3 = avg_bpm3;
        this.avg_ppi_1 = avg_ppi_1;
        this.avg_ppi_2 = avg_ppi_2;
        this.avg_ppi_3 = avg_ppi_3;
        this.avg_overall_bpm = avg_overall_bpm;
        this.avg_overall_ppi = avg_overall_ppi;
        this.min_ppi = min_ppi;
        this.max_ppi = max_ppi;
        this.timestamp = timestamp;
        this.stressValue = stressValue;
        this.stressLabel = stressLabel;
    }

    public String getMeasureID() {
        return this.measureID;
    }

    public void setMeasureID(String measureID) {
        this.measureID = measureID;
    }

    public int getAvg_bpm1() {
        return this.avg_bpm1;
    }

    public void setAvg_bpm1(int avg_bpm1) {
        this.avg_bpm1 = avg_bpm1;
    }

    public int getAvg_bpm2() {
        return this.avg_bpm2;
    }

    public void setAvg_bpm2(int avg_bpm2) {
        this.avg_bpm2 = avg_bpm2;
    }

    public int getAvg_bpm3() {
        return this.avg_bpm3;
    }

    public void setAvg_bpm3(int avg_bpm3) {
        this.avg_bpm3 = avg_bpm3;
    }

    public double getAvg_ppi_1() {
        return this.avg_ppi_1;
    }

    public void setAvg_ppi_1(double avg_ppi_1) {
        this.avg_ppi_1 = avg_ppi_1;
    }

    public double getAvg_ppi_2() {
        return this.avg_ppi_2;
    }

    public void setAvg_ppi_2(double avg_ppi_2) {
        this.avg_ppi_2 = avg_ppi_2;
    }

    public double getAvg_ppi_3() {
        return this.avg_ppi_3;
    }

    public void setAvg_ppi_3(double avg_ppi_3) {
        this.avg_ppi_3 = avg_ppi_3;
    }

    public double getAvg_overall_bpm() {
        return this.avg_overall_bpm;
    }

    public void setAvg_overall_bpm(double avg_overall_bpm) {
        this.avg_overall_bpm = avg_overall_bpm;
    }

    public double getAvg_overall_ppi() {
        return this.avg_overall_ppi;
    }

    public void setAvg_overall_ppi(double avg_overall_ppi) {
        this.avg_overall_ppi = avg_overall_ppi;
    }

    public double getMin_ppi() {
        return this.min_ppi;
    }

    public void setMin_ppi(double min_ppi) {
        this.min_ppi = min_ppi;
    }

    public double getMax_ppi() {
        return this.max_ppi;
    }

    public void setMax_ppi(double max_ppi) {
        this.max_ppi = max_ppi;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getStressValue() {
        return this.stressValue;
    }

    public void setStressValue(int stressValue) {
        this.stressValue = stressValue;
    }

    public String getStressLabel() {
        return this.stressLabel;
    }

    public void setStressLabel(String stressLabel) {
        this.stressLabel = stressLabel;
    }

    public MeasuredResult measureID(String measureID) {
        setMeasureID(measureID);
        return this;
    }

    public MeasuredResult avg_bpm1(int avg_bpm1) {
        setAvg_bpm1(avg_bpm1);
        return this;
    }

    public MeasuredResult avg_bpm2(int avg_bpm2) {
        setAvg_bpm2(avg_bpm2);
        return this;
    }

    public MeasuredResult avg_bpm3(int avg_bpm3) {
        setAvg_bpm3(avg_bpm3);
        return this;
    }

    public MeasuredResult avg_ppi_1(double avg_ppi_1) {
        setAvg_ppi_1(avg_ppi_1);
        return this;
    }

    public MeasuredResult avg_ppi_2(double avg_ppi_2) {
        setAvg_ppi_2(avg_ppi_2);
        return this;
    }

    public MeasuredResult avg_ppi_3(double avg_ppi_3) {
        setAvg_ppi_3(avg_ppi_3);
        return this;
    }

    public MeasuredResult avg_overall_bpm(double avg_overall_bpm) {
        setAvg_overall_bpm(avg_overall_bpm);
        return this;
    }

    public MeasuredResult avg_overall_ppi(double avg_overall_ppi) {
        setAvg_overall_ppi(avg_overall_ppi);
        return this;
    }

    public MeasuredResult min_ppi(double min_ppi) {
        setMin_ppi(min_ppi);
        return this;
    }

    public MeasuredResult max_ppi(double max_ppi) {
        setMax_ppi(max_ppi);
        return this;
    }

    public MeasuredResult timestamp(Date timestamp) {
        setTimestamp(timestamp);
        return this;
    }

    public MeasuredResult stressValue(int stressValue) {
        setStressValue(stressValue);
        return this;
    }

    public MeasuredResult stressLabel(String stressLabel) {
        setStressLabel(stressLabel);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MeasuredResult)) {
            return false;
        }
        MeasuredResult measuredResult = (MeasuredResult) o;
        return Objects.equals(measureID, measuredResult.measureID) && avg_bpm1 == measuredResult.avg_bpm1 && avg_bpm2 == measuredResult.avg_bpm2 && avg_bpm3 == measuredResult.avg_bpm3 && avg_ppi_1 == measuredResult.avg_ppi_1 && avg_ppi_2 == measuredResult.avg_ppi_2 && avg_ppi_3 == measuredResult.avg_ppi_3 && avg_overall_bpm == measuredResult.avg_overall_bpm && avg_overall_ppi == measuredResult.avg_overall_ppi && min_ppi == measuredResult.min_ppi && max_ppi == measuredResult.max_ppi && Objects.equals(timestamp, measuredResult.timestamp) && stressValue == measuredResult.stressValue && Objects.equals(stressLabel, measuredResult.stressLabel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(measureID, avg_bpm1, avg_bpm2, avg_bpm3, avg_ppi_1, avg_ppi_2, avg_ppi_3, avg_overall_bpm, avg_overall_ppi, min_ppi, max_ppi, timestamp, stressValue, stressLabel);
    }

    @Override
    public String toString() {
        return "{" +
                " measureID='" + getMeasureID() + "'" +
                ", avg_bpm1='" + getAvg_bpm1() + "'" +
                ", avg_bpm2='" + getAvg_bpm2() + "'" +
                ", avg_bpm3='" + getAvg_bpm3() + "'" +
                ", avg_ppi_1='" + getAvg_ppi_1() + "'" +
                ", avg_ppi_2='" + getAvg_ppi_2() + "'" +
                ", avg_ppi_3='" + getAvg_ppi_3() + "'" +
                ", avg_overall_bpm='" + getAvg_overall_bpm() + "'" +
                ", avg_overall_ppi='" + getAvg_overall_ppi() + "'" +
                ", min_ppi='" + getMin_ppi() + "'" +
                ", max_ppi='" + getMax_ppi() + "'" +
                ", timestamp='" + getTimestamp() + "'" +
                ", stressValue='" + getStressValue() + "'" +
                ", stressLabel='" + getStressLabel() + "'" +
                "}";
    }



}
