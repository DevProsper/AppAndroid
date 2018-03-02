package com.example.stationvelo;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.stationvelo.model.beans.Station;
import com.example.stationvelo.model.webservice.GetStationAT;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GetStationAT.GetStationATResult {

    private GoogleMap mMap;
    private ArrayList<Station> stations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        stations = new ArrayList<Station>();
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

        rafraichirCarte();
    }

    public void rafraichirCarte(){
        if (mMap == null){
            return;
        }
        mMap.clear();

        if (!stations.isEmpty()){

            //Afficher ou centrer une carte après l'animation
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            for (Station station : stations){
                final MarkerOptions marker = new MarkerOptions();
                LatLng latLng = new LatLng(station.getPosition().getLat(), station.getPosition().getLng());

                builder.include(latLng);

                marker.position(latLng);
                marker.title(station.getName());

                mMap.addMarker(marker).setTag(station);
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
}
