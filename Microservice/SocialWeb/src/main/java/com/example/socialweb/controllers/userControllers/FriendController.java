package com.example.socialweb.controllers.userControllers;

import com.example.socialweb.configurations.utils.Cache;
import com.example.socialweb.configurations.utils.ServerUtils;
import com.example.socialweb.models.entities.Friendship;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.responseModels.ProfileModel;
import com.example.socialweb.services.userServices.FriendshipService;
import com.example.socialweb.services.userServices.UserService;
import com.example.socialweb.services.converters.FriendshipConverter;
import com.example.socialweb.services.converters.UserConverter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
@Slf4j
public class FriendController {
    private final UserService userService;
    private final FriendshipService friendshipService;
    private final Cache cache;

    @PostMapping("/request/{id}")
    public ResponseEntity<String> friendRequest(@PathVariable("id") Long id) {
        User sender = cache.getUser();
        User recipient = userService.getUserById(id);
        try {
            friendshipService.friendRequest(sender, recipient);
            log.info("Request to user with id " + recipient.getId() + " has been sent.");
            return ResponseEntity.ok("Request has been sent.");
        } catch (RequestRejectedException e) {
            log.info(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/request")
    public ResponseEntity<?> friendRequests() {
        List<ProfileModel> profileModels = friendshipService.getAllFriendRequests(cache.getUser(), false);
        if (!profileModels.isEmpty())
            return ResponseEntity.ok(profileModels);
        else
            return ResponseEntity.ok("You have not friend requests.");
    }

    @PostMapping("/request/confirm/{id}")
    public ResponseEntity<String> confirmFriendRequest(@PathVariable("id") Long id) {
        try {
            User recipient = cache.getUser();
            User sender = userService.getUserById(id);
            friendshipService.confirmRequest(sender, recipient);
            log.info("Friendship request from user " + sender.getId() + " has been confirmed.");
            return ResponseEntity.ok("You confirmed this friend request.");
        } catch (RequestRejectedException e) {
            log.info(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PostMapping("/request/reject/{id}")
    public ResponseEntity<String> rejectRequest(@PathVariable("id") Long id) {
        try {
            User sender = userService.getUserById(id);
            User recipient = cache.getUser();
            friendshipService.rejectRequest(sender, recipient);
            log.info("Friendship request from user with id " + sender.getId() + " has been rejected.");
            return ResponseEntity.ok("You have rejected the request from this user.");
        } catch (RequestRejectedException e) {
            log.info(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<String> removeFriend(@PathVariable("id") Long id) {
        try {
            User sender = cache.getUser();
            User recipient = userService.getUserById(id);
            friendshipService.removeFriend(sender, recipient);
            log.info("User with id " + recipient.getId() + " is not longer your friend.");
            return ResponseEntity.ok("The user is not longer your friend.");
        } catch (RequestRejectedException e) {
            log.info(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> allFriends() {
        List<ProfileModel> profileModels = friendshipService.getAllFriendRequests(cache.getUser(), true);
        if (!profileModels.isEmpty()) {
            return ResponseEntity.ok(profileModels);
        } else
            return ResponseEntity.ok("You have not friends.");
    }
}
