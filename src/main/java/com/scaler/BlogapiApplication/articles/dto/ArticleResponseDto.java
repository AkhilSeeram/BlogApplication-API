package com.scaler.BlogapiApplication.articles.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ArticleResponseDto {
    @NonNull
    String title;
    String subtitle;
    @NonNull
    String body;
}
