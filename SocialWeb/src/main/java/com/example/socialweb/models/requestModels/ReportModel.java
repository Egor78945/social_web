package com.example.socialweb.models.requestModels;

import com.example.socialweb.models.entities.Report;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class ReportModel {
    private Long userId;
    private String reason;
    public ReportModel(){

    }
    public ReportModel(String reason){
        this.reason = reason;
    }
    public ReportModel(Report report){
        userId = report.getAppealed().getId();
        reason = report.getReason();
    }
}
