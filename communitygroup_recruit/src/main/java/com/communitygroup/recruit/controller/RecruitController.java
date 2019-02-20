package com.communitygroup.recruit.controller;

import com.communitygroup.recruit.pojo.Recruit;
import com.communitygroup.recruit.service.RecruitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: RecruitController
 * @description: controller层
 * @author: Song
 * @create: Created in 2019-02-20 13:28
 * @Modified by:
 **/
@RestController
@RequestMapping("/recruit")
@CrossOrigin
public class RecruitController {

    @Autowired
    RecruitService recruitService;

    /**
     * 增加
     * @param recruit
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Recruit recruit){
        recruitService.add(recruit);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 查询所有
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK, "查询成功", recruitService.findAll());
    }

    /**
     * 根据id查询
     * @param recruitId
     * @return
     */
    @RequestMapping(value = "/{recruitId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String recruitId){
        return new Result(true, StatusCode.OK, "查询成功", recruitService.findById(recruitId));
    }

    /**
     * 修改
     * @param recruit
     * @return
     */
    @RequestMapping(value = "/{recruitId}", method = RequestMethod.PUT)
    public Result update(@PathVariable String recruitId, @RequestBody Recruit recruit){
        recruit.setId(recruitId);
        recruitService.update(recruit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 根据Id删除
     * @param recruitId
     * @return
     */
    @RequestMapping(value = "/{recruitId}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String recruitId){
        recruitService.delete(recruitId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 条件查询
     * @param recruit
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Recruit recruit){
        return new Result(true, StatusCode.OK, "查询成功", recruitService.findSearch(recruit));
    }

    /**
     * 分页查询
     * @param recruit
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result pageQuery(@RequestBody Recruit recruit, @PathVariable Integer page, @PathVariable Integer size){
        Page<Recruit> recruitPage = recruitService.pageQuery(recruit, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Recruit>(recruitPage.getTotalElements(), recruitPage.getContent()));
    }

    /**
     * 查询推荐
     * @return
     */
    @RequestMapping(value = "/search/recommend", method = RequestMethod.GET)
    public Result recommend(){
        return new Result(true, StatusCode.OK, "查询成功", recruitService.recommend());
    }

    /**
     * 查询最新职位
     * @return
     */
    @RequestMapping(value = "/search/newlist", method = RequestMethod.GET)
    public Result newList(){
        return new Result(true, StatusCode.OK, "查询成功", recruitService.newList());
    }

}
