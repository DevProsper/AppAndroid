package com.example.stationvelo.model.webservice;

import android.os.AsyncTask;

import com.example.stationvelo.model.beans.Station;
import com.example.stationvelo.model.beans.direction.DirectionResult;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by DevProsper on 02/03/2018.
 */

public class GetDirectionAT extends AsyncTask {

    private DirectionResult directionResult;
    private Exception exception;
    private LatLng depart;
    private LatLng arrive;
    private GetDirectionATResult getDirectionATResult;

    public GetDirectionAT(LatLng depart, LatLng arrive,  GetDirectionATResult getDirectionATResult) {
        this.depart = depart;
        this.arrive = arrive;
        this.getDirectionATResult = getDirectionATResult;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            directionResult =  WSUtils.getDirection(depart, arrive);
        }catch (Exception e){
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if (getDirectionATResult != null){

            if (exception !=null){
                getDirectionATResult.GetDirectionATResultErreur(exception);
            }else{
                getDirectionATResult.directionChargees(directionResult);
            }
        }

    }

    public interface GetDirectionATResult{
        void directionChargees(DirectionResult directionResult);
        void GetDirectionATResultErreur(Exception e);
    }
}
