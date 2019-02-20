package com.communitygroup.recruit.dao;

import com.communitygroup.recruit.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-20 10:50
 * @Modified by:
 **/
public interface RecruitDAO extends JpaRepository<Recruit, String>, JpaSpecificationExecutor<Recruit> {

    //查询前6个推荐职位
    public List<Recruit> findTop6ByStateOrderByCreatetimeDesc(String state);

    //查询前6个最新职位
    public List<Recruit> findTop6ByStateNotOrderByCreatetimeDesc(String state);
}
