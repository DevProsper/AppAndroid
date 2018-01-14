package com.example.sharedpreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button charger_nom, sauv_nom, charger_personne, sau_personne;
    private EditText nom, prenom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sauv_nom = (Button)findViewById(R.id.sauv_nom);
        charger_nom = (Button)findViewById(R.id.charger_nom);
        sau_personne = (Button)findViewById(R.id.sauv_personne);
        charger_personne = (Button)findViewById(R.id.charger_personne);

        nom = (EditText)findViewById(R.id.nom);
        prenom = (EditText)findViewById(R.id.prenom);

        sau_personne.setOnClickListener(this);
        sauv_nom.setOnClickListener(this);
        charger_personne.setOnClickListener(this);
        charger_nom.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == sauv_nom){
            Toast.makeText(this, "Sauv Nom", Toast.LENGTH_SHORT).show();
            SharedPreferencesUtils.sauvegarderNom(this, nom.getText().toString());
        }else if (view == charger_nom){
            Toast.makeText(this, "char Nom", Toast.LENGTH_SHORT).show();
            nom.setText(SharedPreferencesUtils.recupererNom(this));
        }else if (view == sau_personne){
            Toast.makeText(this, "Sauv pers", Toast.LENGTH_SHORT).show();
        }else if (view == charger_personne){
            Toast.makeText(this, "charg pers", Toast.LENGTH_SHORT).show();
        }
    }
}
