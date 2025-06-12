package com.example.firstproject.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository comment;

    @Test
    void findByArticleId() {
        comment.findByArticleId(10L)
                .forEach(System.out::println);
    }
    @Test
    void findByArticleId2(){
        comment.findByNickName("Kim")
                .forEach(System.out::println);
    }
}