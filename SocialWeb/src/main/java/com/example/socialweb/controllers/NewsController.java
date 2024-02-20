package com.example.socialweb.controllers;

import com.example.socialweb.configurations.utils.ServerUtils;
import com.example.socialweb.models.entities.News;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.NewsModel;
import com.example.socialweb.services.LikeService;
import com.example.socialweb.services.NewsService;
import com.example.socialweb.services.UserService;
import com.example.socialweb.services.converters.NewsConverter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
@Slf4j
public class NewsController {
    private final NewsService newsService;
    private final UserService userService;
    private final LikeService likeService;

    @PostMapping("/post")
    public ResponseEntity<String> postNews(@RequestBody NewsModel newsModel, HttpServletRequest request) {
        try {
            User user = ServerUtils.getUserFromSession(request);
            newsService.postNews(newsModel, user);
        } catch (RequestRejectedException e) {
            log.info(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok("News has been post.");
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyNews(HttpServletRequest request) {
        User user = ServerUtils.getUserFromSession(request);
        List<News> myNews = newsService.getNewsByPublisher(user);
        if (myNews.isEmpty())
            return ResponseEntity.ok("You have not news.");
        List<NewsModel> newsModels = NewsConverter.convertNewsToNewsModel(myNews);
        return ResponseEntity.ok(newsModels);
    }

    @GetMapping
    public ResponseEntity<?> getAllNews() {
        List<News> news = newsService.getAllNews();
        if (news.isEmpty())
            return ResponseEntity.ok("There is no any news.");
        List<NewsModel> newsModels = NewsConverter.convertNewsToNewsModel(news);
        return ResponseEntity.ok(newsModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllNewsByUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        List<News> news = newsService.getNewsByPublisher(user);
        if (news.isEmpty())
            return ResponseEntity.ok("This user has not any news.");
        List<NewsModel> newsModels = NewsConverter.convertNewsToNewsModel(news);
        return ResponseEntity.ok(newsModels);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable("id") Long id, HttpServletRequest request) {
        try {
            User currentUser = ServerUtils.getUserFromSession(request);
            News news = newsService.getNewsById(id);
            newsService.deleteNews(news, currentUser);
            log.info("News has been deleted.");
            return ResponseEntity.ok("The news has been deleted.");
        } catch (RequestRejectedException e) {
            log.info(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<String> likeNews(@PathVariable("id") Long id, HttpServletRequest request) {
        News news = newsService.getNewsById(id);
        User liker = ServerUtils.getUserFromSession(request);
        if (likeService.like(news, liker)) {
            log.info("You liked this news.");
            return ResponseEntity.ok("You liked this news.");
        }
        else {
            log.info("Your removed like from this news.");
            return ResponseEntity.ok("You removed like from this news.");
        }
    }
}
