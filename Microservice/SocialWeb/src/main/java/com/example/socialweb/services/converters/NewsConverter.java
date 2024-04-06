package com.example.socialweb.services.converters;

import com.example.socialweb.enums.NewsTheme;
import com.example.socialweb.models.entities.News;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.NewsModel;

import java.util.ArrayList;
import java.util.List;

public class NewsConverter {
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
    public static List<NewsModel> convertNewsToNewsModel(List<News> list){
        List<NewsModel> newsModels = new ArrayList<>();
        for(News n: list){
            newsModels.add(convertNewsToNewsModel(n));
        }
        return newsModels;
    }
}
