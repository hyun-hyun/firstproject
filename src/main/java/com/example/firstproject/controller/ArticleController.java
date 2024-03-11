package com.example.firstproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;

import lombok.extern.slf4j.Slf4j;
import java.util.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




@Slf4j //log.info쓸 수 있게하는 롬복
@Controller
public class ArticleController {
    @Autowired //DI의존성주입, 스프링부트가 미리 생성해둔 레파지터리 객체 주입
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;//서비스 객체 주입

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
    
    @GetMapping("/articles/{id}") //컨트롤러 변수{}, 뷰 변수{{}}
    public String show(@PathVariable Long id, Model model){//매개변수로 url의 id받아오기
        log.info("id= "+id);//id잘 받았는지 확인
        //1. id조회해서 데이터(entity, Optional<Article>) 가져오기
        Article articleEntity=articleRepository.findById(id).orElse(null);//id없으면 null넣기
        List<CommentDto> commentsDtos=commentService.comments(id);
        //2. 모델에 데이터 등록
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentsDtos);
        //3. 뷰 페이지 반환
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        //1. 모든 데이터 가져오기 list<entity>
        ArrayList<Article> articleEntityList=articleRepository.findAll();
        //2. 모델에 데이터 등록
        model.addAttribute("articleList", articleEntityList);
        //3. 뷰 페이지 설정
        return "articles/index";
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

    @PostMapping("/articles/update")
    public String update(ArticleForm form){//매개변수로 DTO받기
        log.info(form.toString());
        //1. DTO->entity
        Article articleEntity=form.toEntity();
        log.info(articleEntity.toString());
        //2.entity DB에 저장
        Article target=articleRepository.findById(articleEntity.getId()).orElse(null);
        if(target !=null){
            articleRepository.save(articleEntity);//갱신
        }
        return "redirect:/articles/"+articleEntity.getId();
    }
    @GetMapping("articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청이 들어왔습니다.");
        //1. 삭제 대상 가져오기
        Article target=articleRepository.findById(id).orElse(null);
        log.info(target.toString());//target 데이터 확인
        //2. 대상 엔티티 삭제
        if(target !=null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제됐습니다!");//리다이렉트된 페이지에서 사용
        }
        //3. 결과 페이지로 리다이렉트
        return "redirect:/articles";
    }
}
