package com.example.stationvelo.model.beans.trajet;

import android.graphics.Color;

import com.example.stationvelo.model.beans.direction.DirectionResult;
import com.example.stationvelo.model.beans.direction.Leg;
import com.example.stationvelo.model.beans.direction.Route;
import com.example.stationvelo.model.beans.direction.Step;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by DevProsper on 03/03/2018.
 */

public class Trajet {

    private MarkerOptions depart, arrive;
    private PolylineOptions polylineOptions;
    private LatLngBounds latLngBounds;

    public Trajet(DirectionResult directionResult) throws Exception {
        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.BLUE);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        if (directionResult.getRoutes() != null){
            for (Route route : directionResult.getRoutes()){
                if (route.getLegs() != null){
                    for (Leg leg : route.getLegs()){
                        if (leg.getSteps() !=null){
                            for (Step step : leg.getSteps()){
                                polylineOptions.add(new LatLng(step.getStart_location().getLat(), step.getStart_location().getLng()));
                            }
                        }else if (leg.getStart_location() != null){
                            polylineOptions.add(new LatLng(leg.getStart_location().getLat(), leg.getStart_location().getLng()));
                        }
                        if (leg.getEnd_location() != null){
                            polylineOptions.add(new LatLng(leg.getEnd_location().getLat(), leg.getEnd_location().getLng()));
                        }
                    }
                }
            }
        }

        if (polylineOptions.getPoints().isEmpty()){
            throw new Exception("Aucun chemin");
        }

        for (LatLng latLng : polylineOptions.getPoints()){
            builder.include(latLng);
        }

        latLngBounds = builder.build();

        //Marquer sur l'emplacement de dépard
        depart = new MarkerOptions();
        depart.position(polylineOptions.getPoints().get(0));
        depart.title("Début");
        depart.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

        //Marquer sur l'emplacement de dépard
        arrive = new MarkerOptions();
        arrive.position(polylineOptions.getPoints().get(polylineOptions.getPoints().size() - 1));
        arrive.title("Fin");
        arrive.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
    }

    public MarkerOptions getDepart() {
        return depart;
    }

    public void setDepart(MarkerOptions depart) {
        this.depart = depart;
    }

    public MarkerOptions getArrive() {
        return arrive;
    }

    public void setArrive(MarkerOptions arrive) {
        this.arrive = arrive;
    }

    public PolylineOptions getPolylineOptions() {
        return polylineOptions;
    }

    public void setPolylineOptions(PolylineOptions polylineOptions) {
        this.polylineOptions = polylineOptions;
    }

    public LatLngBounds getLatLngBounds() {
        return latLngBounds;
    }

    public void setLatLngBounds(LatLngBounds latLngBounds) {
        this.latLngBounds = latLngBounds;
    }
}
