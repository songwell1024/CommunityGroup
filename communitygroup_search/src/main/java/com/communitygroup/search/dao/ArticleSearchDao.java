package com.communitygroup.search.dao;

import com.communitygroup.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-25 09:43
 * @Modified by:
 **/
public interface ArticleSearchDao extends ElasticsearchRepository<Article, String> {
    public Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);

}
