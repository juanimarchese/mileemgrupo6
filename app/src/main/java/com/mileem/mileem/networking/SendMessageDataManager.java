package com.mileem.mileem.networking;

import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ramirodiaz on 10/10/14.
 */
public class SendMessageDataManager {
    public static abstract class SendMessageDataManagerCallbackHandler extends CallbackHandler {
        public abstract void onComplete();
    }

    public void sendMessage(final int publicationId, String email, String telephone, String contactTimeInfo, String message, final SendMessageDataManagerCallbackHandler callbackHandler) throws JSONException {
        RequestParams params = new RequestParams();
        params.put("propertyId", publicationId);
        params.put("name", email );
        params.put("email", email);
        params.put("telephone", telephone != null && telephone.length() > 0 ? telephone : "Teléfono no especificado");
        params.put("callAt", contactTimeInfo != null && contactTimeInfo.length() > 0 ? contactTimeInfo : "Horario de respuesta no especificado");
        params.put("message", message);
        AsyncRestHttpClient.get("send-message", params, new MileenJsonResponseHandler(callbackHandler) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    int error = response.getInt("error");
                    if (error == 0) {
                        callbackHandler.onComplete();
                    } else {
                        String message = response.getString("message");
                        callbackHandler.onFailure(new Error(message));
                    }
                } catch (Throwable e) {
                    callbackHandler.onFailure(new Error(e));
                }
            }
        });
    }
}