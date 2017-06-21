package org.daboo.starsapp.model.dto;

public class AuthRequest {
    final String username;
    final String password;

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
