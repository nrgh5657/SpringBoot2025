package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
@Entity
@Table(name="member")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (nullable = false)
    private String email;

    @Column (nullable = false)
    private String password;
}
