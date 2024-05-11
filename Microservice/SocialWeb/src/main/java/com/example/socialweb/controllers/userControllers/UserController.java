package com.example.socialweb.controllers.userControllers;

import com.example.socialweb.annotations.customExceptionHandlers.UserControllersExceptionHandler;
import com.example.socialweb.exceptions.RequestCancelledException;
import com.example.socialweb.models.responseModels.ProfileModel;
import com.example.socialweb.services.userServices.UserService;
import com.example.socialweb.services.converters.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@UserControllersExceptionHandler
public class UserController {
    private final UserService userService;

    // Show my profile:
    @GetMapping("/profile")
    public ResponseEntity<ProfileModel> profile() {
        return ResponseEntity.ok(UserConverter.convertUserToProfileModel(userService.getCurrentUser()));
    }

    // Show all users:
    @GetMapping
    public ResponseEntity<?> allUsers() throws RequestCancelledException {
            return ResponseEntity.ok(UserConverter.convertUserToProfileModel(userService.getAllUsers()));
    }

    // Show individual user profile by id:
    @GetMapping("/{id}")
    public ResponseEntity<?> userProfile(@PathVariable("id") Long id) throws RequestCancelledException {
        ProfileModel user = UserConverter.convertUserToProfileModel(userService.getUserById(id));
        return ResponseEntity.ok(user);
    }
}
