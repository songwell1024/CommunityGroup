package com.communitygroup.article.controller;

import com.communitygroup.article.pojo.Comment;
import com.communitygroup.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-24 14:35
 * @Modified by:
 **/
@RestController
@RequestMapping("comment")
@CrossOrigin
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * 添加文章评论
     * @param comment
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result addComment(@RequestBody Comment comment){
        commentService.add(comment);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 根据文章id查询文章评论
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
    public Result findAllByArticleId(@PathVariable String articleId){
        return new Result(true,StatusCode.OK,"查询成功",commentService.findByArticleId(articleId));
    }

    /**
     * 删除文章的所有评论
     * @param articleId
     * @return
     */
    @RequestMapping(value ="/{articleId}", method = RequestMethod.DELETE)
    public Result deleteAllByArticleId(@PathVariable String articleId){
        commentService.deleteAllByArticleId(articleId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 删除文章的某一条评论
     * @param id
     * @return
     */
    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
    public Result deleteCommentById(@PathVariable String id){
        commentService.deleteCommentById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
