package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor//기본생성자
@ToString
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//DB가 id 자동생성
    private Long id;
    @Column
    private String title;
    @Column
    private String content;
    /*public Long getId() {
        return id;
    }*/
	public void patch(Article article) {
        //article(새데이터)에 내용있는것(바뀐것)만 기존(this, target)에 붙이기
        if(article.title !=null){
            this.title=article.title;
        }
        if(article.content!=null){
            this.content=article.content;
        }
	}

}
