package com.communitygroup.recruit.service;

import com.communitygroup.recruit.dao.RecruitDAO;
import com.communitygroup.recruit.pojo.Recruit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-20 10:56
 * @Modified by:
 **/
@Service
public class RecruitService {

    @Autowired
    RecruitDAO recruitDAO;

    @Autowired
    IdWorker idWorker;

    /**
     * 查询所有的职位
     * @return
     */
    public List<Recruit> findAll(){
        return recruitDAO.findAll();
    }

    /**
     * 通过id查询所有的recruit
     * @param id
     * @return
     */
    public Recruit findById(String id){
        return recruitDAO.findById(id).get();
    }

    /**
     * 根据条件查询
     * @param recruit
     * @return
     */
    public List<Recruit> findSearch(Recruit recruit){
        Specification<Recruit> specification = createSpecification(recruit);
        return recruitDAO.findAll(specification);
    }

    /**
     * 分页查询
     * @param recruit
     * @return
     */
    public Page<Recruit> pageQuery(Recruit recruit, Integer page, Integer size){
        Specification<Recruit> specification = createSpecification(recruit);
        Pageable pageable = PageRequest.of(page - 1, size);
        return recruitDAO.findAll(specification, pageable);
    }

    /**
     * 增加
     * @param recruit
     */
    public void add(Recruit recruit){
        recruit.setId(String.valueOf(idWorker.nextId()));
        recruitDAO.save(recruit);
    }

    /**
     * 修改recruit
     * @param recruit
     */
    public void update(Recruit recruit){
        recruitDAO.save(recruit);
    }

    /**
     * 根据id删除
     * @param id
     */
    public void delete(String id){
        recruitDAO.deleteById(id);
    }

    /**
     * 查询推荐职位
     * @return
     */
    public List<Recruit> recommend(){
        return recruitDAO.findTop6ByStateOrderByCreatetimeDesc("2");
    }

    /**
     * 查询前6个最新职位
     * @return
     */
    public List<Recruit> newList(){
        return recruitDAO.findTop6ByStateNotOrderByCreatetimeDesc("0");
    }

    public Specification<Recruit> createSpecification(Recruit recruit){
        return new Specification<Recruit>() {
            @Override
            public Predicate toPredicate(Root<Recruit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();

                //Id查询
                if (recruit.getId() != null && !"".equals(recruit.getId())){
                    list.add(criteriaBuilder.like(root.get("id").as(String.class),"%" + recruit.getId() + "%"));
                }
                //职位名称
                if (recruit.getJobname() != null && !"".equals(recruit.getJobname())){
                    list.add(criteriaBuilder.like(root.get("jobname").as(String.class), "%" + recruit.getJobname() + "%"));
                }
                //薪资范围
                if (recruit.getSalary() != null && !"".equals(recruit.getSalary())){
                    list.add(criteriaBuilder.like(root.get("salary").as(String.class), "%"+ recruit.getSalary() + "%"));
                }
                // 经验要求
                if (recruit.getCondition()!=null && !"".equals(recruit.getCondition())) {
                    list.add(criteriaBuilder.like(root.get("condition").as(String.class), "%"+recruit.getCondition()+"%"));
                }
                // 学历要求
                if (recruit.getEducation()!=null && !"".equals(recruit.getEducation())) {
                    list.add(criteriaBuilder.like(root.get("education").as(String.class), "%"+recruit.getEducation()+"%"));
                }
                // 任职方式
                if (recruit.getType()!=null && !"".equals(recruit.getType())) {
                    list.add(criteriaBuilder.like(root.get("type").as(String.class), "%"+recruit.getType()+"%"));
                }
                // 办公地址
                if (recruit.getAddress()!=null && !"".equals(recruit.getAddress())) {
                    list.add(criteriaBuilder.like(root.get("address").as(String.class), "%"+recruit.getAddress()+"%"));
                }
                // 企业ID
                if (recruit.getEid()!=null && !"".equals(recruit.getEid())) {
                    list.add(criteriaBuilder.like(root.get("eid").as(String.class), "%"+ recruit.getEid()+"%"));
                }
                // 状态
                if (recruit.getState()!=null && !"".equals(recruit.getState())) {
                    list.add(criteriaBuilder.like(root.get("state").as(String.class), "%"+recruit.getState()+"%"));
                }
                // 网址
                if (recruit.getUrl()!=null && !"".equals(recruit.getUrl())) {
                    list.add(criteriaBuilder.like(root.get("url").as(String.class), "%"+recruit.getUrl()+"%"));
                }
                // 标签
                if (recruit.getLabel()!=null && !"".equals(recruit.getLabel())) {
                    list.add(criteriaBuilder.like(root.get("label").as(String.class), "%"+recruit.getLabel()+"%"));
                }
                // 职位描述
                if (recruit.getContent1()!=null && !"".equals(recruit.getContent1())) {
                    list.add(criteriaBuilder.like(root.get("content1").as(String.class), "%"+recruit.getContent1()+"%"));
                }
                // 职位要求
                if (recruit.getContent2()!=null && !"".equals(recruit.getContent2())) {
                    list.add(criteriaBuilder.like(root.get("content2").as(String.class), "%"+recruit.getContent2()+"%"));
                }
                Predicate[] predicates = new Predicate[list.size()];
                list.toArray(predicates);
                return criteriaBuilder.and(predicates);
            }
        };
    }

}
