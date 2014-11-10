package com.mileem.mileem.networking;

/**
 * Created by ramirodiaz on 07/09/14.
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.mileem.mileem.managers.DefinitionsManager;
import com.mileem.mileem.models.IdName;
import com.mileem.mileem.models.PublicationDetails;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class PublicationDetailsDataManager {
    public static abstract class PublicationsDetailsCallbackHandler extends CallbackHandler {
        public abstract void onComplete(PublicationDetails publication);
    }

    public void getDetails(final int publicationId, final PublicationsDetailsCallbackHandler callbackHandler) throws JSONException {
        RequestParams params = new RequestParams();
        params.put("id", publicationId);
        AsyncRestHttpClient.get("property", params, new MileenJsonResponseHandler(callbackHandler) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject payload = response.getJSONObject("payload");
                    Gson gson = new Gson();
                    PublicationDetails publication = gson.fromJson(payload.toString(), PublicationDetails.class);
                    publication.buildMultimediaData();
                    callbackHandler.onComplete(publication);
                } catch (Throwable e) {
                    callbackHandler.onFailure(new Error(e));
                }
            }
        });
    }
}