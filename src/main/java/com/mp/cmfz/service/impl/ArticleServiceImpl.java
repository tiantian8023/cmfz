package com.mp.cmfz.service.impl;

import com.mp.cmfz.dao.ArticleDao;
import com.mp.cmfz.entity.Article;
import com.mp.cmfz.enums.ExceptionEnum;
import com.mp.cmfz.exception.MyException;
import com.mp.cmfz.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public Map<String, Object> findAllArticle(Integer page, Integer rows) {
        Example example = new Example(Article.class);
        int count = articleDao.selectCountByExample(example);
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Article> articles = articleDao.selectByExampleAndRowBounds(example, rowBounds);
        if (CollectionUtils.isEmpty(articles)) {
            throw new MyException(ExceptionEnum.Article_NOT_FOUND);
        }
        Map map = new HashMap();
        map.put("records", count);
        map.put("rows", articles);
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1);
        map.put("page", page);
        return map;
    }

    @Override
    public void addAriticle(Article article) {
        Map<String, Object> map = new HashMap<>();
        try {
            article.setId(UUID.randomUUID().toString())
                    .setPublishDate(new Date());
            int insert = articleDao.insert(article);
            if (insert == 1) {
                map.put("code", 200);
            }
        } catch (Exception e) {
            map.put("code", 500);
            map.put("error", e.getMessage());
        }
    }

    @Override
    public Map<String, Object> updatearticle(Article article) {
        Map<String, Object> map = new HashMap<>();
        try {
            article.setPublishDate(new Date());
            System.out.println("article = " + article);
            int i = articleDao.updateByPrimaryKeySelective(article);
            if (i == 1) {
                map.put("code", 200);
                map.put("msg", "success");
            } else {
                map.put("code", 500);
                map.put("error", "更改文章失败");
            }
        } catch (Exception e) {
            map.put("code", 500);
            map.put("error", e.getMessage());
        }
        return map;
    }

    @Override
    public void deleteArticle(Article article) {
        articleDao.deleteByPrimaryKey(article);
    }
}
