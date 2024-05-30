package com.example.jwt.controllers;

import com.example.jwt.dto.JWTAuthenticationResponse;
import com.example.jwt.dto.SignInRequest;
import com.example.jwt.dto.SignUpRequest;
import com.example.jwt.dto.SignupResponse;
import com.example.jwt.entities.User;
import com.example.jwt.service.AuthenticationService;
import com.example.jwt.service.AuthenticationServiceImpl;
import com.example.jwt.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    JWTService jwtService;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/api/v1/auth/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignUpRequest signUpRequest){
        SignupResponse response = authenticationService.signup(signUpRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/v1/auth/signin")
    public ResponseEntity<JWTAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest, @RequestHeader("Authorization") String token) {
        try {

            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body(new JWTAuthenticationResponse("Invalid token"));
            }
            String jwtToken = token.substring(7);
            signInRequest.setToken(jwtToken);
            JWTAuthenticationResponse response = authenticationService.signin(signInRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            String errorMessage = "Error: " + e.getMessage();
            System.err.println(errorMessage);
            logger.error(errorMessage, e);
            return ResponseEntity.badRequest().body(new JWTAuthenticationResponse());
        }
    }
}