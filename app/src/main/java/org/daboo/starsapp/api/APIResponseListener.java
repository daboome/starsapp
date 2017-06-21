package org.daboo.starsapp.api;


public interface APIResponseListener {
    void onError(String message);
    void onSuccess(Object results);
}
