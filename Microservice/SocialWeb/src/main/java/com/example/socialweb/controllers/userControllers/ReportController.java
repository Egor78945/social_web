package com.example.socialweb.controllers.userControllers;

import com.example.socialweb.exceptions.WrongDataException;
import com.example.socialweb.models.requestModels.ReportModel;
import com.example.socialweb.services.userServices.ReportService;
import com.example.socialweb.services.userServices.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
@Slf4j
public class ReportController {
    private final ReportService reportService;
    private final UserService userService;

    // Send report to user by report model where you can select user id to report:
    @PostMapping
    public ResponseEntity<String> reportUser(@RequestBody ReportModel reportModel) {
        try {
            Long applicantId = userService.getCurrentUser().getId();
            reportService.reportUser(applicantId, reportModel.getUserId(), reportModel);
            log.info("User with id " + applicantId + " reported user with id " + reportModel.getUserId() + ".");
            return ResponseEntity.ok("You reported this user.");
        } catch (WrongDataException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
