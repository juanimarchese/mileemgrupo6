package com.mileem.mileem.networking;
import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by ramirodiaz on 11/11/14.
 */
public class PublicityDataManager {
    public static abstract class PublicityCallbackHandler extends CallbackHandler {
        public abstract void onComplete(String bannerUrl, String link);
    }
    public void getPublicity(final PublicityCallbackHandler callbackHandler) {
        AsyncRestHttpClient.get("getPublicity", null, new MileenJsonResponseHandler(callbackHandler) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject payload = response.getJSONObject("payload");
                    String bannerUrl = payload.getString("banner");
                    String webUrl = payload.getString("webUrl");
                    callbackHandler.onComplete(bannerUrl, webUrl);
                } catch (Throwable e) {
                    callbackHandler.onFailure(new Error(e));
                }
            }
        });
    }
}
