package com.communitygroup.search.service;

import com.communitygroup.search.dao.ArticleSearchDao;
import com.communitygroup.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-25 09:51
 * @Modified by:
 **/
@Service
@Transactional
public class ArticleSearchService {
    @Autowired
    ArticleSearchDao articleDao;

    /**
     * 添加
     * @param article
     */
    public void add(Article article){
        articleDao.save(article);
    }

    /**
     *  查询
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findByTitleOrContentLike(String keywords, Integer page, Integer size){
        Pageable pageable = PageRequest.of(page - 1, size);
        return articleDao.findByTitleOrContentLike(keywords, keywords, pageable);
    }
}
