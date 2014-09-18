package com.mileem.mileem.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mileem.mileem.R;

/**
 * Created by JuanMarchese on 18/09/2014.
 */
public class PublicationDetailFragment extends BaseFragment {

    public static final String TAG = PublicationDetailFragment.class.getSimpleName();
    private Context context;

    public PublicationDetailFragment() {
        super(TAG);
    }

    public static PublicationDetailFragment newInstance(int publicationDetailId) {
        PublicationDetailFragment myFragment = new PublicationDetailFragment();

        Bundle args = new Bundle();
        args.putInt("publicationDetailId", publicationDetailId);
        myFragment.setArguments(args);

        return myFragment;
    }

    public int getPublicationId(){
        return getArguments().getInt("publicationDetailId", -1);
    }

    @Override
    public String getTittle() {
        return "Detalle";
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            requestPublicationData();
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.publication_detail_fragment, container, false);
        context = rootView.getContext();
        buildPublicationView(rootView);
        requestPublicationData();
        return rootView;
    }

    private void buildPublicationView(View rootView) {

    }

    private void requestPublicationData() {

    }
}
