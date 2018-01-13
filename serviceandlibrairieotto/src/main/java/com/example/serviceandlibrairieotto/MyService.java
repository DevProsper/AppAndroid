package com.example.serviceandlibrairieotto;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    private Timer timer;
    private int startId;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        //Pour indiqué l'action a itulisé tous les X temps
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String afficher;
                if (startId % 2 == 0){
                    afficher = "Bonjour : " +startId;
                }else {
                    afficher = "Bonsoir : " +startId;
                }
                MyApplication.getBus().post(afficher);
                Log.v("___TAG__", afficher);
            }//Exécuté au bout de deux seconde a partir de 1s
        },1000,2000);
        Toast.makeText(this, "onCreate()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        Toast.makeText(this, "onDestroy()", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
