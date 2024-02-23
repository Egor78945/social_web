package com.example.socialweb.controllers.userControllers;

import com.example.socialweb.configurations.security.jwt.JWTCore;
import com.example.socialweb.exceptions.WrongUserDataException;
import com.example.socialweb.models.requestModels.LoginModel;
import com.example.socialweb.models.requestModels.RegisterModel;
import com.example.socialweb.services.userServices.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
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
            log.info(e.getMessage());
            throw new BadCredentialsException("Unauthorized");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtCore.generateToken(authentication);
        log.info("Authentication token has been created.");
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registration(@RequestBody RegisterModel registerModel) throws BadCredentialsException {
        try {
            userService.registration(registerModel, passwordEncoder);
        } catch (WrongUserDataException e) {
            log.info(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok(registerModel);
    }
}
