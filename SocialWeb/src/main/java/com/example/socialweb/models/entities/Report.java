package com.example.socialweb.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Entity
@Table(name = "report")
@Data
@EqualsAndHashCode
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private User applicant;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private User appealed;
    @Column(name = "reason")
    private String reason;
    @Column(name = "report_date")
    private String report_date;

    public Report() {

    }

    public Report(Builder b) {
        this.applicant = b.applicant;
        this.appealed = b.appealed;
        this.reason = b.reason;
        this.report_date = new Date(System.currentTimeMillis()).toString();
    }

    public static class Builder {
        private User applicant;
        private User appealed;
        private String reason;

        public Builder(User applicant, User appealed) {
            this.applicant = applicant;
            this.appealed = appealed;
        }

        public Builder setReason(String reason) {
            this.reason = reason;
            return this;
        }

        public Report build() {
            return new Report(this);
        }
    }
}
