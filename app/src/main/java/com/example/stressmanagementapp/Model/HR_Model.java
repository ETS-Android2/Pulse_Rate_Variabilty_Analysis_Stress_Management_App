package com.example.stressmanagementapp.Model;

import java.util.Date;

public class HR_Model implements Model{
    private Date logDateTime;
    private float hr;

    public HR_Model(Date logDateTime, float hr) {
        this.logDateTime = logDateTime;
        this.hr = hr;
    }

    public Date getLogDateTime() {
        return logDateTime;
    }

    public void setLogDateTime(Date logDateTime) {
        this.logDateTime = logDateTime;
    }

    public float getHr() {
        return hr;
    }

    public void setHr(float hr) {
        this.hr = hr;
    }

    @Override
    public String toString() {
        return "HR_Model{" +
                "logDateTime=" + logDateTime +
                ", hr=" + hr +
                '}';
    }
}
