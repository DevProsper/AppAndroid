package com.example.jeudapplis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeudapplis.beab.Partie;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Composants graphiques
    private TextView tv_tour;
    private TextView tv_score_j1;
    private ImageView iv_j1;
    private TextView tv_score_j2;
    private ImageView iv_j2;
    private Button bt_lancer;
    private TextView tv_de1;
    private TextView tv_de2;

    //Données
    private Partie partie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_tour = (TextView) findViewById(R.id.tv_tour);
        tv_score_j1 = (TextView) findViewById(R.id.tv_score_j1);
        iv_j1 = (ImageView) findViewById(R.id.iv_j1);
        tv_score_j2 = (TextView) findViewById(R.id.tv_score_j2);
        iv_j2 = (ImageView) findViewById(R.id.iv_j2);
        bt_lancer = (Button) findViewById(R.id.bt_lancer);
        tv_de1 = (TextView) findViewById(R.id.tv_de1);
        tv_de2 = (TextView) findViewById(R.id.tv_de2);


        bt_lancer.setOnClickListener(this);

        partie = new Partie("Joueur1", "Joueur2");

        rafraichirEcran();


    }

    @Override
    public void onClick(View v) {
        if (v == bt_lancer) {

            //Je fais jouer le joueur courant
            partie.getJoueurSuivant().lancer();

            //Je contrôle son lancer
            if (partie.getJoueurSuivant().getScoreDes() >= Partie.NB_A_ATTEINDRE) {
                partie.getJoueurSuivant().setScore(partie.getJoueurSuivant().getScore() + 1);
            }

            //Je change de joueur courant
            partie.changerJoueur();

            //J'incrémente le tour si c'est à joueur1 de jouer
            if (partie.getJoueurSuivant() == partie.getJ1()) {
                partie.setTour(partie.getTour() + 1);
            }

            //J'actualise l'écran
            rafraichirEcran();

        }
    }

    /**
     * Actualise les données de l'interface graphique
     */
    private void rafraichirEcran() {

        //Le tour de la partie
        tv_tour.setText(partie.getTour() + "");

        //Le tour des joueurs
        tv_score_j1.setText(partie.getJ1().getScore() + "");
        tv_score_j2.setText(partie.getJ2().getScore() + "");

        //Quelle étoile on affiche
        if (partie.getJ1() == partie.getJoueurSuivant()) {
            iv_j1.setVisibility(View.VISIBLE);
            iv_j2.setVisibility(View.INVISIBLE);

            //On affiche les dés de joueur2
            tv_de1.setText(partie.getJ2().getDe1() + "");
            tv_de2.setText(partie.getJ2().getDe2() + "");

        } else {
            iv_j1.setVisibility(View.INVISIBLE);
            iv_j2.setVisibility(View.VISIBLE);

            //On affiche les dés de joueur1
            tv_de1.setText(partie.getJ1().getDe1() + "");
            tv_de2.setText(partie.getJ1().getDe2() + "");
        }


        //Le jeu se termine
        if (partie.getTour() == Partie.NB_LANCER + 1) {
            //TODO À vous de jouer...
            Toast.makeText(this, "Fin", Toast.LENGTH_SHORT).show();
        }

    }
}
