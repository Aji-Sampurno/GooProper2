package com.gooproper.util;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.gooproper.R;
import com.gooproper.admin.MainAdminActivity;
import com.gooproper.ui.listing.InfoPropertyActivity;
import com.gooproper.ui.listing.ListingkuActivity;
import com.gooproper.ui.PelamarAgenActivity;
import com.gooproper.ui.listing.PraListingRejectedActivity;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "Token pendaftaran yang diperbarui: " + token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "Pesan push diterima: " + remoteMessage.getData());
        Map<String, String> data = remoteMessage.getData();

        int notificationId = (int) System.currentTimeMillis();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "notification";
            CharSequence channelName = "pralisting";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        String title = data.get("title");
        String messageBody = data.get("message");
        String notificationType = data.get("type");

        Intent intent;
        if ("listingku".equals(notificationType)) {
            intent = new Intent(this, ListingkuActivity.class);
        } else if ("pralisting".equals(notificationType)) {
            intent = new Intent(this, MainAdminActivity.class);
            intent.putExtra("fragment_to_open", "pralisting");
        } else if ("infoproperty".equals(notificationType)) {
            intent = new Intent(this, InfoPropertyActivity.class);
        } else if ("pelamar".equals(notificationType)) {
            intent = new Intent(this, PelamarAgenActivity.class);
        } else if ("rejected".equals(notificationType)) {
            intent = new Intent(this, PraListingRejectedActivity.class);
        } else {
            intent = new Intent(this, MainAdminActivity.class);
            intent.putExtra("fragment_to_open", "pralisting");
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Uri customSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.listing_masuk);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "notification")
                        .setSmallIcon(R.drawable.logogp)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                        .setSound(defaultSoundUri)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
