package com.mileem.mileem.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.mileem.mileem.R;
import com.mileem.mileem.models.PublicationDetails;

/**
 * Created by ramirodiaz on 10/10/14.
 */
public class LocationFragment extends BaseFragment {

    public static final String TAG = LocationFragment.class.getSimpleName();
    private PublicationDetails publicationDetails;

    protected LocationFragment() {
        super(TAG);
    }

    public static LocationFragment newInstance(PublicationDetails publicationDetails) {
        LocationFragment myFragment = new LocationFragment();
        myFragment.setPublicationDetails(publicationDetails);
        return myFragment;
    }

    public PublicationDetails getPublicationDetails() {
        return publicationDetails;
    }

    public void setPublicationDetails(PublicationDetails publicationDetails) {
        this.publicationDetails = publicationDetails;
    }

    private void setTextViewText(View rootView, int widgetId, String text) {
        TextView view = (TextView) rootView.findViewById(widgetId);
        view.setText(text);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.location_fragment, container, false);
        Button switchButton = (Button) rootView.findViewById(R.id.switch_button);
        switchButton.setOnTouchListener(new View.OnTouchListener() {
             @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                 StreetViewPanoramaFragment svPFragment = (com.google.android.gms.maps.StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.streetviewpanorama);
//                 svp = svPFragment.getStreetViewPanorama();
//                 svp.setPosition(pointLatLng);
//
//                 FragmentManager fm = getFragmentManager();
//                 FragmentTransaction transaction = fm.beginTransaction();
//                 transaction.replace(R.id.container, svPFragment);
//                 transaction.commit();
            return false;
            }
        });
        this.fillLayout(rootView);
        return rootView;
    }

    private void fillLayout(View rootView) {
        setTextViewText(rootView, R.id.direccion, getPublicationDetails().getAddress());
        setTextViewText(rootView,R.id.barrio, getPublicationDetails().getNeighborhood().getName());
        com.mileem.mileem.fragments.MapFragment map = MapFragment.newInstance(publicationDetails);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.gsm_container, map);
        transaction.commit();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            this.fillLayout(getView());
        }
    }

    @Override
    public String getTittle() {
        return null;
    }
}
