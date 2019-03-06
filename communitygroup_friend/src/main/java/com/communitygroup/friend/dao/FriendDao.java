package com.communitygroup.friend.dao;

import com.communitygroup.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-03-01 09:41
 * @Modified by:
 **/
public interface FriendDao extends JpaRepository<Friend,String>, JpaSpecificationExecutor<Friend> {

    /**
     * 查询
     * @param userid
     * @param friendid
     * @return
     */
    public Friend findByUseridAndFriendid(String userid, String friendid);

    public void deleteByUseridAndFriendid(String userid, String friendid);

    /**
     * 更新喜欢状态
     * @param userid
     * @param friendid
     * @param islike
     */
    @Modifying
    @Query(value = "update tb_friend set islike = ?3 where userid = ?1 and friendid = ?2", nativeQuery = true)
    public void updateLike(String userid, String friendid, String islike);


}
