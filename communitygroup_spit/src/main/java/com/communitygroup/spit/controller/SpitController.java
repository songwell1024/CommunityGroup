package com.communitygroup.spit.controller;

import com.communitygroup.spit.pojo.Spit;
import com.communitygroup.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import javafx.scene.input.InputMethodTextRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.ConfigFile;

import javax.validation.Valid;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-24 10:59
 * @Modified by:
 **/
@RestController
@CrossOrigin
@RequestMapping("spit")
public class SpitController {

    @Autowired
    SpitService spitService;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 查询全部数据
     * @return
     */
    @RequestMapping(method= RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }
    /**
     * 根据ID查询
     * @param id ID
     * @return
     */
    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    public Result findOne(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findById(id));
    }
    /**
     * 增加
     * @param spit
     */
    @RequestMapping(method=RequestMethod.POST)
    public Result add(@RequestBody Spit spit ){
        spitService.add(spit);
        return new Result(true,StatusCode.OK,"增加成功");
    }

    /**
     * 修改
     * @param spit
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method=RequestMethod.PUT)
    public Result update(@RequestBody Spit spit,@PathVariable String id )
    {
        spit.set_id(id);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    /**
     * 删除
     * @param id
     */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id ){
        spitService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 吐下槽点赞
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/thumbup/{spitId}", method = RequestMethod.PUT)
    public Result thump(@PathVariable String spitId){
        String userid = "111";
        if (redisTemplate.opsForValue().get("thump_" + userid + "_" + spitId) != null){
            return new Result(false, StatusCode.ERROR, "你已经点过赞了，不能重复点赞");
        }
        spitService.thump(spitId);
        redisTemplate.opsForValue().set("thump_" + userid + "_" + spitId, 1);
        return new Result(true, StatusCode.OK, "点赞成功");
    }

    /**
     * 根据上一级id查询并分页
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}", method = RequestMethod.GET)
    public Result findCommentByParentId(@PathVariable String parentid, @PathVariable Integer page, @PathVariable Integer size){
        Page<Spit> pageData = spitService.findAllByParentId(parentid,page,size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Spit>(pageData.getTotalElements(),pageData.getContent()));
    }


}
