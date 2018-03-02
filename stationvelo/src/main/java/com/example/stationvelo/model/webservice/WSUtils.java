package com.example.stationvelo.model.webservice;

import android.util.Log;

import com.example.stationvelo.model.beans.Station;
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
}
