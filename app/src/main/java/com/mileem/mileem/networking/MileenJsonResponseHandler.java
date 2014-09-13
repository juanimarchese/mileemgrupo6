package com.mileem.mileem.networking;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ramirodiaz on 11/09/14.
 */
public class MileenJsonResponseHandler extends JsonHttpResponseHandler {
    final CallbackHandler callback;
    static String MileenJsonResponseHandlerTag = "MileenJsonResponseHandler";
    public MileenJsonResponseHandler(CallbackHandler callback) {
        this.callback = callback;
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        Log.v(MileenJsonResponseHandlerTag, "Status Code:" + statusCode);
        callback.onFailure(new Error(errorResponse.toString()));
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        Log.v(MileenJsonResponseHandlerTag, "Status Code :" + statusCode);
        callback.onFailure(new Error(responseString));
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        Log.v(MileenJsonResponseHandlerTag,"Status Code :" + statusCode);
        callback.onFailure(new Error(errorResponse.toString()));
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        Log.v(MileenJsonResponseHandlerTag,"Status Code: " + statusCode + " Response: " + response);
    }
}
