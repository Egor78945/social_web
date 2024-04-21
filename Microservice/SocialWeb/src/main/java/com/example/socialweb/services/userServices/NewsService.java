package com.example.socialweb.services.userServices;

import com.example.socialweb.enums.UserRole;
import com.example.socialweb.models.entities.News;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.NewsModel;
import com.example.socialweb.repositories.NewsRepository;
import com.example.socialweb.repositories.UserRepository;
import com.example.socialweb.services.converters.NewsConverter;
import com.example.socialweb.services.validation.NewsValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    @Transactional
    public void postNews(NewsModel newsModel, Long userId) {
        if (NewsValidation.isValidNews(newsModel)) {
            User user = userRepository.findUserById(userId);
            News news = NewsConverter.convertNewsModelToNews(newsModel, user);
            newsRepository.save(news);
        } else
            throw new RequestRejectedException("Description or theme is invalid.");
    }

    public List<News> getNewsByPublisherId(Long userId) {
        return newsRepository.findAllByPublisherId(userId);
    }
    public List<News> getAllNews(){
        return newsRepository.findAll();
    }
    public News getNewsById(Long id){
        return newsRepository.findNewsById(id);
    }

    @Transactional
    public void deleteNews(Long newsId, User currentUser) {
        News news = getNewsById(newsId);
        if(news.getPublisher().getId().equals(currentUser.getId()) || currentUser.getRole().contains(UserRole.ADMIN)){
            newsRepository.delete(news);
            log.info("News has been deleted.");
        } else
            throw new RequestRejectedException("You can not delete other user news.");
    }
}
