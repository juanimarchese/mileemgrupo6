package com.mileem.mileem.networking;

/**
 * Created by ramirodiaz on 26/10/14.
 */

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.mileem.mileem.models.PublicationDetails;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class ReportDataManager {
    public static abstract class ReportDataManagerCallbackHandler extends CallbackHandler {
        public abstract void onComplete(String neighborhoodName, String graphUrl);
    }
    public void getReportAveragePricePerSquareMeterOfSurroundingNeighborhood(final int neighborhoodId, final String currency, final int width, final int height, final ReportDataManagerCallbackHandler callbackHandler) throws JSONException {
        RequestParams params = new RequestParams();
        params.put("neighborhood", neighborhoodId);
        params.put("width", width);
        params.put("height", height);
        params.put("currency", currency);
        AsyncRestHttpClient.get("average-price-by-neighborhood", params, new MileenJsonResponseHandler(callbackHandler) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject payload = response.getJSONObject("payload");
                    String relativeUrl = payload.getString("url");
                    String neighborhoodName = payload.getJSONObject("neighborhood").getString("name");
                    String graphUrl = AsyncRestHttpClient.getAbsoluteUrlRelativeToHost(relativeUrl);
                    callbackHandler.onComplete(neighborhoodName, graphUrl);
                } catch (Throwable e) {
                    callbackHandler.onFailure(new Error(e));
                }
            }
        });
    }

    public void getReportAveragePropertyByEnviromentNeighborhood(final int neighborhoodId, final int width, final int height, final ReportDataManagerCallbackHandler callbackHandler) throws JSONException {
        RequestParams params = new RequestParams();
        params.put("neighborhood", neighborhoodId);
        params.put("width", width);
        params.put("height", height);
        AsyncRestHttpClient.get("average-property-by-environment", params, new MileenJsonResponseHandler(callbackHandler) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject payload = response.getJSONObject("payload");
                    String relativeUrl = payload.getString("url");
                    String neighborhoodName = payload.getJSONObject("neighborhood").getString("name");
                    String graphUrl = AsyncRestHttpClient.getAbsoluteUrlRelativeToHost(relativeUrl);
                    callbackHandler.onComplete(neighborhoodName, graphUrl);
                } catch (Throwable e) {
                    callbackHandler.onFailure(new Error(e));
                }
            }
        });
    }
}