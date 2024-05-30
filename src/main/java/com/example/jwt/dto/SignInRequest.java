package com.example.jwt.dto;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
    private String token;
}
