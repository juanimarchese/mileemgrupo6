package com.mileem.mileem.networking;

/**
 * Created by ramirodiaz on 26/10/14.
 */

import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class ReportDataManager {
    public static abstract class ReportDataManagerCallbackHandler extends CallbackHandler {
        public abstract void onComplete(String neighborhoodName, String graphUrl);
        public abstract void onComplete(String neighborhoodName, String currency,String price);
    }


    public void getReportAveragePricePerSquareMeterNeighborhood(final int neighborhoodId, final String currency, final int width, final int height, final ReportDataManagerCallbackHandler callbackHandler) throws JSONException {
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
                    String neighborhoodName = payload.getJSONObject("neighborhood").getString("name");
                    String price = payload.getJSONObject("neighborhood").getString("priceByM2");
                    callbackHandler.onComplete(neighborhoodName, currency,price);
                } catch (Throwable e) {
                    callbackHandler.onFailure(new Error(e));
                }
            }
        });
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