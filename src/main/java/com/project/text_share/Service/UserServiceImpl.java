package com.project.text_share.Service;

import com.project.text_share.Entity.MasterUser;
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

    public MasterUser registerUser(MasterUser masterUser) {
        // Check if username/email already exists
        if (userRepository.findByUsername(masterUser.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        if (userRepository.findByEmail(masterUser.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Set timestamps
        masterUser.setCreatedAt(LocalDateTime.now());
        masterUser.setLastSignIn(LocalDateTime.now());

        // Optional: Encode password
        masterUser.setPassword(passwordEncoder.encode(masterUser.getPassword()));

        return userRepository.save(masterUser);
    }
    @Override
    public MasterUser authenticate(String username, String password) {
        Optional<MasterUser> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        MasterUser masterUser = userOpt.get();
        if (!passwordEncoder.matches(password, masterUser.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        masterUser.setLastSignIn(LocalDateTime.now());
        return userRepository.save(masterUser);
    }

}
