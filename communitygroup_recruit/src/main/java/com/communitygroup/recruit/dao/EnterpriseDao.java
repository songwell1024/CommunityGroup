package com.communitygroup.recruit.dao;

import com.communitygroup.recruit.pojo.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @program: EnterpriseDao
 * @description: dao查询
 * @author: Song
 * @create: Created in 2019-02-20 14:06
 * @Modified by:
 **/
public interface EnterpriseDao extends JpaRepository<Enterprise, String>, JpaSpecificationExecutor<Enterprise> {

    //查询热门企业
    public List<Enterprise> findByIshot(String isHot);

}
