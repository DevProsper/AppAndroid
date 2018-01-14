package com.example.localisation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//Demarrer la localisation dans onStart puis l'arrêter dans onStop

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    private TextView tv;
    private Button start, stop;

    private static final int FINE_LOCATION_REQ_CODE = 64;

    //Abonnement a un provider (RESEAU ou Puce GPS)
    private LocationManager manager = null;//Outil qui permet a s'abonner au provider

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        tv = (TextView) findViewById(R.id.tv);

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == start) {
            //Si nous avons la permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                demarrerLocalisation();
            } else {
                //Je demande la permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_REQ_CODE);

            }
        } else if (view == stop) {
            demarrerLocalisation();
        }
    }

    //Pour obtenir la reponse lors de la demande de la permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_REQ_CODE) {
            //Si nous avons la permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                demarrerLocalisation();
            } else {
                Toast.makeText(this, "On ne peut pas utilisé la localisation sans la permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void demarrerLocalisation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //On test si le provider existe avant de s'y abonner
        if (manager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER)) {
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, this);
        }

        if (manager.getAllProviders().contains(LocationManager.GPS_PROVIDER)) {
            //Me rappeler tous les 5s avec une distance de 0m d'écart, si je me suis déplacer de 0m
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
        }
    }

    private void arreterLocalisation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        //Nouvelles Localisatios
        tv.setText(location.getLatitude() + "" +location.getLongitude() + "\n" + tv.getText());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        //Etat est ce que c'est désactivé ou activé
    }

    @Override
    public void onProviderEnabled(String s) {
        //M'indique si le provider a été activé
        Toast.makeText(this, "activé", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        //Si un provider a été desactivé
        Toast.makeText(this, "desactivé", Toast.LENGTH_SHORT).show();
    }
}
