package com.communitygroup.friend.controller;

import com.communitygroup.friend.pojo.Friend;
import com.communitygroup.friend.pojo.NoFriend;
import com.communitygroup.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-03-01 10:12
 * @Modified by:
 **/
@RestController
@RequestMapping("/friend")
@CrossOrigin
public class FriendController {

    @Autowired
    FriendService friendService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    HttpServletRequest request;


    /**
     * 添加好友
     * @param friendid
     * @param type 1表示喜欢， 0 表示不喜欢
     * @return
     */
    @RequestMapping(value = "/like/{friendid}/{type}", method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid, @PathVariable String type){
        Claims claim = (Claims) request.getAttribute("user_claim");
        if (claim == null || !claim.get("roles").equals("user")){
            return new Result(false, StatusCode.LOGINERROR, "权限不足");
        }
        //喜欢
        if (type.equals("1")){
            if (friendService.addFriend(claim.getId(), friendid).equals("0")){
                return new Result(false, StatusCode.REPERROR, "不能重复添加好友");
            }
        }else {  //不喜欢
            if (friendService.addNoFriend(claim.getId(), friendid).equals("0")){
                return new Result(false, StatusCode.REPERROR, "不能重复添加非好友");
            }
        }
        return new Result(true, StatusCode.OK, "添加好友成功");
    }


    @RequestMapping(value = "/{friendid}", method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable String friendid){
       Claims claims = (Claims) request.getAttribute("user_claim");
       if (claims == null || !claims.get("roles").equals("user")){
           return new Result(false, StatusCode.LOGINERROR, "权限不足");
       }
       friendService.deleteFriend(claims.getId(), friendid);
        return new Result(true, StatusCode.OK, "删除好友成功");
    }
}
