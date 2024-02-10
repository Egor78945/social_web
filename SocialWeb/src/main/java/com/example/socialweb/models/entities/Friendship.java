package com.example.socialweb.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "friendship")
@EqualsAndHashCode
@Data
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User sender;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User recipient;
    @Column(name = "status")
    private Boolean status;

    public Friendship() {

    }

    public Friendship(User sender, User recipient) {
        this.sender = sender;
        this.recipient = recipient;
        status = false;
    }
}
