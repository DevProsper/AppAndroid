package com.example.notification;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {

    public static final String NOTIFICATION_EXTRA = "NOTIFICATION_EXTRA";

    @Override
    public void onReceive(Context context, Intent intent) {
        Notification notification = intent.getParcelableExtra(NOTIFICATION_EXTRA);

        //On demande au systeme d'afficher la notification
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, notification);
    }
}
