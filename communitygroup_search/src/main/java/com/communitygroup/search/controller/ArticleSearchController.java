package com.communitygroup.search.controller;

import com.communitygroup.search.pojo.Article;
import com.communitygroup.search.service.ArticleSearchService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-25 09:54
 * @Modified by:
 **/
@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleSearchController {

    @Autowired
    ArticleSearchService articleSearchService;

    /**
     * 向索引库中添加索引
     * @param article
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Article article){
        articleSearchService.add(article);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 查询
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/search/{keywords}/{page}/{size}", method = RequestMethod.GET)
    public Result search(@PathVariable String keywords, @PathVariable Integer page, @PathVariable Integer size){
        Page<Article> pageData = articleSearchService.findByTitleOrContentLike(keywords, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Article>(pageData.getTotalElements(), pageData.getContent()));

    }

}
