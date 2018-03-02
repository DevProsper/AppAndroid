package com.example.stationvelo;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stationvelo.model.beans.Station;
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
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GetStationAT.GetStationATResult, ClusterManager.OnClusterItemClickListener<Station>, GoogleMap.InfoWindowAdapter {

    private GoogleMap mMap;
    private ArrayList<Station> stations;
    private Station stationClick;

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

       new GetStationAT(this).execute();
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

            int padding = 100;
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(
                    builder.build(), padding
            ));
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
}
