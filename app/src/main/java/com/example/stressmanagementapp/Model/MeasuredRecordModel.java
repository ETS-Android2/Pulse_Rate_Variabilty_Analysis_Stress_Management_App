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

    private double avg_RRI_Value;           // The average RRI value
    private double avg_StressLevel_Value;   // The average stress level value
    private double avg_FatigueLevel_Value;  // The average fatigue level value
    private double avg_BPM_Value;           // The average BPM level value

    private String avg_RRI_Label;            // The average RRI label
    private String avg_StressLevelLabel;     // The average stress level label
    private String avg_FatigueLevel_Label;   // The average fatigue level label
    private String avg_BPM_Label;            // The last RRI value

    private double highest_RRI_Value;          // The highest RRI value
    private double highest_StressLevel_Value;  // The highest stress level value
    private double highest_FatigueLevel_Value;  // The highest fatigue level value
    private double highest_BPM_Value;           // The highest bpm value

    private String highest_RRI_Label;           // The highest RRI label
    private String highest_StressLevelLabel;    // The highest stress level label
    private String highest_FatigueLevel_Label;  // The highest fatigue level label
    private String highest_BPM_Label;           // The highest bpm label

    private double lowest_RRI_Value;           // The lowest RRI value
    private double lowest_StressLevel_Value;   // The lowest stress level value
    private double lowest_FatigueLevel_Value;  // The lowest fatigue level value
    private double lowest_BPM_Value;           // The lowest bpm value

    private String lowest_RRI_Label;           // The lowest RRI label
    private String lowest_StressLevelLabel;    // The lowest stress level label
    private String lowest_FatigueLevel_Label;  // The lowest fatigue level label
    private String lowest_BPM_Label;           // The lowest bpm label

    public MeasuredRecordModel(String userID, String measureID, String deviceID, String sensorID, String activityID, String activityName, String category, Date startDateTime, Date endDateTime) {
        this.userID = userID;
        this.measureID = measureID;
        this.deviceID = deviceID;
        this.sensorID = sensorID;
        this.activityID = activityID;
        this.activityName = activityName;
        this.category = category;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public MeasuredRecordModel(ObjectId _id, String userID, String measureID, String deviceID, String sensorID, String activityID, String activityName, String category, Date startDateTime, Date endDateTime, double avg_RRI_Value, double avg_StressLevel_Value, double avg_FatigueLevel_Value, double avg_BPM_Value, String avg_RRI_Label, String avg_StressLevelLabel, String avg_FatigueLevel_Label, String avg_BPM_Label, double highest_RRI_Value, double highest_StressLevel_Value, double highest_FatigueLevel_Value, double highest_BPM_Value, String highest_RRI_Label, String highest_StressLevelLabel, String highest_FatigueLevel_Label, String highest_BPM_Label, double lowest_RRI_Value, double lowest_StressLevel_Value, double lowest_FatigueLevel_Value, double lowest_BPM_Value, String lowest_RRI_Label, String lowest_StressLevelLabel, String lowest_FatigueLevel_Label, String lowest_BPM_Label) {
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
        this.avg_RRI_Value = avg_RRI_Value;
        this.avg_StressLevel_Value = avg_StressLevel_Value;
        this.avg_FatigueLevel_Value = avg_FatigueLevel_Value;
        this.avg_BPM_Value = avg_BPM_Value;
        this.avg_RRI_Label = avg_RRI_Label;
        this.avg_StressLevelLabel = avg_StressLevelLabel;
        this.avg_FatigueLevel_Label = avg_FatigueLevel_Label;
        this.avg_BPM_Label = avg_BPM_Label;
        this.highest_RRI_Value = highest_RRI_Value;
        this.highest_StressLevel_Value = highest_StressLevel_Value;
        this.highest_FatigueLevel_Value = highest_FatigueLevel_Value;
        this.highest_BPM_Value = highest_BPM_Value;
        this.highest_RRI_Label = highest_RRI_Label;
        this.highest_StressLevelLabel = highest_StressLevelLabel;
        this.highest_FatigueLevel_Label = highest_FatigueLevel_Label;
        this.highest_BPM_Label = highest_BPM_Label;
        this.lowest_RRI_Value = lowest_RRI_Value;
        this.lowest_StressLevel_Value = lowest_StressLevel_Value;
        this.lowest_FatigueLevel_Value = lowest_FatigueLevel_Value;
        this.lowest_BPM_Value = lowest_BPM_Value;
        this.lowest_RRI_Label = lowest_RRI_Label;
        this.lowest_StressLevelLabel = lowest_StressLevelLabel;
        this.lowest_FatigueLevel_Label = lowest_FatigueLevel_Label;
        this.lowest_BPM_Label = lowest_BPM_Label;
    }

    public ObjectId get_id() {
        return this._id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMeasureID() {
        return this.measureID;
    }

    public void setMeasureID(String measureID) {
        this.measureID = measureID;
    }

    public String getDeviceID() {
        return this.deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getSensorID() {
        return this.sensorID;
    }

    public void setSensorID(String sensorID) {
        this.sensorID = sensorID;
    }

    public String getActivityID() {
        return this.activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public String getActivityName() {
        return this.activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    public Date getStartDateTime() {
        return this.startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    public Date getEndDateTime() {
        return this.endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public double getAvg_RRI_Value() {
        return this.avg_RRI_Value;
    }

    public void setAvg_RRI_Value(double avg_RRI_Value) {
        this.avg_RRI_Value = avg_RRI_Value;
    }

    public double getAvg_StressLevel_Value() {
        return this.avg_StressLevel_Value;
    }

    public void setAvg_StressLevel_Value(double avg_StressLevel_Value) {
        this.avg_StressLevel_Value = avg_StressLevel_Value;
    }

    public double getAvg_FatigueLevel_Value() {
        return this.avg_FatigueLevel_Value;
    }

    public void setAvg_FatigueLevel_Value(double avg_FatigueLevel_Value) {
        this.avg_FatigueLevel_Value = avg_FatigueLevel_Value;
    }

    public double getAvg_BPM_Value() {
        return this.avg_BPM_Value;
    }

    public void setAvg_BPM_Value(double avg_BPM_Value) {
        this.avg_BPM_Value = avg_BPM_Value;
    }

    public String getAvg_RRI_Label() {
        return this.avg_RRI_Label;
    }

    public void setAvg_RRI_Label(String avg_RRI_Label) {
        this.avg_RRI_Label = avg_RRI_Label;
    }

    public String getAvg_StressLevelLabel() {
        return this.avg_StressLevelLabel;
    }

    public void setAvg_StressLevelLabel(String avg_StressLevelLabel) {
        this.avg_StressLevelLabel = avg_StressLevelLabel;
    }

    public String getAvg_FatigueLevel_Label() {
        return this.avg_FatigueLevel_Label;
    }

    public void setAvg_FatigueLevel_Label(String avg_FatigueLevel_Label) {
        this.avg_FatigueLevel_Label = avg_FatigueLevel_Label;
    }

    public String getAvg_BPM_Label() {
        return this.avg_BPM_Label;
    }

    public void setAvg_BPM_Label(String avg_BPM_Label) {
        this.avg_BPM_Label = avg_BPM_Label;
    }

    public double getHighest_RRI_Value() {
        return this.highest_RRI_Value;
    }

    public void setHighest_RRI_Value(double highest_RRI_Value) {
        this.highest_RRI_Value = highest_RRI_Value;
    }

    public double getHighest_StressLevel_Value() {
        return this.highest_StressLevel_Value;
    }

    public void setHighest_StressLevel_Value(double highest_StressLevel_Value) {
        this.highest_StressLevel_Value = highest_StressLevel_Value;
    }

    public double getHighest_FatigueLevel_Value() {
        return this.highest_FatigueLevel_Value;
    }

    public void setHighest_FatigueLevel_Value(double highest_FatigueLevel_Value) {
        this.highest_FatigueLevel_Value = highest_FatigueLevel_Value;
    }

    public double getHighest_BPM_Value() {
        return this.highest_BPM_Value;
    }

    public void setHighest_BPM_Value(double highest_BPM_Value) {
        this.highest_BPM_Value = highest_BPM_Value;
    }

    public String getHighest_RRI_Label() {
        return this.highest_RRI_Label;
    }

    public void setHighest_RRI_Label(String highest_RRI_Label) {
        this.highest_RRI_Label = highest_RRI_Label;
    }

    public String getHighest_StressLevelLabel() {
        return this.highest_StressLevelLabel;
    }

    public void setHighest_StressLevelLabel(String highest_StressLevelLabel) {
        this.highest_StressLevelLabel = highest_StressLevelLabel;
    }

    public String getHighest_FatigueLevel_Label() {
        return this.highest_FatigueLevel_Label;
    }

    public void setHighest_FatigueLevel_Label(String highest_FatigueLevel_Label) {
        this.highest_FatigueLevel_Label = highest_FatigueLevel_Label;
    }

    public String getHighest_BPM_Label() {
        return this.highest_BPM_Label;
    }

    public void setHighest_BPM_Label(String highest_BPM_Label) {
        this.highest_BPM_Label = highest_BPM_Label;
    }

    public double getLowest_RRI_Value() {
        return this.lowest_RRI_Value;
    }

    public void setLowest_RRI_Value(double lowest_RRI_Value) {
        this.lowest_RRI_Value = lowest_RRI_Value;
    }

    public double getLowest_StressLevel_Value() {
        return this.lowest_StressLevel_Value;
    }

    public void setLowest_StressLevel_Value(double lowest_StressLevel_Value) {
        this.lowest_StressLevel_Value = lowest_StressLevel_Value;
    }

    public double getLowest_FatigueLevel_Value() {
        return this.lowest_FatigueLevel_Value;
    }

    public void setLowest_FatigueLevel_Value(double lowest_FatigueLevel_Value) {
        this.lowest_FatigueLevel_Value = lowest_FatigueLevel_Value;
    }

    public double getLowest_BPM_Value() {
        return this.lowest_BPM_Value;
    }

    public void setLowest_BPM_Value(double lowest_BPM_Value) {
        this.lowest_BPM_Value = lowest_BPM_Value;
    }

    public String getLowest_RRI_Label() {
        return this.lowest_RRI_Label;
    }

    public void setLowest_RRI_Label(String lowest_RRI_Label) {
        this.lowest_RRI_Label = lowest_RRI_Label;
    }

    public String getLowest_StressLevelLabel() {
        return this.lowest_StressLevelLabel;
    }

    public void setLowest_StressLevelLabel(String lowest_StressLevelLabel) {
        this.lowest_StressLevelLabel = lowest_StressLevelLabel;
    }

    public String getLowest_FatigueLevel_Label() {
        return this.lowest_FatigueLevel_Label;
    }

    public void setLowest_FatigueLevel_Label(String lowest_FatigueLevel_Label) {
        this.lowest_FatigueLevel_Label = lowest_FatigueLevel_Label;
    }

    public String getLowest_BPM_Label() {
        return this.lowest_BPM_Label;
    }

    public void setLowest_BPM_Label(String lowest_BPM_Label) {
        this.lowest_BPM_Label = lowest_BPM_Label;
    }

    public MeasuredRecordModel _id(ObjectId _id) {
        set_id(_id);
        return this;
    }

    public MeasuredRecordModel userID(String userID) {
        setUserID(userID);
        return this;
    }

    public MeasuredRecordModel measureID(String measureID) {
        setMeasureID(measureID);
        return this;
    }

    public MeasuredRecordModel deviceID(String deviceID) {
        setDeviceID(deviceID);
        return this;
    }

    public MeasuredRecordModel sensorID(String sensorID) {
        setSensorID(sensorID);
        return this;
    }

    public MeasuredRecordModel activityID(String activityID) {
        setActivityID(activityID);
        return this;
    }

    public MeasuredRecordModel activityName(String activityName) {
        setActivityName(activityName);
        return this;
    }

    public MeasuredRecordModel category(String category) {
        setCategory(category);
        return this;
    }

    public MeasuredRecordModel startDateTime(Date startDateTime) {
        setStartDateTime(startDateTime);
        return this;
    }

    public MeasuredRecordModel endDateTime(Date endDateTime) {
        setEndDateTime(endDateTime);
        return this;
    }

    public MeasuredRecordModel avg_RRI_Value(double avg_RRI_Value) {
        setAvg_RRI_Value(avg_RRI_Value);
        return this;
    }

    public MeasuredRecordModel avg_StressLevel_Value(double avg_StressLevel_Value) {
        setAvg_StressLevel_Value(avg_StressLevel_Value);
        return this;
    }

    public MeasuredRecordModel avg_FatigueLevel_Value(double avg_FatigueLevel_Value) {
        setAvg_FatigueLevel_Value(avg_FatigueLevel_Value);
        return this;
    }

    public MeasuredRecordModel avg_BPM_Value(double avg_BPM_Value) {
        setAvg_BPM_Value(avg_BPM_Value);
        return this;
    }

    public MeasuredRecordModel avg_RRI_Label(String avg_RRI_Label) {
        setAvg_RRI_Label(avg_RRI_Label);
        return this;
    }

    public MeasuredRecordModel avg_StressLevelLabel(String avg_StressLevelLabel) {
        setAvg_StressLevelLabel(avg_StressLevelLabel);
        return this;
    }

    public MeasuredRecordModel avg_FatigueLevel_Label(String avg_FatigueLevel_Label) {
        setAvg_FatigueLevel_Label(avg_FatigueLevel_Label);
        return this;
    }

    public MeasuredRecordModel avg_BPM_Label(String avg_BPM_Label) {
        setAvg_BPM_Label(avg_BPM_Label);
        return this;
    }

    public MeasuredRecordModel highest_RRI_Value(double highest_RRI_Value) {
        setHighest_RRI_Value(highest_RRI_Value);
        return this;
    }

    public MeasuredRecordModel highest_StressLevel_Value(double highest_StressLevel_Value) {
        setHighest_StressLevel_Value(highest_StressLevel_Value);
        return this;
    }

    public MeasuredRecordModel highest_FatigueLevel_Value(double highest_FatigueLevel_Value) {
        setHighest_FatigueLevel_Value(highest_FatigueLevel_Value);
        return this;
    }

    public MeasuredRecordModel highest_BPM_Value(double highest_BPM_Value) {
        setHighest_BPM_Value(highest_BPM_Value);
        return this;
    }

    public MeasuredRecordModel highest_RRI_Label(String highest_RRI_Label) {
        setHighest_RRI_Label(highest_RRI_Label);
        return this;
    }

    public MeasuredRecordModel highest_StressLevelLabel(String highest_StressLevelLabel) {
        setHighest_StressLevelLabel(highest_StressLevelLabel);
        return this;
    }

    public MeasuredRecordModel highest_FatigueLevel_Label(String highest_FatigueLevel_Label) {
        setHighest_FatigueLevel_Label(highest_FatigueLevel_Label);
        return this;
    }

    public MeasuredRecordModel highest_BPM_Label(String highest_BPM_Label) {
        setHighest_BPM_Label(highest_BPM_Label);
        return this;
    }

    public MeasuredRecordModel lowest_RRI_Value(double lowest_RRI_Value) {
        setLowest_RRI_Value(lowest_RRI_Value);
        return this;
    }

    public MeasuredRecordModel lowest_StressLevel_Value(double lowest_StressLevel_Value) {
        setLowest_StressLevel_Value(lowest_StressLevel_Value);
        return this;
    }

    public MeasuredRecordModel lowest_FatigueLevel_Value(double lowest_FatigueLevel_Value) {
        setLowest_FatigueLevel_Value(lowest_FatigueLevel_Value);
        return this;
    }

    public MeasuredRecordModel lowest_BPM_Value(double lowest_BPM_Value) {
        setLowest_BPM_Value(lowest_BPM_Value);
        return this;
    }

    public MeasuredRecordModel lowest_RRI_Label(String lowest_RRI_Label) {
        setLowest_RRI_Label(lowest_RRI_Label);
        return this;
    }

    public MeasuredRecordModel lowest_StressLevelLabel(String lowest_StressLevelLabel) {
        setLowest_StressLevelLabel(lowest_StressLevelLabel);
        return this;
    }

    public MeasuredRecordModel lowest_FatigueLevel_Label(String lowest_FatigueLevel_Label) {
        setLowest_FatigueLevel_Label(lowest_FatigueLevel_Label);
        return this;
    }

    public MeasuredRecordModel lowest_BPM_Label(String lowest_BPM_Label) {
        setLowest_BPM_Label(lowest_BPM_Label);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MeasuredRecordModel)) {
            return false;
        }
        MeasuredRecordModel measuredRecordModel = (MeasuredRecordModel) o;
        return Objects.equals(_id, measuredRecordModel._id) && Objects.equals(userID, measuredRecordModel.userID) && Objects.equals(measureID, measuredRecordModel.measureID) && Objects.equals(deviceID, measuredRecordModel.deviceID) && Objects.equals(sensorID, measuredRecordModel.sensorID) && Objects.equals(activityID, measuredRecordModel.activityID) && Objects.equals(activityName, measuredRecordModel.activityName) && Objects.equals(category, measuredRecordModel.category) && Objects.equals(startDateTime, measuredRecordModel.startDateTime) && Objects.equals(endDateTime, measuredRecordModel.endDateTime) && avg_RRI_Value == measuredRecordModel.avg_RRI_Value && avg_StressLevel_Value == measuredRecordModel.avg_StressLevel_Value && avg_FatigueLevel_Value == measuredRecordModel.avg_FatigueLevel_Value && avg_BPM_Value == measuredRecordModel.avg_BPM_Value && Objects.equals(avg_RRI_Label, measuredRecordModel.avg_RRI_Label) && Objects.equals(avg_StressLevelLabel, measuredRecordModel.avg_StressLevelLabel) && Objects.equals(avg_FatigueLevel_Label, measuredRecordModel.avg_FatigueLevel_Label) && Objects.equals(avg_BPM_Label, measuredRecordModel.avg_BPM_Label) && highest_RRI_Value == measuredRecordModel.highest_RRI_Value && highest_StressLevel_Value == measuredRecordModel.highest_StressLevel_Value && highest_FatigueLevel_Value == measuredRecordModel.highest_FatigueLevel_Value && highest_BPM_Value == measuredRecordModel.highest_BPM_Value && Objects.equals(highest_RRI_Label, measuredRecordModel.highest_RRI_Label) && Objects.equals(highest_StressLevelLabel, measuredRecordModel.highest_StressLevelLabel) && Objects.equals(highest_FatigueLevel_Label, measuredRecordModel.highest_FatigueLevel_Label) && Objects.equals(highest_BPM_Label, measuredRecordModel.highest_BPM_Label) && lowest_RRI_Value == measuredRecordModel.lowest_RRI_Value && lowest_StressLevel_Value == measuredRecordModel.lowest_StressLevel_Value && lowest_FatigueLevel_Value == measuredRecordModel.lowest_FatigueLevel_Value && lowest_BPM_Value == measuredRecordModel.lowest_BPM_Value && Objects.equals(lowest_RRI_Label, measuredRecordModel.lowest_RRI_Label) && Objects.equals(lowest_StressLevelLabel, measuredRecordModel.lowest_StressLevelLabel) && Objects.equals(lowest_FatigueLevel_Label, measuredRecordModel.lowest_FatigueLevel_Label) && Objects.equals(lowest_BPM_Label, measuredRecordModel.lowest_BPM_Label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, userID, measureID, deviceID, sensorID, activityID, activityName, category, startDateTime, endDateTime, avg_RRI_Value, avg_StressLevel_Value, avg_FatigueLevel_Value, avg_BPM_Value, avg_RRI_Label, avg_StressLevelLabel, avg_FatigueLevel_Label, avg_BPM_Label, highest_RRI_Value, highest_StressLevel_Value, highest_FatigueLevel_Value, highest_BPM_Value, highest_RRI_Label, highest_StressLevelLabel, highest_FatigueLevel_Label, highest_BPM_Label, lowest_RRI_Value, lowest_StressLevel_Value, lowest_FatigueLevel_Value, lowest_BPM_Value, lowest_RRI_Label, lowest_StressLevelLabel, lowest_FatigueLevel_Label, lowest_BPM_Label);
    }

    @Override
    public String toString() {
        return "{" +
                " _id='" + get_id() + "'" +
                ", userID='" + getUserID() + "'" +
                ", measureID='" + getMeasureID() + "'" +
                ", deviceID='" + getDeviceID() + "'" +
                ", sensorID='" + getSensorID() + "'" +
                ", activityID='" + getActivityID() + "'" +
                ", activityName='" + getActivityName() + "'" +
                ", category='" + getCategory() + "'" +
                ", startDateTime='" + getStartDateTime() + "'" +
                ", endDateTime='" + getEndDateTime() + "'" +
                ", avg_RRI_Value='" + getAvg_RRI_Value() + "'" +
                ", avg_StressLevel_Value='" + getAvg_StressLevel_Value() + "'" +
                ", avg_FatigueLevel_Value='" + getAvg_FatigueLevel_Value() + "'" +
                ", avg_BPM_Value='" + getAvg_BPM_Value() + "'" +
                ", avg_RRI_Label='" + getAvg_RRI_Label() + "'" +
                ", avg_StressLevelLabel='" + getAvg_StressLevelLabel() + "'" +
                ", avg_FatigueLevel_Label='" + getAvg_FatigueLevel_Label() + "'" +
                ", avg_BPM_Label='" + getAvg_BPM_Label() + "'" +
                ", highest_RRI_Value='" + getHighest_RRI_Value() + "'" +
                ", highest_StressLevel_Value='" + getHighest_StressLevel_Value() + "'" +
                ", highest_FatigueLevel_Value='" + getHighest_FatigueLevel_Value() + "'" +
                ", highest_BPM_Value='" + getHighest_BPM_Value() + "'" +
                ", highest_RRI_Label='" + getHighest_RRI_Label() + "'" +
                ", highest_StressLevelLabel='" + getHighest_StressLevelLabel() + "'" +
                ", highest_FatigueLevel_Label='" + getHighest_FatigueLevel_Label() + "'" +
                ", highest_BPM_Label='" + getHighest_BPM_Label() + "'" +
                ", lowest_RRI_Value='" + getLowest_RRI_Value() + "'" +
                ", lowest_StressLevel_Value='" + getLowest_StressLevel_Value() + "'" +
                ", lowest_FatigueLevel_Value='" + getLowest_FatigueLevel_Value() + "'" +
                ", lowest_BPM_Value='" + getLowest_BPM_Value() + "'" +
                ", lowest_RRI_Label='" + getLowest_RRI_Label() + "'" +
                ", lowest_StressLevelLabel='" + getLowest_StressLevelLabel() + "'" +
                ", lowest_FatigueLevel_Label='" + getLowest_FatigueLevel_Label() + "'" +
                ", lowest_BPM_Label='" + getLowest_BPM_Label() + "'" +
                "}";
    }




}
