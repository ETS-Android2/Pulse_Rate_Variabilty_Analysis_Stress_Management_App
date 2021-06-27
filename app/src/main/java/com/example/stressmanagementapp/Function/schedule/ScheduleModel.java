package com.example.stressmanagementapp.Function.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.bson.types.ObjectId;

import java.util.Date;

public class ScheduleModel {
    private ObjectId _id;
    private ObjectId ownerIdInUserCollection;
    private ObjectId activityIdInActivityCollection;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Date startDateTime;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Date endDateTime;
    private String status;
    private String activityName;
    private String category;

    public ScheduleModel(ObjectId _id, ObjectId ownerIdInUserCollection, ObjectId activityIdInActivityCollection, Date startDateTime, Date endDateTime, String status, String category, String activityName) {
        this._id = _id;
        this.ownerIdInUserCollection = ownerIdInUserCollection;
        this.activityIdInActivityCollection = activityIdInActivityCollection;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
        this.category = category;
        this.activityName = activityName;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public ObjectId getOwnerIdInUserCollection() {
        return ownerIdInUserCollection;
    }

    public void setOwnerIdInUserCollection(ObjectId ownerIdInUserCollection) {
        this.ownerIdInUserCollection = ownerIdInUserCollection;
    }

    public ObjectId getActivityIdInActivityCollection() {
        return activityIdInActivityCollection;
    }

    public void setActivityIdInActivityCollection(ObjectId activityIdInActivityCollection) {
        this.activityIdInActivityCollection = activityIdInActivityCollection;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    @Override
    public String toString() {
        return "ScheduleModel{" +
                "_id=" + _id +
                ", ownerIdInUserCollection=" + ownerIdInUserCollection +
                ", activityIdInActivityCollection=" + activityIdInActivityCollection +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", status='" + status + '\'' +
                ", category='" + category + '\'' +
                ", activityName='" + activityName + '\'' +
                '}';
    }
}
