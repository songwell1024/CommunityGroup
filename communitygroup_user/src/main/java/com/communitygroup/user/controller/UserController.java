package com.communitygroup.user.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.communitygroup.user.pojo.Admin;
import com.communitygroup.user.pojo.User;
import com.communitygroup.user.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	HttpServletRequest request;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
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
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 短信发送
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/sendsms/{mobile}", method =  RequestMethod.POST)
	public Result sendSms(@PathVariable String mobile){
		userService.sendSms(mobile);
		return new Result(true, StatusCode.OK, "短信发送成功");
	}

	/**
	 * 注册
	 * @param code
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/register/{code}", method = RequestMethod.POST)
	public Result register(@PathVariable String code, @RequestBody User user){
		userService.register(user, code);
		return new Result(true, StatusCode.OK, "注册成功");
	}

	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public Result login(@RequestBody User user){

		User findUser = userService.findByPasswordAndMobile(user.getMobile(), user.getPassword());
		if (findUser == null || "".equals(findUser)){
			return new Result(false, StatusCode.LOGINERROR, "用户名或密码错误");
		}
		String token = jwtUtil.createJWT(findUser.getId(), findUser.getMobile(), "user");
		Map<String, String> map = new HashMap<>();
		map.put("mobile", findUser.getMobile());
		map.put("token", token);
		return new Result(true,StatusCode.OK, "登录成功", map);
	}

	/**
	 * 更新粉丝数
	 * @param userid
	 * @param x
	 */
	@RequestMapping(value="/incfans/{userid}/{x}",method= RequestMethod.POST)
	public void updateFansCount(@PathVariable String userid, @PathVariable int x){
		Claims claims = (Claims) request.getAttribute("roles");
		if (claims == null){
			throw new RuntimeException("权限不足");
		}
		userService.updateFanscount(userid,x);
	}

	/**
	 * 更新关注数
	 * @param userid
	 * @param x
	 */
	@RequestMapping(value="/incfollow/{userid}/{x}",method= RequestMethod.POST)
	public void updateFollowCount(@PathVariable String userid, @PathVariable int x){
		Claims claims = (Claims) request.getAttribute("roles");
		if (claims == null){
			throw new RuntimeException("权限不足");
		}
		userService.updateFollowcount(userid,x);
	}






	
}
