package com.communitygroup.qa.dao;

import com.communitygroup.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{
    /**
     * 最新回答
     * @param labelid
     * @param pageable
     * @return
     */
    //select * from tb_problem where id in (select problemid from tb_pl where labelid = "1") order by replytime desc;
    @Query(value = "select * from tb_problem, tb_pl where id = problemid and labelid = ? order by replytime desc", nativeQuery = true)
    public Page<Problem> newList(String labelid, Pageable pageable);

    /**
     * 热门回答
     * @param labelid
     * @param pageable
     * @return
     */
    @Query(value = "select * from tb_problem, tb_pl where id = problemid and labelid = ? order by reply desc", nativeQuery = true)
    public Page<Problem> hotList(String labelid, Pageable pageable);

    /**
     * 等待回答
     * @param labelid
     * @param pageable
     * @return
     */
    @Query(value = "select * from tb_problem, tb_pl where id = problemid and labelid = ? and reply = 0 order by createtime", nativeQuery = true)
    public Page<Problem> waitList(String labelid, Pageable pageable);

}
