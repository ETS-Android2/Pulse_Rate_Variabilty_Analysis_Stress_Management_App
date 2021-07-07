package com.example.stressmanagementapp.Model;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.bson.types.ObjectId;

public class MeasuredRecordModel {

    private ObjectId _id;       //MongoDB document ID
    private String userID;    //The userID in User collection, the owner of the record
    private String measureID;   //The measure ID

    private String deviceID;    //The deviceID for the device to pairing with PPG sensor and send the streaming data to server for analysis
    private String sensorID;    //The PPG sensor ID

    private String activityID;    //The activityID in Activity collection, the activity for the record
    private String activityName;    // The activity name
    private String category;        //The category of the record
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date startDateTime;     //The start date time for this record
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date endDateTime;       //The end date time for this record

    private double avg_PPI_Value;           // The average RRI value
    private double avg_StressLevel_Value;   // The average stress level value
    private double avg_FatigueLevel_Value;  // The average fatigue level value
    private double avg_BPM_Value;           // The average BPM level value

    private String avg_PPI_Label;            // The average RRI label
    private String avg_StressLevelLabel;     // The average stress level label
    private String avg_FatigueLevel_Label;   // The average fatigue level label
    private String avg_BPM_Label;            // The last RRI value

    private double highest_PPI_Value;          // The highest RRI value
    private double highest_StressLevel_Value;  // The highest stress level value
    private double highest_FatigueLevel_Value;  // The highest fatigue level value
    private double highest_BPM_Value;           // The highest bpm value

    private String highest_PPI_Label;           // The highest RRI label
    private String highest_StressLevelLabel;    // The highest stress level label
    private String highest_FatigueLevel_Label;  // The highest fatigue level label
    private String highest_BPM_Label;           // The highest bpm label

    private double lowest_PPI_Value;           // The lowest RRI value
    private double lowest_StressLevel_Value;   // The lowest stress level value
    private double lowest_FatigueLevel_Value;  // The lowest fatigue level value
    private double lowest_BPM_Value;           // The lowest bpm value

    private String lowest_PPI_Label;           // The lowest RRI label
    private String lowest_StressLevelLabel;    // The lowest stress level label
    private String lowest_FatigueLevel_Label;  // The lowest fatigue level label
    private String lowest_BPM_Label;           // The lowest bpm label


