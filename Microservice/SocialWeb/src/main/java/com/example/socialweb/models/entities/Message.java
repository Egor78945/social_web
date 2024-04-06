package com.example.socialweb.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Entity
@Table(name = "messages")
@Data
@EqualsAndHashCode
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private User sender;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private User recipient;
    @Column(name = "message")
    private String message;
    @Column(name = "send_date")
    private String sendDate;

    public Message() {
        this.sendDate = new Date(System.currentTimeMillis()).toString();
    }

    public Message(Builder builder) {
        this.sender = builder.sender;
        this.recipient = builder.recipient;
        this.message = builder.message;
        this.sendDate = new Date(System.currentTimeMillis()).toString();
    }

    public static class Builder {
        private User sender;
        private User recipient;
        private String message;
        private String sendDate;

        public Builder(User sender, User recipient) {
            this.sender = sender;
            this.recipient = recipient;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }
}
