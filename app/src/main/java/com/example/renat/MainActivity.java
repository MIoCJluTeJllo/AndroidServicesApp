package com.example.renat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWebView();
        RecordTask voice_record = new RecordTask(this);
        voice_record.startRecord(this.getExternalCacheDir().getAbsolutePath() + "/first.amr");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                voice_record.stopRecord();
            }
        }, 2000);
    }
    void initWebView(){
        WebView myWebView = new WebView(this);
        setContentView(myWebView);
        FirebaseNotification.getDeviceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w("Firebase", "Fetching device id (firebase) failed", task.getException());
                return;
            }

            String token = task.getResult();
            myWebView.loadUrl(Utils.getWebViewUrl(token));

            // Log and toast
            Log.d("Firebase", "Fetching device id (firebase) success");
        });
        FirebaseNotification.getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w("Firebase", "Fetching firebase token failed", task.getException());
                return;
            }

            String token = task.getResult();
            myWebView.loadUrl(Utils.getWebViewUrl(token));

            // Log and toast
            Log.d("TOKEN", token);
        });
    }
}