package com.example.socialweb.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Entity
@Table(name = "friendships")
@EqualsAndHashCode
@Data
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private User sender;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private User recipient;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "date")
    private String date;

    public Friendship() {
        date = new Date(System.currentTimeMillis()).toString();
    }

    public Friendship(User sender, User recipient) {
        this.sender = sender;
        this.recipient = recipient;
        status = false;
        date = new Date(System.currentTimeMillis()).toString();
    }
}
