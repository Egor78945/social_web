package com.example.socialweb.controllers.userControllers;

import com.example.socialweb.annotations.customExceptionHandlers.UserControllersExceptionHandler;
import com.example.socialweb.exceptions.RequestCancelledException;
import com.example.socialweb.exceptions.WrongDataException;
import com.example.socialweb.exceptions.WrongFormatException;
import com.example.socialweb.models.entities.News;
import com.example.socialweb.models.requestModels.NewsModel;
import com.example.socialweb.services.userServices.LikeService;
import com.example.socialweb.services.userServices.NewsService;
import com.example.socialweb.services.converters.NewsConverter;
import com.example.socialweb.services.userServices.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
@UserControllersExceptionHandler
@Slf4j
public class NewsController {
    private final NewsService newsService;
    private final LikeService likeService;
    private final UserService userService;

    // Post any news:
    @PostMapping("/post")
    public ResponseEntity<String> postNews(@RequestBody NewsModel newsModel) throws WrongFormatException {
        newsService.postNews(newsModel, userService.getCurrentUser().getId());
        return ResponseEntity.ok("News has been post.");
    }

    // Get news by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable("id") Long id) throws WrongDataException {
        News news = newsService.getNewsById(id);
        return ResponseEntity.ok(NewsConverter.convertNewsToNewsModel(news));
    }

    // Get all my news
    @GetMapping("/my")
    public ResponseEntity<?> getMyNews() throws RequestCancelledException {
        List<News> myNews = newsService.getNewsByPublisherId(userService.getCurrentUser().getId());
        return ResponseEntity.ok(NewsConverter.convertNewsToNewsModel(myNews));
    }

    // Get all news
    @GetMapping
    public ResponseEntity<?> getAllNews() throws WrongDataException {
        List<News> news = newsService.getAllNews();
        return ResponseEntity.ok(NewsConverter.convertNewsToNewsModel(news));
    }

    // Get all news, posted by individual user by id:
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getAllNewsByUserId(@PathVariable("id") Long id) throws RequestCancelledException {
        List<News> news = newsService.getNewsByPublisherId(id);
        return ResponseEntity.ok(NewsConverter.convertNewsToNewsModel(news));
    }

    // Delete news by id:
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable("id") Long newsId) throws RequestCancelledException {
        newsService.deleteNews(newsId, userService.getCurrentUser().getId());
        return ResponseEntity.ok("The news has been deleted.");
    }

    // Like or unlike news by id:
    @PostMapping("/like/{id}")
    public ResponseEntity<String> likeNews(@PathVariable("id") Long newsId) throws RequestCancelledException {
        if (likeService.like(newsId, userService.getCurrentUser().getId())) {
            return ResponseEntity.ok("You liked this news.");
        } else {
            return ResponseEntity.ok("You unliked this news.");
        }
    }
}
