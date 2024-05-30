package com.example.jwt.dto;

import lombok.Data;

@Data
public class JWTAuthenticationResponse {
    private String token;
    private String refreshToken;
    private String status;

    public JWTAuthenticationResponse() {
    }

    // Constructor with parameters
    public JWTAuthenticationResponse(String token, String refreshToken, String status) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.status = status;
    }

    public JWTAuthenticationResponse(String invalidToken) {
        this.status = invalidToken;
    }
}
