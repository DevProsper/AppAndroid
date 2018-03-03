package com.example.stationvelo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stationvelo.model.beans.Station;
import com.example.stationvelo.model.beans.direction.DirectionResult;
import com.example.stationvelo.model.beans.trajet.Trajet;
import com.example.stationvelo.model.webservice.GetDirectionAT;
import com.example.stationvelo.model.webservice.GetStationAT;
import com.example.stationvelo.vue.ClusterIconRenderer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GetStationAT.GetStationATResult,
        ClusterManager.OnClusterItemClickListener<Station>,
        GoogleMap.InfoWindowAdapter,
        ClusterManager.OnClusterItemInfoWindowClickListener<Station>,GetDirectionAT.GetDirectionATResult {

    private GoogleMap mMap;
    private ArrayList<Station> stations;
    private Station stationClick;
    private Trajet trajet;

    private static final int LOCATION_REQ_CODE = 456;

    private ClusterManager<Station> mClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        stations = new ArrayList<>();

        stationClick = null;
        trajet = null;

        //Demande de permission de la localisation
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQ_CODE);
        }

       new GetStationAT(this).execute();
    }

    //Retour de la demande à la permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Si j'ai la persmission et si ma Map n'a pas bien été charger dans le cas contraire, on charge la localisation
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if (mMap != null){
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mClusterManager = new ClusterManager<>(this, mMap);
        //Modifier l'icon du marqueur
        mClusterManager.setRenderer(new ClusterIconRenderer(this, mMap, mClusterManager));

        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(this);

        mMap.setOnCameraIdleListener(mClusterManager);//Permet d'être au courant au moment ou le mouvement de camera se termine
        mMap.setOnMarkerClickListener(mClusterManager);//Clic sur le markeur

        mMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());

        //Si on a la permission, on affiche la position sur la carte
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mMap.setMyLocationEnabled(true);
        }

        rafraichirCarte();
    }



    public void rafraichirCarte(){
        if (mMap == null){
            return;
        }
        mMap.clear();

        if (!stations.isEmpty()){

            mClusterManager.clearItems();
            mClusterManager.addItems(stations);

            //Afficher ou centrer une carte après l'animation
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            for (Station station : stations){

                builder.include(station.getPosition());
            }

            if (trajet == null){

                int padding = 100;
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(
                        builder.build(), padding
                ));
            }else{
                //On affiche le trajet
                mMap.addMarker(trajet.getDepart());
                mMap.addPolyline(trajet.getPolylineOptions());
                mMap.addMarker(trajet.getArrive());

                int padding = 100;
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(
                        builder.build(), padding
                ));
            }

        }
    }

    @Override
    public void stationChargees(ArrayList<Station> stations) {
        this.stations.clear();
        this.stations.addAll(stations);
        rafraichirCarte();
    }

    @Override
    public void getStationATResultErreur(Exception e) {
        e.printStackTrace();
        Toast.makeText(MapsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    //Click sur un cluster
    @Override
    public boolean onClusterItemClick(Station station) {
        stationClick = station;
        return false;
    }


    //Pour gerer toute la fenêtre
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    //pour gerer l'interieur de la fenêtre
    @Override
    public View getInfoContents(Marker marker) {
        if (stationClick == null){
            return null;
        }
        View view = LayoutInflater.from(this).inflate(R.layout.marker_station, null);

        TextView tv_nom = (TextView)view.findViewById(R.id.tv_nom);
        TextView tv_adress = (TextView)view.findViewById(R.id.tv_adress);
        TextView tv_velo_dispo = (TextView)view.findViewById(R.id.tv_velo_dispo);
        TextView tv_velo_vide = (TextView)view.findViewById(R.id.tv_velo_vide);

        tv_nom.setText(stationClick.getName());
        tv_adress.setText(stationClick.getAddress());
        tv_velo_dispo.setText(stationClick.getAvailable_bikes() + "");
        tv_velo_vide.setText(stationClick.getAvailable_bike_stands() + "");

        return view;
    }

    //Click sur la fenêtre d'un markeur
    @Override
    public void onClusterItemInfoWindowClick(Station station) {

        if (mMap.isMyLocationEnabled()){
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(new Criteria(), true));
                new GetDirectionAT(new LatLng(location.getLatitude(), location.getLongitude()), station.getPosition(), this).execute();
            }
        }else{
            Toast.makeText(this, "Veuillez activer votre location", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void directionChargees(DirectionResult directionResult) {
        try {
            trajet = new Trajet(directionResult);
            rafraichirCarte();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void GetDirectionATResultErreur(Exception e) {
        e.printStackTrace();
        Toast.makeText(MapsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
