package com.mileem.mileem.networking;

import com.mileem.mileem.models.PublicationDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan-Asus on 13/09/2014.
 */
public class MockDataManager {
    private static final String url = "http://10.0.2.2/api/property-search";

    static public List<PublicationDetails> getPublicationsByPage(int pageNumber){
        List publicationList = new ArrayList<PublicationDetails>();
        for(int i=0+((pageNumber)*5); i<5*(pageNumber+1);i++){
            PublicationDetails publicacion = new PublicationDetails();
            /*publicacion.setPrecio("U$D99999999");
            publicacion.setThumbnailUrl("http://avatarbox.net/avatars/img19/zhou_ming_avatar_picture_49967.jpg");
            publicacion.setDireccion("Av Consejal Tribulato " + i );
            publicacion.setM2("1000 m2");
            publicacion.setCantAmbientes("12 amb");*/

            // adding publicacion to array

            publicationList.add(publicacion);
        }

                /* // Creating volley request obj
        JsonObjectRequest request = new JsonObjectRequest(url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());


                        try {
                            JSONArray array = response.getJSONArray("payload");
                            // Parsing json
                            if(array.length() <= 0){
                                ((MainActivity)context).displayView(new NoResultsFragment());
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
                        notifySuccesDataRequest();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(), "Error al tratar de obtener los datos", Toast.LENGTH_LONG).show();
                ((MainActivity) context).displayViewForMenu(0);
                notifyFailDataRequest();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(request);*/
        return publicationList;
    }
}
