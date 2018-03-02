package com.example.stationvelo.model.webservice;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by DevProsper on 02/03/2018.
 */
//Permet d'effectuer une requette
public class OkHttpUtils {

    public static Response sendGetOkHttpRequest(String url) throws Exception{
        Log.w("TAG_URL", url);
        OkHttpClient client = new OkHttpClient();


        //Création de la requette
        Request request = new Request.Builder().url(url).build();
        return client.newCall(request).execute();
    }
}
