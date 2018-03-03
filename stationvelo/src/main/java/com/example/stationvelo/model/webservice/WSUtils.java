package com.example.stationvelo.model.webservice;

import android.util.Log;

import com.example.stationvelo.model.beans.Station;
import com.example.stationvelo.model.beans.direction.DirectionResult;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import okhttp3.Response;

/**
 * Created by DevProsper on 02/03/2018.
 */
//Quel Requette on devra effectuer
public class WSUtils {

    private static final String KEY = "c4c2a97f77a73076133ab01b9377a369a5b6df1d";
    private static final String CONTRAT = "Toulouse";
    private static final String URL = "https://api.jcdecaux.com/vls/v1/stations?contract="+ CONTRAT +"&apiKey="+KEY;

    private static final Gson gson = new Gson();

    public static ArrayList<Station> getStation() throws Exception {
        Response response =  OkHttpUtils.sendGetOkHttpRequest(URL);

        //Analyse du code de retour
        if (response.code() != HttpURLConnection.HTTP_OK){
            throw new Exception("Réponse du serveur incorecte : " +response.code());
        }else{
            InputStreamReader inputStreamReader = new InputStreamReader(response.body().byteStream());
            //convertir un array en Gson
            ArrayList<Station> stations = gson.fromJson(inputStreamReader,
                    new TypeToken<ArrayList<Station>>(){
            }.getType());
            inputStreamReader.close();
            return stations;
        }
    }

    private static final String URL_WS_GOOGLE_JSON = "https://maps.googleapis.com/maps/api/directions/json?mode=walking";

    public static DirectionResult getDirection(LatLng depart, LatLng arrive) throws Exception {
        String url = URL_WS_GOOGLE_JSON + "&origin=" + depart.longitude+ "," +depart.latitude+ "&destination=" + arrive.longitude+ "," + arrive.latitude;
        Response response = OkHttpUtils.sendGetOkHttpRequest(url);

        //Analyse du code de retour
        if (response.code() != HttpURLConnection.HTTP_OK){
            throw new Exception("Réponse du serveur incorecte : " +response.code());
        }else{
            InputStreamReader inputStreamReader = new InputStreamReader(response.body().byteStream());
            DirectionResult directionResult = gson.fromJson(inputStreamReader, DirectionResult.class);
            inputStreamReader.close();
            return directionResult;
        }
    }
}
