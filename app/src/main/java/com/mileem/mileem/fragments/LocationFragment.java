package com.mileem.mileem.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import android.widget.Toast;

import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.mileem.mileem.R;
import com.mileem.mileem.activities.MainActivity;
import com.mileem.mileem.models.PublicationDetails;

/**
 * Created by ramirodiaz on 10/10/14.
 */
public class LocationFragment extends BaseFragment {

    public static final String TAG = LocationFragment.class.getSimpleName();
    private MapFragment mapFragment = null;
    private StreetViewFragment streetViewFragment = null;
    Boolean showingMap = true;
    protected LocationFragment() {
        super(TAG);
    }

    public static LocationFragment newInstance(PublicationDetails publicationDetails) {
        LocationFragment myFragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putParcelable("publication", publicationDetails);
        myFragment.setArguments(args);
        return myFragment;
    }

    private void setTextViewText(View rootView, int widgetId, String text) {
        TextView view = (TextView) rootView.findViewById(widgetId);
        view.setText(text);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.location, menu);

        MenuItem item = menu.findItem(R.id.action_switch_mode);
        int locationButtonDrawable = showingMap? R.drawable.street_view_icon : R.drawable.map_icon;
        item.setIcon(getResources().getDrawable(locationButtonDrawable));

        MenuItem actionSwitchItem = menu.findItem(R.id.action_switch_mode).setVisible(true);
        final PublicationDetails publication = getArguments().getParcelable("publication");
        actionSwitchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Fragment newFragment = null;
                if (showingMap) {
                    mapFragment = MapFragment.newInstance(publication);
                    newFragment = streetViewFragment;
                } else {
                    streetViewFragment = StreetViewFragment.newInstance(publication);
                    newFragment = mapFragment;
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
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_fragment, container, false);
        this.fillLayout(rootView);
        return rootView;
    }

    private void fillLayout(View rootView) {
        PublicationDetails publication = getArguments().getParcelable("publication");
        setTextViewText(rootView,R.id.direccion, publication.getAddress());
        setTextViewText(rootView,R.id.barrio, publication.getNeighborhood().getName());

        mapFragment = MapFragment.newInstance(publication);
        streetViewFragment = StreetViewFragment.newInstance(publication);

        showingMap = true;

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
    public void onResume() {
        super.onResume();
        this.fillLayout(getView());
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_switch_mode);
        item.setVisible(true);
    }

    @Override
    public String getTittle() {
        return null;
    }
}
