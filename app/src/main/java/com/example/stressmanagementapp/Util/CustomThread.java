package com.example.stressmanagementapp.Util;

import java.util.logging.Handler;

public class CustomThread {
    private Runnable runnable;
    private Thread thread;
    public CustomThread(Runnable r, Thread t){
        this.runnable=r;
        this.thread=t;
    }


    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public void startThread(){
        this.thread.start();
    }
    public void stopThread(){
        this.thread.interrupt();
    }

}
