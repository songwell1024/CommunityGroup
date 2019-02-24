package com.communitygroup.spit.dao;

import com.communitygroup.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-24 10:01
 * @Modified by:
 **/
public interface SpitDao extends MongoRepository<Spit, String> {
//    @Query("db.spit.find({visits：{$gt:?})")
//    public List<Spit> search(Integer visitNum);
    public Page<Spit> findAllByParentid(String parentid, Pageable pageable);
}