    public MeasuredRecordModel(ObjectId _id, String userID, String measureID, String deviceID, String sensorID, String activityID, String activityName, String category, Date startDateTime, Date endDateTime, double avg_PPI_Value, double avg_StressLevel_Value, double avg_FatigueLevel_Value, double avg_BPM_Value, String avg_PPI_Label, String avg_StressLevelLabel, String avg_FatigueLevel_Label, String avg_BPM_Label, double highest_PPI_Value, double highest_StressLevel_Value, double highest_FatigueLevel_Value, double highest_BPM_Value, String highest_PPI_Label, String highest_StressLevelLabel, String highest_FatigueLevel_Label, String highest_BPM_Label, double lowest_PPI_Value, double lowest_StressLevel_Value, double lowest_FatigueLevel_Value, double lowest_BPM_Value, String lowest_PPI_Label, String lowest_StressLevelLabel, String lowest_FatigueLevel_Label, String lowest_BPM_Label) {
        this._id = _id;
        this.userID = userID;
        this.measureID = measureID;
        this.deviceID = deviceID;
        this.sensorID = sensorID;
        this.activityID = activityID;
        this.activityName = activityName;
        this.category = category;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.avg_PPI_Value = avg_PPI_Value;
        this.avg_StressLevel_Value = avg_StressLevel_Value;
        this.avg_FatigueLevel_Value = avg_FatigueLevel_Value;
        this.avg_BPM_Value = avg_BPM_Value;
        this.avg_PPI_Label = avg_PPI_Label;
        this.avg_StressLevelLabel = avg_StressLevelLabel;
        this.avg_FatigueLevel_Label = avg_FatigueLevel_Label;
        this.avg_BPM_Label = avg_BPM_Label;
        this.highest_PPI_Value = highest_PPI_Value;
        this.highest_StressLevel_Value = highest_StressLevel_Value;
        this.highest_FatigueLevel_Value = highest_FatigueLevel_Value;
        this.highest_BPM_Value = highest_BPM_Value;
        this.highest_PPI_Label = highest_PPI_Label;
        this.highest_StressLevelLabel = highest_StressLevelLabel;
        this.highest_FatigueLevel_Label = highest_FatigueLevel_Label;
        this.highest_BPM_Label = highest_BPM_Label;
        this.lowest_PPI_Value = lowest_PPI_Value;
        this.lowest_StressLevel_Value = lowest_StressLevel_Value;
        this.lowest_FatigueLevel_Value = lowest_FatigueLevel_Value;
        this.lowest_BPM_Value = lowest_BPM_Value;
        this.lowest_PPI_Label = lowest_PPI_Label;
        this.lowest_StressLevelLabel = lowest_StressLevelLabel;
        this.lowest_FatigueLevel_Label = lowest_FatigueLevel_Label;
        this.lowest_BPM_Label = lowest_BPM_Label;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMeasureID() {
        return measureID;
    }

    public void setMeasureID(String measureID) {
        this.measureID = measureID;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getSensorID() {
        return sensorID;
    }

    public void setSensorID(String sensorID) {
        this.sensorID = sensorID;
    }

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public double getAvg_PPI_Value() {
        return avg_PPI_Value;
    }

    public void setAvg_PPI_Value(double avg_PPI_Value) {
        this.avg_PPI_Value = avg_PPI_Value;
    }

    public double getAvg_StressLevel_Value() {
        return avg_StressLevel_Value;
    }

    public void setAvg_StressLevel_Value(double avg_StressLevel_Value) {
        this.avg_StressLevel_Value = avg_StressLevel_Value;
    }

    public double getAvg_FatigueLevel_Value() {
        return avg_FatigueLevel_Value;
    }

    public void setAvg_FatigueLevel_Value(double avg_FatigueLevel_Value) {
        this.avg_FatigueLevel_Value = avg_FatigueLevel_Value;
    }

    public double getAvg_BPM_Value() {
        return avg_BPM_Value;
    }

    public void setAvg_BPM_Value(double avg_BPM_Value) {
        this.avg_BPM_Value = avg_BPM_Value;
    }

    public String getAvg_PPI_Label() {
        return avg_PPI_Label;
    }

    public void setAvg_PPI_Label(String avg_PPI_Label) {
        this.avg_PPI_Label = avg_PPI_Label;
    }

    public String getAvg_StressLevelLabel() {
        return avg_StressLevelLabel;
    }

    public void setAvg_StressLevelLabel(String avg_StressLevelLabel) {
        this.avg_StressLevelLabel = avg_StressLevelLabel;
    }

    public String getAvg_FatigueLevel_Label() {
        return avg_FatigueLevel_Label;
    }

    public void setAvg_FatigueLevel_Label(String avg_FatigueLevel_Label) {
        this.avg_FatigueLevel_Label = avg_FatigueLevel_Label;
    }

    public String getAvg_BPM_Label() {
        return avg_BPM_Label;
    }

    public void setAvg_BPM_Label(String avg_BPM_Label) {
        this.avg_BPM_Label = avg_BPM_Label;
    }

    public double getHighest_PPI_Value() {
        return highest_PPI_Value;
    }

    public void setHighest_PPI_Value(double highest_PPI_Value) {
        this.highest_PPI_Value = highest_PPI_Value;
    }

    public double getHighest_StressLevel_Value() {
        return highest_StressLevel_Value;
    }

    public void setHighest_StressLevel_Value(double highest_StressLevel_Value) {
        this.highest_StressLevel_Value = highest_StressLevel_Value;
    }

    public double getHighest_FatigueLevel_Value() {
        return highest_FatigueLevel_Value;
    }

    public void setHighest_FatigueLevel_Value(double highest_FatigueLevel_Value) {
        this.highest_FatigueLevel_Value = highest_FatigueLevel_Value;
    }

    public double getHighest_BPM_Value() {
        return highest_BPM_Value;
    }

    public void setHighest_BPM_Value(double highest_BPM_Value) {
        this.highest_BPM_Value = highest_BPM_Value;
    }

    public String getHighest_PPI_Label() {
        return highest_PPI_Label;
    }

    public void setHighest_PPI_Label(String highest_PPI_Label) {
        this.highest_PPI_Label = highest_PPI_Label;
    }

    public String getHighest_StressLevelLabel() {
        return highest_StressLevelLabel;
    }

    public void setHighest_StressLevelLabel(String highest_StressLevelLabel) {
        this.highest_StressLevelLabel = highest_StressLevelLabel;
    }

    public String getHighest_FatigueLevel_Label() {
        return highest_FatigueLevel_Label;
    }

    public void setHighest_FatigueLevel_Label(String highest_FatigueLevel_Label) {
        this.highest_FatigueLevel_Label = highest_FatigueLevel_Label;
    }

    public String getHighest_BPM_Label() {
        return highest_BPM_Label;
    }

    public void setHighest_BPM_Label(String highest_BPM_Label) {
        this.highest_BPM_Label = highest_BPM_Label;
    }

    public double getLowest_PPI_Value() {
        return lowest_PPI_Value;
    }

    public void setLowest_PPI_Value(double lowest_PPI_Value) {
        this.lowest_PPI_Value = lowest_PPI_Value;
    }

    public double getLowest_StressLevel_Value() {
        return lowest_StressLevel_Value;
    }

    public void setLowest_StressLevel_Value(double lowest_StressLevel_Value) {
        this.lowest_StressLevel_Value = lowest_StressLevel_Value;
    }

    public double getLowest_FatigueLevel_Value() {
        return lowest_FatigueLevel_Value;
    }

    public void setLowest_FatigueLevel_Value(double lowest_FatigueLevel_Value) {
        this.lowest_FatigueLevel_Value = lowest_FatigueLevel_Value;
    }

    public double getLowest_BPM_Value() {
        return lowest_BPM_Value;
    }

    public void setLowest_BPM_Value(double lowest_BPM_Value) {
        this.lowest_BPM_Value = lowest_BPM_Value;
    }

    public String getLowest_PPI_Label() {
        return lowest_PPI_Label;
    }

    public void setLowest_PPI_Label(String lowest_PPI_Label) {
        this.lowest_PPI_Label = lowest_PPI_Label;
    }

    public String getLowest_StressLevelLabel() {
        return lowest_StressLevelLabel;
    }

    public void setLowest_StressLevelLabel(String lowest_StressLevelLabel) {
        this.lowest_StressLevelLabel = lowest_StressLevelLabel;
    }

    public String getLowest_FatigueLevel_Label() {
        return lowest_FatigueLevel_Label;
    }

    public void setLowest_FatigueLevel_Label(String lowest_FatigueLevel_Label) {
        this.lowest_FatigueLevel_Label = lowest_FatigueLevel_Label;
    }

    public String getLowest_BPM_Label() {
        return lowest_BPM_Label;
    }

    public void setLowest_BPM_Label(String lowest_BPM_Label) {
        this.lowest_BPM_Label = lowest_BPM_Label;
    }

    @Override
    public String toString() {
        return "MeasuredRecordModel{" +
                "_id=" + _id +
                ", userID='" + userID + '\'' +
                ", measureID='" + measureID + '\'' +
                ", deviceID='" + deviceID + '\'' +
                ", sensorID='" + sensorID + '\'' +
                ", activityID='" + activityID + '\'' +
                ", activityName='" + activityName + '\'' +
                ", category='" + category + '\'' +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", avg_PPI_Value=" + avg_PPI_Value +
                ", avg_StressLevel_Value=" + avg_StressLevel_Value +
                ", avg_FatigueLevel_Value=" + avg_FatigueLevel_Value +
                ", avg_BPM_Value=" + avg_BPM_Value +
                ", avg_PPI_Label='" + avg_PPI_Label + '\'' +
                ", avg_StressLevelLabel='" + avg_StressLevelLabel + '\'' +
                ", avg_FatigueLevel_Label='" + avg_FatigueLevel_Label + '\'' +
                ", avg_BPM_Label='" + avg_BPM_Label + '\'' +
                ", highest_PPI_Value=" + highest_PPI_Value +
                ", highest_StressLevel_Value=" + highest_StressLevel_Value +
                ", highest_FatigueLevel_Value=" + highest_FatigueLevel_Value +
                ", highest_BPM_Value=" + highest_BPM_Value +
                ", highest_PPI_Label='" + highest_PPI_Label + '\'' +
                ", highest_StressLevelLabel='" + highest_StressLevelLabel + '\'' +
                ", highest_FatigueLevel_Label='" + highest_FatigueLevel_Label + '\'' +
                ", highest_BPM_Label='" + highest_BPM_Label + '\'' +
                ", lowest_PPI_Value=" + lowest_PPI_Value +
                ", lowest_StressLevel_Value=" + lowest_StressLevel_Value +
                ", lowest_FatigueLevel_Value=" + lowest_FatigueLevel_Value +
                ", lowest_BPM_Value=" + lowest_BPM_Value +
                ", lowest_PPI_Label='" + lowest_PPI_Label + '\'' +
                ", lowest_StressLevelLabel='" + lowest_StressLevelLabel + '\'' +
                ", lowest_FatigueLevel_Label='" + lowest_FatigueLevel_Label + '\'' +
                ", lowest_BPM_Label='" + lowest_BPM_Label + '\'' +
                '}';
    }
}
