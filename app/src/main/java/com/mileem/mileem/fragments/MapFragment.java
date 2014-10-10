package com.mileem.mileem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mileem.mileem.R;

/**
 * Created by ramirodiaz on 10/10/14.
 */
public class MapFragment extends BaseFragment {

    public static final String TAG = MapFragment.class.getSimpleName();
    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    private GoogleMap map;

    protected MapFragment() {
        super(TAG);
    }

    public static MapFragment newInstance() {
        MapFragment myFragment = new MapFragment();
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.map_fragment, container, false);
        com.google.android.gms.maps.MapFragment mapFragment = (com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(R.id.map);
        map = mapFragment.getMap();

        //Ejemplo
        Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
                .title("Hamburg"));
        Marker kiel = map.addMarker(new MarkerOptions()
                .position(KIEL)
                .title("Kiel")
                .snippet("Kiel is cool")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_launcher)));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        return rootView;
    }

    @Override
    public String getTittle() {
        return null;
    }
}
