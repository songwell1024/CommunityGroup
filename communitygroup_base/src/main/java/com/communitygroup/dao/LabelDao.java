package com.communitygroup.dao;

import com.communitygroup.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @program: LabelDao
 * @description: 数据库查询层， JpaSpecificationExecutor用来做分页查询的，
 * JpaRepository<T, ID> T是你查询的数据类型， ID是你的主键
 * @author: Song
 * @create: Created in 2019-02-18 20:51
 * @Modified by:
 **/

public interface LabelDao extends JpaRepository<Label, String>, JpaSpecificationExecutor<Label> {
}
