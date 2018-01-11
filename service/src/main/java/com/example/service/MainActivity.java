package com.example.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyService.MyServiceListener {

    private Button btn_start, btn_stop, btn_con;
    private TextView tv;

    private MyService myService;
    //Permet de récupéré le pointeur vers notre service
    private ServiceConnection serviceConnection;
    private boolean bind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = (Button)findViewById(R.id.start);
        btn_stop = (Button)findViewById(R.id.stop);
        tv = (TextView)findViewById(R.id.tv);
        btn_con = (Button)findViewById(R.id.cont);

        btn_stop.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        btn_con.setOnClickListener(this);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder service) {
                //Réussir a binder vers le service
                MyService.MyServiceBinder myServiceBinder = (MyService.MyServiceBinder)service;
                myService = myServiceBinder.getMyService();
                myService.setMyServiceListener(MainActivity.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                myService.setMyServiceListener(null);
                myService = null;
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind){
            unbindService(serviceConnection);

            if (myService != null){
                myService.setMyServiceListener(null);
                myService = null;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btn_start){
            Intent intent = new Intent(this, MyService.class);
            startService(intent);
        }else if (v == btn_stop){
            Intent intent = new Intent(this, MyService.class);
            stopService(intent);
        }else if (v == btn_con){
            Intent intent = new Intent(this, MyService.class);
            //Si non Bind et que ya pas le service, il ne se passe rien 0
            bindService(intent, serviceConnection, 0);
            bind = true;
        }
    }

    @Override
    public void onServiceMessage(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(text + "\n" +tv.getText());
            }
        });
    }
}
