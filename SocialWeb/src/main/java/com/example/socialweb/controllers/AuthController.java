package com.example.socialweb.controllers;

import com.example.socialweb.configurations.security.jwt.JWTCore;
import com.example.socialweb.exceptions.WrongUserDataException;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.LoginModel;
import com.example.socialweb.models.requestModels.RegisterModel;
import com.example.socialweb.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTCore jwtCore;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginModel loginModel) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginModel.getEmail(), loginModel.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Unauthorized");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtCore.generateToken(authentication);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registration(@RequestBody RegisterModel registerModel) throws BadCredentialsException {
        try {
            userService.registration(registerModel, passwordEncoder);
        } catch (WrongUserDataException e) {
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok(registerModel);
    }
}
