package com.mileem.mileem.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mileem.mileem.R;
import com.mileem.mileem.activities.MainActivity;
import com.mileem.mileem.models.PublicationDetails;
import com.mileem.mileem.networking.PublicationDetailsDataManager;

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
        //TODO - Inicializar el Layout de Vista

    }

    private void requestPublicationData() {
        showPDialog(context);
        try {
            new PublicationDetailsDataManager().getDetails(getPublicationId(), new PublicationDetailsDataManager.PublicationsDetailsCallbackHandler() {
                @Override
                public void onComplete(PublicationDetails publication) {
                    //TODO - Bindear datos con la vista
                    hidePDialog();
                }

                @Override
                public void onFailure(Error error) {
                    showError();
                    hidePDialog();
                }
            });
        } catch (Throwable e) {
            showError();
            hidePDialog();
        }
    }

    private void showError() {
        Toast.makeText(getActivity(), "Error al tratar de obtener los datos de la publicación", Toast.LENGTH_LONG).show();
        ((MainActivity) context).displayViewForMenu(1);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_contact).setVisible(true);
        menu.findItem(R.id.action_amenities).setVisible(true);
        menu.findItem(R.id.action_location).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_contact:
                Toast.makeText(context, "Vista de Contacto", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_amenities:
                Toast.makeText(context, "Vista de Amenities", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_location:
                Toast.makeText(context, "Vista de Mapa", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
