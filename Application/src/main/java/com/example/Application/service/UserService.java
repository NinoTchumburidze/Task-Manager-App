package com.example.Application.service;

import com.example.Application.model.User;
import com.example.Application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean registerUser(String username, String password) {
        // Check if the user already exists
        if (userRepository.findByUsername(username).isPresent()) {
            return false; // User already exists
        }

        // Save the user with plain text password
        User user = new User(username, password);
        userRepository.save(user);

        return true;
    }

    public boolean authenticateUser(String username, String password) {
        // Finding the user by username
        Optional<User> user = userRepository.findByUsername(username);  // Corrected line

        if (user.isPresent()) {
            // Checking if the password matches (plain text comparison)
            return user.get().getPassword().equals(password);
        }

        return false; // User not found
    }

}
