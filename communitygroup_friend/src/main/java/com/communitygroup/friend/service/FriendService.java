package com.communitygroup.friend.service;

import com.communitygroup.friend.dao.FriendDao;
import com.communitygroup.friend.pojo.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-03-01 09:44
 * @Modified by:
 **/
@Service
@Transactional
public class FriendService {
    @Autowired
    FriendDao friendDao;


    public String addFriend(String userId, String friendId){
        Friend friend = friendDao.findByUseridAndFriendid(userId, friendId);
        if (friend != null){
            return "0";
        }
        friend = new Friend();
        friend.setUserid(userId);
        friend.setFriendid(friendId);
        friend.setIslike("0");
        friendDao.save(friend);

        //判断对方是否也喜欢你，是的话就更新为相互喜欢
        if (friendDao.findByUseridAndFriendid(friendId, userId)!= null){
            friendDao.updateLike(userId, friendId,"1");
            friendDao.updateLike(friendId, userId,"1");
        }
        return "1";

    }

}
