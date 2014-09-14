package com.mileem.mileem.networking;

/**
 * Created by ramirodiaz on 07/09/14.
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mileem.mileem.managers.DefinitionsManager;
import com.mileem.mileem.models.PublicationDetails;
import com.mileem.mileem.models.IdName;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Collection;

public class DefinitiosDataManager {

    public void getDefinitions(final CallbackHandler callback) throws JSONException {
        AsyncRestHttpClient.get("5412822386a6451704a1c314", null, new MileenJsonResponseHandler(callback) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject payload = response.getJSONObject("payload");
                    Type collectionType = new TypeToken<Collection<IdName>>(){}.getType();
                    Gson gson = new Gson();

                    Object neighborhoods = payload.get("neighborhoods");
                    Collection<IdName> neightboardsCollection = gson.fromJson(neighborhoods.toString(), collectionType);
                    DefinitionsManager.getInstance().setNeightboardsCollection(neightboardsCollection);

                    Object environments = payload.get("neighborhoods");
                    Collection<IdName> environmentsCollection = gson.fromJson(environments.toString(), collectionType);
                    DefinitionsManager.getInstance().setEnvironmentsTypesCollection(environmentsCollection);

                    Object propertyTypes = payload.get("property_types");
                    Collection<IdName> propertyTypesCollection = gson.fromJson(propertyTypes.toString(), collectionType);
                    DefinitionsManager.getInstance().setPropertyTypesCollection(propertyTypesCollection);

                    Object operationTypes = payload.get("operation_types");
                    Collection<IdName> operationTypesCollection = gson.fromJson(operationTypes.toString(), collectionType);
                    DefinitionsManager.getInstance().setOperationTypesCollection(operationTypesCollection);

                    callback.onComplete(null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}