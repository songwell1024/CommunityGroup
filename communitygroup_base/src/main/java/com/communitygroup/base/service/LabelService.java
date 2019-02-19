package com.communitygroup.base.service;

import com.communitygroup.base.dao.LabelDao;
import com.communitygroup.base.pojo.Label;
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
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-18 20:57
 * @Modified by:
 **/
@Service
@Transactional
public class LabelService {

    @Autowired
    LabelDao labelDao;

    @Autowired
    IdWorker idWorker;

    /**
     * 查询全部的label
     * @return
     */
    public List<Label> findAll(){
        return labelDao.findAll();
    }

    /**
     *  根据id查询label
     * @param id
     * @return
     */
    public Label findLabelById(String id){
        return labelDao.findById(id).get();
    }

    /**
     * 添加标签
     * @param label
     */
    public void addLabel(Label label){
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    /**
     * 修改label
     * @param label
     */
    public void updateLabel(Label label){
        labelDao.save(label);
    }

    /**
     * 删除label
     * @param id
     */
    public void deleteLabelById(String id){
        labelDao.deleteById(id);
    }

    /**
     * 根据条件查询label
     * @param label
     * @return
     */
    public List<Label> findSearch(Label label){
        return labelDao.findAll(new Specification<Label>() {
            /**
             *
             * @param root 根对象, 也就是要把对象封装到那个对象中， where 类名 = label.getId
             * @param criteriaQuery 封装的是查询的关键字，比如order by, group by等等
             * @param criteriaBuilder 用来封装条件对象的,如果直接返回了null表示不需要任何的条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> resList = new ArrayList<>();

                //根据标签名称查询, cb.like是模糊查询
                //root.get(这里面的是Root<Label>中的Label中的属性名)  最终这句语句生成的sql语句是 where labelname like %XXX%
                if (label.getLabelname() !=  null && !"".equals(label.getLabelname())){
                    Predicate predicate = criteriaBuilder.like(root.get("labelname").as(String.class),
                           "%" +  label.getLabelname() + "%") ;
                    resList.add(predicate);
                }

                //根据label的state进行准确查询
                //相当于where state = "1"的sql 语句
                if (label.getState() != null && !"".equals(label.getState())){
                    Predicate predicate  = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
                    resList.add(predicate);
                }

                return criteriaBuilder.and(resList.toArray(new Predicate[resList.size()]));
            }
        });
    }

    /**
     * 分页查询
     * @param label
     * @param page
     * @param size
     * @return
     */
    public Page<Label> pageQuery(Label label, Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page -1, size);
        return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (label.getLabelname() != null && ! "".equals(label.getLabelname())){
                    Predicate predicate = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    predicateList.add(predicate);
                }

                if (label.getRecommend() != null && ! "".equals(label.getRecommend())){
                    Predicate predicate = criteriaBuilder.equal(root.get("recommend").as(String.class), label.getRecommend());
                    predicateList.add(predicate);
                }
                Predicate[] predicates = new Predicate[predicateList.size()];
                predicateList.toArray(predicates);
                return criteriaBuilder.and(predicates);
            }
        }, pageable);
    }
}
