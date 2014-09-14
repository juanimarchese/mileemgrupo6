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
        AsyncRestHttpClient.get("", null, new MileenJsonResponseHandler(callbackHandler) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject payload = response.getJSONObject("payload");
                    Type collectionType = new TypeToken<ArrayList<IdName>>(){}.getType();
                    Gson gson = new Gson();

                    Object neighborhoods = payload.get("neighborhoods");
                    ArrayList<IdName> neightboardsCollection = gson.fromJson(neighborhoods.toString(), collectionType);
                    DefinitionsManager.getInstance().setNeightboardsCollection(neightboardsCollection);

                    Object environments = payload.get("neighborhoods");
                    ArrayList<IdName> environmentsCollection = gson.fromJson(environments.toString(), collectionType);
                    DefinitionsManager.getInstance().setEnvironmentsTypesCollection(environmentsCollection);

                    Object propertyTypes = payload.get("property_types");
                    ArrayList<IdName> propertyTypesCollection = gson.fromJson(propertyTypes.toString(), collectionType);
                    DefinitionsManager.getInstance().setPropertyTypesCollection(propertyTypesCollection);

                    Object operationTypes = payload.get("operation_types");
                    ArrayList<IdName> operationTypesCollection = gson.fromJson(operationTypes.toString(), collectionType);
                    DefinitionsManager.getInstance().setOperationTypesCollection(operationTypesCollection);
                    callbackHandler.onComplete();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}