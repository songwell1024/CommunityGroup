package com.communitygroup.friend.dao;

import com.communitygroup.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-03-01 14:27
 * @Modified by:
 **/
public interface NoFriendDao extends JpaRepository<NoFriend, String>, JpaSpecificationExecutor<NoFriend> {
    public NoFriend findByUseridAndFriendid(String userid, String friendid);
}
