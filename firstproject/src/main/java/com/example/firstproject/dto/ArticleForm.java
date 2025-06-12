package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticleForm {
    public Article toEntity;
    private Long id;
    private String title;
    private String content;

    public Article toEntity() {
    return new Article(id, title, content);
    }

    public List<Article> toEntity(List<ArticleForm> articles){
        List list = new ArrayList();
        for(ArticleForm article : articles){
            list.add(new Article(id, title, content));
        }
        return list;
    }
}
