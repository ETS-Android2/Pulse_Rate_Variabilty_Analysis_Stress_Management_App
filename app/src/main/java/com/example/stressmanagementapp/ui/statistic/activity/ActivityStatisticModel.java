package com.example.stressmanagementapp.ui.statistic.activity;

import com.example.stressmanagementapp.Model.MeasuredRecordModel;

import org.bson.types.ObjectId;

import java.util.Date;

public class ActivityStatisticModel extends MeasuredRecordModel {
    public ActivityStatisticModel(ObjectId _id, String userID, String measureID, String deviceID, String sensorID, String activityID, String activityName, String category, Date startDateTime, Date endDateTime, double avg_RRI_Value, double avg_StressLevel_Value, double avg_FatigueLevel_Value, double avg_BPM_Value, String avg_RRI_Label, String avg_StressLevelLabel, String avg_FatigueLevel_Label, String avg_BPM_Label, double highest_RRI_Value, double highest_StressLevel_Value, double highest_FatigueLevel_Value, double highest_BPM_Value, String highest_RRI_Label, String highest_StressLevelLabel, String highest_FatigueLevel_Label, String highest_BPM_Label, double lowest_RRI_Value, double lowest_StressLevel_Value, double lowest_FatigueLevel_Value, double lowest_BPM_Value, String lowest_RRI_Label, String lowest_StressLevelLabel, String lowest_FatigueLevel_Label, String lowest_BPM_Label) {
        super(_id, userID, measureID, deviceID, sensorID, activityID, activityName, category, startDateTime, endDateTime, avg_RRI_Value, avg_StressLevel_Value, avg_FatigueLevel_Value, avg_BPM_Value, avg_RRI_Label, avg_StressLevelLabel, avg_FatigueLevel_Label, avg_BPM_Label, highest_RRI_Value, highest_StressLevel_Value, highest_FatigueLevel_Value, highest_BPM_Value, highest_RRI_Label, highest_StressLevelLabel, highest_FatigueLevel_Label, highest_BPM_Label, lowest_RRI_Value, lowest_StressLevel_Value, lowest_FatigueLevel_Value, lowest_BPM_Value, lowest_RRI_Label, lowest_StressLevelLabel, lowest_FatigueLevel_Label, lowest_BPM_Label);
    }
}
