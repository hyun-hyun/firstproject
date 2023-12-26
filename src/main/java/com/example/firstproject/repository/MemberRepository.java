package com.example.firstproject.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.ArrayList;
import com.example.firstproject.entity.Member;

public interface MemberRepository extends CrudRepository<Member,Long> {
    @Override
    ArrayList<Member> findAll(); //Iterable -> ArrayList수정
}
