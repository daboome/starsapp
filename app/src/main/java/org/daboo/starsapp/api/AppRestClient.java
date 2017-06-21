package org.daboo.starsapp.api;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;

import org.daboo.starsapp.app.AppConfig;
import org.daboo.starsapp.app.GlobalApplication;
import org.daboo.starsapp.helper.Logger;

import java.io.IOException;

import cz.msebera.android.httpclient.HttpEntity;

public class AppRestClient {
    public static final String TAG = AppRestClient.class.getSimpleName();

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static AsyncHttpClient synClient = new SyncHttpClient();

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String HEADER_APP_ID = "AppId";
    private static final String HEADER_BASIC = "basic";

    public static void initAsyncHttpClient() {
        client.setBasicAuth("username", "password/token");
    }

    public static void cancelAllRequests() {
        client.cancelAllRequests(true);
    }

    public static void cancelRequestsByTAG(String TAG) {
        Logger.d("cancelRequestsByTAG", TAG);
        client.cancelRequestsByTAG(TAG, true);
    }

    public static void cancelAllRequestsSyncHttpClient() {
        synClient.cancelAllRequests(true);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler).setTag(url);
    }

    public static void download(String url, FileAsyncHttpResponseHandler responseHandler) {
        synClient.get(GlobalApplication.getAppContext(), url, responseHandler).setTag(url);
    }

    public static void post(String url, HttpEntity entity, AsyncHttpResponseHandler responseHandler) {
        client.post(GlobalApplication.getAppContext(), getAbsoluteUrl(url), entity, "application/json", responseHandler);
    }

    public static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.put(GlobalApplication.getAppContext(), getAbsoluteUrl(url), paramsToEntity(params, responseHandler), null, responseHandler).setTag(url);
    }

    private static HttpEntity paramsToEntity(RequestParams params, ResponseHandlerInterface responseHandler) {
        HttpEntity entity = null;

        try {
            if (params != null) {
                entity = params.getEntity(responseHandler);
            }
        } catch (IOException e) {
            if (responseHandler != null) {
                responseHandler.sendFailureMessage(0, null, null, e);
            } else {
                e.printStackTrace();
            }
        }

        return entity;
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        String url = AppConfig.BASE_URL + relativeUrl;
        Logger.d(TAG, url);
        return url;
    }

    public static void addHeader(String header, String value) {
        client.addHeader(header, value);
    }

    public static void removeHeader(String header) {
        client.removeHeader(header);
    }
}
