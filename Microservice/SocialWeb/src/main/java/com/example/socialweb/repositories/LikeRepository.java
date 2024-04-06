package com.example.socialweb.repositories;

import com.example.socialweb.models.entities.Like;
import com.example.socialweb.models.entities.News;
import com.example.socialweb.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findLikeByNews(News news);
    Boolean existsLikeByNewsAndLiker(News news, User liker);
    Like findLikeByLiker(User user);
    Like findLikeByNewsAndLiker(News news, User liker);
}
