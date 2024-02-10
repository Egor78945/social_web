package com.example.socialweb.controllers;

import com.example.socialweb.configurations.utils.ServerUtils;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.responseModels.ProfileModel;
import com.example.socialweb.services.FriendshipService;
import com.example.socialweb.services.UserService;
import com.example.socialweb.services.converters.UserConverter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final FriendshipService friendshipService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileModel> profile(HttpServletRequest request) {
        User user = ServerUtils.getUserFromSession(request);
        return ResponseEntity.ok(new ProfileModel(user));
    }

    @GetMapping
    public ResponseEntity<List<ProfileModel>> allUsers() {
        return ResponseEntity.ok(UserConverter.convertUsersToProfileModels(userService.getAllUsers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileModel> userProfile(@PathVariable("id") Long id) {
        ProfileModel user = UserConverter.convertUserToProfileModel(userService.getUserById(id));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/friend/request/{id}")
    public ResponseEntity<?> friendRequest(@PathVariable("id") Long id, HttpServletRequest request) {
        User sender = userService.getUserById(ServerUtils.getUserFromSession(request).getId());
        User recipient = userService.getUserById(id);
        try {
            friendshipService.friendRequest(sender, recipient);
            return ResponseEntity.ok("Request has been sent.");
        } catch (RequestRejectedException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
