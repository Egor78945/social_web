package com.example.socialweb.controllers.userControllers;

import com.example.socialweb.configurations.utils.Cache;
import com.example.socialweb.models.requestModels.ReportModel;
import com.example.socialweb.services.userServices.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
@Slf4j
public class ReportController {
    private final ReportService reportService;
    private final Cache cache;

    // Send report to user by report model where you can select user id to report:
    @PostMapping
    public ResponseEntity<String> reportUser(@RequestBody ReportModel reportModel){
        try {
            Long appealedId = reportModel.getUserId();
            Long applicantId = cache.getUser().getId() ;
            reportService.reportUser(applicantId, appealedId, reportModel);
            log.info("User with id " + applicantId + " reported user with id " + appealedId + ".");
            return ResponseEntity.ok("You reported this user.");
        } catch (RequestRejectedException e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
