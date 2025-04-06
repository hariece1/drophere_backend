package com.project.text_share.Service;

import com.project.text_share.Entity.User;
import com.project.text_share.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        // Check if username/email already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Set timestamps
        user.setCreatedAt(LocalDateTime.now());
        user.setLastSignIn(LocalDateTime.now());

        // Optional: Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
    @Override
    public User authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        user.setLastSignIn(LocalDateTime.now());
        return userRepository.save(user);
    }

}
