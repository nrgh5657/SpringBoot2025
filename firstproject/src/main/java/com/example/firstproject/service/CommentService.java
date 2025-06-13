package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
        //1. 댓글 조회
//        List<Comment> comments= commentRepository.findByArticleId(articleId);
//        //2. 엔티티 -> dto변환
//        List<CommentDto> dtos = new ArrayList<>();
//        for (Comment comment : comments){
//            CommentDto dto = CommentDto.createCommentDto(comment);
//            dtos.add(dto);
//        }
//
//        //3. 결과 반환
//        return dtos;

        List<CommentDto> list = commentRepository.
                findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
        return list;
    }

    public CommentDto create(Long articleId, CommentDto dto) {
        //1 게시글 조회 및 예외 발생
         Article article= articleRepository.findById(articleId).
                orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패!" + articleId + "번 게시글이 존재하지 않습니다."));
        //2 댓글 엔티티 생성
        Comment comment = Comment.create(dto,article);
        //3 댓글 엔티티를 DB에 저장
        Comment created = commentRepository.save(comment);
        //4. DTO로 변환에 반환
        return CommentDto.createCommentDto(created);
    }
}
