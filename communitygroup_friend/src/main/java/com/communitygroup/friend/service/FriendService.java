package com.communitygroup.friend.service;

import com.communitygroup.friend.client.FriendClient;
import com.communitygroup.friend.dao.FriendDao;
import com.communitygroup.friend.dao.NoFriendDao;
import com.communitygroup.friend.pojo.Friend;
import com.communitygroup.friend.pojo.NoFriend;
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

    @Autowired
    NoFriendDao noFriendDao;

    @Autowired
    FriendClient friendClient;


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
        friendClient.updateFansCount(friendId,1);  //朋友的粉丝数+1
        friendClient.updateFollowCount(userId, 1); //自己的关注数+1


        //判断对方是否也喜欢你，是的话就更新为相互喜欢
        if (friendDao.findByUseridAndFriendid(friendId, userId)!= null){
            friendDao.updateLike(userId, friendId,"1");
            friendDao.updateLike(friendId, userId,"1");
        }
        return "1";
    }

    /**
     * 添加非好友
     * 返回0 表示不能重复添加
     * @param userid
     * @param friendid
     * @return
     */
    public String addNoFriend(String userid, String friendid){
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userid, friendid);
        if (noFriend != null){
            return "0";
        }
        noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        friendClient.updateFollowCount(userid, -1);
        friendClient.updateFansCount(friendid,-1);
        return "1";
    }

    /**
     * 删除好友,然后添加黑名单
     * @param userid
     * @param friendid
     */
    public void deleteFriend(String userid, String friendid) {
        friendDao.deleteByUseridAndFriendid(userid, friendid);
        friendDao.updateLike(friendid,userid,"0");
        addNoFriend(userid, friendid);
    }
}
