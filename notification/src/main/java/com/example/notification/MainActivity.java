package com.example.notification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_notif, notif_retard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_notif = (Button)findViewById(R.id.notif);
        notif_retard = (Button)findViewById(R.id.notif_retard);

        btn_notif.setOnClickListener(this);
        notif_retard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_notif){
            NotificationUtils.envoyerNotification(this, "Mon message");
        }else if (view == notif_retard){
            NotificationUtils.programmerNotification(this, "Message", 6000);
        }
    }
}
