package com.communitygroup.user.dao;

import com.communitygroup.user.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    public User findByMobile(String mobile);

    /**
     * 更新粉丝数
     * @param userid
     * @param count
     */
    @Modifying
    @Query(value = "update tb_user set fanscount = fanscount + ?2 where userid = ?1", nativeQuery = true)
    public void updateFanscount(String userid, int count);

    /**
     * 更新关注数
     * @param userid
     * @param count
     */
    @Modifying
    @Query(value = "update tb_user set followcount = followcount + ?2 where userid = ?1", nativeQuery = true)
    public void updateFollowcount(String userid, int count);
	
}
