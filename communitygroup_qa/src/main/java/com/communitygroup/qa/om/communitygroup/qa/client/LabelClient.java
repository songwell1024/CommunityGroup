package com.communitygroup.qa.om.communitygroup.qa.client;

import entity.Result;
import entity.StatusCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-02-28 16:45
 * @Modified by:
 **/
@FeignClient("communitygroup-base")
@Component
public interface LabelClient {

    /**
     * value = "/label/{labelId} 访问路径必须写全，要不然找不到
     * @PathVariable("labelId") PathVariable中的属性必须填写，要不然找不到
     * @param labelId
     * @return
     */
    @RequestMapping(value = "/label/{labelId}", method = RequestMethod.GET)
    public Result findLabelById(@PathVariable("labelId") String labelId);
}
