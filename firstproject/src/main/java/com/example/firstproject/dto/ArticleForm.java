package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ArticleForm {
    private String title;
    private String content;

    public Article toEntity() {
    return new Article(null, title, content);
    }
}
