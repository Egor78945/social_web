package com.example.socialweb.controllers.userControllers;

import com.example.socialweb.configurations.utils.Cache;
import com.example.socialweb.models.entities.News;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.NewsModel;
import com.example.socialweb.services.userServices.LikeService;
import com.example.socialweb.services.userServices.NewsService;
import com.example.socialweb.services.converters.NewsConverter;
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
    private final LikeService likeService;
    private final Cache cache;

    // Post any news:
    @PostMapping("/post")
    public ResponseEntity<String> postNews(@RequestBody NewsModel newsModel) {
        try {
            newsService.postNews(newsModel, cache.getUser().getId());
        } catch (RequestRejectedException e) {
            log.info(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok("News has been post.");
    }

    // Get all my news
    @GetMapping("/my")
    public ResponseEntity<?> getMyNews() {
        List<News> myNews = newsService.getNewsByPublisherId(cache.getUser().getId());
        if (myNews.isEmpty())
            return ResponseEntity.ok("You have not news.");
        return ResponseEntity.ok(NewsConverter.convertNewsToNewsModel(myNews));
    }

    // Get all news
    @GetMapping
    public ResponseEntity<?> getAllNews() {
        List<News> news = newsService.getAllNews();
        if (news.isEmpty())
            return ResponseEntity.ok("There is no any news.");
        return ResponseEntity.ok(NewsConverter.convertNewsToNewsModel(news));
    }

    // Get all news, posted by individual user by id:
    @GetMapping("/{id}")
    public ResponseEntity<?> getAllNewsByUserId(@PathVariable("id") Long id) {
        List<News> news = newsService.getNewsByPublisherId(id);
        if (news.isEmpty())
            return ResponseEntity.ok("This user has not any news.");
        return ResponseEntity.ok(NewsConverter.convertNewsToNewsModel(news));
    }

    // Delete news by id:
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable("id") Long newsId) {
        try {
            User currentUser = cache.getUser();
            newsService.deleteNews(newsId, currentUser);
            return ResponseEntity.ok("The news has been deleted.");
        } catch (RequestRejectedException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    // Like or unlike news by id:
    @PostMapping("/like/{id}")
    public ResponseEntity<String> likeNews(@PathVariable("id") Long newsId) {
        User liker = cache.getUser();
        if (likeService.like(newsId, liker)) {
            log.info("You liked this news.");
            return ResponseEntity.ok("You liked this news.");
        } else {
            log.info("Your removed like from this news.");
            return ResponseEntity.ok("You unliked this news.");
        }
    }
}
