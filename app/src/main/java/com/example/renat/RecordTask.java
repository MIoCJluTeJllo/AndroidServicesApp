package com.example.renat;

import java.io.File;
import java.io.IOException;
import android.Manifest;
import android.content.Context;
import android.media.MediaRecorder;
import android.util.Log;

public class RecordTask {
    static final String TAG = "VoiceRecord";
    MediaRecorder recorder;
    String[] permissions = {Manifest.permission.RECORD_AUDIO};
    Context context;
    public RecordTask(Context context){
        this.context = context;
    }
    public void startRecord(String path){
        if (Permission.check(context, permissions)){
            if (createFile(path)) {
                recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                recorder.setOutputFile(path);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                try {
                    recorder.prepare();
                } catch (IOException err){
                    Log.e(TAG, "Failed prepare voice record", err);
                }
                recorder.start();
                Log.i(TAG, "Recording started");
            }
        } else {
        }
    }
    public void stopRecord(){
        if (recorder != null){
            recorder.stop();
            recorder.release();
            recorder = null;
            Log.i(TAG, "Rsecording stopped");
        }
    }
    boolean createFile(String path) {
        File file = new File(path);
        try {
            file.createNewFile();
            return file.exists();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
