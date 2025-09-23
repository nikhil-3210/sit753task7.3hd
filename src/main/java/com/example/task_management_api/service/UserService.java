package com.example.task_management_api.service;

import com.example.task_management_api.model.User;
import com.example.task_management_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String password) {
        String encoded = passwordEncoder.encode(password);
        User user = new User(username, encoded);
        return userRepository.save(user);
    }

    public Optional<User> authenticate(String username, String rawPassword) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent() &&
                passwordEncoder.matches(rawPassword, optionalUser.get().getPassword())) {
            return optionalUser;
        }
        return Optional.empty();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}