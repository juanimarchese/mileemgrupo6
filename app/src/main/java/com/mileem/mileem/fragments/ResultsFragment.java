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
import com.mileem.mileem.models.PublicationDetails;
import com.mileem.mileem.networking.MockDataManager;
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


        requestFirstPageData();
        return rootView;
    }

    private void requestFirstPageData() {
        showPDialog();
        pagina = 0;
        publicationList.clear();
        requestData();
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
        requestData();
    }


    private void requestData() {
        new FakeLoader().execute();
    }

    private class FakeLoader extends AsyncTask<Void,Void,List<PublicationDetails>> {

        @Override
        protected List<PublicationDetails> doInBackground(Void... params) {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return MockDataManager.getPublicationsByPage(pagina);
        }

        @Override
        protected void onPostExecute(List<PublicationDetails> result) {
            super.onPostExecute(result);
            listView.addNewData(result);
            hidePDialog();
        }
    }
}
