package com.example.jwt.service;

import com.example.jwt.dto.JWTAuthenticationResponse;
import com.example.jwt.dto.SignInRequest;
import com.example.jwt.dto.SignUpRequest;
import com.example.jwt.dto.SignupResponse;
import com.example.jwt.entities.User;
import com.example.jwt.exceptions.InvalidUsernameException;
import com.example.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final JWTService jwtService;

    @Autowired
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Override
    public SignupResponse signup(SignUpRequest signUpRequest) {
        User user = new User();
        user.setFirstname(signUpRequest.getFirstname());
        user.setLastname(signUpRequest.getLastname());
        user.setEmail(signUpRequest.getEmail());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        User savedUser = userRepository.save(user);

        String jwtToken = jwtService.generateToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(Map.of(), savedUser);
        SignupResponse signupResponse = new SignupResponse();
        signupResponse.setUser(savedUser);
        signupResponse.setToken(jwtToken);
        signupResponse.setRefreshToken(refreshToken);

        return signupResponse;
    }

    @Override
    public JWTAuthenticationResponse signin(SignInRequest signInRequest) {
        try{
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(), signInRequest.getPassword()
                )
        );
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(signInRequest.getEmail());
        String username = userDetails.getUsername();
        String usernameFromToken = jwtService.extractUserName(signInRequest.getToken());

        if (!username.equals(usernameFromToken)) {
            throw new InvalidUsernameException("Invalid token. Username mismatch.");
        }
            String jwtToken = jwtService.generateToken(userDetails);
            Map<String, Object> extraClaims = new HashMap<>();
            String refreshToken = jwtService.generateRefreshToken(extraClaims, userDetails);


            JWTAuthenticationResponse response = new JWTAuthenticationResponse();
            response.setToken(jwtToken);
            response.setRefreshToken(refreshToken);
            response.setStatus("200 OK");
        return response;
        } catch (Exception e) {
            String errorMessage = "Error during signin: " + e.getMessage();
            System.err.println(errorMessage);
            logger.error(errorMessage, e);
            throw e;
        }
    }
}