package com.example.firstproject;

import com.example.firstproject.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class ArticleServiceTest {
    @Autowired
    private ArticleService articleService;

    @Test
    void index() {
        //1.예상데이터
        //2.실제데이터
        //3. 비교 및 검증
    }

    @Test
    void show_성공() {
        //id :454
        //title : "잘못된 만남-김건모"
        //content:"그런 만남이 있은 후부터 우리는 자주 함께만나며"
    //1. 예상데이터
        Article expected = new Article(454L, "잘못된 만남-김건모", "그런 만남이 있은 후부터 우리는 자주 함께만나며");
    //2. 실제데이터
        Article article = articleService.show(454L);
    //3. 비교및 검증
        assertEquals(expected.toString(), article.toString());

        // log.info("article = {}", article);
    }
    
    @Test
    void show_실패() {

        //id :454
        //title : "잘못된 만남-김건모"
        //content:"그런 만남이 있은 후부터 우리는 자주 함께만나며"
        //1. 예상데이터
        Article expected = new Article(454L, "잘못된 만남", "그런 만남이 있은 후부터 우리는 자주 함께만나며");
        //2. 실제데이터
        Article article = articleService.show(454L);
        //3. 비교및 검증
        assertEquals(expected.toString(), article.toString());

        // log.info("article = {}", article);
    }
}