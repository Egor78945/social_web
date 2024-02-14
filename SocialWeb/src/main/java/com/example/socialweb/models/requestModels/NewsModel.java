package com.example.socialweb.models.requestModels;

import com.example.socialweb.models.entities.News;
import com.example.socialweb.services.converters.NewsConverter;
import lombok.Data;

@Data
public class NewsModel {
    private String theme;
    private String description;
    public NewsModel(){

    }
    public NewsModel(News news){
        this.description = news.getDescription();
        this.theme = NewsConverter.convertNewsThemeToString(news.getTheme());
    }
}
