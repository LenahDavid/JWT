package com.example.jwt.service;

import com.example.jwt.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDetailsService userDetailsService();
    public User createUser(User user);
    public List<User> getAllUsers();
    public Optional<User> getUserById(Long id);
    public User updateUser(User user);
    public void deleteUser(Long id);

}