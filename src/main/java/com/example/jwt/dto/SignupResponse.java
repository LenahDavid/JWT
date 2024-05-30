package com.example.jwt.dto;

import com.example.jwt.entities.User;
import lombok.Data;

@Data
public class SignupResponse {
    private User user;
    private String token;
    private String refreshToken;
}
