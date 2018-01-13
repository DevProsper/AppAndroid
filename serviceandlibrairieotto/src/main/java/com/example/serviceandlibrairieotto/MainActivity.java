package com.example.serviceandlibrairieotto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button start, stop;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button)findViewById(R.id.start);
        stop = (Button)findViewById(R.id.stop);
        tv = (TextView)findViewById(R.id.tv);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MyApplication.getBus().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getBus().unregister(this);
    }

    @Subscribe
    public void afficherMessage(final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(message + "\n" +tv.getText());
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == start){
            startService(new Intent(this, MyService.class));
        }else if (view == stop){
            stopService(new Intent(this, MyService.class));
        }
    }
}
