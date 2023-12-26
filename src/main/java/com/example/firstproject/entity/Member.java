package com.example.firstproject.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor//모든변수 생성자
@NoArgsConstructor//기본생성자
@ToString
public class Member {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String nickname;
    @Column
    private String email;
    @Column
    private String pswd;


}
