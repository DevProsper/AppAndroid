package com.example.serviceandlibrairieotto;

import android.app.Application;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by DevProsper on 13/01/2018.
 */

public class MyApplication extends Application {

    private static Bus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        //Pour signifier quand travail sur tous les thread
        bus = new Bus(ThreadEnforcer.ANY);
    }

    public static Bus getBus() {
        return bus;
    }
}
