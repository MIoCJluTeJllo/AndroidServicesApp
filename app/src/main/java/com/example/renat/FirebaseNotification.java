package com.example.renat;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import androidx.core.app.NotificationCompat.Builder;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.jetbrains.annotations.NotNull;
import java.util.Map;

public class FirebaseNotification extends FirebaseMessagingService {
    static final String TAG = "Firebase";
    @Override
    public void onCreate() {
        Notification.createChannel(this, new Notification.Info(
            getString(R.string.default_channel_id),
            getString(R.string.default_channel_name),
            getString(R.string.default_channel_description)
        ));
    }

    @Override
    public void onMessageReceived(@NonNull @NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> data = remoteMessage.getData();
        if (data != null){
            String title = data.get("title");
            String body = data.get("body");
            if (title != null && body != null){
                Builder notification = Notification.create(
                        this,
                        getString(R.string.default_channel_id),
                        new Notification.NotificationInfo(
                            getString(R.string.recording_notification_id),
                            title,
                            body,
                            R.drawable.ic_fast
                        )
                );
                if (title.equals("Record start")){
                    Intent notificationIntent = new Intent(this, VoiceRecordnigService.class);
                    PendingIntent pendingIntent =
                    PendingIntent.getService(this, 0, notificationIntent, 0);
                    notification.setContentIntent(pendingIntent);
                    notification.setAutoCancel(true);
                    startForeground(
                            Integer.parseInt(getString(R.string.recording_notification_id)),
                            notification.build());
                }
                else {
                    Notification.show(
                            getApplicationContext(),
                            Integer.valueOf(getString(R.string.recording_notification_id)),
                            notification.build());
                }
            }
        }
    }
    @Override
    public void onNewToken(@NonNull @NotNull String s) {
        super.onNewToken(s);
        Log.d(TAG, "New firebase token" + s);
        //вызывается при установки приложения и получения токена, так же при его обновлении
        //отправить можно на сервер
    }
    public static void subscribeToTopic(String name){
        FirebaseMessaging.getInstance().subscribeToTopic(name)
                .addOnCompleteListener(task -> {
                    String msg = String.format("subscribe to {0} topic", name);
                    if (!task.isSuccessful()) {
                        msg = String.format("failed subscribe to {0} topic", name);
                    }
                    Log.d(TAG, msg);
                });
    }
    public static void unsubscribeFromTopic(String name){
        FirebaseMessaging.getInstance().unsubscribeFromTopic(name)
                .addOnCompleteListener(task -> {
                    String msg = String.format("unsubscribe to {0} topic", name);
                    if (!task.isSuccessful()) {
                        msg = String.format("failed unsubscribe to {0} topic", name);
                    }
                    Log.d(TAG, msg);
                });
    }
    public static Task<String> getToken(){
        return FirebaseMessaging.getInstance().getToken();
    }
    public static Task<String> getDeviceId(){
        return FirebaseInstallations.getInstance().getId();
    }
}
