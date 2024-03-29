package com.example.socialweb.controllers;

import com.example.socialweb.configurations.utils.ServerUtils;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.ReportModel;
import com.example.socialweb.services.ReportService;
import com.example.socialweb.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> reportUser(@RequestBody ReportModel reportModel, HttpServletRequest request){
        try {
            User appealed = userService.getUserById(reportModel.getUserId());
            User applicant = ServerUtils.getUserFromSession(request);
            reportService.reportUser(applicant, appealed, reportModel);
            return ResponseEntity.ok("You reported this user.");
        } catch (RequestRejectedException e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
