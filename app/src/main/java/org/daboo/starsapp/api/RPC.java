package org.daboo.starsapp.api;

import android.util.Base64;
import android.util.Log;

import org.daboo.starsapp.app.GlobalApplication;
import org.daboo.starsapp.helper.Logger;
import org.daboo.starsapp.helper.SharedPrefUtils;
import org.daboo.starsapp.model.dto.AuthRequest;
import org.daboo.starsapp.model.dto.AuthResponse;
import org.daboo.starsapp.model.dto.StarsCollection;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.util.TextUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RPC {
    public static final String LOG_TAG = RPC.class.getSimpleName();

    public static final void requestAuthentic(final String username, final String password, final APIResponseListener listener) {
        UserLogin userLogin = ServiceGenerator.createService(UserLogin.class);
        Call<AuthResponse> responseCall = userLogin.postJson(new AuthRequest(username, password));
        responseCall.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    String token = response.body().getToken();
                    byte[] claimsEncoded = Base64.decode(token.split("\\.")[1].getBytes(), Base64.DEFAULT);
                    try {
                        String claimsString = new String(claimsEncoded, "UTF-8");
                        JSONObject claims = new JSONObject(claimsString);
                        String subject = claims.getString("sub");
                        SharedPrefUtils.saveToPrefs("username", subject);
                        SharedPrefUtils.saveToPrefs("token", token);
                        SharedPrefUtils.saveToPrefs("logged", true);
                        listener.onSuccess(subject);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        listener.onError(jObjError.getString("exception"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable throwable) {
//                parseError(statusCode, headers, throwable, errorResponse.toString(), null, listener);
                Log.d("error", call.toString());
            }
        });
    }

    public static final void getAllStars(Callback<StarsCollection> fragmentCallback) {
        String token = SharedPrefUtils.getFromPrefs("token", "");
        if (!TextUtils.isEmpty(token)) {
            StarsInteraction starsInteraction = ServiceGenerator.createService(StarsInteraction.class, token);
            Call<StarsCollection> allStars = starsInteraction.getAllStars();
            allStars.enqueue(fragmentCallback);
        }
    }

    private static void parseError(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, String errorResponse, final APIResponseListener listener) {
        listener.onError(throwable.getMessage());

        debugThrowable(LOG_TAG, throwable);
        if (errorResponse != null)
            debugResponse(LOG_TAG, rawJsonData);

    }

    protected static final void debugThrowable(String TAG, Throwable t) {
        if (!GlobalApplication.getInstance().isProductionEnvironment())
            return;

        if (t != null) {
            Logger.d(TAG, "AsyncHttpClient returned error");
            Logger.d(TAG, throwableToString(t));
        }
    }

    /**
     * @param t
     * @return
     */
    protected static String throwableToString(Throwable t) {
        if (t == null)
            return null;

        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    /**
     * @param TAG
     * @param response
     */
    protected static final void debugResponse(String TAG, String response) {
        if (GlobalApplication.getInstance().isProductionEnvironment())
            return;

        if (response != null) {
            Logger.d(TAG, "Response data:");
            Logger.d(TAG, response);
        }
    }

}