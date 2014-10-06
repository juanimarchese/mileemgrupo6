package com.mileem.mileem.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mileem.mileem.R;
import com.mileem.mileem.activities.MainActivity;
import com.mileem.mileem.adapters.PublicationListAdapter;
import com.mileem.mileem.models.Pagination;
import com.mileem.mileem.models.PublicationDetails;
import com.mileem.mileem.models.PublicationFilter;
import com.mileem.mileem.models.PublicationOrder;
import com.mileem.mileem.networking.PublicationsDataManager;
import com.mileem.mileem.widgets.EndlessListView;

import java.util.ArrayList;

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
        buildEndlessListView(rootView);

        //TODO Absorver estos valores de la vista, de alguna madera(Puede ser un objetod e modelo en comun en la activity?)
        createDefaultRequestInfo();
        requestFirstPageData();

        return rootView;
    }

    private int[] getArrayFromParams(String key){
        if(getArguments() != null && !getArguments().isEmpty()){
            return getArguments().getIntArray(key);
        }
        return new int[]{};
    }

    private void createDefaultRequestInfo() {
        buildFilter();
        order = new PublicationOrder(PublicationOrder.OrderBy.PRIORITY, PublicationOrder.Order.ASC);
    }

    private void buildFilter() {
        int[] neighborhoods = getArrayFromParams("neighborhoods");
        int[] environments = getArrayFromParams("environments");
        int[] propertyTypes = getArrayFromParams("propertyTypes");
        int[] operationTypes = getArrayFromParams("operationTypes");

        filter = new PublicationFilter(neighborhoods, propertyTypes, operationTypes, environments);
    }


    private void buildEndlessListView(View rootView) {
        listView = (EndlessListView) rootView.findViewById(R.id.publicationList);
        listView.setLoadingView(R.layout.loading_layout);
        listView.setListener(this);
        adapter = new PublicationListAdapter(publicationList,rootView.getContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    PublicationDetails publicationDetail = (PublicationDetails) parent.getItemAtPosition(position);
                    int publicationDetailId = publicationDetail.getId();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction tx = fm.beginTransaction();
                    tx.add(R.id.frame_container, PublicationDetailFragment.newInstance(publicationDetailId) ).addToBackStack( "tag" ).commit();
                } catch (Throwable e){
                    showError();
                }

            }
        });
    }

    private void requestFirstPageData() {
        showPDialog(context);
        pagina = 0;
        publicationList.clear();
        adapter.clear();
        //requestData();

        Pagination pagination = new Pagination(10);

        dm = new PublicationsDataManager();
        try {
            dm.getPublicationsList(filter, pagination, order, new PublicationsDataManager.PublicationsCallbackHandler() {
                @Override
                public void onComplete(ArrayList collection) {
                    if(!collection.isEmpty()){
                        listView.addNewData(collection);
                    } else{
                        ((MainActivity) context).displayView(new NoResultsFragment());
                    }
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
        Toast.makeText(getActivity(), "Error al tratar de obtener los datos", Toast.LENGTH_LONG).show();
        createDefaultRequestInfo();
        ((MainActivity) context).displayViewForMenu(0);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            //TODO - Checkear si cambiaron los criterios de busqueda, filtro u ordenamiento tambien, para no hacer busquedas sin sentido!
            buildFilter();
            requestFirstPageData();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_search).setVisible(true);
        MenuItem sortItem = menu.findItem(R.id.action_sort);
        sortItem.setVisible(true);
        buildOrderSubMenuItem(sortItem, R.id.precio_asc, PublicationOrder.OrderBy.PRICE, PublicationOrder.Order.ASC);
        buildOrderSubMenuItem(sortItem, R.id.precio_dsc, PublicationOrder.OrderBy.PRICE, PublicationOrder.Order.DESC);
        buildOrderSubMenuItem(sortItem, R.id.relevancia_asc, PublicationOrder.OrderBy.PRIORITY, PublicationOrder.Order.ASC);
        buildOrderSubMenuItem(sortItem, R.id.relevancia_dsc, PublicationOrder.OrderBy.PRIORITY, PublicationOrder.Order.DESC);
        buildOrderSubMenuItem(sortItem, R.id.fecha_asc, PublicationOrder.OrderBy.PUBLISH_DATE, PublicationOrder.Order.ASC);
        buildOrderSubMenuItem(sortItem, R.id.fecha_dsc,PublicationOrder.OrderBy.PUBLISH_DATE,PublicationOrder.Order.DESC);
        menu.findItem(R.id.action_refresh).setVisible(true);
    }

    private void buildOrderSubMenuItem(MenuItem sortItem,int subItemId, final PublicationOrder.OrderBy orderBy, final PublicationOrder.Order pubOrder) {
        MenuItem porderItem = sortItem.getSubMenu().findItem(subItemId);
        porderItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                order = new PublicationOrder(orderBy, pubOrder);
                requestFirstPageData();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_sort:
                return true;
            case R.id.action_refresh:
                requestFirstPageData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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

        } catch (Throwable e) {
            showError();
        }
    }


}
