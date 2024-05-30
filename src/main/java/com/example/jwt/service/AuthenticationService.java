package com.example.jwt.service;


import com.example.jwt.dto.*;
import com.example.jwt.entities.User;

public interface AuthenticationService {
    SignupResponse signup(SignUpRequest signUpRequest);
    JWTAuthenticationResponse signin(SignInRequest signInRequest);
}


