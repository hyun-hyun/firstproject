package com.example.firstproject.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.ArrayList;
import com.example.firstproject.entity.Article;

public interface ArticleRepository extends CrudRepository<Article,Long>{
    @Override
    ArrayList<Article> findAll(); //Iterable -> ArrayList수정
}
