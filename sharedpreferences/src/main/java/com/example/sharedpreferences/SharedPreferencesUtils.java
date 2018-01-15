package com.example.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by DevProsper on 14/01/2018.
 */

public class SharedPreferencesUtils {

    private static final String NOM_FICHIER = "file.xml";
    private static final String CLE_NOM = "CLE_NOM";
    private static final String CLE_PERSONNE = "CLE_NOM";

    private static final Gson gson = new Gson();

    public static void sauvegarderNom(Context context, String nom){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NOM_FICHIER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CLE_NOM, nom);
        editor.apply();
    }

    public static String recupererNom(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NOM_FICHIER, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(CLE_NOM, "");
    }

    public static void sauvegarderPersonne(Context context, Personne personne){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NOM_FICHIER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(personne);
        editor.putString(CLE_PERSONNE, json);
        editor.apply();
    }

    public static Personne recupererPersonne(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NOM_FICHIER, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(CLE_PERSONNE, null);
        if (json != null){
            return gson.fromJson(json, Personne.class);
        }else {
            return null;
        }
    }
}
