package com.example.firstproject.dto;

import com.example.firstproject.entity.Member;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor //클래스 모든 필드의 생성자 만듬
//@NoArgsConstructor //기본생성자(아무것도 없는거)
@ToString //클래스 모든 필드의 String으로 확인하는 것 오버라이딩
public class MemberForm {
    private Long id;
    private String nickname;
    private String email;
    private String pswd;
    public Member toEntity(){
        return new Member(id, nickname, email, pswd);
    }

/*
    public MemberForm(String nickname, String email, String pswd) {
        this.nickname=nickname;
        this.email=email;
        this.pswd=pswd;
    }

    @Override
    public String toString(){
        return "MemberForm{"+
        "nickname='"+nickname+'\''+
        ", email='"+email+'\''+
        ", password='"+pswd+'\''+
        '}';
    }
*/
}
