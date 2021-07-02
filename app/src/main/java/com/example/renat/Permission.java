package com.example.renat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Map;

public class Permission {
    public static boolean check(Context context, String[] permissions){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            for (String permission : permissions){
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }
    public static void alert(AppCompatActivity activity, String[] permissions, ActivityResultCallback<Map<String, Boolean>> callback){
        activity.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), callback).launch(permissions);
    }
}
