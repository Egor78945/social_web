package com.example.socialweb.controllers.userControllers;

import com.example.socialweb.exceptions.WrongDataException;
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
public class UserController {
    private final UserService userService;
    private final RedisTemplate<String, Object> redisTemplate;

    // Show my profile:
    @GetMapping("/profile")
    public ResponseEntity<ProfileModel> profile() {
        return ResponseEntity.ok(new ProfileModel(UserConverter
                .serializeJsonStringToUser((String) redisTemplate
                        .opsForValue()
                        .get("current"))));
    }

    // Show all users:
    @GetMapping
    public ResponseEntity<?> allUsers() {
        try {
            return ResponseEntity.ok(UserConverter.convertUserToProfileModel(userService.getAllUsers()));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    // Show individual user profile by id:
    @GetMapping("/{id}")
    public ResponseEntity<?> userProfile(@PathVariable("id") Long id) {
        ProfileModel user = null;
        try {
            user = UserConverter.convertUserToProfileModel(userService.getUserById(id));
        } catch (WrongDataException e) {
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok(user);
    }
}
