package com.mileem.mileem.networking;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mileem.mileem.models.PublicationDetails;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by ramirodiaz on 13/09/14.
 */
public class PublicationsDataManager {
    public void getPublicationsList(final CallbackHandler callback) throws JSONException {
        AsyncRestHttpClient.get("5412770f86a6451303a1c311", null, new MileenJsonResponseHandler(callback) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Object payload = response.get("payload");
                    Type collectionType = new TypeToken<Collection<PublicationDetails>>(){}.getType();
                    Gson gson = new Gson();
                    Collection<PublicationDetails> publications = gson.fromJson(payload.toString(), collectionType);
                    callback.onComplete(publications);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
