package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ArticleApiController {

    private final ArticleRepository articleRepository;

    @GetMapping("/articles")
    public List<Article> index() {
        return articleRepository.findAll();
    }

    @GetMapping("/articles/{id}")
    public Article show(@PathVariable("id") Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @PostMapping("/articles")
    public Article create(@RequestBody ArticleForm form) {
        Article article = form.toEntity();
        return articleRepository.save(article);

    }

    // PATCH -> 수정
    @PatchMapping("/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {

        //    Article article = articleRepository.findById(id).get();

        // 1. DTO -> entity 변환
        Article article = dto.toEntity();
        // 2. 타닛 조회하기
        Article target = articleRepository.findById(id).orElse(null);
        // 3. 잘못된 요청 처리하기
        if (target == null ||  !id.equals(article.getId())) { // 잘못된 요청인지 판별
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // ResponseEntity 반환
        }
        // 4. update 및 정상 응답하기
        Article updated = articleRepository.save(article);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
}