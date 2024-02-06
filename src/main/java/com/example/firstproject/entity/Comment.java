package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity //이 클래스는 entity 선언, 바탕으로 DB에 테이블 생성
@Getter //조회 getter메서드 자동생성
@ToString//모든필드출력가능
@AllArgsConstructor//모든 필드 매개변수 갖는 생성자 생성
@NoArgsConstructor//매개변수 없는 기본생성자 생성
public class Comment {
    @Id//대표키 지정
    @GeneratedValue(strategy=GenerationType.IDENTITY)//DB가 자동으로 1씩 증가
    private Long id;//대표키PK
    @ManyToOne//Comment n : Article 1 로  entity 관계설정
    @JoinColumn(name="article_id")//외래키 생성, Article 엔티티 기본키(id)와 매핑
    private Article article;//부모게시글
    @Column//해당 필드를 테이블 속성으로 매핑
    private String nickname;//댓글 단 사람
    @Column
    private String body;//댓글 내용
}
