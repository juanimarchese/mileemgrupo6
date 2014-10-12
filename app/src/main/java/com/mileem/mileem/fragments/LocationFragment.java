package com.mileem.mileem.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.mileem.mileem.R;
import com.mileem.mileem.models.PublicationDetails;

/**
 * Created by ramirodiaz on 10/10/14.
 */
public class LocationFragment extends BaseFragment {

    public static final String TAG = LocationFragment.class.getSimpleName();
    private PublicationDetails publicationDetails;
    private MapFragment mapFragment = null;
    private StreetViewFragment streetViewFragment = null;
    Boolean showingMap = true;
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.location, menu);
        MenuItem item = menu.findItem(R.id.switch_mode);
        int locationButtonDrawable = showingMap? R.drawable.street_view_icon : R.drawable.map_icon;
        item.setIcon(getResources().getDrawable(locationButtonDrawable));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.switch_mode:
                Fragment newFragment = null;
                if (showingMap) {
                    newFragment = StreetViewFragment.newInstance(publicationDetails);
                } else {
                    newFragment = MapFragment.newInstance(publicationDetails);
                }
                showingMap = !showingMap;
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                                R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                        .replace(R.id.gsm_container, newFragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.location_fragment, container, false);
        mapFragment = MapFragment.newInstance(publicationDetails);
        streetViewFragment = StreetViewFragment.newInstance(publicationDetails);
        this.fillLayout(rootView);
        this.setHasOptionsMenu(true);
        return rootView;
    }

    private void fillLayout(View rootView) {
        setTextViewText(rootView, R.id.direccion, getPublicationDetails().getAddress());
        setTextViewText(rootView,R.id.barrio, getPublicationDetails().getNeighborhood().getName());

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.gsm_container, mapFragment, MapFragment.TAG);
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
