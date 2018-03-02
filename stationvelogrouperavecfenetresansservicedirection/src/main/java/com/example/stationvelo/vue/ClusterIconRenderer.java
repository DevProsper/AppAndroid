package com.example.stationvelo.vue;

import android.content.Context;

import com.example.stationvelo.model.beans.Station;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by DevProsper on 02/03/2018.
 */

//Modifier la couleur des marqueurs en fonction des vélos disponible ou non
public class ClusterIconRenderer extends DefaultClusterRenderer<Station> {

    public ClusterIconRenderer(Context context, GoogleMap map, ClusterManager clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(Station station, MarkerOptions markerOptions) {
        markerOptions.title(station.getName());

        if (station.getAvailable_bikes() > 0 && station.getAvailable_bike_stands() > 0){
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }else if (station.getAvailable_bikes() == 0){
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        }else if (station.getAvailable_bike_stands() == 0){
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        }else{
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }

        super.onBeforeClusterItemRendered(station, markerOptions);
    }
}
