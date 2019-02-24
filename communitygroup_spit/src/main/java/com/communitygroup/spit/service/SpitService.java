package com.communitygroup.spit.service;

import com.communitygroup.spit.dao.SpitDao;
import com.communitygroup.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.provider.ConfigFile;
import util.IdWorker;

import java.util.Date;
import java.util.List;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-24 10:08
 * @Modified by:
 **/
@Service
@Transactional
public class SpitService {
    @Autowired
    IdWorker idWorker;

    @Autowired
    SpitDao spitDao;

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 增加
     * 增加内容的时候为了避免出现空，所以需要一开始初始化一些内容
     * @param spit
     */
    public void add(Spit spit){
        spit.set_id(idWorker.nextId() + "");
        spit.setPublishtime(new Date());//发布日期
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        if(spit.getParentid()!=null && !"".equals(spit.getParentid())){ //如果存在上级ID,评论
            Query query=new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update=new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        spitDao.save(spit);
    }

    /**
     * 查询全部
     * @return
     */
    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Spit findById(String id){
        return spitDao.findById(id).get();
    }

    /**
     * 修改
     * @param spit
     */
    public void update(Spit spit){
        spitDao.save(spit);
    }

    /**
     * 根据id删除
     * @param id
     */
    public void deleteById(String id){
        spitDao.deleteById(id);
    }

//    /**
//     * 根据条件查询
//     * @param spit
//     * @return
//     */
//    public List<Spit> search(Spit spit){
//        return spitDao.findAll(new Example<Spit>() {
//            @Override
//            public Spit getProbe() {
//                return null;
//            }
//
//            @Override
//            public ExampleMatcher getMatcher() {
//                return null;
//            }
//        })
//    }

    /**
     * 吐槽点赞
     * @param id
     */
    public void thump(String id){
        //这种方式效率不高，因为查询了两次mongodb
//        Spit spit = spitDao.findById(id).get();
//        spit.setThumbup((spit.getThumbup() == null ? 0 : spit.getThumbup() + 1));
//        spitDao.save(spit);
        //直接使用原生的方法，没有必要进行查询，直接加1即可
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("thump", 1);
        mongoTemplate.updateFirst(query,update,"spit");
    }


    /**
     * 根据上级id查询并分页
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findAllByParentId(String parentid, Integer page, Integer size){
        Pageable pageable = PageRequest.of(page - 1, size);
        return spitDao.findAllByParentid(parentid, pageable);
    }

}
