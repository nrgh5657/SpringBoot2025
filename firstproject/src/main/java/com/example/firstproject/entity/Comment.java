package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickName;

    private String body;

    @ManyToOne //(fecth = FetchType.Lazy)
    @JoinColumn(name="article_id")
    private Article article;


    public static Comment create(CommentDto dto, Article article) {
    //1. 예외발생
        if(dto.getId() !=null){
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어여 합니다");
        }

        if(dto.getArticleId() != article.getId()){
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었습니다.");
        }

    //2. 엔티티 생성 및 반환
        return new Comment(dto.getId(), dto.getNickname(), dto.getBody(), article);
    }

    public void patch(CommentDto dto) {
        //1. 예외발생
        if(this.id != dto.getId()){
            throw new IllegalArgumentException("댓글 수정 실패!, 잘못된 id가 입력되었습니다.");
        }
        if(dto.getNickname() != null){//수정할 nickname이 있니?
             this.nickName = dto.getNickname();
        }
        if(dto.getBody() != null){//수정한 body(내용)가 있니
            this.body = dto.getBody();
        }
   }
}
