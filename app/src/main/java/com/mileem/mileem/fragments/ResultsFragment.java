package com.mileem.mileem.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mileem.mileem.AppController;
import com.mileem.mileem.R;
import com.mileem.mileem.activities.MainActivity;
import com.mileem.mileem.adapters.PublicationListAdapter;
import com.mileem.mileem.models.Pagination;
import com.mileem.mileem.models.PublicationDetails;
import com.mileem.mileem.models.PublicationFilter;
import com.mileem.mileem.models.PublicationOrder;
import com.mileem.mileem.networking.MockDataManager;
import com.mileem.mileem.networking.PublicationsDataManager;
import com.mileem.mileem.widgets.EndlessListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan-Asus on 09/09/2014.
 */
public class ResultsFragment extends BaseFragment implements EndlessListView.EndLessListener{

    public static final String TAG = ResultsFragment.class.getSimpleName();

    private ProgressDialog pDialog;
    private ArrayList<PublicationDetails> publicationList = new ArrayList<PublicationDetails>();
    private EndlessListView listView;
    private PublicationListAdapter adapter;
    private Context context;
    private PublicationsDataManager dm;
    private PublicationOrder order;
    private PublicationFilter filter;

    private int pagina = 0;

    public ResultsFragment() {
        super(TAG);
    }

    @Override
    public String getTittle() {
        return "Resultados";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.results_fragment, container, false);
        context = rootView.getContext();
        listView = (EndlessListView) rootView.findViewById(R.id.publicationList);
        listView.setLoadingView(R.layout.loading_layout);
        listView.setListener(this);
        adapter = new PublicationListAdapter(publicationList,rootView.getContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), "Vista de Detalles", Toast.LENGTH_LONG).show();
            }
        });

        //TODO Absorver estos valores de la vista, de alguna madera(Puede ser un objetod e modelo en comun en la activity?)
        int[] neighborhoods = {};
        int[] environments = {};
        int[] propertyTypes = {};
        int[] operationTypes = {};
        filter = new PublicationFilter(neighborhoods, propertyTypes, operationTypes, environments);
        order = new PublicationOrder(PublicationOrder.OrderBy.PRIORITY, PublicationOrder.Order.ASC);
        requestFirstPageData();

        return rootView;
    }

    private void requestFirstPageData() {
        showPDialog();
        pagina = 0;
        publicationList.clear();
        //requestData();

        Pagination pagination = new Pagination(10);

        dm = new PublicationsDataManager();
        try {
            dm.getPublicationsList(filter, pagination, order, new PublicationsDataManager.PublicationsCallbackHandler() {
                @Override
                public void onComplete(ArrayList collection) {
                    if(!collection.isEmpty()){
                        listView.addNewData(collection);
                        hidePDialog();
                    } else{
                        hidePDialog();
                        ((MainActivity) context).displayView(new NoResultsFragment());
                    }
                }

                @Override
                public void onFailure(Error error) {
                    showError();
                    hidePDialog();
                }

            });

        } catch (JSONException e) {
            showError();
            hidePDialog();
        }

    }

    private void showError() {
        Toast.makeText(getActivity(), "Error al tratar de obtener los datos", Toast.LENGTH_LONG).show();
        ((MainActivity) context).displayViewForMenu(0);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            //Checkear si cambiaron los criterios de busqueda, filtro u ordenamiento tambien, para no hacer busquedas sin sentido!
            requestFirstPageData();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_search).setVisible(true);
        menu.findItem(R.id.action_sort).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_sort:
                order = new PublicationOrder(PublicationOrder.OrderBy.PRIORITY, PublicationOrder.Order.ASC);
                requestFirstPageData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void showPDialog() {
        pDialog = new ProgressDialog(context);
        // Showing progress dialog before making http request
        pDialog.setMessage("Buscando...");
        pDialog.show();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void loadData() {
        pagina += 1;
        //	new data loader
        try {
            dm.getNextPage(new PublicationsDataManager.PublicationsCallbackHandler() {
                @Override
                public void onComplete(ArrayList collection) {
                    listView.addNewData(collection);
                }

                @Override
                public void onFailure(Error error) {
                    showError();
                }

            });

        } catch (JSONException e) {
            showError();
        }
    }


}
