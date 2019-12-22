package com.mp.cmfz.service;

import com.mp.cmfz.entity.Article;

import java.util.Map;

public interface ArticleService {

    Map<String, Object> findAllArticle(Integer page, Integer rows);

    void addAriticle(Article article);

    Map<String, Object> updatearticle(Article article);

    void deleteArticle(Article article);
}
