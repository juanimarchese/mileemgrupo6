package com.mileem.mileem.networking;

/**
 * Created by ramirodiaz on 07/09/14.
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mileem.mileem.managers.DefinitionsManager;
import com.mileem.mileem.models.IdName;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class DefinitionsDataManager {
    public static abstract class DefinitionsCallbackHandler extends CallbackHandler {
        public abstract void onComplete();
    }

    public void getDefinitions(final DefinitionsCallbackHandler callbackHandler) throws JSONException {
        AsyncRestHttpClient.get("definitions", null, new MileenJsonResponseHandler(callbackHandler) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject payload = response.getJSONObject("payload");
                    Gson gson = new Gson();
                    DefinitionsManager df = gson.fromJson(payload.toString(), DefinitionsManager.class);
                    DefinitionsManager.setInstance(df);
                    callbackHandler.onComplete();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}