package com.communitygroup.qa.client;

import com.communitygroup.qa.client.impl.LabelClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-03-01 15:06
 * @Modified by:
 **/
@FeignClient(value = "communitygroup-base",fallback = LabelClientImpl.class)
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
