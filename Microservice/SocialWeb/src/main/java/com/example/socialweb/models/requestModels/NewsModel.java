package com.example.socialweb.models.requestModels;

import com.example.socialweb.models.entities.News;
import com.example.socialweb.services.converters.NewsConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class NewsModel {
    private String theme;
    private String description;
    private Long likeCount;
    public NewsModel(){

    }
    public NewsModel(News news){
        this.description = news.getDescription();
        this.theme = NewsConverter.convertNewsThemeToString(news.getTheme());
        this.likeCount = news.getLikeCount();
    }
}
