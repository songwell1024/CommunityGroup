package com.communitygroup.article.dao;

import com.communitygroup.article.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @Modifying : 增删改都需要加
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

    /**
     * 审核
     * @param id
     */
    @Modifying
    @Query(value = "update tb_article set state = '1' where id = ?1", nativeQuery = true)
    public void updateState(String id);

    /**
     * 点赞
     * @param id
     */
    @Modifying
    @Query(value = "update tb_article set thumbup = thumbup + 1 where id = ?1", nativeQuery = true)
    public void updateThumbup(String id);


	
}
