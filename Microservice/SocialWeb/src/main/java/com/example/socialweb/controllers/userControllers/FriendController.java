package com.example.socialweb.controllers.userControllers;

import com.example.socialweb.annotations.customExceptionHandlers.UserControllersExceptionHandler;
import com.example.socialweb.exceptions.RequestCancelledException;
import com.example.socialweb.exceptions.WrongDataException;
import com.example.socialweb.models.responseModels.ProfileModel;
import com.example.socialweb.services.userServices.FriendshipService;
import com.example.socialweb.services.userServices.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
@UserControllersExceptionHandler
@Slf4j
public class FriendController {
    private final UserService userService;
    private final FriendshipService friendshipService;

    // Send friend request to any user by id:
    @PostMapping("/request/{id}")
    public ResponseEntity<String> friendRequest(@PathVariable("id") Long id) throws RequestCancelledException {
        friendshipService.friendRequest(userService.getCurrentUser().getId(), id);
        return ResponseEntity.ok("Request has been sent.");
    }

    // Show all friend requests, sent to me:
    @GetMapping("/request")
    public ResponseEntity<?> friendRequests() {
        List<ProfileModel> profileModels = friendshipService.getAllFriendRequests(userService.getCurrentUser().getId(), false);
        if (!profileModels.isEmpty())
            return ResponseEntity.ok(profileModels);
        else
            return ResponseEntity.ok("You have not friend requests.");
    }

    // Confirm friend request, sent to me by id:
    @PostMapping("/request/confirm/{id}")
    public ResponseEntity<String> confirmFriendRequest(@PathVariable("id") Long id) throws RequestCancelledException {
        friendshipService.confirmRequest(userService.getCurrentUser().getId(), id);
        return ResponseEntity.ok("You confirmed this friend request.");
    }

    // Reject friend request, sent to me by id:
    @PostMapping("/request/reject/{id}")
    public ResponseEntity<String> rejectRequest(@PathVariable("id") Long id) throws RequestCancelledException {
            friendshipService.rejectRequest(id, userService.getCurrentUser().getId());
            return ResponseEntity.ok("You have rejected the request from this user.");
    }

    // Remove any user from my friend list by id:
    @PostMapping("/remove/{id}")
    public ResponseEntity<String> removeFriend(@PathVariable("id") Long id) throws RequestCancelledException {
            friendshipService.removeFriend(userService.getCurrentUser().getId(), id);
            return ResponseEntity.ok("The user is not longer your friend.");
    }

    // Show all my friends:
    @GetMapping
    public ResponseEntity<?> allFriends() {
        List<ProfileModel> profileModels = friendshipService.getAllFriendRequests(userService.getCurrentUser().getId(), true);
        if (!profileModels.isEmpty()) {
            return ResponseEntity.ok(profileModels);
        } else
            return ResponseEntity.ok("You have not friends.");
    }
}
