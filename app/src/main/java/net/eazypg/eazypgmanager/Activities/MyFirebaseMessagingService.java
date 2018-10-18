package net.eazypg.eazypgmanager.Activities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import net.eazypg.eazypgmanager.R;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyGcmListenerService";
    private NotificationManager notifManager;
    private NotificationChannel mChannel;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.e("Notification", remoteMessage.getNotification().getBody() + "");

        pushNotification(remoteMessage.getNotification().getTitle(),
                            remoteMessage.getNotification().getBody(),
                            remoteMessage.getData());
    }

    private void pushNotification(String title, String body, Map<String,String> data) {

        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        if (notifManager == null) {
            notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        intent = new Intent(this, HomePageActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            if (mChannel == null) {
                NotificationChannel mChannel = new NotificationChannel("0", title, NotificationManager.IMPORTANCE_DEFAULT);
                mChannel.setDescription (body);
                mChannel.enableVibration (true);
                mChannel.setVibrationPattern (new long[] {100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel (mChannel);
            }

            builder = new NotificationCompat.Builder (this, "0");

            intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
            builder.setContentTitle (title)  // flare_icon_30

                    .setSmallIcon (R.drawable.notification) // required

                    .setContentText (body)  // required

                    .setDefaults (Notification.DEFAULT_ALL)
                    .setAutoCancel (true)
                    .setLargeIcon (BitmapFactory.decodeResource(getResources (), R.drawable.icon_logo))
                    .setBadgeIconType (R.drawable.icon_logo)
                    .setContentIntent (pendingIntent)
                    .setSound (RingtoneManager.getDefaultUri (RingtoneManager.TYPE_NOTIFICATION))
                    .setVibrate (new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {

            builder = new NotificationCompat.Builder (this);

            intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity (this, 0, intent, 0);
            builder.setContentTitle (title)
                    .setSmallIcon (R.drawable.icon_logo) // required
                    .setContentText (body)  // required
                    .setDefaults (Notification.DEFAULT_ALL)
                    .setAutoCancel (true)
                    .setContentIntent (pendingIntent)
                    .setSound (RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setVibrate (new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority (Notification.PRIORITY_HIGH);
        } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Notification notification = builder.build ();
        notifManager.notify (0, notification);
    }
}