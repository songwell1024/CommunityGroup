package com.communitygroup.service;

import com.communitygroup.dao.LabelDao;
import com.communitygroup.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-18 20:57
 * @Modified by:
 **/
@Service
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

}
