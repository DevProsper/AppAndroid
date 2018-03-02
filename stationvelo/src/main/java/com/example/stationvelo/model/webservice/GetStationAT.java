package com.example.stationvelo.model.webservice;

import android.os.AsyncTask;

/**
 * Created by DevProsper on 02/03/2018.
 */

public class GetStationAT extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            WSUtils.getStation();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
