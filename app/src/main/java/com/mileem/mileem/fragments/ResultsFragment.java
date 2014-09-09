package com.mileem.mileem.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mileem.mileem.AppController;
import com.mileem.mileem.R;
import com.mileem.mileem.activities.MainActivity;
import com.mileem.mileem.adapters.PublicationListAdapter;
import com.mileem.mileem.model.PublicationDetails;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Juan-Asus on 09/09/2014.
 */
public class ResultsFragment extends BaseFragment {

    public static final String TAG = ResultsFragment.class.getSimpleName();

    private static final String url = "http://www.omdbapi.com/?t=True%20Grit&y=1969";    //TODO - Cambiar a la url donde vienen los datos
    private ProgressDialog pDialog;
    private ArrayList<PublicationDetails> publicationList = new ArrayList<PublicationDetails>();
    private ListView listView;
    private PublicationListAdapter adapter;

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

        requestData(rootView);
        return rootView;
    }

    private void requestData(View rootView) {
        final Context context = rootView.getContext();
        pDialog = new ProgressDialog(context);
        // Showing progress dialog before making http request
        pDialog.setMessage("Buscando...");
        pDialog.show();

          for(int i=0; i<10;i++){
        PublicationDetails publicacion = new PublicationDetails();
        publicacion.setPrecio("$250000");
        publicacion.setThumbnailUrl("http://avatarbox.net/avatars/img19/zhou_ming_avatar_picture_49967.jpg");
        publicacion.setDireccion("Direccion" + i );
        publicacion.setM2("1000m2");
        publicacion.setCantAmbientes("1 ambiente");

        // adding publicacion to array
        publicationList.add(publicacion);
          }
        hidePDialog();
       /* // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                //TODO - Generar el objeto a partir de la response
                                PublicationDetails publicacion = new PublicationDetails();
                                publicacion.setPrecio(obj.getString("Title"));
                                publicacion.setThumbnailUrl(obj.getString("Poster"));
                                publicacion.setDireccion(String.valueOf(obj.get("Runtime")));
                                publicacion.setM2(String.valueOf(obj.getInt("Year")));
                                publicacion.setCantAmbientes(String.valueOf(obj.getInt("Released")));

                               // adding publicacion to array
                                publicationList.add(publicacion);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

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
        AppController.getInstance().addToRequestQueue(movieReq);*/
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            requestData(this.getView());
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
