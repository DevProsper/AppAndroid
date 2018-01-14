package com.example.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DevProsper on 14/01/2018.
 */

public class SharedPreferencesUtils {

    private static final String NOM_FICHIER = "file.xml";
    private static final String CLE_NOM = "CLE_NOM";

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
}
