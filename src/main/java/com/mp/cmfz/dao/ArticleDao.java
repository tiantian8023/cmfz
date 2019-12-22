package com.mp.cmfz.dao;

import com.mp.cmfz.entity.Article;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ArticleDao extends Mapper<Article> {
}
