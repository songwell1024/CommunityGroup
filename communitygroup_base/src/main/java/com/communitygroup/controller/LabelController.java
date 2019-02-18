package com.communitygroup.controller;

import com.communitygroup.pojo.Label;
import com.communitygroup.service.LabelService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @program: @CrossOrigin跨域
 * @description: label web访问
 * @author: Song
 * @create: Created in 2019-02-18 21:08
 * @Modified by:
 **/
@RestController
@RequestMapping("/label")
@CrossOrigin
public class LabelController {

    @Autowired
    LabelService labelService;

    /**
     * 查询全部label
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK, "查询成功", labelService.findAll());
    }

    /**
     * 根据id查label
     * @param labelId
     * @return
     */
    @RequestMapping(value = "/{labelId}", method = RequestMethod.GET)
    public Result findLabelById(@PathVariable String labelId){
        return new Result(true,StatusCode.OK,"查询成功", labelService.findLabelById(labelId));
    }

    /**
     * 增加标签
     * @param label
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result addLabel(@RequestBody Label label){
        labelService.addLabel(label);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改label
     * @param labelId
     * @param label
     * @return
     */
    @RequestMapping(value = "/{labelId}", method = RequestMethod.PUT)
    public Result updateLabel(@PathVariable String labelId, @RequestBody Label label){
        label.setId(labelId);
        labelService.updateLabel(label);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除label
     * @param labelId
     * @return
     */
    @RequestMapping(value = "/{labelId}", method = RequestMethod.DELETE)
    public Result deleteLabel(@PathVariable String labelId){
        labelService.deleteLabelById(labelId);
        return new Result(true, StatusCode.OK, "删除成功");
    }






}
