package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor //클래스 모든 필드의 생성자 만듬 this.title=title;
//@NoArgsConstructor //기본생성자
@ToString //클래스 모든 필드의 String으로 확인하는 것 오버라이딩
public class ArticleForm {
    private Long id;
    private String title;
    private String content;
    
    
    public Article toEntity() {
        return new Article(id, title, content);
    }
}
