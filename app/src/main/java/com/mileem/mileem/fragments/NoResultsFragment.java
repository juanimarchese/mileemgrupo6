package com.mileem.mileem.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.mileem.mileem.R;
import com.mileem.mileem.adapters.PublicationListAdapter;
import com.mileem.mileem.models.PublicationDetails;

import java.util.ArrayList;

/**
 * Created by Juan-Asus on 09/09/2014.
 */
public class NoResultsFragment extends BaseFragment {

    public static final String TAG = NoResultsFragment.class.getSimpleName();


    public NoResultsFragment() {
        super(TAG);
    }

    @Override
    public String getTittle() {
        return "Resultados";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.no_results_fragment, container, false);
        /*ImageView image = (ImageView) rootView.findViewById(R.id.noresult);
        image.setImageResource(R.drawable.noresult);*/
        return rootView;
    }



}
