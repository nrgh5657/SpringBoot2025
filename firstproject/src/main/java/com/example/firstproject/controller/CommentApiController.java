package com.example.firstproject.controller;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CommentApiController {
    private final CommentService commentService;

    //1댓글 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> getCommnets(@PathVariable Long articleId){
        List<CommentDto> dtos= commentService.comments(articleId);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
    //2댓글 생성
    @PostMapping("/api/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto){
        //1. 서비스 위임
        CommentDto createdDto = commentService.create(articleId, dto);
        //2. 결과 전달
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDto);
    }

    //3댓글 수정
    //4댓글 삭제

}
