package com.example.stressmanagementapp.Model;

import java.util.ArrayList;
import java.util.List;

public class PPG_Model_Sample {
    private ArrayList<PPG_Model> ppgList;

    public PPG_Model_Sample() {
        this.ppgList = new ArrayList<PPG_Model>();
    }

    public ArrayList<PPG_Model> getPpgList() {
        return ppgList;
    }

    public void setPpgList(ArrayList<PPG_Model> ppgList) {
        this.ppgList = ppgList;
    }
}
