package com.example.gestiondemenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gestiondemenu.model.Evenement;

public class DetailActivity extends AppCompatActivity {

    public static final String EVENEMENT_EXTRA = "EVENEMET_EXTRA";

    private TextView tv_title;
    private ImageView iv;
    private Evenement evenement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv_title = (TextView)findViewById(R.id.tv_title);
        iv = (ImageView)findViewById(R.id.iv);

        //on Récupère l'événement
        evenement = (Evenement)getIntent().getSerializableExtra(EVENEMENT_EXTRA);

        tv_title.setText(evenement.getDescription());
        //Glide.with(this).load(evenement.getUrl()).error(R.drawable.ic_error).into(iv);
    }
}
