package com.mileem.mileem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.StreetViewPanoramaView;
import com.google.android.gms.maps.model.LatLng;
import com.mileem.mileem.R;
import com.mileem.mileem.models.PublicationDetails;

/**
 * Created by ramirodiaz on 12/10/14.
 */
public class StreetViewFragment extends BaseFragment {
    public static final String TAG = StreetViewFragment.class.getSimpleName();
    private StreetViewPanorama streetViewPanorama;
    private StreetViewPanoramaView mStreetViewPanoramaView;
    private PublicationDetails publicationDetails;
    private Bundle mBundle;

    protected StreetViewFragment() {
        super(TAG);
    }

    public static StreetViewFragment newInstance(PublicationDetails publicationDetails) {
        StreetViewFragment myFragment = new StreetViewFragment();
        myFragment.setPublicationDetails(publicationDetails);
        return myFragment;
    }

    private void setUpStreetPanoramaViewIfNeeded() {
        if (streetViewPanorama == null) {
            streetViewPanorama = mStreetViewPanoramaView.getStreetViewPanorama();
            if (streetViewPanorama != null) {
                this.fillLayout();
            }
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = savedInstanceState;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.street_view_fragment, container, false);
        if (streetViewPanorama == null) {
            MapsInitializer.initialize(getActivity());
            mStreetViewPanoramaView = (StreetViewPanoramaView) rootView.findViewById(R.id.streetviewpanorama);
            mStreetViewPanoramaView.onCreate(mBundle);
            setUpStreetPanoramaViewIfNeeded();
        }
        return rootView;
    }

    public PublicationDetails getPublicationDetails() {
        return publicationDetails;
    }

    public void setPublicationDetails(PublicationDetails publicationDetails) {
        this.publicationDetails = publicationDetails;
    }

    private void fillLayout() {
        double latitude = Double.valueOf(publicationDetails.getLatitude());
        double longitude = Double.valueOf(publicationDetails.getLongitude());
        final LatLng pointLatLng = new LatLng(latitude, longitude);
        streetViewPanorama.setPosition(pointLatLng);
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
}