package com.mileem.mileem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
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
    private PublicationDetails publicationDetails;
    private Bundle mBundle;
    MapView mMapView;


    protected MapFragment() {
        super(TAG);
    }

    public static MapFragment newInstance(PublicationDetails publicationDetails) {
        com.mileem.mileem.fragments.MapFragment myFragment = new com.mileem.mileem.fragments.MapFragment();
        myFragment.setPublicationDetails(publicationDetails);
        return myFragment;
    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            map = mMapView.getMap();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.map_fragment, container, false);
            if (map == null) {
                MapsInitializer.initialize(getActivity());
                mMapView = (MapView) rootView.findViewById(R.id.map);
                mMapView.onCreate(mBundle);
                setUpMapIfNeeded();
            }
            if (map != null) {
                this.fillLayout();
            }
            return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = savedInstanceState;
    }

    public PublicationDetails getPublicationDetails() {
        return publicationDetails;
    }

    public void setPublicationDetails(PublicationDetails publicationDetails) {
        this.publicationDetails = publicationDetails;
    }

    private void fillLayout() {
        String price = "Precio: " + String.valueOf(publicationDetails.getPrice() + ' ' + publicationDetails.getCurrency());
        double latitude = Double.valueOf(publicationDetails.getLatitude());
        double longitude = Double.valueOf(publicationDetails.getLongitude());

        final LatLng pointLatLng = new LatLng(latitude, longitude);
        final LatLng pointLatLngTop = new LatLng(latitude+.01, longitude-.01);
        final LatLng pointLatLngBottom = new LatLng(latitude-.01, longitude+.01);
        map.addMarker(new MarkerOptions()
                .position(pointLatLng)
                .title("Mileen")
                .snippet(price)
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }
}
