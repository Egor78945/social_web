package com.example.socialweb.controllers.adminControllers;

import com.example.socialweb.configurations.utils.Cache;
import com.example.socialweb.configurations.utils.ServerUtils;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.BanModel;
import com.example.socialweb.services.adminServices.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService adminService;
    private final Cache cache;

    // Ban user by ban model, where you can select user id to ban:
    @PostMapping("/ban")
    public ResponseEntity<String> banUser(@RequestBody BanModel banModel) {
        try {
            User admin = cache.getUser();
            adminService.banUser(banModel, admin);
            log.info("The user is successfully banned.");
            return ResponseEntity.ok("The user is successfully banned.");
        } catch (RequestRejectedException e) {
            log.info(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }

    // Unban user by id:
    @PostMapping("/unban/{id}")
    public ResponseEntity<String> unbanUser(@PathVariable("id") Long id) {
        try {
            adminService.unbanUser(id);
            log.info("The user successfully unbanned.");
            return ResponseEntity.ok("The user successfully unbanned.");
        } catch (RequestRejectedException e) {
            log.info(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
