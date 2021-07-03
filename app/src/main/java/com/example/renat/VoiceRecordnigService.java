package com.example.renat;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class VoiceRecordnigService extends Service {
    static final String TAG = "VoiceRecordnigService";
    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            Log.d(TAG, "Strting service");
            RecordTask recordTask = new RecordTask(getApplicationContext());
            recordTask.startRecord(getExternalCacheDir().getAbsolutePath() + "/first.amr");
            Thread.sleep(5000);
            recordTask.stopRecord();
        } catch (InterruptedException e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }
        stopSelf();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
    }
}