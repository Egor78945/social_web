package com.example.socialweb.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Entity
@Table(name = "likes")
@Data
@EqualsAndHashCode
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private User liker;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private News news;
    @Column(name = "date")
    private String date;
    public Like(){
        date = new Date(System.currentTimeMillis()).toString();
    }
    public Like(User liker, News news){
        this.liker = liker;
        this.news = news;
        date = new Date(System.currentTimeMillis()).toString();
    }
}
