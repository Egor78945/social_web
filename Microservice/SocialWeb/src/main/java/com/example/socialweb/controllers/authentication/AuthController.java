package com.example.socialweb.controllers.authentication;

import com.example.socialweb.annotations.customExceptionHandlers.UserControllersExceptionHandler;
import com.example.socialweb.configurations.security.jwt.JWTCore;
import com.example.socialweb.exceptions.WrongDataException;
import com.example.socialweb.models.requestModels.LoginModel;
import com.example.socialweb.models.requestModels.RegisterModel;
import com.example.socialweb.services.converters.UserConverter;
import com.example.socialweb.services.userServices.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@UserControllersExceptionHandler
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTCore jwtCore;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginModel loginModel) throws AuthenticationException {
        Authentication authentication;
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginModel.getEmail(), loginModel.getPassword()));
        redisTemplate.opsForValue().set("current", UserConverter.serializeUserToJsonString(userService.getUserByEmail(loginModel.getEmail())));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtCore.generateToken(authentication);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registration(@RequestBody RegisterModel registerModel) throws BadCredentialsException, WrongDataException {
        userService.registration(registerModel, passwordEncoder);
        return ResponseEntity.ok(registerModel);
    }
}
