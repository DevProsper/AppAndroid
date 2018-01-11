package com.example.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        boolean state = intent.getBooleanExtra("state",false);

        if (state){
            Toast.makeText(context, "Mode Avion activé", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Mode Avion desactivé", Toast.LENGTH_SHORT).show();
        }
    }
}
