package org.daboo.starsapp.api;

import org.daboo.starsapp.model.dto.AuthRequest;
import org.daboo.starsapp.model.dto.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserLogin {
    @POST("/auth")
    Call<AuthResponse> postJson(@Body AuthRequest authRequest);
}
