package com.example.socialweb.services;

import com.example.socialweb.models.entities.News;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.NewsModel;
import com.example.socialweb.repositories.NewsRepository;
import com.example.socialweb.repositories.UserRepository;
import com.example.socialweb.services.converters.NewsConverter;
import com.example.socialweb.services.validation.NewsValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    @Transactional
    public void postNews(NewsModel newsModel, User currentUser) {
        if (NewsValidation.isValidNews(newsModel)) {
            User user = userRepository.findUserById(currentUser.getId());
            News news = NewsConverter.convertNewsModelToNews(newsModel, user);
            newsRepository.save(news);
        } else
            throw new RequestRejectedException("Description or theme is invalid.");
    }

    public List<News> getNewsByPublisher(User user) {
        return newsRepository.findAllByPublisher(user);
    }
    public List<News> getAllNews(){
        return newsRepository.findAll();
    }
    public News getNewsById(Long id){
        return newsRepository.findNewsById(id);
    }

    @Transactional
    public void deleteNews(News news, User currentUser) {
        if(news.getPublisher().getId().equals(currentUser.getId())){
            newsRepository.delete(news);
        } else
            throw new RequestRejectedException("You can not delete other user news.");
    }
}
