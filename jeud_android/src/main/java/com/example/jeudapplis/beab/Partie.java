package com.example.jeudapplis.beab;

/**
 * Created by DevProsper on 27/12/2017.
 */

public class Partie {

    public static final int NB_LANCER = 10;
    public static final int NB_A_ATTEINDRE = 7;

    private Joueur j1, j2;
    private int tour;
    private Joueur joueurSuivant;

    public Partie(String nomJ1, String nomJ2) {
        j1 = new Joueur(nomJ1);
        j2 = new Joueur(nomJ2);
        joueurSuivant = j1;
        tour = 1;
    }

    public void changerJoueur() {
        if (joueurSuivant == j1) {
            joueurSuivant = j2;
        }
        else {
            joueurSuivant = j1;
        }
    }

    /* ---------------------------------
    // Getter/Setter
    // -------------------------------- */

    public Joueur getJ1() {
        return j1;
    }

    public Joueur getJ2() {
        return j2;
    }

    public int getTour() {
        return tour;
    }

    public void setTour(int tour) {
        this.tour = tour;
    }

    public Joueur getJoueurSuivant() {
        return joueurSuivant;
    }
}
