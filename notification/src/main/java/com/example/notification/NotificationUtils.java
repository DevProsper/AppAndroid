package com.example.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

/**
 * Created by DevProsper on 14/01/2018.
 */

public class NotificationUtils {

    public static void envoyerNotification(Context context, String message){
        Notification notification = creerNotification(context, message);

        //On demande au systeme d'afficher la notification
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, notification);
    }

    private static Notification creerNotification(Context context, String message){
        //Action quand on click sur le bouton
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Image a droite sur la notification
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

        //Création de la notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle("Le titre")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true) //Enlever la notification quand on clik dessus
                .setPriority(Notification.PRIORITY_HIGH) //Affichage de la notification à la reception
                .setDefaults(Notification.DEFAULT_ALL); //Affichage + son + vibration

                //Mettre une couleur au titre
                if (Build.VERSION.SDK_INT >= 21){
                    builder.setColor(context.getResources().getColor(R.color.colorAccent))
                            .setVisibility(Notification.VISIBILITY_PUBLIC);
                }
        return builder.build();

    }

    public static void programmerNotification(Context context, String message, long delayInMillis){
        Log.v("TAG", "Delay : "+ delayInMillis);
        Notification notification = creerNotification(context, message);

        //On prépare un broadcast et on met à jour la notif
        Intent notificationIntent = new Intent(context, NotificationReceiver.class);
        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION_EXTRA, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //La date dans le futur
        long futurInMillis = SystemClock.elapsedRealtime() + delayInMillis;

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futurInMillis, pendingIntent);
    }
}
