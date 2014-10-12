package com.mileem.mileem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mileem.mileem.R;
import com.mileem.mileem.models.PublicationDetails;

/**
 * Created by ramirodiaz on 12/10/14.
 */
public class MapFragment extends BaseFragment {
    public static final String TAG = MapFragment.class.getSimpleName();
    private GoogleMap map;

    protected MapFragment() {
        super(TAG);
    }

    public static MapFragment newInstance(PublicationDetails publicationDetails) {
        com.mileem.mileem.fragments.MapFragment myFragment = new com.mileem.mileem.fragments.MapFragment();
        Bundle args = new Bundle();
        args.putString("publicationLatitude", publicationDetails.getLatitude());
        args.putString("publicationLongitude", publicationDetails.getLongitude());
        args.putString("publicationPrice", "Precio: " + String.valueOf(publicationDetails.getPrice() + ' ' + publicationDetails.getCurrency()));
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.map_fragment, container, false);

        if (map == null) {
            com.google.android.gms.maps.MapFragment mapFragment = (com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(R.id.map);
            map = mapFragment.getMap();
        }

        this.fillLayout();
        return rootView;
    }

    private void fillLayout() {
        double latitude = Double.valueOf(getArguments().getString("publicationLatitude"));
        double longitude = Double.valueOf(getArguments().getString("publicationLongitude"));

        final LatLng pointLatLng = new LatLng(latitude, longitude);
        final LatLng pointLatLngTop = new LatLng(latitude+.01, longitude-.01);
        final LatLng pointLatLngBottom = new LatLng(latitude-.01, longitude+.01);
        map.addMarker(new MarkerOptions()
                .position(pointLatLng)
                .title("Mileen")
                .snippet(getArguments().getString("publicationPrice"))
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.house_pin)));

        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLngBounds bounds = new LatLngBounds.Builder()
                        .include(pointLatLngTop)
                        .include(pointLatLng)
                        .include(pointLatLngBottom)
                        .build();
                map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 15));
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            this.fillLayout();
        }
    }

    @Override
    public String getTittle() {
        return null;
    }
}
