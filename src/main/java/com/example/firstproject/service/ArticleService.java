package com.example.firstproject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j //로깅
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article=dto.toEntity();
        if(article.getId() !=null) {
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        //1. dto->entity
        Article article=dto.toEntity();
        log.info("id:{}, article: {}", id, article.toString());
        //2. target 조회
        Article target=articleRepository.findById(id).orElse(null);
        //3. 잘못된 요청 처리
        if(target==null||id!=article.getId()){
            //400, 잘못된 요청 응답
            log.info("잘못된 요청!  id: {}, article: {}",id, article.toString());
            return null;
        }
        //4. 업데이트 및 정상 응답(200)
        target.patch(article);//기존데이터(target)에 새 데이터(article)붙이기
        Article updated=articleRepository.save(target);//db저장
        return updated;
    }

    public Article delete(Long id) {
        //1. 대상찾기
        Article target=articleRepository.findById(id).orElse(null);
        //2. 잘못된 요청 처리
        if(target==null){
            return null;
        }
        //3. 대상 삭제
        articleRepository.delete(target);
        return target;
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        //1. dto 묶음 -> entity 묶음 변환
        List<Article> articleList=dtos.stream()
                    .map(dto->dto.toEntity())
                    .collect(Collectors.toList());
        //2. entity묶음 -> DB 저장
        articleList.stream()
                .forEach(article->articleRepository.save(article));
        //3. 강제로 에러 발생시키기
        articleRepository.findById(-1L)
                .orElseThrow(()->new IllegalArgumentException("결제 실패!"));
        //4. 결과 반환
        return articleList;
    }
}
