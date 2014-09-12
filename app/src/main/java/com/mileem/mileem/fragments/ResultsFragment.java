package com.mileem.mileem.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.mileem.mileem.R;
import com.mileem.mileem.adapters.PublicationListAdapter;
import com.mileem.mileem.models.PublicationDetails;

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

    private void requestData(View rootView) {
        final Context context = rootView.getContext();
        pDialog = new ProgressDialog(context);
        // Showing progress dialog before making http request
        pDialog.setMessage("Buscando...");
        pDialog.show();
        publicationList.clear();

        for(int i=0; i<10;i++){
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
