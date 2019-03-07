package com.communitygroup.friend.client;

import io.jsonwebtoken.Claims;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-03-01 15:08
 * @Modified by:
 **/
@FeignClient("communitygroup-user")
@Component
public interface FriendClient {

    /**
     * 更新粉丝数
     * @param userid
     * @param x
     */
    @RequestMapping(value="/user/incfans/{userid}/{x}",method= RequestMethod.POST)
    public void updateFansCount(@PathVariable("userid") String userid, @PathVariable("x") int x);

    /**
     * 更新关注数
     * @param userid
     * @param x
     */
    @RequestMapping(value="/user/incfollow/{userid}/{x}",method= RequestMethod.POST)
    public void updateFollowCount(@PathVariable("userid") String userid, @PathVariable("x") int x);

}
