package com.example.socialweb.services.validation;

import com.example.socialweb.models.requestModels.ReportModel;

public class ReportValidation {
    public static boolean isValidReport(ReportModel reportModel){
        return isValidReason(reportModel.getReason());
    }
    public static boolean isValidReason(String reason){
        return reason.length() > 20 && reason.length() < 150;
    }
}
