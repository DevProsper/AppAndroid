package com.example.jeudapplis.beab;

/**
 * Created by DevProsper on 27/12/2017.
 */

public class Gobelet {

    private De de1, de2;

    public Gobelet() {
        de1 = new De();
        de2 = new De();
    }

    public void lancer() {
        de1.lancer();
        de2.lancer();
    }

    public int getScoreDes() {
        return de1.getValeur() + de2.getValeur();
    }

    /********************
     * GETTER/SETTER
     *****************/

    public De getDe1() {
        return de1;
    }

    public De getDe2() {
        return de2;
    }
}
