package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;

    @GetMapping("/new")
    public String newArticle() {
        return "articles/new";
    }

    @PostMapping ("/create")
    public String createArticle(ArticleForm form) {
        log.info("New Article Created");
        log.info("articleForm: {}", form);

        //1.DTO를 엔티티로 변환
        Article article = form.toEntity();

        //2. 리파지터리 엔티티로 DB에 저장
        Article saved = articleRepository.save(article);


        return "redirect:/articles/" + saved.getId();
    }

    // 단건조회 : CRUD에 R
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        log.info("Show article {}", id);

        // 1. {id}값을 DB에서 꺼내오기
        //  Optional<Article> article = articleRepository.findById(id); // 아이디 조회(조회 시 데이터가 없을 경우 null 반환, 아이디 있을 경우 엔티티 반환)
        Article articleEntity = articleRepository.findById(id).orElse(null); // 위에 주석처리한 코드와 같은 코드
        List<CommentDto> commentDtos = commentService.comments(id); // 서비스에서 comments(id) 메서드를 호출해 조회한 댓글 목록을 commentDtos 변수에 저장

        log.info("Article : {}", articleEntity);

        // 2. Entity -> DTO 반환
        // 생략

        // 3. View 전달
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos); // 댓글 목록 모델에 등록
        return "articles/show";
    }

    @GetMapping("")
    public String index(Model model) {
        //1. 모든 데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll();
        articleEntityList.forEach(list -> {log.info("List:{}", list);});
        //2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);
        //3. 뷰페이지 설정하기

        return "articles/index";
    }

    //articles/{{article.id}}/edit
    //localhost:8080/articles/2/edit  -> 이렇게 요청하면 edit()메소드가 응답
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id")Long id, Model model) {

        //1. 수정할 데이타 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);

        //3. 뷰 페이지 설정하기
        return "articles/edit";
    }

    @PostMapping("/update")
    public String updateArticle(ArticleForm form) {

        log.info("Update article : {}",form);

        //1. DTO를 엔티티로 변환하기
        Article articleEntity = form.toEntity();

        //2. 엔티티를 DB에 저장하기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        if(target != null) {
            articleRepository.save(articleEntity);
        }

        //3. 수정 결과 페이지로 리다이렉트하기

        return "redirect:/articles/" + articleEntity.getId();
    }

    //localhost:8080/articles/1/delete
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id")Long id, RedirectAttributes redirectAttributes) {

        //1. 삭제할 데이타 가져오기
        Article target = articleRepository.findById(id).orElse(null);

        //2. 대상 엔티티 삭제하기
        if(target != null) {
            articleRepository.delete(target);
            redirectAttributes.addFlashAttribute("msg", "삭제됐습니다!");
        }

        //3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }

}


