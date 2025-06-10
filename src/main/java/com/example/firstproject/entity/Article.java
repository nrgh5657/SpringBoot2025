package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity //테이블로 생성
@Table(name = "article") //테이블 이름
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // 자동생성기능 추가(숫자가 자동증가)
    private Long id;

    @Column
    private String title;

    private String content;



}
