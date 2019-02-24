package com.communitygroup.article.dao;

import com.communitygroup.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-24 14:24
 * @Modified by:
 **/
public interface CommentDao extends MongoRepository<Comment, String> {
    public List<Comment> findAllByArticleid(String articleId);
    public void deleteAllByArticleid(String articleId);
}
