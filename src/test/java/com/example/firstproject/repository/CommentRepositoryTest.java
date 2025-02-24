package com.example.firstproject.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;

@DataJpaTest
public class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글 모든 댓글 조회")
    void findByArticleId() {
        /*Case 1: 4번 게시글의 모든 댓글 조회 */
        {
            //1. 입력 데이터 준비
            Long articleId = 4L;//4번 게시글 조회
            //2. 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);
            //3. 예상 데이터
            Article article=new Article(4L, "당신의 인생 영화는?","댓글 ㄱㄱ");
            Comment a=new Comment(1L, article, "Park", "굿 윌 헌팅");
            Comment b=new Comment(2L, article,"Kim","노트북");
            Comment c=new Comment(3L, article,"Choi","쇼탱크 탈출");
            List<Comment> expected = Arrays.asList(a,b,c);
            //4. 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글 출력!");
        }
        /*Case 2: 1번 게시글의 모든 댓글 조회 */
        {
            //1. 입력 데이터 준비
            Long articleId = 1L;//1번 게시글 조회
            //2. 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);
            //3. 예상 데이터
            Article article=new Article(1L, "가가가가","1111");
            List<Comment> expected = Arrays.asList();
            //4. 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "1번 글은 댓글 없음");
        }

    }
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    @Test
    void testFindByNickname() {
        //1. 입력 데이터 준비
        String nickname="Park";
        //2. 실제 데이터
        List<Comment> comments = commentRepository.findByNickname(nickname);
        //3. 예상 데이터
        Comment a=new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱㄱ"),
            nickname, "굿 윌 헌팅");
        Comment b=new Comment(4L, new Article(5L, "당신의 소울 푸드는?","댓글달아주세요"),
            nickname, "치킨");
        List<Comment> expected = Arrays.asList(a,b);
        //4. 비교 및 검증
        assertEquals(expected.toString(), comments.toString(), "Park의 모든 댓글을 출력!");
    }
}
