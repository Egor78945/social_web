package com.example.socialweb.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "ban_details")
@Data
public class BanDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private User banned;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private User admin;
    @Column(name = "reason")
    private String reason;
    @Column(name = "date")
    private String date;

    public BanDetails() {

    }

    public BanDetails(Builder builder) {
        this.banned = builder.banned;
        this.admin = builder.admin;
        this.reason = builder.reason;
        this.date = new Date(System.currentTimeMillis()).toString();
    }

    public static class Builder {
        private User banned;
        private User admin;
        private String reason;

        public Builder(User banned, User admin) {
            this.banned = banned;
            this.admin = admin;
        }

        public Builder setReason(String reason) {
            this.reason = reason;
            return this;
        }

        public BanDetails build() {
            return new BanDetails(this);
        }
    }
}
