package com.example.socialweb.services.converters;

import com.example.socialweb.models.entities.Report;
import com.example.socialweb.models.requestModels.ReportModel;

import java.util.ArrayList;
import java.util.List;

public class ReportConverter {
    public static ReportModel convertReportToReportModel(Report report) {
        return new ReportModel(report);
    }

    public static List<ReportModel> convertReportToReportModel(List<Report> reports) {
        List<ReportModel> reportModels = new ArrayList<>();
        for (Report r : reports) {
            reportModels.add(convertReportToReportModel(r));
        }
        return reportModels;
    }
}
