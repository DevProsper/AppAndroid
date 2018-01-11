package com.example.service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_start, btn_stop;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = (Button)findViewById(R.id.start);
        btn_stop = (Button)findViewById(R.id.stop);
        tv = (TextView)findViewById(R.id.tv);

        btn_stop.setOnClickListener(this);
        btn_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_start){
            Intent intent = new Intent(this, MyService.class);
            startService(intent);
        }else if (v == btn_stop){
            Intent intent = new Intent(this, MyService.class);
            stopService(intent);
        }
    }
}
