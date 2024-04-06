package com.example.socialweb.models.entities;

import com.example.socialweb.enums.NewsTheme;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Entity
@Table(name = "news")
@Data
@EqualsAndHashCode
public class News{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private User publisher;
    @Column(name = "description")
    private String description;
    @Column(name = "date")
    private String date;
    @Column(name = "theme")
    private NewsTheme theme;
    @Column(name = "like_count")
    private Long likeCount;

    public News() {
        date = new Date(System.currentTimeMillis()).toString();
        likeCount = 0L;
    }

    public News(Builder builder) {
        publisher = builder.publisher;
        description = builder.description;
        date = new Date(System.currentTimeMillis()).toString();
        theme = builder.theme;
        likeCount = 0L;
    }

    public static class Builder {
        private User publisher;
        private String description;
        private NewsTheme theme;

        public Builder(User publisher, String description) {
            this.publisher = publisher;
            this.description = description;
        }

        public Builder setTheme(NewsTheme theme) {
            this.theme = theme;
            return this;
        }

        public News build() {
            return new News(this);
        }
    }
}
