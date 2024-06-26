package com.example.socialweb.controllers.adminControllers;

import com.example.socialweb.annotations.customExceptionHandlers.UserControllersExceptionHandler;
import com.example.socialweb.exceptions.RequestCancelledException;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.BanModel;
import com.example.socialweb.services.adminServices.AdminService;
import com.example.socialweb.services.userServices.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@UserControllersExceptionHandler
@Slf4j
public class AdminController {
    private final AdminService adminService;
    private final UserService userService;

    // Ban user by ban model, where you can select user id to ban:
    @PostMapping("/ban")
    public ResponseEntity<String> banUser(@RequestBody BanModel banModel) throws RequestCancelledException {
        User admin = userService.getCurrentUser();
        adminService.banUser(banModel, admin);
        return ResponseEntity.ok("The user is successfully banned.");
    }

    // Unban user by id:
    @PostMapping("/unban/{id}")
    public ResponseEntity<String> unbanUser(@PathVariable("id") Long id) throws RequestCancelledException {
        adminService.unbanUser(id);
        return ResponseEntity.ok("The user successfully unbanned.");
    }
}
