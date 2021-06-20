package com.example.stressmanagementapp.Util;

import android.content.Context;
import android.os.AsyncTask;

import com.example.stressmanagementapp.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HTTPRequestClientHandler {
    private String url;
    private String endpoint;
    private String response;
    public HTTPRequestClientHandler(String endpoint, Context context){
        this.url=context.getString(R.string.api_path);
        this.endpoint=endpoint;
    }
    public void doTask(){

    }

}
