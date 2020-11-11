package com.huawei.podcast.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    private final String TAG = "MyService";

    private int startId;

    public MyService() {
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;
        Log.i(TAG, "onStartCommand---startId: " + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        try {
            Log.w(TAG, "run: kill my self.");
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (RuntimeException e) {
            Log.e(TAG, TAG, e);
        }
        super.onTaskRemoved(rootIntent);
    }
}