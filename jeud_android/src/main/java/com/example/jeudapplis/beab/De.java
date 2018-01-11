package com.example.jeudapplis.beab;

import java.util.Random;

/**
 * Created by DevProsper on 27/12/2017.
 */

public class De {

    private static final int NB_FACE = 6;
    private static final Random random = new Random();
    private int valeur;

    public void lancer() {
        valeur = random.nextInt(NB_FACE) + 1;
    }

    /********************
     * GETTER/SETTER
     *****************/

    public int getValeur() {
        return valeur;
    }
}
