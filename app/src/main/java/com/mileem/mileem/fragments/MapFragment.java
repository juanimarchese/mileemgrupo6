package com.mileem.mileem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mileem.mileem.R;
import com.mileem.mileem.models.PublicationDetails;

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

    public static MapFragment newInstance(PublicationDetails publicationDetails) {
        MapFragment myFragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString("publicationAddress", publicationDetails.getAddress());
        args.putString("publicationNeigthboorhood", publicationDetails.getNeighborhood().getName());
        args.putString("publicationLatitude", publicationDetails.getLatitude());
        args.putString("publicationLongitude", publicationDetails.getLongitude());
        myFragment.setArguments(args);
        return myFragment;
    }

    private void setTextViewText(View rootView, int widgetId, String text) {
        TextView view = (TextView) rootView.findViewById(widgetId);
        view.setText(text);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.map_fragment, container, false);
        com.google.android.gms.maps.MapFragment mapFragment = (com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(R.id.map);

        setTextViewText(rootView,R.id.direccion,getArguments().getString("publicationAddress"));
        setTextViewText(rootView,R.id.barrio,getArguments().getString("publicationNeigthboorhood"));

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
