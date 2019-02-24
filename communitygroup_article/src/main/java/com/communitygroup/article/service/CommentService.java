package com.communitygroup.article.service;

import com.communitygroup.article.dao.CommentDao;
import com.communitygroup.article.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.List;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-24 14:30
 * @Modified by:
 **/
@Service
@Transactional
public class CommentService {

    @Autowired
    CommentDao commentDao;
    @Autowired
    IdWorker idWorker;

    /**
     * 添加评论
     * @param comment
     */
    public void add(Comment comment){
        comment.set_id(idWorker.nextId() + "");
        commentDao.save(comment);
    }

    /**
     * 通过文章id查询评论列表
     * @param articleId
     * @return
     */
    public List<Comment> findByArticleId(String articleId){
        return commentDao.findAllByArticleid(articleId);
    }

    /**
     * 删除某一条评论
     * @param id
     */
    public void deleteCommentById(String id){
        commentDao.deleteById(id);
    }

    /**
     * 删除文章的全部评论
     * @param articleId
     */
    public void deleteAllByArticleId(String articleId){
        commentDao.deleteAllByArticleid(articleId);
    }




}
