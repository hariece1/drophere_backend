package com.project.text_share.Controller;

import com.project.text_share.DTO.LoginRequest;
import com.project.text_share.Entity.MasterUser;
import com.project.text_share.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.text_share.Security.jwtUtil;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private jwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody MasterUser masterUser) {
        try {
            MasterUser savedMasterUser = userService.registerUser(masterUser);
            return ResponseEntity.ok(savedMasterUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            MasterUser masterUser = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
            String token = jwtUtil.generateToken(masterUser.getUsername());

            return ResponseEntity.ok(Map.of("token", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

}
