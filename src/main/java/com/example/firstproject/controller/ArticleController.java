package com.example.firstproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;
import java.util.*;
import org.springframework.web.bind.annotation.PostMapping;



@Slf4j //log.info쓸 수 있게하는 롬복
@Controller
public class ArticleController {
    @Autowired //DI의존성주입, 스프링부트가 미리 생성해둔 레파지터리 객체 주입
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }
    
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {//폼 데이터를 DTO로 받기
        log.info(form.toString());
        //System.out.println(form.toString());//DTO에 잘 담겼는지 확인        
        //1. DTO -> entity
        Article article = form.toEntity();
        log.info(article.toString());
        //System.out.println(article.toString());//엔티티 변환확인
        //2. save entity in repository(DB)
        Article saved=articleRepository.save(article);
        log.info(saved.toString());
        //System.out.println(saved.toString());//DB저장확인
        return "redirect:/articles/"+saved.getId();
    }
    
    @GetMapping("/articles/{id}") //컨트롤러 변수{}, 뷰 변수{}
    public String show(@PathVariable Long id, Model model){//매개변수로 url의 id받아오기
        log.info("id= "+id);//id잘 받았는지 확인
        //1. id조회해서 데이터(entity, Optional<Article>) 가져오기
        Article articleEntity=articleRepository.findById(id).orElse(null);//id없으면 null넣기
        //2. 모델에 데이터 등록
        model.addAttribute("article", articleEntity);
        //3. 뷰 페이지 반환
        return "/articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        //1. 모든 데이터 가져오기 list<entity>
        ArrayList<Article> articleEntityList=articleRepository.findAll();
        //2. 모델에 데이터 등록
        model.addAttribute("articleList", articleEntityList);
        //3. 뷰 페이지 설정
        return "/articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        //DB에서 수정할 데이터 가져오기
        Article articleEntity=articleRepository.findById(id).orElse(null);
        //모델에 데이터 등록
        model.addAttribute("article", articleEntity);
        //뷰페이지 설정
        return "articles/edit";
    }
}
