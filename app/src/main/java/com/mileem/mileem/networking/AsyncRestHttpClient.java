package com.mileem.mileem.networking;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Map;

/**
 * Created by ramirodiaz on 07/09/14.
 */

public class AsyncRestHttpClient {

    private static final String HOST_URL = "http://107.20.253.197";
    private static final String BASE_URL_PROD = HOST_URL + "/api/";
    private static final String BASE_URL_DEV = HOST_URL + "/api/";
    private static final String BASE_URL = BASE_URL_PROD;

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if (BASE_URL == BASE_URL_PROD)
            client.setBasicAuth("mileen", "m1l33n");
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if (BASE_URL == BASE_URL_PROD)
            client.setBasicAuth("mileen", "m1l33n");
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static String getAbsoluteUrlRelativeToHost(String relativeUrl) {return HOST_URL + relativeUrl;}
}


