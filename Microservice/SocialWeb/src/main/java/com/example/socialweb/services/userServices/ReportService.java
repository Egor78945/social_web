package com.example.socialweb.services.userServices;

import com.example.socialweb.models.entities.Report;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.ReportModel;
import com.example.socialweb.repositories.ReportRepository;
import com.example.socialweb.repositories.UserRepository;
import com.example.socialweb.services.validation.ReportValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    @Transactional
    public void reportUser(Long applicantId, Long appealedId, ReportModel reportModel) {
        if (ReportValidation.isValidReport(reportModel) && !applicantId.equals(appealedId)) {
            User applicant = userRepository.findUserById(applicantId);
            User appealed = userRepository.findUserById(appealedId);
            Report report = new Report.Builder(applicant, appealed)
                    .setReason(reportModel.getReason())
                    .build();
            reportRepository.save(report);
        } else
            throw new RequestRejectedException("Invalid report reason or applicant id equals appealed id.");
    }
}
