package com.example.socialweb.services.userServices;

import com.example.socialweb.enums.UserRole;
import com.example.socialweb.exceptions.RequestCancelledException;
import com.example.socialweb.exceptions.WrongDataException;
import com.example.socialweb.exceptions.WrongFormatException;
import com.example.socialweb.models.entities.News;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.NewsModel;
import com.example.socialweb.repositories.NewsRepository;
import com.example.socialweb.repositories.UserRepository;
import com.example.socialweb.services.converters.NewsConverter;
import com.example.socialweb.services.validation.NewsValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final String HASH_KEY = "news";

    @Transactional
    public void postNews(NewsModel newsModel, Long userId) throws WrongFormatException {
        if (NewsValidation.isValidNews(newsModel)) {
            User user = userRepository.findUserById(userId);
            News news = NewsConverter.convertNewsModelToNews(newsModel, user);
            newsRepository.save(news);
        } else
            throw new WrongFormatException("Description or theme is invalid.");
    }

    public List<News> getNewsByPublisherId(Long userId) throws RequestCancelledException {
        if (userRepository.existsById(userId)) {
            List<News> newsList = newsRepository.findAllByPublisherId(userId);
            if (!newsList.isEmpty()) {
                return newsList;
            }
            throw new RequestCancelledException(String.format("User with id %s has not posted any news.", userId));
        }
        throw new RequestCancelledException(String.format("User with id %s, is not found.", userId));
    }

    @Transactional
    public List<News> getAllNews() throws WrongDataException {
        List<Object> newsHashes = redisTemplate.opsForHash().values(HASH_KEY);
        if (newsHashes.isEmpty()) {
            List<News> newsList = newsRepository.findAll();
            if (!newsList.isEmpty()) {
                newsList.forEach(n -> redisTemplate.opsForHash().put(HASH_KEY, n.getId(), NewsConverter.convertNewsToJsonString(n)));
                return newsList;
            }
            throw new WrongDataException("No one news is found.");
        }
        return newsHashes.stream().map(h -> NewsConverter.convertJsonToNews((String) h)).toList();
    }

    @Transactional
    public News getNewsById(Long id) throws WrongDataException {
        String newsHash = (String) redisTemplate.opsForHash().get(HASH_KEY, id.toString());
        if (newsHash == null) {
            News news = newsRepository.findNewsById(id);
            if (news != null) {
                redisTemplate.opsForHash().put(HASH_KEY, news.getId().toString(), NewsConverter.convertNewsToJsonString(news));
                return news;
            }
            throw new WrongDataException(String.format("News with id %s is not found.", id));
        }
        return NewsConverter.convertJsonToNews(newsHash);
    }

    @Transactional
    public void deleteNews(Long newsId, Long currentUserId) throws RequestCancelledException {
        News news = newsRepository.findNewsById(newsId);
        User currentUser = userRepository.findUserById(currentUserId);
        if (news.getPublisher().getId().equals(currentUser.getId()) || currentUser.getRole().contains(UserRole.ADMIN)) {
            newsRepository.delete(news);
        } else {
            throw new RequestCancelledException("You can not delete other user news.");
        }
    }
}
