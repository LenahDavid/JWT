package com.example.jwt.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String username;
    private String token;
    private String refreshToken;
}