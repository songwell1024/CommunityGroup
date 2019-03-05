package com.communitygroup.friend.pojo;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-28 20:23
 * @Modified by:
 **/
@Entity
@Table(name="tb_nofriend")
@IdClass(NoFriend.class)    //表示联合主键，添加也可以，不添加也可以
public class NoFriend implements Serializable {
    @Id
    private String userid;
    @Id
    private String friendid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFriendid() {
        return friendid;
    }

    public void setFriendid(String friendid) {
        this.friendid = friendid;
    }
}
