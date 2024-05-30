package com.example.jwt.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping("/api/v1/auth/user")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to home!");
    }
}