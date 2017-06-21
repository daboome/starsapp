package org.daboo.starsapp.api;

import org.daboo.starsapp.model.dto.StarsCollection;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StarsInteraction {
    @GET("/stars/all")
    Call<StarsCollection> getAllStars();
}
