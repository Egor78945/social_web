package com.example.socialweb.controllers;

import com.example.socialweb.configurations.utils.ServerUtils;
import com.example.socialweb.models.entities.Friendship;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.responseModels.ProfileModel;
import com.example.socialweb.services.FriendshipService;
import com.example.socialweb.services.UserService;
import com.example.socialweb.services.converters.FriendshipConverter;
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
    public ResponseEntity<String> friendRequest(@PathVariable("id") Long id, HttpServletRequest request) {
        User sender = userService.getUserById(ServerUtils.getUserFromSession(request).getId());
        User recipient = userService.getUserById(id);
        try {
            friendshipService.friendRequest(sender, recipient);
            return ResponseEntity.ok("Request has been sent.");
        } catch (RequestRejectedException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/friend/request")
    public ResponseEntity<List<ProfileModel>> friendRequests(HttpServletRequest request) {
        List<Friendship> friendships = friendshipService.allByRecipientAndStatus(ServerUtils.getUserFromSession(request), false);
        List<User> senders = FriendshipConverter.sendersToUsers(friendships);
        List<ProfileModel> profileModels = UserConverter.convertUsersToProfileModels(senders);
        return ResponseEntity.ok(profileModels);
    }

    @PostMapping("/friend/request/confirm/{id}")
    public ResponseEntity<String> confirmFriendRequest(@PathVariable("id") Long id, HttpServletRequest request) {
        try {
            User sender = userService.getUserById(id);
            User recipient = ServerUtils.getUserFromSession(request);
            friendshipService.confirmRequest(sender, recipient);
            return ResponseEntity.ok("You confirmed this friend request.");
        } catch (RequestRejectedException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PostMapping("/friend/request/reject/{id}")
    public ResponseEntity<String> rejectRequest(@PathVariable("id") Long id, HttpServletRequest request) {
        try {
            User sender = userService.getUserById(id);
            User recipient = ServerUtils.getUserFromSession(request);
            friendshipService.rejectRequest(sender, recipient);
            return ResponseEntity.ok("You have rejected the request from this user.");
        } catch (RequestRejectedException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PostMapping("/friend/remove/{id}")
    public ResponseEntity<String> removeFriend(@PathVariable("id") Long id, HttpServletRequest request) {
        try {
            User sender = ServerUtils.getUserFromSession(request);
            User recipient = userService.getUserById(id);
            friendshipService.removeFriend(sender, recipient);
            return ResponseEntity.ok("The user is not longer your friend.");
        } catch (RequestRejectedException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/friend")
    public ResponseEntity<?> allFriends(HttpServletRequest request) {
        try {
            List<Friendship> friendships = friendshipService.allByRecipientAndStatus(ServerUtils.getUserFromSession(request), true);
            List<User> users = FriendshipConverter.friendshipToUserByUser(friendships, ServerUtils.getUserFromSession(request));
            List<ProfileModel> profileModels = UserConverter.convertUsersToProfileModels(users);
            return ResponseEntity.ok(profileModels);
        } catch (RequestRejectedException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
