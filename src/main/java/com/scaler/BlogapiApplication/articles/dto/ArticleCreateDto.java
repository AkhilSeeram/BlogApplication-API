package com.scaler.BlogapiApplication.articles.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class ArticleCreateDto {
    @NonNull
    String title;
    String subtitle;
    @NonNull
    String body;
}
