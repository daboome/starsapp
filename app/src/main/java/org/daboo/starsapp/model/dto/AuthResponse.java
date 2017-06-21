package org.daboo.starsapp.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;


@Data
public class AuthResponse {
    @SerializedName("token")
    @Expose
    String token;
}
