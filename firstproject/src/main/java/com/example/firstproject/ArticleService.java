package com.example.firstproject;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        articleRepository.findById(id).ifPresent(article->{});
        Article article = articleRepository.findById(id).orElse(null);
        return article;
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId() != null) {
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        Article article = dto.toEntity();
        Article target = articleRepository.findById(id).orElse(null);


        if(target == null || !id.equals(article.getId())){
            return null;
        }
        target.patch(article);
        return articleRepository.save(target);

    }


    public Article delete(Long id) {
        //1. 대상찾기
        Article target = articleRepository.findById(id).orElse(null);
        //2. 잘못된 요청 처리하기
        if (target == null) {
            return null;
        }
        //3. 대상 삭제하기
        articleRepository.delete(target);
        return target;
    }

    public List<Article> createArticles(List<ArticleForm> dtos) {
        //0. toEntity(List<Article> articles) 변환
        ArticleForm articleForm = new ArticleForm();
        List<Article> articlesLists = articleForm.toEntity(dtos);

        //1. dto 묶음을 엔티티 묶음으로 변환하기
        List<Article> articleList= dtos.stream().map(dto->dto.toEntity()).collect(Collectors.toList());
        //2. 엔티티 묶음을 DB에 저장하기
        articleList.stream().forEach(article->articleRepository.save(article));
        //3. 강제 예외 발생시키기
        articleRepository.findById(-1L).orElseThrow(()->new IllegalArgumentException("결제 실패!!"));
        //4. 결과 값 반환하기
        return articleList;
    }
}
