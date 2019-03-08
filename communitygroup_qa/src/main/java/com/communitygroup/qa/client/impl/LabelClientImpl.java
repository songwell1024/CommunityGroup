package com.communitygroup.qa.client.impl;

import com.communitygroup.qa.client.LabelClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-03-01 15:35
 * @Modified by:
 **/
@Component
public class LabelClientImpl implements LabelClient {
    @Override
    public Result findLabelById(String labelId) {
        return new Result(false, StatusCode.ERROR,"熔断器启动了");
    }
}
