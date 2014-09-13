package com.mileem.mileem.fragments;

import android.app.ProgressDialog;
import android.content.Context;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Juan-Asus on 09/09/2014.
 */
public class ResultsFragment extends BaseFragment {

    public static final String TAG = ResultsFragment.class.getSimpleName();

    private static final String url = "http://10.0.2.2/api/property-search";    //TODO - Cambiar a la url donde vienen los datos
    private ProgressDialog pDialog;
    private ArrayList<PublicationDetails> publicationList = new ArrayList<PublicationDetails>();
    private ListView listView;
    private PublicationListAdapter adapter;

    private int cant = 1;

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
        listView = (ListView) rootView.findViewById(R.id.publicationList);
        adapter = new PublicationListAdapter(publicationList,rootView.getContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), "Vista de Detalles", Toast.LENGTH_LONG).show();
            }
        });
        requestData(rootView);
        return rootView;
    }

    private void requestData(final View rootView) {
        final Context context = rootView.getContext();
        pDialog = new ProgressDialog(context);
        // Showing progress dialog before making http request
        pDialog.setMessage("Buscando...");
        pDialog.show();
        publicationList.clear();

       /* for(int i=0; i<10;i++){
        PublicationDetails publicacion = new PublicationDetails();
        publicacion.setPrecio("U$D99999999");
        publicacion.setThumbnailUrl("http://avatarbox.net/avatars/img19/zhou_ming_avatar_picture_49967.jpg");
        publicacion.setDireccion("Av Consejal Tribulato 187" + i );
        publicacion.setM2("1000 m2");
        publicacion.setCantAmbientes("12 amb");

        // adding publicacion to array
        publicationList.add(publicacion);
        }
        adapter.notifyDataSetChanged();
        hidePDialog();*/

        // Creating volley request obj
        JsonObjectRequest request = new JsonObjectRequest(url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        try {
                            JSONArray array = response.getJSONArray("payload");
                            // Parsing json
                            if(array.length() <= 0){
                                ((MainActivity)rootView.getContext()).displayView(new NoResultsFragment());
                            }   else {
                                for (int i = 0; i < array.length(); i++) {


                                    JSONObject obj = array.getJSONObject(i);

                                    PublicationDetails publicacion = new PublicationDetails();
                                    publicacion.setPrecio(obj.getString("currency")+ " " + obj.getString("price"));
                                    publicacion.setThumbnailUrl("http://avatarbox.net/avatars/img19/zhou_ming_avatar_picture_49967.jpg");
                                    publicacion.setDireccion(obj.getString("address"));
                                    publicacion.setM2((obj.getInt("size") + obj.getInt("coveredSize"))+" m2");
                                    publicacion.setCantAmbientes(obj.getJSONObject("environment").getString("name"));

                                       // adding publicacion to array
                                        publicationList.add(publicacion);



                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(), "Error al tratar de obtener los datos", Toast.LENGTH_LONG).show();
                ((MainActivity) context).displayViewForMenu(0);
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            requestData(this.getView());
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

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}
