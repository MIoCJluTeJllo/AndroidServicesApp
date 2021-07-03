package com.example.renat;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWebView();
        if (!Permission.check(this, new String[]{Manifest.permission.FOREGROUND_SERVICE})){
            Permission.alert(this, new String[]{Manifest.permission.FOREGROUND_SERVICE}, null );
        }
        if (!Permission.check(this, new String[]{Manifest.permission.RECORD_AUDIO})){
            Permission.alert(this, new String[]{Manifest.permission.RECORD_AUDIO}, null );
        }
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