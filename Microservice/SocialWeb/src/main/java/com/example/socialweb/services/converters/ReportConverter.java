package com.example.socialweb.services.converters;

import com.example.socialweb.models.entities.Report;
import com.example.socialweb.models.requestModels.ReportModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportConverter {
    public static ReportModel convertReportToReportModel(Report report) {
        return new ReportModel(report);
    }

    public static List<ReportModel> convertReportToReportModel(List<Report> reports) {
        return reports
                .stream()
                .map(ReportConverter::convertReportToReportModel)
                .collect(Collectors.toList());
    }
}
