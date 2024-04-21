package com.example.socialweb.controllers.userControllers;

import com.example.socialweb.configurations.utils.Cache;
import com.example.socialweb.models.responseModels.ProfileModel;
import com.example.socialweb.services.userServices.UserService;
import com.example.socialweb.services.converters.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Cache cache;

    // Show my profile:
    @GetMapping("/profile")
    public ResponseEntity<ProfileModel> profile() {
        return ResponseEntity.ok(new ProfileModel(cache.getUser()));
    }

    // Show all users:
    @GetMapping
    public ResponseEntity<List<ProfileModel>> allUsers() {
        return ResponseEntity.ok(UserConverter.convertUserToProfileModel(userService.getAllUsers()));
    }

    // Show individual user profile by id:
    @GetMapping("/{id}")
    public ResponseEntity<ProfileModel> userProfile(@PathVariable("id") Long id) {
        ProfileModel user = UserConverter.convertUserToProfileModel(userService.getUserById(id));
        return ResponseEntity.ok(user);
    }
}
