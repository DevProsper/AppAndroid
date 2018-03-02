package com.example.stationvelo.model.webservice;

import android.os.AsyncTask;

import com.example.stationvelo.model.beans.Station;

import java.util.ArrayList;

/**
 * Created by DevProsper on 02/03/2018.
 */

public class GetStationAT extends AsyncTask {

    private ArrayList<Station> stations;
    private Exception exception;
    private GetStationATResult getStationATResult;

    public GetStationAT(GetStationATResult getStationATResult) {
        this.getStationATResult = getStationATResult;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            stations =  WSUtils.getStation();
        }catch (Exception e){
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if (getStationATResult != null){

            if (exception !=null){
                getStationATResult.getStationATResultErreur(exception);
            }else{
                getStationATResult.stationChargees(stations);
            }
        }

    }

    public interface GetStationATResult{
        void stationChargees(ArrayList<Station> stations);
        void getStationATResultErreur(Exception e);
    }
}
