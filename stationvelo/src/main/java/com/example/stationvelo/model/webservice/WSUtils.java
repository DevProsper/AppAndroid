package com.example.stationvelo.model.webservice;

import android.util.Log;

import java.net.HttpURLConnection;

import okhttp3.Response;

/**
 * Created by DevProsper on 02/03/2018.
 */
//Quel Requette on devra effectuer
public class WSUtils {

    private static final String KEY = "c4c2a97f77a73076133ab01b9377a369a5b6df1d";
    private static final String CONTRAT = "Toulouse";
    private static final String URL = "https://api.jcdecaux.com/vls/v1/stations?contract="+ CONTRAT +"&apiKey="+KEY;

    public static void getStation() throws Exception {
        Response response =  OkHttpUtils.sendGetOkHttpRequest(URL);

        //Analyse du code de retour
        if (response.code() != HttpURLConnection.HTTP_OK){
            throw new Exception("Réponse du serveur incorecte : " +response.code());
        }else{
            Log.w("TAG", response.body().string());
        }
    }
}
