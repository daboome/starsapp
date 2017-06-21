package org.daboo.starsapp.api;

import org.daboo.starsapp.model.dto.StarsCollection;
import org.daboo.starsapp.model.ui.Star;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StarsInteraction {
    @GET("/stars/all")
    Call<StarsCollection> getAllStars();

    @GET("/stars/delete/{id}")
    Call<StarsCollection> deleteStar(@Path("id") Long id);

    @POST("/stars")
    Call<StarsCollection> postStar(@Body Star star);
}
