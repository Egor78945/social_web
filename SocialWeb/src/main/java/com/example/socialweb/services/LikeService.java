package com.example.socialweb.services;

import com.example.socialweb.models.entities.Like;
import com.example.socialweb.models.entities.News;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.repositories.LikeRepository;
import com.example.socialweb.repositories.NewsRepository;
import com.example.socialweb.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;

    @Transactional
    public boolean like(News news, User liker) {
        if (containsLikeByNewsAndLiker(news, liker)) {
            Like like = likeRepository.findLikeByNewsAndLiker(news, liker);
            likeRepository.delete(like);
            news.setLikeCount(news.getLikeCount()-1L);
            newsRepository.save(news);
            return false;
        } else {
            User user = userRepository.findUserById(liker.getId());
            Like like = new Like(user, news);
            news.setLikeCount(news.getLikeCount()+1L);
            likeRepository.save(like);
            newsRepository.save(news);
        }
        return true;
    }

    public boolean containsLikeByNewsAndLiker(News news, User liker) {
        return likeRepository.existsLikeByNewsAndLiker(news, liker);
    }
}
