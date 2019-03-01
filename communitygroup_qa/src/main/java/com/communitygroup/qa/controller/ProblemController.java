package com.communitygroup.qa.controller;
import java.util.List;
import java.util.Map;

import com.communitygroup.qa.pojo.Problem;
import com.communitygroup.qa.service.ProblemService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


import entity.PageResult;
import entity.Result;
import entity.StatusCode;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",problemService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param problem
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(HttpServletRequest request, @RequestBody Problem problem  ){
		 Claims user_claim = (Claims) request.getAttribute("admin_claim");
		 if (user_claim == null || "".equals(user_claim)){
			 return new Result(false,StatusCode.LOGINERROR,"请先登录");
		 }
		problemService.add(problem);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		problemService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 最新回答
	 * @param labelid
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/newlist/{labelid}/{page}/{size}", method = RequestMethod.GET)
	public Result newList(@PathVariable String labelid, @PathVariable Integer page, @PathVariable Integer size){
		Page<Problem> pageData = problemService.newList(labelid, page, size);
		return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
	}

	/**
	 * 热门回答
	 * @param labelid
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/hotlist/{labelid}/{page}/{size}", method = RequestMethod.GET)
	public Result hotList(@PathVariable String labelid, @PathVariable Integer page, @PathVariable Integer size){
		Page<Problem> pageData = problemService.hotList(labelid, page, size);
		return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
	}

	/**
	 * 等待回答
	 * @param labelid
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/waitlist/{labelid}/{page}/{size}", method = RequestMethod.GET)
	public Result waitList(@PathVariable String labelid, @PathVariable Integer page, @PathVariable Integer size){
		Page<Problem> pageData = problemService.waitList(labelid, page, size);
		return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
	}
}
