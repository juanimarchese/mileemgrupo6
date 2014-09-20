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
                    Type collectionType = new TypeToken<ArrayList<IdName>>(){}.getType();
                    Gson gson = new Gson();
                    DefinitionsManager dm = DefinitionsManager.getInstance();

                    Object neighborhoods = payload.get("neighborhoods");
                    ArrayList<IdName> neightboardsCollection = gson.fromJson(neighborhoods.toString(), collectionType);
                    dm.setNeightboardsCollection(neightboardsCollection);

                    Object environments = payload.get("environments");
                    ArrayList<IdName> environmentsCollection = gson.fromJson(environments.toString(), collectionType);
                    dm.setEnvironmentsTypesCollection(environmentsCollection);

                    Object propertyTypes = payload.get("propertyTypes");
                    ArrayList<IdName> propertyTypesCollection = gson.fromJson(propertyTypes.toString(), collectionType);
                    dm.setPropertyTypesCollection(propertyTypesCollection);

                    Object operationTypes = payload.get("operationTypes");
                    ArrayList<IdName> operationTypesCollection = gson.fromJson(operationTypes.toString(), collectionType);
                    dm.setOperationTypesCollection(operationTypesCollection);

                    Object dateRanges = payload.get("dateRanges");
                    ArrayList<IdName> dateRangesCollection = gson.fromJson(dateRanges.toString(), collectionType);
                    dm.setDateRangesCollections(dateRangesCollection);
                    callbackHandler.onComplete();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}