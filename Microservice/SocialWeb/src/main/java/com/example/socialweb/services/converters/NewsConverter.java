package com.example.socialweb.services.converters;

import com.example.socialweb.enums.NewsTheme;
import com.example.socialweb.models.entities.News;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.NewsModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class NewsConverter {
    private static final JsonMapper MAPPER = new JsonMapper();
    public static String convertNewsThemeToString(NewsTheme theme) {
        if (theme == NewsTheme.SPORT)
            return "sport";
        else if (theme == NewsTheme.GAME)
            return "game";
        else
            return "politic";
    }

    public static NewsTheme convertStringToNewsTheme(String theme) {
        if (theme.equalsIgnoreCase("sport"))
            return NewsTheme.SPORT;
        else if (theme.equalsIgnoreCase("game"))
            return NewsTheme.GAME;
        else
            return NewsTheme.POLITIC;
    }

    public static NewsModel convertNewsToNewsModel(News news) {
        return new NewsModel(news);
    }

    public static News convertNewsModelToNews(NewsModel newsModel, User publisher) {
        return new News.Builder(publisher, newsModel.getDescription())
                .setTheme(convertStringToNewsTheme(newsModel.getTheme()))
                .build();
    }

    public static List<NewsModel> convertNewsToNewsModel(List<News> list) {
        return list
                .stream()
                .map(NewsConverter::convertNewsToNewsModel)
                .collect(Collectors.toList());
    }
    public static String convertNewsToJsonString(News news){
        try {
            return MAPPER.writeValueAsString(news);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            return null;
        }
    }
    public static News convertJsonToNews(String json){
        try {
            return MAPPER.readValue(json, News.class);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            return null;
        }
    }
}
