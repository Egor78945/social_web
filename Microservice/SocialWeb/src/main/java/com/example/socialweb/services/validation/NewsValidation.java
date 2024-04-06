package com.example.socialweb.services.validation;

import com.example.socialweb.models.requestModels.NewsModel;

public class NewsValidation {
    public static boolean isValidDescription(String description) {
        return !description.isEmpty() && description.length() < 200;
    }

    public static boolean isValidTheme(String theme) {
        return theme.equalsIgnoreCase("game") ||
                theme.equalsIgnoreCase("sport") ||
                theme.equalsIgnoreCase("politic");
    }

    public static boolean isValidNews(NewsModel news) {
        return isValidDescription(news.getDescription()) && isValidTheme(news.getTheme());
    }
}
